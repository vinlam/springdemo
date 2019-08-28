package com.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.AnalysisParams;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.springframework.stereotype.Service;

import com.service.AnalyseService;
@Service
public class StandardService implements AnalyseService {

	private SolrServer solrServer = new HttpSolrServer("http://127.0.0.1:9080/solr/itemcore");

	private String qt = "text_mmseg4j_pinyin";

	private String facetField = "title";

	private String tokenizer = "com.chenlb.mmseg4j.analysis.MMSegTokenizer";

	public StandardService() {
	};

	public StandardService(SolrServer solrServer) {
		if (solrServer != null) {
			this.solrServer = solrServer;
		}
	}

	@SuppressWarnings("unchecked")
	public Set<String> analysisBeforeQuery(String queryTerm) {
		SolrQuery query = new SolrQuery();
		query.add(CommonParams.QT, "/analysis/field"); // query type
		query.add(AnalysisParams.FIELD_VALUE, queryTerm);
		query.add(AnalysisParams.FIELD_TYPE, qt);
		try {
			// 对响应进行解析
			QueryResponse response = solrServer.query(query);
			NamedList<Object> analysis = (NamedList<Object>) response.getResponse().get("analysis");// analysis node
			NamedList<Object> field_types = (NamedList<Object>) analysis.get("field_types");// field_types node
			NamedList<Object> textSimple = (NamedList<Object>) field_types.get(this.qt);// textSimple node
			NamedList<Object> index = (NamedList<Object>) textSimple.get("index");// index node
			List<SimpleOrderedMap<String>> list = (ArrayList<SimpleOrderedMap<String>>) index.get(this.tokenizer);// tokenizer
			Set<String> wordSet = new HashSet<String>();
			// 在每个词条中间加上空格，为每个词条进行或运算
			for (Iterator<SimpleOrderedMap<String>> iter = list.iterator(); iter.hasNext();) {
				wordSet.add(iter.next().get("text"));
			}
			return wordSet;
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, String> analysisGroupBy(String queryTerm) {
		Set<String> wordSet = analysisBeforeQuery(queryTerm);
		Map<String, String> map = new HashMap<String, String>();
		if (!wordSet.isEmpty() || wordSet != null) {
			for (String word : wordSet) {
				SolrQuery query = new SolrQuery();// 建立一个新的查询
				query.setQuery(facetField + ":" + word);
				query.setRows(0);
				query.setFacet(true);// 设置facet=on
				query.addFacetField(facetField);// 设置需要facet的字段
				query.setFacetLimit(1);// 限制facet返回的数量
				query.setFacetMinCount(1);// 0的不显示
				QueryResponse response;
				map.put(word, "0");
				try {
					response = solrServer.query(query);
					List<FacetField> facets = response.getFacetFields();// 返回的facet列表
					// if(facets.isEmpty())continue;
					for (FacetField facet : facets) {
						// System.out.println(facet.getName());
						// System.out.println("----------------");
						List<Count> counts = facet.getValues();
						for (Count count : counts) {
							/*
							 * System.out.println(count.getName() + ":" + count.getCount());
							 */
							map.put(word, count.getCount() + "");
						}
						// System.out.println();
					}
				} catch (SolrServerException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}

	public String getFacetField() {
		return facetField;
	}

	public void setFacetField(String facetField) {
		this.facetField = facetField;
	}

	public String getQt() {
		return qt;
	}

	public void setQt(String qt) {
		this.qt = qt;
	}

	public String getTokenizer() {
		return tokenizer;
	}

	public void setTokenizer(String tokenizer) {
		this.tokenizer = tokenizer;
	}

	public void setServer(String url) {
		this.solrServer = new HttpSolrServer(url);
	}

	public static void main(String[] args) {
		// new StandardService().analysisBeforeQuery("张升强喜欢学习天天向上，哈哈哈");
		Map<String, String> map = new StandardService().analysisGroupBy("皇帝明星程序员张升强喜欢学习天天向上张升强，哈哈哈");
		System.out.println(map);
	}

}