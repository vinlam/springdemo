package com.service;

import java.util.List;

import com.entity.Product;

public interface ElasticService {

	void addProduct(Product product);

	void delProduct(String id);

	void updateProduct(Product product);

	List<Product> getProduct(String id);

	List<Product> searchProduct(String fieldName, String name, Integer start, Integer count);

	List<Product> searchProduct(String fieldName, String keyword, int start, int count);

}
