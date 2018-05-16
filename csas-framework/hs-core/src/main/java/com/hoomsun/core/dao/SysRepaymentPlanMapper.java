package com.hoomsun.core.dao;

import java.util.List;

import com.hoomsun.core.model.SysRepaymentPlan;

public interface SysRepaymentPlanMapper {
    int deleteByPrimaryKey(String planId);
    
    int deleteByApplyId(String applyId);

    int insert(SysRepaymentPlan record);

    int insertSelective(SysRepaymentPlan record);

    SysRepaymentPlan selectByPrimaryKey(String planId);

    int updateByPrimaryKeySelective(SysRepaymentPlan record);

    int updateByPrimaryKey(SysRepaymentPlan record);
    
    List<SysRepaymentPlan> findByApplyId(String applyId);
    
    int countFindByApplyId(String applyId);
    
    List<SysRepaymentPlan>  findPlanByApplyId(String applyId);
}