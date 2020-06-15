package com.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.entity.User;
import com.service.MemCacheTestService;
import com.util.JsonMapper;
import com.util.JsonUtil;

@Service("MemCacheTestService")
public class MemCacheTestServiceImpl implements MemCacheTestService {
	private final static Logger log = LoggerFactory.getLogger(MemCacheTestService.class);

	@Override
    @Cacheable(cacheNames="mC",key="#param")
    public String getTimestamp(String param) {
        Long timestamp = System.currentTimeMillis();
        return timestamp.toString();
    }
	@Override
	@Cacheable(cacheNames="mCapi",key="#param")
	public String gettime(String param) {
		Long timestamp = System.currentTimeMillis();
		return timestamp.toString();
	}
	
	@Override
    @Cacheable(cacheNames="mCache")
	//@Cacheable("mCache")
    public String mCache() {
        Long timestamp = System.currentTimeMillis();
        return "mCache:"+timestamp.toString();
    }
	
	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 999;
	}
	
	@Override
	//@CacheEvict(cacheNames="mC",allEntries=true)
	@CacheEvict(cacheNames="mC",beforeInvocation=true,allEntries=true)//beforeInvocation意为是否在执行对应方法之前删除缓存
	public void clearAll() {
		// TODO Auto-generated method stub
		System.out.println("clear all");
	}
	
	@Override
	@CachePut(cacheNames="mC",key="#param")
	public String updateStr(String param) {
		// TODO Auto-generated method stub
		return param+ " --- " + System.currentTimeMillis();
	}
	@Override
	@CacheEvict(cacheNames="mC",key="#param")
	public void deleteOne(String param) {
		// TODO Auto-generated method stub
		System.out.println("delete");
	}

	@Override
	@CachePut(cacheNames="mCache",key="")
	public String mCacheupdate() {
		// TODO Auto-generated method stub
		Long timestamp = System.currentTimeMillis();
        return "updatemCache:"+timestamp.toString();
	}

	@Override
	@CacheEvict(cacheNames="mCache")
	public void mCacheDel() {
		System.out.println("delete");
	}

	@Override
	@Cacheable(cacheNames="mCache",key="#u.id")
	public User cacheUser(User u) {
	    return u;
	}
	
	@Override
	@Cacheable(cacheNames="mCache",key="#u.id")
	public User cache(User u) {
	    return u;
	}
	
	@Override
	@Cacheable(cacheNames="mCache",key="#id")
	public User cacheJson(int id,String s) {
		User u = (User) JsonMapper.fromJsonString(s,User.class);
	    return u;
	}
	@Override
	@Cacheable(cacheNames="mCache",key="#id")
	public String cacheJsonStr(int id,String s) {
		//User u = (User) JsonMapper.fromJsonString(s,User.class);
		return s;
	}
	
	@Override
	@CachePut(cacheNames="mCache",key="#u.id")
	public User cacheUpdateUser(User u) {
		log.info(JsonUtil.beanToJson(u));
		return u;
	}
	
	@Override
	@Cacheable(cacheNames="mCache",key="#id")
	public User getcacheUser(int id) {
		return new User();
	}
	
	@Override
	@Cacheable(cacheNames="mCache",key="#key")
	public String cacheStr(String key,String value) {
		return value;
	}
	
	@Override
	@Cacheable(cacheNames="mCache",key="#key")
	public String getCacheStr(String key) {
		return "";
	}

	@Override
	@Caching(evict={@CacheEvict(value="mC"),@CacheEvict(value="mCache",allEntries = true)})
	public void delMutilKey() {
		// TODO Auto-generated method stub
		
	}
	@Override
	@Cacheable(cacheNames="myCache")
	public String cacheNoKey() {
		
		return String.valueOf(System.currentTimeMillis());
	}
	@Override
	@Cacheable(cacheNames="myCache",key="#key")
	public String cacheKey(String key) {
		
		return "myCache:"+key;
	}

}