/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.vo;

import com.hoomsun.after.api.util.excel.secode.annotation.ExcelField;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月28日 <br>
 * 描述：结清客户响应信息
 */
public class SettleCustomerResult {
	
	//进件编号
		private String loanId;
		//合同编号
		private String conNo;
		//客户姓名
		private String castName;
		//身份证号
		private String cardNo;
		//联系电话
		private String tel;
		//当前期次
		private String currentPeriod;
		//应还日期
		private String repayDate;
		//贷款期数
		private String loanPeriod;
		//放款金额
		private String loanMoney;
		//放款日期
		private String loanDate;
		//合同金额
		private String conMoney;
		//银行名称
		private String bank;
		//银行账户
		private String bankAccount;
		//账户余额
		private String bal;
		//客户ID
		private String castId;
		//应还期次
		private String shouldTerm;
		//应还金额
		private String shouldAmt;
		//应还本金
		private String shouldCapi;
		//应还利息
		private String shouldInte;
		//账单日
		private String billsDate;
		//还款状态
		private String repayStatus;
		//提前还款应还
		private String advanceShould;
		//实际提前还款
		private String advanceMoney;
		//提前还款减免
		private String advanceReduce;
		
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
		@ExcelField(title="当前期次", align=1, sort=6)
		public String getCurrentPeriod() {
			return currentPeriod;
		}
		public void setCurrentPeriod(String currentPeriod) {
			this.currentPeriod = currentPeriod;
		}
		@ExcelField(title="应还日期", align=1, sort=7)
		public String getRepayDate() {
			return repayDate;
		}
		public void setRepayDate(String repayDate) {
			this.repayDate = repayDate;
		}
		@ExcelField(title="贷款期数", align=1, sort=8)
		public String getLoanPeriod() {
			return loanPeriod;
		}
		public void setLoanPeriod(String loanPeriod) {
			this.loanPeriod = loanPeriod;
		}
		@ExcelField(title="放款金额", align=1, sort=9)
		public String getLoanMoney() {
			return loanMoney;
		}
		public void setLoanMoney(String loanMoney) {
			this.loanMoney = loanMoney;
		}
		@ExcelField(title="放款日期", align=1, sort=10)
		public String getLoanDate() {
			return loanDate;
		}
		public void setLoanDate(String loanDate) {
			this.loanDate = loanDate;
		}
		@ExcelField(title="账单日", align=1, sort=11)
		public String getBillsDate() {
			return billsDate;
		}
		public void setBillsDate(String billsDate) {
			this.billsDate = billsDate;
		}
		@ExcelField(title="合同金额", align=1, sort=12)
		public String getConMoney() {
			return conMoney;
		}
		public void setConMoney(String conMoney) {
			this.conMoney = conMoney;
		}
		@ExcelField(title="银行名称", align=1, sort=13)
		public String getBank() {
			return bank;
		}
		public void setBank(String bank) {
			this.bank = bank;
		}
		@ExcelField(title="银行账户", align=1, sort=14)
		public String getBankAccount() {
			return bankAccount;
		}
		public void setBankAccount(String bankAccount) {
			this.bankAccount = bankAccount;
		}
		@ExcelField(title="账户余额", align=1, sort=15)
		public String getBal() {
			return bal;
		}
		public void setBal(String bal) {
			this.bal = bal;
		}
		@ExcelField(title="应还期数", align=1, sort=16)
		public String getShouldTerm() {
			return shouldTerm;
		}
		public void setShouldTerm(String shouldTerm) {
			this.shouldTerm = shouldTerm;
		}
		@ExcelField(title="应还金额", align=1, sort=17)
		public String getShouldAmt() {
			return shouldAmt;
		}
		public void setShouldAmt(String shouldAmt) {
			this.shouldAmt = shouldAmt;
		}
		@ExcelField(title="应还本金", align=1, sort=18)
		public String getShouldCapi() {
			return shouldCapi;
		}
		public void setShouldCapi(String shouldCapi) {
			this.shouldCapi = shouldCapi;
		}
		@ExcelField(title="应还利息", align=1, sort=19)
		public String getShouldInte() {
			return shouldInte;
		}
		public void setShouldInte(String shouldInte) {
			this.shouldInte = shouldInte;
		}
		@ExcelField(title="还款状态", align=1, sort=20)
		public String getRepayStatus() {
			return repayStatus;
		}
		public void setRepayStatus(String repayStatus) {
			this.repayStatus = repayStatus;
		}
		@ExcelField(title="提前还款应还", align=1, sort=21)
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
		@ExcelField(title="提前还款减免", align=1, sort=23)
		public String getAdvanceReduce() {
			return advanceReduce;
		}
		public void setAdvanceReduce(String advanceReduce) {
			this.advanceReduce = advanceReduce;
		}
		public String getCastId() {
			return castId;
		}
		public void setCastId(String castId) {
			this.castId = castId;
		}
}
