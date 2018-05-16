/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

import java.util.Date;
import java.util.List;

import com.hoomsun.after.api.model.table.OverdueRecord;

/**
 * 作者：administrator <br>
 * 创建时间：2018年4月3日 <br>
 * 描述：
 */
public class OverdueRecordUpdateParmas {

	private Date nowDate;
	
	private String nomal;

	private List<OverdueRecord> overdueRecords;

	public List<OverdueRecord> getOverdueRecords() {
		return overdueRecords;
	}

	public void setOverdueRecords(List<OverdueRecord> overdueRecords) {
		this.overdueRecords = overdueRecords;
	}

	public Date getNowDate() {
		return nowDate;
	}

	public void setNowDate(Date nowDate) {
		this.nowDate = nowDate;
	}

	public String getNomal() {
		return nomal;
	}

	public void setNomal(String nomal) {
		this.nomal = nomal;
	}

	
	
}
