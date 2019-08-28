package com.controller.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.User;
import com.event.FaceEvent;
import com.event.MyEventPublisher;
import com.util.EventPublisherUtil;


@RestController
@RequestMapping(value = "/api" )
public class TestEventListenerController {

    @Autowired
    private MyEventPublisher publisher;
    
    @Autowired
    private EventPublisherUtil eventPublishUtil;

    @RequestMapping(value = "/test/testPublishEvent1" )
    public void testPublishEvent(){
        publisher.pushListener("我来了！");
    }
    
    @RequestMapping(value = "/test/event" )
    public void testEvent(){
    	User user = new User();
        user.setId(345);
        user.setName("tom");
    	eventPublishUtil.publishEvent(new FaceEvent(user));
    }
}