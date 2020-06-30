package com.util;

import ch.qos.logback.classic.PatternLayout;

public class MyLogPatternLayout extends PatternLayout {
	static {  
        defaultConverterMap.put("HostName",LogConfig.class.getName());  //自定义模板
    }  
}
