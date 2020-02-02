package com.controller.advice;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class AdviceVController {
	@GetMapping("/adviceshow")
    public String adviceshow() {
        return "show";
    }
	
	@GetMapping("/conadviceshow")
	public String conadviceshow(@ModelAttribute(value = "con") Map<String, String> con){
		System.out.println(con.get("data"));
		return "show";
	}

}
