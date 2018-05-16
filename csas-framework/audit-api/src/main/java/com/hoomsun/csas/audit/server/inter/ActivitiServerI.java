/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.inter;

import java.util.List;

import com.hoomsun.csas.audit.model.vo.HistoricTaskVo;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月28日 <br>
 * 描述：流程相关的业务数据
 */
public interface ActivitiServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月28日 <br>
	 * 描述： 历史任务
	 * @return
	 */
	List<HistoricTaskVo> findAuditHistoric(String applyId);
	
	
	
}
