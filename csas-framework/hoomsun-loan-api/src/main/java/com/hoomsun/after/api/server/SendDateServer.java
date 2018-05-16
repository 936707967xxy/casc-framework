/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.server;

import java.util.Map;

import com.hoomsun.after.api.model.param.DataAll;

/**
 * 作者：金世强 <br>
 * 创建时间：2018年3月21日 <br>
 * 描述：数据迁移相关处理
 */
public interface SendDateServer {

	Map<String, Object> saveDate(DataAll dataAll);
}
