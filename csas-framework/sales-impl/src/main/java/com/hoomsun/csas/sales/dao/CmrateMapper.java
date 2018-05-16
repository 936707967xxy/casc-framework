package com.hoomsun.csas.sales.dao;

import java.util.List;
import java.util.Map;

import com.hoomsun.csas.sales.api.model.Cmrate;


public interface CmrateMapper {
    int deleteByPrimaryKey(String cmrateId);

    int insert(Cmrate record);

    int insertSelective(Cmrate record);

    Cmrate selectByPrimaryKey(String cmrateId);

    int updateByPrimaryKeySelective(Cmrate record);

    int updateByPrimaryKey(Cmrate record);
    
    /**
     * 作者：liushuai<br>
     * 创建时间：2017年9月14日<br>
     * 描述：满足筛选条件的数据 分页  
     * @param param keys:pageIndex,pageSize,comName
     * @return
     */
	List<Cmrate> findPageData(Map<String, Object> param);
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：满足筛选条件的数据量
	 * @param param
	 * @return keys:pageIndex,pageSize,comName
	 */
	int findPageCount(Map<String, Object> param);
    
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：查询出所有的数据
	 * 
	 * @return
	 */
	List<Cmrate> findAllData();
	
	List<Cmrate> findAllDataApp();
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：查询出所有线下产品的数据
	 * 
	 * @return
	 */
	List<Cmrate> findAllDataCredit();
	
	Cmrate findAllDataAppdetial(String applyApprovAll);
}