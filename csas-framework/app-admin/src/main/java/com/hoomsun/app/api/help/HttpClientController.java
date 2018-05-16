package com.hoomsun.app.api.help;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author liudongliang
 * @date 2017-08-15
 * @resource 此类是 调用第三方的方法
 *
 */

public class HttpClientController {
	private static Logger log = LoggerFactory.getLogger(HttpClientController.class);
	/**
	 * get 获取返回值
	 * 
	 * @param urlAdress
	 * @return
	 * @throws IOException
	 */
	public static String check(String urlAdress) throws IOException {
		String url = urlAdress;
		GetMethod postMethod = new GetMethod(url);

		String str = "";
		// 将表单的值放入postMethod�?commons-httpclient.jar
		// postMethod.setRequestBody(data);
		postMethod.getParams().setContentCharset("utf-8");
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		// 执行postMethod
		int statusCode = 0;
		try {
			statusCode = new HttpClient().executeMethod(postMethod);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转
		// 301或302
		if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
			// 从头中取出转向的地址
			Header locationHeader = postMethod.getResponseHeader("location");
			String location = null;
			if (locationHeader != null) {
				location = locationHeader.getValue();
				log.info("diandianLogin:" + location);
			} else {
				log.error("Location field value is null.");
			}
			return "";
		} else {
			log.info(postMethod.getStatusLine().toString());

			try {
				str = postMethod.getResponseBodyAsString();
			} catch (IOException e) {
				e.printStackTrace();
			}
			log.info(str);
		}
		postMethod.releaseConnection();
		return str;
	}

	/**
	 * POST 获取返回值
	 * 
	 * @param urlAdress
	 * @return
	 * @throws IOException
	 */
	public static String postcheck(String urlAdress, NameValuePair[] paramdata) throws IOException {
		String url = urlAdress;
		PostMethod postMethod = new PostMethod(url);
		// 填入各个表单域的值
		NameValuePair[] data = paramdata;
		String str = "";
		// 将表单的值放入postMethod�?commons-httpclient.jar
		postMethod.setRequestBody(data);
		postMethod.getParams().setContentCharset("utf-8");
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		// 执行postMethod
		int statusCode = 0;
		try {
			statusCode = new HttpClient().executeMethod(postMethod);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转�?
		// 301或�?302
		if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
			// 从头中取出转向的地址
			Header locationHeader = postMethod.getResponseHeader("location");
			String location = null;
			if (locationHeader != null) {
				location = locationHeader.getValue();
				log.info("diandianLogin:" + location);
			} else {
				log.error("Location field value is null.");
			}
			return "";
		} else {
			log.info(postMethod.getStatusLine().toString());

			try {
				str = postMethod.getResponseBodyAsString();
			} catch (IOException e) {
				e.printStackTrace();
			}
			log.info(str);
		}
		postMethod.releaseConnection();

		return str;
	}
}
