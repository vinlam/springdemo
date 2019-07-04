package com.core.security.protection.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.core.security.protection.service.Protective;

@Component
public class XSSProtect implements Protective {
	private static final  Logger logger = LoggerFactory.getLogger(XSSProtect.class);
	
	private static final String EVENTS = "(onload|onunload|onchange|onsubmin|onresset" + "|onselect|onblur|onfocus|onkeydown|onkeypress|onkeyup"
			+ "|onclick|ondbclick|onmousedown|onmousemove|onmouseout|onmouseover|onmouseup)" + "(|[A|a][L|l][E|e][R|r][T|t]\\s*\\(";
	private static final String XSS_HTML_TAG = "(%3C)|(%3E)|[<>]";
	private static final String XSS_INJECTION = "[\\\\]|[\\-]|[\']|[\"]|[\\|]|(\\s+|%20)|" + EVENTS + "|[\\=]";
	private static final String XSS_OTHER = "[`~!#$%^&()+=|{}':;\",\\[\\].<>/?~！@#￥%&（）——+|{}《》【】·‘；：“”’。，、？]";
	private static final String XSS_REG = XSS_HTML_TAG + "|" + XSS_INJECTION + "|" + XSS_OTHER;
	@Override
	public String protect(String raw) {
		if(raw == null) {
			return null;
		}
		logger.info("原始请求数据："+ raw);
		String res = raw.replace(XSS_REG, "");
		logger.info("过滤后数据："+ res);
		return res;
	}

}
