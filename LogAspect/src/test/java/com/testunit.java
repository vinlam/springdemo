package com;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.annotation.Validated;

import com.alibaba.fastjson.JSON;
import com.common.solr.SolrClient;
import com.config.BeanConfig;
import com.dao.TB;
import com.entity.Book;
import com.entity.Order;
import com.entity.OrderItem;
import com.service.BookService;
import com.service.CustomerService;
import com.service.EhCacheTestService;
import com.service.IAutoInject;
import com.service.MemCacheTestService;
import com.service.SysLogService;
import com.service.UserService;
import com.service.impl.MemCacheTestServiceImpl;
import com.service.impl.a.AutoInject;

//@Transactional
//@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(SpringRunner.class)
@WebAppConfiguration
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