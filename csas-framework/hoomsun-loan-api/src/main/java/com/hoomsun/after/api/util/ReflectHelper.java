package com.hoomsun.after.api.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReflectHelper {
	/*
	 *2018年3月13日上午9:20:37
	 *xinyuan.xu@hoomsun.com
	 */
	
	private static final Logger LogCvt = LoggerFactory.getLogger(ReflectHelper.class);
    
	private ReflectHelper() {}
	
	/**
	 * <p>Copyright: Copyright (c) 2018</p>  
	 * <p>Company: www.hoomsun.com</p>
	 * @author xinyuan.xu@hoomsun.com
	 * @param obj
	 * @param fieldName
	 * @return
	 * @Description:获取对象指定属性信息，取消安全机制限制，如果当前类不存在，则获取父类属性信息，未找到则返回NULL
	 * @param 2018年3月13日
	 */
	public static Field getField(Object obj , String fieldName){
		if(obj == null){
			return null;
		}
		Field field = null;
		for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
				break;
			} catch (NoSuchFieldException e) {
				continue;//第一次进入该异常，则clazz为obj  
						 //第二次进入该异常，则clazz为obj父类
						 //不考虑子类
			}
		}
		return field;
	}

	/**
	 * 
	 * <p>Copyright: Copyright (c) 2018</p>  
	 * <p>Company: www.hoomsun.com</p>
	 * @author xinyuan.xu@hoomsun.com
	 * @param clazz
	 * @return
	 * @Description:获取指定对象的所有属性，包含父类属性
	 * @param 2018年3月13日
	 */
	public static Field[] getFieldArrayExcludeUID(Class<?> clazz){
		Field[] currField = clazz.getDeclaredFields();
		clazz = clazz.getSuperclass();
		Field[] supField = clazz.getDeclaredFields();
		Field[] temp = new Field[currField.length + supField.length];
		int length = 0;
		for (Field curr : currField) {
			if("serialVersionUID".equals(curr.getName())){
				continue;
			}
			temp[length] = curr;
			length ++ ;
		}
		for (Field sup : supField) {
			if("serialVersionUID".equals(sup.getName())){
				continue;
			}
			temp[length] = sup;
			length ++ ;
		}
		Field[] all = new Field[length];
		for (int i = 0 ; i < all.length ; i ++) {
			all[i] = temp[i];
		}
		return all;
	}
	
	/**
	 * <p>Copyright: Copyright (c) 2018</p>  
	 * <p>Company: www.hoomsun.com</p>
	 * @author xinyuan.xu@hoomsun.com
	 * @param obj
	 * @param fieldName
	 * @return
	 * @Description:获取指定类字段属性值,取消安全访问限制，如果当前类不存在，则获取父类属性值  发生异常则返回NULL
	 * @param 2018年3月13日
	 */
	public static Object getFieldVal(Object obj , String fieldName){
		Field field;
		if(obj == null){
			return null;
		}
		try {
			field = getField(obj,fieldName);
			field.setAccessible(true);//取消安全机制限制
			return field.get(obj);
		} catch (SecurityException e) {
			LogCvt.error(e.getMessage(),e);
			return null;
		} catch (IllegalArgumentException e) {
			LogCvt.error(e.getMessage(),e);
			return null;
		} catch (IllegalAccessException e) {
			LogCvt.error(e.getMessage(),e);
			return null;
		}
	}
	
	
	/**
	 * <p>Copyright: Copyright (c) 2018</p>  
	 * <p>Company: www.hoomsun.com</p>
	 * @author xinyuan.xu@hoomsun.com
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @Description:为字段属性设置属性值
	 * @param 2018年3月13日
	 */
	public static void setFieldVal(Object obj,String fieldName ,Object value){
		Field field;
		if(obj == null){
			return ;
		}
		try {
			field = getField(obj,fieldName);
			field.setAccessible(true);//取消安全机制限制
			field.set(obj, value);
		} catch (SecurityException e) {
			LogCvt.error(e.getMessage(),e);
		} catch (IllegalArgumentException e) {
			LogCvt.error(e.getMessage(),e);
		} catch (IllegalAccessException e) {
			LogCvt.error(e.getMessage(),e);
		}
	}
	
	/**获取指定方法
	 * <p>Copyright: Copyright (c) 2018</p>  
	 * <p>Company: www.hoomsun.com</p>
	 * @author xinyuan.xu@hoomsun.com
	 * @param obj
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 * @Description:
	 * @param 2018年3月13日
	 */
	public static Method getMethod(Object obj,String methodName,Class<?>... parameterTypes){
		if(obj == null){
			return null;
		}
		Class<?> clazz = obj.getClass();
		try {
			return clazz.getMethod(methodName, parameterTypes);
		} catch (SecurityException e) {
			LogCvt.error(e.getMessage(),e);
		} catch (NoSuchMethodException e) {
			LogCvt.error(e.getMessage(),e);
		}
		return null;
	}
	
	/**
	 * <p>Copyright: Copyright (c) 2018</p>  
	 * <p>Company: www.hoomsun.com</p>
	 * @author xinyuan.xu@hoomsun.com
	 * @param obj
	 * @param method
	 * @param parameter
	 * @Description:执行指定方法
	 * @param 2018年3月13日
	 */
	public static void invokeMethod(Object obj,Method method,Object... parameter){
		try {
			method.invoke(obj, parameter);
		} catch (IllegalArgumentException e) {
			LogCvt.error(e.getMessage(),e);
		} catch (IllegalAccessException e) {
			LogCvt.error(e.getMessage(),e);
		} catch (InvocationTargetException e) {
			LogCvt.error(e.getMessage(),e);
		}
	}
	
	public static Class<?> getSignificantSupperClass(Class<?> classType){
		Class<?> supperClass = classType;
		while(classType != null){
			classType = classType.getSuperclass();
			if(Object.class == classType || null == classType){
				break;
			}
			supperClass = classType;
		}
		return supperClass;
	}
}
