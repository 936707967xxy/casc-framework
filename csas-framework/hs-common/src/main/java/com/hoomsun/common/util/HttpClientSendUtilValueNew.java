package com.hoomsun.common.util;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author wangpeng
 * @date 2018-01-06
 * @resource 重新定义数据推送
 *
 */
public class HttpClientSendUtilValueNew {
	
	private static final Logger LOG = LoggerFactory.getLogger(HttpClientSendUtilValueNew.class);
	@SuppressWarnings("rawtypes")
	public static String sendService(String url,Map parameter) {
		String str = "";
		PostMethod postMethod = new PostMethod(url);
		// 填入各个表单域的值
		NameValuePair[] data = null;
		if ((parameter != null) && (!parameter.isEmpty())) {
			data = new NameValuePair[parameter.size()];
			Iterator iter = parameter.keySet().iterator();
	        int count = 0;
	        while (iter.hasNext()) {
	        	String key = checkString(iter.next());
	        	data[count] = new NameValuePair(key,checkString(parameter.get(key)));
	        	count++;
	        }
	        postMethod.setRequestBody(data);
		}
		LOG.info("推送成功");
		// 将表单的值放入postMethod中 commons-httpclient.jar  
		postMethod.setRequestBody(data);
		postMethod.getParams().setContentCharset("utf-8");
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
		// 读取超时
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,30000);
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
		// 执行postMethod
		int statusCode = 0;
		try {
			statusCode = new HttpClient().executeMethod(postMethod);
		} catch (ConnectException e) {
			LOG.error("链接异常");
		} catch (ConnectTimeoutException e) {
			LOG.error("链接超时");
		} catch (SocketTimeoutException e) { 
			LOG.error("响应超时");
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
		// 301或者302
		if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
				|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
			// 从头中取出转向的地址
			Header locationHeader = postMethod.getResponseHeader("location");
			String location = null;
			if (locationHeader != null) {
				location = locationHeader.getValue();
				LOG.info("diandianLogin:" + location);
			} else {
				LOG.info("Location field value is null.");
			}
			return str;
		} else {
			System.out.println(postMethod.getStatusLine());
			try {
				str = postMethod.getResponseBodyAsString();
			} catch (IOException e) {
				e.printStackTrace();
			}
			LOG.info(str);
		}
		postMethod.releaseConnection();
		return str;
	}
	
	public static String checkString(Object str) {
		if (str == null) {
			return "";
		}

		String temp = "";
		try {
			temp = str.toString();
		} catch (Exception localException) {
		}
		return temp;
	}
}
