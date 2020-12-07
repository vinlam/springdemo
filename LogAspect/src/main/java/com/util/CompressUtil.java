package com.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * ZIP压缩文件操作工具类 支持密码 依赖zip4j开源项目(http://www.lingala.net/zip4j/) 版本1.3.1
 * 
 * @author ninemax
 */
public class CompressUtil {

	/**
	 * 使用给定密码解压指定的ZIP压缩文件到指定目录
	 * <p>
	 * 如果指定目录不存在,可以自动创建,不合法的路径将导致异常被抛出
	 * 
	 * @param zip    指定的ZIP压缩文件
	 * @param dest   解压目录
	 * @param passwd ZIP文件的密码
	 * @return 解压后文件数组
	 * @throws ZipException 压缩文件有损坏或者解压缩失败抛出
	 */
	public static File[] unzip(String zip, String dest, String passwd) throws ZipException {
		File zipFile = new File(zip);
		return unzip(zipFile, dest, passwd);
	}

	/**
	 * 使用给定密码解压指定的ZIP压缩文件到当前目录
	 * 
	 * @param zip    指定的ZIP压缩文件
	 * @param passwd ZIP文件的密码
	 * @return 解压后文件数组
	 * @throws ZipException 压缩文件有损坏或者解压缩失败抛出
	 */
	public static File[] unzip(String zip, String passwd) throws ZipException {
		File zipFile = new File(zip);
		File parentDir = zipFile.getParentFile();
		return unzip(zipFile, parentDir.getAbsolutePath(), passwd);
	}

	/**
	 * 使用给定密码解压指定的ZIP压缩文件到指定目录
	 * <p>
	 * 如果指定目录不存在,可以自动创建,不合法的路径将导致异常被抛出
	 * 
	 * @param zip    指定的ZIP压缩文件
	 * @param dest   解压目录
	 * @param passwd ZIP文件的密码
	 * @return 解压后文件数组
	 * @throws ZipException 压缩文件有损坏或者解压缩失败抛出
	 */
	public static File[] unzip(File zipFile, String dest, String passwd) throws ZipException {
		ZipFile zFile = new ZipFile(zipFile);
		zFile.setFileNameCharset("GBK");
		if (!zFile.isValidZipFile()) {
			throw new ZipException("压缩文件不合法,可能被损坏.");
		}
		File destDir = new File(dest);
		if (destDir.isDirectory() && !destDir.exists()) {
			destDir.mkdir();
		}
		if (zFile.isEncrypted()) {
			zFile.setPassword(passwd.toCharArray());
		}
		zFile.extractAll(dest);

		List<FileHeader> headerList = zFile.getFileHeaders();
		List<File> extractedFileList = new ArrayList<File>();
		for (FileHeader fileHeader : headerList) {
			if (!fileHeader.isDirectory()) {
				extractedFileList.add(new File(destDir, fileHeader.getFileName()));
			}
		}
		File[] extractedFiles = new File[extractedFileList.size()];
		extractedFileList.toArray(extractedFiles);
		return extractedFiles;
	}

	/**
	 * 压缩指定文件到当前文件夹
	 * 
	 * @param src 要压缩的指定文件
	 * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败.
	 */
	public static String zip(String src) {
		return zip(src, null);
	}

	/**
	 * 使用给定密码压缩指定文件或文件夹到当前目录
	 * 
	 * @param src    要压缩的文件
	 * @param passwd 压缩使用的密码
	 * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败.
	 */
	public static String zip(String src, String passwd) {
		return zip(src, null, passwd);
	}

	/**
	 * 使用给定密码压缩指定文件或文件夹到当前目录
	 * 
	 * @param src    要压缩的文件
	 * @param dest   压缩文件存放路径
	 * @param passwd 压缩使用的密码
	 * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败.
	 */
	public static String zip(String src, String dest, String passwd) {
		return zip(src, dest, true, passwd);
	}

	/**
	 * 使用给定密码压缩指定文件或文件夹到指定位置.
	 * <p>
	 * dest可传最终压缩文件存放的绝对路径,也可以传存放目录,也可以传null或者"".<br />
	 * 如果传null或者""则将压缩文件存放在当前目录,即跟源文件同目录,压缩文件名取源文件名,以.zip为后缀;<br />
	 * 如果以路径分隔符(File.separator)结尾,则视为目录,压缩文件名取源文件名,以.zip为后缀,否则视为文件名.
	 * 
	 * @param src         要压缩的文件或文件夹路径
	 * @param dest        压缩文件存放路径
	 * @param isCreateDir 是否在压缩文件里创建目录,仅在压缩文件为目录时有效.<br />
	 *                    如果为false,将直接压缩目录下文件到压缩文件.
	 * @param passwd      压缩使用的密码
	 * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败.
	 */
	public static String zip(String src, String dest, boolean isCreateDir, String passwd) {
		File srcFile = new File(src);
		dest = buildDestinationZipFilePath(srcFile, dest);
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // 压缩方式
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); // 压缩级别
		if (!StringUtils.isEmpty(passwd)) {
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD); // 加密方式
			parameters.setPassword(passwd.toCharArray());
		}
		try {
			ZipFile zipFile = new ZipFile(dest);
			if (srcFile.isDirectory()) {
				// 如果不创建目录的话,将直接把给定目录下的文件压缩到压缩文件,即没有目录结构
				if (!isCreateDir) {
					File[] subFiles = srcFile.listFiles();
					ArrayList<File> temp = new ArrayList<File>();
					Collections.addAll(temp, subFiles);
					zipFile.addFiles(temp, parameters);
					return dest;
				}
				zipFile.addFolder(srcFile, parameters);
			} else {
				zipFile.addFile(srcFile, parameters);
			}
			return dest;
		} catch (ZipException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 构建压缩文件存放路径,如果不存在将会创建 传入的可能是文件名或者目录,也可能不传,此方法用以转换最终压缩文件的存放路径
	 * 
	 * @param srcFile   源文件
	 * @param destParam 压缩目标路径
	 * @return 正确的压缩文件存放路径
	 */
	private static String buildDestinationZipFilePath(File srcFile, String destParam) {
		if (StringUtils.isEmpty(destParam)) {
			if (srcFile.isDirectory()) {
				destParam = srcFile.getParent() + File.separator + srcFile.getName() + ".zip";
			} else {
				String fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
				destParam = srcFile.getParent() + File.separator + fileName + ".zip";
			}
		} else {
			createDestDirectoryIfNecessary(destParam); // 在指定路径不存在的情况下将其创建出来
			if (destParam.endsWith(File.separator)) {
				String fileName = "";
				if (srcFile.isDirectory()) {
					fileName = srcFile.getName();
				} else {
					fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
				}
				destParam += fileName + ".zip";
			}
		}
		return destParam;
	}

	/**
	 * 在必要的情况下创建压缩文件存放目录,比如指定的存放路径并没有被创建
	 * 
	 * @param destParam 指定的存放路径,有可能该路径并没有被创建
	 */
	private static void createDestDirectoryIfNecessary(String destParam) {
		File destDir = null;
		if (destParam.endsWith(File.separator)) {
			destDir = new File(destParam);
		} else {
			destDir = new File(destParam.substring(0, destParam.lastIndexOf(File.separator)));
		}
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
	}

	private static void removeDirFromZipArchive(String file, String removeDir) throws ZipException {
		// 创建ZipFile并设置编码
		ZipFile zipFile = new ZipFile(file);
		zipFile.setFileNameCharset("GBK");

		// 给要删除的目录加上路径分隔符
		if (!removeDir.endsWith(File.separator))
			removeDir += File.separator;

		// 如果目录不存在, 直接返回
		FileHeader dirHeader = zipFile.getFileHeader(removeDir);
		if (null == dirHeader)
			return;

		// 遍历压缩文件中所有的FileHeader, 将指定删除目录下的子文件名保存起来
		List headersList = zipFile.getFileHeaders();
		List<String> removeHeaderNames = new ArrayList<String>();
		for (int i = 0, len = headersList.size(); i < len; i++) {
			FileHeader subHeader = (FileHeader) headersList.get(i);
			if (subHeader.getFileName().startsWith(dirHeader.getFileName())
					&& !subHeader.getFileName().equals(dirHeader.getFileName())) {
				removeHeaderNames.add(subHeader.getFileName());
			}
		}
		// 遍历删除指定目录下的所有子文件, 最后删除指定目录(此时已为空目录)
		for (String headerNameString : removeHeaderNames) {
			zipFile.removeFile(headerNameString);
		}
		zipFile.removeFile(dirHeader);
	}

	/**
	 *
	 * @param srcFiles 要压缩的文件绝对路径列表（支持多个文件的合并压缩）
	 * @param destFile 要压缩的zip文件名
	 * @param passwd   压缩密码
	 * @param fileSize 分卷大小
	 * @return 压缩文件路径（如分卷会返回以 "," 分隔的文件路径列表）
	 */
	public static String zipBySplit(List<String> srcFiles, String destFile, String passwd, long fileSize)
			throws ZipException {

		String zipFiles = null;
		File tmpFile = new File(destFile);
		if (tmpFile.exists()) {
			tmpFile.delete();
		}
		// 创建压缩文件对象
		ZipFile zipFile = new ZipFile(destFile);
		// 创建文档对象集合
		ArrayList<File> filesToAdd = new ArrayList<File>();
		// 判断源压缩文件列表是否为空
		if (null != srcFiles && srcFiles.size() > 0) {
			int fileCount = srcFiles.size();
			for (int i = 0; i < fileCount; i++) {
				filesToAdd.add(new File(srcFiles.get(i)));
			}

			// 设置压缩参数
			ZipParameters parameters = new ZipParameters();
			// 设置压缩密码
			if (!StringUtils.isBlank(passwd)) {
				parameters.setEncryptFiles(true);
				parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
				parameters.setPassword(passwd.toCharArray());
			}
			// 设置压缩方式-默认
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			// 设置压缩级别-一般
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			// 设置默认分割大小为64KB
			// SplitLenth has to be greater than 65536 bytes
			if (fileSize == 0) {
				fileSize = 65536;
			}
			// 创建分卷压缩文件
			zipFile.createZipFile(filesToAdd, parameters, true, fileSize);

			// 获取分卷下载列表
			ArrayList<String> zipList = zipFile.getSplitZipFiles();
			if (null != zipList && zipList.size() > 0) {
				String surFix = ".z010";
				String surFixReplace = ".z10";
				// 单独处理第10个包的文件名做特殊处理
				for (int i = 0; i < zipList.size(); i++) {
					String file = zipList.get(i).trim();
					int length = file.length();
					String surFixTmp = file.substring(length - 5, length);
					if (surFix.equals(surFixTmp)) {
						file = file.replace(surFix, surFixReplace);
					}
					zipList.set(i, file);
				}

				// 初始化压缩数组
				String[] zipArray = new String[zipList.size()];
				zipList.toArray(zipArray);
				zipFiles = Arrays.toString(zipArray);
				int length = zipFiles.length();
				zipFiles = zipFiles.substring(1, length - 1);
			}

		}
		return zipFiles;
	}

	/**
	 * 解压缩(支持分卷压缩解压)
	 * 
	 * @param zipFilePath 指定的ZIP压缩文件 路径
	 * @param dest        解压目录
	 * @param passwd      ZIP文件的密码 。需要解压密码时必须传入，否则传入null即可
	 * @return 解压后文件名数组
	 * @throws ZipException 压缩文件有损坏或者解压缩失败抛出
	 */
	public static String[] unzipBySplit(String zipFilePath, String dest, String passwd) throws ZipException {

		File zipFile = new File(zipFilePath);
		ZipFile zFile = new ZipFile(zipFile);
		// 设置编码格式
		zFile.setFileNameCharset("UTF-8");
		if (!zFile.isValidZipFile()) {
			throw new ZipException("压缩文件不合法，可能被损坏!");
		}
		File destDir = new File(dest);
		// 判断是文件夹路径且不存在
		if (destDir.isDirectory() && !destDir.exists()) {
			destDir.mkdir();
		}
		// 判断文件是否加密
		if (zFile.isEncrypted()) {
			if (StringUtils.isBlank(passwd)) {
				throw new ZipException("文件已加密，需要解压密码，解压密码不能为空！");
			} else {
				zFile.setPassword(passwd.toCharArray());
			}

		}
		// 解压文件
		zFile.extractAll(dest);
		ArrayList<String> extractedFileList = new ArrayList<String>();
		List<FileHeader> headerList = zFile.getFileHeaders();
		if (headerList != null && headerList.size() > 0) {
			for (FileHeader fileHeader : headerList) {
				if (!fileHeader.isDirectory()) {
					extractedFileList.add(fileHeader.getFileName());
				}
			}
		}

		String[] extractedFiles = new String[extractedFileList.size()];
		extractedFileList.toArray(extractedFiles);
		return extractedFiles;
	}

	public static void main(String[] args) {

		// 带压缩文件集合
		ArrayList<String> srcFiles = new ArrayList<String>();
		srcFiles.add("/Users/vinlam/tmp/7.jar.b");

		String destFile = "/Users/vinlam/tmp/test.jar"; // 压缩路径

		String passwd = "123"; // 压缩包密码
		long fileSize = 165536; // 分卷大小（当前为最小值）

		try {
			// 1.压缩
			//System.out.println(zipBySplit(srcFiles, destFile, passwd, fileSize));

			// 2.解压
			String[] extractedFiles = unzipBySplit(destFile, "/Users/vinlam/tmp/unzip", passwd);
			for (int i = 0; i < extractedFiles.length; i++) {
				System.out.println(extractedFiles[i]);
			}

		} catch (ZipException e) {
			e.printStackTrace();
		}
	}

	public static void test(String[] args) {
		zip("/Users/vinlam/tmp/", "/Users/vinlam/upload/tmp/cc.zip", "11");
//        try {
//            File[] files = unzip("d:\\test\\汉字.zip", "aa");
//            for (int i = 0; i < files.length; i++) {
//                System.out.println(files[i]);
//            }
//        } catch (ZipException e) {
//            e.printStackTrace();
//        }
	}
}