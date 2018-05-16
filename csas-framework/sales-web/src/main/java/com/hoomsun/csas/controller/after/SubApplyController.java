package com.hoomsun.csas.controller.after;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.after.api.model.param.ApplyExamineParam;
import com.hoomsun.after.api.model.param.SubApplyParam;
import com.hoomsun.after.api.model.table.ApplyFo;
import com.hoomsun.after.api.model.table.OverdueRecord;
import com.hoomsun.after.api.model.table.Sub;
import com.hoomsun.after.api.server.SubApplyServer;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;

/**
 * 
 * 作者：shiqiangjin <br>
 * 创建时间：2018年4月23日 <br>
 * 描述： 减免申请相关
 *
 */
@Controller
public class SubApplyController {

	@Autowired
	private SubApplyServer subApplyServer;

	/**
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月21日 <br>
	 * 描述： 加载审批列表（减免申请，根据申请单状态）
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/subapply/examinelist.do")
	public @ResponseBody Pager<ApplyFo> getExamineList(Integer page, Integer pageSize, Integer status,String castName,String cardNo,String applyType,String loanId,String conNo,String conditionCustID,String conditionCustName,Date createTime, HttpServletRequest request) {

		List<ApplyFo> applyFo = subApplyServer.getExamineList(page, pageSize, status,castName,cardNo,applyType,loanId,conNo,conditionCustID,conditionCustName,createTime);

		int count = subApplyServer.getExamineListSize(status,castName,cardNo,applyType,loanId,conNo,conditionCustID,conditionCustName,createTime);

		Pager<ApplyFo> pager = new Pager<ApplyFo>(applyFo, count);
		return pager;
	}

	/**
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月21日 <br>
	 * 描述： 加载审批列表（见面申请，根据申请单状态,门店副理只可看到此门店的申请）
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/subapply/examinelist2.do")
	public @ResponseBody Pager<ApplyFo> getExamineList2(Integer page, Integer pageSize, Integer status,String castName,String cardNo,String applyType,String loanId,String conNo,String conditionCustID,String conditionCustName,Date createTime,  HttpServletRequest request) {

		SessionRouter session = LoginUtil.getLoginSession(request);
		String applicationCastId = session.getEmpWorkNum();
		String applicationCastName = session.getEmpName();
		String empId = session.getEmpId();

		List<ApplyFo> applyFo = subApplyServer.getExamineList2(page, pageSize, status, applicationCastId, applicationCastName, empId,castName,cardNo,applyType,loanId,conNo,conditionCustID,conditionCustName,createTime);

		int count = subApplyServer.getExamineListSize2(status, applicationCastId, applicationCastName, empId,castName,cardNo,loanId,conNo,applyType,conditionCustID,conditionCustName,createTime);

		Pager<ApplyFo> pager = new Pager<ApplyFo>(applyFo, count);
		return pager;
	}

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月23日 <br>
	 * 描述：获取客户的最小逾期期次逾期数据
	 * 
	 * @param loanId
	 * @return
	 */
	@RequestMapping("/after/singelsubapply/minoverdue.do")
	public @ResponseBody OverdueRecord getMinOverdue(String loanId) {

		return subApplyServer.getMinOverdue(loanId);
	}

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月23日 <br>
	 * 描述：获取客户的所有逾期数据
	 * 
	 * @param loanId
	 * @return
	 */
	@RequestMapping("/after/allsubapply/alloverdue.do")
	public @ResponseBody List<OverdueRecord> getAllOverdue(String loanId) {

		return subApplyServer.getAllOverdue(loanId);
	}

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月23日 <br>
	 * 描述： 单月减免计算金额
	 */
	@RequestMapping("/after/singelsubapply/singelreductionmoney.do")
	public @ResponseBody Map<String, Object> getSingelReductionApplyMoney(String loanId, Date subDate, BigDecimal repaymentMoney) {

		return subApplyServer.getSingelReductionApplyMoney(loanId, subDate, repaymentMoney);

	}

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月23日 <br>
	 * 描述： 多月减免违法金计算
	 * 
	 * @return
	 */
	@RequestMapping("/after/allsubapply/allreductionmoney.do")
	public @ResponseBody Json getAllReductionApplyMoney(String loanId, Date subDate, BigDecimal repaymentMoney) {

		return subApplyServer.getAllReductionApplyMoney(loanId, subDate, repaymentMoney);
	}

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 加载客户逾期减免提交信息
	 * 
	 * @param LoanId
	 * @return
	 */
	@RequestMapping("/after/subapply/subapplylist.do")
	public @ResponseBody List<Sub> getChangeInfo(String applyId) {

		return subApplyServer.getSub(applyId);

	}

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月23日 <br>
	 * 描述：减免申请
	 * 
	 * @return
	 */
	@RequestMapping("/after/subapply/subapply.do")
	public @ResponseBody Json saveSubapply(@RequestBody JSONObject jsonObject, HttpServletRequest request) {

		SubApplyParam subApplyParam = jsonObject.toJavaObject(SubApplyParam.class);

		SessionRouter session = LoginUtil.getLoginSession(request);
		String applicationCastId = session.getEmpWorkNum();
		String applicationCastName = session.getEmpName();

		Json json = subApplyServer.saveSubapply(subApplyParam, applicationCastId, applicationCastName);

		return json;
	}

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月23日 <br>
	 * 描述：减免一级审批
	 * 
	 * @return
	 */
	@RequestMapping("/after/subapply/saveSubapply1.do")
	public @ResponseBody Json saveSubapply1(ApplyExamineParam subExamineParam, HttpServletRequest request) {

		SessionRouter session = LoginUtil.getLoginSession(request);
		String applicationCastId = session.getEmpWorkNum();
		String applicationCastName = session.getEmpName();

		subApplyServer.saveSubapply1(subExamineParam, applicationCastId, applicationCastName);
		return new Json(true, "0".equals(subExamineParam.getApproveOpinion()) ? "审批已通过" : "审批已拒绝");
	}

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月23日 <br>
	 * 描述：减免二级审批
	 * 
	 * @return
	 */
	@RequestMapping("/after/subapply/saveSubapply2.do")
	public @ResponseBody Json saveSubapply2(ApplyExamineParam subExamineParam, HttpServletRequest request) {

		SessionRouter session = LoginUtil.getLoginSession(request);
		String applicationCastId = session.getEmpWorkNum();
		String applicationCastName = session.getEmpName();

		subApplyServer.saveSubapply2(subExamineParam, applicationCastId, applicationCastName);
		return new Json(true, "0".equals(subExamineParam.getApproveOpinion()) ? "审批已通过" : "审批已拒绝");
	}

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述：减免历史纪录
	 * 
	 * @param changeInfo
	 * @return
	 */
	@RequestMapping("/after/subapply/subapplyHistory.do")
	public @ResponseBody Pager<ApplyFo> savesubapplyHistory(Integer page, Integer pageSize,String castName,String cardNo,String applyType,String applyStatus,String loanId,String conNo,Date createTime, HttpServletRequest request) {

		SessionRouter session = LoginUtil.getLoginSession(request);
		String deptId = session.getDeptId();
		String applicationCastId = session.getEmpWorkNum();
		// String applicationCastName = session.getEmpName();

		List<ApplyFo> applyFo = subApplyServer.getSubapplyHistory(page, pageSize, deptId, applicationCastId,castName,cardNo,applyType,applyStatus,loanId,conNo,createTime);

		int count = subApplyServer.getSubapplyHistoryCount(deptId, applicationCastId,castName,cardNo,applyType,applyStatus,loanId,conNo,createTime);
		Pager<ApplyFo> pager = new Pager<ApplyFo>(applyFo, count);
		return pager;

	}

	@RequestMapping("/after/subapply/votedown.do")
	public @ResponseBody Json saveVoteDown(ApplyExamineParam subExamineParam, HttpServletRequest request) {

		SessionRouter session = LoginUtil.getLoginSession(request);
		String applicationCastId = session.getEmpWorkNum();
		String applicationCastName = session.getEmpName();

		Json json=	subApplyServer.saveVoteDown(subExamineParam, applicationCastId, applicationCastName);
		return json;
	}

}
