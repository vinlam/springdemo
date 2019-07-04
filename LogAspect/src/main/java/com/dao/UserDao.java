package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.entity.MBUser;

@Repository
public class UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public MBUser findOneById(String id) {
		// sql语句
        String sql = "select count(*)  from userinf_base";
        String sql2 = "select *  from userinf_base where cstno = ?";
        //Long num = (long) jdbcTemplate.queryForObject(sql, Long.class);
        //Class requiredType只支持基本数据类型的封装类，比如Integer.String等
//        所有在这里使用其他类型的时候需要将其转化一下,使用BeanPropertyRowMapper 
//        将上述代码转化成下列代码就可以正常查询了
        String cstno = "00000002002";
        cstno = id;
        MBUser user = (MBUser) jdbcTemplate.queryForObject(sql2,new Object[]{cstno}, new BeanPropertyRowMapper<MBUser>(MBUser.class));
		return user;
	}
	
	public MBUser addUser(MBUser mbUser) {
		
		//jdbcTemplate.
		return mbUser;
	}
}
