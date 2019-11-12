//package com.service.impl;
//
//import java.io.IOException;
//import java.util.List;
//
//import com.common.elasticsearch.ElasticUtils;
//import com.entity.Product;
//import com.service.ElasticService;
//
//import ma.glasnost.orika.MapperFactory;
//import ma.glasnost.orika.impl.DefaultMapperFactory;
//
//public class ElasticServiceImpl implements ElasticService {
//	MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build(); 
//	 
//    @Override
//    public Message addProduct(Product product) {
//        try {
//            ElasticUtils.addDocument(product);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new Message("500",null);
//        }
//        return new Message("200",null);
//    }
// 
//    @Override
//    public Message delProduct(String id) {
//        Product product = new Product();
//        product.setId(Long.valueOf(id));
//        try {
//            ElasticUtils.deleteDocument(product);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new Message("500",null);
//        }
//        return new Message("200",null);
//    }
// 
//    @Override
//    public Product getProduct(String id)  {
//        try {
//            return ElasticUtils.getDocument("product",id);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
// 
//    @Override
//    public List<Product> searchProduct(String fieldName,String keyword,int start,int count) {
//        try {
//        	List<Procduct> list = 
//            return ;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
// 
//    @Override
//    public Message updateProduct(Product product) {
//        try {
//            ElasticUtils.updateDocument(product);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new Message("500",null);
//        }
//        return new Message("200",null);
//    }
//}