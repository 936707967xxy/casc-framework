package com.hoomsun.after.api.model.param;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title: NomalCustomerReq</p>
 * <p>Description: 正常客户列表请求参数</p>
 * @author xinyuan.xu@hoomsun.com
 * @date 2018年3月13日
 */
public class NomalCustomerReq {

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
	//逾期标示
	private String dalayFlag;
	//结清标示
	private String settleFlag;
	//前线客服ID
	private String managerCastId;
	//客服名称
	private String managerCast;
	//贷后客服ID
	private String loanManagerCastId;
	//贷后客服姓名
	private String loanManagerCast;
	//销售营业部ID
	private String salesDeptment;
	//客户类型
	private String posType;
	/**
	 * 查询类型
	 * 0：正常月还
     *1：逾期月还
     *2：提前月还
	 */
	private String queryType;
	//当前期次
	private String currentPeriod;
	private String deptId;
	private String empId;
	//应还期次
	private String shouldTerm;
	/**
	 * 0：前线
	 *:1：后援
	 */
	private String customerOrLoan;
	private Date updateDate;
	/**
	 * 页码
	 */
	private int page;
	/**
	 * 每页条数
	 */
	private int pageSize;
	
	private HttpServletResponse response;
	
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
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
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
	public String getManagerCastId() {
		return managerCastId;
	}
	public void setManagerCastId(String managerCastId) {
		this.managerCastId = managerCastId;
	}
	public String getConNo() {
		return conNo;
	}
	public void setConNo(String conNo) {
		this.conNo = conNo;
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
	public String getPosType() {
		return posType;
	}
	public void setPosType(String posType) {
		this.posType = posType;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getCurrentPeriod() {
		return currentPeriod;
	}
	public void setCurrentPeriod(String currentPeriod) {
		this.currentPeriod = currentPeriod;
	}
	public String getSalesDeptment() {
		return salesDeptment;
	}
	public void setSalesDeptment(String salesDeptment) {
		this.salesDeptment = salesDeptment;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getLoanManagerCastId() {
		return loanManagerCastId;
	}
	public void setLoanManagerCastId(String loanManagerCastId) {
		this.loanManagerCastId = loanManagerCastId;
	}
	public String getLoanManagerCast() {
		return loanManagerCast;
	}
	public void setLoanManagerCast(String loanManagerCast) {
		this.loanManagerCast = loanManagerCast;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getCustomerOrLoan() {
		return customerOrLoan;
	}
	public void setCustomerOrLoan(String customerOrLoan) {
		this.customerOrLoan = customerOrLoan;
	}
	public String getShouldTerm() {
		return shouldTerm;
	}
	public void setShouldTerm(String shouldTerm) {
		this.shouldTerm = shouldTerm;
	}
	public String getManagerCast() {
		return managerCast;
	}
	public void setManagerCast(String managerCast) {
		this.managerCast = managerCast;
	}
}