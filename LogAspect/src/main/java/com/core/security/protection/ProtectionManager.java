package com.core.security.protection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.core.security.protection.service.Protective;

@Component
public class ProtectionManager {
	@Autowired
	private List<Protective> protections;
	
	public String protect(String raw) {
		if(raw == null) {
			return null;
		}
		
		for(Protective protective : protections) {
			protective.protect(raw);
		}
		return raw;
	}
}
