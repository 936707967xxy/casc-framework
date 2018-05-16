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
import com.hoomsun.after.api.model.param.MessageUpdateParam;
import com.hoomsun.after.api.model.table.ApplyDetail;
import com.hoomsun.after.api.model.table.ApplyFo;
import com.hoomsun.after.api.model.table.ChangeInfo;
import com.hoomsun.after.api.model.table.UserCount;
import com.hoomsun.after.api.model.vo.BankVo;
import com.hoomsun.after.api.server.MessageUpdateServer;
import com.hoomsun.after.api.util.IDGenerator;
import com.hoomsun.after.dao.ApplyDetailMapper;
import com.hoomsun.after.dao.ApplyFoMapper;
import com.hoomsun.after.dao.ApplyMapper;
import com.hoomsun.after.dao.ChangeInfoMapper;
import com.hoomsun.after.dao.LoanbalMapper;
import com.hoomsun.after.dao.UserCountMapper;
import com.hoomsun.common.model.Json;
import com.hoomsun.core.dao.SysEmployeeMapper;
import com.hoomsun.core.model.SysEmployee;
import com.hoomsun.core.util.PrimaryKeyUtil;

/**
 * 作者：administrator <br>
 * 创建时间：2018年4月18日 <br>
 * 描述：
 */
@Service("messageUpdateServer")
public class MessageUpdateServerImpl implements MessageUpdateServer {

	@Autowired
	private ApplyDetailMapper applyDetailMapper;

	@Autowired
	private ApplyMapper applyMapper;

	@Autowired
	private UserCountMapper userCountMapper;

	@Autowired
	private ChangeInfoMapper changeInfoMapper;
	@Autowired
	private ApplyFoMapper applyFoMapper;
	@Autowired
	private LoanbalMapper loanbalMapper;
	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;

	@Override
	public List<ApplyDetail> getApplyDetail(String applyId) {

		return applyDetailMapper.getApplyDetail(applyId);
	}

	@Override
	public BankVo getCustMessageMessage(String loanId) {
		String custId = applyMapper.getCustIdByLoanId(loanId);
		UserCount userCount = userCountMapper.selectByCustId(custId);
		BankVo bankVo = new BankVo();
		bankVo.setCastName(userCount.getCastName());
		bankVo.setSex(userCount.getSex());
		bankVo.setTel(userCount.getTel());
		bankVo.setCardNo(userCount.getCardNo());
		bankVo.setBank(userCount.getBank());
		bankVo.setBankPhone(userCount.getBankPhone());
		bankVo.setBankAccount(userCount.getBankAccount());
		bankVo.setBankCode(userCount.getBankCode());

		return bankVo;
	}

	@Override
	public ChangeInfo getChangeInfo(String applyId) {

		return changeInfoMapper.getChangeInfo(applyId);
	}

