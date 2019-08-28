package com.util;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component//也要被spring管理
public class EventPublisherUtil implements ApplicationEventPublisherAware {

    private static ApplicationEventPublisher eventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        if(eventPublisher == null){
            eventPublisher = applicationEventPublisher;
        }
    }

    /**
    * @description  发布事件
     */
    public static void publishEvent(ApplicationEvent event){
        eventPublisher.publishEvent(event);
    }
}

