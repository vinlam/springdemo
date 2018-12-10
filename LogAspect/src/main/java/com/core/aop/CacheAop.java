package com.core.aop;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cache.annotation.CacheEvict;

import com.service.impl.MemCacheTestServiceImpl;

public class CacheAop {
	@Pointcut("@annotation(org.springframework.cache.annotation.CacheEvict)")  
	public void cacheEvict() {  
	}  

	@Before("cacheEvict()")  
	public void handleCacheable(JoinPoint joinPoint) throws Throwable {  
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		// 获取目标方法
		Method method = methodSignature.getMethod();
		method.getAnnotation(CacheEvict.class).cacheNames();
		
		
		

	    //saveCacheName(method.getDeclaredAnnotation(Cacheable.class).cacheNames());  
	}  

	//static void saveCacheName(String[] cacheNames) {  
//	    Flux.fromArray(cacheNames)  
//	            .filter(StringUtils::hasText)  
//	            .doOnError(e -> log.warn("failed to saveCacheName: " + e.getMessage()))  
//	            .subscribe(cacheName -> {  
//	                HashOperations<String, String, String> opsForHash = SpringContextHelper.started().getBean(StringRedisTemplate.class).opsForHash();  
//	                opsForHash.putIfAbsent("__cacheName:" + value(SpringContextHelper.started().getApplicationName(), "unknown"), cacheName, "{}");  
//	            });  
	//}  

//	@Override  
//	public int getOrder() {  
//	    return -999;  
//	}  
	
	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		MemCacheTestServiceImpl mc = new MemCacheTestServiceImpl();
        //获取Bar的val字段
        //Field field = MemCacheTestServiceImpl.class.getDeclaredField("deleteOne");
        
        Field[] fields = MemCacheTestServiceImpl.class.getDeclaredFields();
        for (Field f : fields) {
            //没加autowired的不需要注值
            System.out.println(f.getName());
        }
        //获取val字段上的Foo注解实例
//        CacheEvict ce = field.getAnnotation(CacheEvict.class);
//        //获取 foo 这个代理实例所持有的 InvocationHandler
//        InvocationHandler h = Proxy.getInvocationHandler(ce);
//        // 获取 AnnotationInvocationHandler 的 memberValues 字段
//        Field hField = h.getClass().getDeclaredField("memberValues");
//        // 因为这个字段事 private final 修饰，所以要打开权限
//        hField.setAccessible(true);
//        // 获取 memberValues
//        Map memberValues = (Map) hField.get(h);
//        // 修改 value 属性值
//        memberValues.put("cacheNames", "ddd");
//        // 获取 foo 的 value 属性值
//        String[] value = ce.value();
//        System.out.println(value.toString()); // ddd
	}
}
