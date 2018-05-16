package com.hoomsun.csas.sales.dao;

import com.hoomsun.csas.sales.api.model.CreditCardBills;

public interface CreditCardBillsMapper {
    int deleteByPrimaryKey(String cardbillsId);

    int insert(CreditCardBills record);

    int insertSelective(CreditCardBills record);

    CreditCardBills selectByPrimaryKey(String cardbillsId);

    int updateByPrimaryKeySelective(CreditCardBills record);

    int updateByPrimaryKeyWithBLOBs(CreditCardBills record);

    int updateByPrimaryKey(CreditCardBills record);
}