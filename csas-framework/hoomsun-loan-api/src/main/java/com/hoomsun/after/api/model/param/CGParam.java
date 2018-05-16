/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

import java.util.List;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年4月28日 <br>
 * 描述：客户存公申请数据
 */
public class CGParam {

	private String loanId;

	private Integer stream;

	private String corporateBankAccount;

	private List<String> id;

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public Integer getStream() {
		return stream;
	}

	public void setStream(Integer stream) {
		this.stream = stream;
	}

	public List<String> getId() {
		return id;
	}

	public void setId(List<String> id) {
		this.id = id;
	}

	public String getCorporateBankAccount() {
		return corporateBankAccount;
	}

	public void setCorporateBankAccount(String corporateBankAccount) {
		this.corporateBankAccount = corporateBankAccount;
	}

}
