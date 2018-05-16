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
import com.hoomsun.after.api.model.param.LeaveApplyParams;
import com.hoomsun.after.api.model.table.ApplyDetail;
import com.hoomsun.after.api.model.table.ApplyFo;
import com.hoomsun.after.api.model.table.Loanbal;
import com.hoomsun.after.api.server.LeaveApplyServer;
import com.hoomsun.after.api.util.IDGenerator;
import com.hoomsun.after.dao.ApplyDetailMapper;
import com.hoomsun.after.dao.ApplyFoMapper;
import com.hoomsun.after.dao.LoanbalMapper;
import com.hoomsun.core.dao.SysEmployeeMapper;
import com.hoomsun.core.model.SysEmployee;
import com.hoomsun.core.util.PrimaryKeyUtil;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年4月27日 <br>
 * 描述：留案申请
 */
@Service("leaveApplyServer")
public class LeaveApplyServerImpl implements LeaveApplyServer {

	@Autowired
	private ApplyFoMapper applyFoMapper;
	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;
	@Autowired
	private ApplyDetailMapper applyDetailMapper;
	@Autowired
	private LoanbalMapper loanbalMapper;

	@Override
	public List<ApplyFo> getExamineList(Integer page, Integer pageSize, Integer status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("pageSize", pageSize);
		params.put("status", status);

		return applyFoMapper.selectLeaveApplyByStstus(params);
	}

	@Override
	public int getExamineListSize(Integer status) {
		return applyFoMapper.selectLeaveApplyByStstusCount(status);
	}

	@Override
	public List<ApplyFo> getExamineList2(Integer page, Integer pageSize, Integer status, String applicationCastId, String applicationCastName, String empId) {
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

		return applyFoMapper.selectLeaveApplyByStstusAndUsers(params);
	}

