package com.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.GreetingService;
import com.util.ToolUtils;
@Service
public class GreetingServiceImpl implements GreetingService {
	@Autowired
	HttpServletRequest httpServletRequest;
	private final static Logger logger = LoggerFactory.getLogger(GreetingServiceImpl.class);
	@Override
	public void sayMessage(String message) {
		System.out.println("GreetingService.sayMessage " + message);
		logger.info("GreetingService.sayMessage " + message);
		logger.info("agent:"+ToolUtils.getPlatform(httpServletRequest));
        this.sayHello();//调用同个service下的方法

	}

	@Override
	public void sayHello() {
		System.out.println("GreetingService.sayHello()");
		logger.info("GreetingService.sayHello");
	}

}
