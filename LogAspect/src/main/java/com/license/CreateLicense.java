package com.license;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.prefs.Preferences;

import javax.security.auth.x500.X500Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.DefaultCipherParam;
import de.schlichtherle.license.DefaultKeyStoreParam;
import de.schlichtherle.license.DefaultLicenseParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

public class CreateLicense {
	private final static Logger log = LoggerFactory.getLogger(CreateLicense.class);
	/**
	 * X500Princal 是一个证书文件的固有格式，详见API
	 */
	private final static X500Principal DEFAULT_HOLDERAND_ISSUER = new X500Principal(
			"CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");

	private String priAlias;
	private String privateKeyPwd;
	private String keyStorePwd;
	private String subject;
	private String priPath;

	private String issued;
	private String notBefore;
	private String notAfter;
	private String ipAddress;
	private String macAddress;
	private String consumerType;
	private int consumerAmount;
	private String info;

	private String licPath;

	/**
	 * 构造器，参数初始化
	 *
	 * @param confPath 参数配置文件路径
	 */
	public CreateLicense(String confPath) {
		// 获取参数
		Properties prop = new Properties();
		//从当前类所在包下加载指定名称的文件，getClass是到当前列
        //InputStream in = this.getClass().getResourceAsStream("xx.properties");
        //从classpath根目录下加载指定名称的文件，这是因为/即代表根目录
        //InputStream in = this.getClass().getResourceAsStream("/xx.properties");
        //从classpath根目录下加载指定名称的文件，这是因为getClassLoader就会到根目录上
        //InputStream in = this.getClass().getClassLoader().getResourceAsStream("xx.properties");
		//ystem.out.println(this.getClass().getClassLoader().getResource("licenseCreateParam.properties"));
		//System.out.println(this.getClass().getResource("/licenseCreateParam.properties"));
		try (InputStream in = getClass().getResourceAsStream(confPath)) {
			prop.load(in);
		} catch (IOException e) {
			log.error("CreateLicense Properties load inputStream error.", e);
		}
		// common param
		priAlias = prop.getProperty("private.key.alias");
		privateKeyPwd = prop.getProperty("private.key.pwd");
		keyStorePwd = prop.getProperty("key.store.pwd");
		subject = prop.getProperty("subject");
		priPath = prop.getProperty("priPath");
		// license content
		issued = prop.getProperty("issuedTime");
		notBefore = prop.getProperty("notBefore");
		notAfter = prop.getProperty("notAfter");
		ipAddress = prop.getProperty("ipAddress");
		macAddress = prop.getProperty("macAddress");
		consumerType = prop.getProperty("consumerType");
		consumerAmount = Integer.valueOf(prop.getProperty("consumerAmount"));
		info = prop.getProperty("info");

		licPath = prop.getProperty("licPath");
	}

	/**
	 * 生成证书，在证书发布者端执行
	 *
	 * @throws Exception
	 */
	public void create() throws Exception {
		LicenseManager licenseManager = LicenseManagerHolder.getLicenseManager(initLicenseParams());
		if(licenseManager == null) {
			log.info("------ 证书发布失败 ------");
		}
		licenseManager.store(buildLicenseContent(), new File(licPath));
		log.info("------ 证书发布成功 ------");
		
	}

	/**
	 * 初始化证书的相关参数
	 *
	 * @return
	 */
	private LicenseParam initLicenseParams() {
		Class<CreateLicense> clazz = CreateLicense.class;
		Preferences preferences = Preferences.userNodeForPackage(clazz);
		// 设置对证书内容加密的对称密码
		CipherParam cipherParam = new DefaultCipherParam(keyStorePwd);
		// 参数 1,2 从哪个Class.getResource()获得密钥库;
		// 参数 3 密钥库的别名;
		// 参数 4 密钥库存储密码;
		// 参数 5 密钥库密码
		KeyStoreParam privateStoreParam = new DefaultKeyStoreParam(clazz, priPath, priAlias, keyStorePwd,
				privateKeyPwd);
		// 返回生成证书时需要的参数
		return new DefaultLicenseParam(subject, preferences, privateStoreParam, cipherParam);
	}

	/**
	 * 通过外部配置文件构建证书的的相关信息
	 *
	 * @return
	 * @throws ParseException
	 */
	public LicenseContent buildLicenseContent() throws ParseException {
		LicenseContent content = new LicenseContent();
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		content.setConsumerAmount(consumerAmount);
		content.setConsumerType(consumerType);
		content.setHolder(DEFAULT_HOLDERAND_ISSUER);
		content.setIssuer(DEFAULT_HOLDERAND_ISSUER);
		content.setIssued(formate.parse(issued));
		content.setNotBefore(formate.parse(notBefore));
		content.setNotAfter(formate.parse(notAfter));
		content.setInfo(info);
		// 扩展字段
		Map<String, String> map = new HashMap<>(4);
		map.put("ip", ipAddress);
		map.put("mac", macAddress);
		content.setExtra(map);
		return content;
	}

	public static void main(String[] args) throws Exception {
		CreateLicense clicense = new CreateLicense("/licenseCreateParam.properties");
		clicense.create();
	}
}