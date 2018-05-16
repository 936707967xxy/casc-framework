/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import com.hoomsun.common.model.Json;
import com.hoomsun.csas.sales.api.model.UserChsi;
import com.hoomsun.csas.sales.api.model.UserHouseFund;
import com.hoomsun.csas.sales.api.model.UserSocialsecurity;
import com.hoomsun.csas.sales.api.model.UserTaobao;
import com.hoomsun.csas.sales.api.model.vo.OtherVo;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月7日 <br>
 * 描述：
 */
public interface OtherServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月7日 <br>
	 * 描述： 获取其他的数据认证信息
	 * 
	 * @param applyId
	 * @return
	 */
	OtherVo findByApplyId(String applyId);
	
	Json saveOrUpdate(OtherVo otherVo);
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月23日 <br>
	 * 描述：保存或修改学历信息 
	 * @param chsi
	 * @return
	 */
	Json saveOrUpadteChsi(UserChsi chsi);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月23日 <br>
	 * 描述： 保存或修改公积金数据
	 * @param houseFund
	 * @return
	 */
	Json saveOrUpadteHouseFund(UserHouseFund houseFund);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月23日 <br>
	 * 描述： 保存或修改社保数据
	 * @param socialsecurity
	 * @return
	 */
	Json saveOrUpadteSocialsecurity(UserSocialsecurity socialsecurity);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月23日 <br>
	 * 描述： 添加或修改淘宝数据
	 * @param taobao
	 * @return
	 */
	Json saveOrUpdateTaobao(UserTaobao taobao);
	
}
