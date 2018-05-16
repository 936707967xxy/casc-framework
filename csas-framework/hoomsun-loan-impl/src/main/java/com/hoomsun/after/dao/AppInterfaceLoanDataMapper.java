package com.hoomsun.after.dao;

import java.util.List;

import com.hoomsun.after.api.model.vo.AppInterfaceLoanData;

public interface AppInterfaceLoanDataMapper {
    int insert(AppInterfaceLoanData record);

    int insertSelective(AppInterfaceLoanData record);
    
    List<AppInterfaceLoanData> selectByLoanId(String loanId);
}