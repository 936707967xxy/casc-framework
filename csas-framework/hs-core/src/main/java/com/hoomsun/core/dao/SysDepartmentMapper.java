package com.hoomsun.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.core.model.SysDepartment;
import com.hoomsun.core.model.vo.VueLazyTree;

public interface SysDepartmentMapper {
    int deleteByPrimaryKey(String deptId);

    int insert(SysDepartment record);

    int insertSelective(SysDepartment record);

    SysDepartment selectByPrimaryKey(String deptId);

    int updateByPrimaryKeySelective(SysDepartment record);

    int updateByPrimaryKey(SysDepartment record);
    /**
     * 作者：liusong<br>
     * 创建时间：2017年9月11日<br>
     * 描述：满足筛选条件的数据 分页  
     * @param param keys:pageIndex,pageSize,empName,comId,deptId
     * @return
     */
    List<SysDepartment> findPageData(Map<String,Object> param);
    /**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月11日<br>
	 * 描述：满足条件的数据量
	 * @param param 筛选条件 keys:pageIndex,pageSize,roleName
	 * @return
	 */
	int findPageCount(Map<String, Object> param);
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月11日<br>
	 * 描述：获取所有的部门信息
	 * @return
	 */
	List<SysDepartment> findAllData();
	/**
     * 作者：liusong <br>
     * 创建时间：2017年9月11日<br>
     * 描述：批量添加员工部门的关联关系
     * @param empRoles
     * @return
     */
    int batchIntser(List<SysDepartment> departments);
    /**
     * 作者：liusong<br>
     * 创建时间：2017年9月11日<br>
     * 描述：根据公司ID删除公司和部门的关系
     * @param comId 公司ID
     * @return
     */
    int deleteByComId(String comId);
    /**
     * 作者：liusong <br>
     * 创建时间：2017年9月11日 <br>
     * 描述： 获取某公司和部门的关联关系
     * @param comId 公司ID
     * @return
     */
	List<SysDepartment> findByComId(String comId);
	
	List<Map<String, Object>>  findByCom(String comId);
	
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年11月29日 <br>
	 * 描述： 删除多个部门
	 * @param deptIds
	 * @return
	 */
	int deleteByMultiDepts(@Param("deptIds")List<String> deptIds);
	
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月9日 <br>
	 * 描述： vue tree数据
	 * @param deptParent
	 * @return
	 */
	List<VueLazyTree> findVueTreeData(@Param("deptParent") String deptParent);
	
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月10日 <br>
	 * 描述： 修改的时候显示部门信息
	 * @param deptId
	 * @return
	 */
	SysDepartment selectByDeptId(String deptId);
	
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月23日 <br>
	 * 描述： 获取所有营业部的名称用于下拉展示
	 * @return
	 */
	List<SysDepartment> selectAllStoreInfoShow();
}