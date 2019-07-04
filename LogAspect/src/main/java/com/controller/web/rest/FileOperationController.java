package com.controller.web.rest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.collections.map.MultiValueMap;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/api")
public class FileOperationController {
	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	public @ResponseBody String upload() {
		String url = "http://localhost:8888/img/uploadfile.php";
		String filePath = "/Users/vinlam/Desktop/IMG_0602.jpg";
 
		RestTemplate rest = new RestTemplate();
		
		FileSystemResource resource = new FileSystemResource(new File(filePath));
		org.springframework.util.MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		param.add("pic", resource);
		//param.add("fileName", "xiaolian.png");
	
		// method 1
		String string = rest.postForObject(url, param, String.class);
		// method 2
//		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String,Object>>(param);
//		ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.POST, httpEntity, String.class);
//		String string = responseEntity.getBody();
		
		System.out.println(string);
		
		return "success";
	}
 
	@RequestMapping(value = "/downloadfile", method = RequestMethod.GET)
	public @ResponseBody String download() {
		String url = "http://restAddr/project/service/download1/kaola";
		String filePath = "/Users/vinlam/Downloads/kaola.jpg";
		InputStream inputStream = null;
		OutputStream outputStream = null;
				
		RestTemplate restTemplate = new RestTemplate();
		try {
			//		Object result = restTemplate.getForObject(url, Object.class);
			HttpHeaders headers = new HttpHeaders();
			ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<byte[]>(headers), byte[].class);
			byte[] result = response.getBody();
			inputStream = new ByteArrayInputStream(result);
			
			outputStream = new FileOutputStream(new File(filePath));
			
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = inputStream.read(buf, 0, 1024)) != -1) {
				outputStream.write(buf, 0, len);
			}
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return "error"; 
		} finally {
			try {
				if(inputStream != null) inputStream.close();
				if(outputStream != null) outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("数据流关闭异常！");
			}
		}
		
		return "success";
	}
}	
