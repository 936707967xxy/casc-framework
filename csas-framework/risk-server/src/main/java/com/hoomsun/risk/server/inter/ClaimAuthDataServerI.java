/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.server.inter;

import com.hoomsun.common.model.Json;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月9日 <br>
 * 描述：认领认证的数据
 */
public interface ClaimAuthDataServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月9日 <br>
	 * 描述： 认领数据
	 * @param applyId 申请编号
	 * @return
	 */
	public Json claimAuthData(String applyId);
	
}
