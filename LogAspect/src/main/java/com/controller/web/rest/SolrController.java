package com.controller.web.rest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.entity.TbItem;
import com.service.impl.ItemSearchServiceImpl;

@RestController
@RequestMapping("/api")
public class SolrController {
	
	@Autowired
	private ItemSearchServiceImpl itemSearchServiceImpl;
	
	//@Autowired
    //private SolrTemplate solrTemplate;
	@RequestMapping(value="/add", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> insert(@RequestBody TbItem tbItem) throws IOException, SolrServerException {
        try {
        	//TbItem tbItem = new TbItem();
            itemSearchServiceImpl.add(tbItem);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("error");
    }

    /**
     * 2、查 id
     * @param id
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
	@RequestMapping(value="/get/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<TbItem> getDocumentById(@PathVariable long id) throws SolrServerException, IOException {
        TbItem iTbItem = itemSearchServiceImpl.searchById(id);
        return ResponseEntity.ok(iTbItem);

    }

    /**
     * 3、删 id
     * @return
     */
	@RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        try {
        	itemSearchServiceImpl.deleteById(id);
            return  ResponseEntity.ok("delete all success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("error");
    }

    /**
     * 4、删 all
     * @return
     */
	@RequestMapping(value="/deleteAll", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<String> deleteAll() {
        try {
        	itemSearchServiceImpl.deleteAll();
            return ResponseEntity.ok("delete all success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("delete all error");
    }

    /**
     * 5、改
     * @param message
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
	@RequestMapping(value="/update", method = RequestMethod.PUT, produces = "application/json")
    public String update(String id, String message) throws IOException, SolrServerException {
        try {
            SolrInputDocument doc = new SolrInputDocument();
            doc.setField("id", id);
            doc.setField("text", message);

            /*
             * 如果 spring.data.solr.host 里面配置到 core了, 那么这里就不需要传 itaem 这个参数 下面都是一样的 即
             * client.commit();
             */
            //solrTemplate.saveDocument(doc);
            //solrTemplate.commit();
            return doc.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    /**
     * 6、
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
	@RequestMapping(value="/get/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<TbItem>> getAll() throws SolrServerException, IOException {
		Page<TbItem> page = itemSearchServiceImpl.pageQuery(2, 10);
        System.out.println("总记录数："+page.getTotalElements());
        List<TbItem> list = page.getContent();
        System.out.println(JSONObject.toJSON(list));
        return ResponseEntity.ok(list);
    }

    /**
     * 7、查  ++:关键字、高亮、分页  ✔
     * @return 
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
	@RequestMapping(value="/select", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> select(@RequestParam String q) throws SolrServerException, IOException {
		String searchStr = new String("{'keywords':'华为','category':'手机','brand':'华为'," +
                "'spec':{'机身内存':'16G','网络':'联通3G'},'price':'1000-3000'," +
                "'pageNo':1,'pageSize':10,'sortField':'price','sort':'ASC'}");
        searchStr = "{'keywords':'华为','category':'手机','brand':'华为','price':'1000-3000','pageNo':1,'pageSize':10,'sortField':'price','sort':'ASC'}";
        Map searchMap = JSON.parseObject(searchStr, Map.class);
        //根据条件进行搜索过滤
        Map<String, Object> search = itemSearchServiceImpl.search(searchMap);
        return ResponseEntity.ok(search);
    }

}
