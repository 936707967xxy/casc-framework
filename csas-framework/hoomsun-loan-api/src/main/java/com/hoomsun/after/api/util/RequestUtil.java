package com.hoomsun.after.api.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSON;
/**
* <p>Title: RequestUtil</p>  
* <p>Description:HttpServletRequest请求辅助类 </p>  
* @author xinyuan.xu@hoomsun.com  
* @date 2018年3月13日
 */
public class RequestUtil {
	
	private static final Logger LogCvt = Logger.getLogger(RequestUtil.class);

	/**
	 * 将request中的所有参数设置到entityClass类型的对象上
	 * 
	 * @param entityClass
	 * @param request
	 * @return
	 */
	public static Object copyParam(Class<?> entityClass, HttpServletRequest request) {
		try {
			Object entity = entityClass.newInstance();

			request.setCharacterEncoding("UTF-8");
			// 把请求中的参数取出
			Map<?, ?> allParams = request.getParameterMap();

			if (EmptyChecker.isNotEmpty(allParams)) {
				LogCvt.info("请求参数(Key-Value)为：" + allParams.toString());
				Set<?> entries = allParams.entrySet();
				for (Iterator<?> iterator = entries.iterator(); iterator.hasNext();) {
					Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iterator.next();
					String name = (String) entry.getKey();
					String[] value = (String[]) entry.getValue();

					if (value != null) {
						if (value.length == 1) {
							BeanUtils.copyProperty(entity, name, value[0]);
						} else {
							BeanUtils.copyProperty(entity, name, value);
						}
					}
				}
			} else {
				// 以key-value形式读取参数失败，改用json
				StringBuffer jb = new StringBuffer();
				String line = null;
				BufferedReader reader = request.getReader();
				while ((line = reader.readLine()) != null) {
					jb.append(line);
				}

				if(jb.toString().contains("soapenv:Envelope")){
					//用xml解析
					LogCvt.info("请求参数(xml)为：" + jb.toString());
					String reqXml = jb.toString();
					Map<String,Object> map = new HashMap<String, Object>();
					
					//保存报文所有信息,除了bizBody
					String respXml = reqXml.substring(0,reqXml.indexOf("<bizBody>")+9);
					respXml += reqXml.substring(reqXml.indexOf("</bizBody>"),reqXml.length());
					map.put("sopRespXml", respXml);
					
					//截取参数
					reqXml = reqXml.substring(reqXml.indexOf("<soapenv:Body>"),reqXml.indexOf("</soapenv:Body>")+15);    	
					String reqHeaderXml = reqXml.substring(reqXml.indexOf("<bizHeader>"),reqXml.indexOf("</bizHeader>")+12);
					String reqBodyXml = reqXml.substring(reqXml.indexOf("<bizBody>"),reqXml.indexOf("</bizBody>")+10);
					//存放body集合
					List<String> listXml = new ArrayList<String>();
					listXml = getXmlList(reqBodyXml,"list");
					reqBodyXml = listXml.get(0);
					if(listXml.size()>1){
						List<Object> listObj = new ArrayList<Object>();
						for(int i=1;i<listXml.size();i++){
							Map<String,Object> mapList = XmlUtil.getMapFromXML(listXml.get(i));
							listObj.add(mapList);
						}
						map.put("list", listObj);
					}
					
					Map<String,Object>  headerMap  = XmlUtil.getMapFromXML(reqHeaderXml);
			    	Map<String,Object>  bodyMap  = XmlUtil.getMapFromXML(reqBodyXml);
			    	
			    	map.putAll(headerMap);
			    	map.putAll(bodyMap);
			    	//转成json,再转成javabean
			    	String jsonXml = JSON.toJSONString(map);
			    	LogCvt.info("json="+jsonXml);
			    	entity = JSON.parseObject(jsonXml, entityClass);
			    	
				}else{
					//用json解析
					LogCvt.info("请求参数(JSON)为：" + jb.toString());
					entity = JSON.parseObject(jb.toString(), entityClass);
				}
			}

			return entity;
		} catch (Exception e) {
			LogCvt.error("请求参数封装失败", e);
		}

		return null;
	}
	
	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
		
