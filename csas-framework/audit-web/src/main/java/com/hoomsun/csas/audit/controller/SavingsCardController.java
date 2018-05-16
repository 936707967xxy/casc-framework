/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.model.Json;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;
import com.hoomsun.csas.sales.api.model.SavingsBills;
import com.hoomsun.csas.sales.api.model.SavingsCard;
import com.hoomsun.csas.sales.api.server.inter.SavingsCardServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月18日 <br>
 * 描述：储蓄卡 银行流水
 */
@Controller
public class SavingsCardController {

	@Autowired
	private SavingsCardServerI savingsCardServer;

	@RequestMapping("/sys/savingscard/save.do")
	@ResponseBody
	public Json saveSavingsCardBills(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
		SessionRouter router = LoginUtil.getLoginSession(request);
		SavingsCard savingsCard = jsonObject.toJavaObject(SavingsCard.class);
		savingsCard.setEmpId(router.getEmpId());
		savingsCard.setEmpName(router.getEmpName());
		return savingsCardServer.createCard(savingsCard);
	}

	@RequestMapping("/sys/savingscard/findbyapplyid.do")
	@ResponseBody
	public SavingsCard saveSavingsCardBills(String applyId, HttpServletRequest request) {
		SavingsCard sc = savingsCardServer.selectByApplyId(applyId);
		if(sc==null){
			sc = new SavingsCard();
			sc.addSavingsBills(new SavingsBills(1,"工资进账"));
			sc.addSavingsBills(new SavingsBills(2,"对公进账"));
			sc.addSavingsBills(new SavingsBills(3,"总计"));
		}
		return sc;
	}

}
