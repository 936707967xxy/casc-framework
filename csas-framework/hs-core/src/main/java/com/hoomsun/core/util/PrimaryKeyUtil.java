/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.util;

import java.util.UUID;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月8日 <br>
 * 描述：主键生成车略 uuid的方式
 */
public class PrimaryKeyUtil {
	/**
	 * 生成一个UUID字符串
	 * @return 字符串
	 */
	public static String getPrimaryKey(){
		String key = UUID.randomUUID().toString();
		return key.replaceAll("-", "");
	}
}
