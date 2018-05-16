/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.core.dao.SysRepaymentPlanMapper;
import com.hoomsun.core.model.SysRepaymentPlan;
import com.hoomsun.core.server.inter.SysRepaymentPlanServerI;

/**
 * 作者：liusong <br>
 * 创建时间：2017年12月8日 <br>
 * 描述：
 */
@Service("sysRepaymentPlanServer")
public class SysRepaymentPlanServerImpl implements SysRepaymentPlanServerI{
	@Autowired
	private SysRepaymentPlanMapper sysRepaymentPlanMapper;

	@Override
	public Json createPlan(SysRepaymentPlan sysRepaymentPlan) {
		if (StringUtils.isBlank(sysRepaymentPlan.getPlanId())) {
			sysRepaymentPlan.setPlanId(PrimaryKeyUtil.getPrimaryKey());
		}
		int result = sysRepaymentPlanMapper.insert(sysRepaymentPlan);
		if(result > 0){
			return new Json(true, "添加成功!");
		}else{
			return new Json(false, "添加失败!");
		}
	}

	@Override
	public List<SysRepaymentPlan> findByApplyId(String applyId) {
		// TODO Auto-generated method stub
		return sysRepaymentPlanMapper.findByApplyId(applyId);
	}

	@Override
	public Json updatePlan(SysRepaymentPlan sysRepaymentPlan) {
		if (StringUtils.isBlank(sysRepaymentPlan.getPlanId())) {
			sysRepaymentPlan.setPlanId(PrimaryKeyUtil.getPrimaryKey());
		}
		int result = sysRepaymentPlanMapper.updateByPrimaryKey(sysRepaymentPlan);
		if(result > 0){
			return new Json(true, "修改成功!");
		}else{
			return new Json(false, "修改失败!");
		}
	}


	@Override
	public int deleteByApplyId(String applyId) {
		// TODO Auto-generated method stub
		return sysRepaymentPlanMapper.deleteByApplyId(applyId);
	}

	@Override
	public int countFindByApplyId(String applyId) {
		// TODO Auto-generated method stub
		return sysRepaymentPlanMapper.countFindByApplyId(applyId);
	}

	@Override
	public List<SysRepaymentPlan>  findPlanByApplyId(String applyId){
		return sysRepaymentPlanMapper.findPlanByApplyId(applyId);
	}

	
}
