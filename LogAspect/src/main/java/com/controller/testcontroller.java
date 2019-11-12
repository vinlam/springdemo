package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.common.gateway.RestClient;
import com.dao.TB;
import com.dao.TC;
import com.entity.User;
import com.entity.UserDTO;
import com.service.EhCacheTestService;
import com.service.MemCacheTestService;
import com.service.SysLogService;
import com.service.UserService;
import com.service.impl.MemCacheTestServiceImpl;
import com.service.impl.a.AutoInject;
import com.service.impl.a.Inject;
import com.util.JsonUtil;

@Controller
@RequestMapping("/t")
public class testcontroller {
	private static final Logger logger = LoggerFactory.getLogger(testcontroller.class);

	@RequestMapping(value = "/testget", method = RequestMethod.GET)
	public String test(Model model,String name) {
		System.out.println("test");
		model.addAttribute("name",name);
		return "success";
	}

	@RequestMapping("/rd")
	public ModelAndView redirect(HttpServletResponse response) {
		System.out.println("redirect");
		// return "redirect:http://www.baidu.com";

		ModelAndView mv = new ModelAndView("redirect:testget");
		return mv;
	}

	@RequestMapping("/rd1")
	public void redirect1(HttpServletResponse response) {
		System.out.println("redirect1");
		try {
			response.sendRedirect("http://www.baidu.com");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//@RequestMapping(value = "/getPostUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@RequestMapping(value = "/getPostUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView getPostUser(@RequestBody User user) {
		//System.out.println(JSONObject.toJSON(userDTO));

		ModelAndView mAndView = new ModelAndView("showUser");
		mAndView.addObject("username", user.getName());
		mAndView.addObject("userid", user.getId());
		
		return mAndView;
	}
	
	
	@RequestMapping(value = "/postUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public void postUser(User user) {
		String url = "http://localhost:8080/LogAspect/t/getPostUser";
		List<User> users = new ArrayList<User>();
        UserDTO userDTO  = new UserDTO();
        userDTO.setUsers(users);
       // userDTO.setIds(ids);
        JSONObject postData = new JSONObject();
        postData.put("name", "request for post");
        postData.put("Id", "12345");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("email", "first.last@example.com");

        HttpEntity<User> request = new HttpEntity<User>(user, headers);
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        RestClient.getClient().setMessageConverters(converters);
        String u = RestClient.getClient().postForEntity(url, user, String.class).getBody();
        ////User json = RestClient.getClient().postForEntity(url,request, User.class).getBody();
        //JSONObject json = RestClient.getClient().postForEntity(url, postData, JSONObject.class).getBody();
        //String json = JsonUtil.beanToJson(u);
        //System.out.println(json.toString());
        System.out.println(u);
		//return mAndView;
	}

	@Autowired
	// @Qualifier("AutoInjectA")
	private AutoInject autoInjecta;
	@Autowired
	private Inject ijecta;
	@Autowired
	// @Qualifier("AutoInjectB")
	private com.service.impl.b.AutoInject autoInjectb;

	@RequestMapping(value = "/b")
	@ResponseBody
	public String testb() {
		String a = autoInjecta.print();
		String b = autoInjectb.print();
		logger.info(a + "--------------" + b);
		return a + "--------------" + b + ijecta.print();
	}

	@Autowired
	private TB tb;
	@Autowired
	private TC tc;

	@RequestMapping(value = "/dao")
	@ResponseBody
	public String testbase() {
		logger.info(tb.getVal("tb--string") + tc.getCount(999));

		return tb.getVal("tb--string") + tb.getUser("jack");
	}

	@RequestMapping(value = "/testpost", method = RequestMethod.POST)
	public ModelAndView testPost(String txt) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("success");
		System.out.println("test:" + txt);

		return mv;
	}

	@RequestMapping(value = "/testApi")
	public ModelAndView testApi() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("testpage");

		return mv;
	}

	@RequestMapping(value = "/upload")
	public ModelAndView upload() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("upload");

		return mv;
	}

	@Autowired
	private MemCacheTestServiceImpl memCacheTestServiceImpl;
	@Autowired
	private SysLogService sysLogService;

	@RequestMapping(value = "/getcache")
	@ResponseBody
	public String getcache() {
		// String cache = memCacheTestServiceImpl.getTimestamp("param");
		int count = sysLogService.count();
		// String cache = "2222";
//		Thread.sleep(1000);
//		System.out.println(cache);
//		Thread.sleep(11000);
//		System.out.println("11秒："+cache);
		return "aaaa" + count;
	}

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/gu")
	public ResponseEntity<User> getUser(String name) {
		User u = userService.getUser("jack");
		return ResponseEntity.ok(u);
	}

	@RequestMapping(value = "/getUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	// @Logs(operationType="add操作:",operationName="添加用户")
	// @Log(desc="test define annotation")
	@ResponseBody
	public String getUser(UserDTO userDTO) {
		System.out.println(JSONObject.toJSON(userDTO));

		return JSONObject.toJSON(userDTO).toString();
	}

	@RequestMapping(value = "/getUser1", method = RequestMethod.GET, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	// @Logs(operationType="add操作:",operationName="添加用户")
	// @Log(desc="test define annotation")
	@ResponseBody
	public String getUser1(UserDTO userDTO) {
		System.out.println(JSONObject.toJSON(userDTO));

		return JSONObject.toJSON(userDTO).toString();
	}

	@Autowired
	private MemCacheTestService memCacheTestService;

	@Autowired
	private EhCacheTestService ehCacheTestService;

	@RequestMapping(value = "/clearcache")
	// get myCache_inTimeCache stats items列出所有keys stats cachedump 7 0 列出的items
	// id，本例中为7，第2个参数为列出的长度，0为全部列出
	@ResponseBody
	public String clearcache() throws InterruptedException {
		memCacheTestService.clearAll();
		System.out.println("5 mC_t：" + memCacheTestService.getTimestamp("t"));
		System.out.println("5 mC_st：" + memCacheTestService.getTimestamp("st"));
		System.out.println("5 myCache_t：" + ehCacheTestService.getTimestamp("t"));
		List<String> listKey = new ArrayList<String>();
		listKey.add("t");
		listKey.add("st");
		for (String s : listKey) {
			memCacheTestService.deleteOne(s);
		}

		System.out.println("5 mC_t：" + memCacheTestService.getTimestamp("t"));
		System.out.println("5 mC_st：" + memCacheTestService.getTimestamp("st"));
		return "ok";
	}
}
