package com.controller.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.component.BeanLoaderFactory;
import com.service.StrategyUserService;

@RestController
@RequestMapping("/api/strategy")
class StrategyController{

    @Autowired
    private BeanLoaderFactory beanLoaderFactory;

    /**
     * 调用方法
     * @param code
     */
    @RequestMapping("/change")
    public void change(String code){
        //实现多个业务策略动态切换
        StrategyUserService userService = (StrategyUserService) beanLoaderFactory.getStrategyMap(code);
        userService.saveUser();
    }
}