package com.controller.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Product;
import com.service.ElasticService;

@RestController
public class ElasticController {
	@Autowired
    private ElasticService elasticService;
 
    @RequestMapping(value = "product",method = RequestMethod.POST)
    @ResponseBody
    public void addProduct(
            @RequestParam(name = "id")String id,
            @RequestParam(name = "name")String name,
            @RequestParam(name = "price")String price,
            @RequestParam(name = "detail")String detail){
         elasticService.addProduct(new Product(id,name,price,detail));
    }
 
    @RequestMapping(value = "product",method = RequestMethod.DELETE)
    @ResponseBody
    public void delProduct(
            @RequestParam(name = "id")String id){
         elasticService.delProduct(id);
    }
 
    @RequestMapping(value = "product",method = RequestMethod.PUT)
    @ResponseBody
    public void updateProduct(
            @RequestParam(name = "id")String id,
            @RequestParam(name = "name")String name,
            @RequestParam(name = "price")String price,
            @RequestParam(name = "detail")String detail){
         elasticService.updateProduct(new Product(id,name,price,detail));
    }
 
    @RequestMapping(value = "product",method = RequestMethod.GET)
    @ResponseBody
    public List<Product> searchProduct(
            @RequestParam(name = "fieldName",required = false)String fieldName,
            @RequestParam(name = "name",required = false)String name,
            @RequestParam(name = "start",required = false)Integer start,
            @RequestParam(name = "count",required = false)Integer count,
            @RequestParam(name = "id",required = false)String id){
        if(id != null){
            return elasticService.getProduct(id);
        }else {
            return elasticService.searchProduct(fieldName,name,start,count);
        }
    }
 
}
