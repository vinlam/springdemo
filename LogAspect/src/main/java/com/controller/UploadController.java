package com.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.service.UploadService;
import com.util.FileUploadUtil;

@RestController
@RequestMapping("/api/upload")
public class UploadController {
	private final static Logger log = LoggerFactory.getLogger(UploadController.class);

	/***
	 * 保存文件
	 * 
	 * @param file
	 * @return
	 */
	@PostMapping("/save")
	private String saveUploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		String path = "/Users/vinlam/upload";
		// 判断文件是否为空
		if (file != null && !file.isEmpty()) {
			try {

				String fileName = file.getOriginalFilename();

				// 文件保存路径
				// String savePath = path + file.getOriginalFilename();

				String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());// 文件后缀
				fileName = new Date().getTime() + "_" + new Random().nextInt(1000) + fileType;// 新的文件名

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String fileAdd = sdf.format(new Date());
				File targetFile = null;
				String filePath = path + "/" + fileAdd;
				// 获取文件夹路径
				File newfilepath = new File(filePath);

				if (!newfilepath.exists() && !newfilepath.isDirectory()) {
					newfilepath.mkdirs();
				}
				targetFile = new File(newfilepath, fileName);
				// 第一种转存文件
				file.transferTo(targetFile);

//		        filePath = filePath+"/copystream";
//				File savedFile = new File(filePath);
//				boolean isCreateSuccess = savedFile.createNewFile(); // 是否创建文件成功
//				if(isCreateSuccess){      //将文件写入    
//					if(!savedFile.exists()  && !savedFile.isDirectory()){       
//						savedFile.mkdirs();  
//			        }
//					//第二种
//					savedFile = new File(filePath,fileName);
//					FileUtils.copyInputStreamToFile(file.getInputStream(),savedFile);
//				}
				return filePath + "/" + fileName;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// 转换request，解析出request中的文件
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

			// 获取文件map集合
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			String fileName = null;
			if (!fileMap.isEmpty()) {
				try {

					// 循环遍历，取出单个文件
					for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {

						// 获取单个文件
						MultipartFile mf = entity.getValue();

						// 获得原始文件名
						fileName = mf.getOriginalFilename();

						// 截取文件类型; 这里可以根据文件类型进行判断
						String fileType = fileName.substring(fileName.lastIndexOf('.'));

						// 截取上传的文件名称
						String newFileName = fileName.substring(0, fileName.lastIndexOf('.'));

						log.debug("上传来的文件名称------->>>>>>>>>" + newFileName);

						// 拼接上传文件位置
						String newfilePath = path + File.separatorChar + newFileName + fileType;

						log.debug("拼接好的文件路径地址------------->>>>>>>>" + newfilePath);

						// 重新组装文件路径，用于保存在list集合中
						String filepathUrl = "files" + File.separatorChar + "reqest" + File.separatorChar + ""
								+ File.separatorChar + newFileName + fileType;

						log.debug("文件位置---------------->>>>>>>>>>" + filepathUrl);

						// 创建文件存放路径实例
						File dest = new File(filepathUrl);

						// 判断文件夹不存在就创建
						if (!dest.exists()) {
							dest.mkdirs();
						}
						mf.transferTo(dest);
						return filepathUrl;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

					log.error("upload failed. filename: " + fileName + "---->>>error message ----->>>>> "
							+ e.getMessage());

					return null;
				}
			}
		}
		return "";
	}

	@PostMapping("/copyStream")
	private String copyStream(MultipartFile file) {
		String path = "/Users/vinlam/upload";
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {

				String fileName = file.getOriginalFilename();

				// 文件保存路径
				// String savePath = path + file.getOriginalFilename();

				String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());// 文件后缀
				fileName = new Date().getTime() + "_" + new Random().nextInt(1000) + fileType;// 新的文件名

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String fileAdd = sdf.format(new Date());
				String filePath = path + "/" + fileAdd;
				filePath = filePath + "/copystream";
				File savedFile = new File(filePath, fileName);
				boolean isCreateSuccess = savedFile.createNewFile(); // 是否创建文件成功
				if (isCreateSuccess) { // 将文件写入
//					if(!savedFile.exists()  && !savedFile.isDirectory()){       
//						savedFile.mkdirs();  
//			        }
					// 第二种
					// savedFile = new File(filePath,fileName);
					FileUtils.copyInputStreamToFile(file.getInputStream(), savedFile);
				}
				return filePath + "/" + fileName;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	@PostMapping("/filesUpload")
	public String filesUpload(@RequestParam("files") MultipartFile[] files, HttpServletRequest request,
			HttpServletResponse response) {
		String path = "/Users/vinlam/upload/";

		String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/resources/upload/imgs/";// 存储路径
		path = request.getSession().getServletContext().getRealPath("resources/upload/imgs"); // 文件存储位置
		List<String> listPath = new ArrayList<String>();
		// 判断file数组不能为空并且长度大于0
		if (files != null && files.length > 0) {
			String rturl = "";
			// 循环获取file数组中得文件
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				System.out.println(file.getName());
				System.out.println(file.getOriginalFilename());
				// 保存文件
				rturl = saveFile(file, path);
				rturl = returnUrl + rturl;
				listPath.add(rturl);
			}
		}
		System.out.println(listPath.toString());
		return "success:" + listPath.toString();
		// 重定向
		// return "redirect:/list.html";
	}
	
