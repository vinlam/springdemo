package com.component;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import com.entity.GiftInfo;
import com.service.IGiftInfoStrategyService;
import com.service.impl.SummerBuyDayGiftInfoStrategyServiceImpl;


/**
 * 礼品信息环境角色类
 */
@Component
public class GiftInfoContext {

    // 注入的策略
    @Resource
    private Map<Integer, IGiftInfoStrategyService> giftInfoStrategyServiceMap;
    // 注入的策略
    @Autowired
    private Map<String, IGiftInfoStrategyService> map;
    
    // 注入的策略
    @Autowired
    private List<IGiftInfoStrategyService> list;
    @Autowired
    private ApplicationContext applicationContext;

    
    // 对外暴露的统一获取礼品信息的返回
	public GiftInfo getGiftInfo(int subjectId, int activityId) {
		Iterator<String> iter = map.keySet().iterator();
		while(iter.hasNext()){
			String key=iter.next();
			IGiftInfoStrategyService value = map.get(key);
			//value.getGiftInfo(activityId).getGiftName();
			System.out.println(key+" "+value.getGiftInfo(activityId).getGiftName());
		}
		for(IGiftInfoStrategyService infoStrategyService:list) {
			System.out.println(AnnotationUtils.getAnnotation(infoStrategyService.getClass(), Service.class));
			
			Order order = AnnotationUtils.getAnnotation(infoStrategyService.getClass(), Order.class);
			if(!ObjectUtils.isEmpty(order)) {
				System.out.println(order.value());
			}
			Annotation annotations = AnnotationUtils.findAnnotation(infoStrategyService.getClass(), Service.class);
		    Map<String, Object> annotAttribs = AnnotationUtils.getAnnotationAttributes(annotations);
			System.out.println(annotAttribs);
		    if(infoStrategyService instanceof SummerBuyDayGiftInfoStrategyServiceImpl) {
			
			}
		}
		
//		DoubleElevenGiftInfoStrategy 空气净化器
//		SummerBuyDayGiftInfoStrategy 铁锅三件套
//		@org.springframework.stereotype.Service(value=DoubleElevenGiftInfoStrategy)
//		1
//		{value=DoubleElevenGiftInfoStrategy}
//		@org.springframework.stereotype.Service(value=SummerBuyDayGiftInfoStrategy)
//		2
//		{value=SummerBuyDayGiftInfoStrategy}
//		{"giftId":1,"giftName":"铁锅三件套"}
        IGiftInfoStrategyService giftInfoStrategyService = giftInfoStrategyServiceMap.get(subjectId);
        Assert.notNull(giftInfoStrategyService,"不能为空");
        return giftInfoStrategyService.getGiftInfo(activityId);
    }
}