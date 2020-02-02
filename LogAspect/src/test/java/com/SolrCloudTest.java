package com;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.FieldAnalysisRequest;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.AnalysisResponseBase.AnalysisPhase;
import org.apache.solr.client.solrj.response.AnalysisResponseBase.TokenInfo;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.FieldAnalysisResponse;
import org.apache.solr.client.solrj.response.FieldAnalysisResponse.Analysis;
import org.apache.solr.client.solrj.response.PivotField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.TermsResponse;
import org.apache.solr.client.solrj.response.TermsResponse.Term;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.AnalysisParams;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.entity.TbItem;
import com.service.impl.StandardService;
import com.util.JsonMapper;
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
	public void queryFacet() {
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

	private String qt = "title";

	private String facetField = "title";

	private String tokenizer = "com.chenlb.mmseg4j.analysis.MMSegTokenizer";


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
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void fieldAnalysis() throws SolrServerException, IOException {
		SolrServer solrServer = new HttpSolrServer(SOLR_PATH);
		SolrQuery query = new SolrQuery();
		query.add(CommonParams.QT, "/analysis/field"); // query type
		query.add(AnalysisParams.FIELD_VALUE, "华为 mate pro 20");
		//query.add(AnalysisParams.FIELD_TYPE, qt);
		query.add(AnalysisParams.FIELD_TYPE, "text_cn");
		//query.add("q", "*:*");
		try {
			// 对响应进行解析
			QueryResponse response = solrServer.query(query);
			//System.out.println(response.getResults());
			NamedList<Object> analysis = (NamedList<Object>) response.getResponse().get("analysis");// analysis node
			NamedList<Object> field_types = (NamedList<Object>) analysis.get("field_types");// field_types node
			NamedList<Object> textSimple = (NamedList<Object>) field_types.get("text_cn");// textSimple node
			NamedList<Object> index = (NamedList<Object>) textSimple.get("index");// index node
			List<SimpleOrderedMap<String>> list = (ArrayList<SimpleOrderedMap<String>>) index.get("org.wltea.analyzer.lucene.IKTokenizer");// tokenizer
			Set<String> wordSet = new HashSet<String>();
			// 在每个词条中间加上空格，为每个词条进行或运算
			if(list !=null && !list.isEmpty()) {
				for (Iterator<SimpleOrderedMap<String>> iter = list.iterator(); iter.hasNext();) {
					wordSet.add(iter.next().get("text"));
				}
				System.out.println(wordSet);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		SolrServer server = new HttpSolrServer(SOLR_PATH);
		FieldAnalysisRequest request = new FieldAnalysisRequest();
	 
		request.setFieldNames(java.util.Collections.singletonList("title"));//这里可以设置多个fieldName，或者是fieldType，但是我们这里只是一个，用来作为例子
		request.setFieldValue("华为 mate pro 20");//设置建立索引时的分词的内容
		request.setQuery("华为 mate pro 20");//设置查询时的分词的内容
//		request.setFieldValue("我来自中国山东 我们那里有很多好吃的");//设置建立索引时的分词的内容
//		request.setQuery("我来自中国山东 我们那里有很多好吃的");//设置查询时的分词的内容
		
		FieldAnalysisResponse response = request.process(server);
		Analysis sis = response.getFieldNameAnalysis("title");//指定要获得的域的名字，因为上面是setFieldNames，所以这里是getFieldNameAnalysis，如果上面是setFieldTypes，则这里就要调用getFieldTypeAnalysis
			
		// 获得fieldValue的分词结果
		Iterator<AnalysisPhase> result = sis.getIndexPhases().iterator();
		while(result.hasNext()){
			AnalysisPhase pharse = result.next();
			List<TokenInfo> list = pharse.getTokens();
	        for (TokenInfo info : list) {
	        	System.out.println(info.getText());//info还有很多的属性，这里没有设置
	        }
		}
			
		// 获得query的
		result = sis.getQueryPhases().iterator();
		while(result.hasNext()){
	        AnalysisPhase pharse = result.next();
			List<TokenInfo> list = pharse.getTokens();
		       for (TokenInfo info : list) {
		       	System.out.println(info.getText());
		       }
		}
	}
	
	
	private static String NEW_SOLR_PATH = "http://127.0.0.1:9080/solr/testcore";
	@Test
	public void TestTerms() throws Exception{
		//http://localhost:9080/solr/testcore/terms?terms.fl=category&terms.prefix=%E5%A5%B3%E8%A3%85
        //[1]获取连接
		SolrServer solrServer = new HttpSolrServer(NEW_SOLR_PATH);
        //[2]创建SolrQuery
        SolrQuery query = new SolrQuery();
        //[3]设置参数
        query.setRequestHandler("/terms");//设置requestHandler
        query.setTerms(true);//开启terms
        query.setTermsLimit(10);//设置每页返回的条目数量
        query.setTermsLower("女装");// 可选的. 这个term开始。如果不指定,使用空字符串,这意味着从头开始的。
        query.setTermsPrefix("女装");//可选的. 限制匹配，设置terms前缀是以什么开始的。
        query.addTermsField("category");//必须的. 统计的字段
        query.setTermsMinCount(1);//可选的. 设置最小统计个数
        //[4]创建QueryRequest 获取 TermsResponse 
        QueryRequest request = new QueryRequest(query);
        QueryResponse process = request.process(solrServer);
        TermsResponse termsResponse = process.getTermsResponse();
        //[5]遍历结果
        List<Term> terms = termsResponse.getTerms("category");
        for (Term term : terms) {
            System.out.println(term.getTerm() + ":\t"+ term.getFrequency());
        }
    }
	
	/**
     * terms词频统计测试2
     * @throws Exception 
     */
    @Test
    public void TestTerms2() throws Exception{
        //[1]实例化HttpSolrClient，以获取与HttpSolrClient的连接
    	SolrServer solrServer = new HttpSolrServer(NEW_SOLR_PATH);
    	//[2]创建SolrQuery
        SolrQuery query = new SolrQuery();
        //[3]设置查询参数  
        query.set("q", "*:*");  
        query.set("qt","/terms");//设置requestHandler
        
        // parameters settings for terms requesthandler  
        // 参考 http://wiki.apache.org/solr/termscomponent  
        query.set("terms","true");//开启terms
        query.set("terms.fl", "name");//必须的. 统计的字段  
        
        //指定下限  
        // query.set("terms.lower", ""); // term lower bounder开始的字符  ，// 可选的. 这个term开始。如果不指定,使用空字符串,这意味着从头开始的。
        // query.set("terms.lower.incl", "true");  
        // query.set("terms.mincount", "1");//可选的. 设置最小统计个数  
        // query.set("terms.maxcount", "100"); //可选的. 设置最大统计个数   
        
        //http://localhost:8983/solr/terms?terms.fl=text&terms.prefix=家//  
        //using for auto-completing   //自动完成  
        //query.set("terms.prefix", "家");//可选的. 限制匹配，设置terms前缀是以什么开始的。  
        query.set("terms.regex", "家+.*");  
        query.set("terms.regex.flag", "case_insensitive");  
         
        //query.set("terms.limit", "20"); //设置每页返回的条目数量 
        //query.set("terms.upper", ""); //结束的字符  
        //query.set("terms.upper.incl", "false");  
        //query.set("terms.raw", "true");  
        
        query.set("terms.sort", "count");//terms.sort={count|index} -如果count，各种各样的条款术语的频率（最高计数第一）。 如果index，索引顺序返回条款。默认是count     
        
        // 查询并获取结果  
        QueryResponse response = solrServer.query(query);  
        // 获取相关的查询结果  
        if (response != null) {  
            TermsResponse termsResponse = response.getTermsResponse();  
            if (termsResponse != null) {  
                Map<String, List<TermsResponse.Term>> termsMap = termsResponse.getTermMap();  
                for (Map.Entry<String, List<TermsResponse.Term>> termsEntry : termsMap.entrySet()) {  
                    //System.out.println("Field Name: " + termsEntry.getKey());  
                    List<TermsResponse.Term> termList = termsEntry.getValue();  
                    for (TermsResponse.Term term : termList) {  
                        System.out.println(term.getTerm() + " : "+ term.getFrequency());  
                    }  
                }  
            }  
        }  
    }
    
    public static String getSerchTerms(String keywords) throws Exception {
    	SolrServer solrServer = new HttpSolrServer(NEW_SOLR_PATH);
        // 创建查询参数以及设定的查询参数  
        SolrQuery query = new SolrQuery();  
        query.set("q", "*:*");  
        query.set("qt", "/terms");  
        query.set("terms", "true");  
        query.set("terms.fl", "name");  
        //tomcat8之前默认是ISO8859-1,tomcat8及以后，是UTF-8,自己百度一下解决办法
        //推荐学习地址： https://www.w3cschool.cn/regexp/tfua1pq5.html
        query.set("terms.regex", keywords+"+.*"); 
        query.set("terms.regex.flag", "case_insensitive");  
        query.set("terms.sort", "count");//terms.sort={count|index} -如果count，各种各样的条款术语的频率（最高计数第一）。 如果index，索引顺序返回条款。默认是count  
        // 查询并获取相应的结果！  
        QueryResponse response = solrServer.query(query);  
        // 获取相关的查询结果  
        List<TermsResponse.Term> termList=null;
        if (response != null) { 
          TermsResponse termsResponse = response.getTermsResponse();  
           if (termsResponse != null) {
              Map<String, List<TermsResponse.Term>> termsMap = termsResponse.getTermMap();  
                for (Map.Entry<String, List<TermsResponse.Term>> termsEntry : termsMap.entrySet()) {  
                    // System.out.println("Field Name: " + termsEntry.getKey());  
                    termList = termsEntry.getValue();  
                    for (TermsResponse.Term term : termList) {  
                        System.out.println(term.getTerm() + " : "+ term.getFrequency());  
                    }  
                }  
            }  
        }
      String jsonstr =JsonMapper.toJsonString(termList);
      return jsonstr;  
    } 
}
