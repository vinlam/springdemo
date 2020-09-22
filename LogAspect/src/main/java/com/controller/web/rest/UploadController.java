package com.controller.web.rest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.mime.MultipartEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.druid.support.logging.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.service.UploadService;

@RestController("HttpFileUpload")
@RequestMapping(value = "/api")
public class UploadController {
	private final static Logger log = LoggerFactory.getLogger(UploadController.class);

	private static final String CHARSET = "utf-8";

	private static final int TIME_OUT = 10000;

	@Autowired
	private UploadService uploadService;

	@PostMapping("/upload/http")
	public Object upload(MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) {
		// multipartFile为上传的文件
		String data = "";
//      form-data中其他参数的处理
		Map<String, String> headerParams = new HashMap<String, String>();
		@SuppressWarnings("unchecked")
		Map<String, String> otherParams = request.getParameterMap();
		headerParams.put("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;)");
		// 注 这里一定不能添加 content-Type:multipart/form-data 属性
		// 因为这里面有个boundary参数属性是不可控的。这个值是由浏览器生成的。如果强行指明和可能
		// 导致边界值不一致 就会请求失败 详细参见
		// http://blog.csdn.net/xiaojianpitt/article/details/6856536
		// application/x-www-form-urlencoded(默认值)
		// multipart/form-data
		// 其实form表单在你不写enctype属性时，也默认为其添加了enctype属性值，默认值是enctype="application/x-
		// www-form-urlencoded".
		// headerParams.put("content-Type", "multipart/form-data");
		// headerParams.put("Host", "****");
		headerParams.put("Accept-Encoding", "gzip");
		headerParams.put("charset", "utf-8");
		// data = JSON.toJSONString(otherParams);
		String url = "http://localhost:8080/LogAspect/api/upload/springUpload";
		return uploadService.upload(url, multipartFile, headerParams, otherParams);
	}
	
	@PostMapping("/upload/httpfile")
	public Object upload(@RequestParam(required = false) String param, HttpServletRequest request, HttpServletResponse response) {
		// multipartFile为上传的文件
		String data = "";
//      form-data中其他参数的处理
		Map<String, String> headerParams = new HashMap<String, String>();
		@SuppressWarnings("unchecked")
//		Map<String, String> otherParams = request.getParameterMap();//等于操作的时候会关联原对象 原对象不可以进行修改 那新参数也无法进行修改
		//修改后
		Map<String, String> otherParams = new HashMap<String, String>(request.getParameterMap());
		headerParams.put("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;)");
		File f1 = new File("/Users/vinlam/www/photo/noimage.png");
		File f2 = new File("/Users/vinlam/www/photo/120.png");
		
		otherParams.put("param", param);
		
		List<File> file = new ArrayList<File>();
		file.add(f1);
		file.add(f2);
		// 注 这里一定不能添加 content-Type:multipart/form-data 属性
		// 因为这里面有个boundary参数属性是不可控的。这个值是由浏览器生成的。如果强行指明和可能
		// 导致边界值不一致 就会请求失败 详细参见
		// http://blog.csdn.net/xiaojianpitt/article/details/6856536
		// application/x-www-form-urlencoded(默认值)
		// multipart/form-data
		// 其实form表单在你不写enctype属性时，也默认为其添加了enctype属性值，默认值是enctype="application/x-
		// www-form-urlencoded".
		// headerParams.put("content-Type", "multipart/form-data");
		// headerParams.put("Host", "****");
		headerParams.put("Accept-Encoding", "gzip");
		headerParams.put("charset", "utf-8");
		// data = JSON.toJSONString(otherParams);
		String url = "http://localhost:8080/LogAspect/api/upload/uploadSubmit";
		return uploadService.uploadFile(url, file, headerParams, otherParams);
	}

	@PostMapping("/upload/http/save")
	public Object saveFile(MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) {
		// multipartFile为上传的文件
		String data = "";
//      form-data中其他参数的处理
		Map<String, String> headerParams = new HashMap<String, String>();
		@SuppressWarnings("unchecked")
		Map<String, String> otherParams = request.getParameterMap();
		headerParams.put("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;)");
		// 注 这里一定不能添加 content-Type:multipart/form-data 属性
		// 因为这里面有个boundary参数属性是不可控的。这个值是由浏览器生成的。如果强行指明和可能
		// 导致边界值不一致 就会请求失败 详细参见
		// http://blog.csdn.net/xiaojianpitt/article/details/6856536
		// application/x-www-form-urlencoded(默认值)
		// multipart/form-data
		// 其实form表单在你不写enctype属性时，也默认为其添加了enctype属性值，默认值是enctype="application/x-
		// www-form-urlencoded".
		// headerParams.put("content-Type", "multipart/form-data");
		// headerParams.put("Host", "****");
		headerParams.put("Accept-Encoding", "gzip");
		headerParams.put("charset", "utf-8");
		// data = JSON.toJSONString(otherParams);
		String url = "http://localhost:8080/LogAspect/api/upload/save";
		return uploadService.upload(url, multipartFile, headerParams, otherParams);
	}
	
