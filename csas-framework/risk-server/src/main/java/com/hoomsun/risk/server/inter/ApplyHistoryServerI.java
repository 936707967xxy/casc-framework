/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.server.inter;

import java.util.List;

import com.hoomsun.common.model.Json;
import com.hoomsun.risk.model.UserApply;
import com.hoomsun.risk.model.UserContact;
import com.hoomsun.risk.model.match.ApplyHistory;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月13日 <br>
 * 描述：申请历史数据匹配业务接口
 */
public interface ApplyHistoryServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月13日 <br>
	 * 描述： 申请历史记录匹配
	 * 
	 * @param applyId
	 *            本次申请ID 需要勾稽的申请数据
	 * @return
	 */
	public Json matchApplyHistory(String applyId);

	public Json matchApplyHistory(UserApply userApply, List<UserContact> nowContacts);
	
	public ApplyHistory findByApplyId(String applyId);

}
