/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.dao.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.hoomsun.risk.model.PhoneBook;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月15日 <br>
 * 描述：通讯簿
 */
public interface PhoneBookDao extends MongoRepository<PhoneBook, ObjectId> {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月16日 <br>
	 * 描述： 获取某人的id
	 * 
	 * @deprecated
	 * @param idNumber
	 * @return
	 */
	@Query(value = "{'idNumber':?0}")
	PhoneBook findByIdNumber(String idNumber);

	@Query(value = "{applyId:?0}")
	PhoneBook findByApplyId(String applyId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月9日 <br>
	 * 描述： 未被认领的电话簿
	 * 
	 * @param idNumber
	 * @return
	 */
	@Query(value = "{'idNumber':?0,applyId:0}")
	PhoneBook findByIdNumberInit(String idNumber);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月24日 <br>
	 * 描述： 获取没有认领的数据
	 * 
	 * @param idNumber
	 * @return
	 */
	@Query(value = "{idNumbe':?0,applyId:0}", fields = "{records:0}")
	PhoneBook findByIdNumberField(String idNumber);
}
