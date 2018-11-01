package com.core.interceptor;

import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.define.annotation.Log;
import com.define.annotation.Logs;
import com.util.IpUtil;

public class LogInterceptor extends HandlerInterceptorAdapter {

	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");
	private final static String REQUEST_ID = "requestId";
    private static final Logger LOGGER = LoggerFactory.getLogger(LogInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		long beginTime = System.currentTimeMillis();
		startTimeThreadLocal.set(beginTime);
		String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        String remoteIp = request.getRemoteAddr();
        String uuid = UUID.randomUUID().toString();
        LOGGER.info("put requestId ({}) to logger", uuid);
        LOGGER.info("request id:{}, client ip:{}, X-Forwarded-For:{}", uuid, remoteIp, xForwardedForHeader);
        MDC.put(REQUEST_ID, uuid);
        String ip = IpUtil.getIpAddr(request);
        MDC.put("Ip", ip);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
		
		String uuid = MDC.get(REQUEST_ID);
        LOGGER.info("remove requestId ({}) from logger", uuid);
        MDC.remove(REQUEST_ID);
	}

	@Override
    public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex) throws Exception {
    	//System.out.println("hander = "+handler);
        if (!(handler instanceof HandlerMethod)) {
            return;
        }
    	HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Log log = null;
        if(method.getAnnotation(Logs.class) != null){
        	log = method.getAnnotation(Log.class);
        	System.out.println("输出日志描述"+log.desc());	
		}
        
        if(log != null){
        	String desc = log.desc();
        	boolean view = log.view();
        	String opeDesc = log.operationDesc();
        	if(view){
        		long endTime=System.currentTimeMillis();
                long beginTime=startTimeThreadLocal.get();
                long consumeTime=endTime - beginTime;
                System.out.println("desc = " + desc + " : view = " + view + " : opeDesc = "+opeDesc+" : beginTime = "+beginTime + " : endTime = "+endTime + " : consumeTime = "+consumeTime);
        	}
        }
        MDC.clear();
         
    }
}