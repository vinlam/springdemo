package com.core.message.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName QueueConsumer_2
 * @Description 队列-消息消费者2
 **/
public class QueueConsumer_2 implements MessageListener {
	private static final Logger logger = LoggerFactory.getLogger(QueueConsumer_2.class);
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
        	logger.info("QueueConsumer_2接收到消息内容是：" + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
