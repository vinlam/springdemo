package com.service;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.entity.TbItem;

public interface ItemSearchService {

	void add(TbItem tbItem);


	void deleteById(String id);

	Page<TbItem> pageQuery(int start, int size);

	void deleteAll();

	Map<String, Object> search(Map searchMap);

	TbItem searchById(long id);

}
