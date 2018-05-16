/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.sales;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.sales.api.server.inter.UserApplyServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月30日 <br>
 * 描述：通话详单的数据
 */
@Controller
public class CallRecordController {
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private UserApplyServerI userApplyServer;
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/sys/callrecord/pager.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Pager<Object>  findCallRecord(HttpServletRequest request,String applyId, Integer page, Integer rows) {
		String idNumber = userApplyServer.findIdNumber(applyId);
		if (idNumber == null) {
			return null;
		}
//		String idNumber = "61052319910609847X";
		if (page == null || rows == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		rows = rows > 50 ? 50 : rows;
		
		Aggregation aggregation = Aggregation.newAggregation(
				Aggregation.match(Criteria.where("_id").is(idNumber)),
				Aggregation.unwind("records"),
				Aggregation.group(Aggregation.bind("commPhone", "records")), // 这个可以直接取其下的值
				Aggregation.skip((long)rows*(page-1)),
				Aggregation.limit((long)rows)
				);
		
		AggregationResults<Object> aggregate = mongoTemplate.aggregate(aggregation, "CALL_RECORDS", Object.class);
		
		// 组装获取的数据
		List<Object> callDetais = aggregate.getMappedResults();
		
		// 获取总数量
		Aggregation aggregationCount = Aggregation.newAggregation(
				Aggregation.match(Criteria.where("_id").is(idNumber)),
				Aggregation.unwind("records"),
				Aggregation.count().as("totle")
				);
		AggregationResults<Map> aggCount = mongoTemplate.aggregate(aggregationCount, "CALL_RECORDS", Map.class);
		int totle = 0;
		if (aggCount.getMappedResults().size() != 0) {
			totle = (int)aggCount.getMappedResults().get(0).get("totle");
		} 
		
		return new Pager<Object>(callDetais, totle);
	}
}
