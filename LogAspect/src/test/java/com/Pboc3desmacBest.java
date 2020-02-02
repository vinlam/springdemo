/**
 * 
 */
package com;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class Pboc3desmacBest {
	
	public static final byte[] ZERO_IVC = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 };
	private static char[] HEXCHAR = { '0', '1', '2', '3', '4', '5', '6', '7',
		'8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	protected static transient final Logger log = LoggerFactory.getLogger(Pboc3desmacBest.class);
	
	public static String sign(Map<String, String> params, String key){
		
		return sign(params,key,"0000000000000000");
		
	}
	
	/**
	 * 对应答的json串签名
	 */
	public static String sign(String respJsonStr, String key){
		return sign(respJsonStr, key, "0000000000000000");
	}
	
	/**
	 * true:老签名
	 * false:新签名
	 * @param params
	 * @param key
	 * @param type
	 * @return
	 */
	public static String sign(Map<String, String> params, String key, boolean type){
		return sign(params,key, "0000000000000000", type);// 
	}
	
	public static String sign(Map<String, String> params, String key, String iv){
		List<String> list=new ArrayList<String>();
		for (String mapkey : params.keySet()) {
			String value=params.get(mapkey);
			if(StringUtils.isNotEmpty(value)){
				list.add(new StringBuilder(mapkey).append("=").append(value).toString());
			}
		}
		Collections.sort(list);
		String signData=StringUtils.join(list, "&");
		log.debug("签名数据：{}",signData);
		byte[]	sign=null;
		try {
			sign=calculatePboc3desMAC(signData.getBytes("UTF-8"),hexStringToBytes(key), hexStringToBytes(iv));
		} catch (Exception e) {
			log.error(null,e);
		}
		if(log.isDebugEnabled()){
			log.debug("签名结果：{}",encodeHexString(sign));
		}
		//royleexh 不再截取
//		sign=ArrayUtils.subarray(sign, 0, 4);
		return encodeHexString(sign);
	}
	
	/**
	 * 不需要对json串进行排序
	 */
	public static String sign(String respJsonStr, String key, String iv) {
		String signData = respJsonStr;
		log.debug("签名数据：{}", signData);
		byte[] sign = null;
		try {
			sign = calculatePboc3desMAC(signData.getBytes("UTF-8"),
					hexStringToBytes(key), hexStringToBytes(iv));
		} catch (Exception e) {
			log.error(null, e);
		}
		if (log.isDebugEnabled()) {
			log.debug("签名结果：{}", encodeHexString(sign));
		}
		return encodeHexString(sign);
	}
	
	/**
	 * @param params
	 * @param key
	 * @param type true:截取; false:不截取
	 * @return
	 */
	public static String sign(Map<String, String> params, String key, String iv, boolean type){
		List<String> list=new ArrayList<String>();
		for (String mapkey : params.keySet()) {
			String value=params.get(mapkey);
			if(StringUtils.isNotEmpty(value)){
				list.add(new StringBuilder(mapkey).append("=").append(value).toString());
			}
		}
		Collections.sort(list);
		String signData=StringUtils.join(list, "&");
		log.debug("签名数据：{}",signData);
		byte[]	sign=null;
		try {
			sign=calculatePboc3desMAC(signData.getBytes("UTF-8"),hexStringToBytes(key), hexStringToBytes(iv));
		} catch (Exception e) {
			log.error(null,e);
		}
		if(log.isDebugEnabled()){
			log.debug("签名结果：{}",encodeHexString(sign));
		}
		// royleexh 根据type决定截取不截取
		if(type){
			sign=ArrayUtils.subarray(sign, 0, 4);
		}
		return encodeHexString(sign);
	}
	
	
	public static String generateKey(){
		KeyGenerator kg;
		try {
			kg = KeyGenerator.getInstance("DESede");
		} catch (NoSuchAlgorithmException e) {
			log.error(null,e);
			return null;
		}
		kg.init(112);//must be equal to 112 or 168
		byte[] key24 =  kg.generateKey().getEncoded();
		byte[] result = new byte[16];
		System.arraycopy(key24, 0, result, 0, 16);
		return encodeHexString(result);
	}
	
	public static final byte[] hexStringToBytes(String s) {
		byte[] bytes;
		bytes = new byte[s.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),
					16);
		}
		return bytes;
	}
	
	public static String encodeHexString(byte[] b) {
		if(b == null)
		{
			return null;
		}
			
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEXCHAR[(b[i] & 0xf0) >>> 4]);
			sb.append(HEXCHAR[b[i] & 0x0f]);
		}
		return sb.toString();
	}
	
	/**
	 * 计算MAC(hex) PBOC_3DES_MAC(符合ISO9797Alg3Mac标准)
	 * (16的整数补8000000000000000) 前n-1组使用单长密钥DES 使用密钥是密钥的左8字节） 最后1组使用双长密钥3DES （使用全部16字节密钥）
	 * 本项目用于apdu指令的mac生成
	 * @param data 带计算的数据
	 * @param key 16字节秘钥
	 * @param icv 算法向量
	 * @return mac签名
	 * @throws Exception
	 */
	public static byte[] calculatePboc3desMAC(byte[] data, byte[] key, byte[] icv) throws Exception {
		
		if (key == null || data == null) throw new RuntimeException("data or key is null.");
		if(key.length != 16) throw new RuntimeException("key length is not 16 byte.");
		
		byte[] leftKey = new byte[8];
		System.arraycopy(key, 0, leftKey, 0, 8);
		
		// 拆分数据（8字节块/Block）
		int dataLength = data.length;
		int groupCount = dataLength/8 + 1;
		int lastGroupLength = dataLength%8;
		
		byte[][] dataGroup = new byte[groupCount][];

		for (int i = 0; i < groupCount; i++) {
			byte[] dataBlk = new byte[8];
			
			if(i == groupCount -1){
				System.arraycopy(data, i*8, dataBlk, 0, dataLength%8);
			} else {
				System.arraycopy(data, i*8, dataBlk, 0, 8);
			}
			dataGroup[i] = dataBlk;
		}
		
		dataGroup[groupCount-1][lastGroupLength] = (byte) 0x80;
		
		
		byte[] desXor = xOr(dataGroup[0], icv);
		for (int i = 1; i < groupCount; i++) {
			byte[] des = encryptByDesCbc(desXor, leftKey);
			desXor = xOr(dataGroup[i], des);
		}
		desXor = encryptBy3DesCbc(desXor, key);
		return desXor;
		
		
	}

	/**
	 * 将s1和s2做异或，然后返回
	 * @param s1
	 * @param s2
	 * @return
	 */
	private static byte[] xOr(byte[] b1, byte[] b2) {
		byte[] tXor = new byte[Math.min(b1.length, b2.length)];
		for (int i = 0; i < tXor.length; i++)
			tXor[i] = (byte) (b1[i] ^ b2[i]); // 异或(Xor)
		return tXor;
	}
	
	public static byte[] encryptBy3DesCbc(byte[] content, byte[] key) throws Exception {
		byte[] _3deskey = new byte[24];
		System.arraycopy(key, 0, _3deskey, 0, 16);
		System.arraycopy(key, 0, _3deskey, 16, 8);
		
        Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");
        SecretKey secureKey = new SecretKeySpec(_3deskey, "DESede");
        IvParameterSpec iv = new IvParameterSpec(ZERO_IVC);
        cipher.init(Cipher.ENCRYPT_MODE, secureKey, iv);
        return cipher.doFinal(content);  
    }
	
	
	/**
	 * des的cbc模式加密算法
	 * @param content 待加密数据
	 * @param key 加密秘钥
	 * @return 加密结果
	 * @throws Exception
	 */
	public static byte[] encryptByDesCbc(byte[] content, byte[] key) throws Exception {
		return encryptByDesCbc(content, key, ZERO_IVC);
	}
	
	/**
	 * des的cbc模式加密算法
	 * @param content 待加密数据
	 * @param key 加密秘钥
	 * @return 加密结果
	 * @throws Exception
	 */
	public static byte[] encryptByDesCbc(byte[] content, byte[] key, byte[] icv) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
		IvParameterSpec iv = new IvParameterSpec(icv);
		
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv, sr);
		
		return cipher.doFinal(content);
	}

	public static String sort(Map<String, Object> params) {
		List<String> list = new ArrayList<String>();
		for (String mapkey : params.keySet()) {
			String value = params.get(mapkey) + "";
			if (StringUtils.isNotEmpty(value)) {
				list.add(new StringBuilder(mapkey).append("=").append(value).toString());
			}
		}
		Collections.sort(list);
		String signData = StringUtils.join(list, "&");
		log.debug("签名数据：{}", signData);
		return signData;
	}

	public static String sort2(Map<String, Object> params) {
		List<String> list = new ArrayList<String>();
		for (String mapkey : params.keySet()) {
			String value = params.get(mapkey) + "";
			if (StringUtils.isNotEmpty(value)) {
				list.add(new StringBuilder(mapkey).append("=").append(value).toString());
			}
		}
		Collections.sort(list);
		String signData = StringUtils.join(list, "&");
		log.debug("签名数据：{}", signData);
		return signData;
	}
}
