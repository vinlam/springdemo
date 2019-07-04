package com.service.impl;

import com.entity.User;
import com.service.IUser;

public class UserImp implements IUser{
	String name;
    public UserImp(String name) {
		// TODO Auto-generated constructor stub
    	this.name = name;
	}
    
    public UserImp() {
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void addUser(User u) {
		// TODO Auto-generated method stub

        System.out.println("======调用了UserImp.addUser()方法======");
		
	}
}
