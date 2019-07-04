package com.core.message.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@EnableJms
public class ReceiveMessageListener {
	private static final Logger logger = LoggerFactory.getLogger(ReceiveMessageListener.class);
	
	@JmsListener(containerFactory="receiverContainerFactory",destination="Jaycekon")
	public void onMessage(Message msg) {
		TextMessage tm = (TextMessage) msg;
		try {
			logger.info("接收到消息："+tm.getText());
		} catch (JMSException e) {
			logger.error("异常：" + e.getMessage());
		}
	}
}
