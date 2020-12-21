package com.util;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {
	private IpUtil(){
		
	}
	
	public static String getIpAddr(HttpServletRequest request){
		if(request == null){
			return "unknown";
		}
		
		String ip = request.getHeader("x-forwarded-for");//HTTP代理或者负载均衡
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-IP");//apache http代理服务器
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("X-Forwarded-For");//HTTP代理或者负载均衡
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("HTTP_CLIENT_IP");//某些代理服务器
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");//nginx
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("WL-Proxy-Client-IP");//weblogic插件
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("X-Real-IP");//nginx代理服务器
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
	
	public static void main(String[] args) throws SocketException {
		Enumeration<NetworkInterface> enumerationInter = NetworkInterface.getNetworkInterfaces();
		while(enumerationInter.hasMoreElements()) {
			NetworkInterface networkInterface = enumerationInter.nextElement();
			Enumeration<InetAddress> address = networkInterface.getInetAddresses();
			while (address.hasMoreElements()) {
				InetAddress inetAddress = (InetAddress) address.nextElement();
				if(inetAddress instanceof Inet4Address) {
					System.out.println("ip v4:"+inetAddress.getHostAddress());
				}else if(inetAddress instanceof Inet6Address) {
					System.out.println("ip v6:"+inetAddress.getHostAddress());
				}
			}
		}
		//如遇到ip v6不出来，查看jdk配置参数是否有-Djava.net.preferIPv4Stack=true,openJDK源码中有 User can disable ipv6 expicitlt by -Djava.net.preferIPv4Stack=true
	}
}
