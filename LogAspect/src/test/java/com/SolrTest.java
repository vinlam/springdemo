//package com;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collection;
//
//import org.apache.solr.client.solrj.SolrQuery;
//import org.apache.solr.client.solrj.SolrServerException;
//import org.apache.solr.client.solrj.impl.CloudSolrClient;
//import org.apache.solr.client.solrj.response.QueryResponse;
//import org.apache.solr.common.SolrDocument;
//import org.apache.solr.common.SolrDocumentList;
//import org.apache.solr.common.SolrInputDocument;
//
//import com.common.solr.SolrClient;
//
////SolrCloud 索引增删查
//public class SolrTest {
//
//	private static CloudSolrClient cloudSolrClient;
//
//	private static synchronized CloudSolrClient getCloudSolrClient(final String zkHost) {
//		if (cloudSolrClient == null) {
//			try {
//				cloudSolrClient = new CloudSolrClient(zkHost);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return cloudSolrClient;
//	}
//
//	private static void addIndex(SolrClient solrClient) {
//		try {
//			SolrInputDocument doc1 = new SolrInputDocument();
//			doc1.addField("id", "421245251215121452521251");
//			doc1.addField("name", "张三");
//			doc1.addField("age", 30);
//			doc1.addField("desc", "张三是个农民，勤劳致富，奔小康");
//
//			SolrInputDocument doc2 = new SolrInputDocument();
//			doc2.addField("id", "4224558524254245848524243");
//			doc2.addField("name", "李四");
//			doc2.addField("age", 45);
//			doc2.addField("desc", "李四是个企业家，白手起家，致富一方");
//
//			SolrInputDocument doc3 = new SolrInputDocument();
//			doc3.addField("id", "2224558524254245848524299");
//			doc3.addField("name", "王五");
//			doc3.addField("age", 60);
//			doc3.addField("desc", "王五好吃懒做，溜须拍马，跟着李四，也过着小康的日子");
//
//			Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
//			docs.add(doc1);
//			docs.add(doc2);
//			docs.add(doc3);
//			solrClient.add(docs);
//			solrClient.commit();
//		} catch (SolrServerException e) {
//			System.out.println("Add docs Exception !!!");
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			System.out.println("Unknowned Exception!!!!!");
//			e.printStackTrace();
//		}
//	}
//
//	public static void search(SolrClient solrClient, String String) {
//		SolrQuery query = new SolrQuery();
//		query.setQuery(String);
//		try {
//			QueryResponse response = solrClient.query(query);
//			SolrDocumentList docs = response.getResults();
//
//			System.out.println("文档个数：" + docs.getNumFound());
//			System.out.println("查询时间：" + response.getQTime());
//
//			for (SolrDocument doc : docs) {
//				String id = (String) doc.getFieldValue("id");
//				String name = (String) doc.getFieldValue("name");
//				Integer age = (Integer) doc.getFieldValue("age");
//				String desc = (String) doc.getFieldValue("desc");
//				System.out.println("id: " + id);
//				System.out.println("name: " + name);
//				System.out.println("age: " + age);
//				System.out.println("desc: " + desc);
//				System.out.println();
//			}
//		} catch (SolrServerException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			System.out.println("Unknowned Exception!!!!");
//			e.printStackTrace();
//		}
//	}
//
//	public void deleteAllIndex(SolrClient solrClient) {
//		try {
//			solrClient.deleteByQuery("*:*");// delete everything!
//			solrClient.commit();
//		} catch (SolrServerException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			System.out.println("Unknowned Exception !!!!");
//			e.printStackTrace();
//		}
//	}
//
//	public static void main(String[] args) throws IOException {
//		 final String zkHost = "192.168.100.90:2181";       
//         final String  defaultCollection = "testcollection";  
//         final int  zkClientTimeout = 20000;  
//         final int zkConnectTimeout = 1000;  
//           
//         CloudSolrClient cloudSolrClient = getCloudSolrClient(zkHost);         
//         System.out.println("The Cloud cloudSolrClient Instance has benn created!");            
//         cloudSolrClient.setDefaultCollection(defaultCollection);  
//         cloudSolrClient.setZkClientTimeout(zkClientTimeout);  
//         cloudSolrClient.setZkConnectTimeout(zkConnectTimeout);                   
//         cloudSolrClient.connect();  
//         System.out.println("The cloud Server has been connected !!!!");      
//         //创建索引
//         SolrCloudTest.addIndex(cloudSolrClient);
//         //查询
//         SolrCloudTest.search(cloudSolrClient, "context:李四");      
//         cloudSolrClient.close();
//	}
//}