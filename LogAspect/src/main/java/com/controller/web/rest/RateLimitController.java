package com.controller.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.define.annotation.LxRateLimit;

@RestController
@RequestMapping(value="/ratelimit")
public class RateLimitController {
	@RequestMapping(value="/testlimit",method=RequestMethod.GET)
	@LxRateLimit(perSecond = 1.0, timeOut = 500)
	public String testAnnotation() {
	    return "get token success";
	}
	
	@RequestMapping(value="/test",method=RequestMethod.GET)
	@LxRateLimit(perSecond = 2.0, timeOut = 200)
	public String testA() {
		return "get limit token success";
	}
}
