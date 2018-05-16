/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月19日 <br>
 * 描述：全局日期处理类
 */
public class DateConvert implements Converter<String, Date> {

	@Override
	public Date convert(String strDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return simpleDateFormat.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
