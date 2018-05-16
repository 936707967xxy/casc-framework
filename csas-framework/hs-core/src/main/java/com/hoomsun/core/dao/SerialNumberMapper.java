package com.hoomsun.core.dao;

import java.util.List;
import java.util.Map;

import com.hoomsun.core.model.SerialNumber;




public interface SerialNumberMapper {
    int deleteByPrimaryKey(String id);

    int insert(SerialNumber record);

    int insertSelective(SerialNumber record);

    SerialNumber selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SerialNumber record);

    int updateByPrimaryKey(SerialNumber record);
    
    
    
    /**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月15日 <br>
	 * 描述： 编号的分页查询
	 * @param param
	 * @return
	 */
	List<SerialNumber> findPageData(Map<String, Object> param);
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月15日 <br>
	 * 描述： 根据条件查询
	 * @param param
	 * @return
	 */
	Integer findPageCount(Map<String, Object> param);
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月30日 <br>
	 * 描述： 根据类型查询
	 * @param type(1：咨询编号 2：申请编号 3：合同编号)
	 * @return
	 */
	SerialNumber findByType(String type);
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月30日 <br>
	 * 描述： 根据id查询
	 * @param id
	 * @return
	 */
	SerialNumber findById(String id);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月29日 <br>
	 * 描述： 生成流水号
	 * @param param
	 */
	void getSerialNumber(Map<String, String> param);
}