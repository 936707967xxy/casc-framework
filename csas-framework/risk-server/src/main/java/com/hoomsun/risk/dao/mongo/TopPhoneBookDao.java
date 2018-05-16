/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.dao.mongo;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.hoomsun.risk.model.TopPhoneBook;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月6日 <br>
 * 描述：
 */
public interface TopPhoneBookDao extends MongoRepository<TopPhoneBook, ObjectId> {
	@Query(value = "{'applyId':{'$ne':?0},'records._id':?1}")
	List<TopPhoneBook> findByPhoneNumber(String applyId, String phoneNumber);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月9日 <br>
	 * 描述：
	 * 
	 * @deprecated
	 * @param idNumber
	 * @return
	 */
	@Query(value = "{'_id':?0}")
	TopPhoneBook findByIdNumber(String idNumber);

	@Query(value = "{applyId:?0}")
	TopPhoneBook findByApplyId(String applyId);

	@Query(value = "{'applyId':{'$ne':?0},'records':{'$all':?1}}")
	List<TopPhoneBook> findByPhoneNumbersExtApp(String nowApplyId, String[] phones);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月9日 <br>
	 * 描述： 未被认领的数据
	 * 
	 * @param idNumber
	 * @param pbId
	 * @return
	 */
	@Query(value = "{'_id':?0,'pbId':?1,applyId:0}")
	TopPhoneBook findByIdNumberAndPbId(String idNumber, String pbId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月24日 <br>
	 * 描述： 获取没有认领的数据
	 * 
	 * @param idNumber
	 * @return
	 */
	@Query(value = "{idNumbe':?0,applyId:0}", fields = "{records:0}")
	TopPhoneBook findByIdNumberField(String idNumber);
}
