package com.common.memcached;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

public class MemcachedCache implements Cache {
	private final static Logger log = LoggerFactory.getLogger(MemcachedCache.class);
	private final String name;
	private final MemcachedClient memcachedClient;
	private final MemCache memCache;

	public MemcachedCache(String name, int expire, MemcachedClient memcachedClient) {
		this.name = name;
		this.memcachedClient = memcachedClient;
		this.memCache = new MemCache(name, expire, memcachedClient);
	}

	@Override
	public void clear() {
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
	public void evict(Object key) {
		memCache.delete(key.toString());
	}

	@Override
	public ValueWrapper get(Object key) {
		ValueWrapper wrapper = null;
		Object value = memCache.get(key.toString());
		if (value != null) {
			wrapper = new SimpleValueWrapper(value);
		}
		return wrapper;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public MemcachedClient getNativeCache() {
		return this.memcachedClient;
	}

	@Override
	public void put(Object key, Object value) {
		memCache.put(key.toString(), value);
	}

	@Override
	public <T> T get(Object key, Class<T> type) {
		Object result = null;
		result = this.memCache.get(getKey(key));
		if (result == null) {
			return null;
		}
		Assert.isAssignable(type, result.getClass());
		return type.cast(result);
	}

	@Override
	public <T> T get(Object key, Callable<T> arg1) {

		return null;
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		// TODO Auto-generated method stub
		ValueWrapper wrapper = get(key);
		if (wrapper != null) {
			put(key, value);
			return null;
		} else {
			return wrapper;
		}

	}

	private String getKey(Object key) {
		Assert.notNull(key, "key is requrie");
//		return getName() + "_" + key;
		return "" + key;
	}
}