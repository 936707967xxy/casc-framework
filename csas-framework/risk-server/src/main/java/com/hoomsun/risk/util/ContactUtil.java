/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.util;

import java.util.ArrayList;
import java.util.List;

import com.hoomsun.risk.model.UserColleague;
import com.hoomsun.risk.model.UserContact;
import com.hoomsun.risk.model.UserOtherLink;
import com.hoomsun.risk.model.UserRelatives;
import com.hoomsun.risk.model.UserSpouse;
import com.hoomsun.risk.model.vo.ContactVO;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月24日 <br>
 * 描述：联系人类型
 */
public class ContactUtil {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月24日 <br>
	 * 描述： 分类出联系人
	 * 
	 * @param contacts
	 *            联系人数据集合
	 * @return
	 */
	public static ContactVO buildByType(List<UserContact> contacts) {
		if (null == contacts || contacts.size() < 1) {
			return null;
		}

		ContactVO contact = new ContactVO();
		List<UserSpouse> spouses = new ArrayList<UserSpouse>();// 1：配偶
		List<UserRelatives> relatives = new ArrayList<UserRelatives>();// 2：直系
		List<UserColleague> colleagues = new ArrayList<UserColleague>();// 3：同事
		List<UserOtherLink> otherLinks = new ArrayList<UserOtherLink>();// 4：其他
		for (UserContact userContact : contacts) {
			Integer type = userContact.getRelationship();// 1：配偶 2：直系 3：同事 4：其他
			Integer source = userContact.getSource();// 0:自动获取 1:客户填写
			type = null == type ? 5 : type;
			if (null != source && 1 == source) {// 1:客户填写
				switch (type) {
				case 1:
					UserSpouse spouse = new UserSpouse();
					spouse.copyFrom(userContact);
					spouses.add(spouse);
					break;
				case 2:
					UserRelatives userRelatives = new UserRelatives();
					userRelatives.copyFrom(userContact);
					relatives.add(userRelatives);
					break;
				case 3:
					UserColleague colleague = new UserColleague();
					colleague.copyFrom(userContact);
					colleagues.add(colleague);
					break;
				case 4:
					UserOtherLink link = new UserOtherLink();
					link.copyFrom(userContact);
					otherLinks.add(link);
					break;
				default:
					break;
				}
			}
		}
		contact.setSpouses(spouses);
		contact.setRelatives(relatives);
		contact.setColleagues(colleagues);
		contact.setOtherLinks(otherLinks);
		return contact;
	}
}
