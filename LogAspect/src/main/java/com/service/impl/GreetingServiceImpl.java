package com.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.service.GreetingService;
@Service
public class GreetingServiceImpl implements GreetingService {
	private final static Logger logger = LoggerFactory.getLogger(GreetingServiceImpl.class);
	@Override
	public void sayMessage(String message) {
		System.out.println("GreetingService.sayMessage " + message);
		logger.info("GreetingService.sayMessage " + message);
        this.sayHello();//调用同个service下的方法

	}

	@Override
	public void sayHello() {
		System.out.println("GreetingService.sayHello()");
		logger.info("GreetingService.sayHello");
	}

}
