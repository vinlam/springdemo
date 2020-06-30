package com.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class LogConfig  extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
    	String hostName = "";
    	try{
    		hostName = InetAddress.getLocalHost().getHostName();
		}catch(UnknownHostException e){
			
		}
		return hostName;
    }
    
}
