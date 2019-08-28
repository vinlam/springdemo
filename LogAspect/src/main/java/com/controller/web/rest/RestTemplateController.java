package com.controller.web.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.common.gateway.RestClient;
import com.entity.User;
import com.entity.UserDTO;
import com.util.JsonUtil;

@RestController
@RequestMapping("/api")
public class RestTemplateController {


    //@Autowired
    //private RestClient restClient;

    /***********HTTP GET method*************/
    @GetMapping("/testGetAction")
    public Object getJson(@RequestParam String actionName) {
        JSONObject json = new JSONObject();
        json.put("username", "tester");
        json.put("pwd", "123456748");
        String name = "";
		try {
			actionName = new String(actionName.getBytes("ISO8859-1"),"utf-8");
			name = URLDecoder.decode(actionName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        json.put("actionname", name);
        return json;
    }

    @GetMapping("/getApi")
    public String testGet() {
        String url = "http://localhost:8080/LogAspect/api/product/getList";
        JSONObject json = RestClient.getClient().getForEntity(url, JSONObject.class).getBody();
        return json.toJSONString();
    }

    /**********HTTP POST method**************/
    @PostMapping(value = "/testPostAction")
    public Object postJson(@RequestBody JSONObject param) {
        System.out.println(param.toJSONString());
        param.put("action", "post");
        param.put("username", "tester");
        param.put("pwd", "123456748");
        return param;
    }

    @PostMapping(value = "/postApi")
    public Object testPost() {
        String url = "http://localhost:8080/LogAspect/api/testPostAction";
        UserDTO userDTO  = new UserDTO();
       // userDTO.setIds(ids);
        JSONObject postData = new JSONObject();
        postData.put("descp", "request for post");
        JSONObject json = RestClient.getClient().postForEntity(url, postData, JSONObject.class).getBody();
        return json.toJSONString();
    }
    
    @PostMapping(value = "/restPostAction")
    public User postJsonRest(@RequestBody User user) {
    	User u = new User();
    	u.setId(user.getId());
    	u.setName(user.getName()+"-boy");
    	u.setPassword(user.getPassword()+"123");
    	return u;
    }
    
    @GetMapping(value = "/restpostApi")
    public Object testPostRest() {
    	String url = "http://localhost:8080/LogAspect/api/restPostAction";
    	UserDTO userDTO  = new UserDTO();
    	User u = new User();
    	u.setId(123);
    	u.setName("jack");
    	u.setPassword("pwd");
    	// userDTO.setIds(ids);
    	JSONObject postData = new JSONObject();
    	postData.put("descp", "request for post");
    	User user = RestClient.getClient().postForEntity(url, u, User.class).getBody();
    	return JsonUtil.beanToJson(user);
    }
}