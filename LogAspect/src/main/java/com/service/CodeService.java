package com.service;

import org.springframework.stereotype.Service;

@Service
public class CodeService {

	public String sendMessage(String username, String forType, String userType) {
		// TODO Auto-generated method stub
		return ""+username+forType+userType;
	}

}
