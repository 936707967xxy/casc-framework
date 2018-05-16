/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

import java.util.List;

import com.hoomsun.after.api.model.table.Deduct;
import com.hoomsun.after.api.model.table.Loanbal;
import com.hoomsun.after.api.model.table.Loanbook;
import com.hoomsun.after.api.model.table.OverdueRecord;
import com.hoomsun.after.api.model.table.UserCount;

/**
 * 作者：数据迁移实体 <br>
 * 创建时间：2018年3月21日 <br>
 * 描述：
 */
public class DataAll {

	private UserCount UserCount;

	private Loanbal loanbal;

	private List<Deduct> deduct;

	private List<OverdueRecord> overdueRecord;

	private List<Loanbook> loanbook;

	public UserCount getUserCount() {
		return UserCount;
	}

	public void setUserCount(UserCount userCount) {
		UserCount = userCount;
	}

	public Loanbal getLoanbal() {
		return loanbal;
	}

	public void setLoanbal(Loanbal loanbal) {
		this.loanbal = loanbal;
	}

	public List<Deduct> getDeduct() {
		return deduct;
	}

	public void setDeduct(List<Deduct> deduct) {
		this.deduct = deduct;
	}

	public List<OverdueRecord> getOverdueRecord() {
		return overdueRecord;
	}

	public void setOverdueRecord(List<OverdueRecord> overdueRecord) {
		this.overdueRecord = overdueRecord;
	}

	public List<Loanbook> getLoanbook() {
		return loanbook;
	}

	public void setLoanbook(List<Loanbook> loanbook) {
		this.loanbook = loanbook;
	}

	
	
	
	
}
