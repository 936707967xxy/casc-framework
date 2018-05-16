package com.hoomsun.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hoomsun.core.model.SysRoleResources;

public interface SysRoleResourcesMapper {
    int deleteByPrimaryKey(SysRoleResources key);

    int insert(SysRoleResources record);

    int insertSelective(SysRoleResources record);
    /**
     * 作者：ywzou<br>
     * 创建时间：2017年9月6日<br>
     * 描述：根据角色ID删除角色资源关系
     * @param roleId 角色ID
     * @return
     */
    int deleteByRoleId(String roleId);
    /**
     * 作者：ywzou<br>
     * 创建时间：2017年9月6日<br>
     * 描述：根据资源ID删除角色资源关系
     * @param resId 资源ID
     * @return
     */
    int deleteByResId(String resId);
    /**
     * 作者：ywzou<br>
     * 创建时间：2017年9月6日<br>
     * 描述：根据角色ID获取角色和资源的关系
     * @param roleId
     * @return
     */
	List<SysRoleResources> findByRoleId(String roleId);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：根据资源ID批量删除角色资源关系
	 * @param removeResIds  要删除的角色资源关系的资源ID集合
	 * @return
	 */
	int batchDeleteByResIds(@Param("removeResIds")List<String> removeResIds);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：批量添加角色资源关系
	 * @param addRoleRes 角色资源关系集合
	 * @return
	 */
	int batchInsert(@Param("addRoleRes")List<SysRoleResources> addRoleRes);
}