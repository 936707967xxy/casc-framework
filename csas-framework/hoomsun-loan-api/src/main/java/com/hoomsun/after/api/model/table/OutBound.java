package com.hoomsun.after.api.model.table;

import java.util.Date;
/**
 * 
 * 作者：屈楠 <br>
 * 创建时间：2018年4月4日 <br>
 * 描述：HS_AFTER_OUTBOUND 外访申请详情表	
 *
 */
public class OutBound {
    
	
	private String id;

	//进件编号
    private String loanId;

    //合同编号
    private String conNo;

    //申请地址
    private String outboundAddress;

    //催收状态
    private String overdueStatus;

    //建议上门时间
    private Date outboundDate;

    //催收详情
    private String overdueDesc;

    //申请备注
    private String applyDesc;

    //外访申请状态（0通过，1失效，2申请中）
    private String outboundStatus;

    //外访人员id
    private String outboundId;

    //外访人员name
    private String outboundName;

    //外访情况说明
    private String outboundDesc;

    //创建日期
    private Date createTime;

    //修改日期
    private Date updateDate;

    //申请单号
    private String applyId;
    
 // 客戶姓名
 	private String castName;

 	// 身份証號
 	private String cardNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId == null ? null : loanId.trim();
    }

    public String getConNo() {
        return conNo;
    }

    public void setConNo(String conNo) {
        this.conNo = conNo == null ? null : conNo.trim();
    }

    public String getOutboundAddress() {
        return outboundAddress;
    }

    public void setOutboundAddress(String outboundAddress) {
        this.outboundAddress = outboundAddress == null ? null : outboundAddress.trim();
    }

    public String getOverdueStatus() {
        return overdueStatus;
    }

    public void setOverdueStatus(String overdueStatus) {
        this.overdueStatus = overdueStatus == null ? null : overdueStatus.trim();
    }

    public Date getOutboundDate() {
        return outboundDate;
    }

    public void setOutboundDate(Date outboundDate) {
        this.outboundDate = outboundDate;
    }

    public String getOverdueDesc() {
        return overdueDesc;
    }

    public void setOverdueDesc(String overdueDesc) {
        this.overdueDesc = overdueDesc == null ? null : overdueDesc.trim();
    }

    public String getApplyDesc() {
        return applyDesc;
    }

    public void setApplyDesc(String applyDesc) {
        this.applyDesc = applyDesc == null ? null : applyDesc.trim();
    }

    public String getOutboundStatus() {
        return outboundStatus;
    }

    public void setOutboundStatus(String outboundStatus) {
        this.outboundStatus = outboundStatus == null ? null : outboundStatus.trim();
    }

    public String getOutboundId() {
        return outboundId;
    }

    public void setOutboundId(String outboundId) {
        this.outboundId = outboundId == null ? null : outboundId.trim();
    }

    public String getOutboundName() {
        return outboundName;
    }

    public void setOutboundName(String outboundName) {
        this.outboundName = outboundName == null ? null : outboundName.trim();
    }

    public String getOutboundDesc() {
        return outboundDesc;
    }

    public void setOutboundDesc(String outboundDesc) {
        this.outboundDesc = outboundDesc == null ? null : outboundDesc.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

	public String getCastName() {
		return castName;
	}

	public void setCastName(String castName) {
		this.castName = castName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
    
    
}