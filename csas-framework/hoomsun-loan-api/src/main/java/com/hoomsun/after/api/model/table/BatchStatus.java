package com.hoomsun.after.api.model.table;

import java.util.Date;
/**
 * 
 * 作者：屈楠 <br>
 * 创建时间：2018年2月27日 <br>
 * 描述： 跑批记录表HS_AFTER_BATCH_STATUS
 *
 */

public class BatchStatus {
    
	private String id;
	
	/**
	 * 跑批类型（逾期跑批overdueBatch）
	 */
    private String batchType;
    
    /**
     * 跑批时间
     */
    private Date batchDate;
    
    /**
     * 跑批状态（0成功，1失败）
     */
    private String batchStatus;
    
    /**
     * 创建日期
     */
    private Date createTime;
    
    /**
     * 修改时间
     */
    private Date updateDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBatchType() {
        return batchType;
    }

    public void setBatchType(String batchType) {
        this.batchType = batchType == null ? null : batchType.trim();
    }

    public Date getBatchDate() {
        return batchDate;
    }

    public void setBatchDate(Date batchDate) {
        this.batchDate = batchDate;
    }

    public String getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus == null ? null : batchStatus.trim();
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