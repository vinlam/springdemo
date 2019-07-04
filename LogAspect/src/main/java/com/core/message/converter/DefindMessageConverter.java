package com.core.message.converter;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.util.ObjectUtils;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

public class DefindMessageConverter  implements MessageConverter {  
   
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {  
        if (object instanceof Message) {  
            return (Message) object;  
        }  
        else if (object instanceof String) {  
            return createMessageForString((String) object, session);  
        }  
        else if (object instanceof byte[]) {  
            return createMessageForByteArray((byte[]) object, session);  
        }  
        else if (object instanceof Map) {  
            return createMessageForMap((Map) object, session);  
        }  
        else if (object instanceof Serializable) {  
            return createMessageForSerializable(((Serializable) object), session);  
        }  
  
        else {  
            throw new MessageConversionException("Cannot convert object of type [" +  
                    ObjectUtils.nullSafeClassName(object) + "] to JMS message. Supported message " +  
                    "payloads are: String, byte array, Map<String,?>, Serializable object.");  
        }  
    }  
   
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {  
        if (message instanceof TextMessage) {  
            return extractStringFromMessage((TextMessage) message);  
        }  
        else if (message instanceof BytesMessage) {  
            return extractByteArrayFromMessage((BytesMessage) message);  
        }  
        else if (message instanceof MapMessage) {  
            return extractMapFromMessage((MapMessage) message);  
        }  
        else if (message instanceof ObjectMessage) {  
            return extractSerializableFromMessage((ObjectMessage) message);  
        }  
        else {  
            return message;  
        }  
    }  
   
    protected TextMessage createMessageForString(String text, Session session) throws JMSException {  
        return session.createTextMessage(text);  
    }  
   
    protected BytesMessage createMessageForByteArray(byte[] bytes, Session session) throws JMSException {  
        BytesMessage message = session.createBytesMessage();  
        message.writeBytes(bytes);  
        return message;  
    }  
   
    protected MapMessage createMessageForMap(Map<?, ?> map, Session session) throws JMSException {  
        MapMessage message = session.createMapMessage();  
        for (Map.Entry entry : map.entrySet()) {  
            if (!(entry.getKey() instanceof String)) {  
                throw new MessageConversionException("Cannot convert non-String key of type [" +  
                        ObjectUtils.nullSafeClassName(entry.getKey()) + "] to JMS MapMessage entry");  
            }  
            message.setObject((String) entry.getKey(), entry.getValue());  
        }  
        return message;  
    }  
   
    protected ObjectMessage createMessageForSerializable(Serializable object, Session session) throws JMSException {  
        return session.createObjectMessage(object);  
    }  
   
   
    protected String extractStringFromMessage(TextMessage message) throws JMSException {  
        return message.getText();  
    }  
   
    protected byte[] extractByteArrayFromMessage(BytesMessage message) throws JMSException {  
        byte[] bytes = new byte[(int) message.getBodyLength()];  
        message.readBytes(bytes);  
        return bytes;  
    }  
   
    protected Map<String, Object> extractMapFromMessage(MapMessage message) throws JMSException {  
        Map<String, Object> map = new HashMap<String, Object>();  
        Enumeration<?> en = message.getMapNames();  
        while (en.hasMoreElements()) {  
            String key = (String) en.nextElement();  
            map.put(key, message.getObject(key));  
        }  
        return map;  
    }  
   
    protected Serializable extractSerializableFromMessage(ObjectMessage message) throws JMSException {  
        return message.getObject();  
    }  
}
