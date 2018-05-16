package com.hoomsun.after.api.util.excel.secode.annotation;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

/**
 *   关于异常的工具类.
 * @author xinyuan.xu@hoomsun.com
 */
public class Exceptions {

	/**
	 * @author xinyuan.xu@hoomsun.com
	 * @param e
	 * @return
	 * @Description:将CheckedException转换为UncheckedException.
	 * @param 2018年2月2日
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}

	/**
	 * @author xinyuan.xu@hoomsun.com
	 * @param e
	 * @return
	 * @Description:将ErrorStack转化为String.
	 * @param 2018年2月2日
	 */
	public static String getStackTraceAsString(Throwable e) {
		if (e == null){
			return "";
		}
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	/**
	 * @author xinyuan.xu@hoomsun.com
	 * @param ex
	 * @param causeExceptionClasses
	 * @return
	 * @Description:判断异常是否由某些底层的异常引起.
	 * @param 2018年2月2日
	 */
	public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
		Throwable cause = ex.getCause();
		while (cause != null) {
			for (Class<? extends Exception> causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}

	/**
	 * @author xinyuan.xu@hoomsun.com
	 * @param request
	 * @return
	 * @Description:在request中获取异常类
	 * @param 2018年2月2日
	 */
	public static Throwable getThrowable(HttpServletRequest request){
		Throwable ex = null;
		if (request.getAttribute("exception") != null) {
			ex = (Throwable) request.getAttribute("exception");
		} else if (request.getAttribute("javax.servlet.error.exception") != null) {
			ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
		}
		return ex;
	}
	
}
