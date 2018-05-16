/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.controller;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;
import com.hoomsun.csas.audit.model.ExcessAudit;
import com.hoomsun.csas.audit.model.FinalAudit;
import com.hoomsun.csas.audit.model.PreAudit;
import com.hoomsun.csas.audit.model.vo.UserApplyVO;
import com.hoomsun.csas.audit.server.inter.ExcessAuditServerI;
import com.hoomsun.csas.audit.server.inter.FinalAuditServerI;
import com.hoomsun.csas.audit.server.inter.PreAuditServerI;

/**
 * 作者：liusong <br>
 * 创建时间：2017年12月16日 <br>
 * 描述：
 */
@Controller
public class ExcessAuditController {
	@Autowired
	private ExcessAuditServerI excessAuditServer;
	@Autowired
	private PreAuditServerI preAuditServer;
	@Autowired
	private FinalAuditServerI finalAuditServer;
	@Autowired
	private TaskService taskService;

	@RequestMapping("/sys/excessaudit/pager.do")
	@ResponseBody
	public Pager<UserApplyVO> findPager(Integer rows, Integer page, String custName, String loanId, String nodeStatus, String idNumber, String node, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		if (sessionRouter == null) {
			return null;
		}
		String empId = sessionRouter.getEmpId();
		String deptId = sessionRouter.getDeptId();
		return excessAuditServer.findPager(page, rows, custName, idNumber, loanId, empId, nodeStatus, node, deptId);
	}

	@RequestMapping(value = "/sys/ecss/query.do")
	@ResponseBody
	public PreAudit findByApplyId(String applyId, HttpServletRequest request) {
		if (StringUtils.isBlank(applyId)) {
			return new PreAudit();
		}
		PreAudit pp = preAuditServer.selectByEcxxess(applyId);
		return pp;
	}

	@RequestMapping(value = "/sys/ecssfinal/query.do")
	@ResponseBody
	public FinalAudit ecssfinal(String applyId, HttpServletRequest request) {
		if (StringUtils.isBlank(applyId)) {
			return new FinalAudit();
		}
		FinalAudit pp = finalAuditServer.selectExcessAudit(applyId);
		return pp;
	}

	// 验证是否被签收
	@RequestMapping("/sys/excessaudit/checkClaim.do")
	@ResponseBody
	public Json checkClaim(String applyId, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		String empId = session.getEmpId();
		return excessAuditServer.checkClaim(applyId, empId);
	}

	// 签收任务
	@RequestMapping("/sys/excessaudit/claim.do")
	@ResponseBody
	public Json claimTask(String applyId, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常，请刷新页面！");
		}
		String empId = session.getEmpId();
		return excessAuditServer.claimTask(applyId, empId);
	}

	// 保存终审审批信息
	@RequestMapping("/sys/excessaudit/savefinalaudit.do")
	@ResponseBody
	public Json saveFinalAudit(ExcessAudit excessAudit, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session != null) {
			excessAudit.setAuditEmp(session.getEmpId());
			excessAudit.setAuditEmpName(session.getEmpName());
		} else {
			return new Json(false, "员工未登录!");
		}
		return excessAuditServer.saveOrUpdateAudit(excessAudit);
	}

	// 保存并发送
	@RequestMapping(value = "/sys/excessaudit/createsubmit.do")
	@ResponseBody
	public Json saveAndSubmitAudit(ExcessAudit excessAudit, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		excessAudit.setAuditEmp(sessionRouter.getEmpId());
		excessAudit.setAuditEmpName(sessionRouter.getEmpName());
		return excessAuditServer.completeTask(excessAudit.getApplyId(), excessAudit);
	}

	// 撤销
	@RequestMapping(value = "/sys/excessaudit/withdraw.do")
	@ResponseBody
	public Json rollBack(String applyId, HttpServletRequest request) {
		String empId = LoginUtil.getLoginSession(request).getEmpId();
		return excessAuditServer.withdraw(applyId, empId);
	}

	// 转办
	@RequestMapping(value = "/sys/excessaudit/complaint.do")
	@ResponseBody
	public Json complaint(String applyId, String userId, HttpServletRequest request) {
		if (StringUtils.isAllBlank(applyId, userId)) {
			return new Json(false, "参数异常,转办失败!");
		}

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("excessAudit").singleResult();
		if (null != task) {
			taskService.setAssignee(task.getId(), userId);
			return new Json(true, "转办成功!");
		} else {
			return new Json(false, "任务已经处理,转办失败!");
		}
	}

	// 显示详情
	@RequestMapping(value = "/sys/excessaudit/finalinfo.do")
	@ResponseBody
	public ExcessAudit getFinalAuditInfo(String applyId) {
		return excessAuditServer.selectByExcessAu(applyId);
	}
}
