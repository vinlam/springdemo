package com;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class CompareTime {
	private String field = null;
	public static void main(String[] args) {
		String t1 = "1990-1-1";
		String t2 = "2000-1-1";
		try {
			System.out.println(CompareTime.getBetweenDays(t1, t2));
			System.out.println(getQuot(t1, t2));
			res(t1, t2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static int getBetweenDays(String t1, String t2)
			throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int betweenDays = 0;
		Date d1 = format.parse(t1);
		Date d2 = format.parse(t2);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		// 保证第二个时间一定大于第一个时间
		if (c1.after(c2)) {
			c1 = c2;
			c2.setTime(d1);
		}
		int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		betweenDays = c2.get(Calendar.DAY_OF_YEAR)
				- c1.get(Calendar.DAY_OF_YEAR);
		System.out.println("out:" + c2.get(Calendar.YEAR) + "-"
				+ c1.get(Calendar.YEAR) + "\nday:"
				+ c2.get(Calendar.DAY_OF_YEAR) + "-"
				+ c1.get(Calendar.DAY_OF_YEAR));
		for (int i = 0; i < betweenYears; i++) {
			c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
			betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
		}
		return betweenDays;
	}

	public static long getQuot(String startdate, String enddate) {
		long m_intervalday = 0;// 初始化时间间隔的值为0
		// 使用的时间格式为yyyy-MM-dd
		SimpleDateFormat m_simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// 创建两个Date对象
			Date date1 = m_simpledateformat.parse(startdate);
			Date date2 = m_simpledateformat.parse(enddate);
			m_intervalday = date2.getTime() - date1.getTime();// 计算所得为微秒数
			m_intervalday = m_intervalday / 1000 / 60 / 60 / 24;// 计算所得的天数

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d1 = sdf.parse("2012-09-08 10:10:10");
			Date d2 = sdf.parse("2012-09-15 00:00:00");
			System.out.println(daysBetween(d1, d2));

			System.out.println(daysBetween("1990-01-01","2000-01-01"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return m_intervalday;
	}

	private static void res(String str1, String str2) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date date1 = sdf.parse(str1);

		Date date2 = sdf.parse(str2);

		GregorianCalendar cal1 = new GregorianCalendar();

		GregorianCalendar cal2 = new GregorianCalendar();

		cal1.setTime(date1);

		cal2.setTime(date2);

		long gap = (cal2.getTimeInMillis() - cal1.getTimeInMillis())
				/ (1000 * 3600 * 24);// 从间隔毫秒变成间隔天数</strong>

		System.out.println("\n相差" + gap + "天");
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 字符串的日期格式的计算
	 */
	public static int daysBetween(String smdate, String bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

}
