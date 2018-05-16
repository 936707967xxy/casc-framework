package com.hoomsun.risk.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.hoomsun.risk.model.match.MatchingRecord;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月4日 <br>
 * 描述： 欺诈勾稽
 */
@Document(collection = "RK_CHEAT_FUNNY")
public class CheatFunny {
	@Id
	private String cfId;// 主键
	private String applyId;// 申请编号 匹配出的申请ID
	private String loanId;// 申请编号
	private String phone;// 电话
	private String idCard;// 证件号
	private String custId;
	private String custName;// 客户姓名
	private Date addData;// 添加时间
	private String procStatus;
	private String procStatusVal;
	private Integer education;
	private String educationVal;
	@Indexed
	private String matchId;// 要匹配的申请ID 本次ID
	@DBRef
	private List<MatchingRecord> matchingRecords;
	
	public void addMatchingRecord(MatchingRecord record) {
		if (null == this.matchingRecords && this.matchingRecords.size() < 1) {
			this.matchingRecords = new ArrayList<MatchingRecord>();
		}
		this.matchingRecords.add(record);
	}
	
	public String getCfId() {
		return cfId;
	}

	public void setCfId(String cfId) {
		this.cfId = cfId == null ? null : cfId.trim();
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId == null ? null : applyId.trim();
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId == null ? null : loanId.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName == null ? null : custName.trim();
	}

	public Date getAddData() {
		return addData;
	}

	public void setAddData(Date addData) {
		this.addData = addData;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public List<MatchingRecord> getMatchingRecords() {
		return matchingRecords;
	}

	public void setMatchingRecords(List<MatchingRecord> matchingRecords) {
		this.matchingRecords = matchingRecords;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getProcStatus() {
		return procStatus;
	}

	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
	}

	public String getProcStatusVal() {
		return procStatusVal;
	}

	public void setProcStatusVal(String procStatusVal) {
		this.procStatusVal = procStatusVal;
	}

	public Integer getEducation() {
		return education;
	}

	public void setEducation(Integer education) {
		this.education = education;
	}

	public String getEducationVal() {
		return educationVal;
	}

	public void setEducationVal(String educationVal) {
		this.educationVal = educationVal;
	}
}