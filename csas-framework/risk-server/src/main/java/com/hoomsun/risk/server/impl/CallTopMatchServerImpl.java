/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.server.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.risk.dao.mongo.TopCallRecordsDao;
import com.hoomsun.risk.dao.mongo.TopPhoneBookDao;
import com.hoomsun.risk.dao.mongo.UserApplyDao;
import com.hoomsun.risk.dao.mongo.UserColleagueDao;
import com.hoomsun.risk.dao.mongo.UserOtherLinkDao;
import com.hoomsun.risk.dao.mongo.UserRelativesDao;
import com.hoomsun.risk.dao.mongo.UserSpouseDao;
import com.hoomsun.risk.model.TopCallRecords;
import com.hoomsun.risk.model.TopPhoneBook;
import com.hoomsun.risk.model.UserApply;
import com.hoomsun.risk.model.UserColleague;
import com.hoomsun.risk.model.UserOtherLink;
import com.hoomsun.risk.model.UserRelatives;
import com.hoomsun.risk.model.UserSpouse;
import com.hoomsun.risk.model.TopCallRecords.TopCall;
import com.hoomsun.risk.model.match.BlackListMatch;
import com.hoomsun.risk.model.match.CallTopMatch;
import com.hoomsun.risk.model.match.CallTopMatch.Apply;
import com.hoomsun.risk.model.match.MatchingRecord;
import com.hoomsun.risk.server.inter.CallTopMatchServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月24日 <br>
 * 描述：通话记录中通话频次TOP-N 通讯录中通话频次TOP-N 数据匹配 业务接口实现
 */
@Service("callTopMatchServer")
public class CallTopMatchServerImpl implements CallTopMatchServerI {
	private final static Logger log = LoggerFactory.getLogger(CallTopMatchServerImpl.class);
	@Autowired
	private UserApplyDao userApplyDao;
	@Autowired
	private TopCallRecordsDao topCallRecordsDao;
	@Autowired
	private TopPhoneBookDao topPhoneBookDao;
	@Autowired
	private UserRelativesDao userRelativesDao;
	@Autowired
	private UserSpouseDao userSpouseDao;
	@Autowired
	private UserColleagueDao userColleagueDao;
	@Autowired
	private UserOtherLinkDao userOtherLinkDao;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Json matchCallTop(String applyId) {
		log.info("【反欺诈  通话记录TOP-N数据匹配】");
		if (StringUtils.isBlank(applyId)) {
			log.info("【反欺诈  通话记录TOP-N数据匹配】##### 参数异常! applyId is null");
			return new Json(false, "参数异常! applyId is null!");
		}

		UserApply userApply = userApplyDao.findByApplyId(applyId);
		if (null == userApply) {
			return new Json(false, "没有获取到申请数据!");
		} else {
			CallTopMatch match = new CallTopMatch();
			match.setApplyId(applyId);
			match.setCustName(userApply.getCustName());
			match.setIdNumber(userApply.getIdNumber());
			match.setLoanId(userApply.getApplyCode());
			match(userApply, match);
			// 保存匹配结果数据
			mongoTemplate.save(match);
		}
		return new Json(true, "通话记录TOP-N数据匹配成功!");
	}

	@Override
	public Json matchCallTop(UserApply userApply) {
		log.info("【反欺诈  通话记录TOP-N数据匹配】");
		if (null == userApply) {
			log.info("【反欺诈  通话记录TOP-N数据匹配】##### 参数异常! applyId is null");
			return new Json(false, "参数异常! applyId is null!");
		}

		CallTopMatch match = new CallTopMatch();
		match.setApplyId(userApply.getApplyId());
		match.setCustName(userApply.getCustName());
		match.setIdNumber(userApply.getIdNumber());
		match.setLoanId(userApply.getApplyCode());
		match(userApply, match);
		// 保存匹配结果数据
		mongoTemplate.save(match);

		return new Json(true, "通话记录TOP-N数据匹配成功!");
	}

