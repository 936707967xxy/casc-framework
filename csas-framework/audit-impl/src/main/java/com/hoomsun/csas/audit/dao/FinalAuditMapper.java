package com.hoomsun.csas.audit.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.csas.audit.model.FinalAudit;
import com.hoomsun.csas.audit.model.vo.UserApplyVO;

public interface FinalAuditMapper {
	int deleteByPrimaryKey(String finalId);

	int insert(FinalAudit record);

	int insertSelective(FinalAudit record);

	FinalAudit selectByPrimaryKey(String finalId);
	

	int updateByPrimaryKeySelective(FinalAudit record);

	int updateByPrimaryKey(FinalAudit record);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月19日 <br>
	 * 描述： 审批列表
	 * 
	 * @param param
	 *            nodeStatus groupId custName pageIndex pageSize
	 * @return
	 */
	Integer findFinalAuditCount(Map<String, Object> param);

	List<UserApplyVO> findPager(Map<String, Object> param);
	
	FinalAudit findByApplyId(String applyId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月21日 <br>
	 * 描述： 查询数据状态为XXX的审批数据id
	 * 
	 * @param applyId
	 *            申请id
	 * @param taskId
	 *            任务id
	 * @param processInstanceId
	 *            流程实例id
	 * @param preStatus
	 *            审核数据状态值
	 * @return
	 */
	FinalAudit selectIdByProcess(@Param("applyId") String applyId, @Param("taskId") String taskId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月21日 <br>
	 * 描述： 修改审核数据状态
	 * 
	 * @param applyId
	 *            申请id
	 * @param taskId
	 *            任务id
	 * @param processInstanceId
	 *            流程实例id
	 * @param preStatus
	 *            审核数据状态值
	 * @return
	 */
	Integer updatePreStatus(@Param("applyId") String applyId, @Param("taskId") String taskId, @Param("processInstanceId") String processInstanceId, @Param("preStatus") Integer preStatus);
	@Deprecated
	int deleteWhenWithDrow(@Param("applyId") String applyId, @Param("processId") String processId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月29日 <br>
	 * 描述： 获取审核的退回数据 初审显示
	 * @param applyId
	 * @return
	 */
	List<FinalAudit> findRollBackByApplyId(String applyId);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月24日 <br>
	 * 描述： 查看是否保存了草稿
	 * @param applyId
	 * @param taskId
	 * @return
	 */
	Integer selectCountByApplyAndTaskId(@Param("applyId") String applyId, @Param("taskId") String taskId);

	
}