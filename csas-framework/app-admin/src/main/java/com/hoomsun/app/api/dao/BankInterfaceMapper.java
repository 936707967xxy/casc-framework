package com.hoomsun.app.api.dao;

import java.util.List;
import java.util.Map;

import com.hoomsun.app.api.model.BankInterface;

public interface BankInterfaceMapper {
	int deleteByPrimaryKey(String bankinterId);

	int insert(BankInterface record);

	int insertSelective(BankInterface record);

	BankInterface selectByPrimaryKey(String bankinterId);

	int updateByPrimaryKeySelective(BankInterface record);

	int updateByPrimaryKey(BankInterface record);

	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月26日<br>
	 * 描述：满足筛选条件的数据 分页
	 * 
	 * @param param
	 *            keys:pageIndex,pageSize,comName
	 * @return
	 */
	List<BankInterface> findPageData(Map<String, Object> param);

	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月26日<br>
	 * 描述：满足筛选条件的数据量
	 * 
	 * @param param
	 * @return keys:pageIndex,pageSize,comName
	 */
	int findPageCount(Map<String, Object> param);
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月26日<br>
	 * 描述：查询出所有的数据
	 * 
	 * @return
	 */
	List<BankInterface> findAllData();
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月27日<br>
	 * 描述：查询出相应银行名称的数据
	 * 
	 * @return
	 */
	BankInterface findByBankName(String bankName);

	/**
	 * 作者： liushuai <br>
	 * 创建时间：2017年9月26日<br>
	 * 描述：查询app需要展现数据
	 * 
	 * @return
	 */
	List<BankInterface> findAppAllData();
	
	/**
	 * 作者： liushuai <br>
	 * 创建时间：2017年9月26日<br>
	 * 描述：查询app bankinterId 为open的银行接口信息
	 * 
	 * @return
	 */
	BankInterface findAppDataDetail(String bankinterId);
	
	
	List<BankInterface> findAppCreAllData();
	
	List<BankInterface> findAppDepAllData();
}