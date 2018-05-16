/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

import javax.servlet.http.HttpServletResponse;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月28日 <br>
 * 描述：结清客户请求信息
 */
public class SettleCustomerReq {

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
	//结清标识
	private String settleFlag;
	//是否逾期
	private String dalayFlag;
	
	//还款状态
	private String repayStatus;
	
	/**
	 * 页码
	 */
	private int page;
	/**
	 * 每页条数
	 */
	private int pageSize;
	
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
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public String getRepayStatus() {
		return repayStatus;
	}
	public void setRepayStatus(String repayStatus) {
		this.repayStatus = repayStatus;
	}
	public String getSettleFlag() {
		return settleFlag;
	}
	public void setSettleFlag(String settleFlag) {
		this.settleFlag = settleFlag;
	}
	public String getDalayFlag() {
		return dalayFlag;
	}
	public void setDalayFlag(String dalayFlag) {
		this.dalayFlag = dalayFlag;
	}
}
