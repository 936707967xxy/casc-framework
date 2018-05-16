/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年4月26日 <br>
 * 描述：日期工具类
 */
public class DateUtils {

	public static final String DATE_19 = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_10 = "yyyy-MM-dd";
	public static final String DATE_8 = "yyyyMMdd";
	public static final String TIME_8 = "HH:mm:ss";
	public static final String TIME_6 = "HHmmss";

	/**
	 * @author xinyuan.xu@hoomsun.com
	 * @param date
	 * @param type
	 * @return
	 * @Description:字符串转日期
	 * @param
	 */
	public static Date stringToDate(String date, String type) {
		Date newDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		try {
			newDate = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newDate;
	}

	/**
	 * @author xinyuan.xu@hoomsun.com
	 * @param date
	 * @param type
	 * @return
	 * @Description:日期转字符串
	 * @param
	 */
	public static String dateToString(Date date, String type) {
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		return sdf.format(date);
	}

	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年5月2日 <br>
	 * 描述： 时间段计算相隔天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDaysByMillisecond(Date date1, Date date2) {
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
		return days;
	}

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年5月9日 <br>
	 * 描述：获取几天后的日期
	 * 
	 * @param d
	 * @param days
	 * @return
	 */
	public static Date getNextDate(Date d, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.DAY_OF_MONTH, days);
		return c.getTime();
	}

	public static void main(String[] args) {
		String dateStr = "2018-05-02 10:11:05";
		String dateStr2 = "2018-05-05 11:10:56";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date2 = format.parse(dateStr2);
			Date date = format.parse(dateStr);
			System.out.println(differentDaysByMillisecond(date, date2));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
