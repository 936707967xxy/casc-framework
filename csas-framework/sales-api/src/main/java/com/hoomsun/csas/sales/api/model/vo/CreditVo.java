/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.model.vo;

import com.hoomsun.csas.sales.api.model.UserCis;
import com.hoomsun.csas.sales.api.model.UserPbccre;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月7日 <br>
 * 描述：信用Tab页数据
 */
public class CreditVo {

	private UserPbccre pbccre;// 征信
	private PbccrcChartViewVo pbccrcChart;// 征信可视化数据
	private UserCis userCis;// 上海资信

	public UserPbccre getPbccre() {
		return pbccre;
	}

	public void setPbccre(UserPbccre pbccre) {
		this.pbccre = pbccre;
	}

	public UserCis getUserCis() {
		return userCis;
	}

	public void setUserCis(UserCis userCis) {
		this.userCis = userCis;
	}

	public PbccrcChartViewVo getPbccrcChart() {
		return pbccrcChart;
	}

	public void setPbccrcChart(PbccrcChartViewVo pbccrcChart) {
		this.pbccrcChart = pbccrcChart;
	}

}
