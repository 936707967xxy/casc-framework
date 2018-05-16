package com.hoomsun.after.api.model.vo;

public class BadRepayParamVo {
	public BadRepayParamVo() {
	}
	/**
	 * 
	 * @param status 请求状态
	 * @param errMsg 错误信息
	 * @param totalCount 请求总条数
	 * @param successCount 成功总条数
	 * @param errCount 失败总条数
	 */
	
	public BadRepayParamVo(String status, String errMsg, String totalCount, String successCount, String errCount) {
		this.status = status;
		this.errMsg = errMsg;
		this.totalCount = totalCount;
		this.successCount = successCount;
		this.errCount = errCount;
	}

	public BadRepayParamVo(String status, String errMsg) {
		super();
		this.status = status;
		this.errMsg = errMsg;
	}

	/**
	 * 请求状态
	 */
	private String status;
	/**
	 * 错误信息
	 */
	private String errMsg;
	/**
	 * 请求总条数
	 */
	private String totalCount;
	/**
	 * 成功总条数
	 */
	private String successCount;
	/**
	 * 失败总条数
	 */
	private String errCount;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(String successCount) {
		this.successCount = successCount;
	}

	public String getErrCount() {
		return errCount;
	}

	public void setErrCount(String errCount) {
		this.errCount = errCount;
	}
}
