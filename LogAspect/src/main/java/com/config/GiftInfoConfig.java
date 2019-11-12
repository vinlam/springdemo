package com.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.service.IGiftInfoStrategyService;

/**
 * 礼品信息配置类
 */
@Configuration
public class GiftInfoConfig {

    @Autowired
    @Qualifier("DoubleElevenGiftInfoStrategy")
    private IGiftInfoStrategyService doubleElevenGiftInfoStrategyService;

    @Autowired
    @Qualifier("SummerBuyDayGiftInfoStrategy")
    private IGiftInfoStrategyService summerBuyDayGiftInfoStrategyService;

    /**
     * 注入bean
     */
    @Bean
    public Map<Integer, IGiftInfoStrategyService> giftInfoStrategyServiceMap() {
        Map<Integer, IGiftInfoStrategyService> dataMap = new HashMap<>();
        dataMap.put(1, summerBuyDayGiftInfoStrategyService);
        dataMap.put(2, doubleElevenGiftInfoStrategyService);
        return dataMap;
    }
}