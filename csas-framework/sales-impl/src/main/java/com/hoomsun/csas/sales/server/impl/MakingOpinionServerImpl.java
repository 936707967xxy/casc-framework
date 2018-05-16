/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.server.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.csas.sales.api.model.MakingOpinion;
import com.hoomsun.csas.sales.api.server.inter.MakingOpinionServerI;
import com.hoomsun.csas.sales.dao.MakingOpinionMapper;

/**
 * 作者：liusong <br>
 * 创建时间：2017年12月8日 <br>
 * 描述：
 */
@Service("makingOpinionServer")
public class MakingOpinionServerImpl implements MakingOpinionServerI{
	@Autowired
	private MakingOpinionMapper makingMapper;

	@Override
	public Json create(MakingOpinion makingOpinion) {
		if (StringUtils.isBlank(makingOpinion.getMakingId())) {
			makingOpinion.setMakingId(PrimaryKeyUtil.getPrimaryKey());
		}
		int result = makingMapper.insertSelective(makingOpinion);
		if(result > 0){
			return new Json(true, "记录添加成功!");
		}else{
			return new Json(false, "记录添加失败!");
		}
	}

	@Override
	public String selectByApplyId(String applyId) {
		// TODO Auto-generated method stub
		return makingMapper.selectByApplyId(applyId);
	}

	@Override
	public String selectByRollBackOpinion(String applyId) {
		// TODO Auto-generated method stub
		return makingMapper.selectByRollBackOpinion(applyId);
	}

}
