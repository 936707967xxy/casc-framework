/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.server.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.risk.dao.mongo.UserApplyDao;
import com.hoomsun.risk.dao.mongo.UserContactDao;
import com.hoomsun.risk.model.UserApply;
import com.hoomsun.risk.model.UserColleague;
import com.hoomsun.risk.model.UserContact;
import com.hoomsun.risk.model.UserOtherLink;
import com.hoomsun.risk.model.UserRelatives;
import com.hoomsun.risk.model.UserSpouse;
import com.hoomsun.risk.model.match.ApplyHistory;
import com.hoomsun.risk.model.match.ApplyHistory.History;
import com.hoomsun.risk.model.match.MatchingRecord;
import com.hoomsun.risk.model.vo.ContactVO;
import com.hoomsun.risk.server.inter.ApplyHistoryServerI;
import com.hoomsun.risk.util.ContactUtil;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月13日 <br>
 * 描述：申请历史数据匹配 业务实现
 */
@Service("applyHistoryServer")
public class ApplyHistoryServerImpl implements ApplyHistoryServerI {
	private final static Logger log = LoggerFactory.getLogger(ApplyHistoryServerImpl.class);
	@Autowired
	private UserApplyDao userApplyDao;
	@Autowired
	private UserContactDao userContactDao;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Json matchApplyHistory(String applyId) {
		log.info("【反欺诈  申请历史勾稽】");
		if (StringUtils.isBlank(applyId)) {
			log.info("【反欺诈  申请历史勾稽】##### 参数异常! applyId is null");
			return new Json(false, "参数异常! applyId is null!");
		}

		UserApply userApply = userApplyDao.findByApplyId(applyId);
		if (null != userApply) {
			String idNumber = userApply.getIdNumber();
			List<UserApply> applyHistory = userApplyDao.findApplyHistory(idNumber, applyId);
			if (null == applyHistory || applyHistory.size() < 1) {
				log.info("【反欺诈  申请历史勾稽  首次申请 无须匹配申请历史】");
				return new Json(false, "匹配失败!没有申请历史!无需匹配!");
			}

			// 开始匹配
			ApplyHistory history = new ApplyHistory();
			history.setApplyId(applyId);
			history.setCustName(userApply.getCustName());
			history.setIdNumber(idNumber);
			history.setLoanId(userApply.getApplyCode());

			// 本次联系人信息 当前申请
			List<UserContact> nowContacts = userContactDao.findByApplyId(userApply.getApplyId());
			// 不同类型的联系人
			ContactVO nowResult = ContactUtil.buildByType(nowContacts);
			for (UserApply hi : applyHistory) {
				match(userApply, nowResult, hi, history);
			}

			// 保存匹配结果数据
			mongoTemplate.save(history);
		}else {
			return new Json(false, "没有获取到申请数据!");
		}
		return new Json(true, "匹配成功");
	}
	
