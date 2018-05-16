/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月10日 <br>
 * 描述：自定义注解 权限控制注解
 */
@Retention(RetentionPolicy.RUNTIME) // 注解的保留策备
@Target({ ElementType.METHOD }) // 注解只能标注在方法上
public @interface Permission {
	/** 权限值 */
	String value();     
}
