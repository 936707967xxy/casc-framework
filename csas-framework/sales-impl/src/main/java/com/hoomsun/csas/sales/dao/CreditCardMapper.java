package com.hoomsun.csas.sales.dao;

import java.util.List;

import com.hoomsun.csas.sales.api.model.CreditCard;

public interface CreditCardMapper {
    int deleteByPrimaryKey(String ccId);

    int insert(CreditCard record);

    int insertSelective(CreditCard record);

    CreditCard selectByPrimaryKey(String ccId);

    int updateByPrimaryKeySelective(CreditCard record);

    int updateByPrimaryKey(CreditCard record);
    
    List<CreditCard> selectByApplyId(String applyId);//根据申请id查询信用卡账单
    /**
     * 作者：ywzou <br>
     * 创建时间：2017年12月7日 <br>
     * 描述： 获取信用卡账单的详细数据
     * @param applyId
     * @return
     */
	List<CreditCard> findByApplyId(String applyId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 验证信用卡账单的数据是否存在
	 * @param applyId
	 * @return
	 */
	Integer checkByApplyId(String applyId);
	
	Integer checkById(String cardbillsId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述：根据申请ID删除啊信用卡账单信息 
	 * @param applyId
	 * @return
	 */
	Integer deleteByApplyId(String applyId);
}