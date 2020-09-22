package com.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

@Service // 注意这里的注解是Springboot的注解
public class UploadService {
	private final static Logger log = LoggerFactory.getLogger(UploadService.class);

	 /**
     * 使用httpclint 发送文件
     * @param file
     * 上传的文件
     * @return 响应结果
     */
    public String upload(String url ,MultipartFile file,Map<String,String>headerParams,Map<String,String>otherParams) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        try {
            String fileName = file.getOriginalFilename();
            HttpPost httpPost = new HttpPost(url);
            //添加header
            for (Map.Entry<String, String> e : headerParams.entrySet()) {
                httpPost.addHeader(e.getKey(), e.getValue());
            }
            String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
	        fileName=new Date().getTime()+"_"+new Random().nextInt(1000)+fileType;//新的文件名
			//1.设置上传的模式；
			//setMode(HttpMultipartMode mode)，其中mode主要有BROWSER_COMPATIBLE，RFC6532，STRICT三种，默认值是STRICT。
			//2.创建MultipartEntityBuilder对象，并添加需要上传的数据；
			//a.利用MultipartEntityBuilder.create()来创建对象；
			//b.addBinaryBody：以二进制的形式添加数据，可以添加File、InputStream、byte[]类型的数据。
			//addBinaryBody(String name, File file, ContentType contentType, String filename)
			//addBinaryBody(String name, InputStream stream, ContentType contentType, String filename)
			//addBinaryBody(String name, byte[] b, ContentType contentType, String filename)
			//c.addTextBody：添加文本数据
			//addTextBody(String name, String text, ContentType contentType)
			//d.addPart：以Key/Value的形式添加ContentBody类型的数据
			//addPart(String name, ContentBody contentBody)
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(Charset.forName("utf-8"));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//加上此行代码解决返回中文乱码问题
            builder.addBinaryBody("file", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
            for (Map.Entry<String, String> e : otherParams.entrySet()) {
                builder.addTextBody(e.getKey(), e.getValue());// 类似浏览器表单提交，对应input的name和value
            }
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);// 执行提交
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * 使用httpclint 发送文件
     * @param file
     * 上传的文件
     * @return 响应结果
     */
    public String uploadFile(String url ,List<File> file,Map<String,String>headerParams,Map<String,String>otherParams) {
    	CloseableHttpClient httpClient = HttpClients.createDefault();
    	String result = "";
    	try {
    		HttpPost httpPost = new HttpPost(url);
    		//添加header
    		for (Map.Entry<String, String> e : headerParams.entrySet()) {
    			httpPost.addHeader(e.getKey(), e.getValue());
    		}
    		//1.设置上传的模式；
    		//setMode(HttpMultipartMode mode)，其中mode主要有BROWSER_COMPATIBLE，RFC6532，STRICT三种，默认值是STRICT。
    		//2.创建MultipartEntityBuilder对象，并添加需要上传的数据；
    		//a.利用MultipartEntityBuilder.create()来创建对象；
    		//b.addBinaryBody：以二进制的形式添加数据，可以添加File、InputStream、byte[]类型的数据。
    		//addBinaryBody(String name, File file, ContentType contentType, String filename)
    		//addBinaryBody(String name, InputStream stream, ContentType contentType, String filename)
    		//addBinaryBody(String name, byte[] b, ContentType contentType, String filename)
    		//c.addTextBody：添加文本数据
    		//addTextBody(String name, String text, ContentType contentType)
    		//d.addPart：以Key/Value的形式添加ContentBody类型的数据
    		//addPart(String name, ContentBody contentBody)
    		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
    		builder.setCharset(Charset.forName("utf-8"));
    		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//加上此行代码解决返回中文乱码问题
    		for (File f:file) {
    			builder.addBinaryBody("file", f);// 文件流
    		}	
    		
    		for (Map.Entry<String, String> e : otherParams.entrySet()) {
    			builder.addTextBody(e.getKey(), e.getValue());// 类似浏览器表单提交，对应input的name和value
    		}
    		HttpEntity entity = builder.build();
    		httpPost.setEntity(entity);
    		HttpResponse response = httpClient.execute(httpPost);// 执行提交
    		HttpEntity responseEntity = response.getEntity();
    		if (responseEntity != null) {
    			// 将响应内容转换为字符串
    			result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		try {
    			httpClient.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    	return result;
    }
}