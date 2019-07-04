package com.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class XSSController {
	
	/**
	 * 获取留言并在页面展示
	 * */
	@RequestMapping("/show.html")
	public ModelAndView showComment(@RequestParam(name = "author", required = true) String author,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "url", required = false) String url,
			@RequestParam(name = "comment", required = false) String comment) {
		
		ModelAndView mAndView = new ModelAndView("show");
		mAndView.addObject("author", author);
		mAndView.addObject("email", email);
		mAndView.addObject("url", url);
		mAndView.addObject("comment", comment);
		
		return mAndView;
	}
	
	@RequestMapping("/xssinput.html")
	public ModelAndView input(@RequestParam(name = "author", required = true) String author,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "url", required = false) String url,
			@RequestParam(name = "comment", required = false) String comment) {
		
		ModelAndView mAndView = new ModelAndView("show");
		mAndView.addObject("author", author);
		mAndView.addObject("email", email);
		mAndView.addObject("url", url);
		mAndView.addObject("comment", comment);
		
		return mAndView;
	}
}
