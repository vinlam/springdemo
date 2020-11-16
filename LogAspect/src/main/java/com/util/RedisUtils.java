package com.util;

import java.time.zone.ZoneOffsetTransitionRule.TimeDefinition;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {
	private final static Logger log = LoggerFactory.getLogger(RedisUtils.class);
	@Autowired
	private static RedisTemplate<String, Object> redisTemplate;

	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}
	
	/**
	 * 缓存并设置时间
	 * @param key
	 * @param value
	 * @param time时间（秒）time > 0 ,如果time <=0则无限期
	 * @return
	 */
	public Boolean set(String key, Object value,long time) {
		
		try {
			if(time > 0) {
				redisTemplate.opsForValue().set(key, value,time,TimeUnit.SECONDS);
			}else {
				set(key,value);
			}
			return true;
		} catch (Exception e) {
			log.error("set err",e);
			return false;
		}
	}

	/**
	 * 普通缓存获取
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return key == null?null:redisTemplate.opsForValue().get(key);
	}
	
	/**
	 * 删除key
	 * @param key
	 */
	public Boolean del(String key) {
		if(StringUtils.isNotBlank(key)) {
			redisTemplate.delete(key);
			return true;
		}else {
			return false;
		}
	}

	public Object getList(String key) {
		return redisTemplate.opsForList().leftPop(key);
	}

	/**
	 * key是否存在
	 * 
	 * @return
	 */
	public boolean exists(final String key) {
		Boolean execute = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.exists(key.getBytes());
			}
		});
		return execute;
	}
	
	/**
	 * key是否存在
	 * 
	 * @return
	 */
	public boolean haskey(final String key) {
		try {
			return redisTemplate.hasKey(key);
		}catch (Exception e) {
			log.error("has key err",e);
			return false;
		}
	}

	/**
	 * value的长度
	 * 
	 * @param key
	 * @return
	 */
	public long llen(String key) {
		return redisTemplate.boundListOps(key).size();
	}

	/**
	 * lpop
	 * 
	 * @param key
	 * @return
	 */
	public Object lpop(String key) {
		return redisTemplate.boundListOps(key).leftPop();
	}

	/**
	 * rpop
	 * 
	 * @param key
	 * @return
	 */
	public Object rpop(String key) {
		return redisTemplate.boundListOps(key).rightPop();
	}

	/**
	 * lpush
	 * 
	 * @param key
	 * @return
	 */
	public void lpush(String key, String value) {
		redisTemplate.boundListOps(key).leftPush(value);
	}
}
