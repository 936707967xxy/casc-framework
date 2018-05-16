/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.util;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月27日 <br>
 * 描述：还款方式类型枚举
 */
public enum RepayType {
	/**
	 * 等额本息 1
	 */
	AVERAGE_CAPITAL_PLUS_INTEREST(1, "等额本息"),
	/** 
	 * 等额本金 2
	 */
	AVERAGE_CAPITAL(2, "等额本金"),
	/**
	 * 到期一次性结清 3
	 */
	EXPIRATION_ONETIME_SETTLEMENT(3, "到期一次性结清"),
	/**
	 * 按期预付利息到期还本 4
	 */
	INTEREST_ON_SCHEDULE_PREPAYMENTS(4, "按期预付利息到期还本");

	private Integer type;
	private String name;

	private RepayType(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
