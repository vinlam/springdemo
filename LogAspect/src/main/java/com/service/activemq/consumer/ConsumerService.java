package com.service.activemq.consumer;

import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
	private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);
    @Autowired
    private JmsTemplate jmsTemplate;

    public TextMessage receive(Destination destination){
        TextMessage textMessage = (TextMessage) jmsTemplate.receive(destination);
        try{
            logger.info("从队列" + destination.toString() + "收到了消息：\t"
                    + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return textMessage;
    }
    
    public Map receiveMsg(Destination destination) throws JMSException{
    	ObjectMessage message = (ObjectMessage) jmsTemplate.receive(destination);
    	logger.info("从队列" + destination.toString() + "收到了消息：\t"
				+ message.toString());
    	return (Map) message.getObject();
    }
}
