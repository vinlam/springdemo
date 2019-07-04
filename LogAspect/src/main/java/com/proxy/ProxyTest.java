package com.proxy;

import com.entity.User;
import com.service.IUser;
import com.service.impl.UserImp;

/**
 * 代理模式[[ 客户端--》代理对象--》目标对象 ]]
 */
public class ProxyTest {
    public static void main(String[] args) {
        System.out.println("**********************CGLibProxy**********************");
        CGLibProxy cgLibProxy = new CGLibProxy();
        IUser userManager = (IUser) cgLibProxy.createProxyObject(new UserImp());
        User u= new User();
        u.setId(1);
        u.setName("jack");
        userManager.addUser(u);
 
        System.out.println("**********************JDKProxy**********************");
        JDKProxy jdkPrpxy = new JDKProxy();
        IUser userManagerJDK = (IUser) jdkPrpxy.newProxy(new UserImp());
        User u2= new User();
        u2.setId(2);
        u2.setName("tom");
        userManagerJDK.addUser(u2);
    }
}