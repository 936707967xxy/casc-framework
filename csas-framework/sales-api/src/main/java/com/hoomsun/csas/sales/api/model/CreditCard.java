package com.hoomsun.csas.sales.api.model;

import java.util.List;

/**
 * 
 * 作者：liming<br>
 * 创建时间：2017年12月10日 <br>
 * 描述： 信用卡主表
 *
 */
public class CreditCard {
    private String ccId;//主鍵

    private String accountName;//开户名 

    private String cardNumber;//卡号

    private String limitMoney;//额度

    private String openingBank;//开户行

    private Object openTime;//开户时间
    
    private List<CreditCardBills> cardBills;
    
    private String applyId;//申请编号

    public String getCcId() {
        return ccId;
    }

    public void setCcId(String ccId) {
        this.ccId = ccId == null ? null : ccId.trim();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber == null ? null : cardNumber.trim();
    }

    public String getLimitMoney() {
        return limitMoney;
    }

    public void setLimitMoney(String limitMoney) {
        this.limitMoney = limitMoney == null ? null : limitMoney.trim();
    }

    public String getOpeningBank() {
        return openingBank;
    }

    public void setOpeningBank(String openingBank) {
        this.openingBank = openingBank == null ? null : openingBank.trim();
    }

    public Object getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Object openTime) {
        this.openTime = openTime;
    }

	public List<CreditCardBills> getCardBills() {
		return cardBills;
	}

	public void setCardBills(List<CreditCardBills> cardBills) {
		this.cardBills = cardBills;
	}
    
    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }
}