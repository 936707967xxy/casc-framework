/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

import javax.servlet.http.HttpServletResponse;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月13日 <br>
 * 描述：催收记录
 */
public class CollectionRecordReq {

	// 客户姓名
		private String castName;
		// 联系电话
		private String tel;
		// 身份证号
		private String cardNo;
		//合同编号
		private String conNo;
		// 进件编号 (对应APPLYID)
		private String loanId;
		//前线客服ID
		private String managerCastId;
		//后援客服ID
		private String loanManagerCastId;
		//后援客服名称
		private String loanManagerCast;
		//销售营业部ID
		private String salesDeptment;
		private String overdueNum;
		/**
		 * 查询渠道
		 * 0：前线
		 * 1：后援
		 */
		private String queryType;
		//M段
		private String mSection;
		/**
		 * 逾期标示
		 * 0：逾期
		 */
		private String dalayFlag;
		//是否已过最后一期（账期终结）
		private String settleFlag;
		/**
		 * 此单子处于贷后还是前线
		 * 前线：0
		 * 贷后：1
		 */
		private String customerOrLoan;
		//部门Id
		private String deptId;
		private String emptId;
		//客户类型
		private String posType;
		
		private HttpServletResponse response;
		//页码
		private int page;
		//每页条数
		private int pageSize;
		public String getCastName() {
			return castName;
		}
		public void setCastName(String castName) {
			this.castName = castName;
		}
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
		public String getCardNo() {
			return cardNo;
		}
		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}
		public String getConNo() {
			return conNo;
		}
		public void setConNo(String conNo) {
			this.conNo = conNo;
		}
		public String getLoanId() {
			return loanId;
		}
		public void setLoanId(String loanId) {
			this.loanId = loanId;
		}
		public String getManagerCastId() {
			return managerCastId;
		}
		public void setManagerCastId(String managerCastId) {
			this.managerCastId = managerCastId;
		}
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
		}
		public int getPageSize() {
			return pageSize;
		}
		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}
		public String getQueryType() {
			return queryType;
		}
		public void setQueryType(String queryType) {
			this.queryType = queryType;
		}
		public String getmSection() {
			return mSection;
		}
		public void setmSection(String mSection) {
			this.mSection = mSection;
		}
		public String getDalayFlag() {
			return dalayFlag;
		}
		public void setDalayFlag(String dalayFlag) {
			this.dalayFlag = dalayFlag;
		}
		public String getCustomerOrLoan() {
			return customerOrLoan;
		}
		public void setCustomerOrLoan(String customerOrLoan) {
			this.customerOrLoan = customerOrLoan;
		}
		public HttpServletResponse getResponse() {
			return response;
		}
		public void setResponse(HttpServletResponse response) {
			this.response = response;
		}
		public String getDeptId() {
			return deptId;
		}
		public void setDeptId(String deptId) {
			this.deptId = deptId;
		}
		public String getPosType() {
			return posType;
		}
		public void setPosType(String posType) {
			this.posType = posType;
		}
		public String getLoanManagerCastId() {
			return loanManagerCastId;
		}
		public void setLoanManagerCastId(String loanManagerCastId) {
			this.loanManagerCastId = loanManagerCastId;
		}
		public String getEmptId() {
			return emptId;
		}
		public void setEmptId(String emptId) {
			this.emptId = emptId;
		}
		public String getSalesDeptment() {
			return salesDeptment;
		}
		public void setSalesDeptment(String salesDeptment) {
			this.salesDeptment = salesDeptment;
		}
		public String getLoanManagerCast() {
			return loanManagerCast;
		}
		public void setLoanManagerCast(String loanManagerCast) {
			this.loanManagerCast = loanManagerCast;
		}
		public String getSettleFlag() {
			return settleFlag;
		}
		public void setSettleFlag(String settleFlag) {
			this.settleFlag = settleFlag;
		}
		public String getOverdueNum() {
			return overdueNum;
		}
		public void setOverdueNum(String overdueNum) {
			this.overdueNum = overdueNum;
		}
}
