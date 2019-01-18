package com.service.impl;

import com.service.IUser;

public class UserImp implements IUser{
	String name;
    public UserImp(String name) {
		// TODO Auto-generated constructor stub
    	this.name = name;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
}
