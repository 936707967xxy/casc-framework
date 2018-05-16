package com.hoomsun.csas.sales.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.csas.sales.api.model.Intention;

/**
 * 
 * 作者：liming<br>
 * 创建时间：2017年11月16日 <br>
 * 描述： 客户预约接口
 *
 */

public interface IntentionMapper {
    int deleteByPrimaryKey(String ordplyId);

    int insert(Intention record);

    int insertSelective(Intention record);

    Intention selectByPrimaryKey(String ordplyId);

    int updateByPrimaryKeySelective(Intention record);

    int updateByPrimaryKey(Intention record);

	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月15日 <br>
	 * 描述： 预约表的分页查询
	 * @param param
	 * @return
	 */
	List<Intention> findPageData(Map<String, Object> param);
	
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
	 * 创建时间：2017年11月20日 <br>
	 * 描述： 根据主键查询预约信息
	 * @param ordplyId
	 * @return
	 */
	Intention findIntentionById(String ordplyId);
	
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月20日 <br>
	 * 描述： 根据主键查询预约信息
	 * @param ordplyId
	 * @return  json数据
	 */
	JSONObject findById(String ordplyId);
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月5日 <br>
	 * 描述： 提供申请表没有的预约客户
	 * @param ordplyId
	 * @return
	 */
	Intention findByOrdplyId(String ordplyId);
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月20日 <br>
	 * 描述： 检查手机号是否存在
	 * @param phone
	 * @return
	 */
	int checkPhone(String phone);
	

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月20日 <br>
	 * 描述： 检查身份证是否存在
	 * @param idnumber
	 * @return
	 */
	int checkIdNumber(String idnumber);
	
	List<Intention> findPagerByStore(Map<String, Object> param);
	
	Integer findPagerCountByStore(Map<String, Object> param);
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年1月2日 <br>
	 * 描述： 查询预约表的申请日期
	 * @param map
	 * @return
	 */
	String selectApplyDate(String mobile);
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年1月2日 <br>
	 * 描述： 根据部门deptId查询团队经理
	 * @param parentId
	 * @param hsoaDB
	 * @return
	 */
	String selectCompanyPart(@Param("deptId") String deptId, @Param("hsoaDB") String hsoaDB);

}