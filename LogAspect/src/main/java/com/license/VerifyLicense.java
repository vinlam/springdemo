package com.license;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.prefs.Preferences;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.DefaultCipherParam;
import de.schlichtherle.license.DefaultKeyStoreParam;
import de.schlichtherle.license.DefaultLicenseParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseContentException;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

public class VerifyLicense {
	private final static Logger log = LoggerFactory.getLogger(VerifyLicense.class);
	
	private String pubAlias;
    private String keyStorePwd;
    private String subject;
    private String licDir;
    private String pubPath;

    public VerifyLicense() {
        // 取默认配置
        setConf("/licenseVerifyParam.properties");
    }

    public VerifyLicense(String confPath) {
        setConf(confPath);
    }

    /**
     * 通过外部配置文件获取配置信息
     *
     * @param confPath 配置文件路径
     */
    private void setConf(String confPath) {
        // 获取参数
        Properties prop = new Properties();
        InputStream in = getClass().getResourceAsStream(confPath);
        try {
            prop.load(in);
        } catch (IOException e) {
            log.error("VerifyLicense Properties load inputStream error.", e);
        }
        this.subject = prop.getProperty("subject");
        this.pubAlias = prop.getProperty("public.alias");
        this.keyStorePwd = prop.getProperty("key.store.pwd");
        this.licDir = prop.getProperty("license.dir");
        this.pubPath = prop.getProperty("public.store.path");
    }

    /**
     * 安装证书证书
     */
    public void install() {
        try {
            LicenseManager licenseManager = getLicenseManager();
            licenseManager.install(new File(licDir));
            log.info("安装证书成功!");
        } catch (Exception e) {
            log.error("安装证书失败!", e);
            Runtime.getRuntime().halt(1);
        }

    }

    private LicenseManager getLicenseManager() {
        return LicenseManagerHolder.getLicenseManager(initLicenseParams());
    }

    /**
     * 初始化证书的相关参数
     */
    private LicenseParam initLicenseParams() {
        Class<VerifyLicense> clazz = VerifyLicense.class;
        Preferences pre = Preferences.userNodeForPackage(clazz);
        CipherParam cipherParam = new DefaultCipherParam(keyStorePwd);
        KeyStoreParam pubStoreParam = new DefaultKeyStoreParam(clazz, pubPath, pubAlias, keyStorePwd, null);
        return new DefaultLicenseParam(subject, pre, pubStoreParam, cipherParam);
    }

    /**
     * 验证证书的合法性
     */
    public boolean vertify() {
        try {
            LicenseManager licenseManager = getLicenseManager();
            LicenseContent verify = licenseManager.verify();
            log.info("验证证书成功!");
            Map<String, String> extra = (Map) verify.getExtra();
            String ip = extra.get("ip");
            InetAddress inetAddress = InetAddress.getLocalHost();
            String localIp = inetAddress.toString().split("/")[1];
            if (!Objects.equals(ip, localIp)) {
                log.error("IP 地址验证不通过");
                return false;
            }
            String mac = extra.get("mac");
            String localMac = getLocalMac(inetAddress);
            if (!Objects.equals(mac, localMac)) {
                log.error("MAC 地址验证不通过");
                return false;
            }
            log.info("IP、MAC地址验证通过");
            return true;
        } catch (LicenseContentException ex) {
            log.error("证书已经过期!", ex);
            return false;
        } catch (Exception e) {
            log.error("验证证书失败!", e);
            return false;
        }
    }

    /**
     * 得到本机 mac 地址
     *
     * @param inetAddress
     * @throws SocketException
     */
    private String getLocalMac(InetAddress inetAddress) throws SocketException {
        //获取网卡，获取地址
        byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            //字节转换为整数
            int temp = mac[i] & 0xff;
            String str = Integer.toHexString(temp);
            if (str.length() == 1) {
                sb.append("0" + str);
            } else {
                sb.append(str);
            }
        }
        return sb.toString().toUpperCase();
    }
}
