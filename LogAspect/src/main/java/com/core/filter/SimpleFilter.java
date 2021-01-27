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
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.thread.ThreadCache;

@WebFilter(value = { "/test/threadLocal/*" })
public class SimpleFilter implements Filter {
	private static Logger log = LoggerFactory.getLogger(SimpleFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		try {
			if ("POST".equals(req.getMethod().toUpperCase())) {
				// 获取请求参数
				byte[] bytes = IOUtils.toByteArray(request.getInputStream());
				String params = new String(bytes, req.getCharacterEncoding());
				ThreadCache.setPostRequestParams(params);
				log.info("filer-post请求参数:[params={}]", params);
			} else {
				log.info("非post请求");
			}

			chain.doFilter(request, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public void destroy() {

	}
}