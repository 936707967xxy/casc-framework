package com.hoomsun.after.api.model.table;

import java.util.Date;

public class HxbRepaySend {
    private String id;

    private String loanId;

    private Integer stream;

    private String repaidType;

    private Date repaidTime;

    private String overdueFlag;

    private Date createDate;

    private Date updateDate;

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

    public Integer getStream() {
        return stream;
    }

    public void setStream(Integer stream) {
        this.stream = stream;
    }

    public String getRepaidType() {
        return repaidType;
    }

    public void setRepaidType(String repaidType) {
        this.repaidType = repaidType == null ? null : repaidType.trim();
    }

    public Date getRepaidTime() {
        return repaidTime;
    }

    public void setRepaidTime(Date repaidTime) {
        this.repaidTime = repaidTime;
    }

    public String getOverdueFlag() {
        return overdueFlag;
    }

    public void setOverdueFlag(String overdueFlag) {
        this.overdueFlag = overdueFlag == null ? null : overdueFlag.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}