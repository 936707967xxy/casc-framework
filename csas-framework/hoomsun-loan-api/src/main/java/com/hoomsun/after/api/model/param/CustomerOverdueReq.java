/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

import javax.servlet.http.HttpServletResponse;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月21日 <br>
 * 描述：客户减免
 */
public class CustomerOverdueReq {

	        // 进件编号
			private String loanId;
			//合同编号
		  	private String conNo;
		  //前线客服ID
		  	private String managerCastId;
		 // 客户姓名
			private String castName;
			// 身份证号
			private String cardNo;
			// 联系电话
			private String tel;
			//审批状态
			private String overdueStatus;
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
		  	
		  	private HttpServletResponse response;

			public String getLoanId() {
				return loanId;
			}

			public void setLoanId(String loanId) {
				this.loanId = loanId;
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

			public String getTel() {
				return tel;
			}

			public void setTel(String tel) {
				this.tel = tel;
			}

			public String getOverdueStatus() {
				return overdueStatus;
			}

			public void setOverdueStatus(String overdueStatus) {
				this.overdueStatus = overdueStatus;
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

			public HttpServletResponse getResponse() {
				return response;
			}

			public void setResponse(HttpServletResponse response) {
				this.response = response;
			}
}
