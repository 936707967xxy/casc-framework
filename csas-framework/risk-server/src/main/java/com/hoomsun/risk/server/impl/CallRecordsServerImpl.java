/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.server.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.model.Result;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.risk.dao.mongo.PhoneBookDao;
import com.hoomsun.risk.dao.mongo.TopPhoneBookDao;
import com.hoomsun.risk.model.CallRecords;
import com.hoomsun.risk.model.NearNMonthCall;
import com.hoomsun.risk.model.PhoneBook;
import com.hoomsun.risk.model.TopCallRecords;
import com.hoomsun.risk.model.TopPhoneBook;
import com.hoomsun.risk.model.CallRecords.Records;
import com.hoomsun.risk.model.PhoneBook.Contact;
import com.hoomsun.risk.model.TopCallRecords.TopCall;
import com.hoomsun.risk.server.inter.CallRecordsServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月29日 <br>
 * 描述：通话详单的业务数据 通话详单接口数据
 */
@Service("callRecordsServer")
public class CallRecordsServerImpl implements CallRecordsServerI {
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private PhoneBookDao phoneBookDao;
	@Autowired
	private TopPhoneBookDao topPhoneBookDao;

	/**
	 * 添加通话记录的数据 通话记录的默认applyId 为 null 或者 为0
	 */
	@Override
	public Result createCallRecords(CallRecords records) {
		String idNumber = records.getIdNumber();

		Query query = new Query();
		query.addCriteria(Criteria.where("idNumber").is(idNumber).and("applyId").is("0"));

		CallRecords cr = mongoTemplate.findOne(query, CallRecords.class);
		Date now = new Date();
		String crId = PrimaryKeyUtil.getPrimaryKey();
		if (null == cr) {// 添加
			cr = new CallRecords();
			cr.setId(crId);
			records.setCreateDate(now);
			records.setApplyId("0");
			mongoTemplate.save(records);
		} else {
			Date createDate = cr.getCreateDate();
			String applyId = cr.getApplyId();

			long limit = (now.getTime() - createDate.getTime()) / (1000 * 60 * 60 * 24);
			if ((StringUtils.isBlank(applyId) || "0".equals(applyId) || null == cr.getClaimDate()) && limit <= 30) {// 没有被认领签收
																													// 修改
				crId = cr.getId();
				cr.setIdNumber(idNumber);
				cr.setPhoneNum(records.getPhoneNum());
				cr.setCustName(records.getCustName());
				cr.setRecords(records.getRecords());
				cr.setApplyId("0");
				cr.setCreateDate(now);
				// 保存原始的通话记录
				mongoTemplate.save(cr);
			} else {// 新数据 添加
				cr.setId(crId);
				cr.setCreateDate(now);
				cr.setApplyId("0");
				cr.setIdNumber(idNumber);
				cr.setCustName(records.getCustName());
				cr.setPhoneNum(records.getPhoneNum());
				cr.setRecords(records.getRecords());
				mongoTemplate.save(cr);
			}
		}

		// 通话频次top 5
		Fields fields = Fields.fields("$records.commPhone");
		Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where("idNumber").is(idNumber).and("applyId").is("0")), Aggregation.unwind("records"), Aggregation.group(fields).count().as("count"),
				Aggregation.sort(new Sort(Direction.DESC, "count")), Aggregation.limit(5L));

		AggregationResults<JSONObject> aggregate = mongoTemplate.aggregate(aggregation, "RK_CALL_RECORDS", JSONObject.class);
		List<JSONObject> objects = aggregate.getMappedResults();
		if (objects != null && objects.size() > 0) {
			query = new Query();
			query.addCriteria(Criteria.where("idNumber").is(idNumber).and("applyId").is("0").and("crId").is(crId));

			TopCallRecords top = mongoTemplate.findOne(query, TopCallRecords.class);
			if (top == null) {
				top = new TopCallRecords();
				top.setId(PrimaryKeyUtil.getPrimaryKey());
			}

			top.setCrId(crId);
			top.setIdNumber(idNumber);
			top.setCreateDate(now);
			top.setApplyId("0");
			top.setPhoneNum(records.getPhoneNum());
			List<TopCall> calls = new ArrayList<TopCall>();
			for (JSONObject jsonObject : objects) {
				TopCall call = top.new TopCall();
				call.setCallPhone(jsonObject.getString("_id"));
				call.setCallFrequency(jsonObject.getInteger("count"));
				calls.add(call);
			}
			top.setRecords(calls);
			mongoTemplate.save(top);
		}

		// 最大的通话时间
		// db.RK_CALL_RECORDS.aggregate([
		// {"$match":{"_id":"610581199004180361"}},
		// {"$unwind":"$records"},
		// {"$group":{"_id":"$records.commDate"}},
		// {"$sort":{"_id":1}},
		// {"$limit":1}
		// ])

		// 近三月的通话电话号码
		// 最大值
		fields = Fields.fields("$records.commDate");
		aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where("idNumber").is(idNumber).and("applyId").is("0")), Aggregation.unwind("records"), Aggregation.group(fields), Aggregation.sort(new Sort(Direction.DESC, "_id")),
				Aggregation.limit(1L));

		AggregationResults<JSONObject> results = mongoTemplate.aggregate(aggregation, "RK_CALL_RECORDS", JSONObject.class);
		List<JSONObject> obj = results.getMappedResults();
		Date maxDate = new Date();
		if (null == obj || obj.size() < 1) {

		} else {
			maxDate = obj.get(0).getDate("_id");
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(maxDate);
		calendar.add(Calendar.MONTH, -2);
		Date start = calendar.getTime();

		// db.RK_CALL_RECORDS.aggregate([
		// {"$match":{"_id":"610581199004180361","records.commDate":{"$gte":ISODate("2017-11-10
		// 00:00:00.000Z"),"$lte":ISODate("2018-01-10 10:44:15.000Z")}}},
		// {"$unwind":"$records"},
		// {"$group":{"_id":"$records.commPhone"}}
		// ])

		fields = Fields.fields("$records.commPhone");
		aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where("idNumber").is(idNumber).and("applyId").is("0").and("records.commDate").gte(start).lte(maxDate)), Aggregation.unwind("records"), Aggregation.group(fields));

		results = mongoTemplate.aggregate(aggregation, "RK_CALL_RECORDS", JSONObject.class);
		objects = results.getMappedResults();

		if (null != objects && objects.size() > 0) {
			query = new Query();
			query.addCriteria(Criteria.where("idNumber").is(idNumber).and("applyId").is("0").and("crId").is(crId));
			NearNMonthCall near = mongoTemplate.findOne(query, NearNMonthCall.class);
			if (near == null) {
				near = new NearNMonthCall();
				near.setId(PrimaryKeyUtil.getPrimaryKey());
			}

			near.setCrId(crId);
			near.setIdNumber(idNumber);
			near.setCreateDate(now);
			near.setApplyId("0");
			String[] phones = new String[objects.size()];
			int index = 0;
			for (JSONObject jsonObject : objects) {
				phones[index] = jsonObject.getString("_id");
				index = index + 1;
			}
			near.setPhones(phones);
			mongoTemplate.save(near);
		}

		// 通讯录匹配
		matchBook(records);

		return new Result(0000, "成功!");
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月6日 <br>
	 * 描述： 通话记录与电话簿的匹配
	 * 
	 * @param records
	 */
	public void matchBook(CallRecords callRecords) {
		if (null == callRecords) {
			return;
		}
		List<Records> records = callRecords.getRecords();
		if (null == records || records.size() < 1) {
			return;
		}

		String idNumber = callRecords.getIdNumber();
		// 查询电话簿 未被认领的电话簿
		PhoneBook phoneBook = phoneBookDao.findByIdNumberInit(idNumber);
		if (null != phoneBook) {
			String pbId = phoneBook.getId();
			String phoneNumber = callRecords.getPhoneNum();
			Date createDate = phoneBook.getCreateDate();
			Date now = new Date();
			String applyId = phoneBook.getApplyId();
			long limit = (now.getTime() - createDate.getTime()) / (1000 * 60 * 60 * 24);
			if ((StringUtils.isBlank(applyId) || "0".equals(applyId)) && limit <= 30) {
				List<Contact> contacts = phoneBook.getContacts();
				if (contacts != null && contacts.size() > 0) {
					HashMap<String, Integer> result = new HashMap<String, Integer>();

					for (Contact ct : contacts) {
						String phone = ct.getPhone();
						for (Records rd : records) {
							if (phone.equals(rd.getCommPhone())) {
								result.put(phone, rd.getFrequency());
								continue;
							}
						}
					}

					// 匹配率
					double match = result.size() / contacts.size();
					phoneBook.setMatchRate(match);
					// 主要是跟新匹配率
					mongoTemplate.save(phoneBook);

					Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String, Integer>>() {
						@Override
						public int compare(Entry<String, Integer> a, Entry<String, Integer> b) {
							return b.getValue().compareTo(a.getValue());// 降序
						}
					};

					// map转换成list进行排序
					List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(result.entrySet());
					// 排序
					Collections.sort(list, valueComparator);

					Integer length = list.size();
					if (list.size() > 5) {
						length = 5;
					}

					TopPhoneBook top = topPhoneBookDao.findByIdNumberAndPbId(idNumber, pbId);
					if (top == null) {
						top = new TopPhoneBook();
						top.setId(PrimaryKeyUtil.getPrimaryKey());
					}

					top.setPbId(pbId);
					top.setApplyId("0");
					top.setIdNumber(idNumber);
					top.setPhoneNum(phoneNumber);
					for (int i = 0; i < length; i++) {
						for (Map.Entry<String, Integer> entry : list) {
							String phone = entry.getKey();
							Integer value = entry.getValue();
							com.hoomsun.risk.model.TopPhoneBook.TopCall call = top.new TopCall();
							call.setCallPhone(phone);
							call.setCallFrequency(value);
							top.addRecords(call);
						}
					}
					mongoTemplate.save(top);
				}
			}
		}
	}
}
