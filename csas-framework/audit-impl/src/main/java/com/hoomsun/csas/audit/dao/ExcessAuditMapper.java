package com.hoomsun.csas.audit.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.csas.audit.model.ExcessAudit;
import com.hoomsun.csas.audit.model.vo.UserApplyVO;

public interface ExcessAuditMapper {
	int deleteByPrimaryKey(String excessId);

	int insert(ExcessAudit record);

	int insertSelective(ExcessAudit record);

	ExcessAudit selectByPrimaryKey(String finalId);
	ExcessAudit selectByExcessAu(String applyId);
	

	int updateByPrimaryKeySelective(ExcessAudit record);

	int updateByPrimaryKey(ExcessAudit record);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月19日 <br>
	 * 描述： 审批列表
	 * 
	 * @param param
	 *            nodeStatus groupId custName pageIndex pageSize
	 * @return
	 */
	Integer findExcessAuditCount(Map<String, Object> param);

	List<UserApplyVO> findPager(Map<String, Object> param);
	
	ExcessAudit findByApplyId(String applyId);

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
	ExcessAudit selectIdByProcess(@Param("applyId") String applyId, @Param("taskId") String taskId, @Param("processInstanceId") String processInstanceId);
}