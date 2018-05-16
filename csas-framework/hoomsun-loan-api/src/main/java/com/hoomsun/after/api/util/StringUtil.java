package com.hoomsun.after.api.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Title: Description:
 * Date: 2017-12-26 17:28:15
 * Author: jiahaiyong Copyright: Copyright (c)
 */
public class StringUtil {
	/**
	 * 判断字符串不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		boolean isNotEmpty=false;
		if(str!=null && !str.trim().equals("") && !str.trim().equalsIgnoreCase("null")){
			isNotEmpty=true;
		}
		return isNotEmpty;
	}
	/**
	 * 判断字符串为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		return !isNotEmpty(str);
	}
	
	public static String isEmptyValue(String str,String value){
		if(isEmpty(str)){
			return value;
		}
		return str;
	}
	
	public static  List<String> splitArrayBox(Object[] object){
		List<String>list=new ArrayList<String>();
		if(object!=null){
			for (Object ob : object) {
				String value=String.valueOf(ob);
				list.add(value.replace("第", "").replace("日", ""));
			}
		}
		return list;
	}
	
	/**
	 * @author xinyuan.xu@hoomsun.com
	 * @param value
	 * @return
	 * @Description:字符串中提取数字
	 * @param 2018年2月12日
	 */
	public static List<String> getNumberToString(String value){
		List<String>list=new ArrayList<String>();
		String result="";
		if(isNotEmpty(value)){
			value=value.trim();
			for(int i=0;i<value.length();i++){
				if(value.charAt(i)>=48 && value.charAt(i)<=57){
					result+=value.charAt(i);
					list.add(result);
				}
			}
		}
		return list;
	}

	public static BigDecimal numberBig(String value){
		if(!isEmpty(value)){
			BigDecimal bd=new BigDecimal(value);  
			bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP); 
			return bd;
		}
		return null;
	}
	
	public static String getPosType(String index){
		Map<String,String> map=autoCode.map;
		return map.get(index);
	}
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年4月20日 <br>
	 * 描述： 金额相加 
	 * @param value1
	 * @param value2
	 * @param scale 小数点后保留几位
	 * @return
	 */
	public static double amountAdd(double value1,double value2,int scale){
		double result=new BigDecimal(Double.toString(value1))
		.add(new BigDecimal(Double.toString(value2)))
		.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		return result;
	}
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年4月26日 <br>
	 * 描述： 字符串转金额
	 * @param value
	 * @return
	 */
	public static BigDecimal StringToBigDecimal(String value){
		return new BigDecimal(Double.parseDouble(value)).setScale(2,BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年4月23日 <br>
	 * 描述： 数据分页
	 * @param rows
	 * @param core
	 * @return
	 */
	public static int getTotalNumber(int rows,int core) {
		int totalNumber = rows / core;
		int remainNumber = rows % core;
		if (remainNumber != 0) {
			totalNumber = totalNumber + 1;
		}
		return totalNumber;
	}
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年4月23日 <br>
	 * 描述： 数据分页
	 * @param rows
	 * @param core
	 * @return
	 */
	public static int getTotalNumber(int rows) {
		int totalNumber = rows / autoCode.batchNum;
		int remainNumber = rows % autoCode.batchNum;
		if (remainNumber != 0) {
			totalNumber = totalNumber + 1;
		}
		return totalNumber;
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年5月2日 <br>
	 * 描述： 计算催收任务----》客户逾期未提醒天数
	 * @param currentDate
	 * @param nextDate
	 * @return
	 */
	public static String noMindMum(Date currentDate,Date nextDate){
		int different=DateUtils.differentDaysByMillisecond(currentDate, nextDate);
		return String.valueOf(different-1);
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年5月16日 <br>
	 * 描述： 读取配置文件参数---导出文件最大轮询次数
	 * @param key
	 * @return
	 */
	public static Integer initConfigMaxRow(String key){
		 String row=SysConfig.getInstance().getProperty(key);
		 if(row!=null){
			 return Integer.parseInt(row);
		 }
		return 0;
	}
	
	
	
	public static void main(String[] args) {
		String sa=StringUtil.isEmptyValue(String.valueOf("2733"), "0");
		System.out.println(sa);
		System.out.println(new BigDecimal(Double.parseDouble(sa)).setScale(2,BigDecimal.ROUND_HALF_UP));
	}
}
