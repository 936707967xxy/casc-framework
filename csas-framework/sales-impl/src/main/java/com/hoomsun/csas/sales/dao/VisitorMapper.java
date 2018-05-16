package com.hoomsun.csas.sales.dao;

import java.util.List;
import java.util.Map;
import com.hoomsun.csas.sales.api.model.Visitor;

public interface VisitorMapper {
    int deleteByPrimaryKey(String visId);

    int insert(Visitor record);

    int insertSelective(Visitor record);

    Visitor selectByPrimaryKey(String visId);

    int updateByPrimaryKeySelective(Visitor record);

    int updateByPrimaryKey(Visitor record);
    
    /**
     * 
     * 作者：liming<br>
     * 创建时间：2017年12月9日 <br>
     * 描述： 分页数据
     * @param param
     * @return
     */
	List<Visitor> findPageData(Map<String, Object> param);
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月15日 <br>
	 * 描述： 根据条件查询
	 * @param param
	 * @return
	 */
	Integer findPageCount(Map<String, Object> param);
}