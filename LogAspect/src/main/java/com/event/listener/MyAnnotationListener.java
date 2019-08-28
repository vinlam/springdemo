package com.event.listener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.event.MyEvent;


@Component
public class MyAnnotationListener {

    @EventListener
    public void listener1(MyEvent event) {
        System.out.println("注解监听器1:" + event.getMsg());
    }
    
    @EventListener
    @Async
    public void listener2(MyEvent event) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("注解监听器2:" + event.getMsg());
    }
}
