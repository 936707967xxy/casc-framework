package com.hoomsun.csas.sales.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.csas.sales.api.model.ReviewApply;

public interface ReviewApplyMapper {
    int deleteByPrimaryKey(String reconId);

    int insert(ReviewApply record);

    int insertSelective(ReviewApply record);

    ReviewApply selectByPrimaryKey(String reconId);

    ReviewApply selectByApplyId(String applyId);
    
    int updateByPrimaryKeySelective(ReviewApply record);

    int updateByPrimaryKey(ReviewApply record);
    
    List<ReviewApply> findPage(Map<String, Object> record);

	int pageCount(Map<String, Object> param);
	/**
	 * 查询最新的一次复议申请记录
	 */
	ReviewApply findByApplyId(@Param("applyId")String applyId, @Param("procId")String processId);
}