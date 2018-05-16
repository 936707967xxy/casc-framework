/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.server.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.after.api.model.param.ApplyExamineParam;
import com.hoomsun.after.api.model.param.OutBoundParam;
import com.hoomsun.after.api.model.table.ApplyDetail;
import com.hoomsun.after.api.model.table.ApplyFo;
import com.hoomsun.after.api.model.table.OutBound;
import com.hoomsun.after.api.server.OutBoundServer;
import com.hoomsun.after.api.util.IDGenerator;
import com.hoomsun.after.dao.ApplyDetailMapper;
import com.hoomsun.after.dao.ApplyFoMapper;
import com.hoomsun.after.dao.OutBoundMapper;
import com.hoomsun.common.model.Json;
import com.hoomsun.core.dao.SysEmployeeMapper;
import com.hoomsun.core.model.SysEmployee;
import com.hoomsun.core.util.PrimaryKeyUtil;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年4月25日 <br>
 * 描述：外访
 */
@Service("outBoundServer")
public class OutBoundServerImpl implements OutBoundServer {

	@Autowired
	private OutBoundMapper outBoundMapper;
	@Autowired
	private ApplyFoMapper applyFoMapper;
	@Autowired
	private ApplyDetailMapper applyDetailMapper;
	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;

	@Override
	public OutBound getOutbound(String applyId) {

		return outBoundMapper.getOutbound(applyId);
	}

	@Override
	public List<ApplyFo> getExamineList(Integer page, Integer pageSize, Integer status,
			String castName,String cardNo,String conditionCustID,String conditionCustName,String loanId,String conNo,
			Date createTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("pageSize", pageSize);
		params.put("status", status);
		params.put("castName", castName);
		params.put("cardNo", cardNo);
		params.put("conditionCustID", conditionCustID);
		params.put("conditionCustName", conditionCustName);
		params.put("loanId", loanId);
		params.put("conNo", conNo);
		params.put("createTime", createTime);
		
		return applyFoMapper.selectOutBoundByStstus(params);
	}

	@Override
	public int getExamineListSize(Integer status,String castName,String cardNo,
			String conditionCustID,String conditionCustName,String loanId,String conNo,Date createTime) {
		Map<String, Object> params = new HashMap<String, Object>();
	
		params.put("status", status);
		params.put("castName", castName);
		params.put("cardNo", cardNo);
		params.put("conditionCustID", conditionCustID);
		params.put("conditionCustName", conditionCustName);
		params.put("loanId", loanId);
		params.put("conNo", conNo);
		params.put("createTime", createTime);
		return applyFoMapper.selectOutBoundByStstusCount(params);
	}

