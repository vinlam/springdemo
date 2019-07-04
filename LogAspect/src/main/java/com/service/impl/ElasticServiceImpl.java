//package com.service.impl;
//
//import java.io.IOException;
//
//import com.common.elasticsearch.ElasticUtils;
//import com.entity.Product;
//import com.service.ElasticService;
//
//public class ElasticServiceImpl implements ElasticService {
//	 
//	 
//    @Override
//    public  addProduct(Product product) {
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
//        product.setId(id);
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
//    public Message getProduct(String id)  {
//        try {
//            return new Message("200",ElasticUtils.getDocument("product",id));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new Message("500",null);
//        }
//    }
// 
//    @Override
//    public Message searchProduct(String fieldName,String keyword,int start,int count) {
//        try {
//            return new Message("200",ElasticUtils.search("product",fieldName,keyword,start,count));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new Message("500",null);
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