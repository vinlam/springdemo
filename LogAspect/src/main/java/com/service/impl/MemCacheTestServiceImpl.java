package com.service.impl;

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
	public int count() {
		// TODO Auto-generated method stub
		return 999;
	}

}