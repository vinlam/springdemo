package com;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.service.EhCacheTestService;
import com.service.MemCacheTestService;
import com.service.SysLogService;
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
	//@Qualifier("AutoInjectA")
	private AutoInject autoInjecta;
	@Autowired
	//@Qualifier("AutoInjectB")
	private com.service.impl.b.AutoInject autoInjectb;
	@Test
	public void test(){
		autoInjecta.print();
		//autoInjectb.print();
		System.out.println("aaat:"+autoInjecta.print());
		//System.out.println("aaat:"+autoInjecta.print()+autoInjectb.print());
	}
	
	@Autowired
	//@Qualifier("redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;  //使用RedisTemplate操作redis
	
	@Test
	public void t() throws InterruptedException{
		//System.out.println("ttttt");
//		MyThead myThead = new MyThead();
//		Thread thread = new Thread(myThead);
//		thread.start();
		int i = 0;
		while(i < 5){
			i = i+1;
			f();
			Thread.sleep(1000);
		}
	}
	
	public void f(){
		String key = "ut";
		int sec = 10;
		String value =  (String) redisTemplate.opsForValue().get(key);
		long expire = redisTemplate.getExpire(key);
        System.out.println(""+value);
        if (value == null) {
            redisTemplate.opsForValue().set(key, "firstvalue", sec, TimeUnit.SECONDS);  //set时一定要加过期时间
            System.out.println(redisTemplate.opsForValue().get(key));
        } else if (expire > 8) {
            redisTemplate.opsForValue().set(key, "second", 5, TimeUnit.SECONDS);
        } else {
        	redisTemplate.delete(key);
        }
	}
	
	@Autowired
    private SysLogService sysLogService;
	@Test
	public void testcache() throws InterruptedException{
		//sysLogService.selectSysLog("123");
		
		System.out.println(sysLogService.selectSysLog("12322").getId());
		Thread.sleep(3000);
		System.out.println(sysLogService.selectSysLog("12322").getId());
		Thread.sleep(11000);
		System.out.println(sysLogService.selectSysLog("12322").getId());
	}
	@Test
	public void tc() throws InterruptedException{
		//sysLogService.selectSysLog("123");
		
		System.out.println("0s count:"+sysLogService.count());
		Thread.sleep(3000);
		System.out.println("after 3s count:"+sysLogService.count());
		Thread.sleep(11000);
		System.out.println("after 11s count:"+sysLogService.count());
	}
	
	@Autowired  
    private EhCacheTestService ehCacheTestService;

    @Test  
    public void getTimestampTest() throws InterruptedException{  
        System.out.println("第一次调用：" + ehCacheTestService.getTimestamp("param"));
        Thread.sleep(2000);
        System.out.println("2秒之后调用：" + ehCacheTestService.getTimestamp("param"));
        Thread.sleep(11000);
        System.out.println("再过11秒之后调用：" + ehCacheTestService.getTimestamp("param"));
    } 
    

	@Autowired  
    private MemCacheTestService memCacheTestService;
	@Autowired  
	private MemCacheTestServiceImpl memCacheTestServiceImpl;

    @Test  
    public void getMemTimestampTest() throws InterruptedException{  
        //System.out.println("第一次调用：" + memCacheTestServiceImpl.getTimestamp("param"));
        System.out.println("m第一次调用：" + memCacheTestService.getTimestamp("t"));
        Thread.sleep(2000);
        System.out.println("m2秒之后调用：" + memCacheTestService.getTimestamp("t"));
        Thread.sleep(11000);
        System.out.println("m再过11秒之后调用：" + memCacheTestService.getTimestamp("t"));
    }
}
class MyThead implements Runnable
{
	public void run()
	{
		System.out.println("我休息了！");
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
		}
		System.out.println("一秒后在叫我吧！");
	}
}