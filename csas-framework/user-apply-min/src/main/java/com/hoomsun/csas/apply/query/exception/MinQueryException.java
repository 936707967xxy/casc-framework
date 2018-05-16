/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.apply.query.exception;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月5日 <br>
 * 描述：
 */
public class MinQueryException extends RuntimeException {
	private static final long serialVersionUID = 2123310049838388151L;

	public MinQueryException() {
		super();
	}

	public MinQueryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MinQueryException(String message, Throwable cause) {
		super(message, cause);
	}

	public MinQueryException(String message) {
		super(message);
	}

	public MinQueryException(Throwable cause) {
		super(cause);
	}

}
