package com.common.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StaticComponent {
	
	public static String emailHost;

	public static String getEmailHost() {
		return emailHost;
	}
	@Value("${email.smtp.host}")
	public static void setEmailHost(String emailHost) {
		StaticComponent.emailHost = emailHost;
	}
	
	

}
