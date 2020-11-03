package com.service.impl;

import org.springframework.stereotype.Service;

import com.service.StrategyUserService;

@Service("userService2")
class UserService2 implements StrategyUserService{

    @Override
    public void saveUser() {
        System.out.println("UserService2：业务实现类");
    }
}