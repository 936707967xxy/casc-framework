/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.dao.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.hoomsun.risk.model.NearNMonthCall;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月1日 <br>
 * 描述：近N月通话电话
 */
public interface NearNMonthCallDao extends MongoRepository<NearNMonthCall, ObjectId> {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月9日 <br>
	 * 描述： 查询某人的近N月的通话电话号码
	 * 
	 * @deprecated
	 * @param idNumber
	 * @return
	 */
	@Query(value = "{'_id':?0}")
	NearNMonthCall findById(String idNumber);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月24日 <br>
	 * 描述： 查询某人的近N月的通话电话号码
	 * 
	 * @param applyId
	 *            申请编号
	 * @return
	 */
	@Query(value = "{applyId:?0}")
	NearNMonthCall findByApplyId(String applyId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月24日 <br>
	 * 描述： 获取没有认领的数据
	 * 
	 * @param idNumber
	 * @return
	 */
	@Query(value = "{idNumbe':?0,applyId:0}", fields = "{records:0}")
	NearNMonthCall findByIdNumberField(String idNumber);
}
