package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
 
@Controller
public class LogController {
    private static Logger logger = LoggerFactory.getLogger(LogController.class);
 
    @RequestMapping("/log")
    @ResponseBody
    public String log(){
        logger.info("info");
        logger.debug("debug");
        logger.warn("warn");
		logger.error("error");
        return "log";
    }
}
