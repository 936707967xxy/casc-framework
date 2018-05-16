/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.server.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.risk.dao.UserApplyMapperRisk;
import com.hoomsun.risk.dao.mongo.CallRecordsDao;
import com.hoomsun.risk.dao.mongo.DebitCardDao;
import com.hoomsun.risk.dao.mongo.NearNMonthCallDao;
import com.hoomsun.risk.dao.mongo.PhoneBookDao;
import com.hoomsun.risk.dao.mongo.TopCallRecordsDao;
import com.hoomsun.risk.dao.mongo.TopPhoneBookDao;
import com.hoomsun.risk.dao.mongo.UserApplyDao;
import com.hoomsun.risk.model.CallRecords;
import com.hoomsun.risk.model.DebitCard;
import com.hoomsun.risk.model.NearNMonthCall;
import com.hoomsun.risk.model.PhoneBook;
import com.hoomsun.risk.model.TopCallRecords;
import com.hoomsun.risk.model.TopPhoneBook;
import com.hoomsun.risk.model.UserApply;
import com.hoomsun.risk.model.UserColleague;
import com.hoomsun.risk.model.UserContact;
import com.hoomsun.risk.model.UserOtherLink;
import com.hoomsun.risk.model.UserRelatives;
import com.hoomsun.risk.model.UserSpouse;
import com.hoomsun.risk.server.inter.ClaimAuthDataServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月9日 <br>
 * 描述：认领数据的实现
 */
@Service("claimAuthDataServer")
public class ClaimAuthDataServerImpl implements ClaimAuthDataServerI {
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private UserApplyMapperRisk userApplyMapper;
	@Autowired
	private UserApplyDao userApplyDao;
	@Autowired
	private CallRecordsDao callRecordsDao;
	@Autowired
	private TopCallRecordsDao topCallRecordsDao;
	@Autowired
	private NearNMonthCallDao nearNMonthCallDao;
	@Autowired
	private PhoneBookDao phoneBookDao;
	@Autowired
	private TopPhoneBookDao topPhoneBookDao;
	@Autowired
	private DebitCardDao debitCardDao;

	/**
	 * 认领数据 一、验证mongo中是否存在申请数据
	 */
	@Override
	public Json claimAuthData(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数异常!applyId is null!");
		}

		UserApply userApply = userApplyDao.findByApplyId(applyId);
		if (userApply == null) {
			userApply = userApplyMapper.findUserApplyCont(applyId);
			// 申请信息不存在 拉取申请数据
			if (userApply != null) {
				mongoTemplate.save(userApply);

				// 拉取联系人
				List<UserContact> contacts = userApply.getContacts();

				if (null != contacts && contacts.size() > 0) {
					for (UserContact userContact : contacts) {
						mongoTemplate.save(userContact);
					}
					insertByType(contacts);
				}
			}
		} 

		Date now = new Date();
		String idNumber = userApply.getIdNumber();
		// 一 、认领通话详单
		CallRecords callRecords = callRecordsDao.findByApplyIdField(applyId);// 验证是否有签收的数据
		if (null == callRecords) {// 没有认领授权数据
			// 更具申请号查询为认领的认证数据
			callRecords = callRecordsDao.findByIdNumberField(idNumber);
			if (null != callRecords) {
				Date createDate = callRecords.getCreateDate();
				long limit = (now.getTime() - createDate.getTime()) / (1000 * 60 * 60 * 24);
				if (limit <= 30) {// 30天以内的数据允许认领 否则不能认领
					callRecords.setApplyId(applyId);
					callRecords.setClaimDate(now);
					mongoTemplate.save(callRecords);
				}
			}
		}

		// 二、认领通话记录Top-N
		TopCallRecords topCallRecords = topCallRecordsDao.findByApplyId(applyId);
		if (null == topCallRecords) {
			topCallRecords = topCallRecordsDao.findByIdNumberField(idNumber);
			if (null != topCallRecords) {
				Date createDate = topCallRecords.getCreateDate();
				long limit = (now.getTime() - createDate.getTime()) / (1000 * 60 * 60 * 24);
				if (limit <= 30) {// 30天以内的数据允许认领 否则不能认领
					topCallRecords.setApplyId(applyId);
					topCallRecords.setClaimDate(now);
					mongoTemplate.save(topCallRecords);
				}
			}
		}

