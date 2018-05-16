/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import com.hoomsun.csas.sales.api.model.vo.CallBillsVo;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月7日 <br>
 * 描述：
 */
public interface CallBillsServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月7日 <br>
	 * 描述： 获取通话详单 电话核查数据
	 * @param applyId
	 * @return
	 */
	CallBillsVo findByApplyId(String applyId);
}
