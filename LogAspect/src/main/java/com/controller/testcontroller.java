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
import org.springframework.util.ObjectUtils;
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
@RequestMapping("/t")
public class testcontroller {
	private static final Logger logger = LoggerFactory.getLogger(testcontroller.class);
	private static org.apache.log4j.Logger log = LoggerUtil.getLog(testcontroller.class);
	@Autowired
	private HttpServletRequest servletRequest;
	@Autowired
	private HttpServletResponse servletResponse;

	// http://localhost:8080/LogAspect/t/testApi
	@RequestMapping(value = "/testget", method = RequestMethod.GET)
	public String test(Model model, String name) {
		logger.info(servletRequest.getRequestURL().toString());
		logger.info("referer:" + servletRequest.getHeader("referer"));
		logger.info("test");
		Cookie[] cookies = servletRequest.getCookies();
		if (!ObjectUtils.isEmpty(cookies)) {
			for (Cookie cookie : cookies) {
				logger.info("cookie ---- "+cookie.getName() + ":" + cookie.getValue());
			}
		}
		model.addAttribute("name", name);
		return "success";
	}

	@RequestMapping(value = "/testcookie", method = RequestMethod.GET)
	public String setCookie(@RequestParam(required = false, defaultValue = "testcookie") String param) {

		Cookie c = new Cookie("name", param);
		c.setDomain(".test.com");
		c.setMaxAge(100);// second
		// c.setSecure(true);
		// c.setPath("/");
		// if HttpOnly
		c.setPath(";Path=/;HttpOnly;");
		servletResponse.addCookie(c);
		return c.getValue();
	}

	@RequestMapping(value = "/rdvcookie", method = RequestMethod.GET)
	public String rdvcookie(@RequestParam(required = false, defaultValue = "testcookie") String param) {

		Cookie c = new Cookie("name", param);
		c.setDomain(".test.com");
		c.setMaxAge(100);// second
		// c.setSecure(true);
		// c.setPath("/");
		// if HttpOnly
		c.setPath(";Path=/;HttpOnly;");
		servletResponse.addCookie(c);
		logger.info("for set cookie:"+ c.getValue());
		return "redirect:testget";
	}

	@RequestMapping(value = "/getcookie", method = RequestMethod.GET)
	public String getCookie() {

		Cookie[] cookies = servletRequest.getCookies();
		if (!ObjectUtils.isEmpty(cookies)) {
			for (Cookie cookie : cookies) {
				logger.info(cookie.getName() + ":" + cookie.getValue());
			}
		}
		return JsonMapper.toJsonString(cookies);
	}

