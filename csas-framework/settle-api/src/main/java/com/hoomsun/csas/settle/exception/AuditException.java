/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.settle.exception;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月28日 <br>
 * 描述：审核异常申明
 */
public class AuditException extends RuntimeException {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月28日 <br>
	 * 描述：
	 */
	private static final long serialVersionUID = -7569845432625791015L;

	public AuditException() {
		super();
	}

	public AuditException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AuditException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuditException(String message) {
		super(message);
	}

	public AuditException(Throwable cause) {
		super(cause);
	}
}
