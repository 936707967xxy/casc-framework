package com.hoomsun.after.api.model.vo;


import java.math.BigDecimal;
/**
 * 
 * 作者：zwLiu <br>
 * 创建时间：2018年4月25日 <br>
 * 描述： 银企直连
 *
 */


public class TransInfoModel {

	/**
	 * 主键ID
	 */
	private String pramKeyid;
	/**
	 * 交易日
	 */
	private String etydat;
	/**
	 * 交易时间
	 */
	private String etytim;
	
	/**
	 * 交期日期
	 */
	private String transactionDate;
	/**
	 * 交易类型
	 */
	private String trscod;
	/**
	 * 摘要
	 */
	private String naryur;
	/**
	 * 贷方金额
	 */
	private BigDecimal trsamtc;
	/**
	 * 借贷标记
	 */
	private String amtcdr;
	/**
	 * 余额
	 */
	private BigDecimal trsblv;
	/**
	 * 流水号
	 */
	private String refnbr;
	/**
	 * 流程实例号
	 */
	private String reqnbr;
	/**
	 * 业务名称
	 */
	private String busnam;
	/**
	 * 收/付方名称
	 */
	private String rpynam;
	/**
	 * 收/付方帐号
	 */
	private String rpyacc;
	/**
	 * 收/付方开户行名
	 */
	private String rpybnk;
	/**
	 * 收/付方开户行地址
	 */
	private String rpyadr;
	/**
	 * 扩展摘要
	 */
	private String narext;	
	/**
	 * 交易金额
	 */
	private BigDecimal trsamt;
	public String getEtydat() {
		return etydat;
	}
	public void setEtydat(String etydat) {
		this.etydat = etydat;
	}
	public String getEtytim() {
		return etytim;
	}
	public void setEtytim(String etytim) {
		this.etytim = etytim;
	}
	public String getTrscod() {
		return trscod;
	}
	public void setTrscod(String trscod) {
		this.trscod = trscod;
	}
	public String getNaryur() {
		return naryur;
	}
	public void setNaryur(String naryur) {
		this.naryur = naryur;
	}
	public BigDecimal getTrsamtc() {
		return trsamtc;
	}
	public void setTrsamtc(BigDecimal trsamtc) {
		this.trsamtc = trsamtc;
	}
	public String getAmtcdr() {
		return amtcdr;
	}
	public void setAmtcdr(String amtcdr) {
		this.amtcdr = amtcdr;
	}


	public String getRefnbr() {
		return refnbr;
	}
	public void setRefnbr(String refnbr) {
		this.refnbr = refnbr;
	}
	public String getReqnbr() {
		return reqnbr;
	}
	public void setReqnbr(String reqnbr) {
		this.reqnbr = reqnbr;
	}
	public String getBusnam() {
		return busnam;
	}
	public void setBusnam(String busnam) {
		this.busnam = busnam;
	}
	public String getRpynam() {
		return rpynam;
	}
	public void setRpynam(String rpynam) {
		this.rpynam = rpynam;
	}
	public String getRpyacc() {
		return rpyacc;
	}
	public void setRpyacc(String rpyacc) {
		this.rpyacc = rpyacc;
	}
	public String getRpybnk() {
		return rpybnk;
	}
	public void setRpybnk(String rpybnk) {
		this.rpybnk = rpybnk;
	}
	public String getRpyadr() {
		return rpyadr;
	}
	public void setRpyadr(String rpyadr) {
		this.rpyadr = rpyadr;
	}
	public String getNarext() {
		return narext;
	}
	public void setNarext(String narext) {
		this.narext = narext;
	}
	public BigDecimal getTrsamt() {
		return trsamt;
	}
	public void setTrsamt(BigDecimal trsamt) {
		this.trsamt = trsamt;
	}


	public BigDecimal getTrsblv() {
		return trsblv;
	}
	public void setTrsblv(BigDecimal trsblv) {
		this.trsblv = trsblv;
	}
	
	public String getPramKeyid() {

		return this.etydat + this.refnbr;
	}
	
	public String getTransactionDate() {
		return this.etydat + this.etytim;
	}








	


}
