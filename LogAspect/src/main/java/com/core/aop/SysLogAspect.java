package com.core.aop;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.define.annotation.Logs;
import com.entity.SysLog;
import com.entity.User;
import com.service.SysLogService;
import com.service.impl.SysLogServiceImp;
import com.util.IpUtil;
import com.util.JsonUtil;

/**
 * @author
 * @E-mail: email
 * @version 创建时间：2015-10-19 下午4:29:05
 * @desc 切点类
 */

@Aspect
@Component
public class SysLogAspect {

	// 注入Service用于把日志保存数据库
	// 这里我用resource注解，一般用的是@Autowired，他们的区别如有时间我会在后面的博客中来写
	@Autowired
	private SysLogServiceImp sysLogServiceImp;

	private static final Logger logger = LoggerFactory
			.getLogger(SysLogAspect.class);

	// Controller层切点
	@Pointcut("execution (* com.controller..*.*(..)) && !execution(* com.controller.UploadController.*(..))")
	//@Pointcut("within (com.controller..*.Controller)")
	//@Pointcut("within(com.controller..*Controller) || within(com.entity..*Entity) || within(com.dao..*Dao) || within (com.service..*Service)")
	public void controllerAspect() {
	}

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 *            切点
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
		logger.info("==========执行controller前置通知===============");
		if (logger.isInfoEnabled()) {
			logger.info("before " + joinPoint);
		}
	}

	// 配置controller环绕通知,使用在方法aspect()上注册的切入点