	@RequestMapping(value = "/getcookiebynaame", method = RequestMethod.GET)
	public String getCookie(String param) {

		Cookie[] cookies = servletRequest.getCookies();
		if (!ObjectUtils.isEmpty(cookies)) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(param)) {
					logger.info(cookie.getName() + ":" + cookie.getValue());
					return cookie.getValue();
				}
			}
		}
		return JsonMapper.toJsonString(cookies);
	}

	@RequestMapping(value = "/delcookie", method = RequestMethod.GET)
	public void delCookie() {

		// 根据 key 将 value 置空
		Cookie cookie_username = new Cookie("name", "");
		// 设置持久时间为0
		cookie_username.setMaxAge(0);
		// 设置共享路径
		cookie_username.setPath("/");
		// 向客户端发送 Cookie
		servletResponse.addCookie(cookie_username);
		logger.info(cookie_username.getValue());
	}

	// http://localhost:8080/LogAspect/t/testApi
	@RequestMapping(value = "/redirectview", method = RequestMethod.GET)
	public String redirectview() throws IOException {
		String path = "https://www.baidu.com";
		return "redirect:" + "path";
	}

	@RequestMapping(value = "/rdv", method = RequestMethod.GET)
	public ModelAndView rdv() throws IOException {
		String path = "/testget";
		servletResponse.setContentType("application/json;charset=utf-8");
		servletResponse.setHeader("Content-Type", "application/json");
		servletResponse.setCharacterEncoding("UTF-8");
		servletResponse.setStatus(301);
		servletResponse.sendRedirect(path);
		return null;
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
		logger.info("redirect");
		logger.info("requesturl:"+servletRequest.getRequestURL().toString());
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
		logger.info("redirect");
		logger.info(servletRequest.getHeader("X-Forwarded-Proto") + "\n" + servletRequest.getRequestURL());
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
		response.setHeader("referer", "asdf");
		mv.setView(new RedirectView("/t/testget", true, false, true));
		// mv.setView(new RedirectView("/t/testget"));
		return mv;
	}

	@RequestMapping("/rv")
	public ModelAndView gorestview(HttpServletResponse response) {
		logger.info("redirect");
		logger.info(servletRequest.getScheme() + "\n" + servletRequest.getRequestURL());
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
		logger.info("redirect");
		logger.info(servletRequest.getScheme() + "\n" + servletRequest.getRequestURL());
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
		logger.info("redirect1");
		try {
			response.sendRedirect("http://www.baidu.com");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("/rdp")
	public void redirectpostattribute(HttpServletResponse response) {
		logger.info("rdp");
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
		// logger.info(JSONObject.toJSON(userDTO));

		ModelAndView mAndView = new ModelAndView("showUser");
		mAndView.addObject("username", user.getName());
		mAndView.addObject("userid", user.getId());

		return mAndView;
	}

	@RequestMapping(value = "/getPostUser1", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public User getPostUser1(User user) {
		// logger.info(JSONObject.toJSON(userDTO));

		return user;
	}

	@RequestMapping(value = "/postUser", method = RequestMethod.POST) // application/xml;charset=UTF-8,
	@ResponseBody
	public User pUser(User user) {
		// logger.info(JSONObject.toJSON(userDTO));

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
//		//logger.info(vo.toString());
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
		// logger.info(u);
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
		logger.info(u);
		return u;
		// User json =
		// RestClient.getClient().postForEntity(url,request,User.class).getBody();
		// JSONObject json = RestClient.getClient().postForEntity(url,
		// postData,JSONObject.class).getBody();
		// String json = JsonUtil.beanToJson(u);
		// logger.info(json.toString());

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
//		// logger.info(json.toString());
//		logger.info(u);
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
		logger.info("test:" + txt);

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
//		logger.info(cache);
//		Thread.sleep(11000);
//		logger.info("11秒："+cache);
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
		logger.info(JSONObject.toJSON(userDTO).toString());

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
		logger.info("getParameter:" + request.getParameter("Id"));
		logger.info(String.valueOf(u.getId()));
		return u;
	}

	@RequestMapping(value = "/getUser1", method = RequestMethod.GET, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	// @Logs(operationType="add操作:",operationName="添加用户")
	// @Log(desc="test define annotation")
	@ResponseBody
	public String getUser1(UserDTO userDTO) {
		logger.info(JSONObject.toJSON(userDTO).toString());

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
		logger.info("5 mC_t：" + memCacheTestService.getTimestamp("t"));
		logger.info("5 mC_st：" + memCacheTestService.getTimestamp("st"));
		logger.info("5 myCache_t：" + ehCacheTestService.getTimestamp("t"));
		List<String> listKey = new ArrayList<String>();
		listKey.add("t");
		listKey.add("st");
		for (String s : listKey) {
			memCacheTestService.deleteOne(s);
		}

		logger.info("5 mC_t：" + memCacheTestService.getTimestamp("t"));
		logger.info("5 mC_st：" + memCacheTestService.getTimestamp("st"));
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
		// modelAndView.setViewName("forward:/t/getforward");
		// modelAndView.setViewName("forward:/view/getforward?abc=123");
		modelAndView.setViewName("redirect:/view/showattr#abc=123");
		return modelAndView;
	}

	@RequestMapping(value = "/sforward", method = RequestMethod.GET)
	public String sforward() {
		return "redirect:/view/showattr#abc=123";
	}

	@ModelAttribute
	public void populateModel(Model model) {
		model.addAttribute("param", "abc"); // 返回model
	}

	@ModelAttribute(value = "fwinfo")
	public Map<String, String> userinfo() {
		HashMap<String, String> map = new HashMap<>();
		map.put("name", "jack");
		map.put("age", "20");
		return map;
	}

	@RequestMapping(value = "/getforward", method = RequestMethod.GET)
	public ResponseEntity<String> getForward(@RequestParam(required = false) String param,
			@ModelAttribute("fwinfo") Map<String, String> m) {
		logger.info("------param------" + param);
		return ResponseEntity.ok(m.get("name"));
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
}
