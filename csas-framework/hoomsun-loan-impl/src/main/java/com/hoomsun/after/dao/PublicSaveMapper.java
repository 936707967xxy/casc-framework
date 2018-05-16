package com.hoomsun.after.dao;

import java.util.List;
import java.util.Map;

import com.hoomsun.after.api.model.table.PublicSave;

public interface PublicSaveMapper {
	int deleteByPrimaryKey(String id);

	int insert(PublicSave record);

	int insertSelective(PublicSave record);

	PublicSave selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(PublicSave record);

	int updateByPrimaryKey(PublicSave record);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月28日 <br>
	 * 描述： 根据提交信息，获取客户未认领的存公信息
	 * 
	 * @param params
	 * @return
	 */
	List<PublicSave> getDeductList(Map<String, Object> params);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月28日 <br>
	 * 描述：根据主键IDs 查询存公记录
	 * 
	 * @param ids
	 * @return
	 */
	List<PublicSave> selectByPrimaryKeys(List<String> ids);
}