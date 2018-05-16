/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.util.enums;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年4月3日 <br>
 * 描述：
 * <p>1、客户划扣渠道</p>
 * <p>2、划扣状态</p>
 * <p>3、划扣类型</p>
 * 线下宝付，线下富友，线下存公，线上宝付，线上富友，线上存公等
 */
public enum CustomerDeductEnum {

	/**
	 * 划扣渠道
	 */
	DEDUCT_CHANNEL_XXBF("XXBF","线下宝付"),
	DEDUCT_CHANNEL_XXFY("XXFY","线下富友"),
	DEDUCT_CHANNEL_XXCG("XXCG","线下存公"),
	DEDUCT_CHANNEL_XSBF("XSBF","线上宝付"),
	DEDUCT_CHANNEL_XSFY("XSFY","线上富友"),
	DEDUCT_CHANNEL_XSCG("XSCG","线上存公"),
	
	/**
	 * 划扣状态;
	 */
	DEDUCT_STATUS_TRUE("0","成功"),
	DEDUCT_STATUS_FALSE("1","失败"),
	DEDUCT_STATUS_WAIT("2","待定"),
	/**
	 * 划扣类型
	 */
	DEDUCT_TYPE_ZC("1","正常月还"),
	DEDUCT_TYPE_TQ("2","提前还款"),
	DEDUCT_TYPE_YQ("3","逾期月还"),
	DEDUCT_TYPE_YE("4","余额充值");
	
	private String mess ;
    private String code ;
     
    private CustomerDeductEnum( String code,String mess  ){
        this.mess = mess ;
        this.code = code ;
    }
    
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMess() {
		return mess;
	}

	public void setMess(String mess) {
		this.mess = mess;
	}
}
