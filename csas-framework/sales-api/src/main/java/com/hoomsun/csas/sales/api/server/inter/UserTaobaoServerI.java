/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import java.util.Map;

import com.hoomsun.common.model.Json;
import com.hoomsun.csas.sales.api.model.UserTaobao;

/**
 * 作者：liusong <br>
 * 创建时间：2017年9月14日 <br>
 * 描述：淘宝
 */
public interface UserTaobaoServerI {
	
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：添加淘宝
	 * @param finalAudit 淘宝表添加数据信息
	 * @return
	 */
	Json createUserTaobao(UserTaobao userTaobao);
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：修改淘宝
	 * @param company 淘宝表修改数据信息
	 * @return
	 */
	Json updateUserTaobao(UserTaobao userTaobao);
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：删除淘宝 ID
	 * @param tbId
	 * @return
	 */
	Json deleteUserTaobao(String tbId);
	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：根据主键查询淘宝信息
	 * @param tbId 淘宝ID
	 * @return
	 */
	UserTaobao findById(String tbId);
	/**
	 * 作者：liusong <br>
	 * 创建时间：2017年9月15日 <br>
	 * 描述： 根据申请id查询
	 * @param applyId申请id
	 * @return
	 */
	UserTaobao findByApplyId(String applyId);

	 int insertMap(Map<String, Object>  map);
}