	@Override
	public Json saveMessageUpdate(MessageUpdateParam messageUpdateParam, String applicationCastId, String applicationCastName) {

		List<ChangeInfo> changeInfos = changeInfoMapper.selectByCustId(messageUpdateParam.getCustId());

		if (changeInfos != null && changeInfos.size() > 0) {
			for (ChangeInfo changeInfo : changeInfos) {
				if ("2".equals(changeInfo.getChangeinfoStatus())) {
					return new Json(false, "此客户已有一笔信息变更处于申请中！");
				}

			}

		}

		// 申请单号
		String applyId = messageUpdateParam.getApplyId();

		if (applyId == null || "".equals(applyId)) {
			messageUpdateParam.setApplyId(IDGenerator.getNextID("UPIMG"));
		}

		// 变更类型
		String applyType = messageUpdateParam.getApplyType();

		// 联系电话变更
		if ("telChange".equals(applyType)) {
			/**
			 * 插入信息变更资料表
			 */
			ChangeInfo changeInfo = new ChangeInfo();
			changeInfo.setId(PrimaryKeyUtil.getPrimaryKey());
			changeInfo.setApplyId(messageUpdateParam.getApplyId());
			changeInfo.setApplyType("telChange");
			changeInfo.setCustId(messageUpdateParam.getCustId());
			changeInfo.setSex(messageUpdateParam.getSex());
			changeInfo.setCastName(messageUpdateParam.getCastName());
			changeInfo.setCardNo(messageUpdateParam.getCardNo());
			changeInfo.setOldTel(messageUpdateParam.getOldTel());
			changeInfo.setNewTel(messageUpdateParam.getNewTel());
			// changeInfo.setOldBank(messageUpdateParam.getOldBank());
			// changeInfo.setOldBankPhone(messageUpdateParam.getOldBankPhone());
			// changeInfo.setOldBankAccount(messageUpdateParam.getOldBankAccount());
			// changeInfo.setOldBankCode(messageUpdateParam.getOldBankCode());
			// changeInfo.setNewBank(messageUpdateParam.getNewBank());
			// changeInfo.setNewBankPhone(messageUpdateParam.getNewBankPhone());
			// changeInfo.setNewBankAccount(messageUpdateParam.getNewBankAccount());
			// changeInfo.setNewBankCode(messageUpdateParam.getNewBankCode());
			changeInfo.setChangeinfoStatus("2");
			changeInfo.setCreateTime(new Date());
			changeInfo.setUpdateDate(null);
			changeInfoMapper.insert(changeInfo);
			/**
			 * 插入申请单表
			 */
			ApplyFo applyFo = new ApplyFo();
			applyFo.setId(PrimaryKeyUtil.getPrimaryKey());
			applyFo.setApplyId(messageUpdateParam.getApplyId());
			applyFo.setApplyType("telChange");
			applyFo.setApplicationCastId(applicationCastId);
			applyFo.setApplicationCastName(applicationCastName);
			applyFo.setApplicationDesc(messageUpdateParam.getApplicationDesc());
			applyFo.setCastName(messageUpdateParam.getCastName());
			applyFo.setCardNo(messageUpdateParam.getCardNo());
			applyFo.setConNo(null);
			applyFo.setLoanId(null);
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
			applyDetail.setApplyId(messageUpdateParam.getApplyId());
			applyDetail.setApplyType("telChange");
			applyDetail.setApplyStatus("2");
			applyDetail.setApproveId(applicationCastId);
			applyDetail.setApproveName(applicationCastName);
			applyDetail.setApproveDesc(messageUpdateParam.getApplicationDesc());
			applyDetail.setApproveOpinion("0");
			applyDetail.setCreateTime(new Date());
			applyDetailMapper.insert(applyDetail);
			return new Json(true, "联系电话信息变更申请成功！");
			// 银行卡变更
		} else if ("bankChange".equals(applyType)) {
			/**
			 * 插入信息变更资料表
			 */
			ChangeInfo changeInfo = new ChangeInfo();

			changeInfo.setId(PrimaryKeyUtil.getPrimaryKey());
			changeInfo.setApplyId(messageUpdateParam.getApplyId());
			changeInfo.setApplyType("bankChange");
			changeInfo.setCustId(messageUpdateParam.getCustId());
			changeInfo.setSex(messageUpdateParam.getSex());
			changeInfo.setCastName(messageUpdateParam.getCastName());
			changeInfo.setCardNo(messageUpdateParam.getCardNo());
			// changeInfo.setOldTel(messageUpdateParam.getOldTel());
			// changeInfo.setNewTel(messageUpdateParam.getNewTel());
			changeInfo.setOldBank(messageUpdateParam.getOldBank());
			changeInfo.setOldBankPhone(messageUpdateParam.getOldBankPhone());
			changeInfo.setOldBankAccount(messageUpdateParam.getOldBankAccount());
			changeInfo.setOldBankCode(messageUpdateParam.getOldBankCode());
			changeInfo.setNewBank(messageUpdateParam.getNewBank());
			changeInfo.setNewBankPhone(messageUpdateParam.getNewBankPhone());
			changeInfo.setNewBankAccount(messageUpdateParam.getNewBankAccount());
			changeInfo.setNewBankCode(messageUpdateParam.getNewBankCode());
			changeInfo.setChangeinfoStatus("2");
			changeInfo.setCreateTime(new Date());
			changeInfo.setUpdateDate(null);
			changeInfoMapper.insert(changeInfo);

			/**
			 * 插入申请单表
			 */
			ApplyFo applyFo = new ApplyFo();
			applyFo.setId(PrimaryKeyUtil.getPrimaryKey());
			applyFo.setApplyId(messageUpdateParam.getApplyId());
			applyFo.setApplyType("bankChange");
			applyFo.setApplicationCastId(applicationCastId);
			applyFo.setApplicationCastName(applicationCastName);
			applyFo.setApplicationDesc(messageUpdateParam.getApplicationDesc());
			applyFo.setCastName(messageUpdateParam.getCastName());
			applyFo.setCardNo(messageUpdateParam.getCardNo());
			applyFo.setConNo(null);
			applyFo.setLoanId(null);
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
			applyDetail.setApplyId(messageUpdateParam.getApplyId());
			applyDetail.setApplyType("bankChange");
			applyDetail.setApplyStatus("2");
			applyDetail.setApproveId(applicationCastId);
			applyDetail.setApproveName(applicationCastName);
			applyDetail.setApproveDesc(messageUpdateParam.getApplicationDesc());
			applyDetail.setApproveOpinion("0");
			applyDetail.setCreateTime(new Date());
			applyDetailMapper.insert(applyDetail);

			return new Json(true, "银行卡变更申请成功！");
		}
		return new Json(false, "申请类型有误！");

	}

