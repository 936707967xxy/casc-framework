package com.hoomsun.csas.sales.api.model;

/**
 * 
 * 作者：liming<br>
 * 创建时间：2017年12月10日 <br>
 * 描述： 信用卡账单详情HCCBI_FKID关联 HS_CREDIT_CARD_BILLS的CARDBILLS_ID
 *
 */
public class CreditCardBillsInfo {
    private String hccbiId;

    private String postAmt;

    private String tranDesc;

    private Object tranDate;

    private String hccbiFkid;

    public String getHccbiId() {
        return hccbiId;
    }

    public void setHccbiId(String hccbiId) {
        this.hccbiId = hccbiId == null ? null : hccbiId.trim();
    }

   

    public String getPostAmt() {
		return postAmt;
	}

	public void setPostAmt(String postAmt) {
		this.postAmt = postAmt;
	}

	public String getTranDesc() {
        return tranDesc;
    }

    public void setTranDesc(String tranDesc) {
        this.tranDesc = tranDesc == null ? null : tranDesc.trim();
    }

    public Object getTranDate() {
        return tranDate;
    }

    public void setTranDate(Object tranDate) {
        this.tranDate = tranDate;
    }

    public String getHccbiFkid() {
        return hccbiFkid;
    }

    public void setHccbiFkid(String hccbiFkid) {
        this.hccbiFkid = hccbiFkid == null ? null : hccbiFkid.trim();
    }
}