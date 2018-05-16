package com.hoomsun.after.api.model.table;

import java.util.Date;


/**
 * 
 * 作者：屈楠 <br>
 * 创建时间：2018年2月27日 <br>
 * 描述：HS_AFTER_REVIEW贷后催收评审表
 *
 */


public class Review {
    private String id;

    //进件编号
    private String loanId;

    //合同编号
    private String conNo;

    //期数
    private Integer stream;

    //评语
    private String commentDesc;

    //创建日期
    private Date createTime;

    //修改时间
    private Date updateDate;

    //操作人ID
    private String applicationCastId;

    //操作人NAME
    private String applicationCastName;
    
    //逾期期次
    private String overdueNum;

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

    public Integer getStream() {
        return stream;
    }

    public void setStream(Integer stream) {
        this.stream = stream;
    }

    public String getCommentDesc() {
        return commentDesc;
    }

    public void setCommentDesc(String commentDesc) {
        this.commentDesc = commentDesc == null ? null : commentDesc.trim();
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

	public String getOverdueNum() {
		return overdueNum;
	}

	public void setOverdueNum(String overdueNum) {
		this.overdueNum = overdueNum;
	}
}