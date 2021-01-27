package com.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.wrapper.WrappedHttpServletRequest;

@WebFilter(value = { "/test/wrapped/*" })
public class SimpleWrappedFilter implements Filter {
	private static Logger log = LoggerFactory.getLogger(SimpleWrappedFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			WrappedHttpServletRequest requestWrapper = new WrappedHttpServletRequest((HttpServletRequest) request);

			if ("POST".equals(requestWrapper.getMethod().toUpperCase())) {
				// 获取请求参数
				String params = requestWrapper.getRequestParams();
				log.info("filer-post请求参数:[params={}]", params);
			} else {
				log.info("非post请求");
			}

			// 这里doFilter传入我们实现的子类
			chain.doFilter(requestWrapper, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public void destroy() {

	}
}