package com.hoomsun.csas.sales.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.csas.sales.api.model.UserContacts;

public interface UserContactsMapper {
    int deleteByPrimaryKey(String phoneid);

    int insert(UserContacts record);

    int insertSelective(UserContacts record);

    UserContacts selectByPrimaryKey(String phoneid);

    int updateByPrimaryKeySelective(UserContacts record);

    int updateByPrimaryKey(UserContacts record);
    
    
    /**
     * 根据appId删除
     */
    int deleteByApplyId(String applyId);
    
    /**
     * 根据appId修改
     */
    int updateByApplyId(UserContacts contacts);
   
	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月21日 <br>
	 * 描述：查询客户id，申请表数据
	 */
	List<UserContacts> selectByApplyId(@Param("applyId") String applyId,@Param("isFillIn")Integer isFillIn);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月7日 <br>
	 * 描述： 查询出通讯通讯录的信息  包括详单
	 * @param applyId
	 * @return
	 */
	List<UserContacts> findByApplyId(@Param("applyId")String applyId,@Param("isFillIn")Integer isFillIn);
	
	
	/**
	 * 作者：pwang <br>
	 * 创建时间：2018年01月5日 <br>
	 * 描述： 获取催收任务列表（客服ID，客服NAME,必须输入）
	 * @param applyId
	 * @return
	 */
	List<UserContacts> getContactsList(String loanId);

}