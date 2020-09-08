package com.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.common.CacheContext;
import com.entity.Account;
import com.google.common.base.Optional;
import com.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private CacheContext<Account> accountCacheContext;
	@Override
	public Account getAccountByName(String accountName) {
		Account result = accountCacheContext.get(accountName);
		if (result != null) {
			logger.info("get from cache... {}", accountName);
			return result;
		}

		Optional<Account> accountOptional = getFromDB(accountName);
		if (!accountOptional.isPresent()) {
			throw new IllegalStateException(String.format("can not find account by account name : [%s]", accountName));
		}

		Account account = accountOptional.get();
		accountCacheContext.addOrUpdateCache(accountName, account);
		return account;
	}
	@Override
	public void reload() {
		accountCacheContext.evictCache();
	}

	private Optional<Account> getFromDB(String accountName) {
		logger.info("real querying db... {}", accountName);
		// Todo query data from database
		return Optional.fromNullable(new Account(accountName));
	}
	
	// 使用了一个缓存名叫 accountCache
    @Cacheable(value="accountCache")
    public Account getAccountByNameCache(String accountName) {

        // 方法内部实现不考虑缓存逻辑，直接实现业务
        logger.info("real querying account... {}", accountName);
        Optional<Account> accountOptional = getFromDB(accountName);
        if (!accountOptional.isPresent()) {
            throw new IllegalStateException(String.format("can not find account by account name : [%s]", accountName));
        }

        return accountOptional.get();
    }

    @CacheEvict(value="accountCache",key="#account.getName()")
    public void updateAccount(Account account) {
        updateDB(account);
    }
    
    private void updateDB(Account account) {
        logger.info("real update db...{}", account.getName());
    }
    
    @CacheEvict(value="accountCache",allEntries=true)
    public void reloadCache() {
    }

}
