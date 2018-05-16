/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.model.match;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月13日 <br>
 * 描述：黑名单匹配
 */
@Document(collection = "RK_MATCH_BLACK_LIST")
public class BlackListMatch {
	@Id
	private String applyId;
	private String loanId;
	private String custName;
	@Indexed
	private String idNumber;
	private List<MatchingRecord> matchingRecords;
	
	public void addMatchingRecord(MatchingRecord record) {
		if (null == matchingRecords) {
			matchingRecords = new ArrayList<MatchingRecord>();
		}
		matchingRecords.add(record);
	}
	
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public List<MatchingRecord> getMatchingRecords() {
		return matchingRecords;
	}

	public void setMatchingRecords(List<MatchingRecord> matchingRecords) {
		this.matchingRecords = matchingRecords;
	}

}
