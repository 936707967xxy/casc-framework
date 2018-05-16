package com.hoomsun.after.api.model.vo;

import java.util.List;

/** 
 * 作者：zwLiu <br>
 * 创建时间：2018年4月25日 <br>
 * 描述：银企直连
 *
 */

public class Bank2BusinessModel {

	private List<TransInfoModel> ntqtsinfz;

	private Ntrbptrsz1Model ntrbptrsz1;

	public List<TransInfoModel> getNtqtsinfz() {
		return ntqtsinfz;
	}

	public void setNtqtsinfz(List<TransInfoModel> ntqtsinfz) {
		this.ntqtsinfz = ntqtsinfz;
	}

	public Ntrbptrsz1Model getNtrbptrsz1() {
		return ntrbptrsz1;
	}

	public void setNtrbptrsz1(Ntrbptrsz1Model ntrbptrsz1) {
		this.ntrbptrsz1 = ntrbptrsz1;
	}

}
