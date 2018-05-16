/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 作者：zwLiu <br>
 * 创建时间：2018年3月15日 <br>
 * 描述：划扣结果
 */
public class DeductResult {
	/**
	 * 划扣状态(0000  成功，1111  失败, 2222 查证)
	 */
	private String deductStatus;
	/**
	 * 订单号
	 */
	private String transId;
	/**
	 * 划扣时间
	 */
	private Date deductDate;
	/**
	 * 返回码
	 */
	private String respCode;
	/**
	 * 返回信息
	 */
	private String respMsg;

	/**
	 * 划扣金额
	 */
	private BigDecimal deductMoney;
	
	/**
	 * 划扣类型
	 */
	private String additionalInfo;
	/**
	 * 划扣手续费
	 */
	private BigDecimal feesMoney; 
	
	/**
	 * 富友查证返回订单状态
	 */
	private String fyCheck;
	
	public String getDeductStatus() {
		return deductStatus;
	}

	public void setDeductStatus(String deductStatus) {
		this.deductStatus = deductStatus;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public Date getDeductDate() {
		return deductDate;
	}

	public void setDeductDate(Date deductDate) {
		this.deductDate = deductDate;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public BigDecimal getDeductMoney() {
		return deductMoney;
	}

	public void setDeductMoney(BigDecimal deductMoney) {
		this.deductMoney = deductMoney;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	@Override
	public String toString() {
		return "BFdeductResult [deductStatus=" + deductStatus + ", transId=" + transId + ", deductDate=" + deductDate + ", respCode=" + respCode + ", respMsg=" + respMsg + ", deductMoney=" + deductMoney + ", additionalInfo="
				+ additionalInfo + "]";
	}

	public BigDecimal getFeesMoney() {
		return feesMoney;
	}

	public void setFeesMoney(BigDecimal feesMoney) {
		this.feesMoney = feesMoney;
	}

	public String getFyCheck() {
		return fyCheck;
	}

	public void setFyCheck(String fyCheck) {
		this.fyCheck = fyCheck;
	}


	

	
	
	
}
