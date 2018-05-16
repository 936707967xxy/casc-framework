package com.hoomsun.csas.sales.dao;

import com.hoomsun.csas.sales.api.model.SavingsCard;

public interface SavingsCardMapper {
    int deleteByPrimaryKey(String scId);

    int insert(SavingsCard record);

    int insertSelective(SavingsCard record);

    SavingsCard selectByPrimaryKey(String scId);

    int updateByPrimaryKeySelective(SavingsCard record);

    int updateByPrimaryKey(SavingsCard record);
    
    SavingsCard findByApplyId(String applyId);//根据applyId查询数据
    
    int countCard(String applyId);//根据applyId查询总条数
    
}