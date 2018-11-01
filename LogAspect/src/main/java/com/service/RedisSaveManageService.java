package com.service;

import java.util.List;

public interface RedisSaveManageService {

	/**
	 * 新增
	 * 
	 * @return
	 */
	boolean add(String key, String value);

	/**
	 * 批量新增 使用pipeline方式
	 * 
	 * @return
	 */
	boolean add(List<String> key, List<String> values);

	/**
	 * 删除
	 * 
	 * @param key
	 */
	void delete(String key);

	/**
	 * 删除多个
	 * 
	 * @param keys
	 */
	void delete(List<String> keys);

	/**
	 * 修改
	 * 
	 * @return
	 */
	boolean update(String key, String value);

	/**
	 * 通过key获取
	 * 
	 * @return
	 */
	String get(String key);
}
