package com.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSONObject;
import com.common.gateway.RestClient;
import com.dao.TB;
import com.dao.TC;
import com.entity.Cat;
import com.entity.Country;
import com.entity.PojoTest;
import com.entity.User;
import com.entity.UserDTO;
import com.service.EhCacheTestService;
import com.service.MemCacheTestService;
import com.service.SysLogService;
import com.service.UserService;
import com.service.impl.MemCacheTestServiceImpl;
import com.service.impl.a.AutoInject;
import com.service.impl.a.Inject;
import com.util.JsonMapper;
import com.util.LoggerUtil;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Controller
@RequestMapping("/view")
public class MyController {
	private static final Logger logger = LoggerFactory.getLogger(MyController.class);
	private static org.apache.log4j.Logger log = LoggerUtil.getLog(MyController.class);
	@Autowired
	private HttpServletRequest servletRequest;
	@Autowired
	private HttpServletResponse servletResponse;

	// http://localhost:8080/LogAspect/t/testApi
	@RequestMapping(value = "/testget", method = RequestMethod.GET)
	public String test(Model model, String name) {
		System.out.println(servletRequest.getRequestURL());
		System.out.println("test");
		model.addAttribute("name", name);
		return "success";
	}

	@RequestMapping(value = "/route", method = RequestMethod.GET)
	public ModelAndView route(Model model, String name) {
		String schema = servletRequest.getScheme();
		int port = servletRequest.getServerPort();
		String path = servletRequest.getContextPath();
		String severName = servletRequest.getServerName();
		logger.info(schema + "://" + severName + ":" + port + path);
		Cookie[] cookies = servletRequest.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie != null && StringUtils.isNotBlank(cookie.getName())) {
					logger.info("cookieName:" + cookie.getName() + " - cookieVal:" + cookie.getValue());
				}
			}
		}
		logger.info("schema:" + schema);
		log.info("mylogutil:" + schema);

		Cookie c = new Cookie("name", "cookie");
		c.setDomain(".test.com");
		// c.setSecure(true);
		// c.setPath("/");
		// if HttpOnly
		c.setPath(";Path=/;HttpOnly;");
		servletResponse.addCookie(c);

		List<User> users = new ArrayList<User>();

		User user = new User();
		user.setAge(10);
		user.setName("jack");
		user.setPassword("000000");
		users.add(user);
		user = new User();
		user.setAge(12);
		user.setName("tom");
		user.setPassword("111111");
		users.add(user);
		User user1 = new User();
		user1.setAge(19);
		user1.setName("kk");
		user1.setPassword("222222");
		ModelAndView mv = new ModelAndView();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		map.put("k", "v=123");
		map.put("a", "123");
		mapList.add(map);
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

		mapperFactory.classMap(User.class, Map.class).byDefault();
		MapperFacade mapper = mapperFactory.getMapperFacade();
		Map<String, Object> mapuser = mapper.map(user1, Map.class);
		logger.info(mapuser.toString());
		mv.addObject("r", "#a=123" + schema);
		mv.addObject("m", map);
		mv.addObject("users", users);
		mv.addObject("u", user1);
		mv.addObject("list", mapList);
		mv.addObject("mapuser", mapuser);
		mv.setViewName("success");

		mv.addObject("uri", servletRequest.getRequestURI());
		mv.addObject("url", servletRequest.getRequestURL());
		PojoTest pojo = new PojoTest("小明", "男");
		model.addAttribute("pojo", pojo);
		return mv;
	}

	@RequestMapping("/rd")
	public ModelAndView redirect(HttpServletResponse response) {
		System.out.println("redirect");
		System.out.println(servletRequest.getRequestURL());
		// return "redirect:http://www.baidu.com";
		try {
			response.getWriter().write("http://www.baidu.com");
			// return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mv = new ModelAndView("redirect:testget");
		return mv;
	}

	// https://localhost/LogAspect/t/testget?msg=%E6%AC%A2%E8%BF%8E%E8%AE%BF%E9%97%AE+hangge.com&blogName=java2blog&authorName=vin
	// https://localhost/LogAspect/t/myrd
	@RequestMapping("/myrd")
	public ModelAndView mvredirect(HttpServletResponse response) {
		System.out.println("redirect");
		System.out.println(servletRequest.getHeader("X-Forwarded-Proto") + "\n" + servletRequest.getRequestURL());
		Log.info("Proto:" + servletRequest.getHeader("X-Forwarded-Proto") + servletRequest.getServerName() + " Port:"
				+ servletRequest.getHeader("X-Real-Port") + "\n" + servletRequest.getRequestURL());
		// return "redirect:http://www.baidu.com";
//		try {
//			response.getWriter().write("http://www.baidu.com");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		ModelAndView mv = new ModelAndView();
		mv.setView(new RedirectView("/t/testget", true, false, true));
		// mv.setView(new RedirectView("/t/testget"));
		return mv;
	}

	@RequestMapping("/rv")
	public ModelAndView gorestview(HttpServletResponse response) {
		System.out.println("redirect");
		System.out.println(servletRequest.getScheme() + "\n" + servletRequest.getRequestURL());
		// return "redirect:http://www.baidu.com";
//		try {
//			response.getWriter().write("http://www.baidu.com");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		ModelAndView mv = new ModelAndView();
		// mv.setView(new RedirectView("/testget", true, false, true));
		mv.setView(new RedirectView("../restview/test"));
		return mv;
	}

	@RequestMapping("/rvphp")
	public ModelAndView rvphp(HttpServletResponse response) {
		System.out.println("redirect");
		System.out.println(servletRequest.getScheme() + "\n" + servletRequest.getRequestURL());
		// return "redirect:http://www.baidu.com";
//		try {
//			response.getWriter().write("http://www.baidu.com");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		ModelAndView mv = new ModelAndView();
		// mv.setView(new RedirectView("/testget", true, false, true));
		mv.setView(new RedirectView("/arr.php"));
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

	@RequestMapping("/rdp")
	public void redirectpostattribute(HttpServletResponse response) {
		System.out.println("rdp");
		try {
			String url = "testpost";
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("txt", "from rdp post");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
			out.println("<HTML>");
			out.println(" <HEAD><TITLE>sender</TITLE></HEAD>");
			out.println(" <BODY>");
			out.println("<form name=\"submitForm\" action=\"" + url + "\" method=\"post\">");
			Iterator<String> it = parameter.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				out.println("<input type=\"hidden\" name=\"" + key + "\" value=\"" + parameter.get(key) + "\"/>");
			}
			out.println("</from>");
			out.println("<script>window.document.submitForm.submit();</script> ");
			out.println(" </BODY>");
			out.println("</HTML>");
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// @RequestMapping(value = "/getPostUser", method = RequestMethod.POST, produces
	// = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@RequestMapping(value = "/getPostUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView getPostUser(@RequestBody User user) {
		// System.out.println(JSONObject.toJSON(userDTO));

		ModelAndView mAndView = new ModelAndView("showUser");
		mAndView.addObject("username", user.getName());
		mAndView.addObject("userid", user.getId());

		return mAndView;
	}

	@RequestMapping(value = "/getPostUser1", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public User getPostUser1(User user) {
		// System.out.println(JSONObject.toJSON(userDTO));

		return user;
	}

	@RequestMapping(value = "/postUser", method = RequestMethod.POST) // application/xml;charset=UTF-8,
	@ResponseBody
	public User pUser(User user) {
		// System.out.println(JSONObject.toJSON(userDTO));

		return user;
	}

	@RequestMapping(value = "/restclientformpost", method = RequestMethod.POST)
	@ResponseBody
	public String restclientformpost(User user) {
		String url = "http://localhost:8080/LogAspect/t/getPostUser1";
		List<User> users = new ArrayList<User>();
		UserDTO userDTO = new UserDTO();
		userDTO.setUsers(users);
		// userDTO.setIds(ids);
		JSONObject postData = new JSONObject();
		postData.put("name", "request for post");
		postData.put("Id", "12345");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);// produces = MediaType.APPLICATION_XML_VALUE

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		// map.add("email", "first.last@example.com");
		map.add("name", "123post");
//		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
//		UserVo vo  = mapperFactory.getMapperFacade().map(user, UserVo.class);
//		//System.out.println(vo.toString());
//		logger.info("vo:"+JsonMapper.toJsonString(vo));
		HttpEntity<MultiValueMap<String, Object>> requestmap = new HttpEntity<MultiValueMap<String, Object>>(map,
				headers);
		// String u = RestClient.getClient().postForEntity(url, requestmap,
		// String.class).getBody();

		// String u = RestClient.getClient().exchange(url,HttpMethod.POST,
		// requestmap,String.class).getBody();//headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);//produces
		// = MediaType.APPLICATION_XML_VALUE
		User uu = RestClient.getClient().exchange(url, HttpMethod.POST, requestmap, User.class).getBody();// headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);//produces
																											// =
																											// MediaType.APPLICATION_XML_VALUE
		// System.out.println(u);
		return JsonMapper.toJsonString(uu);
	}

	@RequestMapping(value = "/returnpostUser", method = RequestMethod.POST)
	public String returnpostUser(User user) {
		String url = "http://localhost:8080/LogAspect/t/postUser";
		List<User> users = new ArrayList<User>();
		UserDTO userDTO = new UserDTO();
		userDTO.setUsers(users);
		// userDTO.setIds(ids);
		JSONObject postData = new JSONObject();
		postData.put("name", "request for post");
		postData.put("Id", "12345");
		HttpHeaders headers = new HttpHeaders();

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		// map.add("email", "first.last@example.com");
		map.add("name", "123post");

		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<User> request = new HttpEntity<User>(user, headers);
//		String u = RestClient.getClient().exchange(url,HttpMethod.POST, request,String.class).getBody();

		HttpEntity<MultiValueMap<String, Object>> requestmap = new HttpEntity<MultiValueMap<String, Object>>(map,
				headers);
//		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
//		converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
//		RestClient.getClient().setMessageConverters(converters);
		// String u = RestClient.getClient().postForEntity(url, user,
		// String.class).getBody();
		String u = RestClient.getClient().exchange(url, HttpMethod.POST, request, String.class).getBody();

		// String u = RestClient.getClient().exchange(url,HttpMethod.POST,
		// requestmap,String.class).getBody();//headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);//produces
		// = MediaType.APPLICATION_XML_VALUE
		System.out.println(u);
		return u;
		// User json =
		// RestClient.getClient().postForEntity(url,request,User.class).getBody();
		// JSONObject json = RestClient.getClient().postForEntity(url,
		// postData,JSONObject.class).getBody();
		// String json = JsonUtil.beanToJson(u);
		// System.out.println(json.toString());

//		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//		
//		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
//		map.add("email", "first.last@example.com");
//		
//		HttpEntity<User> request = new HttpEntity<User>(user, headers);
////		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
////		converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
////		RestClient.getClient().setMessageConverters(converters);
//		//String u = RestClient.getClient().postForEntity(url, user, String.class).getBody();
//		String u = RestClient.getClient().exchange(url,HttpMethod.POST, request,String.class).getBody();
//		//// User json = RestClient.getClient().postForEntity(url,request,
//		//// User.class).getBody();
//		// JSONObject json = RestClient.getClient().postForEntity(url, postData,
//		//// JSONObject.class).getBody();
//		// String json = JsonUtil.beanToJson(u);
//		// System.out.println(json.toString());
//		System.out.println(u);
		// return mAndView;
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
		mv.addObject("name", txt);
		mv.setViewName("success");
		System.out.println("test:" + txt);

		return mv;
	}

	@RequestMapping(value = "/redirectpost", method = RequestMethod.GET)
	public String redirectpost(RedirectAttributes attributes) {

		attributes.addFlashAttribute("txt", "from redirect post");
		String url = "testpost";
		return "redirect:" + url;
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

	// curl -X POST --header 'Content-Type: application/x-www-form-urlencoded' -d
	// 'Id=120&name=jack&password=string'
	// 'https://localhost/LogAspect/t/postformdata' -k
	@RequestMapping(value = "/postformdata", method = RequestMethod.POST)
	@ResponseBody
	public User postformdata(User u, HttpServletRequest request) {
		// public User postformdata(User u){
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(servletRequest.getInputStream());
			BufferedReader br = new BufferedReader(inputStreamReader);
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			logger.info("param:" + sb);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("getParameter:" + request.getParameter("Id"));
		System.out.println(u.getId());
		return u;
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

	@ModelAttribute
	public Country getCountry(@RequestParam(required = false) String countryName,
			@RequestParam(required = false, defaultValue = "1") long population) {
		Country country = new Country();
		country.setCountryName(countryName);
		country.setPopulation(population);
		return country;
	}

	@RequestMapping(value = "/addCountry", method = RequestMethod.POST)
	public String addCountry(@ModelAttribute Country country, ModelMap model) {
		model.addAttribute("countryName", country.getCountryName());
		model.addAttribute("population", country.getPopulation());
		return "countryDetails";
	}

	@RequestMapping(value = "/forward", method = RequestMethod.GET)
	public String forward(@RequestParam String param, HttpServletRequest request, HttpServletResponse response) {
		logger.info("--------------" + param);
		try {
			request.getRequestDispatcher("/t/getforward").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return "forward:/t/getforward?" +
		// servletRequest.getQueryString();//用@RequestParam 接收参数会变成重复： 123变
		// 123，123但用request.getParameter("param")就不会；
		return null;
	}

	@RequestMapping(value = "/mvforward", method = RequestMethod.GET)
	public ModelAndView mvforward(@RequestParam(required = false) String p) {
		logger.info("--------------" + p);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("param", "123");
		// modelAndView.setViewName("forward:/t/getforward?" +
		// servletRequest.getQueryString());
		modelAndView.setViewName("forward:/t/getforward");
		return modelAndView;
	}

	@ModelAttribute
	public void populateModel(@RequestParam(required = false) String abc, Model model) {
		model.addAttribute("data", abc); // 返回model
	}

	@RequestMapping(value = "/helloWorld")
	public String helloWorld() {
		return "helloWorld"; // 返回的视图名
	}
//    
//	@ModelAttribute(value = "fwinfo")
//	public Map<String, String> userinfo() {
//		HashMap<String, String> map = new HashMap<>();
//		map.put("name", "jack");
//		map.put("age", "20");
//		return map;
//	}

	@RequestMapping(value = "/getforward", method = RequestMethod.GET)
	public ResponseEntity<String> getForward(@RequestParam(required = false) String param) {
		logger.info("------param------" + param);
		logger.info("------Attribute param------" + servletRequest.getAttribute("data"));

		return ResponseEntity.ok(param);
	}

	@RequestMapping(value = "/showattr", method = RequestMethod.GET)
	public String showAttr() {
		logger.info("------Attribute param------" + servletRequest.getAttribute("data"));

		return "showattr";
	}

	@RequestMapping(value = "/myiframerd", method = RequestMethod.GET)
	public ModelAndView myiframerd() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("vc", "/LogAspect/t/iframe");
		modelAndView.setViewName("iframeredirect");
		return modelAndView;
	}

	@RequestMapping(value = "/iframe", method = RequestMethod.GET)
	public ModelAndView iframe() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("redirectUrl", "/LogAspect/t/route");
		modelAndView.setViewName("iframe");
		return modelAndView;
	}

	@RequestMapping("/helloshow")
	public String helloshow(@ModelAttribute("users") User user, Map<String, Object> map, Cat cat, String password) {
		cat.setSpeed(100);
		System.out.println(password);
		return "helloshow";
	}
	
//	@ModelAttribute注解的方法在@RequestMapping注解的方法之前执行，并且preUser方法中，一共有四个对象放入map中，相当于：
//
//	map.put("cat", cat)、map.put("user", user)、map.put("users", user1)和map.put("string", "abc");
//
//	POJO在传参的过程中，springmvc会默认的把POJO放入到map中，其中键值就是类名的第一个字母小写。在@ModelAttribute注解的方法里，POJO放入到Map的同时，也放入ImpliciteModel中， 比如上面代码中的user和cat。@ModelAttribute注解的方法里，返回类型不是void，则返回的值也会被放到Map中，其中键值为返回类型的第一个字母小写。比如上述代码中，返回的"abc",就会被放入到Map中，相当于map.put("string", "abc")。
//
//	在执行@ModelAttribute注解的方法里，表单的数据会被当作参数传到@ModelAttribute注解的方法，和@RequestMapping注解的方法传参是一样的。
//
//	(2) @ModelAttribute对参数进行注解
//
//	比如上面的代码，@ModelAttribute("users")User user。在传参的过程中，首先检查ImpliciteModel有没有键值为users，有的话，直接从ImpliciteModel中取出该对象，然后在把表单传过来的数据赋值到该对象中，最后把该对象当作参数传入到@RequestMapping注解方法里，也就是hello方法。当检查到键值的话，并不会创建新的对象，而是直接从ImpliciteModel直接取出来。
//
//	(3) @ModelAttribute和@RequestMapping一起对方法进行注解
//
//	@ModelAttribute和@RequestMapping对方法进行注解时，其中返回类型被到Map中，并不会被当作视图的路径进行解析
	@ModelAttribute
	@RequestMapping("/helloattr")
	public String hello(@ModelAttribute("users") User user, Map<String, Object> map, Cat cat, String password) {
		cat.setSpeed(100);
		System.out.println(password);
		return "bbbbb";
	}

	@ModelAttribute
	public String preUser(Cat cat, User user, Map<String, Object> map, String username) {
		System.out.println(username);
		cat.setSpeed(110);
		user.setId(1);
		User user1 = new User();
		user1.setAge(10);
		user1.setName("jack");
		user1.setPassword("111111");
		map.put("users", user1);
		return "abc";
	}
}
