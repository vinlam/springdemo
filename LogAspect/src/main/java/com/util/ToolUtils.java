package com.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.service.impl.GreetingServiceImpl;

public class ToolUtils {
	private final static Logger logger = LoggerFactory.getLogger(ToolUtils.class);
	public ToolUtils() {
		// TODO Auto-generated constructor stub
	}
	/**java获取客户端*/
	public static String getPlatform(HttpServletRequest request){

	    /**User Agent中文名为用户代理，简称 UA，它是一个特殊字符串头，使得服务器
	    能够识别客户使用的操作系统及版本、CPU 类型、浏览器及版本、浏览器渲染引擎、浏览器语言、浏览器插件等*/  
	    String agent= request.getHeader("user-agent");
	    logger.info("agentinfo:"+agent);
	    //客户端类型常量
	    String type = "";
	    if(agent.contains("iPhone")||agent.contains("iPod")||agent.contains("iPad")){  
	        type = "ios";
	    } else if(agent.contains("Android") || agent.contains("Linux")) { 
	        type = "apk";
	    } else if(agent.indexOf("micromessenger") > 0){ 
	        type = "wx";
	    }else {
	        type = "pc";
	    }
	    return type;
	}
}
