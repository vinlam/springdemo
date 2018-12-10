package com.common.memcached;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;  
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.springframework.cache.Cache;  
import org.springframework.cache.support.SimpleValueWrapper;  
  
public class MemcachedCache implements Cache{  
    private final String name;  
    private final MemcachedClient memcachedClient;  
    private final MemCache memCache;  
      
    public MemcachedCache(String name, int expire, MemcachedClient memcachedClient){  
        this.name = name;  
        this.memcachedClient = memcachedClient;   
        this.memCache = new MemCache(name, expire, memcachedClient);  
    }  
  
    @Override  
    public void clear(){  
        try {
			memCache.clear();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    }  
  
    @Override  
    public void evict(Object key){  
        memCache.delete(key.toString());  
    }  
  
    @Override  
    public ValueWrapper get(Object key){  
        ValueWrapper wrapper = null;  
        Object value = memCache.get(key.toString());  
        if (value != null){  
            wrapper = new SimpleValueWrapper(value);  
        }  
        return wrapper;  
    }  
  
    @Override  
    public String getName(){  
        return this.name;  
    }  
  
    @Override  
    public MemcachedClient getNativeCache(){  
        return this.memcachedClient;  
    }  
  
    @Override  
    public void put(Object key, Object value){  
        memCache.put(key.toString(), value);  
    }

	@Override
	public <T> T get(Object arg0, Class<T> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T get(Object arg0, Callable<T> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		// TODO Auto-generated method stub
		ValueWrapper wrapper = get(key);  
        if (wrapper != null){  
            put(key,value);
            return null;
        }else{
        	return wrapper;
        }
        
	}  
}  