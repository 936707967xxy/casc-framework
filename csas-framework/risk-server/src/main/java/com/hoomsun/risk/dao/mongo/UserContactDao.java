/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.dao.mongo;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.hoomsun.risk.model.UserContact;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月16日 <br>
 * 描述：通话记录前二十 联系人数据层
 */
public interface UserContactDao extends MongoRepository<UserContact, ObjectId> {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月22日 <br>
	 * 描述： 排除某申请的通话记录
	 * 
	 * @param phone
	 * @param applyId
	 * @return
	 */
	@Query(value = "{'linkPhone':?0,'applyId':{'$ne':?1}}")
	public List<UserContact> findByPhone(String phone, String applyId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月13日 <br>
	 * 描述： 更具申请数据获取联系人数据
	 * 
	 * @param applyId
	 *            申请ID
	 * @return 联系人集合
	 */
	@Query(value = "{applyId:?0}")
	public List<UserContact> findByApplyId(String applyId);
}
