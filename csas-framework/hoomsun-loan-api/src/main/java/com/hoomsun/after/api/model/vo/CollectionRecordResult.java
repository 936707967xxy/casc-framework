/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.vo;

import com.hoomsun.after.api.util.excel.secode.annotation.ExcelField;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月13日 <br>
 * 描述：催收记录
 */
public class CollectionRecordResult {

	        // 进件编号 (对应APPLYID)
			private String loanId;
			//合同编号
			private String conNo;
			//APPLY_ID
			private String applyId;
			// 客户姓名
			private String castName;
			//身份证号码
			private String cardNo;
			//客户ID
			private String castId;
			//联系电话
			private String tel;
			//性别
			private String sex;
			//应还日期
			private String repayDate;
			//当前期数
			private String currentPeriod;
			//银行账户
			private String bankAcount;
			//客户来源
			private String castSource;
			//贷款期数
			private String loanPeriod;
			//放款金额
			private String loanMoney;
			//放款日期
			private String loanDate;
			//逾期标识
			private String dalayFlag;
			//是否已过最后一期（账期终结）
			private String settleFlag;
			//线上线下标识
			private String updownStatus;
			//POST类型
			private String posType;
			//产品名称
			private String productName;
			//产品别名
			private String productAlias;
			//此单子处于贷后还是前线（前线：0，贷后：1）
			private String customerOrLoan;
			//前线客服ID
			private String managerCastId;
			//前线客服
			private String managerCast;
			//贷后客服ID
			private String loanManagerCastId;
			//贷后客服Name
			private String loanManagerCastName;
			//贷后留安状态（1在留案中）
			private String loanLeave;
			//挂起标识（1挂起）
			private String hangUp;
			//挂起时间
			private String hangUpDate;
			//M段
			private String mSection;
			//销售营业部ID
			private String salesDeptment;
			//客户版本标识
			private String currentVersion;
			//修改时间
			private String updateDate;
			//创建日期
			private String createTime;
			//账单日
			private String statementDate;
			//合同金额
			private String conMoney;
			//逾期月还减免金额
			private String overdueSubMoney;
			
			/*****HS_AFTER_OVERDUE_RECORD表******************************************************/
			//逾期天数
			private String overdueDays;
			//应收违约金
			private String receivePenalTy;
			//应收罚息
			private String receiveInterst;
			//应收违罚金（应收违约金+应收罚息）
			private String receivePenaltyInt;
			//应还本金
			private String receiveCorpus;
			//应还利息
			private String receiveShouldinte;
			//月还款额
			private String amt;
			//应收总额（违罚金+月还款额）
			private String receiveMoney;
			//逾期期数
			private String overdueNum;
			//是否标红(1标红)
			private String toRed;
			
			//应收违约金
			private String receivePenaltyInterest;
			//应收罚息
			private String receiveInterest;
			//已收金额
			private String receivedMoney;
			//结欠金额
			private String balanceMoney;
			//前线后援标示
			private String frontOrbackup;
			
