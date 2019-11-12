package com.service.impl;

import java.util.Map;

public class Message {
	private String code;
	private Map<String,Object> data;
	public Message(String code, Map data) {
		// TODO Auto-generated constructor stub
		this.code = code;
		this.data = data;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}
