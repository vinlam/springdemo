package com.service.impl;

import org.springframework.stereotype.Service;

import com.service.StrategyUserService;

@Service("userService1")
class UserService1 implements StrategyUserService{

    @Override
    public void saveUser() {
        System.out.println("UserService1：业务实现类");
    }
}