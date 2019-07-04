package com.common.solr;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.LBHttpSolrServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.solr.VersionUtil;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class SolrClient implements InitializingBean,DisposableBean,ApplicationContextAware{
	private ApplicationContext applicationContext;
	private static final String SERVER_URL_SEPATATOR = "";
	private String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private Integer timeout;
	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(Integer maxConnections) {
		this.maxConnections = maxConnections;
	}

	public SolrServer getQuerySolrServer() {
		return querySolrServer;
	}

	public void setQuerySolrServer(SolrServer querySolrServer) {
		this.querySolrServer = querySolrServer;
	}

	public List<SolrServer> getUpdateSolrServerList() {
		return updateSolrServerList;
	}

	public void setUpdateSolrServerList(List<SolrServer> updateSolrServerList) {
		this.updateSolrServerList = updateSolrServerList;
	}

	public SolrTemplate getQuerySolrTemplate() {
		return querySolrTemplate;
	}

	public void setQuerySolrTemplate(SolrTemplate querySolrTemplate) {
		this.querySolrTemplate = querySolrTemplate;
	}

	public List<SolrTemplate> getUpdateSolrTemplateList() {
		return updateSolrTemplateList;
	}

	public void setUpdateSolrTemplateList(List<SolrTemplate> updateSolrTemplateList) {
		this.updateSolrTemplateList = updateSolrTemplateList;
	}

	private Integer maxConnections;
	private SolrServer querySolrServer;
	private List<SolrServer> updateSolrServerList = new ArrayList<SolrServer>();
	
	private SolrTemplate querySolrTemplate;
	private List<SolrTemplate> updateSolrTemplateList = new ArrayList<SolrTemplate>();
	
	public SolrClient() {
		// TODO Auto-generated constructor stub
	}
	
	public SolrClient(String url) {
		// TODO Auto-generated constructor stub
		this.url = url;
	}
	
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}

	protected void destroy(SolrServer solrServer) {
		// TODO Auto-generated method stub
		if(solrServer instanceof HttpSolrServer){
			((HttpSolrServer) solrServer).shutdown();
		}else if(solrServer instanceof LBHttpSolrServer){
			((LBHttpSolrServer) solrServer).shutdown();
		}else if(VersionUtil.isSolr4XAvailable() && (solrServer instanceof CloudSolrServer)){
			((CloudSolrServer) solrServer).shutdown();
		}
	}
	

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		//Assert.hasText(url, "url cannot be null");
		//initSolrServer();
	}
	
	public void initSolrServer(){
		if(this.url.contains(SERVER_URL_SEPATATOR)){
			String[] urls = StringUtils.split(this.url, SERVER_URL_SEPATATOR);
			
			querySolrServer = createLoadBalancedHttpSolrServer(urls);
			querySolrTemplate = createSolrTemplate(querySolrServer);
			for(String u : urls){
				SolrServer updateSolrServer = createHttpSolrServer(u);
				updateSolrServerList.add(updateSolrServer);
				
				SolrTemplate updateSolrTemplate = createSolrTemplate(updateSolrServer);
				updateSolrTemplateList.add(updateSolrTemplate);
			}
		}else{
			querySolrServer = createHttpSolrServer(this.url);
			
			querySolrTemplate = createSolrTemplate(querySolrServer);
			
			SolrServer updateSolrServer = createHttpSolrServer(this.url);
			updateSolrServerList.add(updateSolrServer);
			
			SolrTemplate updateSolrTemplate = createSolrTemplate(updateSolrServer);
			updateSolrTemplateList.add(updateSolrTemplate);
		}
	}

	private HttpSolrServer createHttpSolrServer(String url) {
		HttpSolrServer httpSolrServer = new HttpSolrServer(url);
		if(timeout != null){
			httpSolrServer.setConnectionTimeout(timeout);
		}
		if(maxConnections != null){
			httpSolrServer.setMaxTotalConnections(maxConnections.intValue());
		}
		return httpSolrServer;
	}

	private SolrTemplate createSolrTemplate(SolrServer solrServer) {
		SolrTemplate solrTemplate = new SolrTemplate(solrServer);
		solrTemplate.setApplicationContext(applicationContext);
		solrTemplate.afterPropertiesSet();
		
		return solrTemplate;
	}

	private LBHttpSolrServer createLoadBalancedHttpSolrServer(String[] urls) {
		try{
			LBHttpSolrServer lbHttpSolrServer = new LBHttpSolrServer(urls);
			if(timeout !=null){
				lbHttpSolrServer.setConnectionTimeout(timeout.intValue());
			}
			return lbHttpSolrServer;
		}catch(MalformedURLException e){
			throw new IllegalArgumentException("Unable to create Load Balance Http Solr Server",e);
		}
	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		destroy(querySolrServer);
		for(SolrServer solrServer : updateSolrServerList){
			destroy(solrServer);
		}
	}
	

}
