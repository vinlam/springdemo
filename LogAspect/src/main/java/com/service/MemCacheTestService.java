package com.service;

import org.springframework.stereotype.Service;

import com.entity.User;

@Service
public interface MemCacheTestService {
    String getTimestamp(String param);
    
    void clearAll();
    
    void deleteOne(String param);
    
    String updateStr(String param);
    
    int count();
    
    User cacheUser(User u);
    
    String mCache();
    String mCacheupdate();
    void mCacheDel();

	User getcacheUser(int id);

	User cacheUpdateUser(User u);

	String getCacheStr(String key);

	String cacheStr(String key, String value);

	User cache(User u);

	User cacheJson(int id,String s);

	String cacheJsonStr(int id, String s);
	
	void delMutilKey();

	String gettime(String param);
}