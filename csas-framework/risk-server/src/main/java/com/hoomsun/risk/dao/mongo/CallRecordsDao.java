/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.dao.mongo;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.hoomsun.risk.model.CallRecords;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月15日 <br>
 * 描述：通话记录
 */
public interface CallRecordsDao extends MongoRepository<CallRecords, ObjectId> {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月15日 <br>
	 * 描述： 获取近N月通话记录 未认领的数据
	 * 
	 * @param idNumber
	 * @return
	 */
	@Query(value = "{'idNumber':?0 ,applyId:0,'createDate':{'$gte':?1,'$lte':?2}}")
	List<CallRecords> findByDateRange(String idNumber, Date start, Date end);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月9日 <br>
	 * 描述： 根据applyId查询数据 不返回records
	 * 
	 * @param applyId
	 * @return
	 */
	@Query(value = "{applyId:?0}", fields = "{records:0}")
	CallRecords findByApplyIdField(String applyId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月5日 <br>
	 * 描述： 查詢某人的通话记录 未认领的数据 不返回records
	 * 
	 * @param idNumber
	 * @return
	 */
	@Query(value = "{idNumbe':?0,applyId:0}", fields = "{records:0}")
	CallRecords findByIdNumberField(String idNumber);
}
