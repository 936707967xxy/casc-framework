/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.enums;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月5日 <br>
 * 描述：系统参数key定义
 */
public enum SystemParamEnum {
	/**
	 * 是否推送线上 0：false 1:true 默认0
	 */
	PUSH_ONLINE("PUSH_ONLINE"),
	/**
	 * 拒贷封闭期（天） 默认30天
	 */
	REJECT_LIMIT_DAY("REJECT_LIMIT_DAY"),
	/**
	 * 咨询保护期 （天）  默认7天
	 */
	ADVISORY_PROTECTION_DAY("ADVISORY_PROTECTION_DAY"),
	/**
	 * 签约天数限制N天内不签约 作废处理  默认30天
	 */
	SIGNING_LIMIT_DAY("SIGNING_LIMIT_DAY"),
	/**
	 * 允许签收的任务数
	 */
	CLAIM_TASK_COUNT("CLAIM_TASK_COUNT")
	;
	
	private String key;

	private SystemParamEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
