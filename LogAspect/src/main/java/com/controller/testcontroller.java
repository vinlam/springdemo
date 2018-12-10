package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.dao.TB;
import com.dao.TC;
import com.entity.User;
import com.entity.UserDTO;
import com.service.SysLogService;
import com.service.UserService;
import com.service.impl.MemCacheTestServiceImpl;
import com.service.impl.a.AutoInject;
import com.service.impl.a.Inject;

@Controller
@RequestMapping("/t")
public class testcontroller {
	private static final Logger logger = LoggerFactory.getLogger(testcontroller.class);
	
	@RequestMapping(value="/testget",method=RequestMethod.GET)
	public String test(){
		System.out.println("test");
		return "ok";
	}
	
	@Autowired
	//@Qualifier("AutoInjectA")
	private AutoInject autoInjecta;
	@Autowired
	private Inject ijecta;
	@Autowired
	//@Qualifier("AutoInjectB")
	private com.service.impl.b.AutoInject autoInjectb;
	@RequestMapping(value="/b")
	@ResponseBody
	public String testb(){
		String a = autoInjecta.print();
		String b = autoInjectb.print();
		logger.info(a+"--------------"+b);
		return a+"--------------"+b+ijecta.print();
	}
	
	
	
	@Autowired
	private TB tb;
	@Autowired
	private TC tc;
	@RequestMapping(value="/dao")
	@ResponseBody
	public String testbase(){
		logger.info(tb.getVal("tb--string")+tc.getCount(999));
		
		
		return tb.getVal("tb--string")+tb.getUser("jack");
	}
	
	@RequestMapping(value="/testpost",method=RequestMethod.POST)
	public ModelAndView testPost(String txt){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("success");
		System.out.println("test:" + txt);
		
		return mv;
	}
	
	@RequestMapping(value="/testApi")
	public ModelAndView testApi(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("testpage");
		
		return mv;
	}
	
	@RequestMapping(value="/upload")
	public ModelAndView upload(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("upload");
		
		return mv;
	}
	
	@Autowired
	private MemCacheTestServiceImpl memCacheTestServiceImpl; 
	@Autowired
	private SysLogService sysLogService;
	@RequestMapping(value="/getcache")
	@ResponseBody
	public String getcache(){
		//String  cache = memCacheTestServiceImpl.getTimestamp("param");
		int count = sysLogService.count();
		//String cache = "2222";
//		Thread.sleep(1000);
//		System.out.println(cache);
//		Thread.sleep(11000);
//		System.out.println("11秒："+cache);
		return "aaaa"+count;
	}
	
	@Autowired
	private UserService userService;
	@RequestMapping(value="/gu")
	public ResponseEntity<User> getUser(String name){
		User u = userService.getUser("jack");
		return ResponseEntity.ok(u);
	}
	
	@RequestMapping(value="/getUser",method = RequestMethod.GET,produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    // @Logs(operationType="add操作:",operationName="添加用户")  
    //@Log(desc="test define annotation")  
	@ResponseBody
    public String getUser(UserDTO userDTO){        
    	System.out.println(JSONObject.toJSON(userDTO));
    	
    	return JSONObject.toJSON(userDTO).toString();
    }
	@RequestMapping(value="/getUser1",method = RequestMethod.GET,produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	// @Logs(operationType="add操作:",operationName="添加用户")  
	//@Log(desc="test define annotation")  
	@ResponseBody
	public String getUser1(UserDTO userDTO){        
		System.out.println(JSONObject.toJSON(userDTO));
		
		return JSONObject.toJSON(userDTO).toString();
	}
}
