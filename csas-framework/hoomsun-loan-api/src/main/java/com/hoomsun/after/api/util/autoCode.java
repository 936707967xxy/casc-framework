package com.hoomsun.after.api.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量
 * @author Administrator
 *
 */
public class autoCode {

	public static final String ERROR_NO_FOUND="E400";//未找到对应客户信息
	public static final String ERROR_NO_FUNCTION="E500";//该客户在账单日到达之前无法使用该功能
	public static final String ERROR_NO_SYSTEM="E600";//处理异常客户信息
	/**
	 * 此用户数据为非法数据，不允许退回
	 */
	public static final String ERROR_OVERDUE_EXIT="E700";
	/**
	 * 批量月还异常信息
	 */
	public static final String ERROR_OVERDUE_FUY_EXIT="E800";
	
	public static final int batchNum = 2;
	public static final String EXCEL_VERSION_XLS = "version2003";
	public static final String EXCEL_VERSION_XLSX = "version2007";
	

	/**
	 * 富友月还
	 */
	public static final String PAY_CHANNEL_FY="HS_FY";
	/**
	 * 存公月还
	 */
	public static final String PAY_CHANNEL_CG="HS_CG";
	
	/***********新放款客户************************************************************************/
	/**
	 * 新放款客户VALUE
	 */
	@Deprecated
	public static final String CUSTOMER_NEW_CREATE = "新放款客户";
	/**
	 * 新放款客户KEY
	 */
	@Deprecated
	public static final String CUSTOMER_NEW_CREATE_VALUE = "0";
	/***********新放款客户************************************************************************/
	
	public static final Map<String,String>map=new HashMap<String,String>();//客户类型
	
	public static final String NOMAL_REPAYMENT = "0";//正常月还
	public static final String OVERDUE_REPAYMENT = "1";//逾期月还
	public static final String ADVANCE_REPAYMENT = "2";//提前还款
	
	//用户是否是前线标识
	public static final String CUSTOMER_FRONT = "0";
	//用户是否是后援标识
	public static final String CUSTOMER_BACKUP = "1";
	static{
		map.put("0", "旧POS转新POS模式");
		map.put("1", "旧POS划扣模式");
		map.put("2", "线上POS模式");
		map.put("3", "新放款客户");
		map.put("4", "新POS模式");
	}
	
}
