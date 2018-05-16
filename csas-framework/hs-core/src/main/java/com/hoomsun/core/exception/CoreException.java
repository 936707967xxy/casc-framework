/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.exception;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月17日 <br>
 * 描述：异常定义
 */
public class CoreException extends RuntimeException{
	private static final long serialVersionUID = 6814278907544641349L;

	public CoreException() {
		super();
	}

	public CoreException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CoreException(String message, Throwable cause) {
		super(message, cause);
	}

	public CoreException(String message) {
		super(message);
	}

	public CoreException(Throwable cause) {
		super(cause);
	}

}
