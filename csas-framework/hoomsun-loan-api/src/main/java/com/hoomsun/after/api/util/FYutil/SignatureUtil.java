package com.hoomsun.after.api.util.FYutil;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;


/**
 * IVR卡密签约加密工具
 * @author Jerry
 * 2014.11.17
 */
public class SignatureUtil {
	
	private static final Logger logger = Logger.getLogger(SignatureUtil.class);
	
	public static boolean validate(Object bean,String key){
		List<String> values = new ArrayList<String>();
		String signature = null;
		for(Method method:bean.getClass().getMethods()){
			try {
				if(!method.getName().startsWith("get") || "getClass".equalsIgnoreCase(method.getName()))
					continue ; 
				Object o = method.invoke(bean, null);
				if(o!=null && StringUtils.isNotEmpty(o.toString())){
					if("getSignature".equalsIgnoreCase(method.getName().toLowerCase())){
						signature = o.toString();
					}else{
						values.add(o.toString());
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		String localSignature = hex(values, key);
		return localSignature.equalsIgnoreCase(signature);
	}
	/**
	 * @author honcyGao 20160809 
	 * @param bean
	 * @param key
	 * @return
	 */
	public static boolean validateRsa(Object bean,String key){
		logger.debug("validateRsa  start ");
		List<String> values = new ArrayList<String>();
		String signature = null;
		for(Method method:bean.getClass().getMethods()){
			try {
				if(!method.getName().startsWith("get") || "getClass".equalsIgnoreCase(method.getName()))
					continue ; 
				Object o = method.invoke(bean, null);
				if(o!=null && StringUtils.isNotEmpty(o.toString())){
					if("getSignature".equalsIgnoreCase(method.getName().toLowerCase())){
						signature = o.toString();
					}else{
						values.add(o.toString());
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		String[] strs = new String[values.size()];
		for(int i=0;i<strs.length;i++){
			strs[i] = values.get(i);
		}
		Arrays.sort(strs);
		StringBuffer source = new StringBuffer();
		for(String str:strs){
			source.append(str).append("|");
		}
		String bigstr = source.substring(0,source.length()-1);
		logger.debug("RSAbigstr:"+bigstr);
		try {
			return RSAUtils.verify(bigstr.getBytes(), key, signature);
		} catch (Exception e) {
			logger.debug("RSAbigstr验签出错");
			e.printStackTrace();
		}
		return false;
	}
	
	public static String hex(List<String> values,String key){
		String[] strs = new String[values.size()];
		for(int i=0;i<strs.length;i++){
			strs[i] = values.get(i);
		}
		Arrays.sort(strs);
		StringBuffer source = new StringBuffer();
		for(String str:strs){
			source.append(str).append("|");
		}
		String bigstr = source.substring(0,source.length()-1);
		logger.debug("bigstr:"+bigstr);
		System.out.println(bigstr);
		System.out.println(DigestUtils.shaHex(bigstr));
		String result = DigestUtils.shaHex(DigestUtils.shaHex(bigstr)+"|"+key);
		logger.debug("bigstr hex result:"+result);
		return result;
	}

	
	public static void main(String[] args) {
//		IvrContractReqBean bean = new IvrContractReqBean();
//		bean.setReqInsCd("123");gen
//		bean.setAcntNo("a1");
//		bean.setSignature("b9c6188854e9e877cfa7632dea7e94806919af3c");
//		System.out.println(validate(bean, "123456"));
		String x = DigestUtils.shaHex("0|0002900F0345178|01|0103|13852816377|320321199008231211|6228480031697372923|AC01|DSF|似曾相识|备注");
		System.out.println(x);
		System.out.println(DigestUtils.shaHex(x+"|123456"));
	/*	String s1=DigestUtils.shaHex(x+"|123456");
		String s="69fe256c4fb8e83e49d27045abc9ec0cbfd7e05b";
		System.out.println(s.equals(s1));*/
		
		/*ArrayList<String> list=new ArrayList<String>();
		list.add("000035175811");
		list.add("0002900F0345142");
		list.add("1.0");
		list.add("20170301");
		list.add("20170314");
		
		String s=hex(list,"123456");
		System.out.println(s);*/
	}
}

