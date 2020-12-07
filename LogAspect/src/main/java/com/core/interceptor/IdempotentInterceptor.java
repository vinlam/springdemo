package com.core.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.define.annotation.Idempotent;
import com.util.TokenUtil;
@Component
public class IdempotentInterceptor implements HandlerInterceptor {
	private final static Logger log = LoggerFactory.getLogger(IdempotentInterceptor.class); 
	@Autowired
	private TokenUtil tokenUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		// 对有Idempotent注解的方法进行拦截校验
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		Idempotent methodAnnotation = method.getAnnotation(Idempotent.class);
		if (methodAnnotation != null) {
			// token 校验
			try {
				tokenUtil.verifyToken(request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("token verify err:"+e.getMessage());
				return false;
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
