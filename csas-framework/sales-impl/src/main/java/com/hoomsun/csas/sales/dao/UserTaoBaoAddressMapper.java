package com.hoomsun.csas.sales.dao;

import com.hoomsun.csas.sales.api.model.UserTaoBaoAddress;

public interface UserTaoBaoAddressMapper {
    int deleteByPrimaryKey(String tbAddressId);

    int insert(UserTaoBaoAddress record);

    int insertSelective(UserTaoBaoAddress record);

    UserTaoBaoAddress selectByPrimaryKey(String tbAddressId);

    int updateByPrimaryKeySelective(UserTaoBaoAddress record);

    int updateByPrimaryKey(UserTaoBaoAddress record);

	int deleteByFKId(String tbId);
}