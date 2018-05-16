/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.after;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.after.api.model.param.ApplyExamineParam;
import com.hoomsun.after.api.model.param.MessageUpdateParam;
import com.hoomsun.after.api.model.param.OutBoundParam;
import com.hoomsun.after.api.model.table.ApplyFo;
import com.hoomsun.after.api.model.table.ChangeInfo;
import com.hoomsun.after.api.model.table.OutBound;
import com.hoomsun.after.api.server.OutBoundServer;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年4月25日 <br>
 * 描述：外访申请
 */
@Controller
public class OutBoundController {

	@Autowired
	private OutBoundServer outBoundServer;

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 加载外访提交信息
	 * 
	 * @param LoanId
	 * @return
	 */
	@RequestMapping("/after/outbound/outbound.do")
	public @ResponseBody OutBound getOutbound(String applyId) {

		return outBoundServer.getOutbound(applyId);

	}

	/**
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月21日 <br>
	 * 描述： 加载审批列表（外访申请，根据申请单状态）
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/outbound/examinelist.do")
	public @ResponseBody Pager<ApplyFo> getExamineList(Integer page, Integer pageSize, 
			Integer status,String castName,String cardNo,String conditionCustID,
			String conditionCustName,String loanId,String conNo,Date createTime,HttpServletRequest request) {

		List<ApplyFo> applyFo = outBoundServer.getExamineList(page, pageSize, status,castName,cardNo,conditionCustID,conditionCustName,loanId,conNo,createTime);

		int count = outBoundServer.getExamineListSize(status,castName,cardNo,conditionCustID,conditionCustName,loanId,conNo,createTime);

		Pager<ApplyFo> pager = new Pager<ApplyFo>(applyFo, count);
		return pager;
	}

	/**
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月21日 <br>
	 * 描述： 加载审批列表2（外访申请，根据申请单状态以及管理下人員）
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/outbound/examinelist2.do")
	public @ResponseBody Pager<ApplyFo> getExamineList2(Integer page, Integer pageSize, Integer status, HttpServletRequest request) {

		SessionRouter session = LoginUtil.getLoginSession(request);
		String applicationCastId = session.getEmpWorkNum();
		String applicationCastName = session.getEmpName();
		String empId = session.getEmpId();

		List<ApplyFo> applyFo = outBoundServer.getExamineList2(page, pageSize, applicationCastId, applicationCastName, empId, status);

		int count = outBoundServer.getExamineListSize2(applicationCastId, applicationCastName, empId, status);

		Pager<ApplyFo> pager = new Pager<ApplyFo>(applyFo, count);
		return pager;
	}

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 外访申请
	 * 
	 * @param changeInfo
	 * @return
	 */
	@RequestMapping("/after/outbound/outboundapply.do")
	public @ResponseBody Json saveOutboundApply(OutBoundParam outBoundParma, HttpServletRequest request) {

		SessionRouter session = LoginUtil.getLoginSession(request);
		String applicationCastId = session.getEmpWorkNum();
		String applicationCastName = session.getEmpName();

		Json json = outBoundServer.saveOutboundApply(outBoundParma, applicationCastId, applicationCastName);

		return json;
	}

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 外访1审
	 * 
	 * @param changeInfo
	 * @return
	 */
	@RequestMapping("/after/outbound/outboundapply1.do")
	public @ResponseBody Json saveOutboundApply1(ApplyExamineParam messageUpdateApplyParam, HttpServletRequest request) {

		SessionRouter session = LoginUtil.getLoginSession(request);
		String applicationCastId = session.getEmpWorkNum();
		String applicationCastName = session.getEmpName();

		outBoundServer.saveOutboundApply1(messageUpdateApplyParam, applicationCastId, applicationCastName);
		return new Json(true, "0".equals(messageUpdateApplyParam.getApproveOpinion()) ? "审批已通过" : "审批已拒绝");
	}

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述：外访历史纪录
	 * 
	 * @param changeInfo
	 * @return
	 */
	@RequestMapping("/after/outbound/outboundHistory.do")
	public @ResponseBody Pager<ApplyFo> saveOutboundHistory(Integer page, Integer pageSize,String loanId,String conNo,String castName,String cardNo,Date createTime,String applyStatus,String applyType,HttpServletRequest request) {

		SessionRouter session = LoginUtil.getLoginSession(request);
		String deptId = session.getDeptId();
		String applicationCastId = session.getEmpWorkNum();
		// String applicationCastName = session.getEmpName();

		List<ApplyFo> applyFo = outBoundServer.getOutboundHistory(page, pageSize, deptId, applicationCastId,loanId,conNo,castName,cardNo,createTime,applyStatus,applyType);

		int count = outBoundServer.getOutboundHistoryCount(deptId, applicationCastId,loanId,conNo,castName,cardNo,createTime,applyStatus,applyType);
		Pager<ApplyFo> pager = new Pager<ApplyFo>(applyFo, count);
		return pager;

	}

}