	@Override
	public List<ApplyFo> getExamineList2(Integer page, Integer pageSize, String applicationCastId, String applicationCastName, String empId, Integer status) {
		List<SysEmployee> users = sysEmployeeMapper.findDeptEmp(empId);
		List<String> userIds = new ArrayList<String>();
		for (SysEmployee sysEmployee : users) {
			userIds.add(sysEmployee.getEmpWorkNum());

		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("pageSize", pageSize);
		params.put("status", status);
		params.put("userIds", userIds);

		return applyFoMapper.selectOutBoundByStstusAndUsers(params);
	}

	@Override
	public int getExamineListSize2(String applicationCastId, String applicationCastName, String empId, Integer status) {
		List<SysEmployee> users = sysEmployeeMapper.findDeptEmp(empId);
		List<String> userIds = new ArrayList<String>();
		for (SysEmployee sysEmployee : users) {
			userIds.add(sysEmployee.getEmpWorkNum());

		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		params.put("userIds", userIds);

		return applyFoMapper.selectOutBoundByStstusCountAndUsers(params);
	}

	@Override
	public Json saveOutboundApply(OutBoundParam outBoundParma, String applicationCastId, String applicationCastName) {
		// 申请单号
		String applyId = outBoundParma.getApplyId();

		/**
		 * 判断次单子是否已有外访申请
		 */
		List<OutBound> outBounds = outBoundMapper.selectByLoanId(outBoundParma.getLoanId());

		if (outBounds != null && outBounds.size() > 0) {

			for (OutBound outBound : outBounds) {
				if ("2".equals(outBound.getOutboundStatus())) {

					return new Json(false, "次客户已存在外放申请中的流程！");
				}

				if ("0".equals(outBound.getOutboundStatus())) {

					return new Json(false, "次客户已在外放部门不得再次发起申请");
				}

			}

		}

		if (applyId == null || "".equals(applyId)) {
			applyId = IDGenerator.getNextID("UPIMG");
		}

		// 变更类型
		String applyType = outBoundParma.getApplyType();

		// 联系电话变更
		if ("outBound".equals(applyType)) {
			/**
			 * 插入外访申请资料
			 */
			OutBound outBound = new OutBound();
			outBound.setId(PrimaryKeyUtil.getPrimaryKey());
			outBound.setLoanId(outBoundParma.getLoanId());
			outBound.setConNo(outBoundParma.getCardNo());
			outBound.setOutboundAddress(outBoundParma.getOutboundAddress());
			outBound.setOverdueStatus(outBoundParma.getOverdueStatus());
			outBound.setOutboundDate(outBoundParma.getOutboundDate());
			outBound.setOverdueDesc(outBoundParma.getOverdueDesc());
			outBound.setApplyDesc(outBoundParma.getApplyDesc());
			outBound.setOutboundStatus("2");
			outBound.setOutboundId(outBoundParma.getOutboundId());
			outBound.setOutboundName(outBoundParma.getOutboundName());
			outBound.setOutboundDesc(outBoundParma.getOutboundDesc());
			outBound.setCreateTime(new Date());
			outBound.setUpdateDate(null);
			outBound.setApplyId(applyId);
			outBound.setCastName(outBoundParma.getCastName());
			outBound.setCardNo(outBoundParma.getCardNo());

			outBoundMapper.insert(outBound);
			/**
			 * 插入申请单表
			 */
			ApplyFo applyFo = new ApplyFo();
			applyFo.setId(PrimaryKeyUtil.getPrimaryKey());
			applyFo.setApplyId(applyId);
			applyFo.setApplyType("outBound");
			applyFo.setApplicationCastId(applicationCastId);
			applyFo.setApplicationCastName(applicationCastName);
			applyFo.setApplicationDesc(outBoundParma.getApplyDesc());
			applyFo.setCastName(outBoundParma.getCastName());
			applyFo.setCardNo(outBoundParma.getCardNo());
			applyFo.setConNo(outBoundParma.getConNo());
			applyFo.setLoanId(outBoundParma.getLoanId());
			applyFo.setApplyStatus("2");
			applyFo.setApplyReturn(null);
			applyFo.setCreateTime(new Date());
			applyFo.setUpdateDate(null);
			applyFoMapper.insert(applyFo);

			/**
			 * 插入流程环节表
			 */
			ApplyDetail applyDetail = new ApplyDetail();
			applyDetail.setId(PrimaryKeyUtil.getPrimaryKey());
			applyDetail.setApplyId(applyId);
			applyDetail.setApplyType("outBound");
			applyDetail.setApplyStatus("2");
			applyDetail.setApproveId(applicationCastId);
			applyDetail.setApproveName(applicationCastName);
			applyDetail.setApproveDesc(outBoundParma.getApplyDesc());
			applyDetail.setApproveOpinion("0");
			applyDetail.setCreateTime(new Date());
			applyDetailMapper.insert(applyDetail);

		}
		return new Json(true, "申请成功！");
	}

	@Override
	public void saveOutboundApply1(ApplyExamineParam messageUpdateApplyParam, String applicationCastId, String applicationCastName) {
		String approveOpinion = messageUpdateApplyParam.getApproveOpinion();
		// 获取申请单表
		ApplyFo applyFo = applyFoMapper.selectByApplyId(messageUpdateApplyParam.getApplyId());
		// 获取信息变更资料表
		OutBound outBound = outBoundMapper.getOutbound(messageUpdateApplyParam.getApplyId());

		if ("0".equals(approveOpinion)) {
			/**
			 * 通过，修改申请单已通过
			 */
			applyFo.setApplyStatus("0000");
			applyFo.setApplyReturn("3");
			applyFo.setUpdateDate(new Date());
			applyFoMapper.updateByPrimaryKeySelective(applyFo);

			/**
			 * 添加流程环节
			 */
			ApplyDetail applyDetail = new ApplyDetail();
			applyDetail.setId(PrimaryKeyUtil.getPrimaryKey());
			applyDetail.setApplyId(messageUpdateApplyParam.getApplyId());
			applyDetail.setApplyType(messageUpdateApplyParam.getApplyType());
			applyDetail.setApplyStatus("3");
			applyDetail.setApproveId(applicationCastId);
			applyDetail.setApproveName(applicationCastName);
			applyDetail.setApproveDesc(messageUpdateApplyParam.getApproveDesc());
			applyDetail.setApproveOpinion("0");
			applyDetail.setCreateTime(new Date());
			applyDetailMapper.insert(applyDetail);

			/**
			 * 修改外访申请通过
			 */
			outBound.setOutboundStatus("0");
			outBound.setUpdateDate(new Date());
			outBoundMapper.updateByPrimaryKeySelective(outBound);

		} else {
			/**
			 * 拒绝，修改申请单已拒绝
			 */
			applyFo.setApplyStatus("1111");
			applyFo.setApplyReturn("3");
			applyFo.setUpdateDate(new Date());
			applyFoMapper.updateByPrimaryKeySelective(applyFo);

			/**
			 * 添加流程节点
			 */
			ApplyDetail applyDetail = new ApplyDetail();
			applyDetail.setId(PrimaryKeyUtil.getPrimaryKey());
			applyDetail.setApplyId(messageUpdateApplyParam.getApplyId());
			applyDetail.setApplyType(messageUpdateApplyParam.getApplyType());
			applyDetail.setApplyStatus("3");
			applyDetail.setApproveId(applicationCastId);
			applyDetail.setApproveName(applicationCastName);
			applyDetail.setApproveDesc(messageUpdateApplyParam.getApproveDesc());
			applyDetail.setApproveOpinion("1");
			applyDetail.setCreateTime(new Date());
			applyDetailMapper.insert(applyDetail);
			/**
			 * 修改外访申请拒绝
			 */
			outBound.setOutboundStatus("1");
			outBound.setUpdateDate(new Date());
			outBoundMapper.updateByPrimaryKeySelective(outBound);

		}

	}

	@Override
	public List<ApplyFo> getOutboundHistory(Integer page, Integer pageSize, String deptId,
			String applicationCastId,String loanId,String conNo,String castName,String cardNo,
			Date createTime,String applyStatus,String applyType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("pageSize", pageSize);
		params.put("loanId", loanId);
		params.put("conNo", conNo);
		params.put("castName", castName);
		params.put("cardNo", cardNo);
		params.put("createTime", createTime);
		params.put("applicationCastId", applicationCastId);
		params.put("applyStatus", applyStatus);
		params.put("applyType", applyType);
	
		
		return applyFoMapper.getOutboundHistory(params);

	}

	@Override
	public int getOutboundHistoryCount(String deptId, String applicationCastId,String loanId,
			String conNo,String castName,String cardNo,Date createTime,String applyStatus,
			String applyType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("applicationCastId", applicationCastId);
		params.put("loanId", loanId);
		params.put("conNo", conNo);
		params.put("castName", castName);
		params.put("cardNo", cardNo);
		params.put("createTime", createTime);
		params.put("applicationCastId", applicationCastId);
		params.put("applyStatus", applyStatus);
		params.put("applyType", applyType);
	
		
		return applyFoMapper.getOutboundHistoryCount(params);

	}

}
