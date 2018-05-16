package com.hoomsun.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.core.model.SysEmpRole;

public interface SysEmpRoleMapper {
    int deleteByPrimaryKey(SysEmpRole empRole);

    int insert(SysEmpRole empRole);

    int insertSelective(SysEmpRole empRole);
    
    /**
     * 作者：ywzou<br>
     * 创建时间：2017年9月6日<br>
     * 描述：根据员工ID删除员工和角色的关系
     * @param empId 员工ID
     * @return
     */
    int deleteByEmpId(String empId);
    /**
     * 作者：ywzou<br>
     * 创建时间：2017年9月6日<br>
     * 描述：根据角色ID删除员工和角色的关系
     * @param roleId 角色ID
     * @return
     */
    int deleteByRoleId(String roleId);
    /**
     * 作者：ywzou <br>
     * 创建时间：2017年9月10日 <br>
     * 描述：根据角色的ID集合批量删除员工与角色的关系
     * @param roleIds
     * @return
     */
    int batchDeleteByRoleId(@Param("roleIds")List<String> roleIds);
    /**
     * 作者：ywzou <br>
     * 创建时间：2017年9月10日 <br>
     * 描述：批量添加员工角色的关联关系
     * @param empRoles
     * @return
     */
    int batchIntser(@Param("empRoles")List<SysEmpRole> empRoles);
    /**
     * 作者：ywzou <br>
     * 创建时间：2017年9月10日 <br>
     * 描述： 获取某员工和角色的关联关系
     * @param empId 员工ID
     * @return
     */
	List<SysEmpRole> findByEmpId(String empId);
}