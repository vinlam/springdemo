package com.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.FilterQuery;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleFilterQuery;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Service;

import com.entity.TbItem;
import com.service.ItemSearchService;

@Service
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate solrTemplate;

    /**
     * 添加
     * @param tbItem
     */
    @Override
    public void add(TbItem tbItem) {
        solrTemplate.saveBean(tbItem);
        solrTemplate.commit();
    }

    /**
     * 按主键查询
     * @param id
     * @return
     */
    @Override
    public TbItem searchById(long id) {
        TbItem tbItem = solrTemplate.getById(id, TbItem.class);
        return tbItem;
    }

    /**
     * 按主键删除
     * @param id
     * @return
     */
    @Override
    public void deleteById(String id) {
        solrTemplate.deleteById(id);
        solrTemplate.commit();
    }

    /**
     * 分页查询
     * @param start
     * @param size
     */
    @Override
    public Page<TbItem> pageQuery(int start,int size){
        Query query=new SimpleQuery("*:*");
        query.setOffset(start);//开始索引（默认0）start:(page-1)*rows
        query.setRows(size);//每页记录数(默认10)//rows:rows
        return solrTemplate.queryForPage(query, TbItem.class);
    }

    /**
     * 删除所有
     */
    @Override
    public void deleteAll() {
        Query query = new SimpleQuery("*:*");
        solrTemplate.delete(query);
        solrTemplate.commit();
    }

    /**
     * 搜索
     * @param searchMap
     * @return
     */
    @Override
    public Map<String, Object> search(Map searchMap) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //1.先获取从页面传递过来的参数的值   通过KEY获取
        String keywords = (String)searchMap.get("keywords");//获取主查询的条件

        //2.设置主查询的条件
        HighlightQuery query =  new SimpleHighlightQuery();
        Criteria criteria = new Criteria("item_keywords");
        criteria.is(keywords);
        query.addCriteria(criteria);
        //3.设置高亮查询的条件   设置高亮显示的域  设置前缀  设置后缀
        HighlightOptions hightoptions = new HighlightOptions();
        hightoptions.addField("title");//设置高亮显示的域
        hightoptions.setSimplePrefix("<em style=\"color:red\">");
        hightoptions.setSimplePostfix("</em>");
        query.setHighlightOptions(hightoptions);

        //4.设置过滤条件  商品分类的过滤
        if (searchMap.get("category") != null && !"".equals(searchMap.get("category"))) {
            Criteria fiterCriteria = new Criteria("category").is(searchMap.get("category"));
            FilterQuery filterQuery = new SimpleFilterQuery(fiterCriteria);
            query.addFilterQuery(filterQuery);
        }

        //5.设置品牌的过滤
        if (searchMap.get("brand") != null && !"".equals(searchMap.get("brand"))) {
            Criteria fitercriteria = new Criteria("brand").is(searchMap.get("brand"));
            FilterQuery filterquery = new SimpleFilterQuery(fitercriteria);
            query.addFilterQuery(filterquery);
        }

        //6.设置规格的过滤条件
        if (searchMap.get("spec") != null) {
            Map<String,String> spec = (Map<String, String>) searchMap.get("spec");

            for (String key : spec.keySet()) {
                String value = spec.get(key);
                Criteria fiterCriteria = new Criteria("spec_"+key).is(value);//item_spec_网络：3G
                FilterQuery filterquery = new SimpleFilterQuery(fiterCriteria);
                query.addFilterQuery(filterquery);//
            }
        }

        //7.按照价格筛选
        if (StringUtils.isNotBlank((CharSequence) searchMap.get("price"))){
            //item_price:[10 TO 20]
            String[] split = searchMap.get("price").toString().split("-");
            SimpleFilterQuery filterQuery = new SimpleFilterQuery();
            Criteria itemPrice = new Criteria("price");
            //如果有* 语法是不支持的
            if(!split[1].equals("*")){
                itemPrice.between(split[0],split[1],true,true);
            }else {
                itemPrice.greaterThanEqual(split[0]);
            }
            filterQuery.addCriteria(itemPrice);
            query.addFilterQuery(filterQuery);
        }
        //8.分页查询
        Integer pageNo = (Integer) searchMap.get("pageNo");//提取页面

        if (pageNo==null){
            pageNo =1;
        }
        Integer pageSize = (Integer) searchMap.get("pageSize");//每页记录数
        if (pageSize==null){
            pageSize=20;
        }
        query.setOffset((pageNo-1)*pageSize);//从第几条记录查询
        query.setRows(pageSize);

        //9.排序
        String sortValue = (String) searchMap.get("sort");
        String sortField = (String) searchMap.get("sortField");//排序字段
        if (StringUtils.isNotBlank(sortField)){
            if (sortValue.equals("ASC")){
                Sort sort = new Sort(Sort.Direction.ASC, "" + sortField);
                query.addSort(sort);
            }
            if (sortValue.equals("DESC")){
                Sort sort = new Sort(Sort.Direction.DESC, "" + sortField);
                query.addSort(sort);
            }
        }

        //10.执行查询 获取高亮数据
        HighlightPage<TbItem> highlightPage = solrTemplate.queryForHighlightPage(query, TbItem.class);

        List<HighlightEntry<TbItem>> highlighted = highlightPage.getHighlighted();
        for (HighlightEntry<TbItem> tbItemHighlightEntry : highlighted) {
            TbItem entity = tbItemHighlightEntry.getEntity();//实体对象 现在是没有高亮的数据的

            List<HighlightEntry.Highlight> highlights = tbItemHighlightEntry.getHighlights();
            //如有高亮，就取高亮
            if(highlights!=null && highlights.size()>0 && highlights.get(0)!=null &&  highlights.get(0).getSnipplets()!=null && highlights.get(0).getSnipplets().size()>0) {
                entity.setTitle(highlights.get(0).getSnipplets().get(0));
            }
        }
        List<TbItem> tbItems = highlightPage.getContent();//获取高亮的文档的集合
        //11.执行查询
        System.out.println("结果"+tbItems.size());
        //12.获取结果集  返回
        resultMap.put("rows",tbItems);
        resultMap.put("totalPages",highlightPage.getTotalPages());//返回总页数
        resultMap.put("total",highlightPage.getTotalElements());//返回总记录数
        return resultMap;
    }
}