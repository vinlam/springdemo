package com.core.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.core.security.protection.ProtectionManager;

public class ProtectRequestWrapper extends HttpServletRequestWrapper{
	private ProtectionManager protectionManager; 
	
	public ProtectRequestWrapper(HttpServletRequest request,ProtectionManager protectionManager) {
		super(request);
		this.protectionManager = protectionManager;
	}
	
	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		return protectionManager.protect(value);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getParameterMap() {
		Map<String,String[]> rawMap = super.getParameterMap();
		Map<String,String[]> filteredMap = new HashMap<String,String[]>(rawMap.size());
		Set<String> keys = rawMap.keySet();
		for(String key:keys) {
			String[] rawValue = rawMap.get(key);
			String[] filteredValue = filterStringArray(rawValue);
			filteredMap.put(key, filteredValue);
		}
		return filteredMap;
	}
	
	protected String[] filterStringArray(String[] rawValue) {
		String[] filteredArray = new String[rawValue.length];
		int len = rawValue.length;
		for(int i = 0;i < len;i++) {
			filteredArray[i] = protectionManager.protect(rawValue[i]);
		}
		return filteredArray;
	}
	
	@Override
	public String[] getParameterValues(String name) {
		String[] rawValues = super.getParameterValues(name);
		if(rawValues == null) {
			return null;
		}
		String[] filteredArray = new String[rawValues.length];
		int len = rawValues.length;
		for(int i = 0;i < len;i++) {
			filteredArray[i] = protectionManager.protect(rawValues[i]);
		}
		return filteredArray;
	}

	@Override
	public String getQueryString() {
		return protectionManager.protect(super.getQueryString());
	}
}
