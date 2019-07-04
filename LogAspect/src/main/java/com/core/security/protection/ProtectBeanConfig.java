package com.core.security.protection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
public class ProtectBeanConfig {
	private static final String DEFAULT_FILTER_NAME ="safeFilterChain";//
	private static final String FILTER_NAME ="safeFilter";//
	@Bean
	public List<String> whilenamepattern(){
		return Arrays.asList("/**","/view/**");
	};
	
	
	@Bean(name = FILTER_NAME)
	public SafeFilter xssFilter() {
		return new SafeFilter();
	}

    @Bean(name = DEFAULT_FILTER_NAME)
    public FilterChainProxy proxyFilter() throws Exception {
        List<SecurityFilterChain> chains = new ArrayList<SecurityFilterChain>();
        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/login/**"),
        		xssFilter()));
        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/views/**"),
        		xssFilter()));
        
        return new FilterChainProxy(chains);
    }
}
