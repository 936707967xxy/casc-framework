package com.hoomsun.core.dao;

import java.util.List;
import java.util.Map;




import com.hoomsun.core.model.BlackInfo;



public interface BlackInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(BlackInfo record);

    int insertSelective(BlackInfo record);

    BlackInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BlackInfo record);

    int updateByPrimaryKey(BlackInfo record);
    /**
     * 
     * 作者：liming<br>
     * 创建时间：2017年12月4日 <br>
     * 描述： 黑名单表的分页查询
     * @param param
     * @return
     */
	List<BlackInfo> findPageData(Map<String, Object> param);
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述：  查询黑名单表的条数
	 * @param param
	 * @return
	 */
	Integer findPageCount(Map<String, Object> param);
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 根据id查询
	 * @param id
	 * @return
	 */
	BlackInfo  findById(String id);
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月5日 <br>
	 * 描述： 根据预约信息的手机号查询是否为黑名单客户
	 * @param phone
	 * @return
	 */
	Integer findByPhone(String phone);
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月13日 <br>
	 * 描述： 根据身份证查询是否是黑名单
	 * @param idnumber
	 * @return
	 */
	Integer findByIdNumber(String idnumber);
	
	/**
	 * 
	 * 作者：刘栋梁<br>
	 * 创建时间：2017年1月2日 <br>
	 * 描述： 根据姓名查询是否是黑名单
	 * @param idnumber
	 * @return
	 */
	Integer findByCustname(String custname);
	
}