package com.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.entity.SysLog;
@Repository
public class SysLogMapper {
	public int deleteByPrimaryKey(String id){
		System.out.println("delkey:"+id);
		return 0;
	}

	public int insertSelective(SysLog record) {
		// TODO Auto-generated method stub
		return 0;
	}

	public SysLog selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		SysLog sysLog = new SysLog();
		sysLog.setId("id:"+id +"-"+ System.currentTimeMillis());
		return sysLog;
	}

	public int updateByPrimaryKeySelective(SysLog record) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int insert(SysLog record) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int count() {
		// TODO Auto-generated method stub
		return 15;
	}
}
