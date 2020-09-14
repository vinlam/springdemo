package com;

import java.io.File;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Encoder;

@Service
public class RSAKeyGen implements KeyGen {
	private final static Logger log = LoggerFactory.getLogger(RSAKeyGen.class);
	@Override
	public Map<String, String> create(String key) {
		Map<String,String> map = new HashMap<String,String>();
		KeyPair keyPair;
		try {
			keyPair = RSAUtils.generateKeyPair(key);
			// 获取 公钥 和 私钥
	        PublicKey pubKey = keyPair.getPublic();
	        PrivateKey priKey = keyPair.getPrivate();
	        // 获取密钥编码后的格式
			byte[] pubBytes = pubKey.getEncoded();
			byte[] priBytes = priKey.getEncoded();

			// 转换为Base64文本
			String pubK = new BASE64Encoder().encode(pubBytes);
			String priK = new BASE64Encoder().encode(priBytes);
			map.put("pubKey", pubK);
			map.put("priKey", priK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("生成密钥失败："+e.getMessage());
		}
        

		return map;
	}

}
