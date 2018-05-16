/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.inter;

import com.hoomsun.core.model.HxbRate;

/**
 * 作者：liusong <br>
 * 创建时间：2018年1月10日 <br>
 * 描述：
 */
public interface HxbRateServerI {
	
	HxbRate selectByApplyId(String applyId);

}
