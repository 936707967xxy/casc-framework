package com.hoomsun.csas.sales.dao;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.csas.sales.api.model.UserAllAuthInfo;

/**
 * 所有认证信息mapper--申请单绑定
 * @author ygzhao
 *
 */
public interface UserAllAuthInfoMapper {
    int deleteByPrimaryKey(String allAuthPk);

    int insert(UserAllAuthInfo record);

    int insertSelective(UserAllAuthInfo record);

    UserAllAuthInfo selectByPrimaryKey(String allAuthPk);

    int updateByPrimaryKeySelective(UserAllAuthInfo record);

    int updateByPrimaryKey(UserAllAuthInfo record);
    
    /**
     * 根据申请id查询认证信息
     * ygzhao
     * @param applyId
     * @return
     */
    UserAllAuthInfo selectByApplyId(@Param("applyId")String applyId);
}