package com;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestMd5AndSha1 {
	
	public static String md5(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(data.getBytes());
		StringBuffer buf = new StringBuffer();
		byte[] bits = md.digest();
		for(int i=0;i<bits.length;i++){
			int a = bits[i];
			if(a<0) a+=256;
			if(a<16) buf.append("0");
			buf.append(Integer.toHexString(a));
		}
		return buf.toString();
	}
	
	public static String sha1(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA1");
		md.update(data.getBytes());
		StringBuffer buf = new StringBuffer();
		byte[] bits = md.digest();
		for(int i=0;i<bits.length;i++){
			int a = bits[i];
			if(a<0) a+=256;
			if(a<16) buf.append("0");
			buf.append(Integer.toHexString(a));
		}
		return buf.toString();
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException{
		String data = "abc";
		//MD5
		System.out.println("MD5 : "+md5(data));
		//SHA1
		System.out.println("SHA1 : "+sha1(data));
	}
}