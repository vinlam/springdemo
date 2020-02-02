package com.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginCheckPermissionFilter extends AuthorizationFilter {
	private final static Logger logger = LoggerFactory.getLogger(LoginCheckPermissionFilter.class);

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object arg2) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		String url = req.getRequestURI();
		try {
			Subject subject = SecurityUtils.getSubject();

			return subject.isPermitted(url);
		} catch (Exception e) {
			logger.error("Check perssion error", e);
		}
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		Subject subject = getSubject(request, response);
		if (subject.getPrincipal() == null) {
			saveRequestAndRedirectToLogin(request, response);
		} else {
			return true;
		}
		return false;
	}

}
