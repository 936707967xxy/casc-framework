package com.hoomsun.after.api.util.excel.secode.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel注解定义
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

	/**
	 * 导出字段名（默认调用当前字段的“get”方法，如指定导出字段为对象，请填写“对象名.对象属性”，例：“area.name”、“office.name”）
	 */
	String value() default "";
	
	/**
	 * 导出字段标题（需要添加批注请用“**”分隔，标题**批注，仅对导出模板有效）
	 */
	String title();
	
	/**
	 * 字段类型（0：导出导入；1：仅导出；2：仅导入）
	 */
	int type() default 0;

	/**
	 * 导出字段对齐方式（0：自动；1：靠左；2：居中；3：靠右）
	 */
	int align() default 0;
	
	/**
	 * 导出字段字段排序（升序）
	 */
	int sort() default 0;
	
	/**
	 * 取小数点位数
	 * 对数字进行位数转换，formatScale不为空
	 * @return
	 */
	int formatScale() default 0;
	
	/**
	 * 数据舍入方式
	 * 0-ROUND_UP：舍入远离零的舍入模式;在丢弃非零部分之前始终增加数字（始终对非零舍弃部分前面的数字加1）
	 * 1-ROUND_DOWN：接近零的舍入模式;在丢弃某部分之前始终不增加数字（从不对舍弃部分前面的数字加1，即截短）
	 * 2-ROUND_CEILING：如果 BigDecimal 为正，则舍入行为与 ROUND_UP 相同;如果为负，则舍入行为与 ROUND_DOWN 相同。注意，此舍入模式始终不会减少计算值。
	 * 3-ROUND_FLOOR：接近负无穷大的舍入模式。如果 BigDecimal 为正，则舍入行为与 ROUND_DOWN 相同;如果为负，则舍入行为与 ROUND_UP 相同。注意，此舍入模式始终不会增加计算值。
	 * 4-ROUND_HALF_UP：向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为向上舍入的舍入模式。如果舍弃部分 >= 0.5，则舍入行为与 ROUND_UP 相同;否则舍入行为与 ROUND_DOWN 相同。注意，这是我们大多数人在小学时就学过的舍入模式(四舍五入)。
	 * 5-ROUND_HALF_DOWN：向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为上舍入的舍入模式。如果舍弃部分 > 0.5，则舍入行为与 ROUND_UP 相同;否则舍入行为与 ROUND_DOWN 相同(五舍六入)。
	 * 6-ROUND_HALF_EVEN：向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则向相邻的偶数舍入。如果舍弃部分左边的数字为奇数，则舍入行为与 ROUND_HALF_UP 相同;如果为偶数，则舍入行为与 ROUND_HALF_DOWN 相同。注意，在重复进行一系列计算时，此舍入模式可以将累加错误减到最小。此舍入模式也称为“银行家舍入法”，主要在美国使用。四舍六入，五分两种情况。如果前一位为奇数，则入位，否则舍去。以下例子为保留小数点1位，那么这种舍入方式下的结果。
	 * 7-ROUND_UNNECESSARY：断言请求的操作具有精确的结果，因此不需要舍入。如果对获得精确结果的操作指定此舍入模式，则抛出ArithmeticException。
	 */
	int rounding() default 4;
	
	/**
	 * 反射类型
	 */
	Class<?> fieldType() default Class.class;
	
	/**
	 * 字段归属组（根据分组导出导入）
	 */
	int[] groups() default {};
}
