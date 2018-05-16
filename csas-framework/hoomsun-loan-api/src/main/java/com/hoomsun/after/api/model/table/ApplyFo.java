package com.hoomsun.after.api.model.table;

import java.util.Date;
/**
 * 
 * 作者：屈楠 <br>
 * 创建时间：2018年4月4日 <br>
 * 描述：HS_AFTER_APPLY_FO 贷后流程申请表		
 *
 */

public class ApplyFo {
   

	private String id;

	//申请单号
    private String applyId;

    //申请类型
    private String applyType;

    //申请人id
    private String applicationCastId;

    //申请人name
    private String applicationCastName;

    //申请备注
    private String applicationDesc;

    //客户姓名
    private String castName;

    //身份证号
    private String cardNo;

    //合同编号
    private String conNo;

    //进件编号
    private String loanId;

    //审批状态 (0000申请通过，1111申请拒绝)2 3 4 5 6 7 8 9 
    private String applyStatus;

    //申请拒绝节点
    private String applyReturn;

    //创建日期
    private Date createTime;

    //修改时间
    private Date updateDate;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType == null ? null : applyType.trim();
    }

    public String getApplicationCastId() {
        return applicationCastId;
    }

    public void setApplicationCastId(String applicationCastId) {
        this.applicationCastId = applicationCastId == null ? null : applicationCastId.trim();
    }

    public String getApplicationCastName() {
        return applicationCastName;
    }

    public void setApplicationCastName(String applicationCastName) {
        this.applicationCastName = applicationCastName == null ? null : applicationCastName.trim();
    }

    public String getApplicationDesc() {
        return applicationDesc;
    }

    public void setApplicationDesc(String applicationDesc) {
        this.applicationDesc = applicationDesc == null ? null : applicationDesc.trim();
    }

    public String getCastName() {
        return castName;
    }

    public void setCastName(String castName) {
        this.castName = castName == null ? null : castName.trim();
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public String getConNo() {
        return conNo;
    }

    public void setConNo(String conNo) {
        this.conNo = conNo == null ? null : conNo.trim();
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId == null ? null : loanId.trim();
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus == null ? null : applyStatus.trim();
    }

    public String getApplyReturn() {
        return applyReturn;
    }

    public void setApplyReturn(String applyReturn) {
        this.applyReturn = applyReturn == null ? null : applyReturn.trim();
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
}