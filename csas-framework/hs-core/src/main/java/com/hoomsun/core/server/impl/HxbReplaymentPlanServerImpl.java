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
import com.hoomsun.core.dao.HxbReplaymentPlanMapper;
import com.hoomsun.core.model.HxbReplaymentPlan;
import com.hoomsun.core.server.inter.HxbReplaymentPlanServerI;

/**
 * 作者：liusong <br>
 * 创建时间：2018年1月4日 <br>
 * 描述：
 */
@Service("hxbReplaymentPlanServer")
public class HxbReplaymentPlanServerImpl implements HxbReplaymentPlanServerI{
	
	@Autowired
	private HxbReplaymentPlanMapper hxbReplaymentPlanMapper;

	@Override
	public Json deleteByApplyId(String applyId) {
		int result = hxbReplaymentPlanMapper.deleteByApplyId(applyId);
		if (result > 0) {
			return new Json(true, "删除成功!");
		} else {
			return new Json(false, "删除失败!");
		}
	}

	@Override
	public Json createPlan(HxbReplaymentPlan plan) {
		if (StringUtils.isBlank(plan.getPlanId())) {
			plan.setPlanId(PrimaryKeyUtil.getPrimaryKey());
		}
		int result = hxbReplaymentPlanMapper.insertSelective(plan);
		if (result > 0) {
			return new Json(true, "添加成功!");
		} else {
			return new Json(false, "添加失败!");
		}
	}

	@Override
	public Json updatePlan(HxbReplaymentPlan plan) {
		int result = hxbReplaymentPlanMapper.updateByPrimaryKey(plan);
		if (result > 0) {
			return new Json(true, "修改成功!");
		} else {
			return new Json(false, "修改失败!");
		}
	}

	@Override
	public List<HxbReplaymentPlan> selectByApplyId(String applyId) {
		// TODO Auto-generated method stub
		return hxbReplaymentPlanMapper.selectByApplyId(applyId);
	}

	@Override
	public HxbReplaymentPlan selectByApplyIdAndPhaseNumber(String applyId, Integer phaseNumber) {
		// TODO Auto-generated method stub
		return hxbReplaymentPlanMapper.selectByApplyIdAndPhaseNumber(applyId, phaseNumber);
	}

}
