package com.controller.web.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.common.gateway.AsyncRestClient;
import com.common.gateway.RestClient;
import com.entity.BaiduIpResponse;
import com.entity.User;
import com.entity.UserDTO;
import com.util.JsonMapper;
import com.util.JsonUtil;

@RestController
@RequestMapping("/api")
public class RestTemplateController {
	private final static Logger logger = LoggerFactory.getLogger(RestTemplateController.class);

	// @Autowired
	// private RestClient restClient;

	/*********** HTTP GET method *************/
	@GetMapping("/testGetAction")
	public Object getJson(@RequestParam String actionName) {
		JSONObject json = new JSONObject();
		json.put("username", "tester");
		json.put("pwd", "123456748");
		String name = "";
		try {
			actionName = new String(actionName.getBytes("ISO8859-1"), "utf-8");
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

	/********** HTTP POST method **************/
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
		UserDTO userDTO = new UserDTO();
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
		u.setName(user.getName() + "-boy");
		u.setPassword(user.getPassword() + "123");
		return u;
	}

	// @GetMapping(value = "/restGetAction")
	@RequestMapping(value = "/restGetAction", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public User getJsonRest(@RequestBody User user) {
		User u = new User();
		u.setId(user.getId());
		u.setName(user.getName() + "-boy");
		u.setPassword(user.getPassword() + "123");
		return u;
	}

	@GetMapping(value = "/restpostApi")
	public Object testPostRest() {
		String url = "http://localhost:8080/LogAspect/api/restPostAction";
		UserDTO userDTO = new UserDTO();
		User u = new User();
		u.setId(1233434);
		u.setName("jacksasdf");
		u.setPassword("pwd");
		// userDTO.setIds(ids);
		JSONObject postData = new JSONObject();
		postData.put("descp", "request for post");
		//User user = RestClient.getClient().postForEntity(url, u, User.class).getBody();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<User> httpEntity = new HttpEntity<>(u, headers);
		User user = RestClient.getClient().postForEntity(url, httpEntity, User.class).getBody();
		return JsonUtil.beanToJson(user);
	}

	@RequestMapping(value = "/restclentget", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String restclentget() {
		String p = "{\"iColumns\":7,\"iDisplayLength\":10,\"iDisplayStart\":0,\"iSortingCols\":0,\"sColumns\":\"\",\"sEcho\":1,\"subjectId\":\"11227\"}";
		User u = new User();
		u.setId(123);
		u.setName("jack");
		u.setPassword("pwd");

		p = JsonUtil.beanToJson(u);

		String url = "http://localhost:8080/LogAspect/api/restGetAction";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> httpEntity = new HttpEntity<>(p, headers);

		MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
		requestBody.add("name", "jjjj");
		// HttpEntity
		HttpEntity<MultiValueMap> requestEntity = new HttpEntity<MultiValueMap>(requestBody, headers);
		ResponseEntity<String> responseEntity = RestClient.getClient().exchange(url, HttpMethod.GET, requestEntity,
				String.class);
		return responseEntity.getBody();
	}

	@RequestMapping(value = "/getbaiduipdata", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getbaiduipdata() {
		String url = "https://api.map.baidu.com/location/ip?v=2.0&ak={ak}&ip={ip}&coor={coor}";

		Map<String, Object> parammap = new HashMap<String, Object>();
		parammap.put("ak", "Ff72OypnReCylnDvy0OBuEHLRRumZGX8");// RGl7YqMPBt1TWm4zNnFNO6wFkZleF2D0
		parammap.put("ip", "183.129.210.50");
		parammap.put("coor", "bd09ll");
		// HttpEntity
		String responseEntity = RestClient.getClient().getForObject(url, String.class, parammap);
//        String newurl= "https://api.map.baidu.com/location/ip?v=2.0&ak=%s&ip=%s&coor=%s";
//        String ak = "Ff72OypnReCylnDvy0OBuEHLRRumZGX8";
//        String ip = "183.129.210.50";
//        String coor = "bd09ll";
//        newurl = String.format(newurl, ak,ip,coor);
//        logger.info("baiduurl:",newurl);
//        //HttpEntity
//        String responseEntity = RestClient.getClient().getForObject(newurl, String.class);
		return responseEntity;
	}
	
	@RequestMapping(value = "/getbdip", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaiduIpResponse postbaiduipdata() {
		String url = "https://api.map.baidu.com/location/ip?v=2.0&ak={ak}&ip={ip}&coor={coor}";

		Map<String, Object> parammap = new HashMap<String, Object>();
		parammap.put("ak", "Ff72OypnReCylnDvy0OBuEHLRRumZGX8");// RGl7YqMPBt1TWm4zNnFNO6wFkZleF2D0
		parammap.put("ip", "183.129.210.50");
		//parammap.put("coor", "bd09ll");
		parammap.put("coor", "gcj02");//国测局
		// HttpEntity
		//String responseEntity = RestClient.getClient().getForObject(url, String.class, parammap);
		BaiduIpResponse responseEntity = RestClient.getClient().postForObject(url, null,BaiduIpResponse.class,parammap);
//        String newurl= "https://api.map.baidu.com/location/ip?v=2.0&ak=%s&ip=%s&coor=%s";
//        String ak = "Ff72OypnReCylnDvy0OBuEHLRRumZGX8";
//        String ip = "183.129.210.50";
//        String coor = "bd09ll";
//        newurl = String.format(newurl, ak,ip,coor);
//        logger.info("baiduurl:",newurl);
//        //HttpEntity
//        String responseEntity = RestClient.getClient().getForObject(newurl, String.class);
		return responseEntity;
	}
	
	@RequestMapping(value = "/getbdipasync", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String postbaiduipdataasync() {
		String url = "https://api.map.baidu.com/location/ip?v=2.0&ak={ak}&ip={ip}&coor={coor}";
		
		Map<String, Object> parammap = new HashMap<String, Object>();
		parammap.put("ak", "Ff72OypnReCylnDvy0OBuEHLRRumZGX8");// RGl7YqMPBt1TWm4zNnFNO6wFkZleF2D0
		parammap.put("ip", "183.129.210.50");
		//parammap.put("coor", "bd09ll");
		parammap.put("coor", "gcj02");//国测局
		// HttpEntity
		//String responseEntity = RestClient.getClient().getForObject(url, String.class, parammap);
		
		//调用完后立即返回（没有阻塞）
        ListenableFuture<ResponseEntity<BaiduIpResponse>> forEntity = AsyncRestClient.getClient().getForEntity(url, BaiduIpResponse.class,parammap);
        //异步调用后的回调函数
        forEntity.addCallback(new ListenableFutureCallback<ResponseEntity<BaiduIpResponse>>() {
            //调用失败
            @Override
            public void onFailure(Throwable ex) {
                logger.error("=====rest response faliure======"+ex.getMessage());
            }
            //调用成功
            @Override
            public void onSuccess(ResponseEntity<BaiduIpResponse> result) {
                logger.info("--->async rest response success----, result = "+JsonMapper.toJsonString(result.getBody()));
            }
        });
//        String newurl= "https://api.map.baidu.com/location/ip?v=2.0&ak=%s&ip=%s&coor=%s";
//        String ak = "Ff72OypnReCylnDvy0OBuEHLRRumZGX8";
//        String ip = "183.129.210.50";
//        String coor = "bd09ll";
//        newurl = String.format(newurl, ak,ip,coor);
//        logger.info("baiduurl:",newurl);
//        //HttpEntity
//        String responseEntity = RestClient.getClient().getForObject(newurl, String.class);
		return "ok";
	}
}