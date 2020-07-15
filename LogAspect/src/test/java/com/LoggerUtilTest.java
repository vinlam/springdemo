package com;


import org.apache.log4j.Logger;
import org.junit.Test;

import com.util.LoggerUtil;
 
public class LoggerUtilTest {
	private static Logger logger = LoggerUtil.getLog(LoggerUtilTest.class);
	
	@Test
	public void add() {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			Thread.sleep(5000);
			logger.info("2 s later");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void view() {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
	@Test
	public void edit() {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
	@Test
	public void delete() {
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
}