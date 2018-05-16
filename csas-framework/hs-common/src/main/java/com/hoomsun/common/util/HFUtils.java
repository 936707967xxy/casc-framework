package com.hoomsun.common.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.ynet.payment.hfbank.PayCfg;
import com.ynet.payment.hfbank.SignAndVerify;

public class HFUtils {

	// 签名
	public static String signway(Map<String, String> map) throws Exception {
		Iterator<String> iterator = map.keySet().iterator();
		StringBuffer sbf = new StringBuffer();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			sbf.append((String) map.get(key));// 拼接获得的值
		}
		// 签名
		String merchantprivatekey = PayCfg.getValue("cs0213merchantprivatekey");
		String signdata = SignAndVerify.sign_md(sbf.toString(), "", merchantprivatekey);
		return signdata;
	}

	// 验签
	public static String checksign(String eamssign, Map<String, String> map) throws Exception {
		StringBuffer sbf1 = new StringBuffer();
		Iterator<String> iterator_2 = map.keySet().iterator();
		while (iterator_2.hasNext()) {
			Object key = iterator_2.next();
			// 并将获得的值进行拼接
			sbf1.append((String) map.get(key));
		}
		// 比较加密后的验签字段eamssign和传入的验签字段的值sbf.toString()
		String hfbankpublickey = PayCfg.getValue("cs0213hfbankpublickey");
		String status = SignAndVerify.verify_md(sbf1.toString(), eamssign, "", hfbankpublickey);
		return status;
	}

	// url拼接
	public static String urlway(String tranCode, Map<String, String> map) {
		StringBuffer urlBf = new StringBuffer();

		urlBf.append("https://hfpay-dev.yiguanjinrong.com/HFPay/" + tranCode + ".do?");
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key;
			String value;
			key = it.next().toString();
			value = (String) map.get(key);
			urlBf.append(key);
			urlBf.append("=");
			urlBf.append(value);
			urlBf.append("&");
		}
		String url = urlBf.toString().substring(0, urlBf.toString().length() - 1);
		return url;
	}

	/**
	 * get 请求 获取四要素返回码
	 */

	public static String check(String urlAdress) throws IOException {
		/*
		 * String url =
		 * "http://tianxingshuke.com/api/rest/common/organization/auth"
		 */; // 服务器 urlAdress
		String url = urlAdress;
		GetMethod postMethod = new GetMethod(url);
		String str = "";
		// 将表单的值放入postMethod中 commons-httpclient.jar
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
		// HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
		// 301或者302
		if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
			// 从头中取出转向的地址
			Header locationHeader = postMethod.getResponseHeader("location");
			String location = null;
			if (locationHeader != null) {
				location = locationHeader.getValue();
				System.out.println("diandianLogin:" + location);
			} else {
				System.err.println("Location field value is null.");
			}
			return "";
		} else {
			System.out.println(postMethod.getStatusLine());
			try {
				str = postMethod.getResponseBodyAsString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		postMethod.releaseConnection();
		return str;
	}

}
