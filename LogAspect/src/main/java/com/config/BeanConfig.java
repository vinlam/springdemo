package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.common.solr.SolrClient;

@Configuration
public class BeanConfig {

	@Bean(name="solrclient")
	public SolrClient solrClient() {
		return new SolrClient("http://www.baidu.com");
	}
}
