package com.hoomsun.csas.audit.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.csas.audit.model.PreAudit;
import com.hoomsun.csas.audit.model.vo.UserApplyVO;
import com.hoomsun.csas.sales.api.model.vo.RollBackInfoVo;

public interface PreAuditMapper {
    int deleteByPrimaryKey(String preId);

    int insert(PreAudit record);

    int insertSelective(PreAudit record);

    PreAudit selectByPrimaryKey(String preId);
    

    int updateByPrimaryKeySelective(PreAudit record);

    int updateByPrimaryKey(PreAudit record);
    /**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月18日 <br>
	 * 描述： 初审列表数据
	 * 
	 * @param param
	 *            nodeStatus groupId custName pageIndex pageSize
	 * @return
	 */
	Integer findPreAuditCount(Map<String, Object> param);

	List<UserApplyVO> findPager(Map<String, Object> param);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月18日 <br>
	 * 描述： 根据申请ID查询数据
	 * 
	 * @param
	 * @return
	 */
	PreAudit findByApplyId(String applyId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月21日 <br>
	 * 描述： 查询数据状态为XXX的审批数据id
	 * 
	 * @param applyId
	 *            申请id
	 * @param taskId
	 *            任务id
	 * @return
	 */
	PreAudit selectIdByProcess(@Param("applyId") String applyId, @Param("taskId") String taskId);

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
	/**
	 * 根据申请ID 和 任务ID验证审核数据 
	 * @param applyId
	 * @param taskId
	 * @return
	 */
	Integer checkPreAudit(@Param("applyId")String applyId,@Param("taskId")String taskId);

	List<RollBackInfoVo> selectRollBackInfo(String applyId);
}