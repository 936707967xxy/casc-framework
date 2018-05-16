package com.hoomsun.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.core.model.SysCompany;
import com.hoomsun.core.model.vo.VueLazyTree;

public interface SysCompanyMapper {
    int deleteByPrimaryKey(String comId);

    int insert(SysCompany record);

    int insertSelective(SysCompany record);

    SysCompany selectByPrimaryKey(String comId);

    int updateByPrimaryKeySelective(SysCompany record);

    int updateByPrimaryKey(SysCompany record);
    
    /**
     * 作者：liushuai<br>
     * 创建时间：2017年9月11日<br>
     * 描述：满足筛选条件的数据 分页  
     * @param param keys:pageIndex,pageSize,comName
     * @return
     */
	List<SysCompany> findPageData(Map<String, Object> param);
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月11日<br>
	 * 描述：满足筛选条件的数据量
	 * @param param
	 * @return keys:pageIndex,pageSize,comName
	 */
	int findPageCount(Map<String, Object> param);
    
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月11日<br>
	 * 描述：查询出所有的数据
	 * 
	 * @return
	 */
	List<SysCompany> findAllData();
	
	List<Map<String, Object>> findtreeData();
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月9日 <br>
	 * 描述： vue tree数据
	 * @param comParent
	 * @return
	 */
	List<VueLazyTree> findVueTreeData(@Param("comParent")String comParent);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月10日 <br>
	 * 描述： 修改的时候回显查询
	 * @param comId
	 * @return
	 */
	SysCompany selectByComId(String comId);
}