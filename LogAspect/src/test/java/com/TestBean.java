package com;

import org.springframework.beans.factory.InitializingBean;  
import org.springframework.util.Assert;

/** 
 * 用做测试Bean，观察该Bean初始化过程中上面4个方法执行的先后顺序和内容 
 * 从BeanPostProcessor的作用，可以看出最先执行的是postProcessBeforeInitialization，
 * 然后是afterPropertiesSet，然后是init-method，然后是postProcessAfterInitialization 
 * 
 *  
 */ 
public class TestBean implements InitializingBean {  
    private String name;  
   
    public String getName() {  
        return name;  
    }  
   
    public void setName(String name) {  
        this.name = name;  
    }  
    
    public TestBean(){
    	
    }
    
    public TestBean(String name){
    	this.name = name;
    }
   
    public void init() {  
        System.out.println("init-method is called");  
        System.out.println("******************************");  
    }  
   
    @Override 
    public void afterPropertiesSet() throws Exception {  
        System.out.println("******************************");  
        System.out.println("afterPropertiesSet is called");  
        System.out.println("******************************"); 
        Assert.hasLength(name, "name must have a value");
    }  
    
    public static void main(String[] args) {
    	TestBean testBean = new TestBean("jack");
    	System.out.println(testBean.getName());
	}
}
