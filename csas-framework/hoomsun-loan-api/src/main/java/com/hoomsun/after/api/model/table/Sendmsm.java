package com.hoomsun.after.api.model.table;

import java.util.Date;
/**
 * 
 * 作者：屈楠 <br>
 * 创建时间：2018年2月27日 <br>
 * 描述：HS_AFTER_SENDMSM贷后发送短信记录表
 *
 */
public class Sendmsm {
   
	private String id;

	/**
	 * 进件编号
	 */
    private String laonId;

    /**
     * 合同编号
     */
    private String conNo;

    /**
     * 短信发送期数
     */
    private Integer stream;

    /**
     * 发送内容
     */
    private String msmDesc;

    /**
     * 发送状态（0成功，1失败）
     */
    private String msmStatus;

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

    public String getLaonId() {
        return laonId;
    }

    public void setLaonId(String laonId) {
        this.laonId = laonId == null ? null : laonId.trim();
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

    public String getMsmDesc() {
        return msmDesc;
    }

    public void setMsmDesc(String msmDesc) {
        this.msmDesc = msmDesc == null ? null : msmDesc.trim();
    }

    public String getMsmStatus() {
        return msmStatus;
    }

    public void setMsmStatus(String msmStatus) {
        this.msmStatus = msmStatus == null ? null : msmStatus.trim();
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