package com;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.common.solr.SolrClient;
import com.config.BeanConfig;

public class Testmain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		@SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");// 读取bean.xml中的内容  
		@SuppressWarnings("resource")
		ApplicationContext annotationContext = new AnnotationConfigApplicationContext(BeanConfig.class);
		//SolrClient c = annotationContext.getBean("solrclient", SolrClient.class);
        SolrClient c = ctx.getBean("solrclient", SolrClient.class);
        c = annotationContext.getBean("solrclient", SolrClient.class);
        System.out.println(c.getUrl());
	}

}
