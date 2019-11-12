package com.common;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**

 */
@ControllerAdvice(basePackages = "com.controller.advice")
public class ControllerAdviceModelHandler {
	private final Logger logger = LoggerFactory.getLogger(ControllerAdviceModelHandler.class);

	@ModelAttribute(value = "msg")
	public String message() {
		return "欢迎访问 test.com";
	}

	@ModelAttribute(value = "adviceinfo")
	public Map<String, String> userinfo() {
		HashMap<String, String> map = new HashMap<>();
		map.put("name", "jack");
		map.put("age", "20");
		return map;
	}
	
	@ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("author", "jack");
        model.addAttribute("email", "jack@163.com");
        model.addAttribute("url", "www.jack.com");
        model.addAttribute("comment", "欢迎访问 www.jack.com");
    }
}