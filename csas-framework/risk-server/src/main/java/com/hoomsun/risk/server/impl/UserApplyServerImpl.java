/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.server.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.risk.dao.UserApplyMapperRisk;
import com.hoomsun.risk.model.vo.UserApplyVo;
import com.hoomsun.risk.server.inter.UserApplyServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月30日 <br>
 * 描述：用户申请基本信息
 */
@Service("userApplyServerRisk")
public class UserApplyServerImpl implements UserApplyServerI {
	@Autowired
	private UserApplyMapperRisk userApplyMapper;

	@Override
	public UserApplyVo findByIdNumber(String idNumber) {
		if (StringUtils.isBlank(idNumber)) {
			return null;
		}
		return userApplyMapper.findByIdNumber(idNumber);
	}

}
