package com.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

public class LogUtil {
	private static String machineId = "001";
	
	private static AtomicInteger count = new AtomicInteger(1000);
	static {
		try {
			String currentMachineIp = getInetAddress();
			if(currentMachineIp != null) {
				currentMachineIp = "000" + currentMachineIp.substring(currentMachineIp.lastIndexOf(".")+1);
				currentMachineIp = currentMachineIp.length() == 4 ? "0" + currentMachineIp:currentMachineIp;
				machineId = currentMachineIp.substring(currentMachineIp.length() - 2);
			}
		} catch (Throwable e) {
			// TODO: handle exception
		}
	}


	private static String getInetAddress() {
		Enumeration<NetworkInterface> enumeration;
		try {
			enumeration = NetworkInterface.getNetworkInterfaces();
			InetAddress address = null;
			while (enumeration.hasMoreElements()) {
				NetworkInterface networkInterface = (NetworkInterface) enumeration.nextElement();
				Enumeration<InetAddress> addr = networkInterface.getInetAddresses();
				while (addr.hasMoreElements()) {
					address = (InetAddress) addr.nextElement();
					
					if(!address.isLoopbackAddress() && address.getHostAddress().indexOf(":") == -1) {
						return address.getHostAddress();
					}
				}
			}
			return null;
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String traceId() {
		return traceId(machineId,System.currentTimeMillis(),getNextId());
	}
	public static String traceId(String machineId,long timestamp,int nextId) {
		StringBuilder builder = new StringBuilder();
		builder.append(machineId).append(timestamp).append(nextId);
		return builder.toString();
	}
	
	
	private static int getNextId() {
		for(;;) {
			int current = count.get();
			int next = (current > 9998) ? 1000:current+1;
			if(count.compareAndSet(current, next)) {
				return next;
			}
		}
	}
	
	public static String getTraceId() {
		return traceId();
	}
}
