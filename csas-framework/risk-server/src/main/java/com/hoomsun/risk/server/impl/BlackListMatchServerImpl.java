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
import com.hoomsun.risk.dao.mongo.BlackDao;
import com.hoomsun.risk.dao.mongo.NearNMonthCallDao;
import com.hoomsun.risk.dao.mongo.PhoneBookDao;
import com.hoomsun.risk.dao.mongo.UserApplyDao;
import com.hoomsun.risk.dao.mongo.UserContactDao;
import com.hoomsun.risk.model.BlackList;
import com.hoomsun.risk.model.NearNMonthCall;
import com.hoomsun.risk.model.PhoneBook;
import com.hoomsun.risk.model.UserApply;
import com.hoomsun.risk.model.UserColleague;
import com.hoomsun.risk.model.UserContact;
import com.hoomsun.risk.model.UserOtherLink;
import com.hoomsun.risk.model.UserRelatives;
import com.hoomsun.risk.model.UserSpouse;
import com.hoomsun.risk.model.PhoneBook.Contact;
import com.hoomsun.risk.model.match.ApplyHistory;
import com.hoomsun.risk.model.match.BlackListMatch;
import com.hoomsun.risk.model.match.MatchingRecord;
import com.hoomsun.risk.model.vo.ContactVO;
import com.hoomsun.risk.server.inter.BlackListMatchServerI;
import com.hoomsun.risk.util.ContactUtil;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月24日 <br>
 * 描述：黑名单匹配的业务实现
 */
@Service("blackListMatchServer")
public class BlackListMatchServerImpl implements BlackListMatchServerI {
	private final static Logger log = LoggerFactory.getLogger(BlackListMatchServerImpl.class);
	@Autowired
	private UserApplyDao userApplyDao;
	@Autowired
	private UserContactDao userContactDao;
	@Autowired
	private BlackDao blackDao;
	@Autowired
	private NearNMonthCallDao nearNMonthCallDao;
	@Autowired
	private PhoneBookDao phoneBookDao;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public Json matchBlack(String applyId) {
		log.info("【反欺诈  黑名单数据匹配】");
		if (StringUtils.isBlank(applyId)) {
			log.info("【反欺诈  黑名单数据匹配】##### 参数异常! applyId is null");
			return new Json(false, "参数异常! applyId is null!");
		}

		UserApply userApply = userApplyDao.findByApplyId(applyId);
		if (userApply != null) {
			String idNumber = userApply.getIdNumber();
			// 本次联系人信息 当前申请
			List<UserContact> nowContacts = userContactDao.findByApplyId(userApply.getApplyId());
			ContactVO nowResult = ContactUtil.buildByType(nowContacts);

			// 开始匹配黑名单数据
			BlackListMatch match = new BlackListMatch();
			match.setApplyId(applyId);
			match.setCustName(userApply.getCustName());
			match.setIdNumber(idNumber);
			match.setLoanId(userApply.getApplyCode());

			match(userApply, nowResult, match);
			
			//保存匹配结果数据
			mongoTemplate.save(match);
		} else {
			return new Json(false, "没有获取到申请数据!");
		}

		return new Json(true, "黑名单匹配成功!");
	}
	
	@Override
	public Json matchApplyHistory(UserApply userApply, List<UserContact> contacts) {
		log.info("【反欺诈  黑名单数据匹配】");
		if (null == userApply) {
			log.info("【反欺诈  黑名单数据匹配】##### 参数异常! userApply is null");
			return new Json(false, "参数异常! userApply is null!");
		}
		
		String idNumber = userApply.getIdNumber();
		// 本次联系人信息 当前申请
		if (null == contacts) {
			contacts = userContactDao.findByApplyId(userApply.getApplyId());
		}
		ContactVO nowResult = ContactUtil.buildByType(contacts);

		// 开始匹配黑名单数据
		BlackListMatch match = new BlackListMatch();
		match.setApplyId(userApply.getApplyId());
		match.setCustName(userApply.getCustName());
		match.setIdNumber(idNumber);
		match.setLoanId(userApply.getApplyCode());

		match(userApply, nowResult, match);
		
		//保存匹配结果数据
		mongoTemplate.save(match);
		
		return new Json(true, "黑名单匹配成功!");
	}

