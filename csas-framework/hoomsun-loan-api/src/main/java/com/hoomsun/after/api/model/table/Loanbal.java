package com.hoomsun.after.api.model.table;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 作者：金世强 <br>
 * 创建时间：2018年3月19日 <br>
 * 描述： 贷后逻辑表
 *
 */
public class Loanbal {
	/**
	 * 贷后逻辑表主键
	 */
	private String id;

	/**
	 * 进件编号
	 */
	private String loanId;

	/**
	 * 合同编号
	 */
	private String conNo;

	/**
	 * APPLY_ID
	 */
	private String applyId;

	/**
	 * 客户姓名
	 */
	private String castName;

	/**
	 * 身份证号码
	 */
	private String cardNo;

	/**
	 * 联系电话
	 */
	private String tel;

	/**
	 * 应还日期
	 */
	private Date repayDate;

	/**
	 * 当前期数
	 */
	private Integer currentPeriod;

	/**
	 * 贷款期数
	 */
	private Integer loanPeriod;

	/**
	 * 放款金额
	 */
	private BigDecimal loanMoney;

	/**
	 * 放款日期
	 */
	private Date loanDate;

	/**
	 * 逾期标识
	 */
	private String delayFlag;

	/**
	 * 是否已过最后一期（账期终结）
	 */
	private String settleFlag;

	/**
	 * 线上线下标识
	 */
	private String updownStatus;

	/**
	 * POST类型
	 */
	private String posType;

	/**
	 * 产品名称
	 */
	private String productName;

	/**
	 * 产品别名
	 */
	private String productalias;

	/**
	 * 此单子处于贷后还是前线（前线：0，贷后：1）
	 */
	private String customerOrLoan;

	/**
	 * 前线客服ID
	 */
	private String managerCastId;

	/**
	 * 前线客服
	 */
	private String managerCast;

	/**
	 * 贷后客服ID
	 */
	private String loanManagerCastId;

	/**
	 * 贷后客服Name
	 */
	private String loanManagerCast;
	/**
	 * 贷后留安状态（1在留案中）
	 */
	private String laonLeave;
	/**
	 * 挂起标识（逾期跑批挂起）
	 */
	private String hangUp;

	/**
	 * 挂起时间
	 */
	private Date hangUpDate;

	/**
	 * 挂起标识（划扣挂起）
	 */
	private String hangUpDeduct;

	/**
	 * 挂起时间
	 */
	private Date hangUpDeductDate;
	/**
	 * M段
	 */
	private Integer mSection;
	/**
	 * 销售营业部ID
	 */
	private String salesDeptment;

	/**
	 * 客户版本标识（1。。。）
	 */
	private String currentVersion;

	/**
	 * 修改时间
	 */
	private Date updateDate;

	/**
	 * 创建日期
	 */
	private Date createTime;

	/**
	 * 账单日
	 */
	private Integer statementDate;

	/**
	 * 合同金额
	 */
	private BigDecimal conMoney;

	/**
	 * 正常月还减免金额
	 */
	private BigDecimal normalSubMoney;

	/**
	 * 正常月还减免失效日期
	 */
	private Date normalSubDate;

	/**
	 * 正常月还减免期次
	 */
	private Integer normalSubStream;

	/**
	 * 逾期月还减免金额
	 */
	private BigDecimal overdueSubMoney;

	/**
	 * 逾期月还减免失效时间
	 */
	private Date overdueSubDate;

	/**
	 * 逾期月还减免期次
	 */
	private Integer overdueSubStream;

	/**
	 * 提前还款减免金额
	 */
	private BigDecimal advancedSubMoney;

	/**
	 * 提前还款减免失效时间
	 */
	private Date advancedSubDate;

	/**
	 * 提前还款减免期数
	 */
	private Integer advancedSubStream;

	/**
	 * 客户ID
	 */
	private String castId;
	/**
	 * 外仿留案状态（1留案中）
	 */
	private String outboundLeave;

