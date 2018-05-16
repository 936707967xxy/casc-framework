/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import java.util.Map;

import com.hoomsun.common.model.Json;
import com.hoomsun.csas.sales.api.model.UserPbccre;

/**
 * 作者：liusong <br>
 * 创建时间：2017年9月14日 <br>
 * 描述：征信
 */
public interface UserPbccreServerI {
	
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：添加征信
	 * @param userPbccre 征信表添加数据信息
	 * @return
	 */
	Json createUserPbccre(UserPbccre userPbccre);
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：修改征信
	 * @param userPbccre 征信表修改数据信息
	 * @return
	 */
	Json updateUserPbccre(UserPbccre userPbccre);
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：删除征信 ID
	 * @param crId
	 * @return
	 */
	Json deleteUserPbccre(String crId);
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：根据主键查询征信信息
	 * @param crId 征信ID
	 * @return
	 */
	UserPbccre findById(String crId);
	/**
	 * 作者：liusong <br>
	 * 创建时间：2017年9月15日 <br>
	 * 描述： 根据申请id查询
	 * @param applyId 申请id
	 * @return
	 */
	UserPbccre findByApplyId(String applyId);

	 int insertMap(Map<String, Object>  map);
}
