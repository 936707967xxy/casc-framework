package com.hoomsun.after.api.model.table;

import java.util.Date;
/**
 * 
 * 作者：屈楠 <br>
 * 创建时间：2018年4月4日 <br>
 * 描述：HS_AFTER_ALLOT 分派表	
 *
 */

public class Allot {
    

	private String id;

	//分派类型 （1：前线副理分派，2：贷后分派3：外访分派）
    private String allotType;

    //进件编号
    private String laonId;

    //合同编号
    private String conNo;

    //旧管理客服ID
    private String oldManagerCastid;

    //旧管理客服
    private String oldManagerCast;

    //新管理客服ID
    private String newManagerCastid;

    //新管理客服
    private String newManagerCast;

    //操作人ID
    private String operationId;

    //操作人NAME
    private String operationName;

    //分派时间
    private Date allotTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAllotType() {
        return allotType;
    }

    public void setAllotType(String allotType) {
        this.allotType = allotType == null ? null : allotType.trim();
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

    public String getOldManagerCastid() {
        return oldManagerCastid;
    }

    public void setOldManagerCastid(String oldManagerCastid) {
        this.oldManagerCastid = oldManagerCastid == null ? null : oldManagerCastid.trim();
    }

    public String getOldManagerCast() {
        return oldManagerCast;
    }

    public void setOldManagerCast(String oldManagerCast) {
        this.oldManagerCast = oldManagerCast == null ? null : oldManagerCast.trim();
    }

    public String getNewManagerCastid() {
        return newManagerCastid;
    }

    public void setNewManagerCastid(String newManagerCastid) {
        this.newManagerCastid = newManagerCastid == null ? null : newManagerCastid.trim();
    }

    public String getNewManagerCast() {
        return newManagerCast;
    }

    public void setNewManagerCast(String newManagerCast) {
        this.newManagerCast = newManagerCast == null ? null : newManagerCast.trim();
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId == null ? null : operationId.trim();
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName == null ? null : operationName.trim();
    }

    public Date getAllotTime() {
        return allotTime;
    }

    public void setAllotTime(Date allotTime) {
        this.allotTime = allotTime;
    }
}