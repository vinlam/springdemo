package com.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.service.MemCacheTestService;

@Service("MemCacheTestService")
public class MemCacheTestServiceImpl implements MemCacheTestService {

	@Override
    @Cacheable(cacheNames="mC",key="#param")
    public String getTimestamp(String param) {
        Long timestamp = System.currentTimeMillis();
        return timestamp.toString();
    }
	
	@Override
    @Cacheable(cacheNames="mCache",key="")
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
	@CacheEvict(cacheNames="mC",beforeInvocation=true,allEntries=true)
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

}