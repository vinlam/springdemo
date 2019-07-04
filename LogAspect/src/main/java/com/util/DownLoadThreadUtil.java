package com.util;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoadThreadUtil extends Thread {
	private int startIndex, endIndex, threadId;
	private String urlString;

	public DownLoadThreadUtil(int startIndex, int endIndex, String urlString, int threadId) {
		this.endIndex = endIndex;
		this.startIndex = startIndex;
		this.urlString = urlString;
		this.threadId = threadId;
	}

	@Override
	public void run() {
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(8000);
			conn.setReadTimeout(8000);
			conn.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);// 设置头信息属性,拿到指定大小的输入流
			if (conn.getResponseCode() == 206) {// 拿到指定大小字节流，由于拿到的使部分的指定大小的流，所以请求的code为206
				InputStream is = conn.getInputStream();
				File file = new File("mobilelist.xml");
				RandomAccessFile mAccessFile = new RandomAccessFile(file, "rwd");// "rwd"可读，可写
				mAccessFile.seek(startIndex);// 表示从不同的位置写文件
				byte[] bs = new byte[1024];
				int len = 0;
				int current = 0;
				while ((len = is.read(bs)) != -1) {
					mAccessFile.write(bs, 0, len);
					current += len;
					System.out.println("第" + threadId + "个线程下载了" + current);

				}
				mAccessFile.close();
				System.out.println("第" + threadId + "个线程下载完毕");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.run();
	}
}
