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
import com.hoomsun.after.api.model.table.ApplyDetail;
import com.hoomsun.after.api.model.table.ApplyFo;
import com.hoomsun.after.api.model.table.ChangeInfo;
import com.hoomsun.after.api.model.vo.BankVo;
import com.hoomsun.after.api.server.MessageUpdateServer;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;

/**
 * 作者：金世强 <br>
 * 创建时间：2018年4月18日 <br>
 * 描述：信息变更申请
 */
@Controller
public class MessageUpdateController {

	@Autowired
	private MessageUpdateServer messageUpdateServer;

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 根据申请单加载申请单流程
	 * 
	 * @param applyId
	 * @return
	 */
	@RequestMapping("/after/flow/apply.do")
	public @ResponseBody List<ApplyDetail> getApplyDetail(String applyId) {

		return messageUpdateServer.getApplyDetail(applyId);
	}

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 加载客户信息变更提交信息
	 * 
	 * @param LoanId
	 * @return
	 */
	@RequestMapping("/after/messageupdate/changeinfo.do")
	public @ResponseBody ChangeInfo getChangeInfo(String applyId) {

		return messageUpdateServer.getChangeInfo(applyId);

	}

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 加载客户原信息
	 * 
	 * @param LoanId
	 * @return
	 */
	@RequestMapping("/after/messageupdate/banklist.do")
	public @ResponseBody BankVo getCustMessageMessage(String loanId) {

		return messageUpdateServer.getCustMessageMessage(loanId);

	}

	// =========================================================================================

	/**
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月21日 <br>
	 * 描述： 加载审批列表（信息变更，根据申请单状态）
	 * 2018-4-28 qn 添加 条件检索  castName  createTime
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/messageupdate/examinelist.do")
	public @ResponseBody Pager<ApplyFo> getExamineList(Integer page, Integer pageSize, Integer status,String castName,String cardNo,String conditionCustID,String conditionCustName,Date createTime, HttpServletRequest request) {

		List<ApplyFo> applyFo = messageUpdateServer.getExamineList(page, pageSize, status,castName,cardNo,conditionCustID,conditionCustName,createTime);

		int count = messageUpdateServer.getExamineListSize(status,castName,cardNo,conditionCustID,conditionCustName,createTime);

		Pager<ApplyFo> pager = new Pager<ApplyFo>(applyFo, count);
		return pager;
	}

	/**
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月21日 <br>
	 * 描述： 加载审批列表（信息变更，根据申请单状态,门店副理只可看到此门店的申请）
	 * 2018-4-28 qn 添加 条件检索  castName  createTime
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/messageupdate/examinelist2.do")
	public @ResponseBody Pager<ApplyFo> getExamineList2(Integer page, Integer pageSize, Integer status,String castName,String cardNo,String conditionCustID,String conditionCustName,Date createTime, HttpServletRequest request) {

		SessionRouter session = LoginUtil.getLoginSession(request);
		String applicationCastId = session.getEmpWorkNum();
		String applicationCastName = session.getEmpName();
		String empId = session.getEmpId();

		List<ApplyFo> applyFo = messageUpdateServer.getExamineList2(page, pageSize,  status, applicationCastId,applicationCastName, empId,castName,cardNo,conditionCustID,conditionCustName,createTime);

		int count = messageUpdateServer.getExamineListSize2(status, applicationCastId, applicationCastName, empId,castName,cardNo,conditionCustID,conditionCustName,createTime);

		Pager<ApplyFo> pager = new Pager<ApplyFo>(applyFo, count);
		return pager;
	}

	// =======================================================================================

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 信息变更申请
	 * 
	 * @param changeInfo
	 * @return
	 */
	@RequestMapping("/after/messageupdate/changeinfoupdate.do")
	public @ResponseBody Json saveMessageUpdate(MessageUpdateParam messageUpdateParam, HttpServletRequest request) {

		SessionRouter session = LoginUtil.getLoginSession(request);
		String applicationCastId = session.getEmpWorkNum();
		String applicationCastName = session.getEmpName();

		Json msg = messageUpdateServer.saveMessageUpdate(messageUpdateParam, applicationCastId, applicationCastName);

		return msg;
	}

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 信息变更1审
	 * 
	 * @param changeInfo
	 * @return
	 */
	@RequestMapping("/after/messageupdate/changeinfoupdate1.do")
	public @ResponseBody Json saveMessageUpdate1(ApplyExamineParam messageUpdateApplyParam, HttpServletRequest request) {

		SessionRouter session = LoginUtil.getLoginSession(request);
		String applicationCastId = session.getEmpWorkNum();
		String applicationCastName = session.getEmpName();

		messageUpdateServer.saveMessageUpdate1(messageUpdateApplyParam, applicationCastId, applicationCastName);
		return new Json(true, "0".equals(messageUpdateApplyParam.getApproveOpinion()) ? "审批已通过" : "审批已拒绝");
	}

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 信息变更2审
	 * 
	 * @param changeInfo
	 * @return
	 */
	@RequestMapping("/after/messageupdate/changeinfoupdate2.do")
	public @ResponseBody Json saveMessageUpdate2(ApplyExamineParam messageUpdateApplyParam, HttpServletRequest request) {

		SessionRouter session = LoginUtil.getLoginSession(request);
		String applicationCastId = session.getEmpWorkNum();
		String applicationCastName = session.getEmpName();

		messageUpdateServer.saveMessageUpdate2(messageUpdateApplyParam, applicationCastId, applicationCastName);
		return new Json(true, "0".equals(messageUpdateApplyParam.getApproveOpinion()) ? "审批已通过" : "审批已拒绝");
	}

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 信息变更历史纪录
	 *  qu 添加 条件检索castName,applyStatus,createTime,applyType
	 * @param changeInfo
	 * @return
	 */
	@RequestMapping("/after/messageupdate/changeinfoupdateHistory.do")
	public @ResponseBody Pager<ApplyFo> saveMessageUpdateHistory(Integer page, Integer pageSize,String castName,String cardNo,String applyStatus,String applyType,Date createTime, HttpServletRequest request) {

		SessionRouter session = LoginUtil.getLoginSession(request);
		String deptId = session.getDeptId();
		String applicationCastId = session.getEmpWorkNum();
		// String applicationCastName = session.getEmpName();

		List<ApplyFo> applyFo = messageUpdateServer.getMessageUpdateHistory(page, pageSize, deptId, applicationCastId,castName,cardNo,applyStatus,applyType,createTime);

		int count = messageUpdateServer.getMessageUpdateHistoryCount(deptId, applicationCastId,castName,cardNo,applyStatus,applyType,createTime);
		Pager<ApplyFo> pager = new Pager<ApplyFo>(applyFo, count);
		return pager;

	}

}
