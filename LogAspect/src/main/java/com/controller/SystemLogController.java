package com.controller;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.entity.SysLog;
import com.service.EhCacheTestService;
import com.service.MemCacheTestService;
import com.service.SysLogService;

@Controller
@RequestMapping("/sc")
public class SystemLogController {
	private static Logger logger = LoggerFactory.getLogger(SystemLogController.class);
    @Autowired
    private SysLogService sysLogService;
    
    @Autowired
	private MemCacheTestService memCacheTestServiceImpl;
    
    @Autowired
	private EhCacheTestService ehCacheTestService;
    
    @RequestMapping("testLog")
    public ModelAndView testLog(){    
        ModelMap modelMap = new ModelMap();
        SysLog systemLog = sysLogService.selectSysLog("c30");
        modelMap.addAttribute("data", systemLog);
        logger.info(JSONObject.toJSONString(systemLog));
        return new ModelAndView("success",modelMap);
    }
    
    @RequestMapping("insert")
    @ResponseBody
    public boolean Insert(SysLog record){
        sysLogService.insert(record);
        return true;
    
    }
    
    @RequestMapping(value="gcache")
	@ResponseBody
	public String gcache(){
		return memCacheTestServiceImpl.getTimestamp("param");
	}
    
    @RequestMapping("gcount")
    @ResponseBody
    public String gcount() throws InterruptedException{
    	String val = "0";
    	val = sysLogService.count() +"++++++++++++++++"+memCacheTestServiceImpl.getTimestamp("param");
    	Thread.sleep(1000);
		System.out.println("after 1秒："+val);
		Thread.sleep(11000);
		System.out.println("after 11秒："+val);
    	return sysLogService.count() +"++++++++++++++++"+memCacheTestServiceImpl.getTimestamp("param");
    }
    
    
	
	
	 
	@RequestMapping(value="ecache")
	@ResponseBody
	private String ecache(){
		return ehCacheTestService.getTimestamp("param");
	}
	@RequestMapping(value="gg")
	@ResponseBody
	private int gg(){
		return memCacheTestServiceImpl.count();
	}
    
    @RequestMapping("test1")
    public ModelAndView test1(){
        ModelMap modelMap = new ModelMap();
        int num =sysLogService.count();
        modelMap.addAttribute("num", num);
        return  new ModelAndView("pageEhcache",modelMap);
    }
    
}