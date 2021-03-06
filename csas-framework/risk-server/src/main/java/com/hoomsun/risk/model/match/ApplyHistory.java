/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.model.match;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月13日 <br>
 * 描述：申请历史 欺诈匹配结果
 */
@Document(collection = "RK_MATCH_APPLY_HISTORY")
public class ApplyHistory {
	@Id
	private String applyId;
	private String loanId;
	private String custName;
	@Indexed
	private String idNumber;
	private HashSet<History> histories;

	public void addHistory(History history) {
		if (null == histories) {
			histories = new HashSet<History>();
		}
		histories.add(history);
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

	public HashSet<History> getHistories() {
		return histories;
	}

	public void setHistories(HashSet<History> histories) {
		this.histories = histories;
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月13日 <br>
	 * 描述： 匹配出的申请记录
	 */
	public static class History {
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
			History other = (History) obj;
			if (applyId == null) {
				if (other.applyId != null)
					return false;
			} else if (!applyId.equals(other.applyId))
				return false;
			return true;
		}
	}

}
