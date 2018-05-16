/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.apply.query.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.csas.apply.query.dao.UserApplyMapper;
import com.hoomsun.csas.apply.query.model.UserApply;
import com.hoomsun.csas.apply.query.server.inter.UserApplyServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月1日 <br>
 * 描述：申请表server 实现
 */
@Service("userApplyDataServer")
public class UserApplyServerImpl implements UserApplyServerI {
	@Autowired
	private UserApplyMapper userApplyMapper;

	@Override
	public UserApply findApplyById(String applyId) {
		return userApplyMapper.findApplyById(applyId);
	}

}