	@PostMapping("/upload/restbyteupload")
	public Object restbyteupload(MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
		MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpHeaders pictureHeader = new HttpHeaders();
        pictureHeader.setContentType(MediaType.parseMediaType(multipartFile.getContentType()));
        //如果是用spring 的MultipartFile接受，则加入下面这行， 去个随机文件名
        pictureHeader.setContentDispositionFormData("file", multipartFile.getOriginalFilename());
        HttpEntity<ByteArrayResource> picturePart = new HttpEntity<ByteArrayResource>(new ByteArrayResource(multipartFile.getBytes()), pictureHeader);
        multipartRequest.add("file", picturePart);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<> 
        (multipartRequest, headers);
        
		

		String serverUrl = "http://localhost:8080/LogAspect/api/upload/save";

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				serverUrl, HttpMethod.POST, 
                requestEntity,
                String.class);
		return responseEntity;
	}
	
	@PostMapping("/upload/restmutlbyteupload")
	public Object restmutlbyteupload(MultipartFile f1,MultipartFile f2, HttpServletRequest request, HttpServletResponse response) throws IOException {
		MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		HttpHeaders pictureHeader = new HttpHeaders();
		pictureHeader.setContentType(MediaType.parseMediaType(f1.getContentType()));
		//如果是用spring 的MultipartFile接受，则加入下面这行， 去个随机文件名
		pictureHeader.setContentDispositionFormData("files", f1.getOriginalFilename());
		HttpEntity<ByteArrayResource> picturePart = new HttpEntity<ByteArrayResource>(new ByteArrayResource(f1.getBytes()), pictureHeader);
		multipartRequest.add("files", picturePart);
		HttpHeaders pictureHeader2 = new HttpHeaders();
		pictureHeader2.setContentType(MediaType.parseMediaType(f2.getContentType()));
		//如果是用spring 的MultipartFile接受，则加入下面这行， 去个随机文件名
		pictureHeader2.setContentDispositionFormData("files", f2.getOriginalFilename());
		HttpEntity<ByteArrayResource> picturePart2 = new HttpEntity<ByteArrayResource>(new ByteArrayResource(f1.getBytes()), pictureHeader);
		multipartRequest.add("files", picturePart2);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<> 
		(multipartRequest, headers);
		
		
		
		String serverUrl = "http://localhost:8080/LogAspect/api/upload/filesUpload";
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				serverUrl, HttpMethod.POST, 
				requestEntity,
				String.class);
		return responseEntity;
	}
	
	@PostMapping("/upload/restuploadsubmit")
	public Object restuploadsubmit(MultipartFile f1,MultipartFile f2,@RequestParam(required = false)String user, HttpServletRequest request, HttpServletResponse response) throws IOException {
		MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		HttpHeaders pictureHeader = new HttpHeaders();
		pictureHeader.setContentType(MediaType.parseMediaType(f1.getContentType()));
		//如果是用spring 的MultipartFile接受，则加入下面这行， 去个随机文件名
		pictureHeader.setContentDispositionFormData("files", f1.getOriginalFilename());
		HttpEntity<ByteArrayResource> picturePart = new HttpEntity<ByteArrayResource>(new ByteArrayResource(f1.getBytes()), pictureHeader);
		multipartRequest.add("files", picturePart);
		multipartRequest.add("user", user);
		HttpHeaders pictureHeader2 = new HttpHeaders();
		pictureHeader2.setContentType(MediaType.parseMediaType(f2.getContentType()));
		//如果是用spring 的MultipartFile接受，则加入下面这行， 去个随机文件名
		pictureHeader2.setContentDispositionFormData("files", f2.getOriginalFilename());
		HttpEntity<ByteArrayResource> picturePart2 = new HttpEntity<ByteArrayResource>(new ByteArrayResource(f1.getBytes()), pictureHeader);
		multipartRequest.add("files", picturePart2);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<> 
		(multipartRequest, headers);
		
		
		
		String serverUrl = "http://localhost:8080/LogAspect/api/upload/uploadSubmit";
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				serverUrl, HttpMethod.POST, 
				requestEntity,
				String.class);
		return responseEntity;
	}
	
	@PostMapping("/upload/restupload")
	public Object restTemplateUpload(MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) {
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		//restTemple不能传输File和MulipartFile,转为FileSystemResource后传输
		//构建FileSystemResource需要文件类型为File，因此先将MulipartFile转为File
		File file = multipartFileToFile(multipartFile);
		FileSystemResource resource = new FileSystemResource(file);
		body.add("file", resource);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		
		String serverUrl = "http://localhost:8080/LogAspect/api/upload/save";
		
		RestTemplate restTemplate = new RestTemplate();
//		ObjectMapper newObjectMapper = new ObjectMapper(); 
//		newObjectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false); 
//		MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter=new MappingJackson2HttpMessageConverter();
//		restTemplate.getMessageConverters().add(new FormHttpMessageConverter()); 
//		restTemplate.getMessageConverters().add(mappingJacksonHttpMessageConverter); 
//		restTemplate.getMessageConverters().add(new StringHttpMessageConverter()); 
		//return restTemplate; 
		//ResponseEntity<String> responseEntity = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
		ResponseEntity<String> responseEntity = restTemplate.exchange(serverUrl, HttpMethod.POST,requestEntity, String.class);
		return responseEntity;
	}
	
	public File multipartFileToFile(MultipartFile file) {
        if (file != null) {
            String fileName = file.getOriginalFilename();
            String filePath = "/Users/vinlam/upload/tmp";
            String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());// 文件后缀
			fileName = new Date().getTime() + "_" + new Random().nextInt(1000) + fileType;// 新的文件名

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String fileAdd = sdf.format(new Date());
			filePath = filePath + "/" + fileAdd;
			
			// 获取文件夹路径
			File newfilepath = new File(filePath);
			if (!newfilepath.exists() && !newfilepath.isDirectory()) {
				newfilepath.mkdirs();
			}
			
            File conventFile = new File(filePath,fileName);
            try {
                boolean isCreateSuccess = conventFile.createNewFile();
                if (isCreateSuccess) {
                    file.transferTo(conventFile);
                    return conventFile;
                }
            } catch (IOException e) {
                log.warn("multipartFile convert to File failed  -> " + e.getMessage());
            }
        }
        return null;
    }

	@PostMapping("/upload/urlconnectionupload")
	public Object urlconnectionupload(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
//		HttpURLConnection httpUrlConnection = null;
//		URL url = new URL("http://example.com/server.cgi");
//		httpUrlConnection = (HttpURLConnection) url.openConnection();
//		httpUrlConnection.setUseCaches(false);
//		httpUrlConnection.setDoOutput(true);
//
//		httpUrlConnection.setRequestMethod("POST");
//		httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
//		httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
//		final String boundary = "===" + System.currentTimeMillis() + "===";
//		httpUrlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
		
		String  BOUNDARY =  UUID.randomUUID().toString();  //边界标识   随机生成
		String PREFIX = "--" , LINE_END = "\r\n"; 
		String CONTENT_TYPE = "multipart/form-data";   //内容类型
		String RequestURL = "http://localhost:8080/LogAspect/api/upload/save";
		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true);  //允许输入流
			conn.setDoOutput(true); //允许输出流
			conn.setUseCaches(false);  //不允许使用缓存
			conn.setRequestMethod("POST");  //请求方式
			conn.setRequestProperty("Charset", CHARSET);  //设置编码
			conn.setRequestProperty("connection", "keep-alive");   
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY); 
			if(file!=null)
			{
				/**
				 * 当文件不为空，把文件包装并且上传
				 */
				OutputStream outputSteam=conn.getOutputStream();
				
				DataOutputStream dos = new DataOutputStream(outputSteam);
				StringBuffer sb = new StringBuffer();
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				/**
				 * 这里重点注意：
				 * name里面的值为服务器端需要key   只有这个key 才可以得到对应的文件
				 * filename是文件的名字，包含后缀名的   比如:abc.png  
				 */
				
				sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""+file.getOriginalFilename()+"\""+LINE_END); 
				sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);
				sb.append(LINE_END);
				dos.write(sb.toString().getBytes());
				InputStream is = file.getInputStream();
				byte[] bytes = new byte[1024];
				int len = 0;
				while((len=is.read(bytes))!=-1)
				{
					dos.write(bytes, 0, len);
				}
				is.close();
				dos.write(LINE_END.getBytes());
				byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
				dos.write(end_data);
				dos.flush();
				/**
				 * 获取响应码  200=成功
				 * 当响应成功，获取响应的流  
				 */
				int res = conn.getResponseCode();  
				log.error( "response code:"+res);
				if(res==200)
				{
			     return "SUCCESS";
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "FAILURE";
	}
	
	public String multipost(String urlString, MultipartEntity reqEntity) {
	    try {
	        URL url = new URL(urlString);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(10000);
	        conn.setConnectTimeout(15000);
	        conn.setRequestMethod("POST");
	        conn.setUseCaches(false);
	        conn.setDoInput(true);
	        conn.setDoOutput(true);

	        conn.setRequestProperty("Connection", "Keep-Alive");
	        conn.addRequestProperty("Content-length", reqEntity.getContentLength()+"");
	        conn.addRequestProperty(reqEntity.getContentType().getName(), reqEntity.getContentType().getValue());

	        OutputStream os = conn.getOutputStream();
	        reqEntity.writeTo(conn.getOutputStream());
	        os.close();
	        conn.connect();

	        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
	            return readStream(conn.getInputStream());
	        }

	    } catch (Exception e) {
	        log.error( "multipart post error " + e + "(" + urlString + ")");
	    }
	    return null;        
	}

	private String readStream(InputStream in) {
	    BufferedReader reader = null;
	    StringBuilder builder = new StringBuilder();
	    try {
	        reader = new BufferedReader(new InputStreamReader(in));
	        String line = "";
	        while ((line = reader.readLine()) != null) {
	            builder.append(line);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return builder.toString();
	} 
}
