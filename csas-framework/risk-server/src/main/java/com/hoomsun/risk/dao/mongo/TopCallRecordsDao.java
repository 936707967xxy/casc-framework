/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.dao.mongo;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.hoomsun.risk.model.TopCallRecords;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月1日 <br>
 * 描述：通话记录 TOP N
 */
public interface TopCallRecordsDao extends MongoRepository<TopCallRecords, ObjectId> {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月2日 <br>
	 * 描述： 更具电话号码查询 不包含某人的
	 * 
	 * @param applyId
	 *            证件号
	 * @param phoneNumber
	 *            手机号
	 * @return
	 */
	@Query(value = "{'applyId':{'$ne':?0},'records._id':?1}")
	public List<TopCallRecords> findByPhoneNumber(String applyId, String phoneNumber);

	@Query(value = "{'applyId':{'$ne':?0},'records':{'$all':?1}}")
	public List<TopCallRecords> findByPhoneNumbersExtApp(String applyId, String[] phoneNumber);

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
	public TopCallRecords findByIdNumber(String idNumber);

	@Query(value = "{'applyId':?0}")
	public TopCallRecords findByApplyId(String applyId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月7日 <br>
	 * 描述： 更具电话号码查询
	 * 
	 * @param cp
	 * @param nowApplyId
	 * @return
	 */
	@Query(value = "{'applyId':{'$ne':?0},'records.callPhone':{'$all':?0}}")
	public List<TopCallRecords> findByPhones(String[] cp, String nowApplyId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月24日 <br>
	 * 描述： 获取没有认领的数据
	 * 
	 * @param idNumber
	 * @return
	 */
	@Query(value = "{idNumbe':?0,applyId:0}", fields = "{records:0}")
	public TopCallRecords findByIdNumberField(String idNumber);

}
