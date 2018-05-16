/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;
import com.hoomsun.csas.audit.model.ReviewAudit;
import com.hoomsun.csas.audit.model.vo.UserApplyVO;
import com.hoomsun.csas.audit.server.inter.ReviewAuditServerI;

/**
 * 作者：liushuai <br>
 * 创建时间：2018年1月16日 <br>
 * 描述：复议审核控制层
 */
@Controller
public class ReviewAuditController {
	private ReviewAuditServerI reviewAuditServer;
	@Autowired
	public void setreviewAuditServer(ReviewAuditServerI reviewAuditServer) {
		this.reviewAuditServer = reviewAuditServer;
	}
	
	@RequestMapping("/sys/review/pager.do")
	@ResponseBody
	public Pager<UserApplyVO> findPager(Integer rows, Integer page, String custName, String loanId, String nodeStatus, String idNumber,String node, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if( session==null){
			return null;
		}
		String empId = session.getEmpId();
		String deptId = session.getDeptId();
		return reviewAuditServer.findPager(page, rows, custName, idNumber, loanId, empId, nodeStatus,node, deptId);
	}
	
	// 验证是否被签收
	@RequestMapping("/sys/review/checkClaim.do")
	@ResponseBody
	public Json checkClaim(String applyId, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if( session==null){
			return new Json(false, "登录异常，请刷新页面！");
		}
		String empId = session.getEmpId();
		return reviewAuditServer.checkClaim(applyId, empId);
	}
	
	//签收任务
	@RequestMapping("/sys/review/claim.do")
	@ResponseBody
	public Json claimTask(String applyId, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if( session==null){
			return new Json(false, "登录异常，请刷新页面！");
		}
		String empId =  session.getEmpId();
		return reviewAuditServer.claimTask(applyId, empId);
	}
	
	
	// 处理任务
	@RequestMapping(value = "/sys/review/createsubmit.do")
	@ResponseBody
	public Json saveAndSubmitAudit(ReviewAudit reviewAudit, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if( session==null){
			return new Json(false, "登录异常，请刷新页面！");
		}
		reviewAudit.setAuditEmp(session.getEmpId());
		reviewAudit.setAuditEmpName(session.getEmpName());
		return reviewAuditServer.completeTask(reviewAudit);
	}
	
	// 显示详情
	@RequestMapping(value = "/sys/review/reviewinfo.do")
	@ResponseBody
	public  ReviewAudit getReviewAuditInfo(String applyId) {
		return reviewAuditServer.findByApplyId(applyId);
	}
	
	//审核历史
	@RequestMapping(value = "/sys/review/audithistory.do")
	@ResponseBody
	public List<HistoricTaskInstance> findAuditHistory(String applyId, HttpServletRequest request) {
		return reviewAuditServer.findAuditHistory(applyId);
	}
}
