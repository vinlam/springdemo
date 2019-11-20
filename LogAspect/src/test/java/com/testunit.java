package com;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.component.AsyncTask;
import com.common.component.NoAsyncTask;
import com.common.gateway.RestClient;
import com.common.solr.SolrClient;
import com.config.BeanConfig;
import com.dao.TB;
import com.entity.Book;
import com.entity.Order;
import com.entity.OrderItem;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.service.BookService;
import com.service.CustomerService;
import com.service.EhCacheTestService;
import com.service.IAutoInject;
import com.service.MemCacheTestService;
import com.service.SysLogService;
import com.service.UserService;
import com.service.impl.MemCacheTestServiceImpl;
import com.service.impl.a.AutoInject;
import com.util.DateJsonDeserializer;
import com.util.DateJsonSerializer;

//@Transactional
//@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(SpringRunner.class)
@WebAppConfiguration//整合springfox-swagger2后需加@WebAppConfiguration注解
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

	//@JsonDeserialize(using= DateJsonDeserializer.class)
	@JsonSerialize(using= DateJsonSerializer.class)
	private Date time;
	
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

	@Test // get myCache_12322
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

	@Test // get myCache_inTimeCache
	public void getcacheTest() throws InterruptedException {
		System.out.println("ehcache第一次调用 key inTimeCache：" + ehCacheTestService.getTimestamp("inTimeCache"));
		Thread.sleep(2000);
		System.out.println("ehcache2秒之后调用 key inTimeCache：" + ehCacheTestService.getTimestamp("inTimeCache"));
		Thread.sleep(11000);
		System.out.println("ehcache再过11秒之后调用 key inTimeCache：" + ehCacheTestService.getTimestamp("inTimeCache"));
	}

	@Test // get myCache_inTimeCache stats items列出所有keys stats cachedump 7 0 列出的items
			// id，本例中为7，第2个参数为列出的长度，0为全部列出
	public void clearcacheTest() throws InterruptedException {
		System.out.println("mC_t：" + memCacheTestService.getTimestamp("t"));
		System.out.println("mC_st：" + memCacheTestService.getTimestamp("st"));
		System.out.println("myCache_t：" + ehCacheTestService.getTimestamp("t"));
		Thread.sleep(2000);
		System.out.println("2秒后  mC_t：" + memCacheTestService.getTimestamp("t"));
		System.out.println("2秒后  update mC_t：" + memCacheTestService.updateStr("t"));
		System.out.println("2秒后  myCache_t：" + ehCacheTestService.getTimestamp("t"));
		memCacheTestService.clearAll();
		Thread.sleep(5000);
		System.out.println("5 mC_t：" + memCacheTestService.getTimestamp("t"));
		System.out.println("5 mC_st：" + memCacheTestService.getTimestamp("st"));
		System.out.println("5 myCache_t：" + ehCacheTestService.getTimestamp("t"));
	}

	@Test // get myCache_inTimeCache stats items列出所有keys stats cachedump 7 0 列出的items
			// id，本例中为7，第2个参数为列出的长度，0为全部列出
	public void clearcache() throws InterruptedException {
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
		//Thread.sleep(2000);
		System.out.println("m2秒之后调用：" + memCacheTestService.mCache());
		Thread.sleep(11000);
		System.out.println("m再过11秒之后调用：" + memCacheTestService.mCache());
	}
	@Test
	public void mCacheDel() throws InterruptedException {
		// System.out.println("第一次调用：" + memCacheTestServiceImpl.getTimestamp("param"));
		System.out.println("m第一次调用：" + memCacheTestService.mCache());
		Thread.sleep(2000);
		memCacheTestService.mCacheDel();
		//Thread.sleep(11000);
		System.out.println("del之后调用：" + memCacheTestService.mCache());
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
    	cookies.add("b2b_auth=3b16AHu5Fhx0oT29a%2FB8OuF1yItVPmBVjDH9bPbR5FKGZY%2FJBiUq1D7Ih4OL4DTYeO4dgkNF4iZD6D59PiVRpzj2R5oPgDjeDD6KmVAh1QeQpaumiQpo854LcVm6niBcHPMMUNZlMxVGB4K%2FRSbBFZW9A68B7cC2P41OrvaftE3cLXucSdZv3iSUM4g7QZCwJw3nEX9NNyvuwSEroLUTDekSrfFdRhmfoHhNRcnaRD%2FY%2Fe38MPOpFKS2aFBdtNrhzkwRxPuOO6MoFx2afsTYTUcLgxIpZBsV9YtThcOLZyMy5LSl%2Brt2wnGB%2FDmrZegO9AvYrfgId8gkrifpCpeAUcqbZkoTgFaHVhyOdQSWcvsCvOr78F1seiGRkdPbwJ7NQBpE2c%2BCL0EGToa%2Fd%2FJlOaQXxVk1pk%2B90GdyZEGxn%2BUJnFJ1sP0");
    	cookies.add("cpc_auth=c60dim5r%2BOBcdDCwOlEk4qOTHjG7onLJordyFupqOXEKbkp7qeWvJVI6wO8MkZK0q3tvUhdP9UIHHFgIAcVZ31%2BVZrUqb2143SL3sisdJEnV8xnKmCsgEx73U6yo6NhyBqG9QGyhPxORiEAp0e6ryM9FAhvSzKqwRX1eJRgzUVI%2BX9iI05%2Fz21%2FP0G2vPtCXmYRbmSMFDOkr4sePC2EVRd9o7%2FS6H80%2F2FhIpRFQthwwTkis4ciGGKtozEt%2BA1A39mzVeoiWymY%2FJQvJ49sfmvY243Tx5KfoqtiVFRINSSB%2BJbzNAEBL4GGCjleDUFRfeIZQuxGCBATWPAlq1VA0fbZgzgeEaU3FvgYnKhTpEwqc3622tzm7AyeU2MX6qFouEcqNB5wZmCznB7fuSWw9jI2KDYkXL4mnhuE1%2Bxr4XHgFKQccorR4TVbHaEFJH9pIfqdZnd0sUZk76VRtDaYgIAA7BclnDBLAl3EsX3uj9XfBbqT%2FSbLvS8EwtUc49Z3e1LWamhMsGVw7lxbMwd%2BkVyzC0vXk1kcgyU48qWjlyA8vjfrNzYAiJ9NFNX%2BFQa%2BhI8p%2FkYLwfM2%2Bg%2BoU4JnyltYT5q3mvQ2qU5%2F3WLtu6b%2B0omeoSNSQkS29WiHZrCtpAwdT5RkPYvi7%2F5WurzsbNE8izAL%2BP3W%2F%2BokjFOiytyY2KTupZuJ9LGdAxsSqIXKxm%2BqYg0zglNPxbTIquAPRMlIsEDAmwP5yZ3fg7DEaXl4mhlUWRXvq6igMWtaR%2Brd8G34S95xf5nwkTzsi2JasYkTq6H2qMbcjrSdJ4%2F6NGUrBLgRoTxX1MPGwaWPIxQhw0alDmLgpwG1jrEtrXpCX3Gl%2Byfe593y2n%2FbXn6JArNHukPakZ2Lnz9qBaxfO01DBKVFwP3Ol0WuVdoxWQTXE5vFiRd6ha%2BSGAMOxd%2B%2BXg9H96KD6Uk73hTkpWXI23sxrNNYkqUL00Oak4QtftWLrnbfr%2FwpNBZhvVgf5xzNpcoHfHbHYldffrOSmdgUYUVqo5QxcJxd8jet2yf8x7pIrR96a2qkOHk0%2FQsCZdO99rdpese0K9yOt5tVrF9UD4vC9SHucpo4hCeSs7vRiRtJgzvzcjDH3uh5lp4VOx%2FJsXwLrZ5KO9De3esGWsiKbgAO7N5cXLTVTdFpO2mA0B0%2BifN4p8AhLOOebwPio6Mcg6aYiA2wEMR9sMrel4MrIun4%2F4OboyJ9nxJLZd4wC4dA%2Fa4KZ9xTnk25hFrcV9ZI3tpsPhxdQS0o");
    	headers.put(HttpHeaders.COOKIE, cookies);
    	MultiValueMap<String,String> map = new LinkedMultiValueMap<String,String>();
    	
    	HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<MultiValueMap<String,String>>(map,headers);
    	String url = "http://***.***.com/***/api/***/buy/cart/count";
    	//ResponseEntity<String> response = RestClient.getClient().postForEntity(url, request, String.class);
    	ResponseEntity<String> response = RestClient.getClient().exchange(url, HttpMethod.GET, request, String.class);
    	System.out.println(response.getBody());
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