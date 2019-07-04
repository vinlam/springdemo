package com.core.message.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName TopicConsumer_1
 * @Description 主题-消息消费者1
 **/
public class TopicConsumer_1 implements MessageListener {
	private static final Logger logger = LoggerFactory.getLogger(TopicConsumer_1.class);
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
        	logger.info("TopicConsumer_1接收到消息内容是：" + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
