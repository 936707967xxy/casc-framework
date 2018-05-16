/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.hoomsun.after.api.util.excel.secode.annotation.ExcelField;

/**
 * 作者：Administrator <br>
 * 创建时间：2021年3月16日 <br>
 * 描述：客户划扣记录响应
 */
public class CustomerDeductResult {

	// 正常划扣表ID
		private String id;
		// 进件编号
		private String loanId;
		// 客户姓名
		private String castName;
		// 开户行编码
		private String bankCode;
		// 开户行名称
		private String bankName;
		// 银行卡号
		private String bankAcount;
		//客户ID
		private String castId;
		//客户类型
		private String posType;
		// 银行预留手机号
		private String tel;
		// 身份证号
		private String cardNo;
		// 划扣渠道
		private String deductChannel;
		// 划扣金额
		private BigDecimal deductMoney;
		// 划扣状态（成功：0，失败：-1，待定：2 存公中3）
		private String deductState;
		//划扣结果信息
		private String deductStateVal;
		//划扣结果CODE
		private String deductStateCode;
		//划扣手续费
		private BigDecimal deductSercerMoney;
		//划扣类型
		private String deductType;
		//划扣类型 值：正常月还，提前还款，逾期月还 余额充值
		private String deductTypeVal;
		// 划扣时间
		private Date deductDate;
		//查证时间
		private Date checkDate;
		//订单号
		private String orderNo;
		// 划扣记录类型(0,正常月还，1正常提前，2逾期月还，3逾期提前)
		private String deductRecordType;
		// 失败类型（值）
		private String failType;
		// 划扣客服id
		private String applicationCastId;
		// 划扣客服name
		private String applicationCastName;
		//创建时间
		private Date createTime;
		//更新时间
		private Date updateDate;
		//划扣期数
		private String dedutStream;
		//合同编号
	  	private String conNo;
	  	//划扣前账户存在余额
	  	private BigDecimal deductBal;
	  	//线下线上标示
	  	private String updownStatus;
	  	//进件编号
	  	private String serialId;
	  	
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		@ExcelField(title="进件编号", align=1, sort=2)
		public String getLoanId() {
			return loanId;
		}
		public void setLoanId(String loanId) {
			this.loanId = loanId;
		}
		@ExcelField(title="客户名称", align=1, sort=3)
		public String getCastName() {
			return castName;
		}
		public void setCastName(String castName) {
			this.castName = castName;
		}
		public String getBankCode() {
			return bankCode;
		}
		public void setBankCode(String bankCode) {
			this.bankCode = bankCode;
		}
		@ExcelField(title="银行名称", align=1, sort=6)
		public String getBankName() {
			return bankName;
		}
		public void setBankName(String bankName) {
			this.bankName = bankName;
		}
		@ExcelField(title="身份证号", align=1, sort=4)
		public String getCardNo() {
			return cardNo;
		}
		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}
		@ExcelField(title="划扣渠道", align=1, sort=9)
		public String getDeductChannel() {
			return deductChannel;
		}
		public void setDeductChannel(String deductChannel) {
			this.deductChannel = deductChannel;
		}
		@ExcelField(title="划扣金额", align=1, sort=11)
		public BigDecimal getDeductMoney() {
			return deductMoney;
		}
		public void setDeductMoney(BigDecimal deductMoney) {
			this.deductMoney = deductMoney;
		}
		@ExcelField(title="划扣状态", align=1, sort=13)
		public String getDeductState() {
			return deductState;
		}
		public void setDeductState(String deductState) {
			this.deductState = deductState;
		}
		@ExcelField(title="划扣结果信息", align=1, sort=14)
		public String getDeductStateVal() {
			return deductStateVal;
		}
		public void setDeductStateVal(String deductStateVal) {
			this.deductStateVal = deductStateVal;
		}
		public String getDeductStateCode() {
			return deductStateCode;
		}
		public void setDeductStateCode(String deductStateCode) {
			this.deductStateCode = deductStateCode;
		}
		@ExcelField(title="划扣手续费", align=1, sort=18)
		public BigDecimal getDeductSercerMoney() {
			return deductSercerMoney;
		}
		public void setDeductSercerMoney(BigDecimal deductSercerMoney) {
			this.deductSercerMoney = deductSercerMoney;
		}
		@ExcelField(title="划扣类型", align=1, sort=17)
		public String getDeductType() {
			return deductType;
		}
		public void setDeductType(String deductType) {
			this.deductType = deductType;
		}
		public String getDeductTypeVal() {
			return deductTypeVal;
		}
		public void setDeductTypeVal(String deductTypeVal) {
			this.deductTypeVal = deductTypeVal;
		}
		@ExcelField(title="划扣时间", align=1, sort=15)
		public Date getDeductDate() {
			return deductDate;
		}
		public void setDeductDate(Date deductDate) {
			this.deductDate = deductDate;
		}
		@ExcelField(title="查证时间", align=1, sort=16)
		public Date getCheckDate() {
			return checkDate;
		}
		public void setCheckDate(Date checkDate) {
			this.checkDate = checkDate;
		}
		@ExcelField(title="订单号", align=1, sort=10)
		public String getOrderNo() {
			return orderNo;
		}
		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}
		public String getDeductRecordType() {
			return deductRecordType;
		}
		public void setDeductRecordType(String deductRecordType) {
			this.deductRecordType = deductRecordType;
		}
		public String getFailType() {
			return failType;
		}
		public void setFailType(String failType) {
			this.failType = failType;
		}
		public String getApplicationCastId() {
			return applicationCastId;
		}
		public void setApplicationCastId(String applicationCastId) {
			this.applicationCastId = applicationCastId;
		}
		@ExcelField(title="划扣客服名称", align=1, sort=20)
		public String getApplicationCastName() {
			return applicationCastName;
		}
		public void setApplicationCastName(String applicationCastName) {
			this.applicationCastName = applicationCastName;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public Date getUpdateDate() {
			return updateDate;
		}
		public void setUpdateDate(Date updateDate) {
			this.updateDate = updateDate;
		}
		@ExcelField(title="划扣期数", align=1, sort=12)
		public String getDedutStream() {
			return dedutStream;
		}
		public void setDedutStream(String dedutStream) {
			this.dedutStream = dedutStream;
		}
		@ExcelField(title="合同编号", align=1, sort=1)
		public String getConNo() {
			return conNo;
		}
		public void setConNo(String conNo) {
			this.conNo = conNo;
		}
		@ExcelField(title="划扣前账户存在余额", align=1, sort=19)
		public BigDecimal getDeductBal() {
			return deductBal;
		}
		public void setDeductBal(BigDecimal deductBal) {
			this.deductBal = deductBal;
		}
		public String getUpdownStatus() {
			return updownStatus;
		}
		public void setUpdownStatus(String updownStatus) {
			this.updownStatus = updownStatus;
		}
		public String getSerialId() {
			return serialId;
		}
		public void setSerialId(String serialId) {
			this.serialId = serialId;
		}
		@ExcelField(title="银行账户", align=1, sort=7)
		public String getBankAcount() {
			return bankAcount;
		}
		public void setBankAcount(String bankAcount) {
			this.bankAcount = bankAcount;
		}
		@ExcelField(title="客户号", align=1, sort=8)
		public String getCastId() {
			return castId;
		}
		public void setCastId(String castId) {
			this.castId = castId;
		}
		@ExcelField(title="手机号码", align=1, sort=5)
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
		public String getPosType() {
			return posType;
		}
		public void setPosType(String posType) {
			this.posType = posType;
		}
}