	@Override
	public List<ApplyFo> getExamineList(Integer page, Integer pageSize, Integer status, String castName,String cardNo,String conditionCustID,String conditionCustName, Date createTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("pageSize", pageSize);
		params.put("status", status);
		params.put("castName", castName);
		params.put("cardNo", cardNo);
		params.put("conditionCustID", conditionCustID);
		params.put("conditionCustName", conditionCustName);
		params.put("createTime", createTime);

		return applyFoMapper.selectMessageUpdateByStstus(params);
	}


	
	
	@Override
	public int getExamineListSize(Integer status, String castName,String cardNo,String conditionCustID,String conditionCustName, Date createTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		// 修改
		params.put("status", status);
		params.put("castName", castName);
		params.put("cardNo", cardNo);
		params.put("conditionCustID", conditionCustID);
		params.put("conditionCustName", conditionCustName);
		params.put("createTime", createTime);

		return applyFoMapper.selectMessageUpdateByStstusCount(params);
	}

	@Override
	public List<ApplyFo> getExamineList2(Integer page, Integer pageSize, Integer status, String applicationCastId, String applicationCastName, String empId, String castName,String cardNo,String conditionCustID,String conditionCustName,Date createTime) {

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
		params.put("castName", castName);
		params.put("cardNo", cardNo);
		params.put("conditionCustID", conditionCustID);
		params.put("conditionCustName", conditionCustName);
		params.put("createTime", createTime);
		

		return applyFoMapper.selectMessageUpdateByStstusAndUsers(params);
	}

	@Override
	public int getExamineListSize2(Integer status, String applicationCastId, String applicationCastName, String empId, String castName,String cardNo,String conditionCustID,String conditionCustName, Date createTime) {
		List<SysEmployee> users = sysEmployeeMapper.findDeptEmp(empId);
		List<String> userIds = new ArrayList<String>();
		for (SysEmployee sysEmployee : users) {
			userIds.add(sysEmployee.getEmpWorkNum());

		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		params.put("userIds", userIds);
		params.put("castName", castName);
		params.put("cardNo", cardNo);
		params.put("conditionCustID", conditionCustID);
		params.put("conditionCustName", conditionCustName);
		params.put("createTime", createTime);

		return applyFoMapper.selectMessageUpdateByStstusAndUsersCount(params);
	}

	@Override
	public void saveMessageUpdate1(ApplyExamineParam messageUpdateApplyParam, String applicationCastId, String applicationCastName) {
		String approveOpinion = messageUpdateApplyParam.getApproveOpinion();
		// 获取申请单表
		ApplyFo applyFo = applyFoMapper.selectByApplyId(messageUpdateApplyParam.getApplyId());
		// 获取信息变更资料表
		ChangeInfo changeInfo = changeInfoMapper.getChangeInfo(messageUpdateApplyParam.getApplyId());

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
			applyDetail.setApplyId(messageUpdateApplyParam.getApplyId());
			applyDetail.setApplyType(messageUpdateApplyParam.getApplyType());
			applyDetail.setApplyStatus("3");
			applyDetail.setApproveId(applicationCastId);
			applyDetail.setApproveName(applicationCastName);
			applyDetail.setApproveDesc(messageUpdateApplyParam.getApproveDesc());
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
			 * 修改信息变更资料拒绝
			 */
			changeInfo.setChangeinfoStatus("1");
			changeInfo.setUpdateDate(new Date());
			changeInfoMapper.updateByPrimaryKeySelective(changeInfo);
		}

	}

