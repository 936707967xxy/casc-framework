package com.hoomsun.core.dao;


import com.hoomsun.core.model.SysProductAdvance;

public interface SysProductAdvanceMapper {
//    int deleteByPrimaryKey(String id);
//
//    int insert(SysProductAdvance record);
//
    int insertSelective(SysProductAdvance record);
//
//    SysProductAdvance selectByPrimaryKey(String id);
//
//    int updateByPrimaryKeySelective(SysProductAdvance record);
//
//    int updateByPrimaryKey(SysProductAdvance record);
    
    /**
     * 
     * 作者：liushuai <br>
     * 创建时间：2017年11月29日 <br>
     * 描述： 调用更新的时候删除数据
     * @param prodId
     * @return
     */
    int deleteByProdId(String prodId);
}