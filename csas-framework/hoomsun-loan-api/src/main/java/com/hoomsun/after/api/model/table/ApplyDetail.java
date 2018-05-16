package com.hoomsun.after.api.model.table;

import java.util.Date;
/**
 * 
 * 作者：屈楠 <br>
 * 创建时间：2018年4月23日 <br>
 * 描述：HS_AFTER_APPLY_DETAIL 贷后流程环节表	
 *
 */
public class ApplyDetail {
    

	private String id;

	//申请单号
    private String applyId;

    //申请类型
    private String applyType;

    //申请节点
    private String applyStatus;

    //审批人员id
    private String approveId;

    //审批人员name
    private String approveName;

    //审批备注
    private String approveDesc;

    //审批意见（0同意  1拒绝）
    private String approveOpinion;

    //创建日期
    private Date createTime;

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

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus == null ? null : applyStatus.trim();
    }

    public String getApproveId() {
        return approveId;
    }

    public void setApproveId(String approveId) {
        this.approveId = approveId == null ? null : approveId.trim();
    }

    public String getApproveName() {
        return approveName;
    }

    public void setApproveName(String approveName) {
        this.approveName = approveName == null ? null : approveName.trim();
    }

    public String getApproveDesc() {
        return approveDesc;
    }

    public void setApproveDesc(String approveDesc) {
        this.approveDesc = approveDesc == null ? null : approveDesc.trim();
    }

    public String getApproveOpinion() {
        return approveOpinion;
    }

    public void setApproveOpinion(String approveOpinion) {
        this.approveOpinion = approveOpinion == null ? null : approveOpinion.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}