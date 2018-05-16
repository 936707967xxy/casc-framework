/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.dao.mongo;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.hoomsun.risk.model.DebitCard;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月24日 <br>
 * 描述：储蓄卡认证的数据操作
 */
public interface DebitCardDao extends MongoRepository<DebitCard, ObjectId> {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月24日 <br>
	 * 描述： 根据申请ID获取数据
	 * 
	 * @param applyId
	 * @return
	 */
	@Query(value = "{applyId:?0}")
	public List<DebitCard> findByApplyId(String applyId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月24日 <br>
	 * 描述： 获取没有认领的数据
	 * 
	 * @param idNumber
	 * @return
	 */
	@Query(value = "{idNumbe':?0,applyId:0}", fields = "{records:0}")
	public List<DebitCard> findByIdNumberField(String idNumber);
}
