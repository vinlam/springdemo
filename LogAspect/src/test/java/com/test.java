package com;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import com.define.annotation.TestA;

/**
 */
public class test {

    @TestA(cacheNames={"abc","123"})
    public static void main(String[] args) throws Exception {
        Method method = test.class.getMethod("main", String[].class);
        TestA testA = method.getAnnotation(TestA.class);
        if (testA == null)
            throw new RuntimeException("please add testA");
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(testA);
        Field value = invocationHandler.getClass().getDeclaredField("memberValues");
        value.setAccessible(true);
        @SuppressWarnings("unchecked")
		Map<String, Object> memberValues = (Map<String, Object>) value.get(invocationHandler);
        String[] val = (String[]) memberValues.get("cacheNames");
        System.out.println("改变前：" + val[1]);
        val[1] = "bbb";
        memberValues.put("value", val);
        System.out.println("改变后：" + testA.value()[1]);
    }
}
