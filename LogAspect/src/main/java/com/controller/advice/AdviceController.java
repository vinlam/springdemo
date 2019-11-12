package com.controller.advice;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
@RequestMapping("/view")
public class AdviceController {
	@GetMapping("/hello")
    public String hello(@ModelAttribute("msg") String msg,
                      @ModelAttribute("info") Map<String, String> info) {
        String result = "msg：" + msg + "<br>" + "info：" + info;
        return result;
    }
	
	@GetMapping("/advice")
	public String advice(@ModelAttribute("msg") String msg,
			@ModelAttribute("adviceinfo") Map<String, String> info) {
		String result = "msg：" + msg + "<br>" + "info-name：" + info.get("name") + "info-age:" + info.get("age");
		return result;
	}

}
