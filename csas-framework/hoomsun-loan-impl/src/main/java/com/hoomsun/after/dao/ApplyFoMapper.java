package com.hoomsun.after.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hoomsun.after.api.model.table.ApplyFo;

public interface ApplyFoMapper {
	int deleteByPrimaryKey(String id);

	int insert(ApplyFo record);

	int insertSelective(ApplyFo record);

	ApplyFo selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ApplyFo record);

	int updateByPrimaryKey(ApplyFo record);

	ApplyFo selectByApplyId(String applyId);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述：根据申请状态查询信息变更申请处在此环节的单子
	 * 
	 * @param params
	 * @return
	 */
	List<ApplyFo> selectMessageUpdateByStstus(Map<String, Object> params);

	/**
	 * 根据申请状态 申请人员集合 查询信息变更申请处在此环节的单子
	 */
	List<ApplyFo> selectMessageUpdateByStstusAndUsers(Map<String, Object> params);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述：根据信息变更申请状态查询处在此环节下单子的数量
	 * 
	 * @param status
	 * @return
	 */
	int selectMessageUpdateByStstusCount(Map<String, Object> params);

	/**
	 * 根据申请状态 申请人员集合 查询信息变更申请处在此环节的单子数量
	 */
	int selectMessageUpdateByStstusAndUsersCount(Map<String, Object> params);

	/**
	 * 加载信息变更审批申请记录
	 */
	List<ApplyFo> getMessageUpdateHistory(Map<String, Object> params);
	List<ApplyFo> getMessageUpdateHistoryAll(Map<String, Object> params);

	/**
	 * 加载信息变更审批申请记录数
	 */
	int getMessageUpdateHistoryCount(Map<String, Object>  params);
	int getMessageUpdateHistoryAllCount(Map<String, Object>  params);

	/**
	 * 根据申请状态 申请人员集合 查询逾期减免申请处在此环节的单子
	 */
	List<ApplyFo> selectSubApplyByStstus(Map<String, Object> params);

	/**
	 * 根据申请状态 申请人员集合 查询逾期减免申请处在此环节的单子
	 */
	List<ApplyFo> selectSubApplyByStstusAndUsers(Map<String, Object> params);

	/**
	 * 根据逾期减免申请状态查询处在此环节下单子的数量
	 */
	int selectSubApplyByStstusCount(Map<String, Object> params);

	/**
	 * 根据申请状态 申请人员集合 查询逾期减免申请处在此环节的单子数量
	 */
	int selectSubApplyByStstusAndUsersCount(Map<String, Object> params);

	/**
	 * 加载减免审批申请记录
	 */
	List<ApplyFo> getSubapplyHistory(Map<String, Object> params);
	List<ApplyFo> getSubapplyHistoryAll(Map<String, Object> params);

	/**
	 * 加载减免审批申请记录数
	 */
	int getSubapplyHistoryCount(Map<String, Object> params);
	int getSubapplyHistoryAllCount(Map<String, Object> params);
	
	/**
	 * 根据申请状态 申请人员集合 查询外访申请申请处在此环节的单子
	 */
	List<ApplyFo> selectOutBoundByStstus(Map<String, Object> params);
	List<ApplyFo> selectOutBoundByStstusAndUsers(Map<String, Object> params);

	/**
	 * 根据申请状态 申请人员集合 查询外访申请处在此环节的单子数量
	 */
	int selectOutBoundByStstusCount(Map<String, Object> params);
	int selectOutBoundByStstusCountAndUsers(Map<String, Object> params);
	
	/**
	 * 获取外访申请历史记录
	 */
	List<ApplyFo> getOutboundHistory(Map<String, Object> params);
	int getOutboundHistoryCount(Map<String, Object> params);

	/**
	 * 根据申请状态 申请人员集合 查询留案申请处在此环节的单子
	 */
	List<ApplyFo> selectLeaveApplyByStstus(Map<String, Object> params);
	List<ApplyFo> selectLeaveApplyByStstusAndUsers(Map<String, Object> params);
	
	
	/**
	 * 根据申请状态 申请人员集合 查询留案申请处在此环节的单子數量
	 */
	int selectLeaveApplyByStstusCount(Integer status);
	int selectLeaveApplyByStstusAndUsersCount(Map<String, Object> params);

	/**
	 * 获取留案申请历史记录
	 */
	List<ApplyFo> getLeaveapplyHistory(Map<String, Object> params);
	int getLeaveapplyHistoryCount(String applicationCastId);

}