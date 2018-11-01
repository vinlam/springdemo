package com.service;

import org.springframework.stereotype.Service;

@Service
public interface MemCacheTestService {
    String getTimestamp(String param);
    int count();
}