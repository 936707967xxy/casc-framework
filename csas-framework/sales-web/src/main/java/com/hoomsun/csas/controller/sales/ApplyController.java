/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.csas.audit.model.PhoneVerify;
import com.hoomsun.csas.audit.server.inter.PhoneVerifyServerI;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.model.vo.CreditVo;
import com.hoomsun.csas.sales.api.model.vo.FinancialVo;
import com.hoomsun.csas.sales.api.model.vo.OtherVo;
import com.hoomsun.csas.sales.api.server.inter.CallBillsServerI;
import com.hoomsun.csas.sales.api.server.inter.CreditServerI;
import com.hoomsun.csas.sales.api.server.inter.FinancialServerI;
import com.hoomsun.csas.sales.api.server.inter.OtherServerI;
import com.hoomsun.csas.sales.api.server.inter.UserApplyServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月1日 <br>
 * 描述：申请信息相关的查询控制层
 * copy 自 audit-web中的applyController
 */
@Controller
public class ApplyController {
	@Autowired
	private UserApplyServerI userApplyDataServer;
	@Autowired
	private CreditServerI creditServer;// 征信
	@Autowired
	private FinancialServerI financialServer;
	@Autowired
	private CallBillsServerI callBillsServer;
	@Autowired
	private OtherServerI otherServer;
	@Autowired
	private PhoneVerifyServerI phoneVerifyServer;

	// 获取申请数据信息
	@RequestMapping("/sys/user/apply/data.do")
	@ResponseBody
	public UserApply findApply(String applyId, HttpServletRequest request) {
		if (StringUtils.isBlank(applyId)) {
			return null;
		}
		return userApplyDataServer.findApplyById(applyId);
	}

	// 获取征信数据信息
	@RequestMapping("/sys/user/credit/data.do")
	@ResponseBody
	public CreditVo findCredit(String applyId, HttpServletRequest request) {
		CreditVo creditVo = creditServer.findByApplyId(applyId);
		return creditVo;
	}
	
	//获取财务数据
	@RequestMapping("/sys/user/financial/data.do")
	@ResponseBody
	public FinancialVo findFinancial(String applyId, HttpServletRequest request) {
		return financialServer.findByApplyId(applyId);
	}

	// 获取通话详单信息
	@RequestMapping("/sys/user/callbill/data.do")
	@ResponseBody
	public JSONObject findCallBills(String applyId, HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		obj.put("contacts", callBillsServer.findByApplyId(applyId).getContacts());//通话详单信息
		List<PhoneVerify> phoneVerifies = phoneVerifyServer.findByApplyId(applyId);
		obj.put("phoneVerifies", phoneVerifies);//通话详单信息
		return obj;
	}

	// 获取其他数据信息
	@RequestMapping("/sys/user/other/data.do")
	@ResponseBody
	public OtherVo findOther(String applyId, HttpServletRequest request) {
		return otherServer.findByApplyId(applyId);
	}
	
}
