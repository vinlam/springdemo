package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.common.solr.SolrClient;
import com.dao.TB;
import com.dao.TbItemSearchDAO;
import com.dao.UserDao;
import com.entity.MBUser;
import com.entity.TbItem;
import com.entity.User;
import com.util.JsonUtil;

@Service
public class UserService {
	public String addUser(String userName, String password) {
		// TODO Auto-generated method stub
		User u = new User();
		u.setName(userName);
		return JsonUtil.beanToJson(u);
	}
	
	@Autowired
	private TB tb;
	
	public User getUser(String name){
		User u = tb.getUser(name);
		
		return u;
	}
	
	@Autowired
	public User getUserB(TB tbb){
		//User u = tbb.getUser("jjj");
		
		return tbb.getUser("hhhh");
	}
	
	
	@Autowired
	private UserDao userDao;
	
	public MBUser findById(String id) {
		return userDao.findOneById(id);
	}
	
//	@Autowired 
//	@Qualifier("sClient")
//	private SolrClient solrClient;
//	@Autowired
//	private TbItemSearchDAO itemSearchDAO;
//	public void s() {
//		//solrClient.setUrl("http://www.baidu.com");
//		System.out.println(solrClient.getUrl());
//		//TbItemSearchDAO itemSearchDAO = new TbItemSearchDAO(solrClient);
//		TbItem item = itemSearchDAO.findOne("999"); 
//		System.out.println("----"+item.getTitle());
//	}
}
