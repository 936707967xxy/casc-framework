/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.core.dao.HxbRateMapper;
import com.hoomsun.core.model.HxbRate;
import com.hoomsun.core.server.inter.HxbRateServerI;

/**
 * 作者：liusong <br>
 * 创建时间：2018年1月10日 <br>
 * 描述：
 */
@Service("hxbRateServer")
public class HxbRateServerImpl implements HxbRateServerI{
	@Autowired
	private HxbRateMapper hxbRateMapper;
	@Override
	public HxbRate selectByApplyId(String applyId) {
		// TODO Auto-generated method stub
		return hxbRateMapper.selectByApplyId(applyId);
	}

}
