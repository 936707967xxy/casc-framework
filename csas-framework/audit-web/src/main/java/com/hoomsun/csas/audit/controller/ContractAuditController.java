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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;
import com.hoomsun.csas.audit.model.ContractCheck;
import com.hoomsun.csas.audit.model.vo.UserApplyConVO;
import com.hoomsun.csas.audit.server.inter.ContractAuditServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月12日 <br>
 * 描述：合同审核的页面
 */
@Controller
public class ContractAuditController {
	@Autowired
	private ContractAuditServerI contractAuditServer;
	@Autowired
	private TaskService taskService;

	@RequestMapping("/sys/conaudit/pager.do")
	@ResponseBody
	public Pager<UserApplyConVO> findPager(Integer rows, Integer page, String custName, String loanId, String nodeStatus, String idNumber, String node, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		if (sessionRouter == null) {
			return null;
		}
		String empId = sessionRouter.getEmpId();
		String deptId = sessionRouter.getDeptId();
		return contractAuditServer.findPager(page, rows, custName, idNumber, loanId, empId, nodeStatus, node, deptId);
	}

	// 签收任务
	@RequestMapping("/sys/conaudit/claim.do")
	@ResponseBody
	public Json claimTask(String applyId, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String empId = sessionRouter.getEmpId();
		return contractAuditServer.claimTask(applyId, empId);
	}

	// 保存审核意见
	@RequestMapping("/sys/conaudit/save.do")
	@ResponseBody
	public Json saveAuditOption(ContractCheck audit, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String empId = sessionRouter.getEmpId();
		audit.setCheckEmpName(sessionRouter.getEmpName());
		audit.setCheckEmp(sessionRouter.getEmpId());
		return contractAuditServer.saveAudit(audit, empId);
	}

	// 审核
	@RequestMapping("/sys/conaudit/complete.do")
	@ResponseBody
	public Json completeTask(String applyId, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String empId = sessionRouter.getEmpId();
		return contractAuditServer.completeTask(applyId, empId);
	}

	// 退回
	@RequestMapping("/sys/conaudit/rollback.do")
	@ResponseBody
	public Json rollbackTask(String applyId, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String empId = sessionRouter.getEmpId();
		return contractAuditServer.rollbackTask(applyId, empId);
	}

	// 撤销
	@RequestMapping("/sys/conaudit/withdraw.do")
	@ResponseBody
	public Json withdrawTask(String applyId, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String empId = sessionRouter.getEmpId();
		return contractAuditServer.withdrawTask(applyId, empId);
	}

	// 转办执行
	@RequestMapping(value = "/sys/conaudit/complaint.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json complaint(String applyId, String userId, HttpServletRequest request) {
		if (StringUtils.isAllBlank(applyId, userId)) {
			return new Json(false, "参数异常,转办失败!");
		}

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("conAudit").singleResult();
		if (null != task) {
			taskService.setAssignee(task.getId(), userId);
			return new Json(true, "转办成功!");
		} else {
			return new Json(false, "任务已经处理,转办失败!");
		}
	}

	// 验证是否已经签收
	@RequestMapping(value = "/sys/conaudit/checkclaim.do")
	@ResponseBody
	public Json checkClaim(String applyId, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String empId = sessionRouter.getEmpId();
		return contractAuditServer.checkClaim(applyId, empId);
	}

	@RequestMapping(value = "/sys/conaudit/queryapp.do")
	@ResponseBody
	public ContractCheck findByApplyId(String applyId, HttpServletRequest requests) {
		return contractAuditServer.findByApplyId(applyId);
	}
}
