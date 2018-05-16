/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.core.model.SysContract;
import com.hoomsun.core.server.inter.SysContractServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月21日 <br>
 * 描述：合同数据查询接口
 */
@Controller
public class ContractController {
	@Autowired
	private SysContractServerI contractServer;

	@RequestMapping(value = "/sys/contract/queryapp.do")
	@ResponseBody
	public SysContract findByApplyId(String applyId, HttpServletRequest request) {
		return contractServer.findByApplyId(applyId);
	}

	@RequestMapping(value = "/sys/contract/query.do")
	@ResponseBody
	public SysContract findByContract(String conId, HttpServletRequest request) {
		return contractServer.findByConId(conId);
	}
}
