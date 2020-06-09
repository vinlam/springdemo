package com.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.core.security.protection.service.Protective;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class InputJsonDataDeserializer extends StdDeserializer<String>{
	protected InputJsonDataDeserializer() {
		super(String.class);
		// TODO Auto-generated constructor stub
	}

	private final static Logger LOGGER = LoggerFactory.getLogger(InputJsonDataDeserializer.class);
	private List<String> includePath;
	private List<String> excludePath;
	private List<Protective> protects;
	private final PathMatcher requestMathcer = new AntPathMatcher();
	@Autowired
	private HttpServletRequest request;
	@Override
	public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String value = p.getValueAsString();
		if(filterRequest()) {
			value = "";
		}
		return value;
	}
	
	protected Boolean filterRequest() {
		if(StringUtils.isBlank(request.getServletPath())){
			return false;
		}
		String path = request.getServletPath();
		for(String include:includePath) {
			if(requestMathcer.match(include, path)) {
				for(String exclude:excludePath) {
					if(requestMathcer.match(exclude, path)) {
						return false;
					}
				}
				return true;
			}
		}
		return true;
	}
	
	public List<String> getIncludePath() {
		return includePath;
	}


	public void setIncludePath(List<String> includePath) {
		this.includePath = includePath;
	}


	public List<String> getExcludePath() {
		return excludePath;
	}


	public void setExcludePath(List<String> excludePath) {
		this.excludePath = excludePath;
	}

	public List<Protective> getProtects() {
		return protects;
	}

	public void setProtects(List<Protective> protects) {
		this.protects = protects;
	}


}
