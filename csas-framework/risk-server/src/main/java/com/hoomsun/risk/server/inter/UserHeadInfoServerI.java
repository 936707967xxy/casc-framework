package com.hoomsun.risk.server.inter;


import com.hoomsun.common.model.Json;

public interface UserHeadInfoServerI {

	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2018年01月9日 <br>
	 * 描述:保存用户画像
	 * 
	 * @param applyId
	 *            开始时间
	 * @return
	 */
	Json saveUserHeadinfo(String applyId);
}
