/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.enums;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月9日 <br>
 * 描述：logger 删除的类型
 */
public enum OptionType {
	/**
	 * 创建
	 */
	CREATE("create"), 
	/**
	 * 查询
	 */
	RETRIEVE("retrieve"), 
	/**
	 * 修改
	 */
	UPDATE("update"), 
	/**
	 * 删除
	 */
	DELETE("delete"), 
	/**
	 * 授权
	 */
	GRANT("grant"), 
	/**
	 * 签收任务
	 */
	CLAIM("claim"), 
	/**
	 * 办理任务
	 */
	COMPLETE("complete"), 
	/**
	 * 退回
	 */
	ROLLBACK("rollback"), 
	/**
	 * 撤回
	 */
	WITHDRAW("withdraw")
	;

	private String type;

	private OptionType() {
	}

	private OptionType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