	@PostMapping("/uploadSubmit")
	public String uploadSubmit(@RequestParam("files") MultipartFile[] files,@RequestParam(required = false)String user, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println(user);
		String path = "/Users/vinlam/upload/";
		
		String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath() + "/resources/upload/imgs/";// 存储路径
		path = request.getSession().getServletContext().getRealPath("resources/upload/imgs"); // 文件存储位置
		List<String> listPath = new ArrayList<String>();
		// 判断file数组不能为空并且长度大于0
		if (files != null && files.length > 0) {
			String rturl = "";
			// 循环获取file数组中得文件
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				System.out.println(file.getName());
				System.out.println(file.getOriginalFilename());
				// 保存文件
				rturl = saveFile(file, path);
				rturl = returnUrl + rturl;
				listPath.add(rturl);
			}
		}
		System.out.println(listPath.toString());
		return "success:" + listPath.toString();
		// 重定向
		// return "redirect:/list.html";
	}

	private String saveFile(MultipartFile file, String path) {
		// String path ="/Users/vinlam/upload";
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {

				String fileName = file.getOriginalFilename();

				// 文件保存路径
				// String savePath = path + file.getOriginalFilename();

				String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());// 文件后缀
				fileName = new Date().getTime() + "_" + new Random().nextInt(1000) + fileType;// 新的文件名

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String fileAdd = sdf.format(new Date());
				File targetFile = null;
				String filePath = path + "/" + fileAdd;
				// 获取文件夹路径
				File newfilepath = new File(filePath);

				if (!newfilepath.exists() && !newfilepath.isDirectory()) {
					newfilepath.mkdirs();
				}
				targetFile = new File(newfilepath, fileName);

				file.transferTo(targetFile);
				return fileAdd + "/" + fileName;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	@RequestMapping("/streamfileupload")
	public String fileUpload(@RequestParam("files") CommonsMultipartFile files, HttpServletRequest request)
			throws IOException {
		String path = request.getSession().getServletContext().getRealPath("resources/upload/imgs"); // 文件存储位置
		// 用来检测程序运行时间
		long startTime = System.currentTimeMillis();
		System.out.println("fileName：" + files.getOriginalFilename());
		System.out.println("item fileName：" + files.getFileItem().getName());
		OutputStream os = null;
		InputStream is = null;
		try {
			if (!files.isEmpty()) {
				File newfilepath = new File(path);
				if (!newfilepath.exists() && !newfilepath.isDirectory()) {
					newfilepath.mkdirs();
				}
				// 获取输出流
				os = new FileOutputStream(
						newfilepath.getAbsolutePath() + new Date().getTime() + files.getOriginalFilename());
				// 获取输入流 CommonsMultipartFile 中可以直接得到文件的流
				is = files.getInputStream();
				int temp;
				// 一个一个字节的读取并写入
				while ((temp = is.read()) != (-1)) {
					os.write(temp);
				}
			} else {
				return "请选择上传文件";
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.flush();
				os.close();
			}
			if (is != null) {
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
	public ResponseEntity<List<String>> springUpload(HttpServletRequest request)
			throws IllegalStateException, IOException, FileNotFoundException {
		long startTime = System.currentTimeMillis();
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());

		// 设置编码
		multipartResolver.setDefaultEncoding("utf-8");
		// 检查form中是否有enctype="multipart/form-data"
		List<String> list = new ArrayList<String>();

		list = FileUploadUtil.uploadFile(request, "t", "");
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