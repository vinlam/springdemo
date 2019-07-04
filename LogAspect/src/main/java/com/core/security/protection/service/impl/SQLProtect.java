package com.core.security.protection.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.core.security.protection.service.Protective;
@Component
public class SQLProtect implements Protective{
	private static final  Logger logger = LoggerFactory.getLogger(SQLProtect.class);
	
	private static final String XSS_SQL_STR = "('.+--)|(--)|(%7C)";
	@Override
	public String protect(String raw) {
		if(raw == null) {
			return null;
		}
		logger.info("SQL原始请求数据："+ raw);
		String res = raw.replace(XSS_SQL_STR, "");
		logger.info("SQL过滤后数据："+ res);
		return res;
	}

}
