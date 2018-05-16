/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.csas.sales.api.model.vo.ParentDeptInfoVo;
import com.hoomsun.csas.sales.api.model.vo.SalesmanReportVo;

/**
 * 作者：liushuai <br>
 * 创建时间：2017年12月21日 <br>
 * 描述：
 */
public interface SalesmanReportVoMapper {
	
	List<ParentDeptInfoVo> selectChildIdList(String parentId);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年2月5日 <br>
	 * 描述：获取上级部门信息 
	 * @param deptId
	 * @return
	 */
	ParentDeptInfoVo selectParentDeptInfo(String deptId);
	
	SalesmanReportVo selectBySingDept(String deptId); // 单条业绩查询
	
	SalesmanReportVo selectByBigArea(@Param("parentId")String parentId, @Param("startDate")String startDate, @Param("endDate")String endDate);
	
	List<SalesmanReportVo> selectByArea(@Param("parentId")String parentId, @Param("startDate")String startDate, @Param("endDate")String endDate);
	
	List<SalesmanReportVo> selectByStore(@Param("parentId")String parentId, @Param("startDate")String startDate, @Param("endDate")String endDate);

	List<SalesmanReportVo> selectByCity(@Param("parentId")String parentId, @Param("startDate")String startDate, @Param("endDate")String endDate);
	
	List<SalesmanReportVo> selectByTeam(@Param("parentId")String parentId, @Param("startDate")String startDate, @Param("endDate")String endDate);
	
	List<SalesmanReportVo> selectByPerson(@Param("empId")String empId, @Param("parentId")String parentId, @Param("isMgr")Integer isMgr, @Param("startDate")String startDate, @Param("endDate")String endDate);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月29日 <br>
	 * 描述： 获取manage的所有部门id
	 * @param empId
	 * @return
	 */
	String selectDeptByManager(String empId);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月29日 <br>
	 * 描述： 获取所在等级(部门向上搜索几个)
	 * @param deptId
	 * @return
	 */
	Integer selectDeptLevel(String deptId);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月29日 <br>
	 * 描述： 获取部门名称和部门ID
	 * @param empId
	 * @return
	 */
	ParentDeptInfoVo selectDeptInfo(String deptId);

	SalesmanReportVo selectBySingDept( @Param("parentId")String parentId, @Param("startDate")String startDate, @Param("endDate")String endDate);
}
