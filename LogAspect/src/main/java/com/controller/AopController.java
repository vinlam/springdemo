package com.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.define.annotation.AccessLimit;
import com.service.MemCacheTestService;

@Controller
@RequestMapping("/activity")
public class AopController {
	
	@Autowired
	private MemCacheTestService memCacheTestService;
	
    @ResponseBody
    @RequestMapping("/sk")
    @AccessLimit(limit = 4,sec = 10)  //加上自定义注解即可
    public String test (){
        //TODO somethings……
        return   "hello world !"+memCacheTestService.getTimestamp("mt");
    }
}
