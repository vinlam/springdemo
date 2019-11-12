package com.service;

import java.util.List;

import com.entity.Product;
import com.service.impl.Message;

public interface ElasticService {

	Message addProduct(Product product);

	Message delProduct(String id);

	Message updateProduct(Product product);

	List<Product> getProduct(String id);

	List<Product> searchProduct(String fieldName, String name, Integer start, Integer count);

	List<Product> searchProduct(String fieldName, String keyword, int start, int count);

}
