package com.common.component;

import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

//@EnableAspectJAutoProxy(exposeProxy = true)//来暴露AOP的Proxy对象才行，否则会报错。
@Component
public class NoAsyncTask {
	public void noAsyncTask(String keyword){
	    // 注意这里 调用了代理类的方法
	    //((NoAsyncTask) AopContext.currentProxy()).asyncTask(keyword);
	    asyncTask(keyword);
	}

	@Async
	private void asyncTask(String keyword) {
	    try {
	        Thread.sleep(5000);
	    } catch (InterruptedException e) {
	        //logger
	        //error tracking
	    }
	    System.out.println(keyword);
	}
}
