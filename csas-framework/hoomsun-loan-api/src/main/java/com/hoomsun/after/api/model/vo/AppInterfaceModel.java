package com.hoomsun.after.api.model.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/** 
 * 作者：王鹏 <br>
 * 创建时间：2018年5月9日 <br>
 * 描述：APP接口列表
 *
 */

public class AppInterfaceModel {

	private List<AppInterfaceLoanData>  listMap;
	
	private BigDecimal  bigNum;
	
	private String  current;
	
	private String  totalCurrent;
	
	/**
	 * 应还剩余金额
	 */
	private BigDecimal  totalBalance;
	
	private BigDecimal  Interest;
	
	private BigDecimal  totalReceive;
	
	private String  earlyRepa;
	
	private BigDecimal  earlyRepayment;
	
	private BigDecimal  totalReceivePenaltyInterest;
	
	private Date  replaymentDate;
	
	private String  bank;
	
	private String  bankCode;
	
	private String  bankAccount;
	
	private String  bankPhone;
	
	private String  cardNo;


	public List<AppInterfaceLoanData> getListMap() {
		return listMap;
	}

	public void setListMap(List<AppInterfaceLoanData> listMap) {
		this.listMap = listMap;
	}


	public BigDecimal getBigNum() {
		return bigNum;
	}

	public void setBigNum(BigDecimal bigNum) {
		this.bigNum = bigNum;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public String getTotalCurrent() {
		return totalCurrent;
	}

	public void setTotalCurrent(String totalCurrent) {
		this.totalCurrent = totalCurrent;
	}


	public BigDecimal getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(BigDecimal totalBalance) {
		this.totalBalance = totalBalance;
	}


	public BigDecimal getInterest() {
		return Interest;
	}

	public void setInterest(BigDecimal interest) {
		Interest = interest;
	}

	public BigDecimal getTotalReceive() {
		return totalReceive;
	}

	public void setTotalReceive(BigDecimal totalReceive) {
		this.totalReceive = totalReceive;
	}

	public String getEarlyRepa() {
		return earlyRepa;
	}

	public void setEarlyRepa(String earlyRepa) {
		this.earlyRepa = earlyRepa;
	}

	public BigDecimal getEarlyRepayment() {
		return earlyRepayment;
	}

	public void setEarlyRepayment(BigDecimal earlyRepayment) {
		this.earlyRepayment = earlyRepayment;
	}

	public BigDecimal getTotalReceivePenaltyInterest() {
		return totalReceivePenaltyInterest;
	}

	public void setTotalReceivePenaltyInterest(
			BigDecimal totalReceivePenaltyInterest) {
		this.totalReceivePenaltyInterest = totalReceivePenaltyInterest;
	}

	public Date getReplaymentDate() {
		return replaymentDate;
	}

	public void setReplaymentDate(Date replaymentDate) {
		this.replaymentDate = replaymentDate;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankPhone() {
		return bankPhone;
	}

	public void setBankPhone(String bankPhone) {
		this.bankPhone = bankPhone;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	


}
