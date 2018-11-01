package com.dao;

import org.springframework.stereotype.Repository;

import com.entity.User;

@Repository
public class TC extends BaseDao<User, Integer>{
	public int getCount(int val){
		return val;
	}
	
	public User getUser(Integer id){
		User u = new User();
		u.setId(id);
		return u;
	}
}
