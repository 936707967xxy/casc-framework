/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.model.Result;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.risk.model.CallRecords;
import com.hoomsun.risk.model.CallRecords.Records;
import com.hoomsun.risk.server.inter.CallRecordsServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月29日 <br>
 * 描述：通话详单
 */
@Controller
public class CallRecordsController {
	@Autowired
	private CallRecordsServerI callRecordsServer;

	@RequestMapping(value = "/api/risk/call/detail.do", method = RequestMethod.POST)
	@ResponseBody
	public Result addCallRecords(HttpServletRequest request,@RequestBody JSONObject jsonObject) {
		CallRecords callRecords = new CallRecords();
		String idNumber = jsonObject.getString("idNumber");
		String phoneNum = jsonObject.getString("phoneNumber");
		String custName = jsonObject.getString("custName");
		callRecords.setIdNumber(idNumber);
		callRecords.setPhoneNum(phoneNum);
		callRecords.setCustName(custName);

		JSONArray array = jsonObject.getJSONArray("data");
		for (Object object : array) {
			JSONObject obj = JSONObject.parseObject(JSONObject.toJSONString(object));
			Records rc = callRecords.new Records();
			String commUser = obj.getString("callUser");
			String commPhone = obj.getString("callNumber");
			Integer commType = obj.getInteger("callType");// 0主叫 1 被叫
			String commTypeVal = obj.getString("callTypeVal");
			Date commDate = obj.getDate("callDate");// 呼叫时间
			Long duration = obj.getLong("duration");// 通话时长
			Double money = obj.getDouble("callMoney");
			
			
			rc.setId(PrimaryKeyUtil.getPrimaryKey());
			rc.setCommPhone(commPhone);
			rc.setCommDate(commDate);
			rc.setCommType(commType);
			rc.setCommTypeVal(commTypeVal);
			rc.setCommUser(commUser);
			rc.setDuration(duration);
			rc.setMoney(money);
			
			callRecords.addRecords(rc);
		}

		return callRecordsServer.createCallRecords(callRecords);
	}
	
}
