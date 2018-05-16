/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.csas.sales.api.model.PbccrcChartView;
import com.hoomsun.csas.sales.api.model.vo.PbccrcChartViewVo;
import com.hoomsun.csas.sales.api.server.inter.PbccrcChartViewServerI;
import com.hoomsun.csas.sales.dao.PbccrcChartViewMapper;

/**
 * 作者：liushuai <br>
 * 创建时间：2018年2月9日 <br>
 * 描述：
 */
@Service("pbccrcChartViewServer")
public class PbccrcChartViewServerImpl implements PbccrcChartViewServerI {

	@Autowired
	private PbccrcChartViewMapper pbccrcChartViewMapper;
	
	@Override
	public PbccrcChartViewVo findPbccrcChartViewInfo(String applyId) {
		return pbccrcChartViewMapper.selectByApplyId(applyId);
	}

	@Override
	public Json insertPbccrcChartViewInfo(PbccrcChartView pbccrcChartView) {
		pbccrcChartView.setPbViewId(PrimaryKeyUtil.getPrimaryKey());
		
		int insert = pbccrcChartViewMapper.insert(pbccrcChartView);
		if (insert > 0) {
			return new Json(true, "添加征信图表数据成功!");
		} else {
			return new Json(false, "添加征信图表数据失败!");
		}
	}

}
