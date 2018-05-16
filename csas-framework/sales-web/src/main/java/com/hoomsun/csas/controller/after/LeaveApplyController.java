/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.after;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.after.api.model.param.ApplyExamineParam;
import com.hoomsun.after.api.model.param.LeaveApplyParams;
import com.hoomsun.after.api.model.param.MessageUpdateParam;
import com.hoomsun.after.api.model.table.ApplyFo;
import com.hoomsun.after.api.server.LeaveApplyServer;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年4月27日 <br>
 * 描述：留案申请
 */
@Controller
public class LeaveApplyController {

	@Autowired
	private LeaveApplyServer leaveApplyServer;

	/**
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月21日 <br>
	 * 描述： 加载审批列表（留案申请，根据申请单状态）
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/leaveapply/examinelist.do")
	public @ResponseBody Pager<ApplyFo> getExamineList(Integer page, Integer pageSize, Integer status, HttpServletRequest request) {

		List<ApplyFo> applyFo = leaveApplyServer.getExamineList(page, pageSize, status);

		int count = leaveApplyServer.getExamineListSize(status);

		Pager<ApplyFo> pager = new Pager<ApplyFo>(applyFo, count);
		return pager;
	}

	/**
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月21日 <br>
	 * 描述： 加载审批列表（留案申请，根据申请单状态,门店副理只可看到此门店的申请）
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/leaveapply/examinelist2.do")
	public @ResponseBody Pager<ApplyFo> getExamineList2(Integer page, Integer pageSize, Integer status, HttpServletRequest request) {

		SessionRouter session = LoginUtil.getLoginSession(request);
		String applicationCastId = session.getEmpWorkNum();
		String applicationCastName = session.getEmpName();
		String empId = session.getEmpId();

		List<ApplyFo> applyFo = leaveApplyServer.getExamineList2(page, pageSize, status, applicationCastId, applicationCastName, empId);

		int count = leaveApplyServer.getExamineListSize2(status, applicationCastId, applicationCastName, empId);

		Pager<ApplyFo> pager = new Pager<ApplyFo>(applyFo, count);
		return pager;
	}

	// =======================================================================================

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 留案申请
	 * 
	 * @param changeInfo
	 * @return
	 */
	@RequestMapping("/after/leaveapply/leaveapply.do")
	public @ResponseBody Json saveLeaveApply(LeaveApplyParams leaveApplyParams, HttpServletRequest request) {

		SessionRouter session = LoginUtil.getLoginSession(request);
		String applicationCastId = session.getEmpWorkNum();
		String applicationCastName = session.getEmpName();

		String msg = leaveApplyServer.saveLeaveApply(leaveApplyParams, applicationCastId, applicationCastName);

		return new Json(true, msg);
	}

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 留案1审
	 * 
	 * @param changeInfo
	 * @return
	 */
	@RequestMapping("/after/leaveapply/leaveapply1.do")
	public @ResponseBody Json saveLeaveApply1(ApplyExamineParam leaveApplyParam, HttpServletRequest request) {

		SessionRouter session = LoginUtil.getLoginSession(request);
		String applicationCastId = session.getEmpWorkNum();
		String applicationCastName = session.getEmpName();

		leaveApplyServer.saveLeaveApply1(leaveApplyParam, applicationCastId, applicationCastName);
		return new Json(true, "0".equals(leaveApplyParam.getApproveOpinion()) ? "审批已通过" : "审批已拒绝");
	}

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 留案2审
	 * 
	 * @param changeInfo
	 * @return
	 */
	@RequestMapping("/after/leaveapply/leaveapply2.do")
	public @ResponseBody Json saveMessageUpdate2(ApplyExamineParam leaveApplyParam, HttpServletRequest request) {

		SessionRouter session = LoginUtil.getLoginSession(request);
		String applicationCastId = session.getEmpWorkNum();
		String applicationCastName = session.getEmpName();

		leaveApplyServer.saveLeaveApply2(leaveApplyParam, applicationCastId, applicationCastName);
		return new Json(true, "0".equals(leaveApplyParam.getApproveOpinion()) ? "审批已通过" : "审批已拒绝");
	}

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 留案史纪录
	 * 
	 * @param changeInfo
	 * @return
	 */
	@RequestMapping("/after/leaveapply/leaveapplyHistory.do")
	public @ResponseBody Pager<ApplyFo> saveLeaveapplyHistory(Integer page, Integer pageSize, HttpServletRequest request) {

		SessionRouter session = LoginUtil.getLoginSession(request);
		String deptId = session.getDeptId();
		String applicationCastId = session.getEmpWorkNum();
		// String applicationCastName = session.getEmpName();

		List<ApplyFo> applyFo = leaveApplyServer.getLeaveapplyHistory(page, pageSize, deptId, applicationCastId);

		int count = leaveApplyServer.getLeaveapplyHistoryCount(deptId, applicationCastId);
		Pager<ApplyFo> pager = new Pager<ApplyFo>(applyFo, count);
		return pager;

	}

}
