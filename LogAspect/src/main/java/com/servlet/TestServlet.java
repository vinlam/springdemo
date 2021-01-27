package com.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.util.JsonMapper;

public class TestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(TestServlet.class);

	public TestServlet() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// super.doGet(req, resp);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// super.doPost(req, resp);
		log.info("content type:" + req.getContentType());
		PrintWriter writer = resp.getWriter();
		if ("application/json".equals(req.getContentType())) {
			BufferedReader streamReader = new BufferedReader(new InputStreamReader(req.getInputStream(), "UTF-8"));
			StringBuilder responseStrBuilder = new StringBuilder();
			String inputStr;
			while ((inputStr = streamReader.readLine()) != null) {
				responseStrBuilder.append(inputStr);
			}
			Map<String, Object> map = (Map<String, Object>) JsonMapper.fromJsonString(responseStrBuilder.toString(),
					Map.class);
			log.info("res:" + JsonMapper.toJsonString(map));
			writer.write(JsonMapper.toJsonString(map));
			
		} else if (req.getContentType().indexOf("multipart/form-data;") > -1) {
			// 设置工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置缓冲区大小5M
			// factory.setSizeThreshold(1024*1024*5);
			// 设置临时文件
			// factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
			// 设置解析器
			ServletFileUpload sUpload = new ServletFileUpload(factory);

			try {
				// 解析结果放list
				List<FileItem> list = sUpload.parseRequest(req);
				System.out.println("表单数据项数：" + list.size());
				for (FileItem item : list) {
					String name = item.getFieldName();
					System.out.println("数据项名：" + name);
					InputStream in = item.getInputStream();
					// 提交的表单中，文件键值为File
					if (name.equals("File")) {
						// 保存文件
						// 最好保存在绝对路径
						System.out.println(item.getName());
						// String path=this.getServletConfig().getServletContext().getRealPath("/");
						// path=path+"\\images\\test2.png";
						String path = "test1.png";
						System.out.println(path);
						File file = new File(path);
						BufferedInputStream fi = new BufferedInputStream(in);
						FileOutputStream fo = new FileOutputStream(file);
						int f;
						while ((f = fi.read()) != -1) {
							fo.write(f);
						}
						fo.flush();
						fo.close();
						fi.close();
					} else {
						System.out.println("一般表单" + item.getFieldName() + "," + item.getString());
						writer.write(JsonMapper.toJsonString(item));
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Map<String, Object> map = req.getParameterMap();
			log.info("res:" + JsonMapper.toJsonString(map));
			writer.write(JsonMapper.toJsonString(map));
		}
		writer.close();

	}

	public static String fetchPostByTextPlain(HttpServletRequest request) {
		try {
			BufferedReader reader = request.getReader();
			char[] buf = new char[512];
			int len = 0;
			StringBuffer contentBuffer = new StringBuffer();
			while ((len = reader.read(buf)) != -1) {
				contentBuffer.append(buf, 0, len);
			}
			return contentBuffer.toString();

		} catch (IOException e) {
			e.printStackTrace();
			log.error("[获取request中用POST方式“Content-type”是“text/plain”发送的json数据]异常:{}", e.getCause());
		}
		return "";
	}

	public static <T> T fetchPostByTextPlain(HttpServletRequest request, Class<T> clazz) {
		try {
			BufferedReader reader = request.getReader();
			char[] buf = new char[512];
			int len = 0;
			StringBuffer contentBuffer = new StringBuffer();
			while ((len = reader.read(buf)) != -1) {
				contentBuffer.append(buf, 0, len);
			}
			return JSON.parseObject(contentBuffer.toString(), clazz);

		} catch (IOException e) {
			e.printStackTrace();
			log.error("[获取request中用POST方式“Content-type”是“text/plain”发送的json数据]异常:{}", e.getCause());
		}
		return null;
	}

}
