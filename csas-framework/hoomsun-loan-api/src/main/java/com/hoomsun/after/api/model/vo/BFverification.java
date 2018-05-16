/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 作者：zwLiu <br>
 * 创建时间：2018年3月19日 <br>
 * 描述：宝付查证返回数据
 */
public class BFverification {


	/**
	 * 订单交易日期
	 */
	private Date origTradeDate;
	/**
	 * 交易预留信息
	 */
	private String reqReserved;
	/**
	 * 原始订单号
	 */
	private String origTransId;
	/**
	 * 查证状态 S：交易成功 F：交易失败 I：处理中 FF：交易失败；订单暂不存 在
	 */
	private String orderStat;
	/**
	 * 状态码
	 * eg:BF00111
	 */
	private String respCode;
	
	/**
	 * 查证信息
	 * eg:交易失败
	 */
	private String respMsg;
	/**
	 * 标识 
	 * eg: 查证
	 */
	private String additionalInfo;
	
	/**
	 * 交易金额
	 */
	private BigDecimal succAmt;
	/**
	 * 查证时间
	 */
	private Date checkDate;
	
	public Date getOrigTradeDate() {
		return origTradeDate;
	}
	public void setOrigTradeDate(Date origTradeDate) {
		this.origTradeDate = origTradeDate;
	}
	public String getReqReserved() {
		return reqReserved;
	}
	public void setReqReserved(String reqReserved) {
		this.reqReserved = reqReserved;
	}

	public String getOrderStat() {
		return orderStat;
	}
	public void setOrderStat(String orderStat) {
		this.orderStat = orderStat;
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
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public BigDecimal getSuccAmt() {
		return succAmt;
	}
	public void setSuccAmt(BigDecimal succAmt) {
		this.succAmt = succAmt;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public String getOrigTransId() {
		return origTransId;
	}
	public void setOrigTransId(String origTransId) {
		this.origTransId = origTransId;
	}

}
