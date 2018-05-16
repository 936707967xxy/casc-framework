/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月8日 <br>
 * 描述：时间处理工具类
 */
public class DateUtil {
	public static String ymdhms = "yyyy-MM-dd HH:mm:ss";
	public static String ymd = "yyyy-MM-dd";
	public static String year = "yyyy";
	public static String month = "MM";
	public static String day = "dd";

	public static SimpleDateFormat ymdSDF = new SimpleDateFormat(ymd);
	public static SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat(ymdhms);
	public static SimpleDateFormat yearSDF = new SimpleDateFormat(year);
	public static SimpleDateFormat monthSDF = new SimpleDateFormat(month);
	public static SimpleDateFormat daySDF = new SimpleDateFormat(day);
	public static SimpleDateFormat yyyyMMddHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat yyyyMMddHH_NOT_ = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月8日 <br>
	 * 描述： 返回当前时间字符串 格式为：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		Date date = new Date();
		return yyyyMMddHHmmss.format(date);
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月22日 <br>
	 * 描述： 字符串时间转Date
	 * 
	 * @param format
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date toDate(SimpleDateFormat format, String date) throws ParseException {
		return format.parse(date);
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年10月11日 <br>
	 * 描述： 获取今天零时
	 * 
	 * @return
	 */
	public static Date getToadayZero() {
		return getZeroHour(new Date());
	}
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年10月11日 <br>
	 * 描述： 获取今天的24时
	 * @return
	 */
	public static Date getToadayTwentyFour() {
		return getTwentyFour(new Date());
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年10月11日 <br>
	 * 描述： 获取某一天的零时
	 * 
	 * @param date
	 * @return
	 */
	public static Date getZeroHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date zero = calendar.getTime();
		return zero;
	}
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年10月11日 <br>
	 * 描述： 获取某一天的24时
	 * @param date
	 * @return
	 */
	public static Date getTwentyFour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 24);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date tf = calendar.getTime();
		return tf;
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月22日 <br>
	 * 描述： sql时间转java时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date sqlDateTo(java.sql.Date date) {
		return new Date(date.getTime());
	}
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年10月11日 <br>
	 * 描述： java时间类型转Sql(Date)时间类型
	 * @param date
	 * @return
	 */
	public static java.sql.Date javaDataToSql(Date date){
		return new java.sql.Date(date.getTime());
	}
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年10月11日 <br>
	 * 描述： java时间类型转Sql(Timestamp)时间类型
	 * @param date
	 * @return
	 */
	public static Timestamp javaDataToTimestamp(Date date){
		return new Timestamp(date.getTime());
	}
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月22日 <br>
	 * 描述： 获取sql时间类型
	 * 
	 * @return
	 */
	public static Timestamp getTimestamp() {
		Date date = new Date();
		return new Timestamp(date.getTime());
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月22日 <br>
	 * 描述： 获取Sql时间类型
	 * 
	 * @return
	 */
	public static java.sql.Date getSqlDate() {
		Date date = new Date();
		return new java.sql.Date(date.getTime());
	}

	public static void main(String[] args) {
		System.out.println(yyyyMMddHHmmss.format(getToadayZero()));
		System.out.println(yyyyMMddHHmmss.format(getToadayTwentyFour()));
	}
}
