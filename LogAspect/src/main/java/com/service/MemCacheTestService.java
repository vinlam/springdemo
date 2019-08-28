package com.service;

import org.springframework.stereotype.Service;

@Service
public interface MemCacheTestService {
    String getTimestamp(String param);
    
    void clearAll();
    
    void deleteOne(String param);
    
    String updateStr(String param);
    
    int count();
    
    String mCache();
    String mCacheupdate();
    void mCacheDel();
}