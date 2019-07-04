package com.service.activemq.producer;

import java.io.Serializable;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service
public class ProducerService {
	private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);
	@Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(Destination destination,final String msg){
        logger.info(Thread.currentThread().getName()+" 向队列"+destination.toString()+"发送消息---------------------->"+msg);
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }
    public void sendMessageObj(Destination destination,final Map<String,Object> msgObj){
    	logger.info(Thread.currentThread().getName()+" 向队列"+destination.toString()+"发送消息---------------------->"+JSON.toJSONString(msgObj));
    	jmsTemplate.send(destination, new MessageCreator() {
    		public Message createMessage(Session session) throws JMSException {
    			return session.createObjectMessage((Serializable) msgObj);
    		}
    	});
    	
    	//使用MessageConverter的情况  
        //jmsTemplate.convertAndSend(destination, msgObj);  
    }

}