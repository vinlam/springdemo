package com.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(annotations = Controller.class)
public class ViewControllerAdvice {
	@ModelAttribute(value = "con")
	public Map<String, String> userinfo() {
		HashMap<String, String> map = new HashMap<>();
		map.put("data", "test advice controller");
		map.put("val", "100");
		return map;
	}
}
