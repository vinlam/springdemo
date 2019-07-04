package com.core.security.protection;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.core.security.ProtectRequestWrapper;


public class SafeFilter extends OncePerRequestFilter {

	@Autowired
	private ProtectionManager protectionManager;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		if(StringUtils.isNotBlank(requestURI))
			requestURI = requestURI.replace(request.getContextPath(),"");
		
		
		ProtectRequestWrapper protectRequestWrapper = new ProtectRequestWrapper(request,protectionManager);
		filterChain.doFilter(protectRequestWrapper, response);
	}
}
