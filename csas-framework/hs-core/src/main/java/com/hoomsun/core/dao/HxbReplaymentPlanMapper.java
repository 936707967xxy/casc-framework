package com.hoomsun.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.core.model.HxbReplaymentPlan;


public interface HxbReplaymentPlanMapper {
    int deleteByPrimaryKey(String planId);

    int insert(HxbReplaymentPlan record);

    int insertSelective(HxbReplaymentPlan record);

    HxbReplaymentPlan selectByPrimaryKey(String planId);

    int updateByPrimaryKeySelective(HxbReplaymentPlan record);

    int updateByPrimaryKey(HxbReplaymentPlan record);
    
    int deleteByApplyId(String applyId);
    
    int countByApplyId(String applyId);
    
    List<HxbReplaymentPlan> selectByApplyId(String applyId);
    
    //根据applyId和期数查询数据
    HxbReplaymentPlan selectByApplyIdAndPhaseNumber(@Param("applyId")String applyId,@Param("phaseNumber")Integer phaseNumber);
    
    
}