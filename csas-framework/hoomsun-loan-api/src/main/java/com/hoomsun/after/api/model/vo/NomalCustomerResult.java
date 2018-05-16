package com.hoomsun.after.api.model.vo;

import com.hoomsun.after.api.util.excel.secode.annotation.ExcelField;

/**
* <p>Title: NomalCustomerResp</p>  
* <p>Description:正常客户列表响应参数 </p>  
* @author xinyuan.xu@hoomsun.com  
* @date 2018年3月13日
 */
public class NomalCustomerResult {

	// 主键id
		private String id;
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
		//客户号
		private String castId;
		//客户来源
		private String castSource;
		//联系电话
		private String tel;
		//银行卡号
		private String bankAcount;
		//逾期期数
		private String overdueNum;
		//逾期天数
		private String overdueDays;
		//应收违约金
		private String receivePenaltyInterest;
		//应收罚息
		private String receiveInterest;
		//应收总额
		private String receiveMoney;
		//银行名称
		private String bankName;
		//账户余额
		private String balAmt;
		//客服名称
		private String managerCastName;
		//应还日期
		private String repayDate;
		//当前期数
		private String currentPeriod;
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
		//前线后援标示
		private String frontOrbackup;
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
		//减免金额
		private String deductMoney;
		//正常月还减免金额
		private String normalSubMoney;
		//正常月还减免失效日期
		private String normalSubDate;
		//正常月还减免期次
		private String normalSubStream;
		//逾期月还减免金额
		private String overdueSubMoney;
		//逾期月还减免失效时间
		private String overdueSubDate;
		//逾期月还减免期次
		private String overdueSubStrean;
		//提前还款减免金额
		private String advancedSubMoney;
		//advancedSubDate
		private String advancedSubDate;
		//
		private String advancedSubStream;
		
