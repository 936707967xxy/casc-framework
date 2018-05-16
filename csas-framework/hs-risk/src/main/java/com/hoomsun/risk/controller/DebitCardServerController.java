/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.controller;

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
import com.hoomsun.risk.model.DebitCard;
import com.hoomsun.risk.server.inter.DebitCardServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月6日 <br>
 * 描述：储蓄卡账单
 */
@Controller
public class DebitCardServerController {
	@Autowired
	private DebitCardServerI debitCardServer;

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月6日 <br>
	 * 描述： 添加储蓄卡的接口
	 * 
	 * @param request
	 * @param jsonObject
	 * @return
	 */
	@RequestMapping(value = "/api/risk/debitcard/add.do", method = RequestMethod.POST)
	@ResponseBody
	public Result addDebitCard(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
		if (jsonObject == null) {
			return new Result(1001, "参数异常!");
		}

		Result result = new Result(2001, "保存失败!");
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		for (Object object : jsonArray) {
			if (object instanceof String) {
				System.out.println("################");
			}
			JSONObject obj = JSONObject.parseObject(JSONObject.toJSONString(object));
			DebitCard debitCard = obj.toJavaObject(DebitCard.class);
			result = debitCardServer.saveDebitCard(debitCard);
		}
		return result;
	}

	@RequestMapping(value = "/api/risk/debitcard/test.do", method = RequestMethod.POST)
	@ResponseBody
	public Result addDebitCardTest(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
		if (jsonObject == null) {
			return new Result(1001, "参数异常!");
		}

		Result result = new Result(2001, "保存失败!");
		JSONObject obj = JSONObject.parseObject(JSONObject.toJSONString(jsonObject));
		DebitCard debitCard = obj.toJavaObject(DebitCard.class);
		System.out.println(debitCard);
		return result;
	}
}
