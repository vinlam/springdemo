package com.core.message.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName TopicConsumer_2
 * @Description 主题-消息消费者2
 **/
public class TopicConsumer_2 implements MessageListener {
	private static final Logger logger = LoggerFactory.getLogger(TopicConsumer_2.class);
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
        	logger.info("TopicConsumer_2接收到消息内容是：" + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
