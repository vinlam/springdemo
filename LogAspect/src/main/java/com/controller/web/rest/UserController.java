package com.controller.web.rest;

import java.util.List;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.define.annotation.Log;
import com.define.annotation.Logs;
import com.entity.TestDTO;
import com.entity.UserDTO;
import com.service.UserService;

@RestController
@RequestMapping("/userController")
public class UserController {

    @Autowired
    private UserService userService;
    
    @RequestMapping(value="/testAOP",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @Logs(operationType="add操作:",operationName="添加用户")  
    @Log(desc="test define annotation")  
    public String testAOP(String userName,String password){        
        String res = userService.addUser(userName, password);
        
        return res;
    }
    
    @RequestMapping(value="/getPostUser",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
   // @Logs(operationType="add操作:",operationName="添加用户")  
    //@Log(desc="test define annotation")  
    public ResponseEntity<UserDTO> getPostUser(@RequestBody UserDTO userDTO){        
    	System.out.println(JSONObject.toJSON(userDTO));
    	
    	return ResponseEntity.ok(userDTO);
    }
   
    @RequestMapping(value="/getUser",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    // @Logs(operationType="add操作:",operationName="添加用户")  
    //@Log(desc="test define annotation")  
    public ResponseEntity<TestDTO> getUser( TestDTO tDTO){        
    	System.out.println(JSONObject.toJSON(tDTO));
    	
    	return ResponseEntity.ok(tDTO);
    }
    
    @RequestMapping(value="/getIds",method = RequestMethod.GET)
    // @Logs(operationType="add操作:",operationName="添加用户")  
    //@Log(desc="test define annotation")  
    public ResponseEntity<List<Integer>> getIds(@RequestParam(value = "ids[]")List <Integer> ids){        
    //public ResponseEntity<List<Integer>> getIds(List <Integer> ids){        
    	System.out.println(JSONObject.toJSON(ids));
    	
    	return ResponseEntity.ok(ids);
    }
    @RequestMapping(value="/gids",method = RequestMethod.GET)
    // @Logs(operationType="add操作:",operationName="添加用户")  
    //@Log(desc="test define annotation")  
    public ResponseEntity<String[]> gids(@RequestParam(value = "ids[]")String[] ids){        
    	//public ResponseEntity<List<Integer>> getIds(List <Integer> ids){        
    	System.out.println(JSONObject.toJSON(ids));
    	
    	return ResponseEntity.ok(ids);
    }
    
    @RequestMapping(value="/gds",method = RequestMethod.GET)
    // @Logs(operationType="add操作:",operationName="添加用户")  
    //@Log(desc="test define annotation")  
    public ResponseEntity<List <Long>> gds(@RequestParam List <Long> ids){        
    	//public ResponseEntity<List<Integer>> getIds(List <Integer> ids){        
    	System.out.println(JSONObject.toJSON(ids));
    	
    	return ResponseEntity.ok(ids);
    }
    @RequestMapping(value="/delids",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    // @Logs(operationType="add操作:",operationName="添加用户")  
    //@Log(desc="test define annotation")  
    public ResponseEntity<List <Long>> delids(@RequestBody List <Long> ids){        
    	//public ResponseEntity<List<Integer>> getIds(List <Integer> ids){        
    	System.out.println(JSONObject.toJSON(ids));
    	
    	return ResponseEntity.ok(ids);
    }
}