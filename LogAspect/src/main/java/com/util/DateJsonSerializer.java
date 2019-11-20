package com.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotationMap;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

public class DateJsonSerializer  extends JsonSerializer<Date> implements ContextualSerializer{
    private SimpleDateFormat df=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
 
//    @Override
//    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//        jsonGenerator.writeString(df.format(date));
//    }
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (null != date) {
        	jsonGenerator.writeString(this.df.format(date));
        }
    }

	@Override
	public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
			throws JsonMappingException {
		AnnotationMap annotated = property.getMember().getAllAnnotations();

        JsonFormat jsonFormat = annotated.get(JsonFormat.class);
        if (jsonFormat != null && jsonFormat.pattern() != null){
        	df = new SimpleDateFormat(jsonFormat.pattern());
        }

        return this;
	}
}
