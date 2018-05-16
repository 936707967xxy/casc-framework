/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;



/**
 * 作者：liming <br>
 * 创建时间：2018年1月2日 <br>
 * 描述：计算预约日期工具类
 */
public class IntentionDate {
		
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年1月2日 <br>
	 * 描述：计算预约日期
	 * @param applydate
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException 
	 */
	public Long ApplyDate(String applydate) throws ParseException{
	
		// 日期转换为毫秒 两个日期想减得到天数
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 得到毫秒数
		long timeStart = System.currentTimeMillis();//系统时间
		long timeEnd = sdf.parse(applydate).getTime();//预约日期
		// 两个日期想减得到天数
		long dayCount = (timeStart - timeEnd) / (24 * 3600 * 1000);
		
		return dayCount;
		
	}
}
