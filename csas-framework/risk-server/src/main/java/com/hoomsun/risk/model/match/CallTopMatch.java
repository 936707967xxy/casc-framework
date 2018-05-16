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
 * 描述：同话记录通讯录通话topN匹配结果 当前申请与其他申请的数据匹配
 */
@Document(collection = "RK_MATCH_CALL_TOP")
public class CallTopMatch {
	@Id
	private String applyId;
	private String loanId;
	private String custName;
	@Indexed
	private String idNumber;
	private List<Apply> applies;

	public void addApply(Apply app) {
		if (null == applies) {
			applies = new ArrayList<Apply>();
		}
		applies.add(app);
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

	public List<Apply> getApplies() {
		return applies;
	}

	public void setApplies(List<Apply> applies) {
		this.applies = applies;
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月13日 <br>
	 * 描述： 匹配出的申请
	 */
	public static class Apply {
		private String applyId;
		private String loanId;
		private String custName;
		private String idNumber;
		private List<MatchingRecord> matchResults;

		public void addMatchResult(MatchingRecord record) {
			if (null == matchResults) {
				matchResults = new ArrayList<MatchingRecord>();
			}
			matchResults.add(record);
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

		public List<MatchingRecord> getMatchResults() {
			return matchResults;
		}

		public void setMatchResults(List<MatchingRecord> matchResults) {
			this.matchResults = matchResults;
		}

	}
}
