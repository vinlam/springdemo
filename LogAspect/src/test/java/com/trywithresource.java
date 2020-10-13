package com;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class trywithresource {
	public static void main(String[] args) throws IOException {

		try (ResourceSome some = new ResourceSome(); ResourceOther other = new ResourceOther()) {
			some.doSome();
			other.doOther();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		printFile();
		printFileJava7();
		printFileJava7MultiResource();
	}

	// 传统try catch JDK 1.7之前需要自行关闭资源
	private static void printFile() throws IOException {
//	    InputStream input = null;
//
//	    try {
////	    	String path = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
//	    	String path = trywithresource.class.getResource("/").toString(); 
//			System.out.println(path);
//	        input = new FileInputStream("/Users/vinlam/work/workspace/TestJava/bin/user.txt");
//
//	        int data = input.read();
//	        while(data != -1){
//	            System.out.print((char) data);
//	            data = input.read();
//	        }
//	    } finally {
//	        if(input != null){
//	            input.close();
//	        }
//	    }
		File file = new File("/Users/vinlam/work/workspace/TestJava/bin/user.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		try {
			String line;
			int count = 0;
			while ((line = br.readLine()) != null) {
				// process the line
				System.out.println(line);
				count+=1;
			}
			System.out.println("totalline:"+count);
		} finally {
			br.close();
		}
	}

	//
	private static void printFileJava7() throws IOException {
		File file = new File("/Users/vinlam/work/workspace/TestJava/bin/user.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				// process the line
				System.out.println(line);
			}
		}
	}

	private static void printFileJava7MultiResource() throws IOException {
		String str = null;
		try (FileInputStream input = new FileInputStream("/Users/vinlam/work/workspace/TestJava/bin/user.txt");
				BufferedInputStream bufferedInput = new BufferedInputStream(input)) {
			int counts;
			byte[] byt = new byte[1024];
			while ((counts = bufferedInput.read(byt, 0, byt.length)) != -1) {
				str = new String(byt, 0, counts);
				System.out.println(str);
			}
		}
	}
}

class ResourceSome implements AutoCloseable {
	void doSome() {
		System.out.println("do something");
	}

	@Override
	public void close() throws Exception {
		System.out.println("some resource is closed");
	}
}

class ResourceOther implements AutoCloseable {
	void doOther() {
		System.out.println("do other things");
	}

	@Override
	public void close() throws Exception {
		System.out.println("other resource is closed");
	}
}
