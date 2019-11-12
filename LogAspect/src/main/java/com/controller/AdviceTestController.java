package com.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
@RequestMapping("/restview")
public class AdviceTestController {
	@GetMapping("/test")
    public String test(@ModelAttribute("msg") String msg,
                      @ModelAttribute("info") Map<String, String> info) {
        String result = "msg：" + msg + "<br>" + "info：" + info;
        return result;
    }
	
	@GetMapping("/advicedemo")
	public String advicedemo(@ModelAttribute("msg") String msg,
			@ModelAttribute("adviceinfo") Map<String, String> info) {
		String result = "msg：" + msg + "<br>" + "info-name：" + info.get("name") + "info-age:" + info.get("age");
		return result;
	}

}
