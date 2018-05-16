/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.hoomsun.after.api.model.vo.CustomerTaskAllocationResult;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月14日 <br>
 * 描述：客户任务分配请求参数
 */
public class CustomerTaskAllocationReq implements Serializable{
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年4月4日 <br>
	 * 描述：
	 */
	private static final long serialVersionUID = 7909373681983250085L;
	/**
	 * 合同编号
	 */
	private String conNo;
	/**
	 * 结算标示
	 */
	private String settleFlag;
	/**
	 * 进件编号 (对应APPLYID)
	 */
	private String loanId;
	//身份证号
	private String cardNo;
	/**
	 * 是否分配
	 * 0已分配
	 * 1未分配
	 */
	private String istask;
	/**
	 * 前线客服ID
	 */
	private String managerCastId;
	private String loanManagerCastId;
	private String loanManagerCast;
	//部门Id
	private String deptId;
	//角色名称
	private String roleName;
	
	private String empId;
	//手机号码
	private String tel;
	//客户名称
	private String castName;
	/**
	 * 查询渠道
	 * 0：前线
	 * 1：后援
	 */
	private String queryType;
	/**
	 * 此单子处于贷后还是前线
	 * 0：前线
	 * 1：后援
	 */
	private String customerOrLoan;
	
	private HttpServletResponse response;
	/**
	 * 所属营业部ID
	 */
	private String storeId;
	/**
	 * 被分派件loanId集合
	 */
	private List<NomalCustomerReq>loanIdList;
	/**
	 * 被委托接收件客户集合【empWorkNum,empName】
	 */
	private List<CustomerTaskAllocationResult>appointPeopleId;
	//页码
	private int page;
	//每页条数
	private int pageSize;
	
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
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
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
	public List<CustomerTaskAllocationResult> getAppointPeopleId() {
		return appointPeopleId;
	}
	public void setAppointPeopleId(List<CustomerTaskAllocationResult> appointPeopleId) {
		this.appointPeopleId = appointPeopleId;
	}
	public String getLoanManagerCast() {
		return loanManagerCast;
	}
	public void setLoanManagerCast(String loanManagerCast) {
		this.loanManagerCast = loanManagerCast;
	}
	public List<NomalCustomerReq> getLoanIdList() {
		return loanIdList;
	}
	public void setLoanIdList(List<NomalCustomerReq> loanIdList) {
		this.loanIdList = loanIdList;
	}
	public String getIstask() {
		return istask;
	}
	public void setIstask(String istask) {
		this.istask = istask;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getSettleFlag() {
		return settleFlag;
	}
	public void setSettleFlag(String settleFlag) {
		this.settleFlag = settleFlag;
	}
}
