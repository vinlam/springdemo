package com.util;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class OutputJsonDataSerializer extends StdSerializer<String> implements ContextualSerializer{
	


	private final static Logger LOGGER = LoggerFactory.getLogger(OutputJsonDataSerializer.class);
	private List<String> include;
	private List<String> exclude;
	
	protected OutputJsonDataSerializer() {
		super(String.class);
		// TODO Auto-generated constructor stub
	}
	
	public List<String> getInclude() {
		return include;
	}


	public void setInclude(List<String> include) {
		this.include = include;
	}


	public List<String> getExclude() {
		return exclude;
	}


	public void setExclude(List<String> exclude) {
		this.exclude = exclude;
	}

	@Override
	public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		// TODO Auto-generated method stub
		String returnStr = value;
		gen.writeString(returnStr);
	}


	@Override
	public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
			throws JsonMappingException {
		// TODO Auto-generated method stub
		return null;
	}


}
