package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.TB;
import com.entity.User;
import com.util.JsonUtil;

@Service
public class UserService {
	public String addUser(String userName, String password) {
		// TODO Auto-generated method stub
		User u = new User();
		u.setName(userName);
		return JsonUtil.beanToJson(u);
	}
	
	@Autowired
	private TB tb;
	
	public User getUser(String name){
		User u = tb.getUser(name);
		
		return u;
	}
}
