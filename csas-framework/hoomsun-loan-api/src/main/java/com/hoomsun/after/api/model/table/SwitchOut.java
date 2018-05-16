package com.hoomsun.after.api.model.table;

import java.util.Date;
/**
 * 
 * 作者：屈楠 <br>
 * 创建时间：2018年2月27日 <br>
 * 描述：HS_AFTER_SWITCH_OUT拉闸功能（逾期还款overdueDuct,正常还款nomdueDuct，提前还款AdvenceDuct）
 *
 */

public class SwitchOut {
    
	private String switchOut;

	/**
	 * 状态（0可用，1拉闸）
	 */
    private String status;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 修改日期
     */
    private Date updateDate;

    public String getSwitchOut() {
        return switchOut;
    }

    public void setSwitchOut(String switchOut) {
        this.switchOut = switchOut == null ? null : switchOut.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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