	@Override
	public void saveMessageUpdate2(ApplyExamineParam messageUpdateApplyParam, String applicationCastId, String applicationCastName) {
		String approveOpinion = messageUpdateApplyParam.getApproveOpinion();
		// 获取申请单表
		ApplyFo applyFo = applyFoMapper.selectByApplyId(messageUpdateApplyParam.getApplyId());
		// 获取信息变更资料表
		ChangeInfo changeInfo = changeInfoMapper.getChangeInfo(messageUpdateApplyParam.getApplyId());

		if ("0".equals(approveOpinion)) {
			/**
			 * 通过，修改申请单已通过
			 */
			applyFo.setApplyStatus("0000");
			applyFo.setApplyReturn("4");
			applyFo.setUpdateDate(new Date());
			applyFoMapper.updateByPrimaryKeySelective(applyFo);

			/**
			 * 添加流程环节
			 */
			ApplyDetail applyDetail = new ApplyDetail();
			applyDetail.setId(PrimaryKeyUtil.getPrimaryKey());
			applyDetail.setApplyId(messageUpdateApplyParam.getApplyId());
			applyDetail.setApplyType(messageUpdateApplyParam.getApplyType());
			applyDetail.setApplyStatus("4");
			applyDetail.setApproveId(applicationCastId);
			applyDetail.setApproveName(applicationCastName);
			applyDetail.setApproveDesc(messageUpdateApplyParam.getApproveDesc());
			applyDetail.setApproveOpinion("0");
			applyDetail.setCreateTime(new Date());
			applyDetailMapper.insert(applyDetail);

			/**
			 * 修改信息变更资料通过
			 */
			changeInfo.setChangeinfoStatus("0");
			changeInfo.setUpdateDate(new Date());
			changeInfoMapper.updateByPrimaryKeySelective(changeInfo);

			if ("telChange".equals(messageUpdateApplyParam.getApplyType())) {
				/**
				 * 联系手机号码变更，修改
				 */
				String custId = changeInfo.getCustId();
				UserCount userCount = userCountMapper.selectByCustId(custId);
				userCount.setTel(changeInfo.getNewTel());
				userCountMapper.updateByPrimaryKeySelective(userCount);

				Map<String, Object> balparams = new HashMap<String, Object>();
				balparams.put("custId", changeInfo.getCustId());
				balparams.put("tel", changeInfo.getNewTel());

				loanbalMapper.updateTelByCustId(balparams);

			} else if ("bankChange".equals(messageUpdateApplyParam.getApplyType())) {
				/**
				 * 银行资料变更修改
				 */
				String custId = changeInfo.getCustId();
				UserCount userCount = userCountMapper.selectByCustId(custId);
				userCount.setBank(changeInfo.getNewBank());
				userCount.setBankPhone(changeInfo.getNewBankPhone());
				userCount.setBankAccount(changeInfo.getNewBankAccount());
				userCount.setBankCode(changeInfo.getNewBankCode());
				userCountMapper.updateByPrimaryKeySelective(userCount);
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
			 * 添加流程节点
			 */
			ApplyDetail applyDetail = new ApplyDetail();
			applyDetail.setId(PrimaryKeyUtil.getPrimaryKey());
			applyDetail.setApplyId(messageUpdateApplyParam.getApplyId());
			applyDetail.setApplyType(messageUpdateApplyParam.getApplyType());
			applyDetail.setApplyStatus("4");
			applyDetail.setApproveId(applicationCastId);
			applyDetail.setApproveName(applicationCastName);
			applyDetail.setApproveDesc(messageUpdateApplyParam.getApproveDesc());
			applyDetail.setApproveOpinion("1");
			applyDetail.setCreateTime(new Date());
			applyDetailMapper.insert(applyDetail);
			/**
			 * 修改信息变更资料拒绝
			 */
			changeInfo.setChangeinfoStatus("1");
			changeInfoMapper.updateByPrimaryKeySelective(changeInfo);
		}

	}

	@Override
	public List<ApplyFo> getMessageUpdateHistory(Integer page, Integer pageSize, String deptId, String applicationCastId, String castName,String cardNo ,String applyStatus, String applyType, Date createTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("pageSize", pageSize);
		params.put("applicationCastId", applicationCastId);
		params.put("castName", castName);
		params.put("cardNo", cardNo);
		params.put("applyStatus", applyStatus);
		params.put("applyType", applyType);
		params.put("createTime", createTime);

		if ("593".equals(deptId)) {
			return applyFoMapper.getMessageUpdateHistoryAll(params);
		} else {

			return applyFoMapper.getMessageUpdateHistory(params);
		}
	}

	@Override
	public int getMessageUpdateHistoryCount(String deptId, String applicationCastId, String castName, String cardNo,String applyStatus, String applyType, Date createTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("castName", castName);
		params.put("cardNo", cardNo);
		params.put("applyStatus", applyStatus);
		params.put("applyType", applyType);
		params.put("createTime", createTime);
		params.put("applicationCastId", applicationCastId);

		if ("593".equals(deptId)) {
			return applyFoMapper.getMessageUpdateHistoryAllCount(params);
		} else {

			return applyFoMapper.getMessageUpdateHistoryCount(params);
		}
	}

}
