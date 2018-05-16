/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.hoomsun.risk.model.UserColleague;
import com.hoomsun.risk.model.UserOtherLink;
import com.hoomsun.risk.model.UserRelatives;
import com.hoomsun.risk.model.UserSpouse;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月24日 <br>
 * 描述：联系人的数据
 */
public class ContactVO {
	List<UserSpouse> spouses = new ArrayList<UserSpouse>();// 1：配偶
	List<UserRelatives> relatives = new ArrayList<UserRelatives>();// 2：直系
	List<UserColleague> colleagues = new ArrayList<UserColleague>();// 3：同事
	List<UserOtherLink> otherLinks = new ArrayList<UserOtherLink>();// 4：其他

	public List<UserSpouse> getSpouses() {
		return spouses;
	}

	public void setSpouses(List<UserSpouse> spouses) {
		this.spouses = spouses;
	}

	public List<UserRelatives> getRelatives() {
		return relatives;
	}

	public void setRelatives(List<UserRelatives> relatives) {
		this.relatives = relatives;
	}

	public List<UserColleague> getColleagues() {
		return colleagues;
	}

	public void setColleagues(List<UserColleague> colleagues) {
		this.colleagues = colleagues;
	}

	public List<UserOtherLink> getOtherLinks() {
		return otherLinks;
	}

	public void setOtherLinks(List<UserOtherLink> otherLinks) {
		this.otherLinks = otherLinks;
	}
}
