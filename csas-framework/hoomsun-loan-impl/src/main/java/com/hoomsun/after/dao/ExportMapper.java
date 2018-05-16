package com.hoomsun.after.dao;

import java.util.List;

import com.hoomsun.after.api.model.table.Export;

public interface ExportMapper {
	int deleteByPrimaryKey(String id);

	int insert(Export record);

	int insertSelective(Export record);

	Export selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Export record);

	int updateByPrimaryKey(Export record);

	List<Export> selectByCustId(String castId);

	List<Export> selectByLoanId(String loanId);
}