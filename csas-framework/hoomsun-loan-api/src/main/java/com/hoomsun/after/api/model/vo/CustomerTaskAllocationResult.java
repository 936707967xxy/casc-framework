/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.vo;

import com.hoomsun.after.api.util.excel.secode.annotation.ExcelField;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月14日 <br>
 * 描述：客户任务分配响应参数
 */
public class CustomerTaskAllocationResult {

	        // 主键id
			private String id;
			// 进件编号 (对应APPLYID)
			private String loanId;
			
			private String conNo;
			//APPLY_ID
			private String applyId;
			// 客户姓名
			private String castName;
			//身份证号码
			private String cardNo;
			//联系电话
			private String tel;
			//客户号
			private String castId;
			//客户来源
			private String castSource;
			//应还日期
			private String repayDate;
			//当前期数
			private String currentPeriod;
			//剩余期数
			private String surplusPeriod;
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
			//部门编号
			private String empId;
			//工号
			private String empWorkNum;
			//进件单数
			private String loanBalNum;
			//员工姓名
			private String empName;
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
			private String billDays;
			//合同金额
			private String conMoney;
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
			//前线后援标示
			private String frontOrbackup;
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
			@ExcelField(title="身份证号", align=1, sort=5)
			public String getCardNo() {
				return cardNo;
			}
			public void setCardNo(String cardNo) {
				this.cardNo = cardNo;
			}
			@ExcelField(title="手机号码", align=1, sort=6)
			public String getTel() {
				return tel;
			}
			public void setTel(String tel) {
				this.tel = tel;
			}
			public String getRepayDate() {
				return repayDate;
			}
			public void setRepayDate(String repayDate) {
				this.repayDate = repayDate;
			}
			@ExcelField(title="当前期数", align=1, sort=14)
			public String getCurrentPeriod() {
				return currentPeriod;
			}
			public void setCurrentPeriod(String currentPeriod) {
				this.currentPeriod = currentPeriod;
			}
			@ExcelField(title="贷款期数", align=1, sort=13)
			public String getLoanPeriod() {
				return loanPeriod;
			}
			public void setLoanPeriod(String loanPeriod) {
				this.loanPeriod = loanPeriod;
			}
			@ExcelField(title="放款金额", align=1, sort=11)
			public String getLoanMoney() {
				return loanMoney;
			}
			public void setLoanMoney(String loanMoney) {
				this.loanMoney = loanMoney;
			}
			@ExcelField(title="放款日期", align=1, sort=12)
			public String getLoanDate() {
				return loanDate;
			}
			public void setLoanDate(String loanDate) {
				this.loanDate = loanDate;
			}
			@ExcelField(title="是否逾期", align=1, sort=20)
			public String getDalayFlag() {
				return dalayFlag;
			}
			public void setDalayFlag(String dalayFlag) {
				this.dalayFlag = dalayFlag;
			}
			@ExcelField(title="是否结清", align=1, sort=18)
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
			public String getPosType() {
				return posType;
			}
			public void setPosType(String posType) {
				this.posType = posType;
			}
			@ExcelField(title="产品名称", align=1, sort=9)
			public String getProductName() {
				return productName;
			}
			public void setProductName(String productName) {
				this.productName = productName;
			}
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
			@ExcelField(title="贷后客服", align=1, sort=16)
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
			@ExcelField(title="是否挂起", align=1, sort=19)
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
			@ExcelField(title="M段", align=1, sort=4)
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
			@ExcelField(title="合同金额", align=1, sort=10)
			public String getConMoney() {
				return conMoney;
			}
			@ExcelField(title="账单日", align=1, sort=17)
			public String getBillDays() {
				return billDays;
			}
			public void setBillDays(String billDays) {
				this.billDays = billDays;
			}
			public void setConMoney(String conMoney) {
				this.conMoney = conMoney;
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
			public String getEmpId() {
				return empId;
			}
			public void setEmpId(String empId) {
				this.empId = empId;
			}
			public String getEmpWorkNum() {
				return empWorkNum;
			}
			public void setEmpWorkNum(String empWorkNum) {
				this.empWorkNum = empWorkNum;
			}
			public String getEmpName() {
				return empName;
			}
			public void setEmpName(String empName) {
				this.empName = empName;
			}
			public String getLoanBalNum() {
				return loanBalNum;
			}
			public void setLoanBalNum(String loanBalNum) {
				this.loanBalNum = loanBalNum;
			}
			@ExcelField(title="客户号", align=1, sort=7)
			public String getCastId() {
				return castId;
			}
			public void setCastId(String castId) {
				this.castId = castId;
			}
			@ExcelField(title="客户来源", align=1, sort=8)
			public String getCastSource() {
				return castSource;
			}
			public void setCastSource(String castSource) {
				this.castSource = castSource;
			}
			@ExcelField(title="剩余期数", align=1, sort=15)
			public String getSurplusPeriod() {
				return surplusPeriod;
			}
			public void setSurplusPeriod(String surplusPeriod) {
				this.surplusPeriod = surplusPeriod;
			}
			public String getFrontOrbackup() {
				return frontOrbackup;
			}
			public void setFrontOrbackup(String frontOrbackup) {
				this.frontOrbackup = frontOrbackup;
			}
}
