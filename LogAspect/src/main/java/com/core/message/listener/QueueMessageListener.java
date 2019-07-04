package com.core.message.listener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueMessageListener implements MessageListener {
	private static final Logger logger = LoggerFactory.getLogger(QueueMessageListener.class);
    public void onMessage(Message message) {
        TextMessage tm = (TextMessage) message;
        try {
        	logger.info("QueueMessageListener监听到了文本消息：\t" + tm.getText());
            //do something ...
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