		/************HS_REPAYMENT_PLAN表信息************************************************************/
		//应还期数
		private String shouldTerm;
		//应还金额
		private String shouldAmt;
		//应还本金
		private String shouldCapi;
		//应还利息
		private String shouldInte;
		//账单日
		private String billsDate;
		//应还日期
		private String shouldDate;
		//还款状态
		private String repayStatus;
		//提前还清减免红小宝平台服务费
		private String preretuAmtHxb;
		//提前还清减免渠道服务费1（z1）给至信
		private String preretuAmtChannel;
		//剩余本金(期初)
		private String bal;
		//提前还款应还
		private String advanceShould;
		//实际提前还款
		private String advanceMoney;
		//提前还款减免
		private String advanceReduce;
		//提前还清减免渠道服务费2（z2）
		private String preretutalCredit;
		//剩余本金(期末)
		private String endBal;
		
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
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
		@ExcelField(title="客户姓名", align=1, sort=3)
		public String getCastName() {
			return castName;
		}
		public void setCastName(String castName) {
			this.castName = castName;
		}
		@ExcelField(title="身份证号", align=1, sort=4)
		public String getCardNo() {
			return cardNo;
		}
		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}
		@ExcelField(title="联系电话", align=1, sort=5)
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
		@ExcelField(title="客户类型", align=1, sort=6)
		public String getPosType() {
			return posType;
		}
		public void setPosType(String posType) {
			this.posType = posType;
		}
		@ExcelField(title="银行卡号", align=1, sort=7)
		public String getBankAcount() {
			return bankAcount;
		}
		public void setBankAcount(String bankAcount) {
			this.bankAcount = bankAcount;
		}
		@ExcelField(title="银行名称", align=1, sort=8)
		public String getBankName() {
			return bankName;
		}
		public void setBankName(String bankName) {
			this.bankName = bankName;
		}
		@ExcelField(title="产品名称", align=1, sort=9)
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		@ExcelField(title="贷款期数", align=1, sort=10)
		public String getLoanPeriod() {
			return loanPeriod;
		}
		public void setLoanPeriod(String loanPeriod) {
			this.loanPeriod = loanPeriod;
		}
		@ExcelField(title="当前期数", align=1, sort=11)
		public String getCurrentPeriod() {
			return currentPeriod;
		}
		public void setCurrentPeriod(String currentPeriod) {
			this.currentPeriod = currentPeriod;
		}
		@ExcelField(title="放款金额", align=1, sort=12)
		public String getLoanMoney() {
			return loanMoney;
		}
		public void setLoanMoney(String loanMoney) {
			this.loanMoney = loanMoney;
		}
		@ExcelField(title="放款日期", align=1, sort=13)
		public String getLoanDate() {
			return loanDate;
		}
		public void setLoanDate(String loanDate) {
			this.loanDate = loanDate;
		}
		@ExcelField(title="账单日", align=1, sort=14)
		public String getStatementDate() {
			return statementDate;
		}
		public void setStatementDate(String statementDate) {
			this.statementDate = statementDate;
		}
		@ExcelField(title="合同金额", align=1, sort=15)
		public String getConMoney() {
			return conMoney;
		}
		public void setConMoney(String conMoney) {
			this.conMoney = conMoney;
		}
		@ExcelField(title="账户余额", align=1, sort=16)
		public String getBalAmt() {
			return balAmt;
		}
		public void setBalAmt(String balAmt) {
			this.balAmt = balAmt;
		}
		@ExcelField(title="应还日期", align=1, sort=17)
		public String getRepayDate() {
			return repayDate;
		}
		public void setRepayDate(String repayDate) {
			this.repayDate = repayDate;
		}
		@ExcelField(title="客服名称", align=1, sort=23)
		public String getManagerCastName() {
			return managerCastName;
		}
		public void setManagerCastName(String managerCastName) {
			this.managerCastName = managerCastName;
		}
		
		
		
		public String getProductAlias() {
			return productAlias;
		}
		public void setProductAlias(String productAlias) {
			this.productAlias = productAlias;
		}
		public String getDalayFlag() {
			return dalayFlag;
		}
		public void setDalayFlag(String dalayFlag) {
			this.dalayFlag = dalayFlag;
		}
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
		public String getHangUp() {
			return hangUp;
		}
		public void setHangUp(String hangUp) {
			this.hangUp = hangUp;
		}
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
		public String getNormalSubMoney() {
			return normalSubMoney;
		}
		public void setNormalSubMoney(String normalSubMoney) {
			this.normalSubMoney = normalSubMoney;
		}
		public String getNormalSubDate() {
			return normalSubDate;
		}
		public void setNormalSubDate(String normalSubDate) {
			this.normalSubDate = normalSubDate;
		}
		public String getNormalSubStream() {
			return normalSubStream;
		}
		public void setNormalSubStream(String normalSubStream) {
			this.normalSubStream = normalSubStream;
		}
		public String getOverdueSubMoney() {
			return overdueSubMoney;
		}
		public void setOverdueSubMoney(String overdueSubMoney) {
			this.overdueSubMoney = overdueSubMoney;
		}
		public String getOverdueSubDate() {
			return overdueSubDate;
		}
		public void setOverdueSubDate(String overdueSubDate) {
			this.overdueSubDate = overdueSubDate;
		}
		public String getOverdueSubStrean() {
			return overdueSubStrean;
		}
		public void setOverdueSubStrean(String overdueSubStrean) {
			this.overdueSubStrean = overdueSubStrean;
		}
		public String getAdvancedSubMoney() {
			return advancedSubMoney;
		}
		public void setAdvancedSubMoney(String advancedSubMoney) {
			this.advancedSubMoney = advancedSubMoney;
		}
		public String getAdvancedSubDate() {
			return advancedSubDate;
		}
		public void setAdvancedSubDate(String advancedSubDate) {
			this.advancedSubDate = advancedSubDate;
		}
		public String getAdvancedSubStream() {
			return advancedSubStream;
		}
		public void setAdvancedSubStream(String advancedSubStream) {
			this.advancedSubStream = advancedSubStream;
		}
		@ExcelField(title="应还期数", align=1, sort=21)
		public String getShouldTerm() {
			return shouldTerm;
		}
		public void setShouldTerm(String shouldTerm) {
			this.shouldTerm = shouldTerm;
		}
		@ExcelField(title="月还款额", align=1, sort=20)
		public String getShouldAmt() {
			return shouldAmt;
		}
		public void setShouldAmt(String shouldAmt) {
			this.shouldAmt = shouldAmt;
		}
		@ExcelField(title="应还本金", align=1, sort=19)
		public String getShouldCapi() {
			return shouldCapi;
		}
		public void setShouldCapi(String shouldCapi) {
			this.shouldCapi = shouldCapi;
		}
		@ExcelField(title="应还利息", align=1, sort=18)
		public String getShouldInte() {
			return shouldInte;
		}
		public void setShouldInte(String shouldInte) {
			this.shouldInte = shouldInte;
		}
		public String getBillsDate() {
			return billsDate;
		}
		public void setBillsDate(String billsDate) {
			this.billsDate = billsDate;
		}
		public String getShouldDate() {
			return shouldDate;
		}
		public void setShouldDate(String shouldDate) {
			this.shouldDate = shouldDate;
		}
		public String getRepayStatus() {
			return repayStatus;
		}
		public void setRepayStatus(String repayStatus) {
			this.repayStatus = repayStatus;
		}
		public String getPreretuAmtHxb() {
			return preretuAmtHxb;
		}
		public void setPreretuAmtHxb(String preretuAmtHxb) {
			this.preretuAmtHxb = preretuAmtHxb;
		}
		public String getPreretuAmtChannel() {
			return preretuAmtChannel;
		}
		public void setPreretuAmtChannel(String preretuAmtChannel) {
			this.preretuAmtChannel = preretuAmtChannel;
		}
		public String getBal() {
			return bal;
		}
		public void setBal(String bal) {
			this.bal = bal;
		}
		public String getAdvanceShould() {
			return advanceShould;
		}
		public void setAdvanceShould(String advanceShould) {
			this.advanceShould = advanceShould;
		}
		@ExcelField(title="实际提前还款", align=1, sort=22)
		public String getAdvanceMoney() {
			return advanceMoney;
		}
		public void setAdvanceMoney(String advanceMoney) {
			this.advanceMoney = advanceMoney;
		}
		public String getAdvanceReduce() {
			return advanceReduce;
		}
		public void setAdvanceReduce(String advanceReduce) {
			this.advanceReduce = advanceReduce;
		}
		public String getPreretutalCredit() {
			return preretutalCredit;
		}
		public void setPreretutalCredit(String preretutalCredit) {
			this.preretutalCredit = preretutalCredit;
		}
		public String getEndBal() {
			return endBal;
		}
		public void setEndBal(String endBal) {
			this.endBal = endBal;
		}
		public String getDeductMoney() {
			return deductMoney;
		}
		public void setDeductMoney(String deductMoney) {
			this.deductMoney = deductMoney;
		}
		public String getCastId() {
			return castId;
		}
		public void setCastId(String castId) {
			this.castId = castId;
		}
		public String getOverdueNum() {
			return overdueNum;
		}
		public void setOverdueNum(String overdueNum) {
			this.overdueNum = overdueNum;
		}
		public String getOverdueDays() {
			return overdueDays;
		}
		public void setOverdueDays(String overdueDays) {
			this.overdueDays = overdueDays;
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
		public String getReceiveMoney() {
			return receiveMoney;
		}
		public void setReceiveMoney(String receiveMoney) {
			this.receiveMoney = receiveMoney;
		}
		public String getCastSource() {
			return castSource;
		}
		public void setCastSource(String castSource) {
			this.castSource = castSource;
		}
		public String getFrontOrbackup() {
			return frontOrbackup;
		}
		public void setFrontOrbackup(String frontOrbackup) {
			this.frontOrbackup = frontOrbackup;
		}
}
