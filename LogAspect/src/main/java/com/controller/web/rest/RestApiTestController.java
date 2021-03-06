package com.controller.web.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.core.thread.ThreadCache;
import com.define.annotation.CacheLock;
import com.define.annotation.CacheParam;
import com.define.annotation.Idempotent;
import com.define.annotation.LocalLock;
import com.dto.DemoDTO;
import com.entity.Person;
import com.entity.SafeConfess;
import com.entity.SafeConfessContent;
import com.entity.Student;
import com.entity.TempDTO;
import com.entity.TestDTO;
import com.entity.TestDataDTO;
import com.entity.User;
import com.google.common.collect.Maps;
import com.service.GreetingService;
import com.service.IAutoInject;
import com.service.SaveDataService;
import com.service.StudentService;
import com.service.impl.StudentServiceImpl;
import com.util.ExportExcelByPoiUtil;
import com.util.JsonMapper;
import com.util.TokenUtil;
import com.util.ToolUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

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
	
	@Autowired
	private StudentService studentService; 

	@RequestMapping(value = "/type", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object returnByType(@NotBlank(message = "type notBlank") @RequestParam String type) {
		if ("api".equals(type)) {
			return type;
		} else {
			ModelAndView view = new ModelAndView();

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
			mv.addObject("r", "#a=123");
			mv.addObject("m", map);
			mv.addObject("users", users);
			mv.addObject("u", user1);
			mv.addObject("list", mapList);
			mv.addObject("mapuser", mapuser);
			mv.addObject("uri", servletRequest.getRequestURI());
			mv.addObject("url", servletRequest.getRequestURL());
			view.setViewName("success");
			return view;
		}
	}

	@RequestMapping(value = "/addStudent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String addStudent() {
		Student s = new Student();
		s.setId(1001);
		s.setEmail("jack@163.com");
		s.setStudentName("jack");
		s.setDeptId(1000);
		int i = studentService.addStudent(s);
		return String.valueOf(i);
	}
	
	@RequestMapping(value = "/addStudentT", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addStudentThread() {
		Student s = new Student();
		s.setId(1002);
		s.setEmail("tom@163.com");
		s.setStudentName("tom");
		s.setDeptId(1000);
		studentService.addStudentThread(s);
	}
	
	@RequestMapping(value = "/addStudentTM", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addStudentThreadSyncManager() {
		Student s = new Student();
		s.setId(1003);
		s.setEmail("lam@163.com");
		s.setStudentName("lam");
		s.setDeptId(1000);
		studentService.addStudentThreadSyncManager(s);
	}
	
	
	@RequestMapping(value = "/inject", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	// @ResponseBody
	public String testb() {
		String b = autoInjectb.print();
		logger.info("--------------" + b);
		return "--------------" + b;
	}

	@RequestMapping(value = "/forward", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	// @ResponseBody
	public String forward(@RequestParam String param, HttpServletRequest request, HttpServletResponse response) {
		logger.info("--------------" + param);
		try {
			request.getRequestDispatcher("/api/getforward").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/getforward", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	// @ResponseBody
	public ResponseEntity<String> getForward(@RequestParam(required = false) String param) {

		logger.info("------param------" + param);

		return ResponseEntity.ok(param);
	}

	@RequestMapping(value = "/testvoid", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	// @ResponseBody
	public void testVoid() {
		String b = autoInjectb.print();
		logger.info("void:--------------" + b);
		// return "--------------" + b;
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

	@RequestMapping(value = "/bd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	// @ResponseBody
	public DemoDTO bd(@RequestBody DemoDTO demoDTO) {
		return demoDTO;
	}

	@RequestMapping(value = "/formdata", method = RequestMethod.POST, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	// @ResponseBody
	public String formdata(User u) {
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

	// curl -X POST --header 'Content-Type: application/json' --header 'Accept:
	// application/json' -d '{"a":123,"b":"qwe"}'
	// 'http://localhost:8080/LogAspect/api/postMap/t'
	@RequestMapping(value = "/postMap/{m}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	// @ResponseBody
	public Map<String, Object> postMap(@PathVariable String m, @RequestBody Map<String, Object> map) {
		try {
			String param = ToolUtils.copyToString(servletRequest.getInputStream(), Charset.forName("UTF-8"));
			param = new String(ToolUtils.readInputStream(servletRequest.getInputStream()), Charset.forName("UTF-8"));
			Map<String, Object> mp = servletRequest.getParameterMap();
			System.out.println(mp);
			logger.info("param:" + param);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		System.out.println("--------" + m + "--------");
		return map;
	}

	// http://localhost:8080/LogAspect/api/postStr/post?s=sdfasdf
	@RequestMapping(value = "/postStr/{m}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	// @ResponseBody
	public String postStr(@PathVariable String m, @RequestParam String s) {
		System.out.println("--------" + m + "--------");
		return s;
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

	@RequestMapping(value = { "/getdata",
			"/mgetdata" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getData() {
		return "ok";
	}

	// 限制请求参数(参数匹配才会进入)，追加在url上的键值对，多个参数以&分割
	// 如：/api/login?name=zhangsan&&age=22
	// * 使用：@RequestMapping(path = "/login",
	// params={"name=kolbe","age=22"}),参数及值不对会返回404
	/**
	 * 限制请求头中的“Content-Type”值，客户端请求实体对应的MIME类型（Contect-Type:application/x-www-form-urlencoded）
	 * 使用：@RequestMapping(path = "/login", consumes = "text/plain") consumes =
	 * "text/plain" consumes = {"text/plain", "application/*"} consumes =
	 * MediaType.TEXT_PLAIN_VALUE
	 */

	/**
	 * 限制请求头中的“Accept”值,客户端能够接收的MIME类型（Accept:text/html,application/xml）
	 * 使用：@RequestMapping(path = "/login", produces = "text/plain;charset=UTF-8")
	 * produces = "text/plain" produces = {"text/plain", "application/*"} produces =
	 * MediaType.TEXT_PLAIN_VALUE produces = "text/plain;charset=UTF-8"
	 */
	@RequestMapping(value = { "/login" }, params = { "name=kolbe",
			"age=22" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getParam(@RequestParam String name, @RequestParam int age) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("age", age);

		return map;
	}

	@PostMapping(value = "/postjsonstr", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> postjsonstr(@RequestBody String jsonStr) {
		Map<String, Object> map = (Map<String, Object>) JsonMapper.fromJsonString(jsonStr, Map.class);

		return map;
	}

	@GetMapping(value = "gettest/user", produces = MediaType.APPLICATION_JSON_VALUE)
	public User getTestUser(@Validated User u) {
		return u;
	}

	@GetMapping(value = "testDate", produces = MediaType.APPLICATION_JSON_VALUE)
	public TestDataDTO testDate(TestDataDTO vo) {
		System.out.println("startdate:" + vo.getStartDate());
		System.out.println("enddate:" + vo.getEndDate());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(vo.getStartDate());
		System.out.println("date2:" + date);

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
	public String getParam(@RequestParam(required = false, defaultValue = "single") String name) throws Exception {
		if ("all".equals(name)) {

		} else if ("single".equals(name)) {

		} else {
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
	public Map<String, Object> getObj() {
		Map<String, Object> map = null;
		if (map == null) {
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

	// http://localhost:8091/LogAspect/api/exportSafeConfess
	@RequestMapping(value = "/exportSafeConfess", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportSafeConfess(HttpServletRequest request, HttpServletResponse response) {
		// 获取页面时间参数
		List<SafeConfess> resultList = new ArrayList<SafeConfess>();

		List<SafeConfessContent> list = new ArrayList<SafeConfessContent>();
		SafeConfessContent confessContent = new SafeConfessContent();
		confessContent.setcCheckResult("0");
		confessContent.setcContent("c01");
		confessContent.setcStatus("OK");
		SafeConfessContent confessContent1 = new SafeConfessContent();
		confessContent1.setcCheckResult("1");
		confessContent1.setcContent("c02");
		confessContent1.setcStatus("OK");
		SafeConfessContent confessContent2 = new SafeConfessContent();
		confessContent2.setcCheckResult("0");
		confessContent2.setcContent("c03");
		confessContent2.setcStatus("Fail");
		list.add(confessContent);
		list.add(confessContent1);
		list.add(confessContent2);
		SafeConfess confess = new SafeConfess();
		confess.setComfirmCheckPerson("CCP");
		confess.setComfirmCheckTime("2020-05-09");
		confess.setCompanyPerson("CP");
		confess.setCompanyPhone("13800138001");
		confess.setDangerDiscern("DD");
		confess.setDangerMeasures("DM");
		confess.setDangerManager("DMR");
		confess.setDeptName("DN");
		confess.setEnvironmentDiscren("ED");
		confess.setEnvironmentMeasures("EM");
		confess.setFactoryPerson("FP");
		confess.setFactoryPhone("020-8888888");
		confess.setManagerTime("2020-05-09");
		confess.setProjectName("PN");
		confess.setProjectNumber("P01");
		confess.setProjectPerson("PP");
		confess.setSafeConfessContentList(list);
		confess.setSafeName("SN");
		confess.setSceneComfirmPer("SCP");
		confess.setTestAfterPerson("TAP");
		confess.setTestAfterTime("2020-05-10");
		confess.setTestBeforePerson("TBP");
		confess.setTestBeforeTime("2020-05-15");
		confess.setTestPerson("TP");
		confess.setTestTime("2020-05-09");
		confess.setWorkAddress("WAGD");
		confess.setWorkNumber("WN01");

		SafeConfess confess1 = new SafeConfess();
		confess1.setComfirmCheckPerson("CCP1");
		confess1.setComfirmCheckTime("2020-05-09");
		confess1.setCompanyPerson("CP");
		confess1.setCompanyPhone("13800138002");
		confess1.setDangerDiscern("DD");
		confess1.setDangerMeasures("DM");
		confess1.setDangerManager("DMR");
		confess1.setDeptName("DN");
		confess1.setEnvironmentDiscren("ED");
		confess1.setEnvironmentMeasures("EM");
		confess1.setFactoryPerson("FP");
		confess1.setFactoryPhone("020-8888888");
		confess1.setManagerTime("2020-05-09");
		confess1.setProjectName("PN");
		confess1.setProjectNumber("P01");
		confess1.setProjectPerson("PP");
		confess1.setSafeConfessContentList(list);
		confess1.setSafeName("SN");
		confess1.setSceneComfirmPer("SCP");
		confess1.setTestAfterPerson("TAP");
		confess1.setTestAfterTime("2020-05-10");
		confess1.setTestBeforePerson("TBP");
		confess1.setTestBeforeTime("2020-05-15");
		confess1.setTestPerson("TP");
		confess1.setTestTime("2020-05-09");
		confess1.setWorkAddress("WAGD");
		confess1.setWorkNumber("WN01");

		String titleAttr[] = { "安全交底名称", "部门名称", "项目名称", "项目编号", "项目负责人", "工作地点", "工作编号", "公司联系人", "公司联系方式", "厂方联系人",
				"厂方联系方式", "现场确认人员 ", "执行人", "执行时间", "现场试验其它危险源辨识", "针对上述危险源制定的安全措施", "现场试验其它环境因素识别", "针对上述环境因素的控制措施",
				"措施确认检查人", "措施确认检查时间", "控制措施内容", "控制措施结果", "技术措施内容", "技术措施结果", "试验前检查人", "试验前检查时间", "试验前检查内容",
				"试验前检查结果", "试验中检查人", "试验中检查时间", "试验中检查内容", "试验中检查结果", "试验后检查人", "试验后检查时间", "试验后检查内容", "试验后检查结果" };
		int widthAttr[] = { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
				30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30 };
		String titleHead = "安全交底";
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		for (SafeConfess safeConfess : resultList) {
			Map<String, String> temp = null;
			List<SafeConfessContent> safeConfessContentList = safeConfess.getSafeConfessContentList();
			if (safeConfessContentList.size() > 0) {
				for (SafeConfessContent safeConfessContent : safeConfessContentList) {
					temp = new HashMap<String, String>();
					temp.put("安全交底名称", safeConfess.getSafeName());
					temp.put("部门名称", safeConfess.getDeptName());
					temp.put("项目名称", safeConfess.getProjectName());
					temp.put("项目编号", safeConfess.getProjectNumber());
					temp.put("项目负责人", safeConfess.getProjectPerson());
					temp.put("工作地点", safeConfess.getWorkAddress());
					temp.put("工作编号", safeConfess.getWorkNumber());
					temp.put("公司联系人", safeConfess.getCompanyPerson());
					temp.put("公司联系方式", safeConfess.getCompanyPhone());
					temp.put("厂方联系人", safeConfess.getFactoryPerson());
					temp.put("厂方联系方式", safeConfess.getFactoryPhone());
					temp.put("现场确认人员", safeConfess.getSceneComfirmPer());
					temp.put("执行人", safeConfess.getDangerManager());
					temp.put("执行时间", safeConfess.getManagerTime());
					temp.put("现场试验其它危险源辨识", safeConfess.getDangerDiscern());
					temp.put("针对上述危险源制定的安全措施", safeConfess.getDangerMeasures());
					temp.put("现场试验其它环境因素识别", safeConfess.getEnvironmentDiscren());
					temp.put("针对上述环境因素的控制措施", safeConfess.getEnvironmentMeasures());
					temp.put("措施确认检查人", safeConfess.getComfirmCheckPerson());
					temp.put("措施确认检查时间", safeConfess.getComfirmCheckTime());
					// 状态0：安全及环境相关控制措施 1：技术措施 2：试验前 3：试验中 4：试验后
					if (safeConfessContent.getcStatus().equals("0")) {
						temp.put("控制措施内容", safeConfessContent.getcContent());
						temp.put("控制措施结果", safeConfessContent.getcCheckResult());

					} else if (safeConfessContent.getcStatus().equals("1")) {
						temp.put("技术措施内容", safeConfessContent.getcContent());
						temp.put("技术措施结果", safeConfessContent.getcCheckResult());
					} else if (safeConfessContent.getcStatus().equals("2")) {
						temp.put("试验前检查人", safeConfess.getTestBeforePerson());
						temp.put("试验前检查时间", safeConfess.getTestBeforeTime());
						temp.put("试验前检查内容", safeConfessContent.getcContent());
						temp.put("试验前检查结果", safeConfessContent.getcCheckResult());
					} else if (safeConfessContent.getcStatus().equals("3")) {
						temp.put("试验中检查人", safeConfess.getTestPerson());
						temp.put("试验中检查时间", safeConfess.getTestTime());
						temp.put("试验中检查内容", safeConfessContent.getcContent());
						temp.put("试验中检查结果", safeConfessContent.getcCheckResult());
					} else if (safeConfessContent.getcStatus().equals("4")) {
						temp.put("试验后检查人", safeConfess.getTestAfterPerson());
						temp.put("试验后检查时间", safeConfess.getTestAfterTime());
						temp.put("试验后检查内容", safeConfessContent.getcContent());
						temp.put("试验后检查结果", safeConfessContent.getcCheckResult());
					}
					dataList.add(temp);
				}
			} else {
				temp = new HashMap<String, String>();
			}
		} /* 此处的key为每个sheet的名称，一个excel中可能有多个sheet页 */
		/* 此处key对应每一列的标题 *//* 该list为每个sheet页的数据 */
		Map<String, List<Map<String, String>>> map = Maps.newHashMap();
		map.put("安全交底列表", dataList);
		ExportExcelByPoiUtil.createExcel(request, response, titleAttr, titleHead, widthAttr, map,
				new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 }/* 此处数组为需要合并的列，可能有的需求是只需要某些列里面相同内容合并 */);
	}

	@Autowired
	private TokenUtil tokenUtil;

	@GetMapping("/get/token")
	public String getToken() {
		String token = tokenUtil.generateToken();
		return token;
	}

	@Idempotent
	@GetMapping("/token/test")
	public String testIdempotent() {
		return "校验成功";
	}

	@RequestMapping(value = "/response", method = RequestMethod.GET)
	public void resp(HttpServletResponse servletResponse) throws IOException {
		/**
		 * response对象学习： 作用： 用来响应数据到浏览器的一个对象 使用： 设置响应头 setHeader(String name,String
		 * value); // 在响应头中添加响应信息，但是同键会覆盖。 addHeader(String name,String value); //
		 * 在响应头中添加响应信息，但是不会覆盖。 设置响应状态 sendError(int num,String msg); // 自定义响应状态码。 设置响应实体
		 * resp.getWrite().write(String str); // 响应具体的数据给浏览器 设置响应编码格式：
		 * resp.setContentType("text/html;charset=utf-8");
		 * 
		 * 总结： service请求处理代码流程： (1)设置响应编码格式 (2)获取请求数据 (3)处理请求数据 java逻辑代码 -- 数据库操作（MVC思想）
		 * (4)响应处理结果 response
		 */
		servletResponse.setHeader("mouse", "two fly birds");
		servletResponse.setHeader("mouse", "bjsxt");
		servletResponse.addHeader("key", "thinkpad");
		servletResponse.addHeader("key", "wollo");
		// servletResponse.sendError(201, "wollo");
		// 设置响应编码格式
		// servletResponse.setHeader("content-type", "text/html;charset=utf-8");
		// servletResponse.setContentType("text/plain;charset=utf-8"); //告诉浏览器
		// 发送的是plain普通文本，<b>标签不被解析
		// servletResponse.setContentType("text/xml;charset=utf-8"); //告诉浏览器
		// 发送的是xml数据，并以xml的数据解析
		servletResponse.setContentType("application/json;charset=utf-8");
		servletResponse.setHeader("Content-Type", "application/json");
		servletResponse.setCharacterEncoding("UTF-8");
		servletResponse.setStatus(211);

		// 设置响应状态码
		// servletResponse.sendError(888, "自定义相应状态码，不是很常用");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", 211);
		map.put("msg", "servletResponse set error status 211");
		// 设置响应实体
		PrintWriter writer = servletResponse.getWriter();
		writer.write(JsonMapper.toJsonString(map));
		writer.close();

	}
	
	@RequestMapping("/test")
	//@ResponseStatus(reason="ok",code=HttpStatus.BAD_REQUEST)
	@ResponseStatus(value = HttpStatus.FORBIDDEN,reason="ok")
	public String test() {
	    return "test";
	}
	
	@RequestMapping(value = "/threadLocal/getPostRequestParams",method = RequestMethod.POST)
    public void getPostRequestParams() {
        String params = ThreadCache.getPostRequestParams();
        logger.info("controller-post请求参数:[params={}]", params);
    }
	
	@RequestMapping(value = "/wrapped/getPostRequestParams",method = RequestMethod.POST)
	//public void getPostRequestParams(@RequestBody String params) {
    public void getPostRequestParams(HttpServletRequest request) throws Exception{
        byte[] bytes = IOUtils.toByteArray(request.getInputStream());
        String params = new String(bytes, request.getCharacterEncoding());
        logger.info("controller-post请求参数:[params={}]", params);
    }
	
	@RequestMapping(value = "/postStrParam",method = RequestMethod.POST)
	public String postStrParam(@RequestBody String param) {
		logger.info("post请求参数:[param={}]", param);
		return param;
	}
	
}
