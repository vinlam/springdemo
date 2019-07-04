package com.core.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.info("web security");
		CookieCsrfTokenRepository cookieCsrfTokenRepository = new CookieCsrfTokenRepository();
		cookieCsrfTokenRepository.setCookieHttpOnly(true);
		
		http.csrf().disable();
	}
}
