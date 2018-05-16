/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.risk.model.UserApply;
import com.hoomsun.risk.model.UserContact;
import com.hoomsun.risk.model.vo.UserApplyVo;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月6日 <br>
 * 描述：用户申请数据接口
 */
public interface UserApplyMapperRisk {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月6日 <br>
	 * 描述： 根据申请ID获取基本的申请数据
	 * 
	 * @param applyId
	 * @return
	 */
	UserApply findUserApplyById(String applyId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月6日 <br>
	 * 描述： 获取申请的数据 包含通话记录
	 * 
	 * @param applyId
	 * @return
	 */
	UserApply findUserApplyCont(String applyId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月6日 <br>
	 * 描述： 获取联系人信息
	 * 
	 * @param applyId
	 * @return
	 */
	List<UserContact> findContactByApplyId(String applyId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月6日 <br>
	 * 描述： 获取某时间段内的申请数据
	 * 
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return
	 */
	List<UserApply> findUserApply(@Param("start") Date start, @Param("end") Date end);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月6日 <br>
	 * 描述： 获取某时段内的联系人数据
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	List<UserContact> findUserContact(@Param("start") Date start, @Param("end") Date end);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月30日 <br>
	 * 描述： 根据证件号获取申请数据
	 * 
	 * @param idNumber
	 * @return
	 */
	UserApplyVo findByIdNumber(String idNumber);
}
