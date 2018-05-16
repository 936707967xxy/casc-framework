/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import com.hoomsun.csas.sales.api.model.vo.CreditVo;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月7日 <br>
 * 描述：信用数据的业务
 */
public interface CreditServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月7日 <br>
	 * 描述： 审核信用的数据信息 征信  上海资信
	 * @param applyId
	 * @return
	 */
	CreditVo findByApplyId(String applyId);
	
}
