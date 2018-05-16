/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.dao.mongo;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.hoomsun.risk.model.UserApply;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月8日 <br>
 * 描述：申请信息的操作接口
 */
// @Repository("userApplyDao")
public interface UserApplyDao extends MongoRepository<UserApply, ObjectId> {
	@Query(value = "{'idNumber':?0 }")
	public UserApply findByIdNumber(String idNumber);

	@Query(value = "{'mobile':{'$in':?0}}")
	public List<UserApply> findByMobiles(String[] cp);

	@Query(value = "{'mobile':{'$in':?0},'_id':{'$ne':?1}}")
	public List<UserApply> findByMobilesExtApp(String[] phones, String applyId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月7日 <br>
	 * 描述： 查询一组申请ID是的数据
	 * 
	 * @param ids
	 * @return
	 */
	@Query(value = "{'_id':{'$in':?0}}")
	public List<UserApply> findByApplyIds(String[] ids);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月9日 <br>
	 * 描述： 更具申请ID获取申请数据
	 * 
	 * @param applyId
	 * @return
	 */
	@Query(value = "{'_id':?0}")
	public UserApply findByApplyId(String applyId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月13日 <br>
	 * 描述： 获取客户的申请历史
	 * 
	 * @param idNumber
	 *            证件号
	 * @param applyId
	 *            本次的申请ID
	 * @return
	 */
	@Query(value = "{idNumber:?0,_id:{$nin:[?1,-1]}}")
	public List<UserApply> findApplyHistory(String idNumber, String applyId);
}
