package com.core.aop;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.service.impl.MemCacheTestServiceImpl;

@Aspect
@Component
public class SetterAop {  

    @Pointcut(value="execution(* com.service..*.(..)) && args(param)", argNames = "param")  
    public void beforePointcut(String param) {}


    @Before(value = "beforePointcut(param)", argNames = "param")  
    public void beforeAdvice(String param) {  
        System.out.println("===========before advice param:" + param);  
    }

    @After(value="beforePointcut(param)", argNames="param")  
    public void after(JoinPoint jp, Object param) {  
        //获得代理对象
        Object proxy = jp.getThis();
        //获得目标对象  
        Object target = jp.getTarget();  

        if(AopUtils.isAopProxy(proxy)) {//只有代理对象才需要处理  

            try {  
                Class<?> proxyClass = proxy.getClass();  
                Class<?> targetClass = target.getClass();  
                String methodName = jp.getSignature().getName();  

                Method m = BeanUtils.findDeclaredMethod(proxyClass, methodName, new Class[]{param.getClass()});  
                //找到方法里面使用的类中的属性
                PropertyDescriptor descriptor = BeanUtils.findPropertyForMethod(m);  
                String propName = descriptor.getName();  

                Field f = targetClass.getDeclaredField(propName);  
                if(f != null) {  
                    f.setAccessible(true);  
                    f.set(target, "test");  
                }
            } catch (Exception e) {  
                e.printStackTrace();//记录好异常进行处理  
            }  
        }  
    } 
    public static void main(String[] args) throws Exception {
    	MemCacheTestServiceImpl mc = new MemCacheTestServiceImpl();
        Field f = mc.getClass().getDeclaredField("deleteOne");  
        f.setAccessible(true);  
        f.set(mc, "1");
        System.out.println(f.getName());
    }
}  