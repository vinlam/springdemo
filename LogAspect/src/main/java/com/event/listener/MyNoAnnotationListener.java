package com.event.listener;


import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.event.MyEvent;


@Component
public class MyNoAnnotationListener implements ApplicationListener<MyEvent>{

    @Override
    public void onApplicationEvent(MyEvent event) {
        System.out.println("非注解监听器：" + event.getMsg());
    }

}