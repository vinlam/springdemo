package com.core.interceptor;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class RestTemplateHeaderModifierInterceptor implements ClientHttpRequestInterceptor {

//	@Override
//	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
//			throws IOException {
//		ClientHttpResponse response = execution.execute(request, body);
//		response.getHeaders().add("Foo", "bar");
//		return response;
//	}
	private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateHeaderModifierInterceptor.class);

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		logRequestDetails(request);
		ClientHttpResponse response = execution.execute(request, body);
		response.getHeaders().add("Foo", "bar");
		return response;
	}

	private void logRequestDetails(HttpRequest request) {

		LOGGER.info("Request Headers: {}", request.getHeaders());
		LOGGER.info("Request Method: {}", request.getMethod());
		LOGGER.info("Request URI: {}", request.getURI());
	}
}
