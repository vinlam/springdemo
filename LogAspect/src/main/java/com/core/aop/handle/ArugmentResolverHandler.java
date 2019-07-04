package com.core.aop.handle;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.define.annotation.Param;

public class ArugmentResolverHandler implements HandlerMethodArgumentResolver{

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// TODO Auto-generated method stub
		return parameter.hasMethodAnnotation(Param.class);
	}
    //将值注入参数
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		Param param = parameter.getParameterAnnotation(Param.class);
		String value = param.value();
		if(value == null || "".equalsIgnoreCase(value)) {
			value = "123";
		}
		return value;
	}

}
