/**

 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

import java.util.Date;
import javax.servlet.http.HttpServletResponse;

/**
 * 作者：Administrator <br>
 * 创建时间：2021年3月16日 <br>
 * 描述：客户划扣记录请求
 */
public class CustomerDeductReq {

	    // 进件编号
		private String loanId;
	     // 客户姓名
		private String castName;
		// 身份证号
		private String cardNo;
		// 划扣渠道
		private String deductChannel;
		//划扣类型
		private String deductType;
		// 划扣状态（成功：0，失败：-1，待定：2 存公中3）
		private String deductState;
		//起始划扣日期
		private String startDate;
		//结束划扣日期
		private String endDate;
		//合同编号
	  	private String conNo;
	  	//客户类型
	  	private String posType;
	  	//前线客服ID
	  	private String managerCastId;
	  	//后援客服ID
	  	private String loanManagerCastId;
	  	//部门ID
	  	private String deptId;
	    //页码
	  	private int page;
	  	//每页条数
	  	private int pageSize;
	  	/**
		 * 查询渠道
		 * 0：前线
		 * 1：后援
		 */
		private String queryType;
		/**
		 * 此单子处于贷后还是前线
		 * 前线：0
		 * 贷后：1
		 */
		private String customerOrLoan;
		//部门ID
		private String salesDeptment;
	    private String empId;
	  	
	  	private HttpServletResponse response;
	  	
	  	
		public String getLoanId() {
			return loanId;
		}
		public void setLoanId(String loanId) {
			this.loanId = loanId;
		}
		public String getCastName() {
			return castName;
		}
		public void setCastName(String castName) {
			this.castName = castName;
		}
		public String getCardNo() {
			return cardNo;
		}
		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}
		public String getDeductChannel() {
			return deductChannel;
		}
		public void setDeductChannel(String deductChannel) {
			this.deductChannel = deductChannel;
		}
		public String getDeductType() {
			return deductType;
		}
		public void setDeductType(String deductType) {
			this.deductType = deductType;
		}
		public String getDeductState() {
			return deductState;
		}
		public void setDeductState(String deductState) {
			this.deductState = deductState;
		}
		public String getConNo() {
			return conNo;
		}
		public void setConNo(String conNo) {
			this.conNo = conNo;
		}
		public String getManagerCastId() {
			return managerCastId;
		}
		public void setManagerCastId(String managerCastId) {
			this.managerCastId = managerCastId;
		}
		public HttpServletResponse getResponse() {
			return response;
		}
		public void setResponse(HttpServletResponse response) {
			this.response = response;
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
		public String getCustomerOrLoan() {
			return customerOrLoan;
		}
		public void setCustomerOrLoan(String customerOrLoan) {
			this.customerOrLoan = customerOrLoan;
		}
		public String getStartDate() {
			return startDate;
		}
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		public String getEndDate() {
			return endDate;
		}
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		public String getDeptId() {
			return deptId;
		}
		public void setDeptId(String deptId) {
			this.deptId = deptId;
		}
		public String getLoanManagerCastId() {
			return loanManagerCastId;
		}
		public void setLoanManagerCastId(String loanManagerCastId) {
			this.loanManagerCastId = loanManagerCastId;
		}
		public String getPosType() {
			return posType;
		}
		public void setPosType(String posType) {
			this.posType = posType;
		}
		public String getSalesDeptment() {
			return salesDeptment;
		}
		public void setSalesDeptment(String salesDeptment) {
			this.salesDeptment = salesDeptment;
		}
		public String getEmpId() {
			return empId;
		}
		public void setEmpId(String empId) {
			this.empId = empId;
		}
}
