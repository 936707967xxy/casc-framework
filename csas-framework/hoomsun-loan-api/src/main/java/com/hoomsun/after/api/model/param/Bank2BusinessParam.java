package com.hoomsun.after.api.model.param;
/**
 * 
 * 作者：zwLiu <br>
 * 创建时间：2018年4月25日 <br>
 * 描述：银企直连  
 *
 */
public class Bank2BusinessParam {
	/**
	 * 开始日期
	 */
	private String startDate;
	/**
	 * 结束日期
	 */
	private String endDate;
	
	/**
	 * 交易日期
	 */
	private String transactionDate;
	/**
	 * 起 始 记 账 序
	 */
	private String trsseq;
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTrsseq() {
		return trsseq;
	}
	public void setTrsseq(String trsseq) {
		this.trsseq = trsseq;
	}
	
	
	
}
