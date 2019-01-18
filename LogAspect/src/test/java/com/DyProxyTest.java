package com;


import java.lang.reflect.Proxy;

import com.core.aop.handle.DynamicProxyHandler;
import com.service.IUser;
import com.service.ProxyInterface;
import com.service.impl.RealObject;
import com.service.impl.UserImp;

public class DyProxyTest {
    public static void main(String[] args) {
		RealObject realObject = new RealObject();
		//通过调用Proxy静态方法Proxy.newProxyInstance()可以创建动态代理，
        // 这个方法需要得到一个类加载器，一个你希望该代理实现的接口列表(不是类或抽象类)，以及InvocationHandler的一个实现类。
        ProxyInterface proxy=(ProxyInterface) Proxy.newProxyInstance(ProxyInterface.class.getClassLoader(),new Class[]{ProxyInterface.class},new DynamicProxyHandler(realObject));
        //ProxyInterface proxy=(ProxyInterface) Proxy.newProxyInstance(realObject.getClass().getClassLoader(),realObject.getClass().getInterfaces(),new DynamicProxyHandler(realObject));
        proxy.doSomething();
        proxy.somethingElse("test proxy");
        
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Class<?>[] clazz = realObject.getClass().getInterfaces();
        DynamicProxyHandler dp = new DynamicProxyHandler(realObject);
        ProxyInterface proxy2=(ProxyInterface) Proxy.newProxyInstance(loader,clazz,dp);
        proxy2.doSomething();
        proxy2.somethingElse("test");
        
        IUser realUser = new UserImp("sun");	
        DynamicProxyHandler hand = new DynamicProxyHandler(realUser); 
        IUser dproxy = (IUser) Proxy.newProxyInstance(realUser.getClass().getClassLoader(), realUser.getClass().getInterfaces(), hand); 
        System.out.println(dproxy.getName());
	}
}
