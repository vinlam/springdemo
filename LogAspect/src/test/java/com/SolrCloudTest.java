package com;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.PivotField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.TermsResponse.Term;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.entity.TbItem;
import com.util.JsonUtil;

public class SolrCloudTest {
	@Test
	public void testSolrCloudAddDocument() throws Exception {
		// 第一步：把solrJ相关的jar包添加到工程中。
		// 第二步：创建一个SolrServer对象，需要使用CloudSolrServer子类。构造方法的参数是zookeeper的地址列表。
		// 参数是zookeeper的地址列表，使用逗号分隔
		CloudSolrServer solrServer = new CloudSolrServer("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183");
		// 第三步：需要设置DefaultCollection属性。
		solrServer.setDefaultCollection("itemcore");

		TbItem item = new TbItem();
		item.setId(1);
		item.setBrand("华为");
		item.setCategory("手机");
		item.setGoodsId(1L);
		item.setSeller("华为2号专卖店");
		item.setTitle("华为Mate 9");
		item.setPrice(new BigDecimal("1889.99"));
		TbItem item2 = new TbItem();
		item2.setId(2);
		item2.setBrand("华为");
		item2.setCategory("手机");
		item2.setGoodsId(2L);
		item2.setSeller("华为2号专卖店");
		item2.setTitle("华为Mate20");
		item2.setPrice(new BigDecimal("4889.99"));
		List<TbItem> list = new ArrayList<TbItem>();
		list.add(item);
		list.add(item2);
		DocumentObjectBinder binder = solrServer.getBinder();
		ArrayList<SolrInputDocument> doclist =  new ArrayList<>(list.size());
	    for (TbItem _item : list) {
	    	doclist.add(binder.toSolrInputDocument(_item));
	    }
		long id = (long) (Math.random() * 10000);

//		// 第四步：创建一SolrInputDocument对象。
		SolrInputDocument document = new SolrInputDocument();
		// 第五步：向文档对象中添加域
		document.addField("title", "华为MateP30");
		document.addField("price", 6888.99);
		document.addField("id", id + 1L);
		document.addField("brand", "HUAWEI-华为");
		document.addField("seller", "华为官方专卖店");
		document.addField("goodsId", "20001");
		document.addField("category", "mobile");
		SolrInputDocument doc = new SolrInputDocument();

		// MappingSolrConverter c = new MappingSolrConverter(mappingContext)
		// 第五步：向文档对象中添加域
//		doc.addField("title", "华为P9");
//		doc.addField("price", new BigDecimal("2281.99"));
//		doc.addField("id", id + 1L);
//		doc.addField("brand", "华为");
//		doc.addField("seller", "华为官方专卖店");
//		doc.addField("goodsId", "60001");
//		doc.addField("category", "手机");
		// 第六步：把文档对象写入索引库。
		// solrServer.add(document);
		doc = solrServer.getBinder().toSolrInputDocument(item);
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		docs.add(document);
		// docs.add(doc);
		// solrServer.addBean(item);
		solrServer.add(doc);
		//solrServer.add(doclist);
		// 第七步：提交。
		solrServer.commit();

	}

	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	// solr的服务器地址
	private static final String SOLR_PATH = "http://127.0.0.1:9080/solr/itemcore";

	public static void main(String[] args) throws Exception {

		// 清除索引
		//deleteAll();

		// 建立索引
		// index();

		//queryByPage(1, 20);
		//getterms();
		getAllData();
	}
	
	public static void getterms() throws SolrServerException {
		SolrServer server = new HttpSolrServer(SOLR_PATH);
		 SolrQuery query = new SolrQuery();
		    query.setRequestHandler("/terms");
		    query.setTerms(true);
		    query.setTermsLimit(5);
		    //query.setTermsLower("o");
		    //query.setTermsPrefix("O");
		    //query.setTermsLowerInclusive(true);
		    query.addTermsField("attrvalue");
		    query.addTermsField("title");
		    //query.setTermsRegex(".*o");
		    query.setTermsMinCount(1);

		    QueryRequest request = new QueryRequest(query);
		    //List<Term> terms = request.process(server).getTermsResponse().getTerms("attrvalue");
		    Map<String, List<Term>> terms = request.process(server).getTermsResponse().getTermMap();
		    System.out.println(JsonUtil.beanToJson(terms));
	}

	/**
	 * 描述:分页查询数据
	 * 
	 * @param pageNow  当前页
	 * @param pageSize 页面大小
	 * @throws Exception
	 */
	public static void queryByPage(int pageNow, int pageSize) throws Exception {
		SolrServer server = new HttpSolrServer(SOLR_PATH);
		CloudSolrServer solrServer = new CloudSolrServer("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183");

		SolrQuery solrQuery = new SolrQuery();

		int start = (pageNow - 1) * pageSize;
		int end = pageNow * pageSize;

		System.out.println("开始" + start);
		System.out.println("结束" + end);

		// 添加查询
		solrQuery.add("q", "*:*");
		// 开始
		solrQuery.setStart(start);

		// 该参数就是控制条数
		// 设定为Integer最大值
		solrQuery.setRows(end);

		// 查询数据 ,默认最大是10
		QueryResponse response = server.query(solrQuery);

		SolrDocumentList docs = response.getResults();
		for (SolrDocument doc : docs) {
			String id = doc.get("id").toString();
			String title = doc.get("title").toString();
			String content = doc.get("content").toString();
			System.out.printf("%s:%s:%s\r\n", id, title, content);
		}
	}

