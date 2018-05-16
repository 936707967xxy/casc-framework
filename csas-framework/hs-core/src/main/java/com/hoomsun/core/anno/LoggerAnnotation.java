/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.anno;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hoomsun.core.enums.OptionType;


/**
 * 作者：liusong <br>
 * 创建时间：2017年10月10日 <br>
 * 描述：用户操作日志自定义注解拦截
 */
@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.METHOD}) 
@Documented  
public @interface LoggerAnnotation {  
    //模块名  
    String moduleName() default "";  
    //操作内容  
    String option() default "";  
    OptionType optionType() default OptionType.RETRIEVE;  
    //操作实体对应数据表
    String objectTable() default "";
    //主键名称
    String idName() default "";
    int idIndex() default 0;
    //查询类名
    String className() default "";
    //spring中的beanID
    String beanId() default "";
    //查询方法名
    String selectMethod() default "";
}
