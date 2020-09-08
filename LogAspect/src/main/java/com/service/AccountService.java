package com.service;

import com.entity.Account;

public interface AccountService {

	Account getAccountByName(String string);
	Account getAccountByNameCache(String accountName);
	void updateAccount(Account account);
	void reload();
	void reloadCache();

}
