package com.hoomsun.csas.sales.dao;

import com.hoomsun.csas.sales.api.model.SavingsBills;

public interface SavingsBillsMapper {
    int deleteByPrimaryKey(String sbId);

    int insert(SavingsBills record);

    int insertSelective(SavingsBills record);

    SavingsBills selectByPrimaryKey(String sbId);

    int updateByPrimaryKeySelective(SavingsBills record);

    int updateByPrimaryKey(SavingsBills record);
    
    int countBills(String scId);//根据储蓄卡id查询总条数
    
    int deleteByScId(String scId); //根据储蓄卡id删除之前数据
}