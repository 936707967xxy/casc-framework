package com.hoomsun.csas.sales.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.csas.sales.api.model.SupplementSubmit;

public interface SupplementSubmitMapper {
    int deleteByPrimaryKey(String subPkId);

    int insert(SupplementSubmit record);

    int insertSelective(SupplementSubmit record);

    SupplementSubmit selectByPrimaryKey(String subPkId);

    int updateByPrimaryKeySelective(SupplementSubmit record);

    int updateByPrimaryKey(SupplementSubmit record);
    
    /**
     * ygzhao
     * 2017-12-18
     * 提交回显页面
     * @param applyId
     * @return
     */
    SupplementSubmit findById(String applyId);

    /**
     * 回显历史意见信息
     * @param applyId 
     * @return
     */
	List<Map<String, Object>> beforeSubmit(String applyId);

	/**
	 * 是否是协议审核退回
	 * @param applyId
	 * @return
	 */
	Map<String, Object> isConAudit(@Param("applyId")String applyId);
}