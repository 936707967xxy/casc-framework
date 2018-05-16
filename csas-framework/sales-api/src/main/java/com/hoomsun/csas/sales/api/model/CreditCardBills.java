package com.hoomsun.csas.sales.api.model;

import java.util.List;

/**
 * 
 * 作者：liming<br>
 * 创建时间：2017年12月10日 <br>
 * 描述： 信用卡  CB_FKID关联HS_CREDIT_CARD的CC_ID
 *
 */
public class CreditCardBills {
    private String cbId;//主鍵
    
    private Object CreditLimit;//额度

    private Object billsDate;//账单日

    private Object repaymentDate;//还款日 

    private String minniMoney;//最小还款额 

    private String currentstermShouldmoney;//当期应还款额

    private String repaymentStatus;//还款状态

    private String cbFkid;//外鍵

    private String datas;
    
    private List<CreditCardBillsInfo> cardBillsInfos; 
    
    public String getCbId() {
        return cbId;
    }

    public void setCbId(String cbId) {
        this.cbId = cbId == null ? null : cbId.trim();
    }

    
    public Object getCreditLimit() {
		return CreditLimit;
	}

	public void setCreditLimit(Object creditLimit) {
		CreditLimit = creditLimit;
	}

	public Object getBillsDate() {
        return billsDate;
    }

    public void setBillsDate(Object billsDate) {
        this.billsDate = billsDate;
    }

    public Object getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(Object repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public String getMinniMoney() {
        return minniMoney;
    }

    public void setMinniMoney(String minniMoney) {
        this.minniMoney = minniMoney == null ? null : minniMoney.trim();
    }

    public String getCurrentstermShouldmoney() {
        return currentstermShouldmoney;
    }

    public void setCurrentstermShouldmoney(String currentstermShouldmoney) {
        this.currentstermShouldmoney = currentstermShouldmoney == null ? null : currentstermShouldmoney.trim();
    }

    public String getRepaymentStatus() {
        return repaymentStatus;
    }

    public void setRepaymentStatus(String repaymentStatus) {
        this.repaymentStatus = repaymentStatus == null ? null : repaymentStatus.trim();
    }

    public String getCbFkid() {
        return cbFkid;
    }

    public void setCbFkid(String cbFkid) {
        this.cbFkid = cbFkid == null ? null : cbFkid.trim();
    }

    public String getDatas() {
        return datas;
    }

    public void setDatas(String datas) {
        this.datas = datas == null ? null : datas.trim();
    }

	public List<CreditCardBillsInfo> getCardBillsInfos() {
		return cardBillsInfos;
	}

	public void setCardBillsInfos(List<CreditCardBillsInfo> cardBillsInfos) {
		this.cardBillsInfos = cardBillsInfos;
	}
    
}