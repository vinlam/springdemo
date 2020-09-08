package com;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class Slf4j_logbackTest {
	private static final Logger logger = LoggerFactory.getLogger(Slf4j_logbackTest.class);

	public static void main(String[] args) throws JoranException {
		String path = new File("").getAbsolutePath() + File.separator;
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(lc);
		lc.reset();
		try {
			configurator.doConfigure(Slf4j_logbackTest.class.getResource("/").getPath() + "logback.xml");
		} catch (JoranException e) {
			e.printStackTrace();
		}
		
		logger.info("logback");
	}
}
