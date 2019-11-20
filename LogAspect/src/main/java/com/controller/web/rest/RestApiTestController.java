package com.controller.web.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.entity.User;
import com.service.GreetingService;
import com.service.IAutoInject;
import com.service.SaveDataService;
import com.util.JsonMapper;

@RestController
@RequestMapping(value="/api")
public class RestApiTestController {
	private static final Logger logger = LoggerFactory.getLogger(RestApiTestController.class);

	@Autowired
	@Qualifier("AutoInjectB")
	private IAutoInject autoInjectb;

	@RequestMapping(value = "/inject",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	//@ResponseBody
	public String testb() {
		String b = autoInjectb.print();
		logger.info("--------------" + b);
		return "--------------" + b;
	}
	@Autowired
	private SaveDataService saveDataService;
	
	@RequestMapping(value = "/saveandreturnurl",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	//@ResponseBody
	public String saveData(@RequestBody User u) {
		saveDataService.saveUser(u);
		Map<String,String> map = new HashMap<>();
		map.put("url", "/t/testget?name=xt");
		return JsonMapper.toJsonString(map);
	}
	
	
	
	@RequestMapping(value = "/getSaveData/{uid}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	//@ResponseBody
	public User getSaveData(@PathVariable(required = true) int uid) {
		User u = new User();
		u.setId(uid);
		User r = saveDataService.getUser(u);
		logger.info("result:" + JsonMapper.toJsonString(r));
		if(r.getName() != null) {
			return r;
		}else {
			return null;
		}
	}
	
	@RequestMapping(value="/getdata",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public String getData() throws InterruptedException{
		//String cache = "2222";
//		Thread.sleep(1000);
//		System.out.println(cache);
//		Thread.sleep(11000);
//		System.out.println("11秒："+cache);
		return "ok";
	}
	
	@RequestMapping(value="/deldata/{id}",method=RequestMethod.DELETE,produces=MediaType.APPLICATION_JSON_VALUE)
	public String delData(@PathVariable Long id) throws InterruptedException{
		System.out.println("del:"+ id);
		//String cache = "2222";
//		Thread.sleep(1000);
//		System.out.println(cache);
//		Thread.sleep(11000);
//		System.out.println("11秒："+cache);
		return id.toString();
	}
	
	@RequestMapping(value="/postdata",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public Integer addData(@RequestBody User u){
		//String cache = "2222";
//		Thread.sleep(1000);
//		System.out.println(cache);
//		Thread.sleep(11000);
//		System.out.println("11秒："+cache);
		System.out.println(u.getId());
		return u.getId();
	}
	
	@RequestMapping(value="/updatedata",method=RequestMethod.PUT,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> updatedata(){
		int cache = 2222;
//		Thread.sleep(1000);
//		System.out.println(cache);
//		Thread.sleep(11000);
//		System.out.println("11秒："+cache);
		return ResponseEntity.ok(cache);
	}
	@RequestMapping(value="/deldata",method=RequestMethod.DELETE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> deleteData(){
		int cache = 2222;
//		Thread.sleep(1000);
//		System.out.println(cache);
//		Thread.sleep(11000);
//		System.out.println("11秒："+cache);
		return ResponseEntity.ok(cache);
	}
	
	@RequestMapping(value="/delids",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public List <Long> delids(@RequestBody List <Long> ids){        
    	System.out.println(JSONObject.toJSON(ids));
    	
    	return ids;
    }
	

    @RequestMapping(value="/getIds",method = RequestMethod.GET)
    // @Logs(operationType="add操作:",operationName="添加用户")  
    //@Log(desc="test define annotation")  
    public List<Integer> getIds(@RequestParam(value = "ids[]")List <Integer> ids){        
    //public ResponseEntity<List<Integer>> getIds(List <Integer> ids){        
    	System.out.println(JSONObject.toJSON(ids));
    	
    	return ids;
    }
    @RequestMapping(value="/gids",method = RequestMethod.GET)
    // @Logs(operationType="add操作:",operationName="添加用户")  
    //@Log(desc="test define annotation")  
    public String[] gids(@RequestParam(value = "ids[]")String[] ids){        
    	//public ResponseEntity<List<Integer>> getIds(List <Integer> ids){        
    	System.out.println(JSONObject.toJSON(ids));
    	
    	return ids;
    }
    
    @RequestMapping(value="/gds",method = RequestMethod.GET)
    // @Logs(operationType="add操作:",operationName="添加用户")  
    //@Log(desc="test define annotation")  
    public List <Long> gds(@RequestParam List <Long> ids){        
    	//public ResponseEntity<List<Integer>> getIds(List <Integer> ids){        
    	System.out.println(JSONObject.toJSON(ids));
    	
    	return ids;
    }
 
    @RequestMapping(value="/testpostdata",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    //@RequestMapping(value="/testpostdata",method=RequestMethod.POST,produces=MediaType.ALL_VALUE)
	public User testData(@Valid @RequestBody User u){
		//String cache = "2222";
//		Thread.sleep(1000);
//		System.out.println(cache);
//		Thread.sleep(11000);
//		System.out.println("11秒："+cache);
		System.out.println(u.getId());
		return u;
	}
    
    @Autowired
    private GreetingService greetingService;
    @RequestMapping(value="/aopproxy/expose",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
    public String returnString(@RequestParam("userName") String userName,@RequestParam("password") String password) throws UnsupportedEncodingException {
        greetingService.sayMessage("xiaoming ");
        //对于get中文乱码 js对中文进行两次encodeURI(encodeURI('张三'))
        return "hello return string 这是中文" + URLDecoder.decode(userName,"UTF-8") + "-" + password;
    }
    
}
