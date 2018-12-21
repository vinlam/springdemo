package com.dao;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.common.solr.BaseSolr;
import com.common.solr.SolrClient;
import com.entity.TbItem;
@Repository
public class TbItemSearchDAO extends BaseSolr<TbItem, String> {
	@Autowired
	public TbItemSearchDAO(@Qualifier("solrClient")SolrClient solrClient) {
		super(solrClient);
	}
	
	public SolrQuery initSolrQuery(){
		SolrQuery query = new SolrQuery();
		query.set("qt", "/select").set("wt","json");
		query.setHighlight(true)
			.setHighlightSimplePre("<span>")
			.setHighlightSimplePost("</span>")
			.setHighlightSnippets(1)
			.addHighlightField("title");
		
		query.setFields("id","title","price","goodsId","itemSn","stockCount",
				"barcode","sellPoint","num","categoryid","status");
		return query;
	}
	
	public SolrResponse searchByQuery(SolrQuery query) throws SolrServerException{
		SolrServer solrServer = getSolrOperations().getSolrServer();
		SolrResponse response = solrServer.query(query);
		return response;
	}
}
