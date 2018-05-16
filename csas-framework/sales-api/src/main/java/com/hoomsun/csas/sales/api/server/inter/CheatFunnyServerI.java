/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import com.hoomsun.common.model.Json;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月8日 <br>
 * 描述：反欺诈匹配的操作
 */
public interface CheatFunnyServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月24日 <br>
	 * 描述： 欺诈勾稽数据
	 * 
	 * @param applyId
	 * @return
	 */
	Json cheatMatch(String applyId);
}
