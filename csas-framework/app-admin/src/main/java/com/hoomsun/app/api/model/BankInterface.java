package com.hoomsun.app.api.model;

public class BankInterface {
    private String bankinterId;

    private String bankNum;

    private String bankName;

    private String bankUrl;

    private String openVal;
    
    private String creditcardItf;

    private String creditcardItfOpen;

    private String depositcardItf;

    private String depositcardItfOpen;

    private BankInterfaceURL creditUrl; // 信用卡所有URL
    
    private BankInterfaceURL depositUrl; // 储蓄卡所有URL

	public String getBankinterId() {
        return bankinterId;
    }

    public void setBankinterId(String bankinterId) {
        this.bankinterId = bankinterId == null ? null : bankinterId.trim();
    }

    public String getBankNum() {
        return bankNum;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum == null ? null : bankNum.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankUrl() {
        return bankUrl;
    }

    public void setBankUrl(String bankUrl) {
        this.bankUrl = bankUrl == null ? null : bankUrl.trim();
    }

    public String getOpenVal() {
        return openVal;
    }

    public void setOpenVal(String openVal) {
        this.openVal = openVal == null ? null : openVal.trim();
    }

    public String getCreditcardItf() {
        return creditcardItf;
    }

    public void setCreditcardItf(String creditcardItf) {
        this.creditcardItf = creditcardItf == null ? null : creditcardItf.trim();
    }

    public String getCreditcardItfOpen() {
        return creditcardItfOpen;
    }

    public void setCreditcardItfOpen(String creditcardItfOpen) {
        this.creditcardItfOpen = creditcardItfOpen == null ? null : creditcardItfOpen.trim();
    }

    public String getDepositcardItf() {
        return depositcardItf;
    }

    public void setDepositcardItf(String depositcardItf) {
        this.depositcardItf = depositcardItf == null ? null : depositcardItf.trim();
    }

    public String getDepositcardItfOpen() {
        return depositcardItfOpen;
    }

    public void setDepositcardItfOpen(String depositcardItfOpen) {
        this.depositcardItfOpen = depositcardItfOpen == null ? null : depositcardItfOpen.trim();
    }
    
    public BankInterfaceURL getCreditUrl() {
		return creditUrl;
	}

	public void setCreditUrl(BankInterfaceURL creditUrl) {
		this.creditUrl = creditUrl;
	}

	public BankInterfaceURL getDepositUrl() {
		return depositUrl;
	}

	public void setDepositUrl(BankInterfaceURL depositUrl) {
		this.depositUrl = depositUrl;
	}
}