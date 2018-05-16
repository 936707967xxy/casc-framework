package com.hoomsun.after.dao;

import java.util.List;

import com.hoomsun.after.api.model.table.ApplyDetail;

public interface ApplyDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApplyDetail record);

    int insertSelective(ApplyDetail record);

    ApplyDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplyDetail record);

    int updateByPrimaryKey(ApplyDetail record);
    /**
     * 
     * 作者：金世强 <br>
     * 创建时间：2018年4月18日 <br>
     * 描述： 加载审批流程
     * @param applyId
     * @return
     */
	List<ApplyDetail> getApplyDetail(String applyId);
}