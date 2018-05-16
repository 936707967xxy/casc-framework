/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import com.hoomsun.csas.sales.api.model.vo.FinancialVo;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月7日 <br>
 * 描述：审核财务信息
 */
public interface FinancialServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月7日 <br>
	 * 描述： 获取财务数据  信用卡账单   储蓄卡账单
	 * @param applyId
	 * @return
	 */
	FinancialVo findByApplyId(String applyId);
	
}
