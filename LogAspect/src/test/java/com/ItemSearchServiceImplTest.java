package com;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.entity.TbItem;
import com.fasterxml.jackson.core.JsonParser;
import com.service.ItemSearchService;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ItemSearchServiceImplTest {

    @Autowired
    private ItemSearchService itemSearchService;

    @Test
    public void search() {
        //构造json字符串
        String searchStr = new String("{'keywords':'华为','category':'手机','brand':'华为'," +
                "'spec':{'机身内存':'16G','网络':'联通3G'},'price':'1000-3000'," +
                "'pageNo':1,'pageSize':10,'sortField':'price','sort':'ASC'}");
        searchStr = "{'keywords':'华为','category':'手机','brand':'华为','price':'1000-3000','pageNo':1,'pageSize':10,'sortField':'price','sort':'ASC'}";
        Map searchMap = JSON.parseObject(searchStr, Map.class);
        //根据条件进行搜索过滤
        Map<String, Object> search = itemSearchService.search(searchMap);
        for (String s : search.keySet()) {
            System.out.println(s+":"+search.get(s));
        }
        //Assert.assertNotEquals(0,search.size());
        //结果9
        /*total:9
        totalPages:1
        rows:[
        TbItem{id=1041685, title='<em style="color:red">华为</em> 麦芒B199 白 电信3G手机 双卡双待双通', price=1249.0, image='http://**.jpg', goodsId=1, category='手机', brand='华为', seller='华为', specMap={网络=联通3G, 机身内存=16G}},
        TbItem{id=1060844, title='<em style="color:red">华为</em> Ascend P6S 碳素黑 联通3G手机 双卡双待', price=1259.0,image='http://**.jpg', goodsId=1, category='手机', brand='华为', seller='华为', specMap={网络=联通3G, 机身内存=16G}},
        TbItem{id=1075409, title='<em style="color:red">华为</em> 麦芒B199 深灰 电信3G手机 双卡双待双通', price=1269.0, image='http://**.jpg', goodsId=1, category='手机', brand='华为', seller='华为', specMap={网络=联通3G, 机身内存=16G}},
        TbItem{id=1082721, title='<em style="color:red">华为</em> 麦芒B199 深灰色 电信3G手机 双模双待双通',price=1269.0, image='http://**.jpg',goodsId=1, category='手机', brand='华为', seller='华为', specMap={网络=联通3G, 机身内存=16G}},
        TbItem{id=917460, title='<em style="color:red">华为</em> P6 (P6-C00) 黑 电信3G手机 双卡双待双通',price=1288.0, image='http://**.jpg', goodsId=1, category='手机', brand='华为', seller='华为', specMap={网络=联通3G, 机身内存=16G}},
        TbItem{id=917461, title='<em style="color:red">华为</em> P6 (P6-C00) 白 电信3G手机 双卡双待双通',price=1299.0, image='http://**.jpg', goodsId=1, category='手机', brand='华为', seller='华为', specMap={网络=联通3G, 机身内存=16G}},
        TbItem{id=1060847, title='<em style="color:red">华为</em> Ascend P6S 阿尔卑斯白 联通3G手机 双卡双待',price=1328.0, image='http://**.jpg', goodsId=1, category='手机', brand='华为', seller='华为', specMap={网络=联通3G, 机身内存=16G}},
        TbItem{id=1075413, title='<em style="color:red">华为</em> 麦芒B199 金 电信3G手机 双卡双待双通', price=1329.0, image='http://**.jpg', goodsId=1, category='手机', brand='华为', seller='华为', specMap={网络=联通3G, 机身内存=16G}},
        TbItem{id=917770, title='<em style="color:red">华为</em> P6-C00 电信3G手机（粉色） CDMA2000/GSM 双模双待双通', image='http://**.jpg', goodsId=1, category='手机', brand='华为', seller='华为', specMap={网络=联通3G, 机身内存=16G}}]
    */
    }

    @Test
    public void add(){
        TbItem item=new TbItem();
        item.setId(1L);
        item.setBrand("华为");
        item.setCategory("手机");
        item.setGoodsId(1L);
        item.setSeller("华为2号专卖店");
        item.setTitle("华为Mate9");
        item.setPrice(new BigDecimal(2000));
        itemSearchService.add(item);
    }
    
    @Autowired
    private SolrTemplate solrTemplate;
    /**
     *  增加、修改
     */
    @Test
    public void testAdd(){
    	long id = (long) (Math.random()*10000);
    	TbItem item=new TbItem();
        item.setId(id+1L);
        item.setBrand("华为");
        item.setCategory("智能手机");
        item.setGoodsId(1L);
        item.setSeller("华为官方专卖店");
        item.setTitle("华为Mate20");
        item.setPrice(new BigDecimal(4999.00));
        System.out.println(item.getId());
       // System.out.println(item.getPrice());
        solrTemplate.saveBean(item);
        solrTemplate.commit();
    }
    
    @Test
    public void testAddList(){
        List<TbItem> list=new ArrayList<TbItem>();
        TbItem item = null;
        for(int i=0;i<100;i++){
        	item=new TbItem();
            item.setId(i+1L);
            item.setBrand("华为");
            item.setCategory("手机");
            item.setGoodsId(1L);
            item.setSeller("华为专卖店");
            item.setTitle("华为Mate"+i);
            item.setPrice(new BigDecimal(2000+i));
            list.add(item);
        }
 
        solrTemplate.saveBeans(list);
        solrTemplate.commit();
    }


    @Test
    public void searchById(){
        TbItem tbItem = itemSearchService.searchById(1537420688098L);
        System.out.println(JSONObject.toJSON(tbItem));
    }

    @Test
    public void deleteById(){
        itemSearchService.deleteById("536563");
    }

    @Test
    public void pageQuery(){
        Page<TbItem> page = itemSearchService.pageQuery(2, 10);
        System.out.println("总记录数："+page.getTotalElements());
        List<TbItem> list = page.getContent();
        System.out.println(JSONObject.toJSON(list));
        for(TbItem item:list){
            System.out.println(item.getTitle() +item.getPrice());
        }
    }

    @Test
    public void deleteAll(){
        itemSearchService.deleteAll();
    }
}