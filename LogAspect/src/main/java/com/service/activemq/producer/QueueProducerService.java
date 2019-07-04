package com.service.activemq.producer;

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

/**
 * @version V1.0
 * @ClassName QueueProducerService
 * @Description 队列-消息生产者 Service
 **/
@Service
public class QueueProducerService {
	private static final Logger logger = LoggerFactory.getLogger(QueueProducerService.class);
	@Autowired
	private JmsTemplate jmsQueueTemplate;

	// 发送消息
	public void sendMessage(Destination destination, final String message) {
		logger.info("QueueProducerService发送消息：" + message);
		jmsQueueTemplate.send(destination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
	}
}