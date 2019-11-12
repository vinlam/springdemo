package com.service;

import com.entity.User;

public interface SaveDataService {
	User saveUser(User u);
	User getUser(User u);
}