		// 三、认领近N月通话电话
		NearNMonthCall nearNMonthCall = nearNMonthCallDao.findByApplyId(applyId);
		if (null == nearNMonthCall) {
			nearNMonthCall = nearNMonthCallDao.findByIdNumberField(idNumber);
			if (null != nearNMonthCall) {
				Date createDate = nearNMonthCall.getCreateDate();
				long limit = (now.getTime() - createDate.getTime()) / (1000 * 60 * 60 * 24);
				if (limit <= 30) {// 30天以内的数据允许认领 否则不能认领
					nearNMonthCall.setApplyId(applyId);
					nearNMonthCall.setClaimDate(now);
					mongoTemplate.save(nearNMonthCall);
				}
			}
		}

		// 四、认领通讯录
		PhoneBook phoneBook = phoneBookDao.findByApplyId(applyId);
		if (null == phoneBook) {
			phoneBook = phoneBookDao.findByIdNumberField(idNumber);
			if (null != phoneBook) {
				Date createDate = phoneBook.getCreateDate();
				long limit = (now.getTime() - createDate.getTime()) / (1000 * 60 * 60 * 24);
				if (limit <= 30) {// 30天以内的数据允许认领 否则不能认领
					phoneBook.setApplyId(applyId);
					phoneBook.setClaimDate(now);
					mongoTemplate.save(phoneBook);
				}
			}
		}

		// 五、认领通讯录通话频次Top N
		TopPhoneBook topPhoneBook = topPhoneBookDao.findByApplyId(applyId);
		if (null == topPhoneBook) {
			topPhoneBook = topPhoneBookDao.findByIdNumberField(idNumber);
			if (null != topPhoneBook) {
				Date createDate = topPhoneBook.getCreateDate();
				long limit = (now.getTime() - createDate.getTime()) / (1000 * 60 * 60 * 24);
				if (limit <= 30) {// 30天以内的数据允许认领 否则不能认领
					topPhoneBook.setApplyId(applyId);
					topPhoneBook.setClaimDate(now);
					mongoTemplate.save(topPhoneBook);
				}
			}
		}

		// 六、储蓄卡认证 认领
		List<DebitCard> debitCards = debitCardDao.findByApplyId(applyId);
		if (null == debitCards || debitCards.size() < 1) {
			debitCards = debitCardDao.findByIdNumberField(idNumber);
			if (null != debitCards && debitCards.size() > 0) {
				for (DebitCard debitCard : debitCards) {
					Date createDate = debitCard.getCreateDate();
					long limit = (now.getTime() - createDate.getTime()) / (1000 * 60 * 60 * 24);
					if (limit <= 30) {// 30天以内的数据允许认领 否则不能认领
						debitCard.setApplyId(applyId);
						debitCard.setClaimDate(now);
						mongoTemplate.save(debitCard);
					}
				}
			}
		}

		// 七、认领征信认证数据

		// 八、学历

		// 九、社保

		// 十、公积金

		// 十一、淘宝

		return new Json(true, "数据认领成功!");
	}

	// 1：配偶 2：直系 3：同事 4：其他
	public void insertByType(List<UserContact> contacts) {
		if (contacts == null || contacts.size() < 1) {
			return;
		}
		List<UserRelatives> relatives = new ArrayList<UserRelatives>();// 2：直系
		List<UserSpouse> spouses = new ArrayList<UserSpouse>();// 1：配偶
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

		if (relatives != null && relatives.size() > 0) {
			for (UserRelatives relative : relatives) {
				mongoTemplate.save(relative);
			}
		}

		if (spouses != null && spouses.size() > 0) {
			for (UserSpouse spouse : spouses) {
				mongoTemplate.save(spouse);
			}
		}

		if (colleagues != null && colleagues.size() > 0) {
			for (UserColleague colleague : colleagues) {
				mongoTemplate.save(colleague);
			}
		}

		if (otherLinks != null && otherLinks.size() > 0) {
			for (UserOtherLink link : otherLinks) {
				mongoTemplate.save(link);
			}
		}
	}
}
