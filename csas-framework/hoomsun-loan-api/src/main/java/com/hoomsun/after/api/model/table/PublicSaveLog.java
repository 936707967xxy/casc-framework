/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.table;

import java.util.Date;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年4月26日 <br>
 * 描述：HS_AFTER_PUBLIC_SAVE_LOG存公导入记录表
 */
public class PublicSaveLog {

	private String id;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 流水号
	 */
	private String refnbr;
	/**
	 * 交易日期
	 */
	private String transactionDate;
	/**
	 * 借贷标记(C:收入，D:支出) 
	 */
	private String amtcdr;
	/**
	 * 公户余额
	 */
	private String trsblv;
	/**
	 * 交易金额
	 */
	private String trsamt;
	/**
	 * 对应公户（0707，7180）
	 */
	private String corporateBankAccount;
	/**
	 * 数据来源（银企直连，结算导入）
	 */
	private String dataSources;
	/**
	 * 失败交易号
	 */
	private String errorCode;
	/**
	 * 失败原因
	 */
	private String errorMsg;
	/**
	 * 操作员id
	 */
	private String oprationId;
	/**
	 * 操作员姓名
	 */
	private String oprationName;
	/**
	 * 操作员IP
	 */
	private String hostIp;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRefnbr() {
		return refnbr;
	}
	public void setRefnbr(String refnbr) {
		this.refnbr = refnbr;
	}
	public String getAmtcdr() {
		return amtcdr;
	}
	public void setAmtcdr(String amtcdr) {
		this.amtcdr = amtcdr;
	}
	public String getTrsblv() {
		return trsblv;
	}
	public void setTrsblv(String trsblv) {
		this.trsblv = trsblv;
	}
	public String getTrsamt() {
		return trsamt;
	}
	public void setTrsamt(String trsamt) {
		this.trsamt = trsamt;
	}
	public String getCorporateBankAccount() {
		return corporateBankAccount;
	}
	public void setCorporateBankAccount(String corporateBankAccount) {
		this.corporateBankAccount = corporateBankAccount;
	}
	public String getDataSources() {
		return dataSources;
	}
	public void setDataSources(String dataSources) {
		this.dataSources = dataSources;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getOprationId() {
		return oprationId;
	}
	public void setOprationId(String oprationId) {
		this.oprationId = oprationId;
	}
	public String getOprationName() {
		return oprationName;
	}
	public void setOprationName(String oprationName) {
		this.oprationName = oprationName;
	}
	public String getHostIp() {
		return hostIp;
	}
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
}
