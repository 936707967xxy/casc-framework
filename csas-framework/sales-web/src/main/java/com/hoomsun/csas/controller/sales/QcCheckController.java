/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.sales;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.hoomsun.core.anno.Permission;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;
import com.hoomsun.csas.sales.api.model.QcCheck;
import com.hoomsun.csas.sales.api.model.SavingsCard;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.model.UserPbccrcHtml;
import com.hoomsun.csas.sales.api.model.vo.RollBackInfoVo;
import com.hoomsun.csas.sales.api.server.inter.QcCheckServerI;
import com.hoomsun.csas.sales.api.server.inter.SavingsCardServerI;
import com.hoomsun.csas.sales.api.server.inter.UserApplyServerI;
import com.hoomsun.csas.sales.api.server.inter.UserPbccrcHtmlServerI;

/**
 * 作者：liushuai <br>
 * 创建时间：2017年12月4日 <br>
 * 描述：
 */
@Controller
public class QcCheckController {
	@Autowired
	private QcCheckServerI qcCheckServer;
	@Autowired
	private TaskService taskService;
	@Autowired
	private SavingsCardServerI savingsCardServer;
	@Autowired
	private UserPbccrcHtmlServerI userPbccrcHtmlServer;
	@Autowired
	private UserApplyServerI userApplyServer;
	
	
	@RequestMapping("/sys/savingscard/findbyapplyid.do")
	@ResponseBody
	public SavingsCard saveSavingsCardBills(String applyId, HttpServletRequest request) {
		SavingsCard sc = savingsCardServer.selectByApplyId(applyId);
		if (sc == null) {
			sc = new SavingsCard();
		}
		return sc;
	}

	@RequestMapping("/sys/pbccrc/html.do")
	public void showHtmlData(String applyId, HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();

			if (StringUtils.isAllBlank(applyId)) {
				writer.write("<h1>数据为空！</h1>");
				writer.flush();
				writer.close();
				return;
			}

			UserPbccrcHtml pbccrcHtml = userPbccrcHtmlServer.findByApplyId(applyId);
			if (null == pbccrcHtml) {
				writer.write("<h1>数据为空！</h1>");
				writer.flush();
				writer.close();
				return;
			}

			String html = pbccrcHtml.getHtmlData();
			if (StringUtils.isAllBlank(html)) {
				writer.write("<h1>数据为空！</h1>");
				writer.flush();
				writer.close();
				return;
			}

			writer.write(html);
			writer.flush();
			writer.close();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/sys/qccheck/query.do")
	@ResponseBody
	public UserApply findById(String applyId) {
		return qcCheckServer.findById(applyId);
	}

	@RequestMapping(value = "/sys/qccheck/datagrid.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Pager<UserApply> getPager(Integer page, Integer rows, String custName, String loanId, String nodeStatus, String idNumber, String salesEmpName, String custMobile, String node, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return null;
		}
		return qcCheckServer.findPagerData(page, rows, custName, idNumber, loanId, nodeStatus, salesEmpName, custMobile, node, session);
	}

	// 验证是否被签收
	@Permission("qc_audit")
	@RequestMapping("/sys/qccheck/checkClaim.do")
	@ResponseBody
	public Json checkClaim(String applyId, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常，请刷新页面！");
		}
		String empId = session.getEmpId();
		return qcCheckServer.checkClaim(applyId, empId);
	}

	// 签收
	@Permission("qc_sign")
	@RequestMapping("/sys/qccheck/claim.do")
	@ResponseBody
	public Json claimTask(String applyId, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常，请刷新页面！");
		}
		String empId = session.getEmpId();
		return qcCheckServer.claimTask(applyId, empId);
	}

	// 撤回
	@Permission("qc_withdraw")
	@RequestMapping(value = "/sys/qccheck/withdraw.do")
	@ResponseBody
	public Json withdraw(String applyId, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常，请刷新页面！");
		}
		String empId = session.getEmpId();
		return qcCheckServer.withdraw(applyId, empId);
	}

	// 转办
	@Permission("qc_transfer")
	@RequestMapping(value = "/sys/qccheck/complaint.do")
	@ResponseBody
	public Json complaint(String applyId, String userId, HttpServletRequest request) {
		if (StringUtils.isAllBlank(applyId, userId)) {
			return new Json(false, "参数异常,转办失败!");
		}
		
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("qcCheck").singleResult();
		if (null != task) {
			String createEmp = userApplyServer.findCreateEmp(applyId);
			if(StringUtils.isNotBlank(createEmp)) {
				if (createEmp.equals(userId)) {
					return new Json(false, "不能转办给录单人!");
				}
			}
			
			taskService.setAssignee(task.getId(), userId);
			return new Json(true, "转办成功!");
		} else {
			return new Json(false, "任务已经处理,转办失败!");
		}
	}

	// 发送任务
	@Permission("qc_audit")
	@RequestMapping(value = "/sys/qccheck/createsubmit.do")
	@ResponseBody
	public Json saveAndSubmitAudit(String applyId, String remarks, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常，请刷新页面！");
		}
		String empId = session.getEmpId();
		String empName = session.getEmpName();
		return qcCheckServer.completeTask(applyId, empId, empName, remarks);
	}

	// 退回
	@Permission("qc_audit")
	@RequestMapping(value = "/sys/qccheck/rollback.do")
	@ResponseBody
	public Json rollback(String applyId, String remarks, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常，请刷新页面！");
		}
		String empId = session.getEmpId();
		String empName = session.getEmpName();
		return qcCheckServer.rollback(applyId, empId, empName, remarks);
	}

	// 拒贷
	@Permission("qc_audit")
	@RequestMapping(value = "/sys/qccheck/reject.do")
	@ResponseBody
	public Json reject(String applyId, String remarks, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常，请刷新页面！");
		}
		String empId = session.getEmpId();
		String empName = session.getEmpName();
		return qcCheckServer.reject(applyId, empId, empName, remarks);
	}

	// 客户放弃
	@Permission("qc_audit")
	@RequestMapping(value = "/sys/qccheck/waiver.do")
	@ResponseBody
	public Json waiver(String applyId, String remarks, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常，请刷新页面！");
		}
		String empId = session.getEmpId();
		String empName = session.getEmpName();
		return qcCheckServer.waiver(applyId, empId, empName, remarks);
	}

	// 显示详情
	@Permission("qc_show")
	@RequestMapping(value = "/sys/qccheck/qccheckinfo.do")
	@ResponseBody
	public QcCheck getFinalAuditInfo(String applyId) {
		return qcCheckServer.findByApplyId(applyId);
	}

	// 初审退回信息列表
	@RequestMapping(value = "/sys/qccheck/prerollbackhis.do")
	@ResponseBody
	public List<RollBackInfoVo> getPreRollBackInfo(String applyId) {
		return qcCheckServer.findPreRollBackHis(applyId);
	}

}