	private void match(UserApply userApply, ContactVO nowResult, BlackListMatch match) {
		String applyId = userApply.getApplyId();
		// 21 黑名单关联 申请人通话记录中的手机号码在黑名单中（线上+线下名单）(3个月)
		NearNMonthCall near = nearNMonthCallDao.findByApplyId(applyId);
		if (near != null) {
			String[] phones = near.getPhones();
			if (phones != null && phones.length > 0) {
				List<BlackList> blackLists = blackDao.findMobile(phones);
				if (!blackLists.isEmpty()) {
					MatchingRecord record = new MatchingRecord();
					record.setMatchType("200001");// 匹配类型
					StringBuffer remark = new StringBuffer();
					remark.append("黑名单:");
					remark.append(buildBlackPhone(blackLists));
					record.setMatchTypeVal("申请人通话记录中的手机号码在黑名单中");
					record.setRemarks(remark.toString());
					record.setMatchLevel(3);// 匹配等级
					record.setMatchLevelVal("红");
					match.addMatchingRecord(record);
				}
			}
		}

		// 22 申请人通讯录中的手机号码在黑名单中（线上+线下名单）
		PhoneBook book = phoneBookDao.findByApplyId(applyId);
		List<Contact> contacts = book.getContacts();
		if(contacts != null && contacts.size() > 0){
			List<String> bookPhones = new ArrayList<String>();
			for (com.hoomsun.risk.model.PhoneBook.Contact contact : book.getContacts()) {
				bookPhones.add(contact.getPhone());
			}
			List<BlackList> blackLists = blackDao.findMobile(bookPhones.toArray(new String[] {}));
			if (!blackLists.isEmpty()) {
				MatchingRecord record = new MatchingRecord();
				
				record.setMatchType("200002");// 匹配类型
				StringBuffer remark = new StringBuffer();
				remark.append("黑名单:");
				remark.append(buildBlackPhone(blackLists));
				record.setMatchTypeVal("申请人通讯录中的手机号码在黑名单中");
				record.setRemarks(remark.toString());
				record.setMatchLevel(3);// 匹配等级
				record.setMatchLevelVal("红");
				match.addMatchingRecord(record);
			}


		// 23 申请人手机号与黑名单库中电话前9位相同且最后两位不同，或前10位相同且最后一位不同
		String applyPhone = userApply.getMobile();
		String nine = "";
		String ten = "";
		if (applyPhone.length() > 8) {
			nine = applyPhone.substring(0, 8);
		}

		if (applyPhone.length() > 9) {
			ten = applyPhone.substring(0, 9);
		}

		String reg = "";
		if (!StringUtils.isAllBlank(nine, ten)) {
			reg = "/^(" + nine + "|" + ten + ")/";
		} else {
			if (!StringUtils.isBlank(nine)) {
				reg = "/^" + nine + "/";
			}

			if (!StringUtils.isBlank(ten)) {
				reg = "/^" + ten + "/";
			}
		}

		blackLists = blackDao.findByMobileReg(reg);
		if (!blackLists.isEmpty()) {
			MatchingRecord record = new MatchingRecord();

			record.setMatchType("200003");// 匹配类型
			StringBuffer remark = new StringBuffer();
			remark.append("黑名单:");
			remark.append(buildBlackPhone(blackLists));
			record.setMatchTypeVal("申请人手机号与黑名单库中电话前9位或前10位相同");
			record.setRemarks(remark.toString());
			record.setMatchLevel(3);// 匹配等级
			record.setMatchLevelVal("红");
			match.addMatchingRecord(record);
		}
	}

		// 24 配偶手机号码存在于系统黑名单中
		List<UserSpouse> spouses = nowResult.getSpouses();// 配偶
		if (!spouses.isEmpty()) {
			List<String> phones = new ArrayList<>();
			for (UserSpouse spouse : spouses) {
				phones.add(spouse.getLinkPhone());
			}

			List<BlackList> black = blackDao.findTels(phones);
			if (!black.isEmpty()) {
				MatchingRecord record = new MatchingRecord();

				record.setMatchType("200004");// 匹配类型
				StringBuffer remark = new StringBuffer();
				remark.append("黑名单:");
				remark.append(buildBlackPhone(black));
				record.setMatchTypeVal("配偶手机号码存在于系统黑名单中");
				record.setRemarks(remark.toString());
				record.setMatchLevel(2);// 匹配等级
				record.setMatchLevelVal("橙");
				match.addMatchingRecord(record);
			}
		}

		// 25 亲属手机号码存在与系统黑名单中
		List<UserRelatives> relatives = nowResult.getRelatives();
		if (!relatives.isEmpty()) {
			List<String> phones = new ArrayList<>();
			for (UserRelatives userRelatives : relatives) {
				phones.add(userRelatives.getLinkPhone());
			}

			List<BlackList> black = blackDao.findTels(phones);
			if (!black.isEmpty()) {
				MatchingRecord record = new MatchingRecord();

				record.setMatchType("200005");// 匹配类型
				StringBuffer remark = new StringBuffer();
				remark.append("黑名单:");
				remark.append(buildBlackPhone(black));
				record.setMatchTypeVal("亲属手机号码存在与系统黑名单中");
				record.setRemarks(remark.toString());
				record.setMatchLevel(2);// 匹配等级
				record.setMatchLevelVal("橙");
				match.addMatchingRecord(record);
			}
		}

		// 26 同事1手机号码存在与系统黑名单中
		// 27 同事2手机号码存在与系统黑名单中
		List<UserColleague> colleagues = nowResult.getColleagues();
		if (!colleagues.isEmpty()) {
			List<String> phones = new ArrayList<>();
			for (UserColleague colleague : colleagues) {
				phones.add(colleague.getLinkPhone());
			}

			List<BlackList> black = blackDao.findTels(phones);
			if (!black.isEmpty()) {
				MatchingRecord record = new MatchingRecord();

				record.setMatchType("200006");// 匹配类型200007
				StringBuffer remark = new StringBuffer();
				remark.append("黑名单:");
				remark.append(buildBlackPhone(black));
				record.setMatchTypeVal("同事手机号码存在与系统黑名单中");
				record.setRemarks(remark.toString());
				record.setMatchLevel(1);// 匹配等级
				record.setMatchLevelVal("黄");
				match.addMatchingRecord(record);
			}
		}

		// 28 其他联系人手机号码存在与系统黑名单中
		List<UserOtherLink> otherLinks = nowResult.getOtherLinks();
		if (!otherLinks.isEmpty()) {
			List<String> phones = new ArrayList<>();
			for (UserOtherLink otherLink : otherLinks) {
				phones.add(otherLink.getLinkPhone());
			}

			List<BlackList> black = blackDao.findTels(phones);
			if (!black.isEmpty()) {
				MatchingRecord record = new MatchingRecord();

				record.setMatchType("200008");// 匹配类型200007
				StringBuffer remark = new StringBuffer();
				remark.append("黑名单:");
				remark.append(buildBlackPhone(black));
				record.setMatchTypeVal("其他联系人手机号码存在与系统黑名单中");
				record.setRemarks(remark.toString());
				record.setMatchLevel(2);// 匹配等级
				record.setMatchLevelVal("黄");
				match.addMatchingRecord(record);
			}
		}

		// 29 申请人单位名称与我司黑名单单位名称相同
		String comName = userApply.getComName();
		if (StringUtils.isNotBlank(comName)) {
			BlackList black = blackDao.findComName(comName);
			if (null != black) {
				MatchingRecord record = new MatchingRecord();

				record.setMatchType("200009");// 匹配类型200007
				StringBuffer remark = new StringBuffer();
				remark.append("黑名单:");
				remark.append(comName);
				record.setMatchTypeVal("申请人单位名称与我司黑名单单位名称相同");
				record.setRemarks(remark.toString());
				record.setMatchLevel(3);// 匹配等级
				record.setMatchLevelVal("红");
				match.addMatchingRecord(record);
			}
		}

		// 30 申请人单位地址与我司黑名单住宅地址相同
		String comAddr = userApply.getComAddr();
		if (StringUtils.isNotBlank(comAddr)) {
			BlackList black = blackDao.findHousAddr(comAddr);
			if (null != black) {
				MatchingRecord record = new MatchingRecord();

				record.setMatchType("200010");
				StringBuffer remark = new StringBuffer();
				remark.append("黑名单:");
				remark.append(comAddr);
				record.setMatchTypeVal("申请人单位地址与我司黑名单住宅地址相同");
				record.setRemarks(remark.toString());
				record.setMatchLevel(3);// 匹配等级
				record.setMatchLevelVal("红");
				match.addMatchingRecord(record);
			}
		}

		// 31 申请人单位地址与我司黑名单单位地址相同
		if (StringUtils.isNotBlank(comAddr)) {
			BlackList black = blackDao.findComAddr(comAddr);
			if (null != black) {
				MatchingRecord record = new MatchingRecord();

				record.setMatchType("200011");
				StringBuffer remark = new StringBuffer();
				remark.append("黑名单:");
				remark.append(comAddr);
				record.setMatchTypeVal("申请人单位地址与我司黑名单单位地址相同");
				record.setRemarks(remark.toString());
				record.setMatchLevel(3);// 匹配等级
				record.setMatchLevelVal("红");
				match.addMatchingRecord(record);
			}
		}

		String housAddr = userApply.getHouseAddr();
		if (StringUtils.isNotBlank(housAddr)) {
			// 32 申请人住宅地址与我司黑名单住宅地址相同
			BlackList black = blackDao.findHousAddr(housAddr);
			if (null != black) {
				MatchingRecord record = new MatchingRecord();

				record.setMatchType("200012");
				StringBuffer remark = new StringBuffer();
				remark.append("黑名单:");
				remark.append(housAddr);
				record.setMatchTypeVal("申请人住宅地址与我司黑名单住宅地址相同");
				record.setRemarks(remark.toString());
				record.setMatchLevel(3);// 匹配等级
				record.setMatchLevelVal("红");
				match.addMatchingRecord(record);
			}

			// 33 申请人住宅地址与我司黑名单单位地址相同
			black = blackDao.findComAddr(housAddr);
			if (null != black) {
				MatchingRecord record = new MatchingRecord();

				record.setMatchType("200013");
				StringBuffer remark = new StringBuffer();
				remark.append("黑名单:");
				remark.append(housAddr);
				record.setMatchTypeVal("申请人住宅地址与我司黑名单单位地址相同");
				record.setRemarks(remark.toString());
				record.setMatchLevel(3);// 匹配等级
				record.setMatchLevelVal("红");
				match.addMatchingRecord(record);
			}
		}

		// 34 申请人单位电话与我司黑名单单位手机号码相同
		String comTel = userApply.getComTel();
		if (StringUtils.isNotBlank(comTel)) {
			BlackList black = blackDao.findComTel(comTel);
			if (null != black) {
				MatchingRecord record = new MatchingRecord();

				record.setMatchType("200014");
				StringBuffer remark = new StringBuffer();
				remark.append("黑名单:");
				remark.append(comTel);
				record.setMatchTypeVal("申请人单位电话与我司黑名单单位手机号码相同");
				record.setRemarks(remark.toString());
				record.setMatchLevel(3);// 匹配等级
				record.setMatchLevelVal("红");
				match.addMatchingRecord(record);
			}

			// 35 申请人单位电话与我司黑名单住宅手机号码相同
			black = blackDao.findHousTel(comTel);
			if (null != black) {
				MatchingRecord record = new MatchingRecord();

				record.setMatchType("200015");
				StringBuffer remark = new StringBuffer();
				remark.append("黑名单:");
				remark.append(comTel);
				record.setMatchTypeVal("申请人单位电话与我司黑名单单位手机号码相同");
				record.setRemarks(remark.toString());
				record.setMatchLevel(3);// 匹配等级
				record.setMatchLevelVal("红");
				match.addMatchingRecord(record);
			}
		}

		// 36 申请人住宅电话与我司黑名单单位手机号码相同
		String housTel = userApply.getHouseTel();
		if (StringUtils.isNotBlank(comTel)) {
			BlackList black = blackDao.findComTel(housTel);
			if (null != black) {
				MatchingRecord record = new MatchingRecord();

				record.setMatchType("200016");
				StringBuffer remark = new StringBuffer();
				remark.append("黑名单:");
				remark.append(comTel);
				record.setMatchTypeVal("申请人住宅电话与我司黑名单单位电话相同");
				record.setRemarks(remark.toString());
				record.setMatchLevel(2);// 匹配等级
				record.setMatchLevelVal("橙色");
				match.addMatchingRecord(record);
			}

			// 37 申请人住宅电话与我司黑名单住宅手机号码相同
			black = blackDao.findHousTel(housTel);
			if (null != black) {
				MatchingRecord record = new MatchingRecord();

				record.setMatchType("200017");
				StringBuffer remark = new StringBuffer();
				remark.append("黑名单:");
				remark.append(comTel);
				record.setMatchTypeVal("申请人住宅电话与我司黑名单住宅手机号码相同");
				record.setRemarks(remark.toString());
				record.setMatchLevel(2);// 匹配等级
				record.setMatchLevelVal("橙色");
				match.addMatchingRecord(record);
			}
		}

		// 38 申请人房产地址与我司黑名单单位地址相同
		String estate = userApply.getEstate();
		if (StringUtils.isNoneBlank(estate)) {
			BlackList black = blackDao.findComAddr(housTel);
			if (null != black) {
				MatchingRecord record = new MatchingRecord();

				record.setMatchType("200018");
				StringBuffer remark = new StringBuffer();
				remark.append("黑名单:");
				remark.append(estate);
				record.setMatchTypeVal("申请人房产地址与我司黑名单单位地址相同");
				record.setRemarks(remark.toString());
				record.setMatchLevel(2);// 匹配等级
				record.setMatchLevelVal("橙色");
				match.addMatchingRecord(record);
			}

			// 39 申请人房产地址与我司黑名单住宅地址相同
			black = blackDao.findHousAddr(estate);
			if (null != black) {
				MatchingRecord record = new MatchingRecord();

				record.setMatchType("200019");
				StringBuffer remark = new StringBuffer();
				remark.append("黑名单:");
				remark.append(estate);
				record.setMatchTypeVal("申请人房产地址与我司黑名单住宅地址相同");
				record.setRemarks(remark.toString());
				record.setMatchLevel(2);// 匹配等级
				record.setMatchLevelVal("橙色");
				match.addMatchingRecord(record);
			}
		}
	}

	private String buildBlackPhone(List<BlackList> black) {
		StringBuffer str = new StringBuffer();
		for (BlackList bc : black) {
			if (StringUtils.isNotBlank(bc.getCustName())) {
				str.append("#姓名:");
				str.append(bc.getCustName());
				str.append("#");
			}

			if (StringUtils.isNotBlank(bc.getMobile())) {
				str.append("#电话:");
				str.append(bc.getMobile());
				str.append("#");
			}

			if (StringUtils.isNotBlank(bc.getComName())) {
				str.append("#单位公司:");
				str.append(bc.getComName());
				str.append("#");
			}

		}
		return str.toString();
	}

	@Override
	public BlackListMatch findByApplyId(String applyId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(applyId));
		BlackListMatch blackList = mongoTemplate.findOne(query, BlackListMatch.class);
		return blackList;
	}
}
