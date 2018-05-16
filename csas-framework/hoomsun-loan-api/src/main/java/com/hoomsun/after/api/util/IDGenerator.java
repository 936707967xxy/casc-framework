/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.util;

import java.util.Date;

/**
 * 作者：zwLiu <br>
 * 创建时间：2018年4月17日 <br>
 * 描述：发号器，获取固定格式 sign+16 位ID
 */
public class IDGenerator {
	public static String getNextID(String sign) {

		int i =  (int) (100 + Math.random()*(900-1+1));
		String id = sign + String.valueOf(new Date().getTime()) + i;
		return id;
	}
}
