package com.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.entity.PojoTest;

@Controller
@RequestMapping(value="/model")
public class ModelAttributeController {
	
	@RequestMapping(value="/test")
    //@ModelAttribute(value="p")
    public String modelTest()
    {
        System.out.println("进入modelTest方法");

        return "modelTest";
    }
	
	@RequestMapping(value="/modelpojo")
    public String modelpojo(@ModelAttribute("pojo") PojoTest pojo) 
    {
        try {
            pojo.setUserName(new String(pojo.getUserName().getBytes("iso-8859-1"),"utf-8"));
            pojo.setSex(new String(pojo.getSex().getBytes("iso-8859-1"),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(pojo);
        return "modelTest";
    }
	
	@RequestMapping(value="/attr/{name}")
	public ModelAndView modeattr(@PathVariable String name){
		ModelAndView mv = new ModelAndView();
		mv.addObject("name", name);
		mv.setViewName("modeattr");
		return mv;
	}
	
	@RequestMapping(value="/pojo")
	public ModelAndView modepojo(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("modepojo");
		return mv;
	}
	
	@ModelAttribute
    public String init(Model mode)
    {
        System.out.println("进入init方法");
        PojoTest pojo=new PojoTest("小明", "男");
        mode.addAttribute("pojo", pojo);
        return "model/befor";
    }

    @RequestMapping(value="/befor")
    public String befor(){

        System.out.println("进入befor方法");
        return "index";

    }

    @RequestMapping(value="/modelTest1")
    public String modelTest1()
    {
        System.out.println("进入modelTest1方法");
        return "modelTest";
    }
    
    @ModelAttribute
    public void init()
    {
        System.out.println("最先执行的方法");
    }

    @ModelAttribute
    public void init02()
    {
        System.out.println("最先执行的方法02");
    }

    @RequestMapping(value="/tmodel")
    public String test()
    {
        System.out.println("然后执行的方法");
        return "modelTest";
    }

    @ModelAttribute
    public void init03()
    {
        System.out.println("最先执行的方法03");
    }
}
