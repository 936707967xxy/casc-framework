package com.hoomsun.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.core.model.SysComponents;

public interface SysComponentsMapper {
    int deleteByPrimaryKey(String componentsId);

    int insert(SysComponents record);

    int insertSelective(SysComponents record);

    SysComponents selectByPrimaryKey(String componentsId);

    int updateByPrimaryKeySelective(SysComponents record);

    int updateByPrimaryKey(SysComponents record);
    
    /**
     * 作者：liusong <br>
     * 创建时间：2017年11月16日 <br>
     * 描述： 查询所有组件
     * @return
     */
    List<SysComponents> findAll(@Param("systemName")String systemName);
    List<SysComponents> findByName(@Param("name")String name,@Param("systemName")String systemName);
    /**
     * 作者：liusong <br>
     * 创建时间：2017年11月16日 <br>
     * 描述： 根据条件进行分页查询数据
     * @param param
     * @return
     */
    List<SysComponents> findPageData(Map<String, Object> param);
    /**
     * 作者：liusong <br>
     * 创建时间：2017年11月16日 <br>
     * 描述： 
     * @param param
     * @return
     */
    int findPageCount(Map<String, Object> param);
    
    
    /**
     * 
     * 作者：liushuai <br>
     * 创建时间：2018年1月22日 <br>
     * 描述： 修改组件的时候调用
     * @param componentsId
     * @return
     */
    SysComponents selectByCptId(String componentsId);
    
    
}