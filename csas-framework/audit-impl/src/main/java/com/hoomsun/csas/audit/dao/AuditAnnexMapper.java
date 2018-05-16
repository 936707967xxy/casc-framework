package com.hoomsun.csas.audit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.csas.audit.model.AuditAnnex;

public interface AuditAnnexMapper {
    int deleteByPrimaryKey(String anxId);
    
    int insert(AuditAnnex record);

    int insertSelective(AuditAnnex record);

    AuditAnnex selectByPrimaryKey(String anxId);

    int updateByPrimaryKeySelective(AuditAnnex record);

    int updateByPrimaryKey(AuditAnnex record);
    
	List<AuditAnnex> findByApplyId(String applyId);
	
	List<AuditAnnex> findByapplyIdAndAnnexType(@Param("applyId")String applyId,@Param("anxType")Integer anxType);
}