/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.exception;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月8日 <br>
 * 描述：反欺诈 勾稽 异常定义
 */
public class CheatException extends RuntimeException {
	private static final long serialVersionUID = 3474912736102817620L;

	public CheatException() {
		super();
	}

	public CheatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CheatException(String message, Throwable cause) {
		super(message, cause);
	}

	public CheatException(String message) {
		super(message);
	}

	public CheatException(Throwable cause) {
		super(cause);
	}

}
