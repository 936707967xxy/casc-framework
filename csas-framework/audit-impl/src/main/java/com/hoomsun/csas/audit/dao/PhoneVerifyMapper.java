package com.hoomsun.csas.audit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.csas.audit.model.PhoneVerify;

public interface PhoneVerifyMapper {
    int deleteByPrimaryKey(String pvId);

    int insert(PhoneVerify record);

    int insertSelective(PhoneVerify record);

    PhoneVerify selectByPrimaryKey(String pvId);

    int updateByPrimaryKeySelective(PhoneVerify record);

    int updateByPrimaryKey(PhoneVerify record);
    /**
     * 作者：ywzou <br>
     * 创建时间：2017年12月9日 <br>
     * 描述：更具申请ID验证数据 
     * @param applyId
     * @return
     */
	Integer checkByApplyId(String applyId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 根据主键验证数据是否已经存在
	 * @param pvId
	 * @return
	 */
	Integer checkById(String pvId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月10日 <br>
	 * 描述： 根据申请ID获取数据
	 * @param applyId
	 * @return
	 */
	List<PhoneVerify> findByApplyId(String applyId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月10日 <br>
	 * 描述： 根据联系人ID获取电核数据
	 * @param consId
	 * @return
	 */
	PhoneVerify findByConsId(String consId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述： 验证审核数据是否存在
	 * @param applyId
	 * @param phone
	 * @return
	 */
	Integer checkByApplyTel(@Param("applyId")String applyId, @Param("telNumber")String phone);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月8日 <br>
	 * 描述： 获取电话审核数据
	 * @param applyId
	 * @param phone
	 * @return
	 */
	PhoneVerify queryByApplyTel(@Param("applyId")String applyId, @Param("telNumber")String phone);
}