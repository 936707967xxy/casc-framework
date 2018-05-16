package com.hoomsun.after.api.util;

import java.util.Collection;
import java.util.Date;
import java.util.Dictionary;
import java.util.Map;

public class EmptyChecker {
	public static boolean isEmpty(Object paramObject) {
		if (paramObject == null)
			return true;
		if (paramObject instanceof String)
			return (((String) paramObject == null) || ("".equals(paramObject)));
		if (paramObject instanceof Collection)
			return ((Collection) paramObject).isEmpty();
		if (paramObject instanceof Dictionary)
			return ((Dictionary) paramObject).isEmpty();
		if (paramObject instanceof Map)
			return ((Map) paramObject).isEmpty();
		return false;
	}

	public static boolean isNotEmpty(Object paramObject) {
		return (!(isEmpty(paramObject)));
	}

	public static String emptyDefaultValue(String paramString1,
			String paramString2) {
		if (isEmpty(paramString1))
			return paramString2;
		return paramString1;
	}

	public static int emptyDefaultValue(Integer paramInteger) {
		if (isEmpty(paramInteger))
			return 0;
		return paramInteger.intValue();
	}

	public static long emptyDefaultValue(Long paramLong) {
		if (isEmpty(paramLong))
			return 0L;
		return paramLong.longValue();
	}

	public static int emptyDefaultValue(String paramString, Integer paramInteger) {
		if (isEmpty(paramString))
			return paramInteger.intValue();
		return Integer.valueOf(paramString).intValue();
	}

	public static long emptyDefaultValue(String paramString, Long paramLong) {
		if (isEmpty(paramString))
			return 0L;
		return Long.valueOf(paramString).longValue();
	}

	public static long emptyDefaultValue(Date paramDate) {
		if (isEmpty(paramDate))
			return 0L;
		return paramDate.getTime();
	}

	public static boolean emptyDefaultValue(Boolean paramBoolean) {
		if (isEmpty(paramBoolean))
			return false;
		return paramBoolean.booleanValue();
	}
}
