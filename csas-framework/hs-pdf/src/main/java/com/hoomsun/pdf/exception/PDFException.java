/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.pdf.exception;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月26日 <br>
 * 描述：pdf 解析异常
 */
public class PDFException extends RuntimeException {
	private static final long serialVersionUID = 8096924329009908129L;

	public PDFException() {
		super();
	}

	public PDFException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PDFException(String message, Throwable cause) {
		super(message, cause);
	}

	public PDFException(String message) {
		super(message);
	}

	public PDFException(Throwable cause) {
		super(cause);
	}

}
