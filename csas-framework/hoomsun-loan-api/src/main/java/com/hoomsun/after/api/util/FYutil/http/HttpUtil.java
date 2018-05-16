/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.util.FYutil.http;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.hoomsun.after.api.util.BFutil.JXMConvertUtil;
import com.hoomsun.after.api.util.FYutil.XmlUtil;

/**
 * 作者：zwLiu <br>
 * 创建时间：2018年4月26日 <br>
 * 描述：
 */
public class HttpUtil {
	
	private static final String ENCODEING="UTF-8";
	/**
	 * 
	 * 作者：zwLiu <br>
	 * 创建时间：2018年4月23日 <br>
	 * 描述：发起请求 
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, Object> requestPost(String url,List<NameValuePair> params) throws ClientProtocolException, IOException, DocumentException {
	    CloseableHttpClient httpclient = HttpClientBuilder.create().build();

	    HttpPost httppost = new HttpPost(url);
	        httppost.setEntity(new UrlEncodedFormEntity(params,ENCODEING));
	         
	        CloseableHttpResponse response = httpclient.execute(httppost);
	        System.out.println(response.toString());
	         
	        HttpEntity entity = response.getEntity();
	        String jsonStr = EntityUtils.toString(entity, "utf-8");
	        System.out.println(jsonStr);
	        Map<String, Object> resultMap = XmlUtil.XmltoMap(DocumentHelper.parseText(jsonStr));
	        httppost.releaseConnection();
	        return resultMap;
	}
}
