/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.settle.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.SysContract;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.server.inter.SysContractServerI;
import com.hoomsun.core.util.LoginUtil;
import com.hoomsun.csas.settle.model.LoanRecord;
import com.hoomsun.csas.settle.model.vo.LoanVo;
import com.hoomsun.csas.settle.server.inter.LoanServerI;

/**
 * 作者：liming <br>
 * 创建时间：2017年12月12日 <br>
 * 描述：放款控制层
 */
@Controller
public class LoanController {

	@Autowired
	private LoanServerI loanServer;
	@Autowired
	private TaskService taskService;

	// 查询合同信息
	@Autowired
	private SysContractServerI sysContractServer;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	/***
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月16日 <br>
	 * 描述： 放款数据展示
	 * 
	 * @param page
	 * @param rows
	 * @param custName
	 * @param loanId
	 * @param nodeStatus
	 * @param idNumber
	 * @param custMobile
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sys/loan/pager.do", method = RequestMethod.POST)
	@ResponseBody
	public Pager<LoanVo> findAllDate(Integer page, Integer rows, String custName, String conCode,String idNumber, String nodeStatus,String custMobile, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String empId = sessionRouter.getEmpId();
		String deptId = sessionRouter.getDeptId();// 部门id
		Pager<LoanVo> LoanInfoVo = loanServer.findAllData(page, rows, custName, custMobile, conCode,idNumber, empId, deptId, nodeStatus);
		return LoanInfoVo;
	}
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月20日 <br>
	 * 描述： 放款审核页面数据  也即是合同数据
	 * @param conId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sys/loan/audit.do")
	@ResponseBody
	public SysContract findContractPlan(String conId, HttpServletRequest request) {
		return sysContractServer.findByConId(conId);
	}
	
	
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月18日 <br>
	 * 描述： 调用core的controller里的查询合同
	 * 
	 * @param applyId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sys/contract/loandetail.do", method = RequestMethod.GET)
	@ResponseBody
	public SysContract contracts(String applyId, HttpServletRequest request) {
		SysContract contract = sysContractServer.selectByApplyId(applyId);
		return contract;
	}

	
	
	// 通过
	@RequestMapping(value = "/sys/loan/complete.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json complete(LoanRecord loanRecord,  HttpServletRequest request){
		SessionRouter session = LoginUtil.getLoginSession(request);
		return loanServer.completeTask(loanRecord, session);
	}
	
	// 退回
	@RequestMapping(value = "/sys/loan/rollback.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json rollback(LoanRecord loanRecord, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		return loanServer.rollback(loanRecord, session);
	}
	// 转办方法执行
	@RequestMapping(value = "/sys/loan/savecomplaint.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json complaint(String applyId, String userId, HttpServletRequest request) {
		if (StringUtils.isAllBlank(applyId, userId)) {
			return new Json(false, "参数异常,转办失败!");
		}
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String empId = sessionRouter.getEmpId();
		if (empId.equals(userId)) {
			return new Json(false, "相同员工不能转办!");
		} else {
			Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskAssignee(empId).singleResult();
			if (null != task) {
				taskService.claim(task.getId(), userId);
				return new Json(true, "转办成功!");
			} else {
				return new Json(false, "任务已经处理,转办失败!");
			}
		}
	}
	// 签收
	@RequestMapping("/sys/loan/claim.do")
	@ResponseBody
	public Json claimTask(String applyId, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		String empId = session.getEmpId();
		return loanServer.claimTask(applyId, empId);
	}

	// 验证是否已经签收
	@RequestMapping("/sys/loan/checkclaim.do")
	@ResponseBody
	public Json checkClaimTask(String applyId, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		String empId = session.getEmpId();
		return loanServer.checkClaim(applyId, empId);
	}
	
	@RequestMapping("/sys/loan/record/bycon.do")
	@ResponseBody
	public LoanRecord findRecord(String conId, HttpServletRequest request) {
		return loanServer.findByConId(conId);
	}
}
