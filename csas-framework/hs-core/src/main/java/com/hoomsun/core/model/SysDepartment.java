package com.hoomsun.core.model;

import java.util.Date;


public class SysDepartment {
    private String deptId;

    private String addDate;

    private String addEmp;

    private String deptName;

    private Integer deptStatus;

    private String deptTel;

    private String deptWorkAddr;

    private String modifyDate;

    private String modifyEmp;

    private String comId;

    private String deptParent;

    private String deptClass;

    private String deptWorkAddrNo;

    private String deptNo;
	
	// 增加用于回显
    private String deptParentName;
    // 增加用于回显
    private String comName;
	
	private String manager; // 部门负责人id

    private String oaParent;
	
    private String deptLongitude; // 经度

    private String deptLatitude; // 纬度

    private String levelVal;

    private String levelText;

    private String deptCode;

    private Short storeFlag;

    private String oaId;
    
    private String cityName;

    private String cityCode;

    private String provinceName;

    private String provinceCode;

    private Date foundDate;

	

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate == null ? null : addDate.trim();
    }

    public String getAddEmp() {
        return addEmp;
    }

    public void setAddEmp(String addEmp) {
        this.addEmp = addEmp == null ? null : addEmp.trim();
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public Integer getDeptStatus() {
        return deptStatus;
    }

    public void setDeptStatus(Integer deptStatus) {
        this.deptStatus = deptStatus;
    }

    public String getDeptTel() {
        return deptTel;
    }

    public void setDeptTel(String deptTel) {
        this.deptTel = deptTel == null ? null : deptTel.trim();
    }

    public String getDeptWorkAddr() {
        return deptWorkAddr;
    }

    public void setDeptWorkAddr(String deptWorkAddr) {
        this.deptWorkAddr = deptWorkAddr == null ? null : deptWorkAddr.trim();
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate == null ? null : modifyDate.trim();
    }

    public String getModifyEmp() {
        return modifyEmp;
    }

    public void setModifyEmp(String modifyEmp) {
        this.modifyEmp = modifyEmp == null ? null : modifyEmp.trim();
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId == null ? null : comId.trim();
    }

    public String getDeptParent() {
        return deptParent;
    }

    public void setDeptParent(String deptParent) {
        this.deptParent = deptParent == null ? null : deptParent.trim();
    }

    public String getDeptClass() {
        return deptClass;
    }

    public void setDeptClass(String deptClass) {
        this.deptClass = deptClass == null ? null : deptClass.trim();
    }

    public String getDeptWorkAddrNo() {
        return deptWorkAddrNo;
    }

    public void setDeptWorkAddrNo(String deptWorkAddrNo) {
        this.deptWorkAddrNo = deptWorkAddrNo == null ? null : deptWorkAddrNo.trim();
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo == null ? null : deptNo.trim();
    }

    public String getDeptLongitude() {
        return deptLongitude;
    }

    public void setDeptLongitude(String deptLongitude) {
        this.deptLongitude = deptLongitude == null ? null : deptLongitude.trim();
    }

    public String getDeptLatitude() {
        return deptLatitude;
    }

    public void setDeptLatitude(String deptLatitude) {
        this.deptLatitude = deptLatitude == null ? null : deptLatitude.trim();
    }

    public String getLevelVal() {
        return levelVal;
    }

    public void setLevelVal(String levelVal) {
        this.levelVal = levelVal == null ? null : levelVal.trim();
    }

    public String getLevelText() {
        return levelText;
    }

    public void setLevelText(String levelText) {
        this.levelText = levelText == null ? null : levelText.trim();
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    public Short getStoreFlag() {
        return storeFlag;
    }

    public void setStoreFlag(Short storeFlag) {
        this.storeFlag = storeFlag;
    }

    public String getOaId() {
        return oaId;
    }

    public void setOaId(String oaId) {
        this.oaId = oaId == null ? null : oaId.trim();
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager == null ? null : manager.trim();
    }

    public String getOaParent() {
        return oaParent;
    }

    public void setOaParent(String oaParent) {
        this.oaParent = oaParent == null ? null : oaParent.trim();
    }

	public String getDeptParentName() {
		return deptParentName;
	}

	public void setDeptParentName(String deptParentName) {
		this.deptParentName = deptParentName;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public Date getFoundDate() {
		return foundDate;
	}

	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}
    
	
}