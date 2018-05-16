package com.hoomsun.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.core.model.SysRoleCpt;

public interface SysRoleCptMapper {
    int insert(SysRoleCpt record);

    int insertSelective(SysRoleCpt record);
    
    Integer deleteByCptId(String cptId);
    
    Integer deleteByRoleId(String roleId);
    
    List<SysRoleCpt> findByRoleId(String roleId);
    
    Integer batchDeleteByCptIds(@Param("removeCptIds")List<String> removeCptIds);
    
    Integer batchInsert(@Param("addRoleCpts")List<SysRoleCpt> addRoleCpts);
}