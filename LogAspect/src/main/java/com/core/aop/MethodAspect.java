package com.core.aop;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.common.solr.SolrClient;
import com.dao.TB;
import com.dao.TbItemSearchDAO;
import com.define.annotation.Logs;
import com.entity.User;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

@Component
@Aspect
public class MethodAspect {

	// 定义切入点,拦截servie包其子包下的所有类的所有方法
//    @Pointcut("execution(* com.haiyang.onlinejava.complier.service..*.*(..))")
	// 拦截指定的方法,这里指只拦截TestService.getResultData这个方法
	//@Pointcut("execution(* com.service.UserService.getUserB(..))")
//	@Pointcut("execution(* com.dao.TbItemSearchDAO.*(..))")
	@Pointcut("execution(* com.dao.TB.getUser(..))")
	public void excuteService() {

	}

	@Before("excuteService()")
	public String doBefore(JoinPoint joinPoint) throws Exception {
		Object[] args = joinPoint.getArgs();
		String classType = joinPoint.getTarget().getClass().getName();
		Class<?> clazz = Class.forName(classType);
		String clazzName = clazz.getName();
		String methodName = joinPoint.getSignature().getName(); // 获取方法名称   
		//获取参数名称和值  
		Map<String, Object> nameAndArgs = this.getFieldsName(this.getClass(), clazzName, methodName, args);
		//nameAndArgs的两种类型，用实体类接收的类似这样： reqParams=com.ynet.finmall.innermanage.event.SaveOrUpdateRoleReqParam@616b9c0e
		// 用Map<String,Object>接收的是这样：menuNo=56473283，遍历这个map区分两种不同，使用不同的取值方式。
		//根据获取到的值所属的不同类型通过两种不同的方法获取参数
		boolean flag = false;
		TB tb = new TB();
		tb.getUser("jack");
//		if (clazz.equals(TB.class)) {
//			//((TB) clazz.).getUser("jack");
//			Method[] methods = clazz.getMethods();
//			String operationType = "";
//			String operationName = "";
//			for (Method method : methods) {
//				if (method.getName().equals(methodName)) {
//					Class[] clazzs = method.getParameterTypes();
//					if (clazzs.length == args.length) {
//						//获取类的默认构造器对象并通过它实例化Car 
//					       Constructor cons = clazz.getDeclaredConstructor((Class[])null); 
//					       tb = (TB)cons.newInstance();
//					       tb.getUser("jack");
//						//method.invoke(tb,"jack");
//						break;
//					}
//				}
//			}
//		}
		
		return "jjjjjj";
//		if (nameAndArgs != null && nameAndArgs.size() > 0) {
//			for (Map.Entry<String, Object> entry : nameAndArgs.entrySet()) {
//				if (entry.getValue() instanceof TB) {
//					((TB) entry.getValue()).getUser("jack");
//					//User u = ((TB) entry.getValue()).getUser("bbbb");
//					//System.out.println(u.getName());
//					break;// 跳出循环
//				}
////				if (entry.getValue() instanceof SolrClient) {
////					((SolrClient) entry.getValue()).setUrl("http://www.baidu.com");;
////					//User u = ((TB) entry.getValue()).getUser("bbbb");
////					//System.out.println(u.getName());
////					break;// 跳出循环
////				}
//			}
//		}
		//System.out.println("==========执行controller前置通知===============");
	}

	private Map<String, Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		ClassPool pool = ClassPool.getDefault();
		ClassClassPath classPath = new ClassClassPath(cls);
		pool.insertClassPath(classPath);

		CtClass cc = pool.get(clazzName);
		CtMethod cm = cc.getDeclaredMethod(methodName);
		MethodInfo methodInfo = cm.getMethodInfo();
		CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
		LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
		if (attr == null) {
			// exception
		}
		int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
		for (int i = 0; i < cm.getParameterTypes().length; i++) {
			map.put(attr.variableName(i + pos), args[i]);// paramNames即参数名
		}
		return map;
	}

}
