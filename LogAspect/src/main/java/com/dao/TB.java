package com.dao;

import org.springframework.stereotype.Repository;

import com.entity.User;

@Repository
public class TB extends BaseDao<User, String>{
	public String getVal(String val){
		return val;
	}
	
	public User getUser(String name){
		User u = new User();
		u.setName(name);
		return u;
	}
}
