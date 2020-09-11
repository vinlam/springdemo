package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.entity.GiftInfo;
import com.service.IGiftInfoStrategyService;

/**
 * 夏季购车节
 */
@Service("SummerBuyDayGiftInfoStrategy")
@Order(2)
public class SummerBuyDayGiftInfoStrategyServiceImpl implements IGiftInfoStrategyService {

	// @Autowired
	// private GiftInfoMapper giftInfoMapper;

	public GiftInfo getGiftInfo(int activityId) {
		// 从数据库中查询
		GiftInfo giftInfo = new GiftInfo();
		giftInfo.setGiftId(activityId);
		giftInfo.setGiftName("铁锅三件套");
		// giftInfoMapper.getGiftInfoByActivityId(activityId)
		return giftInfo;
	}
}
