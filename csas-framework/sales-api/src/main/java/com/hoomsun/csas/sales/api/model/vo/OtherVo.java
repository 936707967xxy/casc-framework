/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.model.vo;

import com.hoomsun.csas.sales.api.model.UserChsi;
import com.hoomsun.csas.sales.api.model.UserHouseFund;
import com.hoomsun.csas.sales.api.model.UserSocialsecurity;
import com.hoomsun.csas.sales.api.model.UserTaobao;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月7日 <br>
 * 描述：其他审核的必须数据
 */
public class OtherVo {

	private UserChsi userChsi = new UserChsi();// 学历
	private UserHouseFund houseFund = new UserHouseFund();// 公积金
	private UserSocialsecurity socialsecurity = new UserSocialsecurity();// 社保
	private UserTaobao taobao = new UserTaobao();// 淘宝

	public UserChsi getUserChsi() {
		return userChsi;
	}

	public void setUserChsi(UserChsi userChsi) {
		this.userChsi = userChsi;
	}

	public UserHouseFund getHouseFund() {
		return houseFund;
	}

	public void setHouseFund(UserHouseFund houseFund) {
		this.houseFund = houseFund;
	}

	public UserSocialsecurity getSocialsecurity() {
		return socialsecurity;
	}

	public void setSocialsecurity(UserSocialsecurity socialsecurity) {
		this.socialsecurity = socialsecurity;
	}

	public UserTaobao getTaobao() {
		return taobao;
	}

	public void setTaobao(UserTaobao taobao) {
		this.taobao = taobao;
	}

}
