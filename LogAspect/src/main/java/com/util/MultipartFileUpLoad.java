package com.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class MultipartFileUpLoad {
	public Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

	/**
	 * 封装上传
	 * 
	 * @param request
	 * @param fileName          页面表单file元素name
	 * @param folderName创建的文件夹名
	 * @param type              判断类型
	 * @return 本地文件路径
	 */

	public String transferToFile(HttpServletRequest request, String fileName, String folderName, FileTypeEnum type)
			throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile mfile = multipartRequest.getFile(fileName);

		if (mfile.getSize() == 0) {
			throw new Exception("请选择文件！");
		}
		FILE_TYPE_MAP.clear();
		if (type.equals(FileTypeEnum.audio)) {
			getAudioFileType();
		} else if (type.equals(FileTypeEnum.picture)) {
			getPicFileType();
		} else if (type.equals(FileTypeEnum.video)) {
			getVideoFileType();
		}

		InputStream is = mfile.getInputStream();
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();

		for (int i = 0; i < 10; i++) {
			int a = is.read();
			bytestream.write(a);
		}
		byte imgdata[] = bytestream.toByteArray();

		if (getFileTypeByStream(imgdata) == null) {
			throw new Exception("不是指定类型文件！");
		}
		try {
			InetAddress addr = InetAddress.getLocalHost(); // 获取本地计算机信息

			File file = new File(request.getSession().getServletContext()
					.getRealPath("/upload/" + addr.getHostName().toString() + folderName + "/"
							+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "/"
							+ mfile.getOriginalFilename()));
			if (!file.exists()) { // 判断文件夹是否存在
				file.mkdirs(); // 不存在则创建
			}
			mfile.transferTo(file);
			System.out.println(file.getPath());
			return file.getPath();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getFileTypeByStream(byte[] b) {
		String filetypeHex = String.valueOf(getFileHexString(b));
		System.out.println(filetypeHex);
		Iterator<Entry<String, String>> entryiterator = FILE_TYPE_MAP.entrySet().iterator();
		while (entryiterator.hasNext()) {
			Entry<String, String> entry = entryiterator.next();
			String fileTypeHexValue = entry.getValue().toUpperCase();
			if (filetypeHex.toUpperCase().startsWith(fileTypeHexValue)) {
				return entry.getKey();
			}
		}
		return null;
	}

	public String getFileHexString(byte[] b) {
		StringBuilder stringBuilder = new StringBuilder();
		if (b == null || b.length <= 0) {
			return null;
		}
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public void getVideoFileType() {
		FILE_TYPE_MAP.put("ram", "2E7261FD"); // Real Audio (ram)
		FILE_TYPE_MAP.put("rm", "2E524D46"); // Real Media (rm)
		FILE_TYPE_MAP.put("mov", "00000014667479707174"); // Quicktime (mov)
		// FILE_TYPE_MAP.put("rmvb", "2e524d46000000120001"); // rmvb
		FILE_TYPE_MAP.put("avi", "41564920");
		FILE_TYPE_MAP.put("avi", "52494646b440c02b4156");
		FILE_TYPE_MAP.put("flv", "464C5601050000000900");
		FILE_TYPE_MAP.put("mp4", "00000020667479706d70");
		FILE_TYPE_MAP.put("wmv", "3026b2758e66CF11a6d9");
		// FILE_TYPE_MAP.put("3gp", "00000014667479703367");
		FILE_TYPE_MAP.put("mkv", "1a45dfa3010000000000");
	}

	public void getPicFileType() {
		FILE_TYPE_MAP.put("jpg", "FFD8FF"); // JPEG (jpg)
		FILE_TYPE_MAP.put("png", "89504E47"); // PNG (png)
		FILE_TYPE_MAP.put("gif", "47494638"); // GIF (gif)
		FILE_TYPE_MAP.put("bmp", "424D"); // Windows Bitmap (bmp)
		FILE_TYPE_MAP.put("png", "89504E470D0a1a0a0000"); // PNG (png)
		FILE_TYPE_MAP.put("bmp", "424d228c010000000000"); // 16色位图(bmp)
		FILE_TYPE_MAP.put("bmp", "424d8240090000000000"); // 24位位图(bmp)
		FILE_TYPE_MAP.put("bmp", "424d8e1b030000000000"); // 256色位图(bmp
	}

	public void getAudioFileType() {
		FILE_TYPE_MAP.put("wav", "57415645"); // Wave (wav)
		FILE_TYPE_MAP.put("mid", "4D546864"); // MIDI (mid)
		FILE_TYPE_MAP.put("mp3", "49443303000000002176");
		FILE_TYPE_MAP.put("wav", "52494646e27807005741");
		FILE_TYPE_MAP.put("aac", "fff1508003fffcda004c");
		FILE_TYPE_MAP.put("wv", "7776706ba22100000704");
		FILE_TYPE_MAP.put("flac", "664c6143800000221200");
	}

	public MultipartFileUpLoad() {
	}
}