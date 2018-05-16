/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.core.dao.SystemParamMapper;
import com.hoomsun.core.model.SystemParam;
import com.hoomsun.core.server.inter.SystemParamServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月18日 <br>
 * 描述：
 */
@Service("systemParamServer")
public class SystemParamServerImpl implements SystemParamServerI {
	@Autowired
	private SystemParamMapper systemParamMapper;

	@Override
	public SystemParam findParamByKey(String key) {
		return systemParamMapper.findParamByKey(key);
	}

}
