package com.controller.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.service.MemCacheTestService;
import com.service.SysLogService;
import com.service.impl.MemCacheTestServiceImpl;

@RestController
@RequestMapping(value="/api")
public class FTestController {
	
	@Autowired
	private MemCacheTestServiceImpl memCacheTestServiceImpl; 
	@Autowired
	private SysLogService sysLogService; 
	
	@RequestMapping(value="/getcache",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getcache() throws InterruptedException{
		String  cache = memCacheTestServiceImpl.getTimestamp("param");
		//String cache = "2222";
//		Thread.sleep(1000);
//		System.out.println(cache);
//		Thread.sleep(11000);
//		System.out.println("11秒："+cache);
		return ResponseEntity.ok(cache);
	}
	
	@RequestMapping(value="/count",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> count(){
		int  cache = sysLogService.count();
		//String cache = "2222";
//		Thread.sleep(1000);
//		System.out.println(cache);
//		Thread.sleep(11000);
//		System.out.println("11秒："+cache);
		return ResponseEntity.ok(cache);
	}
	
	@RequestMapping(value="/t",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> t(){
		int cache = 2222;
//		Thread.sleep(1000);
//		System.out.println(cache);
//		Thread.sleep(11000);
//		System.out.println("11秒："+cache);
		return ResponseEntity.ok(cache);
	}
}