	@Override
	public int getExamineListSize2(Integer status, String applicationCastId, String applicationCastName, String empId) {
		List<SysEmployee> users = sysEmployeeMapper.findDeptEmp(empId);
		List<String> userIds = new ArrayList<String>();
		for (SysEmployee sysEmployee : users) {
			userIds.add(sysEmployee.getEmpWorkNum());

		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		params.put("userIds", userIds);

		return applyFoMapper.selectLeaveApplyByStstusAndUsersCount(params);
	}

	@Override
	public List<ApplyFo> getLeaveapplyHistory(Integer page, Integer pageSize, String deptId, String applicationCastId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("pageSize", pageSize);
		params.put("applicationCastId", applicationCastId);

		return applyFoMapper.getLeaveapplyHistory(params);

	}

	@Override
	public int getLeaveapplyHistoryCount(String deptId, String applicationCastId) {

		return applyFoMapper.getLeaveapplyHistoryCount(applicationCastId);

	}

	@Override
	public String saveLeaveApply(LeaveApplyParams leaveApplyParams, String applicationCastId, String applicationCastName) {

		// 申请单号
		String applyId = leaveApplyParams.getApplyId();

		if (applyId == null || "".equals(applyId)) {
			leaveApplyParams.setApplyId(IDGenerator.getNextID("UPIMG"));
		}

		// 变更类型
		String applyType = leaveApplyParams.getApplyType();

		if ("overdueLeave".equals(applyType) || "outBoundLeave".equals(applyType)) {

			Loanbal loanbal = loanbalMapper.selectByLoanId(leaveApplyParams.getLoanId());

			/**
			 * 插入申请单表
			 */
			ApplyFo applyFo = new ApplyFo();
			applyFo.setId(PrimaryKeyUtil.getPrimaryKey());
			applyFo.setApplyId(leaveApplyParams.getApplyId());
			applyFo.setApplyType(applyType);
			applyFo.setApplicationCastId(applicationCastId);
			applyFo.setApplicationCastName(applicationCastName);
			applyFo.setApplicationDesc(leaveApplyParams.getApplicationDesc());
			applyFo.setCastName(loanbal.getCastName());
			applyFo.setCardNo(loanbal.getCardNo());
			applyFo.setConNo(loanbal.getConNo());
			applyFo.setLoanId(leaveApplyParams.getLoanId());
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
			applyDetail.setApplyId(leaveApplyParams.getApplyId());
			applyDetail.setApplyType(applyType);
			applyDetail.setApplyStatus("2");
			applyDetail.setApproveId(applicationCastId);
			applyDetail.setApproveName(applicationCastName);
			applyDetail.setApproveDesc(leaveApplyParams.getApplicationDesc());
			applyDetail.setApproveOpinion("0");
			applyDetail.setCreateTime(new Date());
			applyDetailMapper.insert(applyDetail);
			return "留案申请成功！";
		}
		return "留案申请类型有误，请重试";

	}

	@Override
	public void saveLeaveApply1(ApplyExamineParam leaveApplyParam, String applicationCastId, String applicationCastName) {

		String approveOpinion = leaveApplyParam.getApproveOpinion();
		// 获取申请单表
		ApplyFo applyFo = applyFoMapper.selectByApplyId(leaveApplyParam.getApplyId());

		if ("0".equals(approveOpinion)) {
			/**
			 * 通过，修改申请单到下一节点
			 */
			applyFo.setApplyStatus("3");
			applyFo.setUpdateDate(new Date());
			applyFoMapper.updateByPrimaryKeySelective(applyFo);
			/**
			 * 添加流程环节
			 */
			ApplyDetail applyDetail = new ApplyDetail();
			applyDetail.setId(PrimaryKeyUtil.getPrimaryKey());
			applyDetail.setApplyId(leaveApplyParam.getApplyId());
			applyDetail.setApplyType(leaveApplyParam.getApplyType());
			applyDetail.setApplyStatus("3");
			applyDetail.setApproveId(applicationCastId);
			applyDetail.setApproveName(applicationCastName);
			applyDetail.setApproveDesc(leaveApplyParam.getApproveDesc());
			applyDetail.setApproveOpinion("0");
			applyDetail.setCreateTime(new Date());
			applyDetailMapper.insert(applyDetail);

		} else {
			/**
			 * 拒绝，修改申请单已拒绝
			 */
			applyFo.setApplyStatus("1111");
			applyFo.setApplyReturn("3");
			applyFo.setUpdateDate(new Date());
			applyFoMapper.updateByPrimaryKeySelective(applyFo);

			/**
			 * 插入流程环节
			 */
			ApplyDetail applyDetail = new ApplyDetail();
			applyDetail.setId(PrimaryKeyUtil.getPrimaryKey());
			applyDetail.setApplyId(leaveApplyParam.getApplyId());
			applyDetail.setApplyType(leaveApplyParam.getApplyType());
			applyDetail.setApplyStatus("3");
			applyDetail.setApproveId(applicationCastId);
			applyDetail.setApproveName(applicationCastName);
			applyDetail.setApproveDesc(leaveApplyParam.getApproveDesc());
			applyDetail.setApproveOpinion("1");
			applyDetail.setCreateTime(new Date());
			applyDetailMapper.insert(applyDetail);

		}

	}

	@Override
	public void saveLeaveApply2(ApplyExamineParam leaveApplyParam, String applicationCastId, String applicationCastName) {
		String approveOpinion = leaveApplyParam.getApproveOpinion();
		// 获取申请单表
		ApplyFo applyFo = applyFoMapper.selectByApplyId(leaveApplyParam.getApplyId());

		if ("0".equals(approveOpinion)) {
			/**
			 * 通过，修改申请单到下一节点
			 */
			applyFo.setApplyStatus("0000");
			applyFo.setUpdateDate(new Date());
			applyFoMapper.updateByPrimaryKeySelective(applyFo);
			/**
			 * 添加流程环节
			 */
			ApplyDetail applyDetail = new ApplyDetail();
			applyDetail.setId(PrimaryKeyUtil.getPrimaryKey());
			applyDetail.setApplyId(leaveApplyParam.getApplyId());
			applyDetail.setApplyType(leaveApplyParam.getApplyType());
			applyDetail.setApplyStatus("4");
			applyDetail.setApproveId(applicationCastId);
			applyDetail.setApproveName(applicationCastName);
			applyDetail.setApproveDesc(leaveApplyParam.getApproveDesc());
			applyDetail.setApproveOpinion("0");
			applyDetail.setCreateTime(new Date());
			applyDetailMapper.insert(applyDetail);

			if ("overdueLeave".equals(leaveApplyParam.getApplyType())) {
				Map<String, Object> overdueLeave = new HashMap<String, Object>();
				overdueLeave.put("loanId", applyFo.getLoanId());
				overdueLeave.put("leave", 1);
				loanbalMapper.updateOverdueLeaveByLoanId(overdueLeave);

			} else if ("outBoundLeave".equals(leaveApplyParam.getApplyType())) {
				Map<String, Object> outBoundLeave = new HashMap<String, Object>();
				outBoundLeave.put("loanId", applyFo.getLoanId());
				outBoundLeave.put("leave", 1);
				loanbalMapper.updateOutBoundByLoanId(outBoundLeave);

			}

		} else {
			/**
			 * 拒绝，修改申请单已拒绝
			 */
			applyFo.setApplyStatus("1111");
			applyFo.setApplyReturn("4");
			applyFo.setUpdateDate(new Date());
			applyFoMapper.updateByPrimaryKeySelective(applyFo);

			/**
			 * 插入流程环节
			 */
			ApplyDetail applyDetail = new ApplyDetail();
			applyDetail.setId(PrimaryKeyUtil.getPrimaryKey());
			applyDetail.setApplyId(leaveApplyParam.getApplyId());
			applyDetail.setApplyType(leaveApplyParam.getApplyType());
			applyDetail.setApplyStatus("4");
			applyDetail.setApproveId(applicationCastId);
			applyDetail.setApproveName(applicationCastName);
			applyDetail.setApproveDesc(leaveApplyParam.getApproveDesc());
			applyDetail.setApproveOpinion("1");
			applyDetail.setCreateTime(new Date());
			applyDetailMapper.insert(applyDetail);

		}

	}

}
