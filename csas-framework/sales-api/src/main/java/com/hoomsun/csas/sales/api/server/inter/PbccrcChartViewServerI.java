/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import com.hoomsun.common.model.Json;
import com.hoomsun.csas.sales.api.model.PbccrcChartView;
import com.hoomsun.csas.sales.api.model.vo.PbccrcChartViewVo;

/**
 * 作者：liushuai <br>
 * 创建时间：2018年2月9日 <br>
 * 描述：
 */
public interface PbccrcChartViewServerI {

	PbccrcChartViewVo findPbccrcChartViewInfo(String applyId);
	
	Json insertPbccrcChartViewInfo(PbccrcChartView pbccrcChartView);
}
