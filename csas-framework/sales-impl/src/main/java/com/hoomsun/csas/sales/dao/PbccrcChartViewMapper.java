package com.hoomsun.csas.sales.dao;

import com.hoomsun.csas.sales.api.model.PbccrcChartView;
import com.hoomsun.csas.sales.api.model.vo.PbccrcChartViewVo;

public interface PbccrcChartViewMapper {
    int deleteByPrimaryKey(String pbViewId);

    int insert(PbccrcChartView record);

    int insertSelective(PbccrcChartView record);

    PbccrcChartView selectByPrimaryKey(String pbViewId);

    int updateByPrimaryKeySelective(PbccrcChartView record);

    int updateByPrimaryKey(PbccrcChartView record);
    
    PbccrcChartViewVo selectByApplyId(String applyId);
}