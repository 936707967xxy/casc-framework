/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.csas.audit.model.vo.HistoricTaskVo;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月28日 <br>
 * 描述：审批历史
 */
public interface BPMNMapper {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月28日 <br>
	 * 描述：获取审批历史
	 * 
	 * @param applyId
	 * @return
	 */
	List<HistoricTaskVo> findAuditHistoric(String applyId);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月6日 <br>
	 * 描述： 当前用户签收的任务 减去 允许签收的任务数量
	 * 
	 * @param empId
	 * @return
	 */
	Integer selectClaimTaskSign(String empId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月18日 <br>
	 * 描述： 某申请的某节点的最后一位处理人
	 * 
	 * @param applyId
	 * @param procId
	 * @param taskKey
	 * @return
	 */
	Map<String, Object> findHiTaskAssignee(@Param("applyId") String applyId, @Param("procInstId") String procId, @Param("taskKey") String taskKey);

}
