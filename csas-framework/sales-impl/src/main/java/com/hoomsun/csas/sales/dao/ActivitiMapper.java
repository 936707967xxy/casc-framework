/**
 * Copyright www.idawn.org 邹益伟 idawnorg@gmail.com
 */
package com.hoomsun.csas.sales.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.csas.sales.api.model.vo.HistoricTaskVo;
import com.hoomsun.csas.sales.api.model.vo.UpcomingTaskVo;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月24日 <br>
 * 描述：流程相关的查询
 */
public interface ActivitiMapper {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月24日 <br>
	 * 描述： 查询历史任务数据
	 * 
	 * @param applyId
	 * @return
	 */
	List<HistoricTaskVo> findHistoricTask(String applyId);

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
	 * 创建时间：2018年1月22日 <br>
	 * 描述： 待办运行的任务
	 * 
	 * @param storeId
	 * @param empId
	 * @param deptId
	 * @return
	 */
	List<UpcomingTaskVo> findRunTask(@Param("storeId")String storeId, @Param("empId")String empId, @Param("deptId")String deptId,@Param("isMgr")Integer isMgr);
}