		String reqXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header><in:sysHeader xmlns:in=\"http://www.ahrcu.com/common/header/in\"><msgId>20170919180016ESBB0CCD2BF62D45AFESB0</msgId><msgDate>2017-09-19</msgDate><msgTime>18:00:16.509</msgTime><servCd>P00002005974</servCd><operation>AddCount</operation><sysCd>094</sysCd><serverCd>099</serverCd><bizId>EPAY005</bizId><bizType/><orgCd/><resCd/><resText/><bizResCd/><bizResText/><ver>100100100</ver><authId/><authPara/><authContext/><pinIndex/><pinValue/></in:sysHeader></soapenv:Header><soapenv:Body><r09:request xmlns:r09=\"http://www.ahrcu.com/service/bd/R09902005973\"><bizHeader><authCode/><channel>00</channel></bizHeader><bizBody><subMerchantId>94734081520001C</subMerchantId><list><accountName>test</accountName><accountNo>6217788302400000057</accountNo><openningBank>枞阳农村商业银行营业部</openningBank><outletId>0</outletId></list><list><accountName>test</accountName><accountNo>6217788302400000057</accountNo><openningBank>枞阳农村商业银行营业部1</openningBank><outletId>0</outletId></list><yyyy>94734081520001C</yyyy></bizBody></r09:request></soapenv:Body></soapenv:Envelope>";
		Map<String,Object> map = new HashMap<String, Object>();
		
		//保存报文所有信息,除了bizBody
		String respXml = reqXml.substring(0,reqXml.indexOf("<bizBody>")+9);
		respXml += reqXml.substring(reqXml.indexOf("</bizBody>"),reqXml.length());
		map.put("sopRespXml", respXml);
		
		//截取参数
		reqXml = reqXml.substring(reqXml.indexOf("<soapenv:Body>"),reqXml.indexOf("</soapenv:Body>")+15);    	
		String reqHeaderXml = reqXml.substring(reqXml.indexOf("<bizHeader>"),reqXml.indexOf("</bizHeader>")+12);
		String reqBodyXml = reqXml.substring(reqXml.indexOf("<bizBody>"),reqXml.indexOf("</bizBody>")+10);
		//存放body集合
		List<String> listXml = new ArrayList<String>();
		listXml = getXmlList(reqBodyXml,"list");
		reqBodyXml = listXml.get(0);
		if(listXml.size()>1){
			List<Object> listObj = new ArrayList<Object>();
			for(int i=1;i<listXml.size();i++){
				Map<String,Object> mapList = XmlUtil.getMapFromXML(listXml.get(i));
				listObj.add(mapList);
			}
			map.put("list", listObj);
		}
		
//		System.out.println("==="+JSON.toJSONString(listXml));
//		System.out.println("reqBodyXml="+reqBodyXml);
		
		
		Map<String,Object>  headerMap  = XmlUtil.getMapFromXML(reqHeaderXml);
    	Map<String,Object>  bodyMap  = XmlUtil.getMapFromXML(reqBodyXml);
    	
    	map.putAll(headerMap);
    	map.putAll(bodyMap);
    	//转成json,再转成javabean
    	String jsonXml = JSON.toJSONString(map);
    	System.out.println(jsonXml);
//    	entity = JSON.parseObject(jsonXml, entityClass);
		
	}
	
	/**
	 * 返回集合数据已经去除集合xml剩余数据
	 * @param reqBodyXml
	 * @param listkey 集合的节点名
	 * @return
	 */
	public static List<String> getXmlList(String reqBodyXml,String listkey){
		
		List<String> list  = new ArrayList<String>();
		if(reqBodyXml.indexOf("<"+listkey+">")>=0){
			String listStr = reqBodyXml;
			do{
				
				String listInfoStr = listStr.substring(listStr.indexOf("<"+listkey+">"),listStr.indexOf("</"+listkey+">")+7);
				list.add(listInfoStr);
				listStr = listStr.substring(listStr.indexOf("</"+listkey+">")+7,listStr.length());//获取第一个集合之后的集合xml
				
				String badyXml1 = reqBodyXml.substring(0, reqBodyXml.indexOf("<"+listkey+">"));
				String badyXml2 = reqBodyXml.substring(reqBodyXml.indexOf("</"+listkey+">")+7, reqBodyXml.length());
				
				reqBodyXml = badyXml1+badyXml2;
				
			}while(listStr.indexOf("<"+listkey+">")>=0);
		}
		List<String> lists  = new ArrayList<String>();
		
		lists.add(reqBodyXml);
		if(list.size()>0){
			lists.addAll(list);
		}
		
		return lists;
	}
	
}
