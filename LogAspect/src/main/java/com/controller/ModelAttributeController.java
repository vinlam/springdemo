package com.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.entity.PojoTest;

@Controller
@RequestMapping(value="model")
public class ModelAttributeController {
	
	@RequestMapping(value="modelTest")
    @ModelAttribute(value="pojo")
    public String modelTest()
    {
        System.out.println("进入modelTest方法");

        return "views/modelTest";
    }
	
	@RequestMapping(value="modelpojo")
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
	
	@RequestMapping(value="view/modeattr")
	public ModelAndView modeattr(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("modeattr");
		return mv;
	}
	
	@RequestMapping(value="view/modepojo")
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

    @RequestMapping(value="befor")
    public String befor(){

        System.out.println("进入befor方法");
        return "index";

    }

    @RequestMapping(value="modelTest1")
    public String modelTest1()
    {
        System.out.println("进入modelTest1方法");
        return "modelTest";
    }
}
