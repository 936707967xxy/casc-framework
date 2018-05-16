/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.csas.audit.exception.AuditException;
import com.hoomsun.csas.audit.model.vo.HistoricTaskVo;
import com.hoomsun.csas.audit.server.inter.ActivitiServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月28日 <br>
 * 描述：流程相关的业务控制
 */
@Controller
public class ActivitiController {
	
	private ActivitiServerI activitiServer;

	@Autowired
	public void setActivitiServer(ActivitiServerI activitiServer) {
		this.activitiServer = activitiServer;
	}
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月28日 <br>
	 * 描述： 获取审批历史数据
	 * @param applyId
	 * @param request
	 * @return
	 */
	@RequestMapping("/sys/task/audit/historic.do")
	@ResponseBody
	public List<HistoricTaskVo> findAuditHistoric(String applyId,HttpServletRequest request) {
		if (StringUtils.isBlank(applyId)) {
			throw new AuditException("参数异常!");
		}
		return activitiServer.findAuditHistoric(applyId);
	}
	
}
