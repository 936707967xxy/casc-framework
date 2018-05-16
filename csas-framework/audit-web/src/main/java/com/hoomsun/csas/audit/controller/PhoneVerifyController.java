/**
 * Copyright www.idawn.org 邹益伟 idawnorg@gmail.com
 */
package com.hoomsun.csas.audit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Json;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;
import com.hoomsun.csas.audit.model.PhoneVerify;
import com.hoomsun.csas.audit.server.inter.PhoneVerifyServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月10日 <br>
 * 描述：电话核查
 */
@Controller
public class PhoneVerifyController {
	private PhoneVerifyServerI phoneVerifyServer;

	@Autowired
	public void setPhoneVerifyServer(PhoneVerifyServerI phoneVerifyServer) {
		this.phoneVerifyServer = phoneVerifyServer;
	}

	// 根据联系人ID获取审核数据
	@RequestMapping("/sys/phoneverify/querybycons.do")
	@ResponseBody
	public PhoneVerify findByCons(String consId, HttpServletRequest request) {
		return phoneVerifyServer.findByConsId(consId);
	}
	
	@RequestMapping("/sys/phoneverify/querybyapply.do")
	@ResponseBody
	public List<PhoneVerify> findByApplyId(String applyId, HttpServletRequest request) {
		return phoneVerifyServer.findByApplyId(applyId);
	}

	// 根据联系人ID获取审核数据
	@RequestMapping("/sys/phoneverify/querybyid.do")
	@ResponseBody
	public PhoneVerify findByAudit(String id, HttpServletRequest request) {
		return phoneVerifyServer.findById(id);
	}

	// 根据联系人ID获取审核数据
	@RequestMapping("/sys/phoneverify/save.do")
	@ResponseBody
	public Json saveAudit(PhoneVerify phoneVerify, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		phoneVerify.setEmpId(sessionRouter.getEmpId());
		phoneVerify.setEmpName(sessionRouter.getEmpName());
		return phoneVerifyServer.savePhoneVerify(phoneVerify);
	}
}
