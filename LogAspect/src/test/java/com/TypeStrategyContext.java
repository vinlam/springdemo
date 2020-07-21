package com;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TypeStrategyContext {
	// 注入的策略
    @Autowired
    private Map<String, TypeStrategyInferface> typeMap;

    // 对外暴露的统一获取礼品信息的返回
	public String getRes(String[] types,String key) throws Exception {
		TypeStrategyInferface typeStrategyInferface = typeMap.get(key);
        Assert.notNull(typeStrategyInferface,"Type类型不在");
        return typeStrategyInferface.TypeRes(types);
    }
}
