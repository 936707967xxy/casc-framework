/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.server;

import com.hoomsun.after.api.model.param.ToAfterParam;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年4月28日 <br>
 * 描述：放款推送贷后
 */
public interface ToAfterServer {
	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月28日 <br>
	 * 描述：放款后推送贷后数据
	 * 
	 * @param toAfterParam
	 * @return 是否推送成功
	 * @throws Exception
	 *             发生异常 推送失败
	 */
	boolean toAfter(ToAfterParam toAfterParam) throws Exception;
}
