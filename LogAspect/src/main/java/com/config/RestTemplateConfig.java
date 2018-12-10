package com.config;

import java.io.Closeable;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig implements Closeable{
	private static final Logger log = LoggerFactory.getLogger(RestTemplateConfig.class);

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(clientHttpRequestFactory());
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
		return restTemplate;
	}
	
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		HttpClient httpClient = getHttpClient();
		try {
			((CloseableHttpClient) httpClient).close();
		} catch (Exception e) {
			log.error("close httpclient fail", e);
		}
	}
	
	public HttpClient getHttpClient() {
		HttpClient httpClient = clientHttpRequestFactory().getHttpClient();
		return null;
	}

	@Bean
	public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
		try {
			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
//                public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
//                    return true;
//                }
//            }).build();

			SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
					.loadTrustMaterial(null, new TrustStrategy() {
						public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
							return true;
						}
					}).build();
			httpClientBuilder.setSSLContext(sslContext);
			HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
			SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
					hostnameVerifier);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.getSocketFactory())
					.register("https", sslConnectionSocketFactory).build();// 注册http和https请求
			// 开始设置连接池
			PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(
					socketFactoryRegistry);
			poolingHttpClientConnectionManager.setMaxTotal(2700); // 最大连接数2700
			poolingHttpClientConnectionManager.setDefaultMaxPerRoute(100); // 同路由并发数100
			httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
			httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(3, true)); // 重试次数
			HttpClient httpClient = httpClientBuilder.build();
			HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
					httpClient); // httpClient连接配置
			clientHttpRequestFactory.setConnectTimeout(20000); // 连接超时
			clientHttpRequestFactory.setReadTimeout(30000); // 数据读取超时时间
			clientHttpRequestFactory.setConnectionRequestTimeout(20000); // 连接不够用的等待时间
			return clientHttpRequestFactory;
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			log.error("初始化HTTP连接池出错", e);
		}
		return null;
	}

}