	private void match(UserApply userApply, CallTopMatch match) {
		HashMap<String, Apply> result = new HashMap<String, Apply>();
		String nowApplyId = userApply.getApplyId();
		String nowMobile = userApply.getMobile();

		// 53.申请人手机号码和其他进件通话记录中通话频次前五名中手机号码相同
		List<TopCallRecords> tops = topCallRecordsDao.findByPhoneNumber(nowApplyId, nowMobile);
		if (!tops.isEmpty() && tops.size() > 0) {
			for (TopCallRecords top : tops) {
				String applyId = top.getApplyId();
				String phoneNumber = top.getPhoneNum();

				MatchingRecord record = new MatchingRecord();

				record.setMatchType("300014");
				record.setMatchTypeVal("申请人手机号码和其他进件通话记录中通话频次前五名中手机号码相同");
				record.setRemarks(phoneNumber);
				record.setMatchLevel(3);// 匹配等级
				record.setMatchLevelVal("红色");
				buildCheatFunny(result, record, applyId);
			}
		}

		// 54.申请人手机号码和其他进件通讯录中通话频次前五名中手机号码相同
		List<TopPhoneBook> topBook = topPhoneBookDao.findByPhoneNumber(nowApplyId, nowMobile);
		if (!topBook.isEmpty() && topBook.size() > 0) {
			for (TopPhoneBook top : topBook) {
				String applyId = top.getApplyId();
				String phoneNumber = top.getPhoneNum();

				MatchingRecord record = new MatchingRecord();

				record.setMatchType("300015");
				record.setMatchTypeVal("申请人手机号码和其他进件通讯录中通话频次前五名中手机号码相同");
				record.setRemarks(phoneNumber);
				record.setMatchLevel(3);// 匹配等级
				record.setMatchLevelVal("红色");
				buildCheatFunny(result, record, applyId);
			}
		}

		// 61.亲属手机号码和其他进件通话记录中通话频次前五名中手机号码相同
		// 62.亲属手机号码和其他进件通讯录中通话频次前五名中手机号码相同
		List<UserRelatives> relatives = userRelativesDao.findByApplyId(nowApplyId);
		if (null != relatives && relatives.size() > 0) {
			for (UserRelatives userRelatives : relatives) {
				tops = topCallRecordsDao.findByPhoneNumber(nowApplyId, userRelatives.getLinkPhone());
				if (!tops.isEmpty() && tops.size() > 0) {
					for (TopCallRecords top : tops) {
						String applyId = top.getApplyId();
						String phoneNumber = top.getPhoneNum();

						MatchingRecord record = new MatchingRecord();

						record.setMatchType("300022");
						record.setMatchTypeVal("亲属手机号码和其他进件通话记录中通话频次前五名中手机号码相同");
						record.setRemarks(phoneNumber);
						record.setMatchLevel(2);// 匹配等级
						record.setMatchLevelVal("橙色");
						buildCheatFunny(result, record, applyId);
					}
				}

				topBook = topPhoneBookDao.findByPhoneNumber(nowApplyId, userRelatives.getLinkPhone());
				if (!topBook.isEmpty() && topBook.size() > 0) {
					for (TopPhoneBook top : topBook) {
						String applyId = top.getApplyId();
						String phoneNumber = top.getPhoneNum();

						MatchingRecord record = new MatchingRecord();

						record.setMatchType("300023");
						record.setMatchTypeVal("亲属手机号码和其他进件通讯录中通话频次前五名中手机号码相同");
						record.setRemarks(phoneNumber);
						record.setMatchLevel(2);// 匹配等级
						record.setMatchLevelVal("橙色");
						buildCheatFunny(result, record, applyId);
					}
				}
			}
		}

		// 69.配偶手机号码和其他进件通话记录中通话频次前五名中手机号码相同
		// 70.配偶手机号码和其他进件通讯录中通话频次前五名中手机号码相同
		List<UserSpouse> spouses = userSpouseDao.findByApplyId(nowApplyId);
		if (spouses != null && spouses.size() > 0) {
			for (UserSpouse userSpouse : spouses) {
				tops = topCallRecordsDao.findByPhoneNumber(nowApplyId, userSpouse.getLinkPhone());
				if (!tops.isEmpty() && tops.size() > 0) {
					for (TopCallRecords top : tops) {
						String applyId = top.getApplyId();
						String phoneNumber = top.getPhoneNum();

						MatchingRecord record = new MatchingRecord();

						record.setMatchType("300030");
						record.setMatchTypeVal("配偶手机号码和其他进件通话记录中通话频次前五名中手机号码相同");
						record.setRemarks(phoneNumber);
						record.setMatchLevel(2);// 匹配等级
						record.setMatchLevelVal("橙色");
						buildCheatFunny(result, record, applyId);
					}
				}

				topBook = topPhoneBookDao.findByPhoneNumber(nowApplyId, userSpouse.getLinkPhone());
				if (!topBook.isEmpty() && topBook.size() > 0) {
					for (TopPhoneBook top : topBook) {
						String applyId = top.getApplyId();
						String phoneNumber = top.getPhoneNum();

						MatchingRecord record = new MatchingRecord();

						record.setMatchType("300031");
						record.setMatchTypeVal("配偶手机号码和其他进件通讯录中通话频次前五名中手机号码相同");
						record.setRemarks(phoneNumber);
						record.setMatchLevel(2);// 匹配等级
						record.setMatchLevelVal("橙色");
						buildCheatFunny(result, record, applyId);
					}
				}
			}
		}

		// 77.同事1手机号码和其他进件通话记录中通话频次前五名中手机号码相同
		// 78.同事1手机号码和其他进件通讯录中通话频次前五名中手机号码相同
		// 85.同事2手机号码和其他进件通话记录中通话频次前五名中手机号码相同
		// 86.同事2手机号码和其他进件通讯录中通话频次前五名中手机号码相同
		List<UserColleague> colleagues = userColleagueDao.findByApplyId(nowApplyId);
		if (colleagues != null && colleagues.size() > 0) {
			for (UserColleague userColleague : colleagues) {
				tops = topCallRecordsDao.findByPhoneNumber(nowApplyId, userColleague.getLinkPhone());
				if (!tops.isEmpty() && tops.size() > 0) {
					for (TopCallRecords top : tops) {
						String applyId = top.getApplyId();
						String phoneNumber = top.getPhoneNum();

						MatchingRecord record = new MatchingRecord();

						record.setMatchType("300038");
						record.setMatchTypeVal("同事手机号码和其他进件通话记录中通话频次前五名中手机号码相同");
						record.setRemarks(phoneNumber);
						record.setMatchLevel(2);// 匹配等级
						record.setMatchLevelVal("橙色");
						buildCheatFunny(result, record, applyId);
					}
				}

				topBook = topPhoneBookDao.findByPhoneNumber(nowApplyId, userColleague.getLinkPhone());
				if (!topBook.isEmpty() && topBook.size() > 0) {
					for (TopPhoneBook top : topBook) {
						String applyId = top.getApplyId();
						String phoneNumber = top.getPhoneNum();

						MatchingRecord record = new MatchingRecord();

						record.setMatchType("300039");
						record.setMatchTypeVal("同事手机号码和其他进件通讯录中通话频次前五名中手机号码相同");
						record.setRemarks(phoneNumber);
						record.setMatchLevel(2);// 匹配等级
						record.setMatchLevelVal("橙色");
						buildCheatFunny(result, record, applyId);
					}
				}
			}
		}

		// 93.其他联系人手机号码和其他进件通话记录中通话频次前五名中手机号码相同
		List<UserOtherLink> otherLinks = userOtherLinkDao.findByApplyId(nowApplyId);
		if (otherLinks != null && otherLinks.size() > 0) {
			for (UserOtherLink otherLink : otherLinks) {
				tops = topCallRecordsDao.findByPhoneNumber(nowApplyId, otherLink.getLinkPhone());
				if (!tops.isEmpty() && tops.size() > 0) {
					for (TopCallRecords top : tops) {
						String applyId = top.getApplyId();
						String phoneNumber = top.getPhoneNum();

						MatchingRecord record = new MatchingRecord();

						record.setMatchType("300054");
						record.setMatchTypeVal("其他联系人手机号码和其他进件通话记录中通话频次前五名中手机号码相同");
						record.setRemarks(phoneNumber);
						record.setMatchLevel(2);// 匹配等级
						record.setMatchLevelVal("橙色");
						buildCheatFunny(result, record, applyId);
					}
				}

				topBook = topPhoneBookDao.findByPhoneNumber(nowApplyId, otherLink.getLinkPhone());
				if (!topBook.isEmpty() && topBook.size() > 0) {
					for (TopPhoneBook top : topBook) {
						String applyId = top.getApplyId();
						String phoneNumber = top.getPhoneNum();

						MatchingRecord record = new MatchingRecord();

						record.setMatchType("300055");
						record.setMatchTypeVal("其他联系人手机号码和其他进件通讯录中通话频次前五名中手机号码相同");
						record.setRemarks(phoneNumber);
						record.setMatchLevel(2);// 匹配等级
						record.setMatchLevelVal("橙色");
						buildCheatFunny(result, record, applyId);
					}
				}
			}
		}

		// 当前申请人 通话记录 的top5
		TopCallRecords callTop = topCallRecordsDao.findByApplyId(nowApplyId);
		if (callTop != null) {
			if (callTop.getRecords() != null && callTop.getRecords().size() > 0) {
				String[] cp = new String[callTop.getRecords().size()];
				int index = 0;
				for (TopCall top : callTop.getRecords()) {
					cp[index] = top.getCallPhone();
					index = index + 1;
				}

				// 95.申请人通话记录中通话频次前五名中的手机号码与其他进件申请人的手机号码相同
				List<UserApply> applies = userApplyDao.findByMobiles(cp);
				if (null != applies && applies.size() > 0) {
					for (UserApply app : applies) {
						MatchingRecord record = new MatchingRecord();

						record.setMatchType("300056");
						record.setMatchTypeVal("申请人通话记录中通话频次前五名中的手机号码与其他进件申请人的手机号码相同");
						record.setRemarks(Arrays.toString(cp));
						record.setMatchLevel(2);// 匹配等级
						record.setMatchLevelVal("橙色");
						buildCheatFunny(result, record, app);
					}
				}

				// 96.申请人通话记录中通话频次前五名中的手机号码与其他进件亲属的手机号码相同
				List<UserRelatives> userRelatives = userRelativesDao.findByPhones(cp, nowApplyId);
				if (userRelatives != null && userRelatives.size() > 0) {
					String[] ids = new String[userRelatives.size()];
					index = 0;
					for (UserRelatives us : userRelatives) {
						ids[index] = us.getApplyId();
						index = index + 1;
					}

					applies = userApplyDao.findByApplyIds(ids);
					if (null != applies && applies.size() > 0) {
						for (UserApply app : applies) {
							MatchingRecord record = new MatchingRecord();

							record.setMatchType("300057");
							record.setMatchTypeVal("申请人通话记录中通话频次前五名中的手机号码与其他进件亲属的手机号码相同");
							record.setRemarks(Arrays.toString(cp));
							record.setMatchLevel(3);// 匹配等级
							record.setMatchLevelVal("红色");
							buildCheatFunny(result, record, app);
						}
					}
				}

				// 97.申请人通话记录中通话频次前五名中的手机号码与其他进件配偶的手机号码相同
				List<UserSpouse> userSpouses = userSpouseDao.findByPhones(cp, nowApplyId);
				if (userSpouses != null && userSpouses.size() > 0) {
					String[] ids = new String[userSpouses.size()];
					index = 0;
					for (UserSpouse us : userSpouses) {
						ids[index] = us.getApplyId();
						index = index + 1;
					}

					applies = userApplyDao.findByApplyIds(ids);
					if (null != applies && applies.size() > 0) {
						for (UserApply app : applies) {
							MatchingRecord record = new MatchingRecord();

							record.setMatchType("300058");
							record.setMatchTypeVal("申请人通话记录中通话频次前五名中的手机号码与其他进件配偶的手机号码相同");
							record.setRemarks(Arrays.toString(cp));
							record.setMatchLevel(3);// 匹配等级
							record.setMatchLevelVal("红色");
							buildCheatFunny(result, record, app);
						}
					}
				}

				// 98.申请人通话记录中通话频次前五名中的手机号码与其他进件同事1的手机号码相同
				// 99.申请人通话记录中通话频次前五名中的手机号码与其他进件同事2的手机号码相同
				List<UserColleague> userColleagues = userColleagueDao.findByPhones(cp, nowApplyId);
				if (userColleagues != null && userColleagues.size() > 0) {
					String[] ids = new String[userColleagues.size()];
					index = 0;
					for (UserColleague us : userColleagues) {
						ids[index] = us.getApplyId();
						index = index + 1;
					}

					applies = userApplyDao.findByApplyIds(ids);
					if (null != applies && applies.size() > 0) {
						for (UserApply app : applies) {
							MatchingRecord record = new MatchingRecord();

							record.setMatchType("300059");
							record.setMatchTypeVal("申请人通话记录中通话频次前五名中的手机号码与其他进件同事的手机号码相同");
							record.setRemarks(Arrays.toString(cp));
							record.setMatchLevel(2);// 匹配等级
							record.setMatchLevelVal("橙色");
							buildCheatFunny(result, record, app);
						}
					}
				}

				// 100.申请人通话记录中通话频次前五名中的手机号码与其他进件其他联系人的手机号码相同
				List<UserOtherLink> userOtherLinks = userOtherLinkDao.findByPhones(cp, nowApplyId);
				if (userOtherLinks != null && userOtherLinks.size() > 0) {
					String[] ids = new String[userOtherLinks.size()];
					index = 0;
					for (UserOtherLink us : userOtherLinks) {
						ids[index] = us.getApplyId();
						index = index + 1;
					}

					applies = userApplyDao.findByApplyIds(ids);
					if (null != applies && applies.size() > 0) {
						for (UserApply app : applies) {
							MatchingRecord record = new MatchingRecord();

							record.setMatchType("300061");
							record.setMatchTypeVal("申请人通话记录中通话频次前五名中的手机号码与其他进件其他联系人的手机号码相同");
							record.setRemarks(Arrays.toString(cp));
							record.setMatchLevel(2);// 匹配等级
							record.setMatchLevelVal("橙色");
							buildCheatFunny(result, record, app);
						}
					}
				}

				List<TopCall> calls = callTop.getRecords();
				if (calls != null && calls.size() > 0) {
					String[] phones = new String[calls.size()];
					index = 0;
					for (TopCall us : calls) {
						phones[index] = us.getCallPhone();
						index = index + 1;
					}

					// 101.申请人通话记录中通话频次前五名中的手机号码与其他进件通话记录中通话频次前五名中手机号码相同
					List<TopCallRecords> otherTopCall = topCallRecordsDao.findByPhoneNumbersExtApp(nowApplyId, phones);
					if (null != otherTopCall && otherTopCall.size() > 0) {
						String[] ids = new String[otherTopCall.size()];
						index = 0;
						for (TopCallRecords pb : otherTopCall) {
							ids[index] = pb.getApplyId();
							index = index + 1;
						}
						applies = userApplyDao.findByApplyIds(ids);
						if (null != applies && applies.size() > 0) {
							for (UserApply app : applies) {
								MatchingRecord record = new MatchingRecord();

								record.setMatchType("300062");
								record.setMatchTypeVal("申请人通话记录中通话频次前五名中的手机号码与其他进件通话记录中通话频次前五名中手机号码相同");
								record.setRemarks(Arrays.toString(cp));
								record.setMatchLevel(3);// 匹配等级
								record.setMatchLevelVal("红色");
								buildCheatFunny(result, record, app);
							}
						}
					}

					// 102.申请人通话记录中通话频次前五名中的手机号码与其他进件通讯录中通话频次前五名中手机号码相同
					List<TopPhoneBook> otherTopCallRecords = topPhoneBookDao.findByPhoneNumbersExtApp(nowApplyId, phones);
					if (null != otherTopCallRecords && otherTopCallRecords.size() > 0) {
						String[] ids = new String[otherTopCallRecords.size()];
						index = 0;
						for (TopPhoneBook pb : otherTopCallRecords) {
							ids[index] = pb.getApplyId();
							index = index + 1;
						}

						applies = userApplyDao.findByApplyIds(ids);
						if (null != applies && applies.size() > 0) {
							for (UserApply app : applies) {
								MatchingRecord record = new MatchingRecord();

								record.setMatchType("300063");
								record.setMatchTypeVal("申请人通话记录中通话频次前五名中的手机号码与其他进件通讯录中通话频次前五名中手机号码相同");
								record.setRemarks(Arrays.toString(cp));
								record.setMatchLevel(3);// 匹配等级
								record.setMatchLevelVal("红色");
								buildCheatFunny(result, record, app);
							}
						}
					}
				}

			}
		}

		// 当前申请人 通话记录 的top5
		TopPhoneBook bookTop = topPhoneBookDao.findByApplyId(nowApplyId);
		if (bookTop != null) {
			if (bookTop.getRecords() != null && bookTop.getRecords().size() > 0) {
				String[] bp = new String[bookTop.getRecords().size()];
				int index = 0;
				for (com.hoomsun.risk.model.TopPhoneBook.TopCall top : bookTop.getRecords()) {
					bp[index] = top.getCallPhone();
					index = index + 1;
				}

				// 103.申请人通讯录中通话频次前五名中的手机号码与其他进件申请人的手机号码相同
				List<UserApply> applies = userApplyDao.findByMobilesExtApp(bp, nowApplyId);
				if (null != applies && applies.size() > 0) {
					for (UserApply app : applies) {
						MatchingRecord record = new MatchingRecord();

						record.setMatchType("300064");
						record.setMatchTypeVal("申请人通话记录中通话频次前五名中的手机号码与其他进件通讯录中通话频次前五名中手机号码相同");
						record.setRemarks(Arrays.toString(bp));
						record.setMatchLevel(3);// 匹配等级
						record.setMatchLevelVal("红色");
						buildCheatFunny(result, record, app);
					}
				}

				// 104.申请人通讯录中通话频次前五名中的手机号码与其他进件亲属的手机号码相同
				List<UserRelatives> userRelatives = userRelativesDao.findByPhones(bp, nowApplyId);
				if (userRelatives != null && userRelatives.size() > 0) {
					String[] ids = new String[userRelatives.size()];
					index = 0;
					for (UserRelatives us : userRelatives) {
						ids[index] = us.getApplyId();
						index = index + 1;
					}

					applies = userApplyDao.findByApplyIds(ids);
					if (null != applies && applies.size() > 0) {
						for (UserApply app : applies) {
							MatchingRecord record = new MatchingRecord();

							record.setMatchType("300065");
							record.setMatchTypeVal("申请人通讯录中通话频次前五名中的手机号码与其他进件亲属的手机号码相同");
							record.setRemarks(Arrays.toString(bp));
							record.setMatchLevel(3);// 匹配等级
							record.setMatchLevelVal("红色");
							buildCheatFunny(result, record, app);
						}
					}
				}
				// 105.申请人通讯录中通话频次前五名中的手机号码与其他进件配偶的手机号码相同
				List<UserSpouse> userSpouses = userSpouseDao.findByPhones(bp, nowApplyId);
				if (userSpouses != null && userSpouses.size() > 0) {
					String[] ids = new String[userSpouses.size()];
					index = 0;
					for (UserSpouse us : userSpouses) {
						ids[index] = us.getApplyId();
						index = index + 1;
					}

					applies = userApplyDao.findByApplyIds(ids);
					if (null != applies && applies.size() > 0) {
						for (UserApply app : applies) {
							MatchingRecord record = new MatchingRecord();

							record.setMatchType("300066");
							record.setMatchTypeVal("申请人通讯录中通话频次前五名中的手机号码与其他进件配偶的手机号码相同");
							record.setRemarks(Arrays.toString(bp));
							record.setMatchLevel(3);// 匹配等级
							record.setMatchLevelVal("红色");
							buildCheatFunny(result, record, app);
						}
					}
				}

				// 106.申请人通讯录中通话频次前五名中的手机号码与其他进件同事1的手机号码相同
				// 107.申请人通讯录中通话频次前五名中的手机号码与其他进件同事2的手机号码相同
				List<UserColleague> userColleagues = userColleagueDao.findByPhones(bp, nowApplyId);
				if (userColleagues != null && userColleagues.size() > 0) {
					String[] ids = new String[userColleagues.size()];
					index = 0;
					for (UserColleague us : userColleagues) {
						ids[index] = us.getApplyId();
						index = index + 1;
					}

					applies = userApplyDao.findByApplyIds(ids);
					if (null != applies && applies.size() > 0) {
						for (UserApply app : applies) {
							MatchingRecord record = new MatchingRecord();

							record.setMatchType("300067");
							record.setMatchTypeVal("申请人通讯录中通话频次前五名中的手机号码与其他进件同事的手机号码相同");
							record.setRemarks(Arrays.toString(bp));
							record.setMatchLevel(2);// 匹配等级
							record.setMatchLevelVal("橙色");
							buildCheatFunny(result, record, app);
						}
					}
				}

				// 108.申请人通讯录中通话频次前五名中的手机号码与其他进件其他联系人的手机号码相同
				List<UserOtherLink> userOtherLinks = userOtherLinkDao.findByPhones(bp, nowApplyId);
				if (userOtherLinks != null && userOtherLinks.size() > 0) {
					String[] ids = new String[userOtherLinks.size()];
					index = 0;
					for (UserOtherLink us : userOtherLinks) {
						ids[index] = us.getApplyId();
						index = index + 1;
					}

					applies = userApplyDao.findByApplyIds(ids);
					if (null != applies && applies.size() > 0) {
						for (UserApply app : applies) {
							MatchingRecord record = new MatchingRecord();

							record.setMatchType("300069");
							record.setMatchTypeVal("申请人通讯录中通话频次前五名中的手机号码与其他进件其他联系人的手机号码相同");
							record.setRemarks(Arrays.toString(bp));
							record.setMatchLevel(2);// 匹配等级
							record.setMatchLevelVal("橙色");
							buildCheatFunny(result, record, app);
						}
					}
				}

				List<com.hoomsun.risk.model.TopPhoneBook.TopCall> calls = bookTop.getRecords();
				if (calls != null && calls.size() > 0) {
					String[] phones = new String[calls.size()];
					index = 0;
					for (com.hoomsun.risk.model.TopPhoneBook.TopCall us : calls) {
						phones[index] = us.getCallPhone();
						index = index + 1;
					}

					// 109.申请人通讯录中通话频次前五名中的手机号码与其他进件通话记录中通话频次前五名中手机号码相同
					List<TopCallRecords> otherTopCall = topCallRecordsDao.findByPhoneNumbersExtApp(nowApplyId, phones);
					if (null != otherTopCall && otherTopCall.size() > 0) {
						String[] ids = new String[otherTopCall.size()];
						index = 0;
						for (TopCallRecords pb : otherTopCall) {
							ids[index] = pb.getApplyId();
							index = index + 1;
						}

						applies = userApplyDao.findByApplyIds(ids);
						if (null != applies && applies.size() > 0) {
							for (UserApply app : applies) {
								MatchingRecord record = new MatchingRecord();

								record.setMatchType("300070");
								record.setMatchTypeVal("申请人通讯录中通话频次前五名中的手机号码与其他进件通话记录中通话频次前五名中手机号码相同");
								record.setRemarks(Arrays.toString(bp));
								record.setMatchLevel(3);// 匹配等级
								record.setMatchLevelVal("红色");
								buildCheatFunny(result, record, app);
							}
						}
					}

					// 110.申请人通讯录中通话频次前五名中的手机号码与其他进件通讯录中通话频次前五名中手机号码相同
					List<TopPhoneBook> otherTopBook = topPhoneBookDao.findByPhoneNumbersExtApp(nowApplyId, phones);
					if (null != otherTopBook && otherTopBook.size() > 0) {
						String[] ids = new String[otherTopBook.size()];
						index = 0;
						for (TopPhoneBook pb : otherTopBook) {
							ids[index] = pb.getApplyId();
							index = index + 1;
						}

						applies = userApplyDao.findByApplyIds(ids);
						if (null != applies && applies.size() > 0) {
							for (UserApply app : applies) {
								MatchingRecord record = new MatchingRecord();
								record.setMatchType("300071");
								record.setMatchTypeVal("申请人通讯录中通话频次前五名中的手机号码与其他进件通讯录中通话频次前五名中手机号码相同");
								record.setRemarks(Arrays.toString(bp));
								record.setMatchLevel(3);// 匹配等级
								record.setMatchLevelVal("红色");
								buildCheatFunny(result, record, app);
							}
						}
					}
				}
			}
		}

		// 处理结果
		for (Map.Entry<String, Apply> en : result.entrySet()) {
			match.addApply(en.getValue());
		}
	}

