package com.common.memcached;

import java.util.Collection;  
import java.util.HashMap;  
import java.util.Map;  
import java.util.concurrent.ConcurrentHashMap;  
import java.util.concurrent.ConcurrentMap;  
  
import net.rubyeye.xmemcached.MemcachedClient;  
  
import org.springframework.cache.Cache;  
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;  
  
public class MemcachedCacheManager extends AbstractTransactionSupportingCacheManager {  
    private ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();  
    private Map<String, Integer> expireMap = new HashMap<String, Integer>();  
    private int defaultExpire = 1800;

	private MemcachedClient memcachedClient;  
  
    public MemcachedCacheManager(){  
    }  
  
    @Override  
    protected Collection<? extends Cache> loadCaches(){  
        Collection<Cache> values = cacheMap.values();  
        return values;  
    }  
  
    @Override  
    public Cache getCache(String name){  
        Cache cache = cacheMap.get(name);  
        if (cache == null){  
            Integer expire = expireMap.get(name);  
            if (expire == null){  
                expire = 0;  
                expireMap.put(name, expire);  
            }  
            
            if(expire <= 0){
            	expire = defaultExpire;
            }
            cache = new MemcachedCache(name, expire.intValue(), memcachedClient);  
            cacheMap.put(name, cache);  
        }  
        return cache;  
    }  
  
    public void setMemcachedClient(MemcachedClient memcachedClient){  
        this.memcachedClient = memcachedClient;  
    }  
  
    public void setConfigMap(Map<String, Integer> configMap){  
        this.expireMap = configMap;  
    }  
    
    public int getDefaultExpire() {
		return defaultExpire;
	}

	public void setDefaultExpire(int defaultExpire) {
		this.defaultExpire = defaultExpire;
	}
}  