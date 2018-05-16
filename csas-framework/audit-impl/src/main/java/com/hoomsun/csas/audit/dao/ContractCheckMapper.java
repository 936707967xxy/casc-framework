package com.hoomsun.csas.audit.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.csas.audit.model.ContractCheck;
import com.hoomsun.csas.audit.model.vo.UserApplyConVO;


public interface ContractCheckMapper {
    int deleteByPrimaryKey(String checkId);

    int insert(ContractCheck record);

    int insertSelective(ContractCheck record);

    ContractCheck selectByPrimaryKey(String checkId);

    int updateByPrimaryKeySelective(ContractCheck record);

    int updateByPrimaryKey(ContractCheck record);
    /**
     * 作者：ywzou <br>
     * 创建时间：2017年12月12日 <br>
     * 描述： 分页数据的查询
     * @param param
     * @return
     */
    List<UserApplyConVO> findPager(Map<String, Object> param);
    Integer findPagerCount(Map<String, Object> param);
    
	Integer checkAudit(@Param("applyId")String applyId, @Param("taskId")String taskId);
	
	ContractCheck checkConAudit(@Param("applyId")String applyId, @Param("taskId")String taskId);

	ContractCheck findByApplyId(String applyId);
	
	List<ContractCheck> findRollBack(String applyId);
    
}