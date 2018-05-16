/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

import java.math.BigDecimal;

/**
 * 作者：zwLiu <br>
 * 创建时间：2018年4月23日 <br>
 * 描述：富友划扣
 */
public class FYdeductModel {
	/**
	 * 账号
	 */
	private String accntno;
	/**
	 * 账户名称
	 */
	private String accntnm;
	/**
	 * 划扣金额
	 */
	private BigDecimal amt;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 身份证号
	 */
	private String certno;

	/**
	 * 开户行代码
	 */
	private String bankno;

	/**
	 * 短信验证码
	 */
	private String verifyCode;

	/**
	 * 线上线下标识（线上：1；线下：0）
	 */
	private String updownStatus;

	/**
	 * 项目ID
	 */
	private String prijectid;

	/**
	 * 文件项目路径
	 */
	private String path;

	public String getAccntno() {
		return accntno;
	}

	public void setAccntno(String accntno) {
		this.accntno = accntno;
	}

	public String getAccntnm() {
		return accntnm;
	}

	public void setAccntnm(String accntnm) {
		this.accntnm = accntnm;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public String getUpdownStatus() {
		return updownStatus;
	}

	public void setUpdownStatus(String updownStatus) {
		this.updownStatus = updownStatus;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCertno() {
		return certno;
	}

	public void setCertno(String certno) {
		this.certno = certno;
	}

	public String getBankno() {
		return bankno;
	}

	public void setBankno(String bankno) {
		this.bankno = bankno;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getPrijectid() {
		return prijectid;
	}

	public void setPrijectid(String prijectid) {
		this.prijectid = prijectid;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "FYdeductModel [账号=" + accntno + ", 账户名称=" + accntnm + ", 划扣金额=" + amt + ", 手机号码=" + mobile + ", 身份证号=" + certno + ", 开户行代码=" + bankno + ", 短信验证码=" + verifyCode + ", 线上线下标识=" + updownStatus + ", prijectid=" + prijectid + "]";
	}

}
