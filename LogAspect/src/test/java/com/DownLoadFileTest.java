package com;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.util.DownLoadThreadUtil;

/**
 * 多线程下载
 */
public class DownLoadFileTest {
	public static final String PATH = "http://120.203.56.190:8088/upload/mobilelist.xml";
	public static int threadCount = 3;// 进行下载的线程数量

	public static void main(String[] args) {
		try {
			URL url = new URL(PATH);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(8000);
			conn.setReadTimeout(8000);
			conn.connect();
			if (conn.getResponseCode() == 200) {
				int length = conn.getContentLength();// 返回文件大小
				// 占据文件空间
				File file = new File("mobilelist.xml");
				@SuppressWarnings("resource")
				RandomAccessFile mAccessFile = new RandomAccessFile(file, "rwd");// "rwd"可读，可写
				mAccessFile.setLength(length);// 占据文件的空间
				int size = length / threadCount;
				for (int id = 0; id < threadCount; id++) {
					// 1、确定每个线程的下载区间
					// 2、开启对应子线程下载
					int startIndex = id * size;
					int endIndex = (id + 1) * size - 1;
					if (id == threadCount - 1) {
						endIndex = length - 1;
					}
					System.out.println("第" + id + "个线程的下载区间为" + startIndex + "--" + endIndex);
					new DownLoadThreadUtil(startIndex, endIndex, PATH, id).start();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}