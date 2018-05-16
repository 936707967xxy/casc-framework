/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.server.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.csas.sales.api.exception.AuditException;
import com.hoomsun.csas.sales.api.model.UserCis;
import com.hoomsun.csas.sales.api.model.UserPbccre;
import com.hoomsun.csas.sales.api.model.vo.CreditVo;
import com.hoomsun.csas.sales.api.model.vo.PbccrcChartViewVo;
import com.hoomsun.csas.sales.api.server.inter.CreditServerI;
import com.hoomsun.csas.sales.dao.PbccrcChartViewMapper;
import com.hoomsun.csas.sales.dao.UserCisMapper;
import com.hoomsun.csas.sales.dao.UserPbccreMapper;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月7日 <br>
 * 描述：信用审核的数据
 */
@Service("creditServer")
public class CreditServerImpl implements CreditServerI {

	@Autowired
	private UserPbccreMapper userPbccreMapper;
	@Autowired
	private UserCisMapper userCisMapper;
	@Autowired
	private PbccrcChartViewMapper pbccrcChartViewMapper;

	@Override
	public CreditVo findByApplyId(String applyId) {

		if (StringUtils.isBlank(applyId)) {
			throw new AuditException("参数异常! applyId is not null!");
		}

		// 简版征信 解析数据
		UserPbccre pbccre = userPbccreMapper.selectByApplyId(applyId);
		if (pbccre == null) {
			pbccre = new UserPbccre();
		}

		CreditVo vo = new CreditVo();
		vo.setPbccre(pbccre);

		UserCis userCis = userCisMapper.selectByApplyId(applyId);
		if (null == userCis) {
			userCis = new UserCis();
		}
		vo.setUserCis(userCis);

		// 征信可视化数据
		PbccrcChartViewVo pbccrcChart = pbccrcChartViewMapper.selectByApplyId(applyId);
		vo.setPbccrcChart(pbccrcChart);
		return vo;
	}

}
