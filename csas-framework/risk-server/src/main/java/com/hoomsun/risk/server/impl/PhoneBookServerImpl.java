/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.server.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Result;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.risk.dao.mongo.TopPhoneBookDao;
import com.hoomsun.risk.model.CallRecords;
import com.hoomsun.risk.model.CallRecords.Records;
import com.hoomsun.risk.model.PhoneBook;
import com.hoomsun.risk.model.PhoneBook.Contact;
import com.hoomsun.risk.model.TopPhoneBook;
import com.hoomsun.risk.repos.CallRecordsRepos;
import com.hoomsun.risk.server.inter.PhoneBookServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月1日 <br>
 * 描述：电话薄的数据处理 业务接口 数据的获取 信息的匹配 通话频次 匹配率
 */
@Service("phoneBookServer")
public class PhoneBookServerImpl implements PhoneBookServerI {

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CallRecordsRepos callRecordsRepos;
	@Autowired
	private TopPhoneBookDao topPhoneBookDao;

	@Override
	public Result createPhoneBook(PhoneBook phoneBook) {
		String idNumber = phoneBook.getIdNumber();

		Query query = new Query();
		query.addCriteria(Criteria.where("idNumber").is(idNumber).and("applyId").is("0"));
		List<Contact> contacts = phoneBook.getContacts();

		PhoneBook book = mongoTemplate.findOne(query, PhoneBook.class);
		Date now = new Date();
		String pbId = PrimaryKeyUtil.getPrimaryKey();
		if (null == book) { // 数据添加
			book = new PhoneBook();
			book.setId(pbId);
			book.setApplyId("0");
			book.setIdNumber(idNumber);
			book.setCreateDate(now);
			book.setCustName(phoneBook.getCustName());
			book.setContacts(phoneBook.getContacts());
		} else {
			Date createDate = book.getCreateDate();
			String applyId = book.getApplyId();

			long limit = (now.getTime() - createDate.getTime()) / (1000 * 60 * 60 * 24);
			if ((StringUtils.isBlank(applyId) || "0".equals(applyId)) && limit <= 30) {// 没有被认领签收
																						// 数据修改
				pbId = book.getId();
				book.setApplyId("0");
				book.setIdNumber(idNumber);
				book.setCreateDate(now);
				book.setCustName(phoneBook.getCustName());
				book.setContacts(phoneBook.getContacts());
			} else {// 数据添加
				book.setId(pbId);
				book.setApplyId("0");
				book.setCreateDate(now);
				book.setIdNumber(idNumber);
				book.setCustName(phoneBook.getCustName());
				book.setContacts(phoneBook.getContacts());
			}
		}
		mongoTemplate.save(book);// 保存电话簿
		
		// 通话记录匹配率
		// 首先获取通话记录
		CallRecords callRecords = callRecordsRepos.findByIdNumber(idNumber);
		if (callRecords != null && (null != contacts && contacts.size() > 0)) {// 通话记录已经推送
			List<Records> records = callRecords.getRecords();
			if (records != null && records.size() > 0 ) {
				HashMap<String, Integer> result = matchBook(contacts, records);

				// 匹配率
				double match = result.size() / contacts.size();
				book.setMatchRate(match);
				mongoTemplate.save(book);// 保存电话簿
				
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
				top.setIdNumber(idNumber);
				top.setApplyId("0");
				top.setPhoneNum(callRecords.getPhoneNum());
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
		return new Result(0000, "成功!");
	}

	public HashMap<String, Integer> matchBook(List<Contact> contacts, List<Records> records) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		for (Contact ct : contacts) {
			String phone = ct.getPhone();
			for (Records rd : records) {
				if (phone.equals(rd.getCommPhone())) {
					map.put(phone, rd.getFrequency());
					continue;
				}
			}
		}
		return map;
	}

	public static void main(String[] args) {
		Map<String, Integer> result = new HashMap<>();
		result.put("15109239020", 1);
		result.put("15109239021", 2);
		result.put("15109239022", 3);

		Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> a, Entry<String, Integer> b) {
				return b.getValue().compareTo(a.getValue());
			}
		};

		// map转换成list进行排序
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(result.entrySet());
		// 排序
		Collections.sort(list, valueComparator);

		for (Map.Entry<String, Integer> entry : list) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}
}
