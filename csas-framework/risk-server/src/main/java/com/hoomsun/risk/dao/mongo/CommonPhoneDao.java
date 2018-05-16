/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.dao.mongo;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.hoomsun.risk.model.CommonPhone;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月16日 <br>
 * 描述：同业电话库 
 */
public interface CommonPhoneDao extends MongoRepository<CommonPhone, ObjectId> {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月16日 <br>
	 * 描述： 申请人近2个月通话记录中存在2条以上银行、小贷、P2P等金融机构催债通话记录
	 * 
	 * 1、localField：在输入文档中的查找字段
	 * 
	 * 2、from：需要连接的集合
	 * 
	 * 3、foreignField：需要在from集合中查找的字段
	 * 
	 * 4、as：输出的字段名字
	 * 
	 * @return
	 */
//	@Query("aggregate([{'$lookup':{'localField':'phoneNumber','from':'CALL_RECORDS','foreignField':'commPhone','as':'inventory_docs'}},{'$project':{'inventory_docs':1}}])")
	@Query("{}")
	public List<CommonPhone> findComm();
}
