/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import java.util.Map;

import com.hoomsun.common.model.Json;
import com.hoomsun.csas.sales.api.model.UserCis;

/**
 * 作者：liusong <br>
 * 创建时间：2017年9月14日 <br>
 * 描述：上海资信表
 */
public interface UserCisServerI {
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：添加上海资信
	 * @param userCis 上海资信表添加数据信息
	 * @return
	 */
	Json createUserCis(UserCis userCis);
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：修改上海资信
	 * @param userCis 上海资信表修改数据信息
	 * @return
	 */
	Json updateUserCis(UserCis userCis);
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：删除上海资信 ID
	 * @param cisId
	 * @return
	 */
	Json deleteUserCis(String cisId);
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：根据主键查询上海资信信息
	 * @param cisId 上海资信ID
	 * @return
	 */
	UserCis findById(String cisId);
	/**
	 * 作者：liusong <br>
	 * 创建时间：2017年9月15日 <br>
	 * 描述： 根据申请id查询
	 * @param applyId 申请id
	 * @return
	 */
	UserCis findByApplyId(String applyId);

	
	 int insertMap(Map<String, Object>  map);
}
