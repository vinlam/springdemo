package com.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ImageCode {
	// 验证码字符集
	private static final char[] chars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z' };
	// 字符数量
	private static final int size = 4;
	// 干扰线数量
	private static final int lines = 5;
	// 宽度
	private static int width = 110;
	// 高度
	private static int height = 50;
	// 字体大小
	private static final int font_size = 30;

	public static Map<String, Object> createCode(int imgWidth,int imgHeight) {
		width = imgWidth<=0?width:imgWidth;
		height = imgHeight<=0?height:imgHeight;
		Map<String,Object> resMap = new HashMap<String, Object>();
		// 1、创建缓存图片 参数3：图片类型：RGB
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		// 2、创建画笔,绘制图形
		Graphics graphics = image.getGraphics();
		// 3、设置背景颜色
		graphics.setColor(Color.LIGHT_GRAY);// 颜色
		// 4、绘制背景（此处占用了缓存图片的全部内容）
		graphics.fillRect(0, 0, width, height);
		// 5、画随机字符
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size; i++) {
			// 取随机字符索引
			int n = random.nextInt(chars.length);
			// 设置随机颜色
			graphics.setColor(getRandomColor());
			// 设置随机字体和相关样式
			String font_family = getRandomFont();
			graphics.setFont(new Font(font_family, Font.BOLD, font_size));
			// 画字符
			graphics.drawString(chars[n] + "", i * width / size, height * 2 / 3);
			// 记录字符
			sb.append(chars[n]);
		}

		// 把字符存储到session中，便与登录时验证
		// req.getSession().setAttribute("code", sb.toString());
		// 6、设置干扰线
		for (int i = 0; i < lines; i++) {
			// 设置随机颜色
			graphics.setColor(getRandomColor());
			// 随机画线
			graphics.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width),random.nextInt(height));
		}
		
		resMap.put("code", sb.toString());
		resMap.put("image", image);
		
		return resMap;
		
	}

	/**
	 * 随机取色
	 */
	public static Color getRandomColor() {
		Random ran = new Random();
		Color color = new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
		return color;
	}

	/**
	 * 随机字体
	 */
	public static String getRandomFont() {
		String[] fonts = new String[] { "微软雅黑", "宋体", "Serif", "Cursive", "Fantasy" };
		int i = (int) (Math.random() * (fonts.length));
		String font = fonts[i];
		return font;
	}
}
