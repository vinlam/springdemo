package com;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CommonParams;

import com.common.solr.SolrClient;
 
/**
 * 测试edismax的代码实现自定义权重排序股则
 *
 */
public class ScoreByEdismax {
 
    private final static String baseURL = "http://127.0.0.1:9080/solr/itemcore";
 
 
    public static void main(String[] args) throws Exception{
 
        scoreBySum() ;
 
        System.out.println("=====分割线==========");
 
        scoreByProportion();
 
    }
 
    /**
     * 
     * @描述：按照各个数值的比重来进行权重计算  title 10% brand 45% seller 45%  --假设总分为100分 那么 分别最大分为 10 45 45
     * @return void
     * @exception
     * @createTime：2016年4月18日
     * @author: 
     * @throws IOException 
     * @throws SolrServerException 
     */
    public static void scoreByProportion() throws SolrServerException, IOException{
        //先查询出来三个字段中每个字段的最大值--编写好计算权重的公式---实际项目中应该还要加入是否存在的判断
 
        long titleMax = getMaxForField("title");
        long brandMax = getMaxForField("brand");
        long sellerMax = getMaxForField("seller");
 
        String scoreMethod = "sum(product(div(title,"+titleMax+"),10),product(div(brand,"+brandMax+"),45),product(div(seller,"+sellerMax+"),45))^1000000000";
 
        SolrQuery query = new SolrQuery();
        query.set(CommonParams.Q, "国美*");
        query.set(CommonParams.FL,"id","price","title","brand","seller","score");
 
        query.set("defType","edismax");
 
        query.set("bf", scoreMethod);
 
 
        QueryResponse response = getServer().query(query);
 
        resultShow(response);
 
    }
    /**
     * @描述：XXXXXXX
     * @return
     * @return long
     * @exception
     * @createTime：2016年4月18日
     * @author: 
     * @throws IOException 
     * @throws SolrServerException 
     */
    private static long getMaxForField(String fieldName) throws SolrServerException, IOException{
 
        SolrQuery query = new  SolrQuery();
 
        query.set(CommonParams.Q, "*:*");
        query.set(CommonParams.FL, fieldName);
        query.setSort(fieldName, ORDER.desc);
        query.setRows(1);
 
        QueryResponse countResponse = getServer().query(query);
 
        SolrDocument maxCount = countResponse.getResults().get(0);
 
        //long result = (long) maxCount.getFieldValue(fieldName);
        long result = (long) maxCount.size();
        System.out.println(fieldName + ":" + result);
        return result;
    }
 
 
    /**
     * 
     * @描述:按照 title brand 和 seller 的 和的数量来进行权重计算
     * @return void
     * @exception
     * @createTime：2016年4月18日
     * @author: 
     * @throws IOException 
     * @throws SolrServerException 
     */
    public static  void  scoreBySum() throws SolrServerException, IOException{
 
        SolrQuery query  = new SolrQuery();
 
        query.set(CommonParams.Q, "华为");
 
        query.set(CommonParams.FL,"id","title","price","brand","seller","score");
 
        //开启edismax方式来进行自定义权重算法
        query.set("defType", "edismax");
 
        query.set("bf","sum(title,brand,seller)^1000000000");
 
 
        QueryResponse response = getServer().query(query);
 
        resultShow(response);
 
    }
 
    /**
     * 
     * @描述：查询结果显示类
     * @param response
     * @return void
     * @exception
     * @createTime：2016年4月18日
     * @author: 
     */
    private static void resultShow(QueryResponse response){
 
        int time = response.getQTime();
        System.out.println("响应时间:"+ time+"ms");
 
        SolrDocumentList results = response.getResults();
        long numFound = results.getNumFound();
        System.out.println("总数量:"+numFound);
//       document.addField("title", "华为MateP30");
//		document.addField("price", 6888.99);
//		document.addField("id", id + 1L);
//		document.addField("brand", "HUAWEI-华为");
//		document.addField("seller", "华为官方专卖店");
//		document.addField("goodsId", "20001");
//		document.addField("category", "mobile");
        for (SolrDocument doc : results) {
 
           System.out.println("id:"+ doc.getFieldValue("id").toString());
           System.out.println("title:"+ doc.getFieldValue("title").toString());
           System.out.println("brand:"+ doc.getFieldValue("brand").toString());
           System.out.println("seller:"+ doc.getFieldValue("seller").toString());
           System.out.println("price:"+ doc.getFieldValue("price").toString());
           System.out.println("score:"+ doc.getFieldValue("score").toString());
           System.out.println();
        }
    }
 
 
    /**
     * 
     * @描述：获取单机版本的连接信息
     * @return
     * @return SolrServer
     * @exception
     * @createTime：2016年4月18日
     */
    public static SolrServer getServer(){
 
        SolrServer server = new HttpSolrServer(baseURL);
 
        return server;
    }
 
 
}