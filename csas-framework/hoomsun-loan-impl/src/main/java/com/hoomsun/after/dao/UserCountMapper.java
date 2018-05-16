package com.hoomsun.after.dao;

import java.util.Map;

import com.hoomsun.after.api.model.table.UserCount;

public interface UserCountMapper {
	int deleteByPrimaryKey(String id);

	int insert(UserCount record);

	int insertSelective(UserCount record);

	UserCount selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(UserCount record);

	int updateByPrimaryKey(UserCount record);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月9日 <br>
	 * 描述： 查询账户表
	 * 
	 * @param loanId
	 * @return
	 */
	UserCount selectByCustId(String custId);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月12日 <br>
	 * 描述：根据custId修改账户余额
	 * 
	 * @param userCountparams
	 */
	void updatebal(Map<String, Object> userCountparams);
}