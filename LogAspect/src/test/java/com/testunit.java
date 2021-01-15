package com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.component.AsyncTask;
import com.common.component.MyRunnable;
import com.common.component.NoAsyncTask;
import com.common.gateway.RestClient;
import com.common.memcached.MemcachedCacheManager;
import com.common.solr.SolrClient;
import com.config.BeanConfig;
import com.dao.TB;
import com.entity.Book;
import com.entity.User;
import com.entry.xml.Address;
import com.entry.xml.Person;
import com.entry.xml.XmlElment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.license.LicenseCreator;
import com.license.LicenseCreatorParam;
import com.service.BookService;
import com.service.CustomerService;
import com.service.EhCacheTestService;
import com.service.IAutoInject;
import com.service.MemCacheTestService;
import com.service.SysLogService;
import com.service.UserService;
import com.service.impl.MemCacheTestServiceImpl;
import com.service.impl.a.AutoInject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.util.DateJsonSerializer;
import com.util.JsonMapper;
import com.util.JsonUtil;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

//@Transactional
//@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(SpringRunner.class)
@WebAppConfiguration // 整合springfox-swagger2后需加@WebAppConfiguration注解
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
//@ContextConfiguration({"file:src/main/java/applicationContext.xml"})
public class testunit {

	@Autowired
	// @Qualifier("AutoInjectA")
	private AutoInject autoInjecta;
	@Autowired
	// @Qualifier("AutoInjectB")
	private com.service.impl.b.AutoInject autoInjectb;
	@Autowired
	@Qualifier("AutoInjectB")
	private IAutoInject inj;

	// @JsonDeserialize(using= DateJsonDeserializer.class)
	@JsonSerialize(using = DateJsonSerializer.class)
	private Date time;

	@Autowired
	// @Qualifier("redisTemplate")
	private StringRedisTemplate stringRedisTemplate; // 使用RedisTemplate操作redis

	@Autowired
	private MyRunnable threadRunnable;

