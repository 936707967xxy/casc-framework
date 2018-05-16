/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Json;
import com.hoomsun.csas.audit.server.inter.LendImportServerI;

/**
 * 作者：liusong <br>
 * 创建时间：2018年2月2日 <br>
 * 描述：
 */
@Controller
@RequestMapping("/lendImport")
public class LendImportController {
	@Autowired
	private LendImportServerI lendImportServer;

	// 不带还款明细的推送
	@RequestMapping(value = "/send.do")
	@ResponseBody
	public Json lendImport(String applyId) {
		return lendImportServer.lendImport(applyId);
	}

	// 带还款明细的推送
	@RequestMapping(value = "/sendRePlan.do")
	@ResponseBody
	public Json lendImportPlan(String applyId) {
		return lendImportServer.lendImportReplaymentPlan(applyId);
	}
}
