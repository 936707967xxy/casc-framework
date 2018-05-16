/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.core.dao.SysContractMapper;
import com.hoomsun.core.dao.SysRepaymentPlanMapper;
import com.hoomsun.core.model.SysContract;
import com.hoomsun.core.model.SysRepaymentPlan;
import com.hoomsun.core.server.inter.SysContractServerI;
import com.hoomsun.core.util.PrimaryKeyUtil;

/**
 * 作者：liusong <br>
 * 创建时间：2017年12月8日 <br>
 * 描述：
 */
@Service("contractServer")
public class SysContractServerImpl implements SysContractServerI {
	@Autowired
	private SysContractMapper contractMapper;
	@Autowired
	private SysRepaymentPlanMapper repaymentPlanMapper;

	@Override
	public Json createContract(SysContract contract) {
		if (StringUtils.isBlank(contract.getConId())) {
			contract.setConId(PrimaryKeyUtil.getPrimaryKey());
		}

		int total = 0;
		// 验证合同是否已经存在 根据申请ID
		String conId = contractMapper.findConIdByApplyId(contract.getApplyId());
		if (StringUtils.isNotBlank(conId)) {// 存在就修改数据
			contract.setConId(conId);
			total += contractMapper.updateByPrimaryKeySelective(contract);
		} else {// 添加
			total += contractMapper.insertSelective(contract);
		}

		// 验证还款计划是否已经存在
		int count = repaymentPlanMapper.countFindByApplyId(contract.getApplyId());
		if (count > 0) {
			total += repaymentPlanMapper.deleteByApplyId(contract.getApplyId());
		}

		for (SysRepaymentPlan sysRepaymentPlan : contract.getRepaymentPlans()) {
			if (StringUtils.isBlank(sysRepaymentPlan.getPlanId())) {
				sysRepaymentPlan.setPlanId(PrimaryKeyUtil.getPrimaryKey());
			}
			sysRepaymentPlan.setApplyId(contract.getApplyId());
			sysRepaymentPlan.setLoanId(contract.getLoanId());
			sysRepaymentPlan.setConId(contract.getConId());
			total += repaymentPlanMapper.insertSelective(sysRepaymentPlan);
		}

		if (total > 0) {
			return new Json(true, "添加成功!");
		} else {
			return new Json(false, "添加失败!");
		}

	}

	@Override
	public Json updateCom(SysContract contract) {
		int result = contractMapper.updateByPrimaryKey(contract);
		if (result > 0) {
			return new Json(true, "更新成功!");
		} else {
			return new Json(true, "更新失败!");
		}
	}

	@Override
	public SysContract selectByApplyId(String applyId) {
		return contractMapper.selectByApplyId(applyId);
	}

	@Override
	public int selectByApplyIdCount(String applyId) {
		return contractMapper.selectByApplyIdCount(applyId);
	}

	@Override
	public SysContract findByConId(String conId) {
		if (StringUtils.isBlank(conId)) {
			return new SysContract();
		}
		return contractMapper.findByConId(conId);
	}

	@Override
	public SysContract findByApplyId(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			return new SysContract();
		}
		return contractMapper.findByApplyId(applyId);
	}

	/**
	 * 作者：刘栋梁 <br>
	 * 创建时间：2017年12月20日 <br>
	 * 描述： 根据合apply查询合同信息
	 * 
	 * @param applyId
	 * @return
	 */
	@Override
	public SysContract findinfoByApplyId(String applyId) {
		return contractMapper.findinfoByApplyId(applyId);
	}

	@Override
	public String findApplyIdByConId(String conId) {
		return contractMapper.findApplyIdByConId(conId);
	}
	
	/**
	 * 作者：刘栋梁 <br>
	 * 创建时间：2018年1月16日 <br>
	 * 描述： 查询合同账单日---在修改银行卡时给提示信息 
	 * 
	 * @param applyId
	 * @return
	 */
	@Override
	public List<SysContract> findConPayData(Map<String, String> constatus){
		return contractMapper.findConPayData(constatus);
	}

	@Override
	public int updateConStatus(String applyId) {
		// TODO Auto-generated method stub
		return contractMapper.updateConStatus(applyId);
	}
}
