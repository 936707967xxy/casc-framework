package com.hoomsun.after.api.model.vo;

import java.math.BigDecimal;

/**
 * 断点查询标记
 * @author zwLiu
 *
 */
public class Ntrbptrsz1Model {
	/**
	 *未传完标记
	 */
	private String cotflg;
	/**
	 *末位记账序号
	 */
	private String trsseq;
	/**
	 *借方笔数
	 */
	private String dbtnbr;
	/**
	 *借方金额 
	 */
	private BigDecimal dbtamt;
	/**
	 *贷方笔数 
	 */
	private String crtnbr;
	/**
	 *贷方金额
	 */
	private BigDecimal crtamt;
	
	public String getCotflg() {
		return cotflg;
	}
	public void setCotflg(String cotflg) {
		this.cotflg = cotflg;
	}
	public String getTrsseq() {
		return trsseq;
	}
	public void setTrsseq(String trsseq) {
		this.trsseq = trsseq;
	}
	public String getDbtnbr() {
		return dbtnbr;
	}
	public void setDbtnbr(String dbtnbr) {
		this.dbtnbr = dbtnbr;
	}


	public String getCrtnbr() {
		return crtnbr;
	}
	public void setCrtnbr(String crtnbr) {
		this.crtnbr = crtnbr;
	}
	public BigDecimal getDbtamt() {
		return dbtamt;
	}
	public void setDbtamt(BigDecimal dbtamt) {
		this.dbtamt = dbtamt;
	}
	public BigDecimal getCrtamt() {
		return crtamt;
	}
	public void setCrtamt(BigDecimal crtamt) {
		this.crtamt = crtamt;
	}



	
}
