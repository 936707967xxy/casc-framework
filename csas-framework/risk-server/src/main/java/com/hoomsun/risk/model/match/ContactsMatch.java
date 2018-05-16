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
 * 描述：联系人数据匹配 申请人手机号/联系人手机号与其他进件号码信息的匹配
 */
@Document(collection = "RK_MATCH_CONTACTS")
public class ContactsMatch {
	@Id
	private String applyId;
	private String loanId;
	private String custName;
	@Indexed
	private String idNumber;

	@Indexed
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
	 * 描述： 匹配出的其他进件申请
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((applyId == null) ? 0 : applyId.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Apply other = (Apply) obj;
			if (applyId == null) {
				if (other.applyId != null)
					return false;
			} else if (!applyId.equals(other.applyId))
				return false;
			return true;
		}

	}
}
