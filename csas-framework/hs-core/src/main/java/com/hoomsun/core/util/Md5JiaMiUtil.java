/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月8日 <br>
 * 描述：MD5方式加密字符 不可逆
 */
public class Md5JiaMiUtil {
	public static String md5JiaMi(String str){
		String prefix = "@#$%！@#￥^&*(";
		String subfix = "(*@#￥%*%（$&%";
		
		if (StringUtils.isBlank(str)) {
			str = "Hs@12#¥";
		}
		
		for (int i = 0; i < 50; i++) {
			str = DigestUtils.md5Hex(prefix + str + subfix);
		}
		return str;
	}
	
	public static void main(String[] args) {
		System.out.println(md5JiaMi("90000018Hs@"));
	}
}
