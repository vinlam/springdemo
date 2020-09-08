package com;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.entity.Account;
import com.service.AccountService;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(JUnit4ClassRunner.class)
@WebAppConfiguration // 整合springfox-swagger2后需加@WebAppConfiguration注解
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class AccountService1Test {
	@Autowired
    private AccountService accountService;

    private final Logger logger = LoggerFactory.getLogger(AccountService1Test.class);

    @Before
    public void setUp() throws Exception {
		//ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-test.xml");
        //ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-test.xml");
        //accountService1 = context.getBean("accountService1", AccountService.class);
    }

    @Test
    public void testInject(){
        assertNotNull(accountService);
    }

    @Test
    public void testGetAccountByName() throws Exception {
        accountService.getAccountByName("accountName");
        accountService.getAccountByName("accountName");

        accountService.reload();
        logger.info("after reload ....");

        accountService.getAccountByName("accountName");
        accountService.getAccountByName("accountName");
    }
    
    @Test
    public void testGetAccountByNameCache() throws Exception {

        logger.info("first query.....");
        accountService.getAccountByNameCache("accountName");

        logger.info("second query....");
        accountService.getAccountByNameCache("accountName");

    }

    @Test
    public void testUpdateAccount() throws Exception {
        Account account1 = accountService.getAccountByNameCache("accountName1");
        logger.info(account1.toString());
        System.out.println(account1.toString());
        Account account2 = accountService.getAccountByNameCache("accountName2");
        logger.info(account2.toString());

        account2.setId(121212);
        accountService.updateAccount(account2);

        // account1会走缓存
        account1 = accountService.getAccountByNameCache("accountName1");
        logger.info(account1.toString());
        // account2会查询db
        account2 = accountService.getAccountByNameCache("accountName2");
        logger.info(account2.toString());

    }

    @Test
    public void testReload() throws Exception {
        accountService.reload();
        // 这2行查询数据库
        accountService.getAccountByName("somebody1");
        accountService.getAccountByName("somebody2");

        // 这两行走缓存
        accountService.getAccountByName("somebody1");
        accountService.getAccountByName("somebody2");
    }
}