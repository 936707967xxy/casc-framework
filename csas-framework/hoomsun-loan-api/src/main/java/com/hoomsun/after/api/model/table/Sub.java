package com.hoomsun.after.api.model.table;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * 作者：屈楠 <br>
 * 创建时间：2018年4月24日 <br>
 * 描述：HS_AFTER_SUB减免详情表
 *
 */
public class Sub {
	
	private String id;

	//申请单号
    private String applyId;

    //减免类型
    private String applyType;

    //进件编号
    private String loanId;

    //合同编号
    private String conNo;

    //客户姓名
    private String castName;

  
    //减免罚息
    private BigDecimal subPenalty;
    //减免违约金
    private BigDecimal subInterest;

    //减免本金
    private BigDecimal subCorpus;

    //减免利息
    private BigDecimal subShouldinte;

    //减免总额
    private BigDecimal subSum;

    //减免期数
    private Integer subStream;

    //减免失效时间
    private Date subDate;

    //减免申请状态（1失效）
    private String subStatus;

    //创建日期
    private Date createTime;

    //修改时间
    private Date updateDate;

    //实还违约金
    private BigDecimal shPenalty;

    //实还罚息
    private BigDecimal shInterest;

    //实还本金
    private BigDecimal shCorpus;

    //实还利息
    private BigDecimal shShouldinte;

    //实还总额
    private BigDecimal shSum;

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

    public String getCastName() {
        return castName;
    }

    public void setCastName(String castName) {
        this.castName = castName == null ? null : castName.trim();
    }

    public BigDecimal getSubPenalty() {
        return subPenalty;
    }

    public void setSubPenalty(BigDecimal subPenalty) {
        this.subPenalty = subPenalty;
    }

    public BigDecimal getSubInterest() {
        return subInterest;
    }

    public void setSubInterest(BigDecimal subInterest) {
        this.subInterest = subInterest;
    }

    public BigDecimal getSubCorpus() {
        return subCorpus;
    }

    public void setSubCorpus(BigDecimal subCorpus) {
        this.subCorpus = subCorpus;
    }

    public BigDecimal getSubShouldinte() {
        return subShouldinte;
    }

    public void setSubShouldinte(BigDecimal subShouldinte) {
        this.subShouldinte = subShouldinte;
    }

    public BigDecimal getSubSum() {
        return subSum;
    }

    public void setSubSum(BigDecimal subSum) {
        this.subSum = subSum;
    }

    public Integer getSubStream() {
        return subStream;
    }

    public void setSubStream(Integer subStream) {
        this.subStream = subStream;
    }

    public Date getSubDate() {
        return subDate;
    }

    public void setSubDate(Date subDate) {
        this.subDate = subDate;
    }

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus == null ? null : subStatus.trim();
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

    public BigDecimal getShPenalty() {
        return shPenalty;
    }

    public void setShPenalty(BigDecimal shPenalty) {
        this.shPenalty = shPenalty;
    }

    public BigDecimal getShInterest() {
        return shInterest;
    }

    public void setShInterest(BigDecimal shInterest) {
        this.shInterest = shInterest;
    }

    public BigDecimal getShCorpus() {
        return shCorpus;
    }

    public void setShCorpus(BigDecimal shCorpus) {
        this.shCorpus = shCorpus;
    }

    public BigDecimal getShShouldinte() {
        return shShouldinte;
    }

    public void setShShouldinte(BigDecimal shShouldinte) {
        this.shShouldinte = shShouldinte;
    }

    public BigDecimal getShSum() {
        return shSum;
    }

    public void setShSum(BigDecimal shSum) {
        this.shSum = shSum;
    }
}