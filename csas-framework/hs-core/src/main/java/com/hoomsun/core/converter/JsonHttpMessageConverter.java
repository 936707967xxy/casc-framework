/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.converter;

import java.io.IOException;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月26日 <br>
 * 描述：
 */
public class JsonHttpMessageConverter extends FastJsonHttpMessageConverter {

	// QuoteFieldNames———-输出key时是否使用双引号,默认为true
	// WriteMapNullValue——–是否输出值为null的字段,默认为false
	// WriteNullNumberAsZero—-数值字段如果为null,输出为0,而非null
	// WriteNullListAsEmpty—–List字段如果为null,输出为[],而非null
	// WriteNullStringAsEmpty—字符类型字段如果为null,输出为"",而非null
	// WriteNullBooleanAsFalse–Boolean字段如果为null,输出为false,而非null

	@Override
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteNullBooleanAsFalse,SerializerFeature.WriteNullListAsEmpty);
		super.writeInternal(obj, outputMessage);
	}
}
