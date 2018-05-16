/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.risk.model.UserApply;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月8日 <br>
 * 描述：
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-cfg.xml")
public class CheatTest {
	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void maxTest() {
		Query query = new Query();
		String IdNumber = "230805198708133015";// 身份证号码 b2e4617e3c0249a2b69e70ae6f5b7bee
		query.addCriteria(Criteria.where("idNumber").is(IdNumber));//.and("applyDate").is("{$max:$applyDate}")
		Order order = new Order(Direction.DESC,"applyDate");
		Sort sort = new Sort(order);
		query.with(sort);
//		query.limit(1);
		UserApply lastApply = mongoTemplate.findOne(query, UserApply.class);
		System.out.println(DateUtil.format(lastApply.getApplyDate(), DateUtil.ymdhms));
		System.out.println(lastApply);
	}
	
	@Test
	public void topTest() {
		Fields fields = Fields.fields("$records.commPhone");
		Aggregation aggregation = Aggregation.newAggregation(
		        Aggregation.match(Criteria.where("_id").is("610581199004180361")),
		        Aggregation.unwind("records"),
		        Aggregation.group(fields).count().as("count"),
		        Aggregation.sort(new Sort(Direction.DESC, "count")),
		        Aggregation.limit(5L)
				);
		AggregationResults<JSONObject> aggregate = mongoTemplate.aggregate(aggregation, "CALL_RECORDS", JSONObject.class);
		List<JSONObject> objects = aggregate.getMappedResults();
		for (JSONObject jsonObject : objects) {
			System.out.println("###### :" + jsonObject.toJSONString());
		}
		
		fields = Fields.fields("$records.commDate");
		aggregation = Aggregation.newAggregation(
		        Aggregation.match(Criteria.where("_id").is("610581199004180361")),
		        Aggregation.unwind("records"),
		        Aggregation.group(fields),
		        Aggregation.sort(new Sort(Direction.DESC, "_id")),
		        Aggregation.limit(1L)
				);
		
		AggregationResults<JSONObject> results = mongoTemplate.aggregate(aggregation, "CALL_RECORDS", JSONObject.class);
		List<JSONObject> obj = results.getMappedResults();
		Date maxDate = obj.get(0).getDate("_id");
		System.out.println("######################: " + maxDate);
		System.out.println("######################: " + obj.get(0));
	}
	
	
	@Test
	public void nearTest() {
		Date maxDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(maxDate);
		calendar.add(Calendar.MONTH, -2);
		Date start = calendar.getTime();
		
		Fields fields = Fields.fields("$records.commPhone");
		Aggregation aggregation = Aggregation.newAggregation(
		        Aggregation.match(Criteria.where("_id").is("610581199004180361")),
		        Aggregation.match(Criteria.where("records.commDate").gte(start).lte(maxDate)),
		        Aggregation.unwind("records"),
		        Aggregation.group(fields)
				);
		
		AggregationResults<JSONObject> results = mongoTemplate.aggregate(aggregation, "CALL_RECORDS", JSONObject.class);
		List<JSONObject>  objects = results.getMappedResults();
		for (JSONObject jsonObject : objects) {
			System.out.println(jsonObject.get("_id"));
		}
		System.out.println("#########:" + objects.size());
		System.out.println("#########:" + objects);
	}
}
