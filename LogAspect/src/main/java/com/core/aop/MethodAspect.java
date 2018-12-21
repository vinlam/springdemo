package com.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MethodAspect {
    
    //定义切入点,拦截servie包其子包下的所有类的所有方法
//    @Pointcut("execution(* com.haiyang.onlinejava.complier.service..*.*(..))")
    //拦截指定的方法,这里指只拦截TestService.getResultData这个方法
    @Pointcut("execution(* com.service.UserService.addUser(..))")
    public void excuteService() {
 
    }
    
    @Before("excuteService()")
	public void doBefore(JoinPoint joinPoint) {
		System.out.println("==========执行controller前置通知===============");
	}

}