//	@Around("controllerAspect()")
//	public void around(JoinPoint joinPoint) {
//		logger.info("==========开始执行controller环绕通知===============");
//		long start = System.currentTimeMillis();
//		try {
//			((ProceedingJoinPoint) joinPoint).proceed();
//			long end = System.currentTimeMillis();
//			if (logger.isInfoEnabled()) {
//				logger.info("around " + joinPoint + "\tUse time : "
//						+ (end - start) + " ms!");
//			}
//			logger.info("==========结束执行controller环绕通知===============");
//		} catch (Throwable e) {
//			long end = System.currentTimeMillis();
//			if (logger.isInfoEnabled()) {
//				logger.info("around " + joinPoint + "\tUse time : "
//						+ (end - start) + " ms with exception : "
//						+ e.getMessage());
//			}
//		}
//	}

	/**
	 * 后置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 *            切点
	 * @throws Throwable 
	 */
	@Around("controllerAspect()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();//获取request
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();//获取response

		long start = System.currentTimeMillis();
		logger.info("Enter : {}.{}() {}",
				joinPoint.getTarget().getClass().getName(), 
				joinPoint.getSignature().getName(),
				"withargs:"+JsonUtil.beanToJson(joinPoint.getArgs())
				);
		try {
			Object result = joinPoint.proceed();
			long end = System.currentTimeMillis();
			logger.info("exit : {}.{}() elapsed: {}ms {}",
					joinPoint.getTarget().getClass().getName(), 
					joinPoint.getSignature().getName(),
					end-start,
					"withargs:"+JsonUtil.beanToJson(result));
			return result;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("IllegalArgumentException : {}.{}() {}",
					joinPoint.getTarget().getClass().getName(), 
					joinPoint.getSignature().getName(),
					"withargs:"+JsonUtil.beanToJson(joinPoint.getArgs()),e);
			throw e;
		}
	}
	
	
	/**
	 * 后置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 *            切点
	 */
	@After("controllerAspect()")
	public void after(JoinPoint joinPoint) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();//获取request
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();//获取response

		/*
		 * HttpServletRequest request = ((ServletRequestAttributes)
		 * RequestContextHolder.getRequestAttributes()).getRequest();
		 * HttpSession session = request.getSession();
		 */
		// 读取session中的用户
		// User user = (User) session.getAttribute("user");
		// 请求的IP
		// String ip = request.getRemoteAddr();
		User user = new User();
		user.setId(1);
		user.setName("张三");
		String ip = IpUtil.getIpAddr(request);
		try {
			String targetName = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			Object[] arguments = joinPoint.getArgs();
			Class targetClass = Class.forName(targetName);
			Method[] methods = targetClass.getMethods();
			String operationType = "";
			String operationName = "";
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					Class[] clazzs = method.getParameterTypes();
					if (clazzs.length == arguments.length) {
						if(method.getAnnotation(Logs.class) != null){
							operationType = method.getAnnotation(Logs.class).operationType();
							operationName = method.getAnnotation(Logs.class).operationName();
							break;
						}
					}
				}
			}
			// *========控制台输出=========*//
			logger.info("=====controller后置通知开始=====");
			logger.info("请求方法:"
					+ (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()") + "."
					+ operationType);
			
			logger.info("方法描述:" + operationName);
			logger.info("请求人:" + user.getName());
			logger.info("请求IP:" + ip);
			// *========数据库日志=========*//
			SysLog log = new SysLog();
			log.setId(UUID.randomUUID().toString());
			log.setDescription(operationName);
			log.setMethod((joinPoint.getTarget().getClass().getName() + "."
					+ joinPoint.getSignature().getName() + "()")
					+ "." + operationType);
			log.setLogType((long) 0);
			log.setRequestIp(ip);
			log.setExceptioncode(null);
			log.setExceptionDetail(null);
			log.setParams(null);
			log.setCreateBy(user.getName());
			log.setCreateDate(new Date());
			// 保存数据库
			sysLogServiceImp.insert(log);
			logger.info("=====controller后置通知结束=====");
		} catch (Exception e) {
			// 记录本地异常日志
			e.printStackTrace();
			logger.error("==后置通知异常==");
			logger.error("异常信息:{}", e.getMessage());
		}
	}

	// 配置后置返回通知,使用在方法aspect()上注册的切入点
	@AfterReturning("controllerAspect()")
	public void afterReturn(JoinPoint joinPoint) {
		logger.info("=====执行controller后置返回通知=====");
		if (logger.isInfoEnabled()) {
			logger.info("afterReturn " + joinPoint);
		}
	}

	/**
	 * 异常通知 用于拦截记录异常日志
	 * 
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();//获取request
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();//获取response

		/*
		 * HttpServletRequest request = ((ServletRequestAttributes)
		 * RequestContextHolder.getRequestAttributes()).getRequest();
		 * HttpSession session = request.getSession(); //读取session中的用户 User user
		 * = (User) session.getAttribute(WebConstants.CURRENT_USER); //获取请求ip
		 * String ip = request.getRemoteAddr();
		 */
		// 获取用户请求方法的参数并序列化为JSON格式字符串

		User user = new User();
		user.setId(1);
		user.setName("");
		String ip = IpUtil.getIpAddr(request);

		String params = "";
		if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
			for (int i = 0; i < joinPoint.getArgs().length; i++) {
				params += JsonUtil.beanToJson(joinPoint.getArgs()[i]) + ";";
			}
		}
		try {

			String targetName = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			Object[] arguments = joinPoint.getArgs();
			@SuppressWarnings("rawtypes")
			Class targetClass = Class.forName(targetName);
			Method[] methods = targetClass.getMethods();
			String operationType = "";
			String operationName = "";
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					@SuppressWarnings("rawtypes")
					Class[] clazzs = method.getParameterTypes();
					if (clazzs.length == arguments.length) {
						if(method.getAnnotation(Logs.class) != null){
							operationType = method.getAnnotation(Logs.class).operationType();
							operationName = method.getAnnotation(Logs.class).operationName();
							break;
						}
					}
				}
			}
			/* ========控制台输出========= */
			logger.info("=====异常通知开始=====");
			logger.info("异常代码:" + e.getClass().getName());
			logger.info("异常信息:" + e.getMessage());
			logger.info("异常方法:"
					+ (joinPoint.getTarget().getClass().getName() + "."
							+ joinPoint.getSignature().getName() + "()") + "."
					+ operationType);
			logger.info("方法描述:" + operationName);
			logger.info("请求人:" + user.getName());
			logger.info("请求IP:" + ip);
			logger.info("请求参数:" + params);
			/* ==========数据库日志========= */
			SysLog log = new SysLog();
			log.setId(UUID.randomUUID().toString());
			log.setDescription(operationName);
			log.setExceptioncode(e.getClass().getName());
			log.setLogType((long) 1);
			log.setExceptionDetail(e.getMessage());
			log.setMethod((joinPoint.getTarget().getClass().getName() + "."
					+ joinPoint.getSignature().getName() + "()"));
			log.setParams(params);
			log.setCreateBy(user.getName());
			log.setCreateDate(new Date());
			log.setRequestIp(ip);
			// 保存数据库
			sysLogServiceImp.insertTest(log);
			logger.info("=====异常通知结束=====");
		} catch (Exception ex) {
			// 记录本地异常日志
			logger.error("==异常通知异常==");
			logger.error("异常信息:{}", ex.getMessage());
		}
		/* ==========记录本地异常日志========== */
		logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget()
				.getClass().getName()
				+ joinPoint.getSignature().getName(), e.getClass().getName(),
				e.getMessage(), params);

	}

}