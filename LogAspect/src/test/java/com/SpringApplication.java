package com;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(JUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:*/applicationContext-test.xml" })
public class SpringApplication {

	static {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(lc);
		lc.reset();
		try {
			configurator.doConfigure("/Users/vinlam/work/gitproject/springdemo/LogAspect/src/test/java/logback.xml");// 加载logback配置文件
		} catch (JoranException e) {
			e.printStackTrace();
		}
		// PropertyConfigurator.configure("/Users/vinlam/work/gitproject/springdemo/LogAspect/src/test/java/log4j.properties");//加载logj配置文件
	}
	private static Logger log = LoggerFactory.getLogger(SpringApplication.class);

	@Test
	public void application() {
		log.info("---------> {} <---------");
	}

}
