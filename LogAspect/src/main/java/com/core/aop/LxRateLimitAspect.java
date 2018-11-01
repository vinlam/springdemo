package com.core.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.define.annotation.LxRateLimit;
import com.google.common.util.concurrent.RateLimiter;

@Aspect
@Component
public class LxRateLimitAspect {

	private final static Logger logger = LoggerFactory
			.getLogger(LxRateLimitAspect.class);

	private RateLimiter rateLimiter = RateLimiter.create(Double.MAX_VALUE);

	/**
	 * 定义切点 1、通过扫包切入 2、带有指定注解切入
	 */
	// @Pointcut("execution(public * com.ycn.springcloud.*.*(..))")
	@Pointcut("@annotation(com.define.annotation.LxRateLimit)")
	public void checkPointcut() {
	}

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 *            切点
	 */
	//@Before("@annotation(lxRateLimit)")
	@Before(value = "checkPointcut() && @annotation(lxRateLimit)")
	public void doBefore(JoinPoint joinPoint, LxRateLimit lxRateLimit) {
		
		System.out.println("==========执行controller前置通知==============="+lxRateLimit.perSecond());
		if (logger.isInfoEnabled()) {
			logger.info("before " + joinPoint);
		}
	}
	
	
	@ResponseBody
	@Around(value = "checkPointcut()")
	public Object aroundNotice(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("拦截到了{}方法...", pjp.getSignature().getName());
		Signature signature = pjp.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		// 获取目标方法
		Method targetMethod = methodSignature.getMethod();
		if (targetMethod.isAnnotationPresent(LxRateLimit.class)) {
			// 获取目标方法的@LxRateLimit注解
			LxRateLimit lxRateLimit = targetMethod.getAnnotation(LxRateLimit.class);
			rateLimiter.setRate(lxRateLimit.perSecond());
			if (!rateLimiter.tryAcquire(lxRateLimit.timeOut(),lxRateLimit.timeOutUnit()))
			{	
				return "服务器繁忙，请稍后再试!";
			}
		}
		return pjp.proceed();
	}
}
