/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.dao.mongo;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.hoomsun.risk.model.UserRelatives;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月6日 <br>
 * 描述：
 */
public interface UserRelativesDao extends MongoRepository<UserRelatives, ObjectId> {
	@Query("{'applyId':?0}")
	List<UserRelatives> findByApplyId(String applyId);
	
	@Query("{'applyId':{'$ne':?1},linkPhone:{'$in':?0}}")
	List<UserRelatives> findByPhones(String[] cp, String nowApplyId);
}
