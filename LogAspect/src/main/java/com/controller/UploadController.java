package com.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.util.FileUploadUtil;

@RestController
@RequestMapping("/upload")
public class UploadController {
	// 通过Spring的autowired注解获取spring默认配置的request

	/***
	 * 保存文件
	 * 
	 * @param file
	 * @return
	 */
	private boolean saveFile(MultipartFile file, String path) {
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				File filepath = new File(path);
				if (!filepath.exists())
					filepath.mkdirs();
				// 文件保存路径
				String savePath = path + file.getOriginalFilename();
				// 转存文件
				file.transferTo(new File(savePath));
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@RequestMapping("/filesUpload")
	public String filesUpload(@RequestParam("files") MultipartFile[] files) {
		String path = "F:/upload/";
		// 判断file数组不能为空并且长度大于0
		if (files != null && files.length > 0) {
			// 循环获取file数组中得文件
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				// 保存文件
				saveFile(file, path);
			}
		}
		return "success";
		// 重定向
		// return "redirect:/list.html";
	}

	@RequestMapping("/cmfileUpload")
	public String fileUpload(@RequestParam("files") CommonsMultipartFile files) throws IOException {

		// 用来检测程序运行时间
		long startTime = System.currentTimeMillis();
		System.out.println("fileName：" + files.getOriginalFilename());
		OutputStream os = null;
		InputStream is = null;
		try {
			// 获取输出流
			os = new FileOutputStream("F:/upload/" + new Date().getTime() + files.getOriginalFilename());
			// 获取输入流 CommonsMultipartFile 中可以直接得到文件的流
			is = files.getInputStream();
			int temp;
			// 一个一个字节的读取并写入
			while ((temp = is.read()) != (-1)) {
				os.write(temp);
			}
//			os.flush();
//			os.close();
//			is.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(os != null) {
				os.flush();
				os.close();
			}
			if(is != null) {
				is.close();
			}
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("方法一的运行时间：" + String.valueOf(endTime - startTime) + "ms");
		return "success";
	}

