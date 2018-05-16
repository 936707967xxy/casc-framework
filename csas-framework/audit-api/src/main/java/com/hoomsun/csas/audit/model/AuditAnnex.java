package com.hoomsun.csas.audit.model;
/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月16日 <br>
 * 描述： 审核附件
 *
 */
public class AuditAnnex {
    private String anxId;

    private String applyId;

    private String fileName;

    private String saveName;

    private String savePath;

    private String preView;

    private Long fileSize;

    private String fileType;

    private Object createDate;

    private String empId;

    private String empName;

    private String actId;

    private String actNode;

    private Integer anxType;

    private String anxTypeVal;

    public String getAnxId() {
        return anxId;
    }

    public void setAnxId(String anxId) {
        this.anxId = anxId == null ? null : anxId.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName == null ? null : saveName.trim();
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath == null ? null : savePath.trim();
    }

    public String getPreView() {
        return preView;
    }

    public void setPreView(String preView) {
        this.preView = preView == null ? null : preView.trim();
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public Object getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Object createDate) {
        this.createDate = createDate;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId == null ? null : empId.trim();
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId == null ? null : actId.trim();
    }

    public String getActNode() {
        return actNode;
    }

    public void setActNode(String actNode) {
        this.actNode = actNode == null ? null : actNode.trim();
    }

    public Integer getAnxType() {
        return anxType;
    }

    public void setAnxType(Integer anxType) {
        this.anxType = anxType;
    }

    public String getAnxTypeVal() {
        return anxTypeVal;
    }

    public void setAnxTypeVal(String anxTypeVal) {
        this.anxTypeVal = anxTypeVal == null ? null : anxTypeVal.trim();
    }
}