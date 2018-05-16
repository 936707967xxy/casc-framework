package com.hoomsun.core.dao;

import java.util.List;
import java.util.Map;

import com.hoomsun.core.model.Bankinfo;

public interface BankinfoMapper {
    int deleteByPrimaryKey(String pid);

    int insert(Bankinfo record);

    int insertSelective(Bankinfo record);

    Bankinfo selectByPrimaryKey(String pid);

    int updateByPrimaryKeySelective(Bankinfo record);

    int updateByPrimaryKey(Bankinfo record);
    
    List<Bankinfo> findAllData();
    
    /**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月26日<br>
	 * 描述：满足筛选条件的数据 分页
	 * 
	 * @param param
	 *            keys:pageIndex,pageSize,comName
	 * @return
	 */
	List<Bankinfo> findPageData(Map<String, Object> param);

	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月26日<br>
	 * 描述：满足筛选条件的数据量
	 * 
	 * @param param
	 * @return keys:pageIndex,pageSize,comName
	 */
	int findPageCount(Map<String, Object> param);
	
	Bankinfo selectByBankId(String bankId);
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年1月11日 <br>
	 * 描述： 根据银行名称查询银行简写
	 * @param bankname
	 * @return
	 */
	Bankinfo selectByBankName(String bankname); 
}