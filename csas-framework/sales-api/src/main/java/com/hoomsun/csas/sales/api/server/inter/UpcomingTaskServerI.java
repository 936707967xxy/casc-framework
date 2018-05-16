/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import java.util.List;

import com.hoomsun.common.model.Json;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.csas.sales.api.model.vo.UpcomingTaskVo;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月22日 <br>
 * 描述：
 */
public interface UpcomingTaskServerI {

	public List<UpcomingTaskVo> findRunTask(String emp, String storeId, String deptId);
	
	Json claimTask(String applyId,SessionRouter session);
	
	Json checkClaim(String applyId,SessionRouter session);

}
