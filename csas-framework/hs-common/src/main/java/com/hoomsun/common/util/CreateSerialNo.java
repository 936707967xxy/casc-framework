package com.hoomsun.common.util;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.digest.DigestVerify;
import com.hoomsun.exception.SignFailException;
import com.hoomsun.util.ScGeneralInfo;


public  class  CreateSerialNo {
	private static final AtomicInteger counter = new AtomicInteger(0);
	
	public static String SerialNo(){
		String SerialNo="";
		try{
			SimpleDateFormat asdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	        String applyTime = asdf.format(new Date());
	        String number=String.format("%3d", counter.incrementAndGet())
		              .replace(' ', '0');
	        SerialNo=applyTime+number;
	        
		}catch(Exception e){
			e.printStackTrace();
		}
		return SerialNo;
        
	}
	/**
	 * sign加密方法工具类
	 * @return
	 */
	public static  Map<Object,String> sign(){
		Map <Object,String> result = new HashMap<Object, String>();
		ScGeneralInfo info = new ScGeneralInfo();
		//生成唯一流水号
	    try {
	    	info.setSerialNo(CreateSerialNo.SerialNo());//生成唯一流水号
	    	result.put("serialNo",info.getSerialNo());
	    	//发起时间（定为当前系统时间）
	    	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	String applyTime = sdfDate.format(new Date());
	    	info.setApplyTime(applyTime);
	    	result.put("applyTime",info.getApplyTime());
	    	 info.setSign(new DigestVerify().sign(info, "UTF-8", "debx-zhixin"));
	    	result.put("sign",info.getSign());
		} catch (SignFailException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}
	/**
	 * 根据请求参数得到相应数据
	 * @param key
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String  getString(String key,HttpServletRequest req) throws UnsupportedEncodingException{
		String value=req.getParameter(key);
		if(value==null){
			value="";
		}
		String obj=new String(value.getBytes("ISO-8859-1"), "UTF-8");
		
		return obj;
	}
	/**
	 * 解密方法
	 * @throws  
	 */
      public static boolean decode(JSONObject generalInfo)  {
    	  boolean verify=false;
    	  try{
    		  ScGeneralInfo info=new ScGeneralInfo();
  		    info.setApplyTime(generalInfo.getString("applyTime"));
  		    info.setSerialNo(generalInfo.getString("serialNo"));
  		    info.setSign(generalInfo.getString("sign"));
  		    verify = new DigestVerify().verify(generalInfo.getString("sign"),info, "UTF-8", "debx-zhixin"); 
    	  }catch(Exception e){
    		 e.printStackTrace(); 
    	  }
    	  
		    return verify;
      }
}
