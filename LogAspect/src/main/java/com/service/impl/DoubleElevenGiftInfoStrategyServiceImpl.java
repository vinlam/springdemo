package com.service.impl;

import org.springframework.stereotype.Service;

import com.entity.GiftInfo;
import com.service.IGiftInfoStrategyService;

/**
 * 双11活动
 */
@Service("DoubleElevenGiftInfoStrategy")
public class DoubleElevenGiftInfoStrategyServiceImpl implements IGiftInfoStrategyService {
	@Override
	public GiftInfo getGiftInfo(int activityId) {
		// 双11调用统一平台接口获取礼品信息
		GiftInfo giftInfo = new GiftInfo();
		giftInfo.setGiftId(902);
		giftInfo.setGiftName("空气净化器");
		return giftInfo;
	}
}
