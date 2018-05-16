/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.server.impl;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.server.inter.ProposedServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月29日 <br>
 * 描述：
 */
public class ProposedServerImpl implements ProposedServerI{

	@Override
	public Pager<UserApply> findPager(Integer page, Integer rows, String idNo, String name, String empId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Json completeTask(String applyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Json claimTask(String applyId, String empId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Json rollbackTask(String applyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Json withdrawTask(String applyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
