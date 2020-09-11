package com.service.impl;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.entity.GiftInfo;
import com.service.IGiftInfoStrategyService;

/**
 * 双11活动
 */
@Service("DoubleElevenGiftInfoStrategy")
@Order(1)
public class DoubleElevenGiftInfoStrategyServiceImpl implements IGiftInfoStrategyService {
	@Override
	public GiftInfo getGiftInfo(int activityId) {
		// 双11调用统一平台接口获取礼品信息
		GiftInfo giftInfo = new GiftInfo();
		giftInfo.setGiftId(activityId);
		giftInfo.setGiftName("空气净化器");
		return giftInfo;
	}
}
