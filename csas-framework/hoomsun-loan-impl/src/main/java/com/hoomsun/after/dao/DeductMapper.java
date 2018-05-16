package com.hoomsun.after.dao;

import java.util.List;

import com.hoomsun.after.api.model.table.Deduct;

public interface DeductMapper {
	int deleteByPrimaryKey(String id);

	int insert(Deduct record);

	int insertSelective(Deduct record);

	Deduct selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Deduct record);

	int updateByPrimaryKey(Deduct record);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年5月9日 <br>
	 * 描述：根据客户ID 划扣记录 并按照划扣时间排序
	 * 
	 * @param id
	 * @return
	 */
	List<Deduct> selectByCustId(String custId);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年5月9日 <br>
	 * 描述：根据LoanId查询划扣记录
	 * 
	 * @param loanId
	 * @return
	 */
	List<Deduct> selectByLoanId(String loanId);
}