	private void buildCheatFunny(HashMap<String, Apply> result, MatchingRecord record, UserApply app) {
		Apply applyr = result.get(app.getApplyId());
		if (applyr == null) {
			// 根据申请ID获取申请信息 匹配出的结果
			applyr = new Apply();
			applyr.setApplyId(app.getApplyId());
			applyr.setCustName(app.getCustName());
			applyr.setIdNumber(app.getIdNumber());
			applyr.setLoanId(app.getApplyCode());
			applyr.addMatchResult(record);
			result.put(app.getApplyId(), applyr);
		} else {
			applyr.addMatchResult(record);
		}
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月5日 <br>
	 * 描述： 封装欺诈匹配结果
	 * 
	 * @param result
	 *            结果集合
	 * @param record
	 *            匹配结果
	 * @param applyId
	 *            匹配出的申请编号
	 */
	public void buildCheatFunny(HashMap<String, Apply> result, MatchingRecord record, String applyId) {
		Apply applyr = result.get(applyId);
		if (applyr == null) {
			// 根据申请ID获取申请信息 匹配出的结果
			applyr = new Apply();
			UserApply apply = userApplyDao.findByApplyId(applyId);
			applyr.setApplyId(applyId);
			applyr.setCustName(apply.getCustName());
			applyr.setIdNumber(apply.getIdNumber());
			applyr.setLoanId(apply.getApplyCode());
			applyr.addMatchResult(record);
			result.put(applyId, applyr);
		} else {
			applyr.addMatchResult(record);
		}
	}

	@Override
	public CallTopMatch findByApplyId(String applyId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(applyId));
		CallTopMatch callTop = mongoTemplate.findOne(query, CallTopMatch.class);
		return callTop;
	}
}
