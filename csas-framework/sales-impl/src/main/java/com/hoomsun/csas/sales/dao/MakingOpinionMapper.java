package com.hoomsun.csas.sales.dao;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.csas.sales.api.model.MakingOpinion;

public interface MakingOpinionMapper {
    int deleteByPrimaryKey(String makingId);

    int insert(MakingOpinion record);

    int insertSelective(MakingOpinion record);

    MakingOpinion selectByPrimaryKey(String makingId);

    int updateByPrimaryKeySelective(MakingOpinion record);

    int updateByPrimaryKey(MakingOpinion record);
    
    MakingOpinion selectByApplyIdAndTaskId(@Param("applyId")String applyId,@Param("taskId")String taskId);
    
    String selectByApplyId(String applyId);
    String selectByRollBackOpinion(String applyId);
}