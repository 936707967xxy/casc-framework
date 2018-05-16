package com.hoomsun.after.dao;

import java.util.List;

import com.hoomsun.after.api.model.table.ChangeInfo;
import com.hoomsun.after.api.model.vo.BankVo;

public interface ChangeInfoMapper {
	int deleteByPrimaryKey(String id);

	int insert(ChangeInfo record);

	int insertSelective(ChangeInfo record);

	ChangeInfo selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ChangeInfo record);

	int updateByPrimaryKey(ChangeInfo record);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 查询信息变更信息
	 * 
	 * @param applyId
	 * @return
	 */
	ChangeInfo getChangeInfo(String applyId);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 查询信息变更信息
	 * 
	 * @param applyId
	 * @return
	 */
	List<ChangeInfo> selectByCustId(String custId);

}