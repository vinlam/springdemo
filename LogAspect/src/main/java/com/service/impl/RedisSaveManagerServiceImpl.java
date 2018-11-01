package com.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import com.dao.AbstractBaseRedisDao;
import com.service.RedisSaveManageService;

@Service
public class RedisSaveManagerServiceImpl<K, V> extends AbstractBaseRedisDao<String, String> implements RedisSaveManageService {

	@Autowired
	private RedisTemplate<K, V> redisTemplate;
	/**
	 * 新增
	 * 
	 * @return
	 */
	@Override
	public boolean add(final String key, final String value) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO Auto-generated method stub
				RedisSerializer<String> serializer = getRedisSerializer();
				// 序列化
				byte[] key2 = serializer.serialize(key);
				byte[] value2 = serializer.serialize(value);
				return connection.setNX(key2, value2);
			}
		});
		return result;
	}

	/**
	 * 批量新增 使用pipeline方式
	 * 
	 * @return
	 */
	@Override
	public boolean add(final List<String> key, final List<String> values) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				for (int i = 0; i < values.size(); i++) {
					byte[] key2 = serializer.serialize(key.get(i));
					byte[] value2 = serializer.serialize(values.get(i));
					connection.setNX(key2, value2);
				}
				return true;
			}
		}, false, true);
		return result;
	}

	@Override
	public void delete(String key) {
		List<String> list = new ArrayList<>();
		list.add(key);
		delete(list);
	}

	@Override
	public void delete(final List<String> keys) {
		redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO Auto-generated method stub
				RedisSerializer<String> serializer = getRedisSerializer();
				// 序列化
				for (String k : keys) {
					byte[] key = serializer.serialize(k);
					connection.del(key);
				}
				return true;
			}
		});

	}

	@Override
	public boolean update(final String key, final String value) {
		// TODO Auto-generated method stub
		if (get(key) == null) {
			throw new NullPointerException("数据行不存在, key = " + key);
		}
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key2 = serializer.serialize(key);
				byte[] value2 = serializer.serialize(value);
				connection.set(key2, value2);
				return true;
			}
		});
		return result;
	}

	@Override
	public String get(final String key) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key2 = serializer.serialize(key);
				byte[] value = connection.get(key2);
				if (value == null) {
					return null;
				}
				String value2 = serializer.deserialize(value);
				return value2;
			}
		});
		return result;
	}

}