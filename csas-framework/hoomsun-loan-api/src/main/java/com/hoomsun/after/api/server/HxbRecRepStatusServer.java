/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.server;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年5月14日 <br>
 * 描述：
 */
public interface HxbRecRepStatusServer {

	JSONObject saveRecRepStatus(HttpServletRequest req);

}
