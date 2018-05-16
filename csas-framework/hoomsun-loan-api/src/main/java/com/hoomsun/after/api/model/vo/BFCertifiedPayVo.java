/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 作者：zwLiu <br>
 * 创建时间：2018年5月14日 <br>
 * 描述：宝付认证支付返回
 */
public class BFCertifiedPayVo {
	/**
	 * 划扣状态
	 */
	private String deductStatus;
	
	/**
	 * 划扣日期
	 */
	private Date deductDate;

	/**
	 * 流水号
	 */
	private String businessNo;
	
	/**
	 * 订单编号
	 */
	private String orderNo;
	
	/**
	 * 
	 */
	private BigDecimal DeductMoney;
	
	/**
	 * 响应信息
	 */
	private String statusMsg;
	

	/**
	 * 划扣渠道
	 */
	private String additionalInfo;
	
	public String getDeductStatus() {
		return deductStatus;
	}

	public void setDeductStatus(String deductStatus) {
		this.deductStatus = deductStatus;
	}

	public Date getDeductDate() {
		return deductDate;
	}

	public void setDeductDate(Date deductDate) {
		this.deductDate = deductDate;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public BigDecimal getDeductMoney() {
		return DeductMoney;
	}

	public void setDeductMoney(BigDecimal deductMoney) {
		DeductMoney = deductMoney;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	
}
