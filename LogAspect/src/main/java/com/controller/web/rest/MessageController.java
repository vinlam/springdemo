package com.controller.web.rest;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.service.activemq.consumer.ConsumerService;
import com.service.activemq.producer.ProducerService;

@RestController
@RequestMapping(value = "api/")
public class MessageController {
    private Logger logger = LoggerFactory.getLogger(MessageController.class);
    @Resource(name = "demoQueueDestination")
    private Destination destination;

    //队列消息生产者
    @Resource(name = "producerService")
    private ProducerService producer;

    //队列消息消费者
    @Resource(name = "consumerService")
    private ConsumerService consumer;

    @RequestMapping(value = "/SendMessage", method = RequestMethod.POST)
    @ResponseBody
    public String send(String msg) {
        logger.info(Thread.currentThread().getName()+"------------send to jms Start");
        producer.sendMessage(destination,msg);
        logger.info(Thread.currentThread().getName()+"------------send to jms End");
        return "ok";
    }
    @RequestMapping(value = "/SendMessageObj", method = RequestMethod.POST)
    @ResponseBody
    public String sendObj(@RequestBody Map<String,Object> msg) {
    	logger.info(Thread.currentThread().getName()+"------------send to jms Start");
    	producer.sendMessageObj(destination,msg);
    	logger.info(Thread.currentThread().getName()+"------------send to jms End");
    	return "ok";
    }

    @RequestMapping(value= "/ReceiveTextMessage",method = RequestMethod.GET)
    @ResponseBody
    public String receiveText(){
        logger.info(Thread.currentThread().getName()+"------------receive from jms Start");
        TextMessage tm = consumer.receive(destination);
        logger.info(Thread.currentThread().getName()+"------------receive from jms End");
        String res = null;
        try {
			res =  tm.getText();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return res;
    }
    
    
    @RequestMapping(value= "/ReceiveMessage",method = RequestMethod.GET)
    @ResponseBody
    public Object receive(){
    	logger.info(Thread.currentThread().getName()+"------------receive from jms Start");
    	Map m = null;
		try {
			m = consumer.receiveMsg(destination);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	logger.info(Thread.currentThread().getName()+"------------receive from jms End");
    	return m;
    }

}
