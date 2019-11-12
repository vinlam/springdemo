package com.controller.advice;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/view")
public class AdviceVController {
	@GetMapping("/adviceshow")
    public String adviceshow() {
        return "show";
    }

}
