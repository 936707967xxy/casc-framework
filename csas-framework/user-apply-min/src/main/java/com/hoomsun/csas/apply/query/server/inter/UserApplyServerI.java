/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.apply.query.server.inter;

import com.hoomsun.csas.apply.query.model.UserApply;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月22日 <br>
 * 描述：申请表server
 */
public interface UserApplyServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月1日 <br>
	 * 描述： 根据组件查询申请数据 申请表信息
	 * @param applyId 申请ID
	 * @return
	 */
	public UserApply findApplyById(String applyId);
}
