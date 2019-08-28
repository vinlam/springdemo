package com.event.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.entity.User;
import com.event.ArmEvent;
import com.event.FaceEvent;
import com.util.EventPublisherUtil;

@Component
public class FaceListener {
	private static Logger logger = LoggerFactory.getLogger(FaceEvent.class);
//	@TransactionalEventListener(fallbackExecution = true)
//	@Order(5)
//    public void onApplicationEvent(FaceEvent event) {
//        User user = (User) event.getEventData();
//        logger.info("===> A 收到人脸事件:  {}}",user);
//
//        //@TransactionalEventListener指不和发布事件的在同一个事务内，发布事件的方法事务结束后才会执行本方法，本方法发生异常不会回滚发布事件的事务，
//        throw new  RuntimeException("监听事件抛出异常");
//    }

	@Transactional(rollbackFor = Exception.class)
    public void handle(){
        User user = new User();
        user.setId(123);
        user.setName("jack");

        //处理完上面的逻辑后，发布事件
        EventPublisherUtil.publishEvent(new FaceEvent(user));
 
        //数据库添加操作
        //Integer integer = deviceAlarmService.addDevice();
    }

	@EventListener
    @Order(4)
    public void onApplicationEvent1(FaceEvent event) {
        User user = (User) event.getSource();
        logger.info("===> A 收到人脸事件:  {}",user);
    }


    @EventListener({FaceEvent.class,ArmEvent.class})
    @Order(3)
    public void onApplicationEvent3(Object event) {

        if(event instanceof FaceEvent){
        	logger.info("===> B 收到人脸事件:  {}",((FaceEvent) event).getEventData());
        }else if(event instanceof ArmEvent){
            ArmEvent armEvent = (ArmEvent) event;
            logger.info("===> B 收到臂膀事件:  {}",armEvent.getEventData());
        }
    }
}
