package com.service.impl;

import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.LBHttpSolrServer;

import com.google.common.base.Strings;
import com.service.AnalyseService;

public class CloudStandardService implements AnalyseService {

	private String cloudServerEndPoints = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

	private static final int ZK_CLIENT_TIMEOUT = 5 * 60 * 1000;

	private static final int ZK_CONNECT_TIMEOUT = 5 * 60 * 1000;

	private String collectionName = "itemcore";

	private AnalyseService analyseService = null;

	private static CloudSolrServer cloudSolrServer = null;

	public CloudStandardService(String cloudServerEndPoints) {
		if (!Strings.isNullOrEmpty(cloudServerEndPoints)) {
			this.cloudServerEndPoints = cloudServerEndPoints;
		}
		initSolrCloudServer();
	}

	private CloudSolrServer initSolrCloudServer() {
		try {
			if (cloudSolrServer == null) {
				LBHttpSolrServer lbHttpSolrServer = new LBHttpSolrServer(cloudServerEndPoints);
				cloudSolrServer = new CloudSolrServer(cloudServerEndPoints, lbHttpSolrServer);
				cloudSolrServer.setZkClientTimeout(ZK_CLIENT_TIMEOUT);
				cloudSolrServer.setZkConnectTimeout(ZK_CONNECT_TIMEOUT);
				cloudSolrServer.setDefaultCollection(collectionName);
				cloudSolrServer.connect();
				analyseService = getAnalyseService();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cloudSolrServer;
	}

	public AnalyseService getAnalyseService() {
		if (this.analyseService == null) {
			analyseService = new StandardService(cloudSolrServer);
		}
		return analyseService;
	}

	public void setAnalyseService(AnalyseService analyseService) {
		this.analyseService = analyseService;
	}

	@Override
	public Set<String> analysisBeforeQuery(String queryTerm) {
		return analyseService.analysisBeforeQuery(queryTerm);
	}

	@Override
	public Map<String, String> analysisGroupBy(String queryTerm) {
		return analyseService.analysisGroupBy(queryTerm);
	}

	public String getCloudServerEndPoints() {
		return cloudServerEndPoints;
	}

	public void setCloudServerEndPoints(String cloudServerEndPoints) {
		this.cloudServerEndPoints = cloudServerEndPoints;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public static void main(String[] args) {
		Map<String, String> map = new CloudStandardService(null).analysisGroupBy("皇帝开发员程序员张升强喜欢学习天天向上张升强，哈哈哈");
		System.out.println(map);
	}
}