/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.dao.mongo;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.hoomsun.risk.model.UserSpouse;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月6日 <br>
 * 描述：
 */
public interface UserSpouseDao extends MongoRepository<UserSpouse, ObjectId> {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月7日 <br>
	 * 描述： 更具申请ID获取数据
	 * @param applyId
	 * @return
	 */
	@Query("{'applyId':?0}")
	List<UserSpouse> findByApplyId(String applyId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月7日 <br>
	 * 描述： 根据电话号码查询数据 但是不包括某个单子的数据
	 * @param cp
	 * @param nowApplyId
	 * @return
	 */
	@Query("{'applyId':{'$ne':?1},linkPhone:{'$in':?0}}")
	List<UserSpouse> findByPhones(String[] cp, String nowApplyId);
}
