package com.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.core.interceptor.RestTemplateHeaderModifierInterceptor;

@Configuration
public class RestClientConfig {

	@Bean("rest")
	public RestTemplate restTemplate() {
		RestTemplate rest = new RestTemplate();

		List<ClientHttpRequestInterceptor> interceptors = rest.getInterceptors();
		if (CollectionUtils.isEmpty(interceptors)) {
			interceptors = new ArrayList<>();
		}
		interceptors.add(new RestTemplateHeaderModifierInterceptor());
		rest.setInterceptors(interceptors);

		rest.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
		rest.setErrorHandler(new DefaultResponseErrorHandler());
		return rest;
	}
}