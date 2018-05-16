/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.dao.mongo;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.hoomsun.risk.model.BlackList;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月10日 <br>
 * 描述：黑名单的数据接口
 */
public interface BlackDao extends MongoRepository<BlackList, ObjectId> {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月10日 <br>
	 * 描述：
	 * 
	 * @param applyId
	 * @return
	 */
	@Query(value = "{'applyId':?0 }", fields = "{ 'mobile' : 1}")
	List<BlackList> findTels(String applyId);
	
	@Query(value = "{'mobile':{'$in':?1}}", fields = "{ 'mobile' : 1}")
	List<BlackList> findMobile(String[] phones);
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月10日 <br>
	 * 描述：
	 * 
	 * @param applyId
	 * @return
	 */
	@Query(value = "{'mobile':?0 }", fields = "{ 'mobile' : 1}")
	List<BlackList> findTels(List<String> phones);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月12日 <br>
	 * 描述： 查询公司名称
	 * 
	 * @param comName
	 * @return
	 */
	@Query(value = "{'comName':?0 }", fields = "{ 'comName' : 1}")
	BlackList findComName(String comName);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月12日 <br>
	 * 描述： 公司别名
	 * @param comAlias
	 * @return
	 */
	@Query(value = "{'comAlias':?0 }", fields = "{ 'comAlias' : 1}")
	BlackList findComAlias(String comAlias);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月12日 <br>
	 * 描述： 公司唯一标识  组织机构代码  社会统一代码
	 * @param comCode
	 * @return
	 */
	@Query(value = "{'comCode':?0 }", fields = "{ 'comCode' : 1}")
	BlackList findComCode(String comCode);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月12日 <br>
	 * 描述： 公司地址
	 * @param comAddr
	 * @return
	 */
	@Query(value = "{'comAddr':?0 }", fields = "{ 'comAddr' : 1}")
	BlackList findComAddr(String comAddr);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月12日 <br>
	 * 描述： 公司电话
	 * @param comTel
	 * @return
	 */
	@Query(value = "{'comTel':?0 }", fields = "{ 'comTel' : 1}")
	BlackList findComTel(String comTel);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月12日 <br>
	 * 描述： 居住地址
	 * @param housAddr
	 * @return
	 */
	@Query(value = "{'housAddr':?0 }", fields = "{ 'housAddr' : 1}")
	BlackList findHousAddr(String housAddr);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月12日 <br>
	 * 描述： 住宅地址
	 * @param housTel
	 * @return
	 */
	@Query(value = "{'housTel':?0 }", fields = "{ 'housTel' : 1}")
	BlackList findHousTel(String housTel);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月16日 <br>
	 * 描述： 根据正则表达式查询
	 * @param reg
	 * @return
	 */
	@Query(value = "{'housTel':{'$regex':?0 }}", fields = "{ 'mobile' : 1}")
	List<BlackList> findByMobileReg(String reg);
}