			@ExcelField(title="进件编号", align=1, sort=1)
			public String getLoanId() {
				return loanId;
			}
			public void setLoanId(String loanId) {
				this.loanId = loanId;
			}
			@ExcelField(title="合同编号", align=1, sort=2)
			public String getConNo() {
				return conNo;
			}
			public void setConNo(String conNo) {
				this.conNo = conNo;
			}
			public String getApplyId() {
				return applyId;
			}
			public void setApplyId(String applyId) {
				this.applyId = applyId;
			}
			@ExcelField(title="客户姓名", align=1, sort=4)
			public String getCastName() {
				return castName;
			}
			public void setCastName(String castName) {
				this.castName = castName;
			}
			@ExcelField(title="身份证号", align=1, sort=5)
			public String getCardNo() {
				return cardNo;
			}
			public void setCardNo(String cardNo) {
				this.cardNo = cardNo;
			}
			@ExcelField(title="电话号码", align=1, sort=6)
			public String getTel() {
				return tel;
			}
			public void setTel(String tel) {
				this.tel = tel;
			}
			@ExcelField(title="应还日期", align=1, sort=7)
			public String getRepayDate() {
				return repayDate;
			}
			public void setRepayDate(String repayDate) {
				this.repayDate = repayDate;
			}
			@ExcelField(title="当前期数", align=1, sort=8)
			public String getCurrentPeriod() {
				return currentPeriod;
			}
			public void setCurrentPeriod(String currentPeriod) {
				this.currentPeriod = currentPeriod;
			}
			@ExcelField(title="贷款期数", align=1, sort=9)
			public String getLoanPeriod() {
				return loanPeriod;
			}
			public void setLoanPeriod(String loanPeriod) {
				this.loanPeriod = loanPeriod;
			}
			@ExcelField(title="放款金额", align=1, sort=10)
			public String getLoanMoney() {
				return loanMoney;
			}
			public void setLoanMoney(String loanMoney) {
				this.loanMoney = loanMoney;
			}
			@ExcelField(title="放款金额", align=1, sort=11)
			public String getLoanDate() {
				return loanDate;
			}
			public void setLoanDate(String loanDate) {
				this.loanDate = loanDate;
			}
			@ExcelField(title="是否逾期", align=1, sort=12)
			public String getDalayFlag() {
				return dalayFlag;
			}
			public void setDalayFlag(String dalayFlag) {
				this.dalayFlag = dalayFlag;
			}
			@ExcelField(title="是否最后一期", align=1, sort=12)
			public String getSettleFlag() {
				return settleFlag;
			}
			public void setSettleFlag(String settleFlag) {
				this.settleFlag = settleFlag;
			}
			public String getUpdownStatus() {
				return updownStatus;
			}
			public void setUpdownStatus(String updownStatus) {
				this.updownStatus = updownStatus;
			}
			@ExcelField(title="POS类型", align=1, sort=13)
			public String getPosType() {
				return posType;
			}
			public void setPosType(String posType) {
				this.posType = posType;
			}
			@ExcelField(title="产品名称", align=1, sort=14)
			public String getProductName() {
				return productName;
			}
			public void setProductName(String productName) {
				this.productName = productName;
			}
			@ExcelField(title="产品别名", align=1, sort=15)
			public String getProductAlias() {
				return productAlias;
			}
			public void setProductAlias(String productAlias) {
				this.productAlias = productAlias;
			}
			public String getCustomerOrLoan() {
				return customerOrLoan;
			}
			public void setCustomerOrLoan(String customerOrLoan) {
				this.customerOrLoan = customerOrLoan;
			}
			public String getManagerCastId() {
				return managerCastId;
			}
			public void setManagerCastId(String managerCastId) {
				this.managerCastId = managerCastId;
			}
			public String getManagerCast() {
				return managerCast;
			}
			public void setManagerCast(String managerCast) {
				this.managerCast = managerCast;
			}
			public String getLoanManagerCastId() {
				return loanManagerCastId;
			}
			public void setLoanManagerCastId(String loanManagerCastId) {
				this.loanManagerCastId = loanManagerCastId;
			}
			public String getLoanManagerCastName() {
				return loanManagerCastName;
			}
			public void setLoanManagerCastName(String loanManagerCastName) {
				this.loanManagerCastName = loanManagerCastName;
			}
			public String getLoanLeave() {
				return loanLeave;
			}
			public void setLoanLeave(String loanLeave) {
				this.loanLeave = loanLeave;
			}
			@ExcelField(title="是否挂起", align=1, sort=16)
			public String getHangUp() {
				return hangUp;
			}
			public void setHangUp(String hangUp) {
				this.hangUp = hangUp;
			}
			@ExcelField(title="挂起时间", align=1, sort=17)
			public String getHangUpDate() {
				return hangUpDate;
			}
			public void setHangUpDate(String hangUpDate) {
				this.hangUpDate = hangUpDate;
			}
			public String getmSection() {
				return mSection;
			}
			public void setmSection(String mSection) {
				this.mSection = mSection;
			}
			public String getSalesDeptment() {
				return salesDeptment;
			}
			public void setSalesDeptment(String salesDeptment) {
				this.salesDeptment = salesDeptment;
			}
			public String getCurrentVersion() {
				return currentVersion;
			}
			public void setCurrentVersion(String currentVersion) {
				this.currentVersion = currentVersion;
			}
			public String getUpdateDate() {
				return updateDate;
			}
			public void setUpdateDate(String updateDate) {
				this.updateDate = updateDate;
			}
			public String getCreateTime() {
				return createTime;
			}
			public void setCreateTime(String createTime) {
				this.createTime = createTime;
			}
			@ExcelField(title="账单日", align=1, sort=18)
			public String getStatementDate() {
				return statementDate;
			}
			public void setStatementDate(String statementDate) {
				this.statementDate = statementDate;
			}
			@ExcelField(title="合同金额", align=1, sort=19)
			public String getConMoney() {
				return conMoney;
			}
			public void setConMoney(String conMoney) {
				this.conMoney = conMoney;
			}
			@ExcelField(title="逾期天数", align=1, sort=20)
			public String getOverdueDays() {
				return overdueDays;
			}
			public void setOverdueDays(String overdueDays) {
				this.overdueDays = overdueDays;
			}
			public String getReceivePenalTy() {
				return receivePenalTy;
			}
			public void setReceivePenalTy(String receivePenalTy) {
				this.receivePenalTy = receivePenalTy;
			}
			@ExcelField(title="应收罚息", align=1, sort=21)
			public String getReceiveInterst() {
				return receiveInterst;
			}
			public void setReceiveInterst(String receiveInterst) {
				this.receiveInterst = receiveInterst;
			}
			
