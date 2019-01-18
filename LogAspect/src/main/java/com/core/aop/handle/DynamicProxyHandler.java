package com.core.aop.handle;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class DynamicProxyHandler implements InvocationHandler{
	private Object targetproxyed;
	
	public DynamicProxyHandler(Object proxyed) {
		// TODO Auto-generated constructor stub
		this.setProxyed(proxyed);
		//this.targetproxyed = proxyed;
	}

	@Override
	public Object invoke(Object proxyed, Method method, Object[] args) throws Throwable {
		System.out.println("----动态代理----");
//		beforeMethod(method);//在方法执行前所要执行的业务逻辑
//		long starttime=System.currentTimeMillis();
//		method.invoke(proxyed, args);//自己代理自己，是死循环了
//		long result=System.currentTimeMillis()-starttime;
//		System.out.println("执行时间为："+result+"毫秒");
//		afterMethod(method);//在方法执行后所要执行的业务逻辑
		try {
			if(args != null) {
				for(Object o :args) {
				    System.out.println("arg:"+ o);
			    }
			}
			System.out.println("proxy:"+ proxyed.getClass().getName());
			beforeMethod(method);//在方法执行前所要执行的业务逻辑
			long starttime=System.currentTimeMillis();
			method.invoke(targetproxyed, args);
			long result=System.currentTimeMillis()-starttime;
			System.out.println("执行时间为："+result+"毫秒");
			afterMethod(method);//在方法执行后所要执行的业务逻辑
			return method.invoke(targetproxyed,args);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e.getCause();
		}
		//return null;
	}
    
	public void beforeMethod(Method m){
		System.out.println(m.getName()+"执行before....");
	}
	
	public void afterMethod(Method m){
		System.out.println(m.getName()+"执行after...");
	}

	public Object getProxyed() {
		return targetproxyed;
	}

	public void setProxyed(Object proxyed) {
		this.targetproxyed = proxyed;
	}
}
