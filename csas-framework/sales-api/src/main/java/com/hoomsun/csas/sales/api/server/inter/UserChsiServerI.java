/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import java.util.List;
import java.util.Map;

import com.hoomsun.common.model.Json;
import com.hoomsun.csas.sales.api.model.UserChsi;

/**
 * 作者：liusong <br>
 * 创建时间：2017年9月14日 <br>
 * 描述：
 */
public interface UserChsiServerI {
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：添加学信网信息
	 * @param userChsi 学信网信息添加数据信息
	 * @return
	 */
	Json createUserChsi(UserChsi userChsi);
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：修改学信网信息
	 * @param company 学信网信息修改数据信息
	 * @return
	 */
	Json updateUserChsi(UserChsi userChsi);
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：删除学信网信息 ID
	 * @param chsiId
	 * @return
	 */
	Json deleteUserChsi(String chsiId);
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：根据主键查询学信网信息 
	 * @param chsiId 学信网ID
	 * @return
	 */
	UserChsi findById(String chsiId);
	/**
	 * 作者：liusong <br>
	 * 创建时间：2017年9月15日 <br>
	 * 描述： 根据申请id查询
	 * @param applyId 申请id
	 * @return
	 */
	UserChsi findByApplyId(String applyId);
	
	 int insertMap(Map<String, Object>  map);
	 
	 List<Map<String,Object> >   selectActrivity(Map<String,Object >  map);

}
