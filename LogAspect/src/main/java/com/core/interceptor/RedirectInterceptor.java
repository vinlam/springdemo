package com.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class RedirectInterceptor implements HandlerInterceptor{  
  
    @Override  
    public void afterCompletion(HttpServletRequest request,  
            HttpServletResponse response, Object obj, Exception err)  
            throws Exception {  
    }  
  
    @Override  
    public void postHandle(HttpServletRequest request, HttpServletResponse response,  
            Object obj, ModelAndView mav) throws Exception {  
          
    }  
  
    @Override  
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,  
            Object obj) throws Exception { 
    	response.sendRedirect("/LogAspect/view/testget");
        return true;  
    }  
}  
