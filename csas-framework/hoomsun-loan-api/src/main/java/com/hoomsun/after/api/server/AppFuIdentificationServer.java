/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.server;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年5月14日 <br>
 * 描述：App线下客户 富友鉴权
 */
public interface AppFuIdentificationServer {

	/**
	 * 查询是否需要弹出 富有 鉴权绑卡
	 * 
	 * 
	 */
	public Map<String, Object> getIdentification(String custId, HttpServletRequest req);

	/**
	 * 录入项目ID 认证支付 返回验证码,项目ID
	 * 
	 */

	public Map<String, Object> saveProduct(String custId, String loanId, HttpServletRequest req);

	/**
	 * 输入验证码 富有绑卡成功
	 * 
	 * 插入 项目ID 以及 paycode2 paycode3
	 * 
	 */

	public Map<String, Object> saveIdentification(String custId, String loanId, String projectId, String yzm, HttpServletRequest req);

}
