/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.server.inter;

import com.hoomsun.common.model.Json;
import com.hoomsun.risk.model.UserApply;
import com.hoomsun.risk.model.match.CallTopMatch;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月24日 <br>
 * 描述：通话记录中通话频次TOP-N 通讯录中通话频次TOP-N 数据匹配 业务接口
 */
public interface CallTopMatchServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月24日 <br>
	 * 描述： 通话记录TOP-N匹配
	 * 
	 * @param applyId
	 *            申请ID
	 * @return
	 */
	public Json matchCallTop(String applyId);

	public Json matchCallTop(UserApply userApply);
	
	public CallTopMatch findByApplyId(String applyId);
}
