/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import java.util.Map;

import com.hoomsun.common.model.Json;
import com.hoomsun.csas.sales.api.model.UserSocialsecurity;

/**
 * 作者：liusong <br>
 * 创建时间：2017年9月14日 <br>
 * 描述：社保
 */
public interface UserSocialsecurityServerI {
	
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：添加社保信息
	 * @param userSocialsecurity 社保表添加数据信息
	 * @return
	 */
	Json createUserSocialsecurity(UserSocialsecurity userSocialsecurity);
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：修改社保
	 * @param userSocialsecurity 社保表修改数据信息
	 * @return
	 */
	Json updateUserSocialsecurity(UserSocialsecurity userSocialsecurity);
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：删除社保 ID
	 * @param siId
	 * @return
	 */
	Json deleteUserSocialsecurity(String siId);
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：根据主键查询社保信息
	 * @param siId 社保ID
	 * @return
	 */
	UserSocialsecurity findById(String siId);
	UserSocialsecurity findByApplyId(String applyId);
	 int insertMap(Map<String, Object>  map);

}