	@Test
	public void testthread() throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(10);
		for (int i = 0; i < 11; i++) {
			threadRunnable.executeThread("架构师成长之路", latch);
		}
		latch.await();
		System.out.println("执行完毕了吗！");
	}

	private static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

	@Test
	public void testJsonAnnotation() throws JsonMappingException, JsonProcessingException {
		String str = "[{\"name\":\"jack\",\"age\":18},{\"name\":\"tom\",\"age\":20}]";
		String ob = "{\"name\":\"jack\",\"age\":18,\"data\":{\"name\":\"tom\",\"age\":10,\"sex\":\"Man\",\"newUserData\":{\"weight\":\"50KG\",\"height\":\"170CM\"}}}";
		String agenullobj = "{\"name\":\"jack\",\"age\":null,\"data\":{\"name\":\"tom\",\"age\":10,\"sex\":\"Man\",\"newUserData\":{\"weight\":\"50KG\",\"height\":\"170CM\"}}}";
		String agenull = "{\"name\":\"jack\",\"age\":\"\",\"data\":{\"name\":\"tom\",\"age\":10,\"sex\":\"Man\",\"newUserData\":{\"weight\":\"50KG\",\"height\":\"170CM\"}}}";
		String ob1 = "{\"name\":\"jack\",\"data\":{\"name\":\"tom\",\"age\":10,\"sex\":\"Man\",\"newUserData\":{\"weight\":\"50KG\",\"height\":\"170CM\"}}}";
		ObjectMapper mapper1 = new ObjectMapper();
		List<JsonDTO> jsonDTOs = mapper1.readValue(str, new TypeReference<List<JsonDTO>>() {
		});

		// 1.实体上

		// @JsonInclude(value = Include.NON_NULL)

		// 将该标记放在属性上，如果该属性为NULL则不参与序列化
		// 如果放在类上边,那对这个类的全部属性起作用
		// Include.Include.ALWAYS 默认
		// Include.NON_DEFAULT 属性为默认值不序列化
		// Include.NON_EMPTY 属性为 空（“”） 或者为 NULL 都不序列化
		// Include.NON_NULL 属性为NULL 不序列化

		JsonDTO j = mapper1.readValue(ob, JsonDTO.class);
		System.out.println(JsonMapper.toJsonString(j));
		JavaType javaType = JsonMapper.getInstance().createCollectionType(List.class, JsonDTO.class);
		// JavaType jType = JsonMapper.getInstance().createCollectionType(JsonDTO.class,
		// NewUser.class);
		JavaType jType = JsonMapper.getInstance().createCollectionType(JsonDTO.class, NewUser.class);
		List<JsonDTO> jsonDTO = JsonMapper.getInstance().fromJson(str, javaType);
		JsonDTO<NewUser> jDTO = JsonMapper.getInstance().fromJson(ob, jType);
		// JsonDTO<NewUser> jn = JsonMapper.getInstance().fromJson(ob, JsonDTO.class);
		JsonDTO jn = JsonMapper.getInstance().fromJson(ob, JsonDTO.class);
		JsonDTO jn1 = JsonMapper.getInstance().fromJson(ob1, JsonDTO.class);
		JsonDTO jnagenullobj = JsonMapper.getInstance().fromJson(agenullobj, JsonDTO.class);
		System.out.println("age:" + jn1.getAge());// null
		System.out.println("agenull序列返回\"\":" + jnagenullobj.getAge());// agenullobj 中age:null 返回 null, agenull中age:""
																		// 返回无内容
		System.out.println("agenull序列返回\"\":" + JsonMapper.toJsonString(jnagenullobj));// agenull序列返回"":{"name":"jack","age":"","data":{"name":"tom","age":10,"sex":"Man","newUserData":{"weight":"50KG","height":"170CM"}}}
		JsonInDTO jsonInDTO = JsonMapper.getInstance().fromJson(ob, JsonInDTO.class);
		// mapperFactory.classMap(JsonInDTO.class, JsonOutDTO.class).byDefault();
		mapperFactory.classMap(JsonInDTO.class, JsonOutDTO.class).field("name", "name").field("age", "age")
				.field("data", "newUser")// 或者单个设置如下面四个field
//		.field("data.name", "newUser.name")
//	    .field("data.sex", "newUser.sex")
//	    .field("data.age", "newUser.age")
//	    .field("data.newUserData", "newUser.newUserData")
				.register();
		;
		MapperFacade mapperFacade = mapperFactory.getMapperFacade();

		JsonOutDTO newDto = mapperFacade.map(jsonInDTO, JsonOutDTO.class);
		System.out.println("newDto:" + JsonMapper.toJsonString(newDto));
		System.out.println("jn:" + JsonMapper.toJsonString(jn));
		System.out.println(JsonMapper.toJsonString(jDTO));
		System.out.println(JsonMapper.toJsonString(jsonDTO));
		System.out.println(JsonMapper.toJsonString(jsonDTOs));

		JsonDTO<NewUser> json = new JsonDTO<NewUser>();
		json.setAge("20");
		json.setName("Zhang");

		NewUser data = new NewUser();
		data.setAge(15);
		data.setName("Li");
		data.setSex("F");
		json.setData(data);
		System.out.println(JsonMapper.toJsonString(json));
		System.out.println(javaType.hasRawClass(List.class));// true
		String s = "{\"id\": 1,\"name\": \"小明\",\"array\": [\"1\", \"2\"]}";
		ObjectMapper mapper = new ObjectMapper();
		// Json映射为对象
		Student student = mapper.readValue(s, Student.class);
		// 对象转化为Json
		String jsonstr = mapper.writeValueAsString(student);
		System.out.println(jsonstr);
		System.out.println(student.toString());
	}

	@Test
	public void testRedisSerializer() {
		User u = new User();
		u.setName("java");
		u.setId(132);
		redisTemplate.opsForHash().put("user:", "1", u);
		/* 查看redisTemplate 的Serializer */
		System.out.println(redisTemplate.getKeySerializer());
		System.out.println(redisTemplate.getValueSerializer());

		/* 查看StringRedisTemplate 的Serializer */
		System.out.println(stringRedisTemplate.getValueSerializer());
		System.out.println(stringRedisTemplate.getValueSerializer());

		/* 将stringRedisTemplate序列化类设置成RedisTemplate的序列化类 */
		stringRedisTemplate.setKeySerializer(new JdkSerializationRedisSerializer());
		stringRedisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());

		stringRedisTemplate.opsForHash().put("user:", "1", JsonMapper.toJsonString(u));// 只能处理String

		/*
		 * 即使在更换stringRedisTemplate的的Serializer和redisTemplate一致的
		 * JdkSerializationRedisSerializer 最后还是无法从redis中获取序列化的数据
		 */
		System.out.println(stringRedisTemplate.getValueSerializer());
		System.out.println(stringRedisTemplate.getValueSerializer());

		User user = (User) redisTemplate.opsForHash().get("user:", "1");
		// User user2 = (User) stringRedisTemplate.opsForHash().get("user:","1");
		String userstr = (String) stringRedisTemplate.opsForHash().get("user:", "1");
		System.out.println("dsd");
		System.out.println(JsonMapper.toJsonString(user));
		System.out.println("userstr:" + userstr);
		// System.out.println("user2:"+JsonMapper.toJsonString(user2));//空
	}

	@Test
	public void setStrRedis() {
		// 给String类型赋值
		// key
		String key = "name";
		// 值
		String value = "redis";
		stringRedisTemplate.opsForValue().set(key, value);
	}

	@Test
	public void getStrRedis() {
		// 查询String类型key的value
		String key = "name";
		System.out.println(stringRedisTemplate.opsForValue().get(key));
	}

	@Test
	public void expire() {
		// 设置key的过期时间
		String key = "name1";
		long time = 100;
		stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
	}

	@Test
	public void del() {
		// 删除
		String key = "name";
		stringRedisTemplate.delete(key);
	}

	@Test
	public void testJsonProperty() throws IOException {
		NewUser user = new NewUser("shiyu", "man", 22);
		System.out.println(new ObjectMapper().writeValueAsString(user));
		String str = "{\"sex\":\"man\",\"age\":22,\"JsonPropertyName\":\"shiyu\"}";
		user = new ObjectMapper().readValue(str, NewUser.class);
		System.out.println(JsonMapper.toJsonString(user));
		System.out.println(new ObjectMapper().readValue(str, NewUser.class).toString());
	}

	@Test
	public void jsonTest() {
		HeaderDTO headerDTO = new HeaderDTO();
		headerDTO.setTxn_Rsp_cd_Dsc("000000");
		headerDTO.setTxn_Rsp_Inf("成功");

		DataDTO dataDTO = new DataDTO();
		dataDTO.setScrtData("asdfadf");
		dataDTO.setScrtSgn("34lkdflk2j3l4jl");

		SignDTO signDTO = new SignDTO();
		signDTO.setHead(headerDTO);
		signDTO.setData(dataDTO);

		System.out.println(JSONObject.toJSONString(signDTO));
		ObjectMapper mapper = new ObjectMapper();
		try {
			String s = mapper.writeValueAsString(signDTO);

			System.out.println(mapper.writeValueAsString(signDTO));
			signDTO = (SignDTO) JsonMapper.fromJsonString(s, SignDTO.class);
			System.out.println("mapper:" + mapper.writeValueAsString(signDTO));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDate() {
		time = new Date();
		System.out.println("当前时间:");
		System.out.println(time);
		System.out.println();
		DataEntity dataEntity = new DataEntity();
		dataEntity.setDate(time);
		System.out.println(JSONObject.toJSONString(dataEntity));
		// 从1970年1月1日 早上8点0分0秒 开始经历的毫秒数
		Date d2 = new Date(5000);
		System.out.println("从1970年1月1日 早上8点0分0秒 开始经历了5秒的时间");
		System.out.println(d2);
	}

	@Test
	public void test() {
		autoInjecta.print();
		// autoInjectb.print();
		// System.out.println("aaat:"+autoInjecta.print());
		System.out.println("aaat:" + inj.print());
		// System.out.println("aaat:"+autoInjecta.print()+autoInjectb.print());
	}

	@Autowired
	// @Qualifier("redisTemplate")
	private RedisTemplate<String, Object> redisTemplate; // 使用RedisTemplate操作redis

	@Test
	public void t() throws InterruptedException {
		// System.out.println("ttttt");
//		MyThead myThead = new MyThead();
//		Thread thread = new Thread(myThead);
//		thread.start();
		int i = 0;
		while (i < 5) {
			i = i + 1;
			f();
			Thread.sleep(1000);
		}
	}

	public void f() {
		String key = "ut";
		int sec = 10;
		String value = (String) redisTemplate.opsForValue().get(key);
		long expire = redisTemplate.getExpire(key);
		System.out.println("" + value);
		if (value == null) {
			redisTemplate.opsForValue().set(key, "firstvalue", sec, TimeUnit.SECONDS); // set时一定要加过期时间
			System.out.println(redisTemplate.opsForValue().get(key));
		} else if (expire > 8) {
			redisTemplate.opsForValue().set(key, "second", 5, TimeUnit.SECONDS);
		} else {
			redisTemplate.delete(key);
		}
	}

	@Autowired
	private SysLogService sysLogService;

	@Test // get myECache_12322
	public void testcache() throws InterruptedException {
		// sysLogService.selectSysLog("123");

		System.out.println(sysLogService.selectSysLog("12322").getId());
		Thread.sleep(3000);
		System.out.println(sysLogService.selectSysLog("12322").getId());
		Thread.sleep(11000);
		System.out.println(sysLogService.selectSysLog("12322").getId());
	}

	@Test
	public void tc() throws InterruptedException {
		// sysLogService.selectSysLog("123");

		System.out.println("0s count:" + sysLogService.count());
		Thread.sleep(3000);
		System.out.println("after 3s count:" + sysLogService.count());
		Thread.sleep(11000);
		System.out.println("after 11s count:" + sysLogService.count());
	}

	@Autowired
	private EhCacheTestService ehCacheTestService;

	@Test
	public void getTimestampTest() throws InterruptedException {
		System.out.println("ehcache第一次调用 key t：" + ehCacheTestService.getTimestamp("t"));
		Thread.sleep(2000);
		System.out.println("ehcache2秒之后调用 key t：" + ehCacheTestService.getTimestamp("t"));
		Thread.sleep(11000);
		System.out.println("ehcache再过11秒之后调用 key t：" + ehCacheTestService.getTimestamp("t"));
	}

	@Test // get myECache_inTimeCache
	public void getcacheTest() throws InterruptedException {
		System.out.println("ehcache第一次调用 key inTimeCache：" + ehCacheTestService.getTimestamp("inTimeCache"));
		Thread.sleep(2000);
		System.out.println("ehcache2秒之后调用 key inTimeCache：" + ehCacheTestService.getTimestamp("inTimeCache"));
		Thread.sleep(11000);
		System.out.println("ehcache再过11秒之后调用 key inTimeCache：" + ehCacheTestService.getTimestamp("inTimeCache"));
	}

	@Test // get myECache_inTimeCache stats items列出所有keys stats cachedump 7 0 列出的items
			// id，本例中为7，第2个参数为列出的长度，0为全部列出
	public void clearcacheTest() throws InterruptedException {
		System.out.println("mC_t：" + memCacheTestService.getTimestamp("t"));
		System.out.println("mC_st：" + memCacheTestService.getTimestamp("st"));
		System.out.println("myECache_t：" + ehCacheTestService.getTimestamp("t"));
		Thread.sleep(2000);
		System.out.println("2秒后  mC_t：" + memCacheTestService.getTimestamp("t"));
		System.out.println("2秒后  update mC_t：" + memCacheTestService.updateStr("t"));
		System.out.println("2秒后  myECache_t：" + ehCacheTestService.getTimestamp("t"));
		memCacheTestService.clearAll();
		Thread.sleep(5000);
		System.out.println("5 mC_t：" + memCacheTestService.getTimestamp("t"));
		System.out.println("5 mC_st：" + memCacheTestService.getTimestamp("st"));
		System.out.println("5 myECache_t：" + ehCacheTestService.getTimestamp("t"));
	}

	@Test // get myECache_inTimeCache stats items列出所有keys stats cachedump 7 0 列出的items
			// id，本例中为7，第2个参数为列出的长度，0为全部列出
	public void clearcache() throws InterruptedException {
		memCacheTestService.clearAll();
		System.out.println("5 mC_t：" + memCacheTestService.getTimestamp("t"));
		System.out.println("5 mC_st：" + memCacheTestService.getTimestamp("st"));
		System.out.println("5 myECache_t：" + ehCacheTestService.getTimestamp("t"));
		List<String> listKey = new ArrayList<String>();
		listKey.add("t");
		listKey.add("st");
		for (String s : listKey) {
			memCacheTestService.deleteOne(s);
		}

		System.out.println("5 mC_t：" + memCacheTestService.getTimestamp("t"));
		System.out.println("5 mC_st：" + memCacheTestService.getTimestamp("st"));
	}

	@Autowired
	private MemCacheTestService memCacheTestService;
	@Autowired
	private MemCacheTestServiceImpl memCacheTestServiceImpl;

	@Test
	public void getMemTimestampTest() throws InterruptedException {
		// System.out.println("第一次调用：" + memCacheTestServiceImpl.getTimestamp("param"));
		System.out.println("m第一次调用：" + memCacheTestService.getTimestamp("t"));
		Thread.sleep(2000);
		System.out.println("m2秒之后调用：" + memCacheTestService.getTimestamp("t"));
		Thread.sleep(11000);
		System.out.println("m再过11秒之后调用：" + memCacheTestService.getTimestamp("t"));
	}

	@Test
	public void mCache() throws InterruptedException {
		// System.out.println("第一次调用：" + memCacheTestServiceImpl.getTimestamp("param"));
		System.out.println("m第一次调用：" + memCacheTestService.mCache());
		Thread.sleep(2000);
		System.out.println("m2秒之后调用：" + memCacheTestService.mCache());
		Thread.sleep(11000);
		System.out.println("m再过11秒之后调用：" + memCacheTestService.mCache());
	}

	@Test
	public void mCacheUpdate() throws InterruptedException {
		// System.out.println("第一次调用：" + memCacheTestServiceImpl.getTimestamp("param"));
		System.out.println("m第一次调用：" + memCacheTestService.mCacheupdate());
		User u3 = new User();
		u3.setId(222);
		u3.setName("vv");
		u3.setPassword("asdf1234");
		String s = JsonUtil.beanToJson(u3);
		// System.out.println("m第0次调用：" + memCacheTestService.cacheJsonStr(u3.getId(),
		// s));
		// Thread.sleep(2000);
		// System.out.println("m2秒之后调用：" + memCacheTestService.mCache());
		// System.out.println("cacheStr调用：" +
		// memCacheTestService.cacheStr("1","mCache_1"));
		System.out.println("cacheNoKey调用：" + memCacheTestService.cacheNoKey());
		System.out.println("cacheKey调用：" + memCacheTestService.cacheKey("cache key"));

		Thread.sleep(11000);
		System.out.println("cacheKey11秒之后调用：" + memCacheTestService.cacheKey("cache key"));
		System.out.println("cacheNoKey 11秒之后调用：" + memCacheTestService.cacheNoKey());

		// System.out.println("m再过11秒之后调用：" + memCacheTestService.mCache());
	}

	@Autowired
	private MemcachedCacheManager memcachedCacheManager;
	@Autowired
	private MemcachedClient memcachedClient;

	@Test
	public void mCacheDel() throws InterruptedException {
		System.out.println("第一次调用：" + memCacheTestServiceImpl.getTimestamp("time"));
		System.out.println("第一次调用mCapi：" + memCacheTestServiceImpl.gettime("t"));
		System.out.println("m第一次调用：" + memCacheTestService.mCache());
		Thread.sleep(2000);
		memCacheTestService.mCacheDel();
		memcachedCacheManager.getCache("mC").put("t", "asd");
		System.out.println(memcachedCacheManager.getCache("mC").get("t", String.class));
		try {
			// memcachedClient.flushAll();//清除所有；
			memCacheTestService.delMutilKey();
			System.out.println("调用delMutilKey 后mCapi：" + memCacheTestServiceImpl.gettime("t"));
			System.out.println("调用delMutilKey：" + memCacheTestService.mCache());
			memcachedCacheManager.getCache("mC").put("test", "test cache");
			System.out.println(memcachedCacheManager.getCache("mC").get("test", String.class));
			memcachedClient.delete("mC_test");
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("mC_time:" + memcachedCacheManager.getCache("mC").get("time", String.class));
		System.out.println(memcachedCacheManager.getCache("mC").get("test", String.class));
		System.out.println("del之后调用：" + memCacheTestService.mCache());

		System.out.println(memcachedCacheManager.getCache("mC").get("t", String.class));
		System.out.println("第2次调用：" + memCacheTestServiceImpl.getTimestamp("time"));
	}

	@Test
	public void mc() {

		System.out.println(memcachedCacheManager.getCache("mC").get("t", String.class));
	}

	@Test
	public void mCacheUser() throws InterruptedException {
		// System.out.println("第一次调用：" + memCacheTestServiceImpl.getTimestamp("param"));
		User u = new User();
		u.setId(123);
		u.setName("jack");
		u.setPassword("123445");
		User u2 = new User();
		u2.setId(666);
		u2.setName("vin");
		u2.setPassword("888888");
		memCacheTestService.cacheStr("sk", "string cache with sk");
		System.out.println(memCacheTestService.getCacheStr("sk"));
		System.out.println("m第0次调用：" + JsonUtil.beanToJson(memCacheTestService.cacheUser(u)));
		System.out.println("m第0次调用：" + JsonUtil.beanToJson(memCacheTestService.cache(u2)));
//		System.out.println("m第00次调用：" + JsonUtil.beanToJson(memCacheTestService.cache(u2)));
		u.setId(333);
		u.setName("lili");
		u.setPassword("111111");
		String s = JsonUtil.beanToJson(u);
		System.out.println("json第00次调用：" + JsonUtil.beanToJson(memCacheTestService.cacheJson(u.getId(), s)));
		System.out.println("m第11次调用：" + JsonUtil.beanToJson(memCacheTestService.cacheJson(u.getId(), s)));
		User u3 = new User();
		u3.setId(222);
		u3.setName("vv");
		u3.setPassword("asdf1234");
		s = JsonUtil.beanToJson(u3);
		System.out.println("m第0次调用：" + memCacheTestService.cacheJsonStr(u3.getId(), s));
		String str = memCacheTestService.cacheJsonStr(u3.getId(), s);
		User user = (User) JsonMapper.fromJsonString(str, User.class);
		System.out.println("json str to bean:" + JsonUtil.beanToJson(user));
		System.out.println("m第0次调用：" + JsonUtil.beanToJson(memCacheTestService.cacheUser(u)));
		u.setId(123);
		u.setName("jack123123");
		u.setPassword("123445");
		System.out.println("m第1次调用：" + JsonUtil.beanToJson(memCacheTestService.cacheUser(u)));
		System.out.println("m第2次调用：" + JsonUtil.beanToJson(memCacheTestService.getcacheUser(123)));
		Thread.sleep(5000);
		System.out.println("m第3次调用：" + JsonUtil.beanToJson(memCacheTestService.getcacheUser(123)));
		u.setId(111);
		u.setName("tom");
		u.setPassword("453534");
		memCacheTestService.cacheUser(u);
		System.out.println("m第4次调用：" + JsonUtil.beanToJson(memCacheTestService.getcacheUser(111)));
		Thread.sleep(1000);
		System.out.println("m第5次调用：" + JsonUtil.beanToJson(memCacheTestService.getcacheUser(111)));
		u.setId(111);
		u.setName("tom");
		u.setPassword("000000");
		memCacheTestService.cacheUpdateUser(u);
		Thread.sleep(1000);

		System.out.println("m第6次update调用：" + JsonUtil.beanToJson(memCacheTestService.getcacheUser(111)));
		Thread.sleep(1000);
		System.out.println("m第7次调用：" + JsonUtil.beanToJson(memCacheTestService.getcacheUser(123)));
		System.out.println("m第8次调用：" + JsonUtil.beanToJson(memCacheTestService.getcacheUser(111)));
		Thread.sleep(11000);
		System.out.println("10秒过期后：" + JsonUtil.beanToJson(memCacheTestService.getcacheUser(123)));
		System.out.println("10秒过期后：" + JsonUtil.beanToJson(memCacheTestService.getcacheUser(111)));
	}

	@Autowired
	@Qualifier("customerServiceProxy")
	private CustomerService customerService;

	@Test
	public void aopMethod() {
		System.out.println("使用Spring AOP 如下");
		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("spring-aop.xml");
		// CustomerService cust = (CustomerService)
		// context.getBean("customerServiceProxy");
		System.out.println("*************************");

		customerService.printName();
		System.out.println("*************************");
		customerService.printURL();
		System.out.println("*************************");
//        ApplicationContext context = new ClassPathXmlApplicationContext("spring-aop.xml");  
//        
//        
//        CustomerService cust = (CustomerService) context.getBean("customerServiceProxy");
//        System.out.println("*************************");
//        cust.printName();
//        System.out.println("*************************");
//        cust.printURL();
//        System.out.println("*************************");

		try {
			customerService.printThrowException();
		} catch (Exception e) {

		}
	}

	@Autowired
	private UserService userService;

	@Test
	public void testPostConstruct() {
		// System.out.println(userService.addUser("aaa", "bbb"));

		TB tb = new TB();

		// tb.getUser("jack");
		System.out.println(JSON.toJSON(userService.getUserB(tb)).toString());
		// userService.s();
	}

	@SuppressWarnings("resource")
	@Test
	public void testBeanConfig() {
		ApplicationContext annotationContext = new AnnotationConfigApplicationContext(BeanConfig.class);
		SolrClient bc = annotationContext.getBean("solrclient", SolrClient.class);
		System.out.println("AnnotationConfigApplicationContext:" + bc.getUrl());

//        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");// 读取bean.xml中的内容
////        SolrClient c = ctx.getBean("client", SolrClient.class);
//        SolrClient c = (SolrClient)ctx.getBean("sClient");
//        System.out.println("ClassPathXmlApplicationContext:"+c.getUrl());

		AnnotationConfigApplicationContext ct = new AnnotationConfigApplicationContext(BeanConfig.class);
		SolrClient sc = (SolrClient) ct.getBean("client");
		System.out.println(sc.getUrl());

		ct.close();
	}

	@Autowired
	private BookService bs;

	@Test
	public void DroolsTest() {
		Book b = new Book();
		b.setBasePrice(120.50);
		b.setClz("computer");
		b.setName("C plus programing");
		b.setSalesArea("China");
		b.setYears(2);
		Book b2 = new Book();
		b2.setBasePrice(10.50);
		b2.setClz("Chinese");
		b2.setName("语文");
		b2.setSalesArea("China");
		b2.setYears(3);

		double price = bs.getBookSalePrice(b2);
		System.out.println(b2.getName() + " : " + price);

//		KieServices ks = KieServices.Factory.get();  
//	    KieContainer kContainer = ks.getKieClasspathContainer("bookprice_KB");  
//	    KieSession kSession = kContainer.newKieSession("bookprice_KS");  
//		
//		kSession.insert(b);
//		kSession.fireAllRules();
//		double realPrice = b.getSalesPrice();
//     System.out.println(b.getName() + ":" + realPrice);
	}

//    @Test
//    public void BeanConfig(@Qualifier("solrclient") SolrClient sc) {
//    	System.out.println(sc.getUrl());
//    }
//    @Test
//    @Validated
//	public void testvalidate(){
//		Order order = new Order();
//		OrderItem item = new OrderItem();
//		OrderItem item1 = null;
//		order.setOrderNo(null);
//        item.setProductName("productName");
//        item.setProductCode("1000001");
//        item.setPrice(new BigDecimal("8765.45"));
//		order.setOrderItem(item1);
//		System.out.println(JSON.toJSONString(order));
//	}

	@Autowired
	private AsyncTask task;

	@Test
	public void testAsync() throws Exception {

//        for (int i = 0; i < 10000; i++) {
//            task.doTaskOne();
//            task.doTaskTwo();
//            task.doTaskThree();
// 
//            if (i == 9999) {
//                System.exit(0);
//            }
//        }
		for (int i = 0; i < 10; i++) {
			task.doTaskOne();
			task.doTaskTwo();
			task.doTaskThree();

			if (i == 10) {
				System.exit(0);
			}
		}
	}

	@Autowired
	private NoAsyncTask noTask;

	@Test
	public void testnoAsync() {
		noTask.noAsyncTask("no Task Test");
	}

	@Test
	public void restTemplateWithCookie() {
		HttpHeaders headers = new HttpHeaders();
		List<String> cookies = new ArrayList<String>();
		cookies.add(
				"b2b_auth=3b16AHu5Fhx0oT29a%2FB8OuF1yItVPmBVjDH9bPbR5FKGZY%2FJBiUq1D7Ih4OL4DTYeO4dgkNF4iZD6D59PiVRpzj2R5oPgDjeDD6KmVAh1QeQpaumiQpo854LcVm6niBcHPMMUNZlMxVGB4K%2FRSbBFZW9A68B7cC2P41OrvaftE3cLXucSdZv3iSUM4g7QZCwJw3nEX9NNyvuwSEroLUTDekSrfFdRhmfoHhNRcnaRD%2FY%2Fe38MPOpFKS2aFBdtNrhzkwRxPuOO6MoFx2afsTYTUcLgxIpZBsV9YtThcOLZyMy5LSl%2Brt2wnGB%2FDmrZegO9AvYrfgId8gkrifpCpeAUcqbZkoTgFaHVhyOdQSWcvsCvOr78F1seiGRkdPbwJ7NQBpE2c%2BCL0EGToa%2Fd%2FJlOaQXxVk1pk%2B90GdyZEGxn%2BUJnFJ1sP0");
		cookies.add(
				"cpc_auth=c60dim5r%2BOBcdDCwOlEk4qOTHjG7onLJordyFupqOXEKbkp7qeWvJVI6wO8MkZK0q3tvUhdP9UIHHFgIAcVZ31%2BVZrUqb2143SL3sisdJEnV8xnKmCsgEx73U6yo6NhyBqG9QGyhPxORiEAp0e6ryM9FAhvSzKqwRX1eJRgzUVI%2BX9iI05%2Fz21%2FP0G2vPtCXmYRbmSMFDOkr4sePC2EVRd9o7%2FS6H80%2F2FhIpRFQthwwTkis4ciGGKtozEt%2BA1A39mzVeoiWymY%2FJQvJ49sfmvY243Tx5KfoqtiVFRINSSB%2BJbzNAEBL4GGCjleDUFRfeIZQuxGCBATWPAlq1VA0fbZgzgeEaU3FvgYnKhTpEwqc3622tzm7AyeU2MX6qFouEcqNB5wZmCznB7fuSWw9jI2KDYkXL4mnhuE1%2Bxr4XHgFKQccorR4TVbHaEFJH9pIfqdZnd0sUZk76VRtDaYgIAA7BclnDBLAl3EsX3uj9XfBbqT%2FSbLvS8EwtUc49Z3e1LWamhMsGVw7lxbMwd%2BkVyzC0vXk1kcgyU48qWjlyA8vjfrNzYAiJ9NFNX%2BFQa%2BhI8p%2FkYLwfM2%2Bg%2BoU4JnyltYT5q3mvQ2qU5%2F3WLtu6b%2B0omeoSNSQkS29WiHZrCtpAwdT5RkPYvi7%2F5WurzsbNE8izAL%2BP3W%2F%2BokjFOiytyY2KTupZuJ9LGdAxsSqIXKxm%2BqYg0zglNPxbTIquAPRMlIsEDAmwP5yZ3fg7DEaXl4mhlUWRXvq6igMWtaR%2Brd8G34S95xf5nwkTzsi2JasYkTq6H2qMbcjrSdJ4%2F6NGUrBLgRoTxX1MPGwaWPIxQhw0alDmLgpwG1jrEtrXpCX3Gl%2Byfe593y2n%2FbXn6JArNHukPakZ2Lnz9qBaxfO01DBKVFwP3Ol0WuVdoxWQTXE5vFiRd6ha%2BSGAMOxd%2B%2BXg9H96KD6Uk73hTkpWXI23sxrNNYkqUL00Oak4QtftWLrnbfr%2FwpNBZhvVgf5xzNpcoHfHbHYldffrOSmdgUYUVqo5QxcJxd8jet2yf8x7pIrR96a2qkOHk0%2FQsCZdO99rdpese0K9yOt5tVrF9UD4vC9SHucpo4hCeSs7vRiRtJgzvzcjDH3uh5lp4VOx%2FJsXwLrZ5KO9De3esGWsiKbgAO7N5cXLTVTdFpO2mA0B0%2BifN4p8AhLOOebwPio6Mcg6aYiA2wEMR9sMrel4MrIun4%2F4OboyJ9nxJLZd4wC4dA%2Fa4KZ9xTnk25hFrcV9ZI3tpsPhxdQS0o");
		headers.put(HttpHeaders.COOKIE, cookies);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		String url = "http://***.***.com/***/api/***/buy/cart/count";
		// ResponseEntity<String> response = RestClient.getClient().postForEntity(url,
		// request, String.class);
		ResponseEntity<String> response = RestClient.getClient().exchange(url, HttpMethod.GET, request, String.class);
		System.out.println(response.getBody());
	}

	@Test
	public void licenseCreate() {
		// 生成license需要的一些参数
		LicenseCreatorParam param = new LicenseCreatorParam();
		param.setSubject("ioserver");
		param.setPrivateAlias("privatekey");
		param.setKeyPass("123456");
		param.setStorePass("123456");
		param.setLicensePath("/Users/vinlam/license.lic");
		param.setPrivateKeysStorePath(
				"/Users/vinlam/work/gitproject/springdemo/LogAspect/src/main/java/privateKeys.keystore");
		Calendar issueCalendar = Calendar.getInstance();
		param.setIssuedTime(issueCalendar.getTime());
		Calendar expiryCalendar = Calendar.getInstance();
		expiryCalendar.set(2020, Calendar.DECEMBER, 31, 23, 59, 59);
		param.setExpiryTime(expiryCalendar.getTime());
		param.setConsumerType("user");
		param.setConsumerAmount(1);
		param.setDescription("测试");
		LicenseCreator licenseCreator = new LicenseCreator(param);
		// 生成license
		licenseCreator.generateLicense();
	}

	@Autowired
	private TypeStrategyContext typeStrategy;

	@Test
	public void testStrategy() {
		try {
			String[] types = { "pc1", "mobile" };
			// types = null;
			String key = "TypePc";// TypeMobile
			String res = typeStrategy.getRes(types, key);
			System.out.println(res);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testxmltobean() {
		// 定义构造函数使用'XppDriver'防止双下划线问题。
		XStream xStream = new XStream(new XppDriver(new XmlFriendlyReplacer("_-", "_")));
		xStream = new XStream(new StaxDriver());
		// xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyReplacer("_-",
		// "_"))) ;
		xStream = new XStream(new Xpp3Driver(new NoNameCoder()));
		xStream.autodetectAnnotations(true);// 自动注解扫描，否则注解不生效

		// 构造JavaBean
		Person p = new Person("1", new XmlElment("中文名", "汤姆"), "Male", 18, new Address("China", "FuJian", "XiaMen"));
		System.out.println("实体信息：" + p);

		// JavaBean -》 xml
		String xml = xStream.toXML(p);
		System.out.println("xml内容：");
		System.out.println(xml);

		// xml -》 JavaBean
		Person p2 = (Person) xStream.fromXML(xml);
		System.out.println(p2.getAddress());
		System.out.println(JsonMapper.getInstance().toJson(p2));

	}

	@Test
	public void with_unspecified_arguments() {
		List list = Mockito.mock(List.class);
		// 匹配任意参数
		/*
		 * Mockito.anyInt() 任何 int 值 
		 * Mockito.anyLong() 任何 long 值 
		 * Mockito.anyString() 任何 String 值 
		 * Mockito.any(XXX.class) 任何 XXX 类型的值
		 */
		System.out.println("Mockito.anyInt:"+list.get(Mockito.anyInt()));
		Mockito.when(list.get(Mockito.anyInt())).thenReturn(1);
		Mockito.when(list.contains(Mockito.argThat(new IsValid()))).thenReturn(true);
		Assert.assertEquals(1, list.get(1));
		Assert.assertEquals(1, list.get(999));
		Assert.assertTrue(list.contains(1));
		Assert.assertTrue(!list.contains(3));
	}

	@Test
	public void testAnswer1() {
		List<String> mock = Mockito.mock(List.class);
		Mockito.doAnswer(new CustomAnswer()).when(mock).get(Mockito.anyInt());
		Assert.assertEquals("大于三", mock.get(4));
		Assert.assertEquals("小于三", mock.get(2));
	}

	public class CustomAnswer implements Answer<String> {
		public String answer(InvocationOnMock invocation) throws Throwable {
			Object[] args = invocation.getArguments();
			Integer num = (Integer) args[0];
			if (num > 3) {
				return "大于三";
			} else {
				return "小于三";
			}
		}
	}

}

class IsValid implements ArgumentMatcher<List> {
	@Override
	public boolean matches(List argument) {
		// TODO Auto-generated method stub
		return false;
	}
}

class MyThead implements Runnable {
	public void run() {
		System.out.println("我休息了！");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		System.out.println("一秒后在叫我吧！");
	}
}