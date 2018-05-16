/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年5月4日 <br>
 * 描述：外访申请分批请求参数
 */
public class VistTaskReq implements Serializable{

	private static final long serialVersionUID = -5737105044219747803L;
	private String loanId;
	private String applyId;
	private String conNo;
	private transient  String   cardNo;
	private  String tel;
	private String castName;
	private String outboundStatus;
	private String isTask;
	private String deptId;
	private String queryType;
	private String queryVisitType;//区分是总查询还是单客户查询
	private String outboundId;
	private String outboundName;
	private String empWorkNum;
	private String empName;
	private String oprationEmpWorkNum;
	private String oprationEmpName;
	private List<NomalCustomerReq>loanIdList;
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
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getConNo() {
		return conNo;
	}
	public void setConNo(String conNo) {
		this.conNo = conNo;
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
	public String getCastName() {
		return castName;
	}
	public void setCastName(String castName) {
		this.castName = castName;
	}
	public String getIsTask() {
		return isTask;
	}
	public void setIsTask(String isTask) {
		this.isTask = isTask;
	}
	public List<NomalCustomerReq> getLoanIdList() {
		return loanIdList;
	}
	public void setLoanIdList(List<NomalCustomerReq> loanIdList) {
		this.loanIdList = loanIdList;
	}
	public String getOutboundStatus() {
		return outboundStatus;
	}
	public void setOutboundStatus(String outboundStatus) {
		this.outboundStatus = outboundStatus;
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
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getOutboundId() {
		return outboundId;
	}
	public void setOutboundId(String outboundId) {
		this.outboundId = outboundId;
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
	public String getOprationEmpWorkNum() {
		return oprationEmpWorkNum;
	}
	public void setOprationEmpWorkNum(String oprationEmpWorkNum) {
		this.oprationEmpWorkNum = oprationEmpWorkNum;
	}
	public String getOprationEmpName() {
		return oprationEmpName;
	}
	public void setOprationEmpName(String oprationEmpName) {
		this.oprationEmpName = oprationEmpName;
	}
	public String getOutboundName() {
		return outboundName;
	}
	public void setOutboundName(String outboundName) {
		this.outboundName = outboundName;
	}
	public String getQueryVisitType() {
		return queryVisitType;
	}
	public void setQueryVisitType(String queryVisitType) {
		this.queryVisitType = queryVisitType;
	}
}
