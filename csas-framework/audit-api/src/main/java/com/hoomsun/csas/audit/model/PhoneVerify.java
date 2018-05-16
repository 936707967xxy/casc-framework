package com.hoomsun.csas.audit.model;

import java.util.Date;

/**
 * 
 * 作者：liming<br>
 * 创建时间：2017年12月9日 <br>
 * 描述： 电核表
 *
 */
public class PhoneVerify {
	private String pvId;
	private String telNumber;
	private Integer knowVal;
	private String knowStr;
	private Integer telStatus;
	private String telStatusval;
	private String consId;
	private String remark;
	private String applyId;
	private Integer relation;
	private String relationVal;
	private String relationName;
	private Integer ckStatus;
	private String ckStatusVal;
	private String empId;
	private String empName;
	private Date ckDate;

	public String getPvId() {
		return pvId;
	}

	public void setPvId(String pvId) {
		this.pvId = pvId == null ? null : pvId.trim();
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber == null ? null : telNumber.trim();
	}

	public Integer getKnowVal() {
		return knowVal;
	}

	public void setKnowVal(Integer knowVal) {
		this.knowVal = knowVal;
	}

	public String getKnowStr() {
		if (this.knowVal != null) {
			if (1 == this.knowVal) {
				this.knowStr = "知道";
			} else {
				this.knowStr = "不知道";
			}
		}
		return knowStr;
	}

	public void setKnowStr(String knowStr) {
		this.knowStr = knowStr == null ? null : knowStr.trim();
	}

	public Integer getTelStatus() {
		return telStatus;
	}

	public void setTelStatus(Integer telStatus) {
		this.telStatus = telStatus;
	}

	public String getTelStatusval() {
		if (null != this.telStatus) {
			if (0 == this.telStatus) {
				this.telStatusval = "空号";
			} else if (1 == this.telStatus) {
				this.telStatusval = "停机";
			} else if (2 == this.telStatus) {
				this.telStatusval = "无人接听";
			} else if (3 == this.telStatus) {
				this.telStatusval = "无法接通";
			} else if (4 == this.telStatus) {
				this.telStatusval = "关机";
			} else if (5 == this.telStatus) {
				this.telStatusval = "正常";
			} else if (6 == this.telStatus) {
				this.telStatusval = "其他";
			}
		}
		return telStatusval;
	}

	public void setTelStatusval(String telStatusval) {
		this.telStatusval = telStatusval == null ? null : telStatusval.trim();
	}

	public String getConsId() {
		return consId;
	}

	public void setConsId(String consId) {
		this.consId = consId == null ? null : consId.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId == null ? null : applyId.trim();
	}

	public Integer getRelation() {
		return relation;
	}

	public void setRelation(Integer relation) {
		this.relation = relation;
	}

	public String getRelationVal() {
		if (null != this.relation) {
			if (0 == this.relation) {
				this.relationVal = "本人";
			} else if (1 == this.relation) {
				this.relationVal = "配偶";
			} else if (2 == this.relation) {
				this.relationVal = "父母";
			} else if (3 == this.relation) {
				this.relationVal = "子女";
			} else if (4 == this.relation) {
				this.relationVal = "同事";
			} else if (5 == this.relation) {
				this.relationVal = "朋友";
			} else if (6 == this.relation) {
				this.relationVal = "其他";
			}
		}
		return relationVal;
	}

	public void setRelationVal(String relationVal) {
		this.relationVal = relationVal;
	}

	public Integer getCkStatus() {
		return ckStatus;
	}

	public void setCkStatus(Integer ckStatus) {
		this.ckStatus = ckStatus;
	}

	public String getCkStatusVal() {
		if (null != this.ckStatus) {
			if (0 == this.ckStatus) {
				this.ckStatusVal = "异常";
			} else if (1 == this.ckStatus) {
				this.ckStatusVal = "未知";
			} else if (2 == this.ckStatus) {
				this.ckStatusVal = "待核";
			} else if (3 == this.ckStatus) {
				this.ckStatusVal = "正常";
			}
		}
		return ckStatusVal;
	}

	public void setCkStatusVal(String ckStatusVal) {
		this.ckStatusVal = ckStatusVal;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Date getCkDate() {
		return ckDate;
	}

	public void setCkDate(Date ckDate) {
		this.ckDate = ckDate;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

}