	/*
	 * 采用spring提供的上传文件的方法
	 */
	@RequestMapping("/springUpload")
	public ResponseEntity<List<String>> springUpload(HttpServletRequest request) throws IllegalStateException, IOException,FileNotFoundException {
		long startTime = System.currentTimeMillis();
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		
		//设置编码
		multipartResolver.setDefaultEncoding("utf-8");
		// 检查form中是否有enctype="multipart/form-data"
		List<String> list = new ArrayList<String>();
		
		list = FileUploadUtil.uploadFile(request,"t","");
//		if (multipartResolver.isMultipart(request)) {
//			// 将request变成多部分request
//			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
//			// 获取multiRequest 中所有的文件名
//			Iterator<String> iter = multiRequest.getFileNames();
//		
//			while (iter.hasNext()) {
//				// 一次遍历所有文件
//				MultipartFile file = multiRequest.getFile(iter.next().toString());
//				if (file != null) {
//					String path = "F:/upload/" + file.getOriginalFilename();
//					// 上传
//					file.transferTo(new File(path));
//					list.add(path);
//				}
//
//			}
//
//		}
		long endTime = System.currentTimeMillis();
		System.out.println("方法三的运行时间：" + String.valueOf(endTime - startTime) + "ms");
		return ResponseEntity.ok(list);
	}
	
//	@RequestMapping(value = "/img", method = RequestMethod.POST)
//	@ResponseBody
//	public Result uploadImg(@RequestParam(value = "file", required = false) MultipartFile file, String pathName,
//			Integer sizeRule, Integer isDeviation, HttpServletRequest request)
//					throws FileNotFoundException, IOException {
//		String loghead = "通用接口-上传图片:";
// 
//		JSONObject json = new JSONObject();
//		if (file == null) {
//			Log.info(loghead + "上传失败:文件为空");
//			return new Result(900001, "", "上传失败:文件为空");
//		}
//		if (StringUtils.isBlank(pathName)) {
//			pathName = ConstantsConfig.getString("DEFALTUPLOADPATH");
//		}
//		List<Object> uploadPathNames = ConstantsConfig.getList("UPLOADPATHNAMES");
//		boolean flag = false;
//		for (int i = 0; i < uploadPathNames.size(); i++) {
//			if (pathName.equals(uploadPathNames.get(i))) {
//				flag = true;
//			}
//		}
//		if (!flag) {
//			Log.info(loghead + "上传失败:上传路径无效");
//			return new Result(900001, "", "上传失败:上传路径无效");
//		}
//		String fileName = file.getOriginalFilename();
//		// 获取上传文件扩展名
//		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
//		// 对扩展名进行小写转换
//		fileExt = fileExt.toLowerCase();
//		// 图片文件大小过滤
//		if (!"jpg".equals(fileExt) && !"jpeg".equals(fileExt) && !"png".equals(fileExt) && !"bmp".equals(fileExt)
//				&& !"gif".equals(fileExt)) {
//			Log.info(loghead + "上传失败:无效图片文件类型");
//			return new Result(900001, "", "上传失败:无效图片文件类型");
//		}
//		long fileSize = file.getSize();
//		Log.info(loghead + "fileInfo:fileName=" + fileName + "&fileSize=" + fileSize);
//		if (fileSize <= 0) {
//			Log.info(loghead + "上传失败:文件为空");
//			return new Result(900001, "", "上传失败:文件为空");
//		} else if (fileSize > (500 * 1024)) {
//			Log.info(loghead + "上传失败:文件大小不能超过500K");
//			return new Result(900001, "", "上传失败:文件大小不能超过500K");
//		}
//		File tmpFile = null;
//		// 判断文件是否为空
//		if (!file.isEmpty()) {
//			String uploadPath = request.getSession().getServletContext().getRealPath("/") + "/upload/";
//			File uploadDir = new File(uploadPath);
//			if (uploadDir.exists() && uploadDir.isDirectory()) {
//				String[] childFileNameList = uploadDir.list();
//				if (childFileNameList != null) {
//					File temp;
//					for (int i = 0; i < childFileNameList.length; i++) {
//						temp = new File(uploadPath + childFileNameList[i]);
//						if (temp.isFile()) {
//							temp.delete();
//						}
//					}
//				}
//			} else {
//				uploadDir.mkdir();
//			}
// 
//			try {
//				Date now = new Date();
//				String tmpFileName = String.valueOf(now.getTime()) + Math.round(Math.random() * 1000) + "." + fileExt;
//				// 文件保存路径
//				String tmpFilePath = uploadPath + tmpFileName;
//				tmpFile = new File(tmpFilePath);
//				file.transferTo(tmpFile);
//				BufferedImage sourceImg = ImageIO.read(new FileInputStream(tmpFile));
//				int imgWidth = sourceImg.getWidth();
//				int imgHeight = sourceImg.getHeight();
//				System.out.println("上传的图片宽：" + imgWidth);
//				System.out.println("上传的图片高：" + imgHeight);
//				// 图片文件尺寸过滤
//				if (sizeRule == null) {
//					// 上传到图片服务器
//					String imgUrl = Upload.upload(tmpFile, "/" + pathName + "/");
//					json.put("fileName", fileName);
//					json.put("url", imgUrl);
//					json.put("imgWidth", imgWidth);
//					json.put("imgHeight", imgHeight);
//					return new Result(0, json, "success");
//				} else {
//					System.out.println("前端选择图片尺寸规则" + sizeRule);
//					int ruleWidth = 0;
//					int ruleHeight = 0;
//					try {
//						String imgSizeRule = ConstantsConfig.getString("UPLOADIMG_RULE" + sizeRule);
//						String imgSizeRule_width_height[] = imgSizeRule.split(",");
//						String imgSizeRule_width = imgSizeRule_width_height[0];
//						String imgSizeRule_height = imgSizeRule_width_height[1];
//						ruleWidth = Integer.parseInt(imgSizeRule_width);
//						ruleHeight = Integer.parseInt(imgSizeRule_height);
//					} catch (Exception e) {
//						System.out.println("没有配置尺寸规则" + sizeRule);
//						json.put("fileName", fileName);
//						json.put("imgWidth", imgWidth);
//						json.put("imgHeight", imgHeight);
//						return new Result(-1, json, "配置系统没有配置上传图片尺寸规则" + sizeRule
//								+ ",请前端修改参数sizeRule值或管理员在配置系统配置sizeRule" + sizeRule + "规则对应的配置项");
//					}
//					if (isDeviation == null) {
//						System.out.println("严格限制图片尺寸");
//						if (ruleWidth == imgWidth && ruleHeight == imgHeight) {
//							String imgUrl = Upload.upload(tmpFile, "/" + pathName + "/");
//							json.put("fileName", fileName);
//							json.put("url", imgUrl);
//							json.put("imgWidth", imgWidth);
//							json.put("imgHeight", imgHeight);
//							return new Result(0, json, "success");
//						} else {
//							json.put("fileName", fileName);
//							json.put("imgWidth", imgWidth);
//							json.put("imgHeight", imgHeight);
//							json.put("ruleWidth", ruleWidth);
//							json.put("ruleHeight", ruleHeight);
//							return new Result(-1, json, "请上传" + ruleWidth + "*" + ruleHeight + "px的图片");
//						}
//					} else {
//						int deviation = Integer.parseInt(ConstantsConfig.getString("UPLOADIMG_DEVIATION"));
//						System.out.println("允许尺寸误差在" + deviation + "以内");
//						if (Math.abs(ruleWidth - imgWidth) <= deviation
//								&& Math.abs(ruleHeight - imgHeight) <= deviation) {
//							String imgUrl = Upload.upload(tmpFile, "/" + pathName + "/");
//							json.put("fileName", fileName);
//							json.put("url", imgUrl);
//							json.put("imgWidth", imgWidth);
//							json.put("imgHeight", imgHeight);
//							return new Result(0, json, "success");
//						} else {
//							json.put("fileName", fileName);
//							json.put("imgWidth", imgWidth);
//							json.put("imgHeight", imgHeight);
//							json.put("ruleWidth", ruleWidth);
//							json.put("ruleHeight", ruleHeight);
//							return new Result(-1, json, "请上传" + ruleWidth + "*" + ruleHeight + "px的图片");
//						}
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				Log.info(loghead + "上传失败:文件上传失败");
//				return new Result(900001, "", "上传失败:文件上传异常");
//			} finally {
//				// 删除临时文件
//				if (tmpFile.exists()) {
//					tmpFile.deleteOnExit();
//					Log.info(loghead + "删除临时文件" + tmpFile.getAbsolutePath());
//				}
//			}
//		}
//		return new Result(-1, json, "后台校验上传图片流程异常");
//	}


}