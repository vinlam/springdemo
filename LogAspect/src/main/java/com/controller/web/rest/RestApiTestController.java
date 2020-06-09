package com.controller.web.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.define.annotation.CacheLock;
import com.define.annotation.CacheParam;
import com.define.annotation.LocalLock;
import com.entity.Person;
import com.entity.TempDTO;
import com.entity.TestDTO;
import com.entity.TestDataDTO;
import com.entity.User;
import com.service.GreetingService;
import com.service.IAutoInject;
import com.service.SaveDataService;
import com.util.JsonMapper;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
//@Validated
public class RestApiTestController {
	private static final Logger logger = LoggerFactory.getLogger(RestApiTestController.class);
	@Autowired
	private HttpServletRequest servletRequest;
	@Autowired
	@Qualifier("AutoInjectB")
	private IAutoInject autoInjectb;

	@RequestMapping(value = "/inject", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	// @ResponseBody
	public String testb() {
		String b = autoInjectb.print();
		logger.info("--------------" + b);
		return "--------------" + b;
	}

	@Autowired
	private SaveDataService saveDataService;

	@RequestMapping(value = "/saveandreturnurl", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	// @ResponseBody
	public String saveData(@RequestBody User u) {
		saveDataService.saveUser(u);
		Map<String, String> map = new HashMap<>();
		map.put("url", "/t/testget?name=xt");
		return JsonMapper.toJsonString(map);
	}
	
	@RequestMapping(value = "/tempData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	// @ResponseBody
	public TempDTO tempData(@RequestBody TempDTO t) {
		
		return t;
	}

	@RequestMapping(value = "/getSaveData/{uid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	// @ResponseBody
	public User getSaveData(@PathVariable(required = true) int uid) {
		User u = new User();
		u.setId(uid);
		User r = saveDataService.getUser(u);
		logger.info("result:" + JsonMapper.toJsonString(r));
		if (r.getName() != null) {
			return r;
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/getdata", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getData() {
		return "ok";
	}

	@GetMapping(value = "gettest/user", produces = MediaType.APPLICATION_JSON_VALUE)
	public User getTestUser(@Validated User u) {
		return u;
	}
	
	@GetMapping(value ="testDate", produces = MediaType.APPLICATION_JSON_VALUE)
    public TestDataDTO testDate(TestDataDTO vo){
        System.out.println("startdate:"+vo.getStartDate());
        System.out.println("enddate:"+vo.getEndDate());
 
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(vo.getStartDate());
        System.out.println("date2:"+date);
 
        TestDataDTO vo2 = new TestDataDTO();
        vo2.setStartDate(new Date());
        vo2.setEndDate(new Date());
        return vo2;
    }

	@GetMapping(value = "gettest/v/{str}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Validated
	public String gettest(@PathVariable("str") String str,
			@NotBlank(message = "str notBlank") @RequestParam String param,
			@RequestParam @Min(1) @Max(7) Integer dayOfWeek) {
		return str + "-" + dayOfWeek;
	}

	@GetMapping(value = "/valid-name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Validated
	public void createUsername(@PathVariable("name") @NotBlank @Size(max = 10) String username) {
		logger.info("valid:" + username);
	}

	// http://localhost:8080/LogAspect/api/addPerson?id=123123&name=weiyihaoge&sex=nan&age=30&email=test%40163.com&phone=13800138000&hostUrl=http%3A%2F%2Flocalhost%3A80&isnull=112312&hasJob=true
	// http://localhost:8080/LogAspect/api/addPerson?name=weiyihaoge&age=30&hasJob=true&sex=nan
	@GetMapping(value = "addPerson", produces = MediaType.APPLICATION_JSON_VALUE)
	public Person addPerson(@Validated final Person person) {
		return person;
	}

	@RequestMapping(value = "/deldata/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String delData(@PathVariable Long id) {
		System.out.println("del:" + id);
		return id.toString();
	}

	@RequestMapping(value = "/del/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void del(@PathVariable Long id) {
		System.out.println("del:" + id);
	}

	@RequestMapping(value = "/testput/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public String testPut(@PathVariable Long id, @RequestParam(defaultValue = "0") String type) {
		logger.info("testput:" + id + "-----" + type);
		return id + "-----" + type;
	}

	@RequestMapping(value = "/testputreq/{type}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public String testPutReq(@PathVariable String type,
			@RequestParam @Pattern(regexp = "^([0|1])$", message = "类型不正确") String id, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("testput:" + id + "-----" + type);
		return id + "-----" + type;
	}

	@RequestMapping(value = "/postdata", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Integer addData(@RequestBody User u) {
		System.out.println(u.getId());
		return u.getId();
	}

	@RequestMapping(value = "/updatedata", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> updatedata() {
		int cache = 2222;
		return ResponseEntity.ok(cache);
	}

	@RequestMapping(value = "/deldata", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> deleteData() {
		int cache = 2222;
		return ResponseEntity.ok(cache);
	}

	@RequestMapping(value = "/delids", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Long> delids(@RequestBody List<Long> ids) {
		System.out.println(JSONObject.toJSON(ids));

		return ids;
	}

	@RequestMapping(value = "/getIds", method = RequestMethod.GET)
	// @Logs(operationType="add操作:",operationName="添加用户")
	// @Log(desc="test define annotation")
	public List<Integer> getIds(@RequestParam(value = "ids[]") List<Integer> ids) {
		// public ResponseEntity<List<Integer>> getIds(List <Integer> ids){
		System.out.println(JSONObject.toJSON(ids));

		return ids;
	}
	
	@RequestMapping(value = "/getListId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	// @Logs(operationType="add操作:",operationName="添加用户")
	// @Log(desc="test define annotation")
	public List<Integer> getListId(@RequestParam(value = "ids[]") List<Integer> ids) {
		// public ResponseEntity<List<Integer>> getIds(List <Integer> ids){
		System.out.println(JSONObject.toJSON(ids));
		
		return ids;
	}

	@RequestMapping(value = "/gids", method = RequestMethod.GET)
	// @Logs(operationType="add操作:",operationName="添加用户")
	// @Log(desc="test define annotation")
	public String[] gids(@RequestParam(value = "ids[]") String[] ids) {
		// public ResponseEntity<List<Integer>> getIds(List <Integer> ids){
		System.out.println(JSONObject.toJSON(ids));

		return ids;
	}

	@RequestMapping(value = "/gds", method = RequestMethod.GET)
	// @Logs(operationType="add操作:",operationName="添加用户")
	// @Log(desc="test define annotation")
	public List<Long> gds(@RequestParam List<Long> ids) {
		// public ResponseEntity<List<Integer>> getIds(List <Integer> ids){
		System.out.println(JSONObject.toJSON(ids));

		return ids;
	}

	@RequestMapping(value = "/getUserEntity", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	// @Logs(operationType="add操作:",operationName="添加用户")
	// @Log(desc="test define annotation")
	public TestDTO getUser(TestDTO tDTO) {
		System.out.println(JSONObject.toJSON(tDTO));

		return tDTO;
	}

	@RequestMapping(value = "/testpostdata", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	// @RequestMapping(value="/testpostdata",method=RequestMethod.POST,produces=MediaType.ALL_VALUE)
	public User testData(@Validated @RequestBody User u) {
		System.out.println(u.getId());
		return u;
	}

	@RequestMapping(value = "/testgetdata", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public User testgetdata(User u) throws IOException {
		System.out.println(u.getId());
//    	InputStreamReader inputStreamReader = new InputStreamReader(servletRequest.getInputStream());
//		BufferedReader br = new BufferedReader(inputStreamReader);
//		String line = null;
//		StringBuilder sb = new StringBuilder();
//		while ((line = br.readLine()) != null) {
//			sb.append(line);
//		}
//		logger.info("param:"+sb);
		return u;
	}

	@RequestMapping(value = "/getUsers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> getUsers(@RequestParam(required = false) String name) {
		User u = new User();
		List<User> list = new ArrayList<User>();
		if (StringUtils.isNotBlank(name)) {
			u.setName(name);
			list.add(u);
		} else {
			// return null;
		}

		return list;
	}
	@RequestMapping(value = "/getParam", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getParam(@RequestParam(required = false,defaultValue = "single") String name) throws Exception {
		if("all".equals(name)) {
			
		}else if("single".equals(name)) {
			
		}else {
			throw new Exception("type error");
		}
		return name;
	}

	@Autowired
	private GreetingService greetingService;

	@RequestMapping(value = "/aopproxy/expose", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String returnString(@RequestParam("userName") String userName, @RequestParam("password") String password)
			throws UnsupportedEncodingException {
		greetingService.sayMessage("xiaoming ");
		// 对于get中文乱码 js对中文进行两次encodeURI(encodeURI('张三'))
		return "hello return string 这是中文" + URLDecoder.decode(userName, "UTF-8") + "-" + password;
	}
	
	@RequestMapping(value = "/getObj", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Object> getObj(){
		Map<String,Object> map = null;
		if(map == null) {
			map = new HashMap<String, Object>();
		}
		return map;
	}

	@PostMapping(value = "/localLock")
	@ApiOperation(value = "重复提交验证测试--使用本地缓存锁")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "token", value = "token", dataType = "String") })
	@LocalLock(key = "localLock:test:arg[0]")
	public String localLock(String token) {

		return "sucess=====" + token;
	}

	@PostMapping(value = "/cacheLock")
	@ApiOperation(value = "重复提交验证测试--使用redis锁")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "token", value = "token", dataType = "String") })
	@CacheLock()
	public String cacheLock(String token) {
		return "sucess=====" + token;
	}

	@PostMapping(value = "/cacheLock1")
	@ApiOperation(value = "重复提交验证测试--使用redis锁")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "token", value = "token", dataType = "String") })
	@CacheLock(prefix = "redisLock.test", expire = 20)
	public String cacheLock1(String token) {
		return "sucess=====" + token;
	}

	@PostMapping(value = "/cacheLock2")
	@ApiOperation(value = "重复提交验证测试--使用redis锁")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "token", value = "token", dataType = "String") })
	// @CacheLock
	@CacheLock(prefix = "redisLock.test", expire = 20)
	public String cacheLock2(@CacheParam(name = "token") String token) {
		return "sucess=====" + token;
	}
}