	@Override
	public Json matchApplyHistory(UserApply userApply, List<UserContact> contacts) {
		log.info("【反欺诈  申请历史勾稽】");
		if (null == userApply) {
			log.info("【反欺诈  申请历史勾稽】##### 参数异常! userApply is null");
			return new Json(false, "参数异常! userApply is null!");
		}
		
		String idNumber = userApply.getIdNumber();
		String applyId = userApply.getApplyId();
		List<UserApply> applyHistory = userApplyDao.findApplyHistory(idNumber, applyId);
		if (null == applyHistory || applyHistory.size() < 1) {
			log.info("【反欺诈  申请历史勾稽  首次申请 无须匹配申请历史】");
			return new Json(false, "匹配失败!没有申请历史!无需匹配!");
		}

		// 开始匹配
		ApplyHistory history = new ApplyHistory();
		history.setApplyId(applyId);
		history.setCustName(userApply.getCustName());
		history.setIdNumber(idNumber);
		history.setLoanId(userApply.getApplyCode());

		// 本次联系人信息 当前申请
		if (null == contacts) {
			contacts = userContactDao.findByApplyId(userApply.getApplyId());
		}
		
		// 不同类型的联系人
		ContactVO nowResult = ContactUtil.buildByType(contacts);
		for (UserApply hi : applyHistory) {
			match(userApply, nowResult, hi, history);
		}
		
		// 保存匹配结果数据
		mongoTemplate.save(history);
		return new Json(true, "匹配成功");
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月13日 <br>
	 * 描述： 数据匹配
	 * 
	 * @param nowApply
	 *            本次申请
	 * @param nowResult
	 *            本次申请的联系人信息 已经分类的数据
	 * @param hiApply
	 *            历史申请
	 * @param history
	 *            匹配结果
	 */
	private void match(UserApply userApply, ContactVO nowResult, UserApply lastApply, ApplyHistory history) {
		History hi = new History();
		hi.setApplyId(lastApply.getApplyId());
		hi.setCustName(lastApply.getCustName());
		hi.setIdNumber(lastApply.getIdNumber());
		hi.setLoanId(lastApply.getApplyCode());
		history.addHistory(hi);

		// 1.同一申请人婚姻状况与本次不同
		Integer nowMarital = userApply.getMarital();
		Integer marital = lastApply.getMarital();
		if (nowMarital != marital) {
			MatchingRecord record = new MatchingRecord();
			record.setMatchType("100001");// 匹配类型
			StringBuffer remark = new StringBuffer();
			remark.append(":");
			remark.append(marital);
			remark.append("#");
			remark.append(userApply.getMaritalVal());
			remark.append(",本次:");
			remark.append(nowMarital);
			remark.append("#");
			remark.append(lastApply.getMaritalVal());
			record.setMatchTypeVal("申请人婚姻状况与本次不同");
			record.setRemarks(remark.toString());
			record.setMatchLevel(1);// 匹配等级
			record.setMatchLevelVal("黄");
			hi.addMatchResult(record);
		}

		// 2.同一申请人学历与本次不同
		Integer nowEducation = userApply.getEducation();
		Integer education = lastApply.getEducation();
		if (nowEducation != education) {
			MatchingRecord record = new MatchingRecord();
			record.setMatchType("100002");// 匹配类型
			record.setMatchTypeVal("申请人学历与本次不同");
			StringBuffer remark = new StringBuffer();
			remark.append(":");
			remark.append(education);
			remark.append("#");
			remark.append(userApply.getEducationVal());
			remark.append(",本次:");
			remark.append(nowEducation);
			remark.append("#");
			remark.append(lastApply.getEducationVal());
			record.setRemarks(remark.toString());
			record.setMatchLevel(1);// 匹配等级
			record.setMatchLevelVal("黄");
			hi.addMatchResult(record);
		}

		// 3.同一申请人未成年子女及供养亲属人数与本次不同
		Integer nowRaisePerson = userApply.getRaisePerson();// 供养
		Integer raisePerson = lastApply.getRaisePerson();// 供养
		if (nowRaisePerson != raisePerson) {
			MatchingRecord record = new MatchingRecord();

			record.setMatchType("100003");// 匹配类型
			record.setMatchTypeVal("申请人供养亲属人数与本次不同");
			StringBuffer remark = new StringBuffer();
			remark.append(":");
			remark.append(raisePerson);
			remark.append(",本次:");
			remark.append(nowRaisePerson);
			record.setRemarks(remark.toString());
			record.setMatchLevel(1);// 匹配等级
			record.setMatchLevelVal("黄");
			hi.addMatchResult(record);
		}

		Integer nowChildNumber = userApply.getChildNumber();
		Integer childNumber = lastApply.getChildNumber();
		if (nowChildNumber != childNumber) {
			MatchingRecord record = new MatchingRecord();

			record.setMatchType("100004");// 匹配类型
			record.setMatchTypeVal("申请人子女人数与本次不同");
			StringBuffer remark = new StringBuffer();
			remark.append(":");
			remark.append(childNumber);
			remark.append(",本次:");
			remark.append(nowChildNumber);
			record.setRemarks(remark.toString());
			record.setMatchLevel(1);// 匹配等级
			record.setMatchLevelVal("黄");
			hi.addMatchResult(record);
		}

		// 4.同一申请人手机号码与本次不同
		String nowPhone = userApply.getMobile();
		String phone = lastApply.getMobile();
		if (nowPhone != phone) {
			MatchingRecord record = new MatchingRecord();
			record.setMatchType("100005");// 匹配类型
			record.setMatchTypeVal("申请人手机号码与本次不同");
			StringBuffer remark = new StringBuffer();
			remark.append(":");
			remark.append(phone);
			remark.append(",本次:");
			remark.append(nowPhone);
			record.setRemarks(remark.toString());
			record.setMatchLevel(3);// 匹配等级
			record.setMatchLevelVal("红");
			hi.addMatchResult(record);
		}

		// 获取前一次申请的申请联系人数据
		List<UserContact> contacts = userContactDao.findByApplyId(lastApply.getApplyId());
		if (null != nowResult && !contacts.isEmpty()) {
			ContactVO result = ContactUtil.buildByType(contacts);
			// 5.同一申请人配偶姓名与本次不同
			// 当前申请的配偶数据
			List<UserSpouse> nowSpouses = nowResult.getSpouses();// 1：配偶 当前
			List<UserSpouse> spouses = result.getSpouses();// 1：配偶
			if ((nowSpouses.isEmpty() && !spouses.isEmpty()) || (spouses.isEmpty() && !nowSpouses.isEmpty())) {// 本次配偶为空
																												// 前一次不为空
				MatchingRecord record = new MatchingRecord();

				record.setMatchType("100006");// 匹配类型
				record.setMatchTypeVal("申请人配偶姓名与本次不同");
				StringBuffer remark = new StringBuffer();
				remark.append(":");
				if (spouses.isEmpty()) {
					remark.append("未知");
				} else {
					remark.append(spouses.get(0).getLinkName());
				}
				remark.append(",本次:");
				if (nowSpouses.isEmpty()) {
					remark.append("未知");
				} else {
					remark.append(nowSpouses.get(0).getLinkName());
				}
				record.setRemarks(remark.toString());
				record.setMatchLevel(3);// 匹配等级
				record.setMatchLevelVal("红");
				hi.addMatchResult(record);
			} else {
				String nowLinkName = nowSpouses.get(0).getLinkName();
				String linkName = spouses.get(0).getLinkName();
				if (StringUtils.equals(nowLinkName, linkName)) {
					MatchingRecord record = new MatchingRecord();
					record.setMatchType("100006");// 匹配类型
					record.setMatchTypeVal("申请人配偶姓名与本次不同");
					StringBuffer remark = new StringBuffer();
					remark.append(":");
					remark.append(linkName);
					remark.append(",本次:");
					remark.append(nowLinkName);
					record.setRemarks(remark.toString());
					record.setMatchLevel(3);// 匹配等级
					record.setMatchLevelVal("红");
					hi.addMatchResult(record);
				}

				// 6.同一申请人配偶手机号码与本次不同
				String nowTel = nowSpouses.get(0).getLinkPhone();
				String tel = spouses.get(0).getLinkPhone();
				if (StringUtils.equals(nowTel, tel)) {
					MatchingRecord record = new MatchingRecord();

					record.setMatchType("100007");// 匹配类型
					record.setMatchTypeVal("申请人配偶手机号码与本次不同");
					StringBuffer remark = new StringBuffer();
					remark.append(":");
					remark.append(tel);
					remark.append(",本次:");
					remark.append(nowTel);
					record.setRemarks(remark.toString());
					record.setMatchLevel(3);// 匹配等级
					record.setMatchLevelVal("红");
					hi.addMatchResult(record);
				}
			}

			// 直系
			List<UserRelatives> nowRelatives = nowResult.getRelatives();// 2：直系
			List<UserRelatives> relatives = result.getRelatives();// 2：直系
			if (!nowRelatives.isEmpty() && !relatives.isEmpty()) {
				List<String> nowNames = new ArrayList<String>();
				List<String> nowPhones = new ArrayList<String>();

				for (UserRelatives userRelatives : nowRelatives) {
					nowNames.add(userRelatives.getLinkName());
					nowPhones.add(userRelatives.getLinkPhone());
				}

				// 7.同一申请人亲属姓名与本次不同
				for (UserRelatives userRelatives : relatives) {
					String name = userRelatives.getLinkName();
					String lastPhone = userRelatives.getLinkPhone();
					if (!nowNames.contains(name)) {
						MatchingRecord record = new MatchingRecord();
						record.setMatchType("100008");// 匹配类型
						record.setMatchTypeVal("申请人亲属姓名与本次不同");
						StringBuffer remark = new StringBuffer();
						remark.append(":");
						remark.append(name);
						remark.append(",本次:");
						remark.append(nowNames.toString());
						record.setRemarks(remark.toString());
						record.setMatchLevel(3);// 匹配等级
						record.setMatchLevelVal("红");
						hi.addMatchResult(record);
					}

					// 9.同一申请人亲属手机号码与本次不同
					if (!nowPhones.contains(lastPhone)) {
						MatchingRecord record = new MatchingRecord();

						record.setMatchType("100010");// 匹配类型
						record.setMatchTypeVal("申请人亲属手机号码与本次不同");
						StringBuffer remark = new StringBuffer();
						remark.append(":");
						remark.append(lastPhone);
						remark.append(",本次:");
						remark.append(nowPhones.toString());
						record.setRemarks(remark.toString());
						record.setMatchLevel(3);// 匹配等级
						record.setMatchLevelVal("红");
						hi.addMatchResult(record);
					}
				}

				// 8.同一申请人亲属关系与本次不同
				// 1：配偶 2：亲属 3：同事 4：其他
				// 找出联系相同名字的亲属
				List<String> lastNames = new ArrayList<String>();
				for (UserRelatives contact : relatives) {
					lastNames.add(contact.getLinkName());
				}

				for (UserRelatives contact : nowRelatives) {
					String name = contact.getLinkName();
					if (!lastNames.contains(name)) {// 前一次的直系在本次的联系人中 但是关系不是直系
						MatchingRecord record = new MatchingRecord();
						record.setMatchType("100009");// 匹配类型
						record.setMatchTypeVal("申请人亲属关系与本次不同");
						StringBuffer remark = new StringBuffer();
						remark.append(name);
						remark.append(",本次关系亲属");
						record.setRemarks(remark.toString());
						record.setMatchLevel(3);// 匹配等级
						record.setMatchLevelVal("红");
						hi.addMatchResult(record);
					}
				}
			}

			List<UserColleague> nowColleagues = nowResult.getColleagues();// 3：同事
			List<UserColleague> colleagues = result.getColleagues();// 3：同事
			if (!nowColleagues.isEmpty() && !colleagues.isEmpty()) {
				List<String> lastNames = new ArrayList<String>();
				List<String> lastPhone = new ArrayList<String>();

				for (UserColleague userColleague : colleagues) {
					lastNames.add(userColleague.getLinkName());
					lastPhone.add(userColleague.getLinkPhone());
				}

				// 10.同一申请人同事姓名与本次不同
				for (UserColleague colleague : nowColleagues) {
					String name = colleague.getLinkName();
					String nowCollePhone = colleague.getLinkPhone();
					if (!lastNames.contains(name)) {
						MatchingRecord record = new MatchingRecord();

						record.setMatchType("100011");// 匹配类型
						record.setMatchTypeVal("申请人同事姓名与本次不同");
						StringBuffer remark = new StringBuffer();
						remark.append(":");
						remark.append(lastNames.toString());
						remark.append(",本次:" + name);
						record.setRemarks(remark.toString());
						record.setMatchLevel(2);// 匹配等级
						record.setMatchLevelVal("黄");
						hi.addMatchResult(record);
					} else {// 相同的姓名
							// 11.同一申请人同事手机号码与本次不同
						if (!lastPhone.contains(nowCollePhone)) {
							MatchingRecord record = new MatchingRecord();
							record.setMatchType("100012");// 匹配类型
							record.setMatchTypeVal("申请人同事姓名与本次不同");
							StringBuffer remark = new StringBuffer();
							remark.append(":");
							remark.append(lastPhone.toString());
							remark.append(",本次:" + nowCollePhone);
							record.setRemarks(remark.toString());
							record.setMatchLevel(2);// 匹配等级
							record.setMatchLevelVal("黄");
							hi.addMatchResult(record);
						}
					}
				}
			}

			List<UserOtherLink> nowOtherLinks = nowResult.getOtherLinks();// 4：其他
			List<UserOtherLink> otherLinks = result.getOtherLinks();// 4：其他
			if (!nowOtherLinks.isEmpty() && !otherLinks.isEmpty()) {
				List<String> lastNames = new ArrayList<String>();
				List<String> lastPhones = new ArrayList<String>();
				for (UserOtherLink links : otherLinks) {
					lastNames.add(links.getLinkName());
					lastPhones.add(links.getLinkPhone());
				}

				for (UserOtherLink otherLink : nowOtherLinks) {
					String nowName = otherLink.getLinkName();
					String nowOthPhone = otherLink.getLinkPhone();
					// 12.同一申请人其他联系人姓名与本次不同
					if (!lastNames.contains(nowName)) {
						MatchingRecord record = new MatchingRecord();
						record.setMatchType("100013");// 匹配类型
						record.setMatchTypeVal("申请人同事姓名与本次不同");
						StringBuffer remark = new StringBuffer();
						remark.append(":");
						remark.append(lastNames.toString());
						remark.append(",本次:" + nowName);
						record.setRemarks(remark.toString());
						record.setMatchLevel(2);// 匹配等级
						record.setMatchLevelVal("黄");
						hi.addMatchResult(record);
					} else {// 相同的姓名
							// 13.同一申请人其他联系人手机号码与本次不同
						if (!lastPhones.contains(nowOthPhone)) {
							MatchingRecord record = new MatchingRecord();

							record.setMatchType("100014");// 匹配类型
							record.setMatchTypeVal("申请人同事姓名与本次不同");
							StringBuffer remark = new StringBuffer();
							remark.append(":");
							remark.append(lastPhones.toString());
							remark.append(",本次:" + nowOthPhone);
							record.setRemarks(remark.toString());
							record.setMatchLevel(2);// 匹配等级
							record.setMatchLevelVal("黄");
							hi.addMatchResult(record);
						}
					}
				}

			}

		}

		// 14.同一申请人审核结果
		// String nowStatus = userApply.getProcStatus();
		String status = lastApply.getProcStatus();
		if (!StringUtils.isNotBlank(status)) {
			MatchingRecord record = new MatchingRecord();

			record.setMatchType("100015");// 匹配类型
			StringBuffer remark = new StringBuffer();
			remark.append(":");
			remark.append(status);
			remark.append("#");
			remark.append(userApply.getProcStatusVal());
			// remark.append(",本次:");
			// remark.append(nowStatus);
			// remark.append("#");
			// remark.append(lastApply.getProcStatusVal());
			record.setMatchTypeVal("申请人婚姻状况与本次不同");
			record.setRemarks(remark.toString());
			record.setMatchLevel(3);// 匹配等级
			record.setMatchLevelVal("红");
			hi.addMatchResult(record);
		}

		// 15.同一申请人申请单位名称与本次不同
		String nowComName = userApply.getComName();
		String comName = lastApply.getComName();
		if (!StringUtils.equals(comName, nowComName)) {
			MatchingRecord record = new MatchingRecord();

			record.setMatchType("100016");// 匹配类型
			StringBuffer remark = new StringBuffer();
			remark.append(":");
			remark.append(comName);
			remark.append("#");
			remark.append(",本次:");
			remark.append(nowComName);
			record.setMatchTypeVal("申请人单位名称与本次不同");
			record.setRemarks(remark.toString());
			record.setMatchLevel(3);// 匹配等级
			record.setMatchLevelVal("红");
			hi.addMatchResult(record);
		}

		// 16.同一申请人申请单位地址与本次不同
		String nowComAddr = userApply.getComAddr();
		String comAddr = lastApply.getComAddr();
		if (!StringUtils.equals(nowComAddr, comAddr)) {
			MatchingRecord record = new MatchingRecord();

			record.setMatchType("100017");// 匹配类型
			StringBuffer remark = new StringBuffer();
			remark.append(":");
			remark.append(comAddr);
			remark.append("#");
			remark.append(",本次:");
			remark.append(nowComAddr);
			record.setMatchTypeVal("申请人单位地址与本次不同");
			record.setRemarks(remark.toString());
			record.setMatchLevel(3);// 匹配等级
			record.setMatchLevelVal("红");
			hi.addMatchResult(record);
		}

		// 17.同一申请人申请单位电话与本次不同
		String nowComTel = userApply.getComTel();
		String comTel = lastApply.getComTel();
		if (!StringUtils.equals(nowComTel, comTel)) {
			MatchingRecord record = new MatchingRecord();

			record.setMatchType("100018");// 匹配类型
			StringBuffer remark = new StringBuffer();
			remark.append(":");
			remark.append(comTel);
			remark.append("#");
			remark.append(",本次:");
			remark.append(nowComTel);
			record.setMatchTypeVal("申请人单位电话与本次不同");
			record.setRemarks(remark.toString());
			record.setMatchLevel(3);// 匹配等级
			record.setMatchLevelVal("红");
			hi.addMatchResult(record);
		}

		// 18.同一申请人住宅地址与本次不同
		String nowHousAddr = userApply.getHouseAddr();
		String housAddr = lastApply.getHouseAddr();
		if (!StringUtils.equals(nowHousAddr, housAddr)) {
			MatchingRecord record = new MatchingRecord();

			record.setMatchType("100019");// 匹配类型
			StringBuffer remark = new StringBuffer();
			remark.append(":");
			remark.append(housAddr);
			remark.append(",本次:");
			remark.append(nowHousAddr);
			record.setMatchTypeVal("申请人住宅地址与本次不同");
			record.setRemarks(remark.toString());
			record.setMatchLevel(3);// 匹配等级
			record.setMatchLevelVal("红");
			hi.addMatchResult(record);
		}

		// 19.同一申请人住宅电话与本次不同
		String nowHousTel = userApply.getHouseTel();
		String housTel = lastApply.getHouseTel();
		if (!StringUtils.equals(nowHousTel, housTel)) {
			MatchingRecord record = new MatchingRecord();

			record.setMatchType("100020");// 匹配类型
			StringBuffer remark = new StringBuffer();
			remark.append(":");
			remark.append(housTel);
			remark.append(",本次:");
			remark.append(nowHousTel);
			record.setMatchTypeVal("申请人住宅电话与本次不同");
			record.setRemarks(remark.toString());
			record.setMatchLevel(2);// 匹配等级
			record.setMatchLevelVal("橙");
			hi.addMatchResult(record);
		}

		// 20.同一申请人“是否有社保/公积金”与本次不同
		Integer nowSocial = userApply.getSocial();
		Integer social = lastApply.getSocial();
		if (!StringUtils.equals(nowHousTel, housTel)) {
			MatchingRecord record = new MatchingRecord();
			record.setMatchType("100021");// 匹配类型
			StringBuffer remark = new StringBuffer();
			remark.append(":");
			remark.append(social);
			remark.append(userApply.getSocialVal());
			remark.append(",本次:");
			remark.append(nowSocial);
			remark.append(lastApply.getSocialVal());
			record.setMatchTypeVal("申请人'是否有社保/公积金'与本次不同");
			record.setRemarks(remark.toString());
			record.setMatchLevel(2);// 匹配等级
			record.setMatchLevelVal("橙");
			hi.addMatchResult(record);
		}
	}

	@Override
	public ApplyHistory findByApplyId(String applyId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(applyId));
		ApplyHistory history = mongoTemplate.findOne(query, ApplyHistory.class);
		return history;
	}
	
}
