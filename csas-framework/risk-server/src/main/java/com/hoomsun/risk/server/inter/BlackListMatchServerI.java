/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.server.inter;

import java.util.List;

import com.hoomsun.common.model.Json;
import com.hoomsun.risk.model.UserApply;
import com.hoomsun.risk.model.UserContact;
import com.hoomsun.risk.model.match.BlackListMatch;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月24日 <br>
 * 描述：黑名单勾稽匹配
 */
public interface BlackListMatchServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月24日 <br>
	 * 描述： 申请数据与黑名单的数据匹配
	 * 
	 * @param applyId
	 *            申请ID
	 * @return 返回匹配的数据信息
	 */
	public Json matchBlack(String applyId);

	public Json matchApplyHistory(UserApply userApply, List<UserContact> contacts);
	
	public BlackListMatch findByApplyId(String applyId);
}
