/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月15日 <br>
 * 描述：通话记录 原版记录
 */
@Document(collection = "RK_CALL_RECORDS")
public class CallRecords {
	@Id
	private String id;
	@Indexed
	private String idNumber;
	@Indexed
	private String phoneNum;
	private String custName;
	private Date createDate;
	private Date claimDate;
	@Indexed
	private String applyId;
	private List<Records> records;

	public void addRecords(Records rc) {
		if (null == records || records.size() < 1) {
			records = new ArrayList<Records>();
		}
		records.add(rc);
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public List<Records> getRecords() {
		return records;
	}

	public void setRecords(List<Records> records) {
		this.records = records;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}

	public class Records {
		@Id
		private String Id;
		private String commUser;
		@Indexed
		private String commPhone;
		private Integer commType;// 0主叫 1 被叫
		private String commTypeVal;
		private Date commDate;// 呼叫时间
		private Long duration;// 通话时长
		private Double money;// 通话费用
		private Integer frequency;// 頻次

		public String getId() {
			return Id;
		}

		public void setId(String id) {
			Id = id;
		}

		public String getCommUser() {
			return commUser;
		}

		public void setCommUser(String commUser) {
			this.commUser = commUser;
		}

		public String getCommPhone() {
			return commPhone;
		}

		public void setCommPhone(String commPhone) {
			this.commPhone = commPhone;
		}

		public Integer getCommType() {
			return commType;
		}

		public void setCommType(Integer commType) {
			this.commType = commType;
		}

		public String getCommTypeVal() {
			return commTypeVal;
		}

		public void setCommTypeVal(String commTypeVal) {
			this.commTypeVal = commTypeVal;
		}

		public Long getDuration() {
			return duration;
		}

		public void setDuration(Long duration) {
			this.duration = duration;
		}

		public Date getCommDate() {
			return commDate;
		}

		public void setCommDate(Date commDate) {
			this.commDate = commDate;
		}

		public Double getMoney() {
			return money;
		}

		public void setMoney(Double money) {
			this.money = money;
		}

		public Integer getFrequency() {
			return frequency;
		}

		public void setFrequency(Integer frequency) {
			this.frequency = frequency;
		}
	}

}
