package com.common.gateway;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * @title：使用spring的AsyncRestTemplate替代httpclient工具
 */
@Component
@Lazy(false)
public class AsyncRestClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncRestClient.class);

	private static AsyncRestTemplate asyncRestTemplate;

	static {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		// 设置链接超时时间
		factory.setConnectTimeout(100);
		// 设置读取资料超时时间
		factory.setReadTimeout(200);
		// 设置异步任务（线程不会重用，每次调用时都会重新启动一个新的线程）
		factory.setTaskExecutor(new SimpleAsyncTaskExecutor());
		asyncRestTemplate = new AsyncRestTemplate(factory);

		LOGGER.info("RestClient初始化完成");
	}

	private AsyncRestClient() {

	}

	@PostConstruct
	public static AsyncRestTemplate getClient() {
		// asyncRestTemplate.getMessageConverters().add(0, new
		// StringHttpMessageConverter(Charset.forName("UTF-8")));
		// 删除所有的 StringHttpMessageConverter
		Iterator<HttpMessageConverter<?>> iterator = asyncRestTemplate.getMessageConverters().iterator();
		while (iterator.hasNext()) {
			final HttpMessageConverter<?> converter = iterator.next();
			if (converter instanceof StringHttpMessageConverter) {
				iterator.remove();
			}
		}
		// 添加内容转换器
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
		messageConverters.add(new MappingJackson2HttpMessageConverter());

		// 添加 UTF-8 的解析器
		asyncRestTemplate.getMessageConverters().addAll(messageConverters);
		return asyncRestTemplate;
	}
}