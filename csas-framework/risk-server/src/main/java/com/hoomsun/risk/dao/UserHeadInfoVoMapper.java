package com.hoomsun.risk.dao;

import java.util.Map;

import com.hoomsun.risk.model.UserHeadInfoVo;

public interface UserHeadInfoVoMapper {
    int deleteByPrimaryKey(String poId);

    int insert(UserHeadInfoVo record);

    int insertSelective(UserHeadInfoVo record);

    UserHeadInfoVo selectByPrimaryKey(String poId);

    int updateByPrimaryKeySelective(UserHeadInfoVo record);

    int updateByPrimaryKey(UserHeadInfoVo record);
    
    /* 获取资信   */
    Map<String, Object> selectCis(String applyId); 
    /* 获取征信   */
    Map<String, Object> selectPbccrc(String applyId);
    /* 获取同盾   */
    Map<String, Object> selectTongdun(String applyId);
    /* 获取社保   */
    Map<String, Object> selectSocialsecurity(String applyId);
    /* 获取职业   */
    Map<String, Object> selectOccupationalinfo(String applyId);
    /* 获取申请单信息   */
    Map<String, Object>  findApplybyApplyId(String applyId);
    /* 获取门店地址   */
    Map<String, Object>  findStorebyId(String deptId);
}