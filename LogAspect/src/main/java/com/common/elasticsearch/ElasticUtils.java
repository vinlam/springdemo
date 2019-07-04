package com.common.elasticsearch;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import com.entity.Product;

public class ElasticUtils {
//相当于数据库名称（数据量小）
	public static String indexName = "shose";

// 初始化api客户端
	public static RestHighLevelClient client = new RestHighLevelClient(
			RestClient.builder(new HttpHost("localhost", 9200, "http")));

// 关键字搜索 指定匹配类型
	public static List<Map<String, Object>> search(String type, String fieldName, String keyword, int start, int count)
			throws IOException {

		SearchRequest searchRequest = new SearchRequest(indexName);

		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		// 关键字匹配对应字段
		MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(fieldName, keyword);
		// 模糊匹配
		matchQueryBuilder.fuzziness(Fuzziness.AUTO);
		sourceBuilder.query(matchQueryBuilder);
		// 第几页
		sourceBuilder.from(start);
		// 第几条
		sourceBuilder.size(count);

		searchRequest.source(sourceBuilder);
		searchRequest.types(type);
		// 匹配度从高到低
		sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));

		SearchResponse searchResponse = client.search(searchRequest);

		SearchHits hits = searchResponse.getHits();

		List<Map<String, Object>> matchRsult = new LinkedList<Map<String, Object>>();

		for (SearchHit hit : hits.getHits()) {
			matchRsult.add(hit.getSourceAsMap());
		}
		return matchRsult;
	}

//删除指定类型
	public static void deleteDocument(Object object) throws IOException {
		if (object instanceof Product) {
			DeleteRequest deleteRequest = new DeleteRequest(indexName, "product", String.valueOf(((Product) object).getId()));
			System.out.println("已经从ElasticSearch服务器上删除id=" + ((Product) object).getId() + "的product文档");
			client.delete(deleteRequest);
		}
	}

// 获得指定type指定id的数据 json
	public static Map getDocument(String type, String id) throws IOException {
		// TODO Auto-generated method stub
		GetRequest request = new GetRequest(indexName, type, id);

		GetResponse response = client.get(request);

		if (!response.isExists()) {
			System.out.println("检查到服务器上 " + type + " id=" + id + "的文档不存在");
			return null;
		} else {
			String source = response.getSourceAsString();
			System.out.print("获取到服务器上 " + type + " id=" + id + "的文档内容是：");
			System.out.println(source);
			return response.getSourceAsMap();
		}
	}

// 插入指定type，数据
	public static void addDocument(Object object) throws IOException {
		Map<String, Object> jsonMap = new HashMap<>();
		if (object instanceof Product) {
			jsonMap.put("id", ((Product) object).getId());
			jsonMap.put("name", ((Product) object).getName());
			jsonMap.put("price", ((Product) object).getPrice());
			jsonMap.put("detail", ((Product) object).getDetail());
			IndexRequest indexRequest = new IndexRequest(indexName, "product", String.valueOf(((Product) object).getId()))
					.source(jsonMap);
			client.index(indexRequest);
			System.out.println("已经向ElasticSearch服务器增加Product：" + object);
		}

	}

// 更新数据
	public static void updateDocument(Object object) throws IOException {

		if (object instanceof Product) {
			UpdateRequest updateRequest = new UpdateRequest(indexName, "product", String.valueOf(((Product) object).getId()))
					.doc("name", ((Product) object).getId()).doc("price", ((Product) object).getPrice())
					.doc("detail", ((Product) object).getDetail());
			client.update(updateRequest);
			System.out.println("已经在ElasticSearch服务器修改产品为：" + object);
		}

	}

	private static boolean checkExistIndex(String indexName) throws IOException {
		boolean result = true;
		try {

			OpenIndexRequest openIndexRequest = new OpenIndexRequest(indexName);
			client.indices().open(openIndexRequest).isAcknowledged();

		} catch (ElasticsearchStatusException ex) {
			String m = "Elasticsearch exception [type=index_not_found_exception, reason=no such index]";
			if (m.equals(ex.getMessage())) {
				result = false;
			}
		}
		if (result)
			System.out.println("索引:" + indexName + " 是存在的");
		else
			System.out.println("索引:" + indexName + " 不存在");

		return result;

	}

	private static void deleteIndex(String indexName) throws IOException {
		DeleteIndexRequest request = new DeleteIndexRequest(indexName);
		DeleteResponse resp = new DeleteResponse();
		//client.indices().deleteAsync(request,);
		System.out.println("删除了索引：" + indexName);

	}

	private static void createIndex(String indexName) throws IOException {
		// TODO Auto-generated method stub
		CreateIndexRequest request = new CreateIndexRequest(indexName);
		client.indices().create(request);
		System.out.println("创建了索引：" + indexName);
	}

// 批量插入
//private static void batchInsert(List<Product> products) throws IOException {
//    // TODO Auto-generated method stub
//    BulkRequest request = new BulkRequest();
//
//    for (Product product : products) {
//        Map<String,Object> m  = product.toMap();
//        IndexRequest indexRequest= new IndexRequest(indexName, "product", String.valueOf(product.getId())).source(m);
//        request.add(indexRequest);
//    }
//
//    client.bulk(request);
//    System.out.println("批量插入完成");
//}
	public static String getIndexName() {
		return indexName;
	}

	public static void setIndexName(String indexName) {
		ElasticUtils.indexName = indexName;
	}

	public static RestHighLevelClient getClient() {
		return client;
	}

	public static void setClient(RestHighLevelClient client) {
		ElasticUtils.client = client;
	}

}
