package com.hoomsun.csas.sales.dao;

import com.hoomsun.csas.sales.api.model.CreditCardBillsInfo;

public interface CreditCardBillsInfoMapper {
    int deleteByPrimaryKey(String hccbiId);

    int insert(CreditCardBillsInfo record);

    int insertSelective(CreditCardBillsInfo record);

    CreditCardBillsInfo selectByPrimaryKey(String hccbiId);

    int updateByPrimaryKeySelective(CreditCardBillsInfo record);

    int updateByPrimaryKey(CreditCardBillsInfo record);
}