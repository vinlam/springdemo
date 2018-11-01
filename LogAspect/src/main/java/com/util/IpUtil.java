package com.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {
	private IpUtil(){
		
	}
	
	public static String getIpAddr(HttpServletRequest request){
		if(request == null){
			return "unknown";
		}
		
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("X-Forwarded-For");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("X-Real-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getRemoteAddr();
			if(ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")){
            	//根据网卡取本机配置的 IP
            	InetAddress inet=null;
            	try {
            		inet = InetAddress.getLocalHost();
            	} catch (UnknownHostException e) {
            		// e.printStackTrace();
            	}
            	ip= inet.getHostAddress();
            }
        }
        
        //存在多级反向代理的情况下，从x-forwarded-for获取的值为逗号分隔的ip串
        if(ip.indexOf(",") > 0){
        	ip = ip.substring(0, ip.indexOf(",")).trim();
        }
		
		return ip;
	}
	
	public static String getLocalHostAddr(){
		try{
			return InetAddress.getLocalHost().getHostAddress();
		}catch(UnknownHostException e){
			
		}
		return "127.0.0.1";
	}
	public static String getLocalHostName(){
		try{
			return InetAddress.getLocalHost().getHostName();
		}catch(UnknownHostException e){
			
		}
		return "";
	}
}