	/**
	 * 检索到所有的数据 描述:
	 */
	public static void getAllData() throws Exception {
		SolrServer server = new HttpSolrServer(SOLR_PATH);

		SolrQuery solrQuery = new SolrQuery();

		// 添加查询
		solrQuery.add("q", "*:*");
		// 开始
		solrQuery.setStart(0);
		// solrQuery.set("start", 0);

		// 该参数就是控制条数
		// 设定为Integer最大值
		solrQuery.setRows(Integer.MAX_VALUE);
		// solrQuery.set("rows", Integer.MAX_VALUE);
		DocumentObjectBinder binder = server.getBinder();
		// 查询数据 ,默认最大是10
		QueryResponse response = server.query(solrQuery);
		
		List<TbItem> items = response.getBeans(TbItem.class);
		for(TbItem item :items) {
			System.out.println(item.getId());
		}
		SolrDocumentList docs = response.getResults();
		for (SolrDocument doc : docs) {
			String id = doc.get("id").toString();
			String title = doc.get("title").toString();
			String seller = doc.get("seller").toString();
			System.out.printf("%s:%s:%s\r\n", id, title, seller);
		}
	}

	/**
	 * 描述:添加索引
	 * 
	 * @throws Exception
	 * @throws SolrServerException
	 */
	public static void index() throws SolrServerException, Exception {

		SolrServer server = new HttpSolrServer(SOLR_PATH);

		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		for (int i = 1; i <= 100; i++) {
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", Integer.toString(i));
			doc.addField("author", "张三" + i);
			doc.addField("title", "Solr" + i);
			doc.addField("content", "Solr是xx" + i);

			docs.add(doc);
		}
		// 添加文档
		server.add(docs);

		// 提交任务
		server.commit();
	}
	
	/**
     * 将SolrDocumentList转换成BeanList
     * @param records
     * @param clazz
     * @return
     */
    public static Object toBeanList(SolrDocumentList records, Class clazz){
        List list = new ArrayList();
        for(SolrDocument record : records){
            list.add(toBean(record,clazz));
        }
        return list;
    }
    
    /**
     * 将SolrDocument转换成Bean
     * @param record
     * @param clazz
     * @return
     */
    public static Object toBean(SolrDocument record, Class clazz){
        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields){
            Object value = record.get(field.getName());
            try {
                org.apache.commons.beanutils.BeanUtils.setProperty(obj, field.getName(), value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

	/**
	 * 描述:清空Solr里面的所有数据
	 */
	public static void deleteAll() throws Exception {
		SolrServer server = new HttpSolrServer(SOLR_PATH);
		// 清空数据
		server.deleteByQuery("*:*");
		server.commit();
	}

	@Test
	public void queryNewsType() {
		SolrServer server = new HttpSolrServer(SOLR_PATH);
		// CloudSolrServer solrServer = new
		// CloudSolrServer("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183");
		SolrQuery sQuery = new SolrQuery();
		try {
			// 第三步：需要设置DefaultCollection属性。
			// solrServer.setDefaultCollection("itemcore");
			sQuery.setFacet(true);
			sQuery.addFacetField(new String[] { "title" });// 设置需要facet的字段
			sQuery.add("facet.pivot", "title,seller");// 根据这两维度来分组查询
			sQuery.setQuery("*:*");
			//sQuery.set("explainOther", "on");
			sQuery.set("wt", "json");
            //sQuery.set("rows", "20");
            //sQuery.set("version", "2.2");
            sQuery.set("fl", "");
            //sQuery.set("q","");
            //sQuery.set( "facet" , "true");
            //sQuery.set("facet.field", "");
            sQuery.set("qt", "");
            sQuery.set("fq", "");
			QueryResponse response = server.query(sQuery);
			List<FacetField> facets = response.getFacetFields();// 返回的facet列表
			NamedList<List<PivotField>> namedList = response.getFacetPivot();
			
			System.out.println(namedList);// 底下为啥要这样判断，把这个值打印出来，你就明白了
			if (namedList != null) {
				List<PivotField> pivotList = null;
				for (int i = 0; i < namedList.size(); i++) {
					pivotList = namedList.getVal(i);
					if (pivotList != null) {
						// ReportNewsTypeDTO dto = null;
						for (PivotField pivot : pivotList) {
							List<PivotField> fieldList = pivot.getPivot();
							if (fieldList != null) {
								for (PivotField pfield : fieldList) {
									int proValue = Integer.parseInt((String) pfield.getValue()) ;
									int count = pfield.getCount();
									String field = pfield.getField();
									System.out.println("field:" + field + " value:" + proValue + " count:"+count);
									if (proValue == 1) {
										
										// pos = count;
									} else {
										// neg = count;
									}
								}
							}
						}

					}
				}
			}

			for (FacetField facet : facets) {
				System.out.println(facet.getName());
				System.out.println("----------------");
				List<Count> counts = facet.getValues();
				for (Count count : counts) {
					System.out.println(count.getName() + ":" + count.getCount());
				}
				System.out.println();
			}

		} catch (SolrServerException e) {
			System.out.println("查询solr失败" + e.getMessage());
			e.printStackTrace();
		} finally {
			// solrServer.shutdown();
			// solrServer = null;
			server.shutdown();
			server = null;
		}
		// return list;
	}
}
