package com.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.jasper.tagplugins.jstl.core.Redirect;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
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
import com.entity.Country;
import com.entity.User;
import com.entity.UserDTO;
import com.entity.UserVo;
import com.service.EhCacheTestService;
import com.service.MemCacheTestService;
import com.service.SysLogService;
import com.service.UserService;
import com.service.impl.MemCacheTestServiceImpl;
import com.service.impl.a.AutoInject;
import com.service.impl.a.Inject;
import com.util.JsonMapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Controller
@RequestMapping("/t")
public class testcontroller {
	private static final Logger logger = LoggerFactory.getLogger(testcontroller.class);
	@Autowired
	private HttpServletRequest servletRequest;
	@Autowired
	private HttpServletResponse servletResponse;

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
		logger.info(schema+"://"+severName+":"+port+path);
		Cookie[] cookies = servletRequest.getCookies();
		if(cookies !=null)
		for(Cookie cookie: cookies) {
			if(cookie!=null&&StringUtils.isNotBlank(cookie.getName())) {
				logger.info("cookieName:"+cookie.getName() + " - cookieVal:" + cookie.getValue());
			}
		}
		logger.info("schema:"+schema);
		
		Cookie c = new Cookie("name", "cookie");
		c.setDomain(".test.com");
		//c.setPath("/");
		//if HttpOnly
		c.setPath(";Path=/;HttpOnly;");
	    servletResponse.addCookie(c);
		ModelAndView mv = new ModelAndView();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("k","v=123");
		map.put("a","123");
		mv.addObject("r", "#a=123"+schema);
		mv.addObject("m", map);
		mv.setViewName("success");
		return mv;
	}

	@RequestMapping("/rd")
	public ModelAndView redirect(HttpServletResponse response) {
		System.out.println("redirect");
		System.out.println(servletRequest.getRequestURL());
		// return "redirect:http://www.baidu.com";
		try {
			response.getWriter().write("http://www.baidu.com");
			//return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mv = new ModelAndView("redirect:testget");
		return mv;
	}
	//https://localhost/LogAspect/t/testget?msg=%E6%AC%A2%E8%BF%8E%E8%AE%BF%E9%97%AE+hangge.com&blogName=java2blog&authorName=vin
	//https://localhost/LogAspect/t/myrd
	@RequestMapping("/myrd")
	public ModelAndView mvredirect(HttpServletResponse response) {
		System.out.println("redirect");
		System.out.println(servletRequest.getHeader("X-Forwarded-Proto")+"\n"+servletRequest.getRequestURL());
		Log.info("Proto:"+servletRequest.getHeader("X-Forwarded-Proto")+servletRequest.getServerName()+" Port:"+servletRequest.getHeader("X-Real-Port")+"\n"+servletRequest.getRequestURL());
		// return "redirect:http://www.baidu.com";
//		try {
//			response.getWriter().write("http://www.baidu.com");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		ModelAndView mv = new ModelAndView();
		mv.setView(new RedirectView("/t/testget", true, false, true));
		//mv.setView(new RedirectView("/t/testget"));
		return mv;
	}
	
	@RequestMapping("/rv")
	public ModelAndView gorestview(HttpServletResponse response) {
		System.out.println("redirect");
		System.out.println(servletRequest.getScheme()+"\n"+servletRequest.getRequestURL());
		// return "redirect:http://www.baidu.com";
//		try {
//			response.getWriter().write("http://www.baidu.com");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		ModelAndView mv = new ModelAndView();
		//mv.setView(new RedirectView("/testget", true, false, true));
		mv.setView(new RedirectView("../restview/test"));
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
	
	@RequestMapping(value = "/getPostUser1", method = RequestMethod.POST,produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public User getPostUser1(User user) {
		// System.out.println(JSONObject.toJSON(userDTO));
		
		return user;
	}
	@RequestMapping(value = "/postUser", method = RequestMethod.POST)//application/xml;charset=UTF-8,
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
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);//produces = MediaType.APPLICATION_XML_VALUE

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		//map.add("email", "first.last@example.com");
		map.add("name", "123post");
//		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
//		UserVo vo  = mapperFactory.getMapperFacade().map(user, UserVo.class);
//		//System.out.println(vo.toString());
//		logger.info("vo:"+JsonMapper.toJsonString(vo));
		HttpEntity<MultiValueMap<String, Object>> requestmap = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
		//String u = RestClient.getClient().postForEntity(url, requestmap, String.class).getBody();
		
		//String u = RestClient.getClient().exchange(url,HttpMethod.POST, requestmap,String.class).getBody();//headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);//produces = MediaType.APPLICATION_XML_VALUE
		User uu = RestClient.getClient().exchange(url,HttpMethod.POST, requestmap,User.class).getBody();//headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);//produces = MediaType.APPLICATION_XML_VALUE
		//System.out.println(u);
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
		//map.add("email", "first.last@example.com");
		map.add("name", "123post");
		
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<User> request = new HttpEntity<User>(user, headers);
//		String u = RestClient.getClient().exchange(url,HttpMethod.POST, request,String.class).getBody();
		
		HttpEntity<MultiValueMap<String, Object>> requestmap = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
//		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
//		converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
//		RestClient.getClient().setMessageConverters(converters);
		//String u = RestClient.getClient().postForEntity(url, user, String.class).getBody();
		String u = RestClient.getClient().exchange(url,HttpMethod.POST, request,String.class).getBody();
		
		
		//String u = RestClient.getClient().exchange(url,HttpMethod.POST, requestmap,String.class).getBody();//headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);//produces = MediaType.APPLICATION_XML_VALUE
		System.out.println(u);
		return u;
		//User json = RestClient.getClient().postForEntity(url,request,User.class).getBody();
		//JSONObject json = RestClient.getClient().postForEntity(url, postData,JSONObject.class).getBody();
		//String json = JsonUtil.beanToJson(u);
		//System.out.println(json.toString());
		
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
	
	//curl -X POST --header 'Content-Type: application/x-www-form-urlencoded' -d 'Id=120&name=jack&password=string' 'https://localhost/LogAspect/t/postformdata' -k 
    @RequestMapping(value="/postformdata",method=RequestMethod.POST)
    @ResponseBody
    public User postformdata(User u,HttpServletRequest request){
    //public User postformdata(User u){
    	try {
    		InputStreamReader inputStreamReader = new InputStreamReader(servletRequest.getInputStream());
			BufferedReader br = new BufferedReader(inputStreamReader);
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			logger.info("param:"+sb);
		} catch (Exception e) {
			// TODO: handle exception
		}
    	System.out.println("getParameter:"+request.getParameter("Id"));
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
	public Country getCountry(@RequestParam(required = false) String countryName, @RequestParam(required = false,defaultValue = "1") long population) {
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
}
