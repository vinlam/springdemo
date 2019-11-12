package com.component;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.entity.GiftInfo;
import com.service.IGiftInfoStrategyService;

/**
 * 礼品信息环境角色类
 */
@Component
public class GiftInfoContext {

    // 注入的策略
    @Resource
    private Map<Integer, IGiftInfoStrategyService> giftInfoStrategyServiceMap;

    // 对外暴露的统一获取礼品信息的返回
	public GiftInfo getGiftInfo(int subjectId, int activityId) {
        IGiftInfoStrategyService giftInfoStrategyService = giftInfoStrategyServiceMap.get(subjectId);
        Assert.notNull(giftInfoStrategyService,"不能为空");
        return giftInfoStrategyService.getGiftInfo(activityId);
    }
}