	/**
	 * 富友项目ID
	 */
	private String productId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId == null ? null : loanId.trim();
	}

	public String getConNo() {
		return conNo;
	}

	public void setConNo(String conNo) {
		this.conNo = conNo == null ? null : conNo.trim();
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId == null ? null : applyId.trim();
	}

	public String getCastName() {
		return castName;
	}

	public void setCastName(String castName) {
		this.castName = castName == null ? null : castName.trim();
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo == null ? null : cardNo.trim();
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel == null ? null : tel.trim();
	}

	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}

	public Integer getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(Integer currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public Integer getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(Integer loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public BigDecimal getLoanMoney() {
		return loanMoney;
	}

	public void setLoanMoney(BigDecimal loanMoney) {
		this.loanMoney = loanMoney;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public String getDelayFlag() {
		return delayFlag;
	}

	public void setDelayFlag(String delayFlag) {
		this.delayFlag = delayFlag == null ? null : delayFlag.trim();
	}

	public String getSettleFlag() {
		return settleFlag;
	}

	public void setSettleFlag(String settleFlag) {
		this.settleFlag = settleFlag == null ? null : settleFlag.trim();
	}

	public String getUpdownStatus() {
		return updownStatus;
	}

	public void setUpdownStatus(String updownStatus) {
		this.updownStatus = updownStatus == null ? null : updownStatus.trim();
	}

	public String getPosType() {
		return posType;
	}

	public void setPosType(String posType) {
		this.posType = posType == null ? null : posType.trim();
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName == null ? null : productName.trim();
	}

	public String getProductalias() {
		return productalias;
	}

	public void setProductalias(String productalias) {
		this.productalias = productalias == null ? null : productalias.trim();
	}

	public String getCustomerOrLoan() {
		return customerOrLoan;
	}

	public void setCustomerOrLoan(String customerOrLoan) {
		this.customerOrLoan = customerOrLoan == null ? null : customerOrLoan.trim();
	}

	public String getManagerCastId() {
		return managerCastId;
	}

	public void setManagerCastId(String managerCastId) {
		this.managerCastId = managerCastId == null ? null : managerCastId.trim();
	}

	public String getManagerCast() {
		return managerCast;
	}

	public void setManagerCast(String managerCast) {
		this.managerCast = managerCast == null ? null : managerCast.trim();
	}

	public String getLoanManagerCastId() {
		return loanManagerCastId;
	}

	public void setLoanManagerCastId(String loanManagerCastId) {
		this.loanManagerCastId = loanManagerCastId == null ? null : loanManagerCastId.trim();
	}

	public String getLoanManagerCast() {
		return loanManagerCast;
	}

	public void setLoanManagerCast(String loanManagerCast) {
		this.loanManagerCast = loanManagerCast == null ? null : loanManagerCast.trim();
	}

	public String getLaonLeave() {
		return laonLeave;
	}

	public void setLaonLeave(String laonLeave) {
		this.laonLeave = laonLeave == null ? null : laonLeave.trim();
	}

	public String getHangUp() {
		return hangUp;
	}

	public void setHangUp(String hangUp) {
		this.hangUp = hangUp == null ? null : hangUp.trim();
	}

	public Date getHangUpDate() {
		return hangUpDate;
	}

	public void setHangUpDate(Date hangUpDate) {
		this.hangUpDate = hangUpDate;
	}

	public Integer getmSection() {
		return mSection;
	}

	public void setmSection(Integer mSection) {
		this.mSection = mSection;
	}

	public String getSalesDeptment() {
		return salesDeptment;
	}

	public void setSalesDeptment(String salesDeptment) {
		this.salesDeptment = salesDeptment == null ? null : salesDeptment.trim();
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion == null ? null : currentVersion.trim();
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatementDate() {
		return statementDate;
	}

	public void setStatementDate(Integer statementDate) {
		this.statementDate = statementDate;
	}

	public BigDecimal getConMoney() {
		return conMoney;
	}

	public void setConMoney(BigDecimal conMoney) {
		this.conMoney = conMoney;
	}

	public BigDecimal getNormalSubMoney() {
		return normalSubMoney;
	}

	public void setNormalSubMoney(BigDecimal normalSubMoney) {
		this.normalSubMoney = normalSubMoney;
	}

	public Date getNormalSubDate() {
		return normalSubDate;
	}

	public void setNormalSubDate(Date normalSubDate) {
		this.normalSubDate = normalSubDate;
	}

	public Integer getNormalSubStream() {
		return normalSubStream;
	}

	public void setNormalSubStream(Integer normalSubStream) {
		this.normalSubStream = normalSubStream;
	}

	public BigDecimal getOverdueSubMoney() {
		return overdueSubMoney;
	}

	public void setOverdueSubMoney(BigDecimal overdueSubMoney) {
		this.overdueSubMoney = overdueSubMoney;
	}

	public Date getOverdueSubDate() {
		return overdueSubDate;
	}

	public void setOverdueSubDate(Date overdueSubDate) {
		this.overdueSubDate = overdueSubDate;
	}

	public Integer getOverdueSubStream() {
		return overdueSubStream;
	}

	public void setOverdueSubStream(Integer overdueSubStream) {
		this.overdueSubStream = overdueSubStream;
	}

	public BigDecimal getAdvancedSubMoney() {
		return advancedSubMoney;
	}

	public void setAdvancedSubMoney(BigDecimal advancedSubMoney) {
		this.advancedSubMoney = advancedSubMoney;
	}

	public Date getAdvancedSubDate() {
		return advancedSubDate;
	}

	public void setAdvancedSubDate(Date advancedSubDate) {
		this.advancedSubDate = advancedSubDate;
	}

	public Integer getAdvancedSubStream() {
		return advancedSubStream;
	}

	public void setAdvancedSubStream(Integer advancedSubStream) {
		this.advancedSubStream = advancedSubStream;
	}

	public String getCastId() {
		return castId;
	}

	public void setCastId(String castId) {
		this.castId = castId;
	}

	public String getOutboundLeave() {
		return outboundLeave;
	}

	public void setOutboundLeave(String outboundLeave) {
		this.outboundLeave = outboundLeave;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getHangUpDeduct() {
		return hangUpDeduct;
	}

	public void setHangUpDeduct(String hangUpDeduct) {
		this.hangUpDeduct = hangUpDeduct;
	}

	public Date getHangUpDeductDate() {
		return hangUpDeductDate;
	}

	public void setHangUpDeductDate(Date hangUpDeductDate) {
		this.hangUpDeductDate = hangUpDeductDate;
	}

}