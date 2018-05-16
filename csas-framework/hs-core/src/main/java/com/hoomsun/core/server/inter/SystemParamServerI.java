/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.inter;

import com.hoomsun.core.model.SystemParam;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月18日 <br>
 * 描述：系统参数
 */
public interface SystemParamServerI {
	SystemParam findParamByKey(String key);
}
