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
public class RestApiTestController {
	
	@RequestMapping(value="/getdata",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public String getData() throws InterruptedException{
		//String cache = "2222";
//		Thread.sleep(1000);
//		System.out.println(cache);
//		Thread.sleep(11000);
//		System.out.println("11秒："+cache);
		return "ok";
	}
	
	@RequestMapping(value="/postdata",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public void addData(){
		//String cache = "2222";
//		Thread.sleep(1000);
//		System.out.println(cache);
//		Thread.sleep(11000);
//		System.out.println("11秒："+cache);
	}
	
	@RequestMapping(value="/updatedata",method=RequestMethod.PUT,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> updatedata(){
		int cache = 2222;
//		Thread.sleep(1000);
//		System.out.println(cache);
//		Thread.sleep(11000);
//		System.out.println("11秒："+cache);
		return ResponseEntity.ok(cache);
	}
	@RequestMapping(value="/deldata",method=RequestMethod.DELETE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> deleteData(){
		int cache = 2222;
//		Thread.sleep(1000);
//		System.out.println(cache);
//		Thread.sleep(11000);
//		System.out.println("11秒："+cache);
		return ResponseEntity.ok(cache);
	}
}
