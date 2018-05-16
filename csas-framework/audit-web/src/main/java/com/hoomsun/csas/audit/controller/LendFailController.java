/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;
import com.hoomsun.csas.audit.model.vo.UserApplyVO;
import com.hoomsun.csas.audit.server.inter.LendFailServerI;

/**
 * 作者：liusong <br>
 * 创建时间：2018年1月22日 <br>
 * 描述：红小宝新申请单子流标操作
 */
@Controller
public class LendFailController {
	@Autowired
	private LendFailServerI lendFailServer;
	
	
	
	/**
	 * 流标展示列表
	 */
	@RequestMapping("/sys/lendfail/pager.do")
	@ResponseBody
	public Pager<UserApplyVO> findPager(Integer rows, Integer page, String custName, String loanId,  String idNumber,HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if( session==null){
			return null;
		}
		return lendFailServer.findPager(page, rows, custName, idNumber, loanId);
	}
	
	/**
	 * 红小宝主动流标接口
	 */
	@RequestMapping(value = "/sys/making/lendfail.do")
	@ResponseBody
	public Json lendFail(String applyId){
		return lendFailServer.lendFail(applyId);
	}

}