			public String getReceivePenaltyInt() {
				return receivePenaltyInt;
			}
			public void setReceivePenaltyInt(String receivePenaltyInt) {
				this.receivePenaltyInt = receivePenaltyInt;
			}
			public String getReceiveCorpus() {
				return receiveCorpus;
			}
			public void setReceiveCorpus(String receiveCorpus) {
				this.receiveCorpus = receiveCorpus;
			}
			public String getReceiveShouldinte() {
				return receiveShouldinte;
			}
			public void setReceiveShouldinte(String receiveShouldinte) {
				this.receiveShouldinte = receiveShouldinte;
			}
			public String getAmt() {
				return amt;
			}
			public void setAmt(String amt) {
				this.amt = amt;
			}
			public String getReceiveMoney() {
				return receiveMoney;
			}
			public void setReceiveMoney(String receiveMoney) {
				this.receiveMoney = receiveMoney;
			}
			public String getOverdueNum() {
				return overdueNum;
			}
			public void setOverdueNum(String overdueNum) {
				this.overdueNum = overdueNum;
			}
			public String getToRed() {
				return toRed;
			}
			public void setToRed(String toRed) {
				this.toRed = toRed;
			}
			public String getBankAcount() {
				return bankAcount;
			}
			public void setBankAcount(String bankAcount) {
				this.bankAcount = bankAcount;
			}
			public String getCastSource() {
				return castSource;
			}
			public void setCastSource(String castSource) {
				this.castSource = castSource;
			}
			public String getOverdueSubMoney() {
				return overdueSubMoney;
			}
			public void setOverdueSubMoney(String overdueSubMoney) {
				this.overdueSubMoney = overdueSubMoney;
			}
			@ExcelField(title="性别", align=1, sort=4)
			public String getSex() {
				return sex;
			}
			public void setSex(String sex) {
				this.sex = sex;
			}
			@ExcelField(title="客户ID", align=1, sort=3)
			public String getCastId() {
				return castId;
			}
			public void setCastId(String castId) {
				this.castId = castId;
			}
			public String getReceivePenaltyInterest() {
				return receivePenaltyInterest;
			}
			public void setReceivePenaltyInterest(String receivePenaltyInterest) {
				this.receivePenaltyInterest = receivePenaltyInterest;
			}
			public String getReceiveInterest() {
				return receiveInterest;
			}
			public void setReceiveInterest(String receiveInterest) {
				this.receiveInterest = receiveInterest;
			}
			public String getReceivedMoney() {
				return receivedMoney;
			}
			public void setReceivedMoney(String receivedMoney) {
				this.receivedMoney = receivedMoney;
			}
			public String getBalanceMoney() {
				return balanceMoney;
			}
			public void setBalanceMoney(String balanceMoney) {
				this.balanceMoney = balanceMoney;
			}
			public String getFrontOrbackup() {
				return frontOrbackup;
			}
			public void setFrontOrbackup(String frontOrbackup) {
				this.frontOrbackup = frontOrbackup;
			}
}
