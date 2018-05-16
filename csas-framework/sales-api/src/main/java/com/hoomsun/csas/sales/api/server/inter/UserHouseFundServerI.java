/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import java.util.Map;

import com.hoomsun.common.model.Json;
import com.hoomsun.csas.sales.api.model.UserHouseFund;

/**
 * 作者：liusong <br>
 * 创建时间：2017年9月14日 <br>
 * 描述：
 */
public interface UserHouseFundServerI {
	
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：添加公积金
	 * @param userHouseFund 公积金添加数据信息
	 * @return
	 */
	Json createUserHouseFund(UserHouseFund userHouseFund);
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：修改公积金
	 * @param userHouseFund 公积金修改数据信息
	 * @return
	 */
	Json updateUserHouseFund(UserHouseFund userHouseFund);
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：删除公积金ID
	 * @param hfId
	 * @return
	 */
	Json deleteUserHouseFund(String hfId);
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：根据主键查询终审信息
	 * @param hfId 公积金ID
	 * @return
	 */
	UserHouseFund findById(String hfId);
	UserHouseFund findByApplyId(String applyId);

	int insertMap(Map<String, Object>  map);
}
