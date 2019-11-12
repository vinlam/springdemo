package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.SaveDataDao;
import com.entity.User;
import com.service.SaveDataService;

@Service
public class SaveDataServiceImpl implements SaveDataService {

	@Autowired
	private SaveDataDao saveDataDao;
	
	@Override
	public User saveUser(User u) {
		// TODO Auto-generated method stub
		return saveDataDao.userDataCache(u);
	}

	@Override
	public User getUser(User u) {
		// TODO Auto-generated method stub
		return saveDataDao.userDataCache(u);
	}

}
