/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.repos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.risk.model.CallRecords;
import com.hoomsun.risk.model.CallRecords.Records;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月5日 <br>
 * 描述：
 */
@Repository("callRecordsRepos")
public class CallRecordsRepos {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月5日 <br>
	 * 描述： 某人的通话记录  按电话号码分组的数据 未被认领的数据
	 * @param idNumber
	 * @return
	 */
	public CallRecords findByIdNumber(String idNumber) {
		CallRecords records = new CallRecords();
		Fields fields = Fields.fields("$records.commPhone");
		Aggregation aggregation = Aggregation.newAggregation(
	        Aggregation.match(Criteria.where("idNumber").is(idNumber).and("applyId").is("0")),
	        Aggregation.unwind("records"),
	        Aggregation.group(fields).count().as("count"),
	        Aggregation.sort(new Sort(Direction.DESC, "count"))
		);
		
		AggregationResults<JSONObject> aggregate = mongoTemplate.aggregate(aggregation, "RK_CALL_RECORDS", JSONObject.class);
		List<JSONObject> objects = aggregate.getMappedResults();
		for (JSONObject obj : objects) {
			records.setIdNumber(idNumber);
			
			JSONArray array = obj.getJSONArray("records");
			for (Object object : array) {
				JSONObject rd = JSONObject.parseObject(JSONObject.toJSONString(object));
				Records rds = records.new Records();
				rds.setCommPhone(rd.getString("_id"));
				rds.setFrequency(rd.getInteger("count"));
				records.addRecords(rds);
			}
		}
		return records;
	}
}
