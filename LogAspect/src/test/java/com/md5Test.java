package com;

import java.security.MessageDigest;

public class md5Test {
	 public static byte[] originMD5(byte[] input) throws Exception {
	        MessageDigest md5 = MessageDigest.getInstance("MD5");
	        byte[] out = md5.digest(input);
	        return out;
	    }
	 
	    /**
	     * @param input 输入
	     * @return 返回16个字节
	     * @throws Exception
	     */
	    public static byte[] MD5(byte[] input) throws Exception {
	        String str = new String(input, 0, input.length);
	        //创建MD5加密对象
	        MessageDigest md5 = MessageDigest.getInstance("MD5");
	        // 进行加密
	        md5.update(str.getBytes());
	        //获取加密后的字节数组
	        byte[] md5Bytes = md5.digest();
	        String res = "";
	        for (int i = 0; i < md5Bytes.length; i++) {
	            int temp = md5Bytes[i] & 0xFF;
	            // 转化成十六进制不够两位，前面加零
	            if (temp <= 0XF) {
	                res += "0";
	            }
	            res += Integer.toHexString(temp);
	        }
	        return res.getBytes();
	    }
	 
	    public static void main(String[] args) throws Exception {
	        byte[] data = {0x4C, 0x2B, 0x3E, 0x5A, 0x26, 0x3A, 0x3C, 0x18};
	        byte[] md5Data = MD5(data);
	        String strMd5Key = new String(md5Data, 0, md5Data.length);
	 
	        System.out.println(strMd5Key);
	    }
}
