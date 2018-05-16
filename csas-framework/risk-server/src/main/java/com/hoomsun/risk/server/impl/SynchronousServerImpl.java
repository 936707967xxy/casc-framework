/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.server.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.risk.dao.UserApplyMapperRisk;
import com.hoomsun.risk.model.SynchronousLog;
import com.hoomsun.risk.model.UserApply;
import com.hoomsun.risk.model.UserColleague;
import com.hoomsun.risk.model.UserContact;
import com.hoomsun.risk.model.UserOtherLink;
import com.hoomsun.risk.model.UserRelatives;
import com.hoomsun.risk.model.UserSpouse;
import com.hoomsun.risk.server.inter.SynchronousServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月4日 <br>
 * 描述：数据同步的接口
 */
@Service("synchronousServer")
public class SynchronousServerImpl implements SynchronousServerI {

	private static final Logger log = LoggerFactory.getLogger(SynchronousServerImpl.class);

	@Autowired
	private UserApplyMapperRisk userApplyMapper;
	@Autowired
	private MongoTemplate mongoTemplate;

	// 同步申请数据
	@Override
	public Json synchronousData(Date startTime, Date endTime, Integer synType) {
		SynchronousLog synLog = new SynchronousLog();
		synLog.setStartTime(startTime);
		synLog.setEndTime(endTime);
		synLog.setSynType(synType);
		synLog.setSynTypeVal(synLog.getSynTypeVal());
		Date now = new Date();
		synLog.setDoTime(now);
		long start = now.getTime();
		log.info("###【同步数据】  【开始查询申请数据源】【 " + start + " 】开始时间：" + startTime + "结束时间：" + endTime);
		// 获取数据
		List<UserApply> applies = userApplyMapper.findUserApply(startTime, endTime);
		synLog.setSuccess(applies.size());
		long end = new Date().getTime();
		log.info("###【同步数据】【申请数据获取成功,开始同步数据 】【   " + end + " 】【查询耗时:" + (end - start) + "毫秒】");
		synLog.setQueryDuration(end - start);

		for (UserApply userApply : applies) {
			mongoTemplate.save(userApply);
		}

		long iend = new Date().getTime();
		log.info("###【同步数据】【添加申请数据到mongo成功 】【   " + iend + " 】【添加耗时:" + (iend - end) + "毫秒】");
		synLog.setInsertDuration(iend - end);
		// 添加日志
		synLog.setDataType(1);
		synLog.setDataTypeVal(synLog.getDataTypeVal());
		mongoTemplate.save(synLog);
		return new Json(true, "数据同步成功!");
	}

	// 同步联系人数据
	@Override
	public Json synchronousLinkData(Date startTime, Date endTime, Integer synType) {
		SynchronousLog synLog = new SynchronousLog();
		synLog.setStartTime(startTime);
		synLog.setEndTime(endTime);
		synLog.setSynType(synType);
		synLog.setSynTypeVal(synLog.getSynTypeVal());
		Date now = new Date();
		synLog.setDoTime(now);
		long start = now.getTime();
		log.info("###【同步数据】  【开始查询联系人数据源】【 " + start + " 】开始时间：" + startTime + "结束时间：" + endTime);
		// 获取联系人数据
		List<UserContact> contacts = userApplyMapper.findUserContact(startTime, endTime);
		synLog.setSuccess(contacts.size());
		long end = new Date().getTime();
		log.info("###【同步数据】【联系人数据获取成功,开始同步数据 】【   " + end + " 】【查询耗时:" + (end - start) + "毫秒】");
		synLog.setQueryDuration(end - start);

		for (UserContact userContact : contacts) {
			mongoTemplate.save(userContact);
		}

		insertByType(contacts);

		long iend = new Date().getTime();
		log.info("###【同步数据】【添加联系人数据到mongo成功 】【   " + iend + " 】【添加耗时 :" + (iend - end) + " 毫秒】");
		synLog.setInsertDuration(iend - end);
		// 添加日志
		synLog.setDataType(2);
		mongoTemplate.save(synLog);
		return new Json(true, "数据同步成功!");
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月8日 <br>
	 * 描述： 分关系存储数据
	 * 
	 * @param contacts
	 */
	// 1：配偶 2：直系 3：同事 4：其他
	public void insertByType(List<UserContact> contacts) {
		if (contacts == null || contacts.size() < 1) {
			return;
		}
		long start = new Date().getTime();
		log.info("###【同步数据】  【按照联系人类型同步数据】【 " + start + " 】");
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

		long end = new Date().getTime();
		log.info("###【同步数据】  【按照联系人类型同步数据】【耗时:  " + (end - start) + " 毫秒】");
	}

	// 更具申请ID拉取数据
	@Override
	public Json pullByApply(String applyId) {
		SynchronousLog synLog = new SynchronousLog();
		synLog.setObjId(applyId);
		synLog.setSynType(4);
		synLog.setSynTypeVal(synLog.getSynTypeVal());
		Date now = new Date();
		synLog.setDoTime(now);
		long start = now.getTime();
		log.info("###【同步数据】  【开始查询申请数据源】【 " + start + " 】");
		// 获取数据
		UserApply apply = userApplyMapper.findUserApplyCont(applyId);
		long end = new Date().getTime();
		log.info("###【同步数据】【申请数据获取成功,开始同步数据 】【   " + end + " 】【查询耗时:" + (end - start) + "毫秒】");
		synLog.setQueryDuration(end - start);

		// 添加申请数据
		mongoTemplate.save(apply);

		// 添加联系人数据
		List<UserContact> contacts = apply.getContacts();
		if (null != contacts && contacts.size() > 0) {
			for (UserContact userContact : contacts) {
				mongoTemplate.save(userContact);
			}
			insertByType(contacts);
		}

		long iend = new Date().getTime();
		log.info("###【同步数据】【添加申请数据到mongo成功 】【   " + iend + " 】【添加耗时:" + (iend - end) + "毫秒】");
		synLog.setInsertDuration(iend - end);
		// 添加日志
		synLog.setDataType(3);
		synLog.setDataTypeVal(synLog.getDataTypeVal());
		mongoTemplate.save(synLog);
		return new Json(true, "数据同步成功!");
	}

}
