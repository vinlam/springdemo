package com;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA 工具类（生成/保存密钥对、加密、解密）
 * 
 */
public class RSAUtils {

	/** 算法名称 */
	private static final String ALGORITHM = "RSA";

	/** 密钥长度 */
	private static final int KEY_SIZE = 2048;

	/**
	 * 随机生成密钥对（包含公钥和私钥）
	 */
	public static KeyPair generateKeyPair() throws Exception {
		// 获取指定算法的密钥对生成器
		KeyPairGenerator gen = KeyPairGenerator.getInstance(ALGORITHM);

		// 初始化密钥对生成器（指定密钥长度,使用默认的安全随机数源）
		gen.initialize(KEY_SIZE);

		// 随机生成一对密钥（包含公钥和私钥）
		return gen.generateKeyPair();
	}
	/**
	 * 生成密钥对（包含公钥和私钥）
	 */
	public static KeyPair generateKeyPair(String ormKey) throws Exception {
		if(StringUtils.isBlank(ormKey)) {
			throw new IllegalArgumentException("原始Key不合法");
		}
		// 获取指定算法的密钥对生成器
		KeyPairGenerator gen = KeyPairGenerator.getInstance(ALGORITHM);
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG", ormKey);
		long mySeed;
		mySeed = System.currentTimeMillis();
		random.setSeed(mySeed);
		// 初始化密钥对生成器（指定密钥长度,使用默认的安全随机数源）
		gen.initialize(KEY_SIZE,random);
		
		// 随机生成一对密钥（包含公钥和私钥）
		return gen.generateKeyPair();
	}

	/**
	 * 将 公钥/私钥 编码后以 Base64 的格式保存到指定文件
	 */
	public static void saveKeyForEncodedBase64(Key key, File keyFile) throws IOException {
		// 获取密钥编码后的格式
		byte[] encBytes = key.getEncoded();

		// 转换为Base64文本
		String encBase64 = new BASE64Encoder().encode(encBytes);

		// 保存到文件
		IOUtils.writeFile(encBase64, keyFile);
	}

	/**
	 * 根据公钥的 Base64 文本创建公钥对象
	 */
	public static PublicKey getPublicKey(String pubKeyBase64) throws Exception {
		// 把公钥的Base64文本转换为已编码的公钥bytes
		byte[] encPubKey = new BASE64Decoder().decodeBuffer(pubKeyBase64);

		// 创建已编码的公钥规格
		X509EncodedKeySpec encPubKeySpec = new X509EncodedKeySpec(encPubKey);

		// 获取指定算法的密钥工厂,根据已编码的公钥规格,生成公钥对象
		return KeyFactory.getInstance(ALGORITHM).generatePublic(encPubKeySpec);
	}

	/**
	 * 根据私钥的 Base64 文本创建私钥对象
	 */
	public static PrivateKey getPrivateKey(String priKeyBase64) throws Exception {
		// 把私钥的Base64文本转换为已编码的私钥bytes
		byte[] encPriKey = new BASE64Decoder().decodeBuffer(priKeyBase64);

		// 创建已编码的私钥规格
		PKCS8EncodedKeySpec encPriKeySpec = new PKCS8EncodedKeySpec(encPriKey);

		// 获取指定算法的密钥工厂,根据已编码的私钥规格,生成私钥对象
		return KeyFactory.getInstance(ALGORITHM).generatePrivate(encPriKeySpec);
	}

	/**
	 * 公钥加密数据
	 */
	public static byte[] encrypt(byte[] plainData, PublicKey pubKey) throws Exception {
		// 获取指定算法的密码器
		Cipher cipher = Cipher.getInstance(ALGORITHM);

		// 初始化密码器（公钥加密模型）
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);

		// 加密数据, 返回加密后的密文
		return cipher.doFinal(plainData);
	}

	/**
	 * 私钥解密数据
	 */
	public static byte[] decrypt(byte[] cipherData, PrivateKey priKey) throws Exception {
		// 获取指定算法的密码器
		Cipher cipher = Cipher.getInstance(ALGORITHM);

		// 初始化密码器（私钥解密模型）
		cipher.init(Cipher.DECRYPT_MODE, priKey);

		// 解密数据, 返回解密后的明文
		return cipher.doFinal(cipherData);
	}
	
	public static void main(String[] args) throws Exception {
        // 随机生成一对密钥（包含公钥和私钥）
        KeyPair keyPair = RSAUtils.generateKeyPair();
        // 获取 公钥 和 私钥
        PublicKey pubKey = keyPair.getPublic();
        PrivateKey priKey = keyPair.getPrivate();

        // 保存 公钥 和 私钥
        RSAUtils.saveKeyForEncodedBase64(pubKey, new File("/cert/pub.txt"));
        RSAUtils.saveKeyForEncodedBase64(priKey, new File("/cert/pri.txt"));

        /*
         * 上面代码是事先生成密钥对保存,
         * 下面代码是在实际应用中, 客户端和服务端分别拿现成的公钥和私钥加密/解密数据。
         */

        // 原文数据
        String data = "你好, World!";

        // 客户端: 加密
        byte[] cipherData = clientEncrypt(data.getBytes(), new File("/cert/pub.txt"));
        // 服务端: 解密
        byte[] plainData = serverDecrypt(cipherData, new File("/cert/pri.txt"));

        // 输出查看原文
        System.out.println(new String(plainData));  // 结果打印: 你好, World!
    }

    /**
     * 客户端加密, 返回加密后的数据
     */
    private static byte[] clientEncrypt(byte[] plainData, File pubFile) throws Exception {
        // 读取公钥文件, 创建公钥对象
        PublicKey pubKey = RSAUtils.getPublicKey(IOUtils.readFile(pubFile));

        // 用公钥加密数据
        byte[] cipher = RSAUtils.encrypt(plainData, pubKey);

        return cipher;
    }

    /**
     * 服务端解密, 返回解密后的数据
     */
    private static byte[] serverDecrypt(byte[] cipherData, File priFile) throws Exception {
        // 读取私钥文件, 创建私钥对象
        PrivateKey priKey = RSAUtils.getPrivateKey(IOUtils.readFile(priFile));

        // 用私钥解密数据
        byte[] plainData = RSAUtils.decrypt(cipherData, priKey);

        return plainData;
    }

}
