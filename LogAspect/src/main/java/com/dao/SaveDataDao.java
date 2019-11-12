package com.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.controller.web.rest.RestApiTestController;
import com.entity.User;
import com.util.JsonMapper;

@Repository
public class SaveDataDao {
	static final Logger logger = LoggerFactory.getLogger(SaveDataDao.class);
	//conditio判断参数条件，unless 返回结果#result
	@Cacheable(cacheNames="userCache",key="#u.Id",condition="#u.name!=null&&#u.password!=null")
    public User userDataCache(User u) {
		logger.info("save:"+JsonMapper.toJsonString(u));
        return u;
    }
	
	@CachePut(cacheNames="userCache",key="#u.Id",condition="#u.name!=null&&#u.password!=null")
	public User userDataUpdateCache(User u) {
		logger.info("update:"+JsonMapper.toJsonString(u));
		return u;
	}
}
