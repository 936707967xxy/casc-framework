/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.anno.Permission;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;
import com.hoomsun.csas.audit.model.FinalAudit;
import com.hoomsun.csas.audit.model.vo.UserApplyVO;
import com.hoomsun.csas.audit.server.inter.FinalAuditServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月1日 <br>
 * 描述：初审审核
 */
@Controller
public class FinalAuditController {
	private FinalAuditServerI finalAuditServer;
	@Autowired
	private TaskService taskService;

	@Autowired
	public void setFinalAuditServer(FinalAuditServerI finalAuditServer) {
		this.finalAuditServer = finalAuditServer;
	}

	@RequestMapping("/sys/finalaudit/pager.do")
	@ResponseBody
	public Pager<UserApplyVO> findPager(Integer rows, Integer page, String custName, String loanId, String nodeStatus, String idNumber, String node, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return null;
		}
		String empId = session.getEmpId();
		String deptId = session.getDeptId();
		return finalAuditServer.findPager(page, rows, custName, idNumber, loanId, empId, nodeStatus, node, deptId);
	}

	// 验证是否被签收
	@Permission("finalaudit_audit")
	@RequestMapping("/sys/finalaudit/checkClaim.do")
	@ResponseBody
	public Json checkClaim(String applyId, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常，请刷新页面！");
		}
		String empId = session.getEmpId();
		return finalAuditServer.checkClaim(applyId, empId);
	}

	// 签收任务
	@Permission("finalaudit_sign")
	@RequestMapping("/sys/finalaudit/claim.do")
	@ResponseBody
	public Json claimTask(String applyId, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常，请刷新页面！");
		}
		String empId = session.getEmpId();
		return finalAuditServer.claimTask(applyId, empId);
	}

	// 处理任务
	@Permission("finalaudit_audit")
	@RequestMapping(value = "/sys/finishaudit/createsubmit.do")
	@ResponseBody
	public Json saveAndSubmitAudit(FinalAudit finalAudit, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常，请刷新页面！");
		}
		finalAudit.setAuditEmp(session.getEmpId());
		finalAudit.setAuditEmpName(session.getEmpName());
		return finalAuditServer.completeTask(finalAudit);
	}

	// 撤销
	@Permission("finalaudit_withdraw")
	@RequestMapping(value = "/sys/finalaudit/withdraw.do")
	@ResponseBody
	public Json rollBack(String applyId, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常，请刷新页面！");
		}
		String empId = session.getEmpId();
		return finalAuditServer.withdraw(applyId, empId);
	}

	// 转办
	@Permission("finalaudit_complaint")
	@RequestMapping(value = "/sys/finalaudit/complaint.do")
	@ResponseBody
	public Json complaint(String applyId, String userId, HttpServletRequest request) {
		if (StringUtils.isAllBlank(applyId, userId)) {
			return new Json(false, "参数异常,转办失败!");
		}

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("finalAudit").singleResult();
		if (null != task) {
			taskService.setAssignee(task.getId(), userId);
			return new Json(true, "转办成功!");
		} else {
			return new Json(false, "任务已经处理,转办失败!");
		}
	}

	// 显示详情
	// @Permission("finalaudit_show")
	@RequestMapping(value = "/sys/finalaudit/finalinfo.do")
	@ResponseBody
	public FinalAudit getFinalAuditInfo(String applyId) {
		return finalAuditServer.findByApplyId(applyId);
	}

	// 审核历史
	@RequestMapping(value = "/sys/finalaudit/audithistory.do")
	@ResponseBody
	public List<HistoricTaskInstance> findAuditHistory(String applyId, HttpServletRequest request) {
		return finalAuditServer.findAuditHistory(applyId);
	}

	//保存审核信息(保存草稿)
	@RequestMapping(value = "/sys/finalaudit/saveauditinfo.do")
	@ResponseBody
	public Json saveAuditInfo(FinalAudit finalAudit, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if( session==null){
			return new Json(false, "登录异常，请刷新页面！");
		}
		finalAudit.setAuditEmp(session.getEmpId());
		finalAudit.setAuditEmpName(session.getEmpName());
		return finalAuditServer.saveAuditInfo(finalAudit);
	}
	
}
