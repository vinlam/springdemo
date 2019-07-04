package com;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.common.component.StaticComponent;
import com.common.solr.SolrClient;
import com.config.BeanConfig;

public class Testmain {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//@SuppressWarnings("resource")
		//ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");// 读取bean.xml中的内容  
		@SuppressWarnings("resource")
		ApplicationContext annotationContext = new AnnotationConfigApplicationContext(BeanConfig.class);
		SolrClient c = annotationContext.getBean("solrclient", SolrClient.class);
        //SolrClient c = ctx.getBean("solrclient", SolrClient.class);
        c = annotationContext.getBean("solrclient", SolrClient.class);
        System.out.println(c.getUrl());
        
        AnnotationConfigApplicationContext ct = new AnnotationConfigApplicationContext(BeanConfig.class);
        SolrClient scB = ct.getBean("solrclient", SolrClient.class);
        System.out.println("annotation:"+scB.getUrl());
        ct.close(); 
        //System.out.println(StaticComponent.emailHost);
	}

}
