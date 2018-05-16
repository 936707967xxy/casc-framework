package com.hoomsun.app.api.model;

public class AfreshProtoinfo {
    private String protoinfoId;

    private String fkId;

    private String accbankname;

    private String accbankid;

    private String accname;

    private String accno;

    private String mobile;

    private String branchnameAddress;

    private String branchnameProvName;
    
    private String branchnameProvCode;

    private String branchnameCityName;
    
    private String branchnameCityCode;

    private String type;    //卡类型    还款卡  1       其他卡   2

    private String typeVal; //卡类型    还款卡  1       其他卡   2

    private String deleteFlag;    //生效    1    失效    0

    private String deleteFlagVal; //生效    1    失效    0
    
    private String isDefault;  //默认卡  0   1 不是

    private String isDefaultVal; //默认卡  0   1 不是
    
    private String isSalary;  //工资卡  0   1 不是

    private String isSalaryVal;//工资卡  0   1 不是

    public String getProtoinfoId() {
        return protoinfoId;
    }

    public void setProtoinfoId(String protoinfoId) {
        this.protoinfoId = protoinfoId == null ? null : protoinfoId.trim();
    }

    public String getFkId() {
        return fkId;
    }

    public void setFkId(String fkId) {
        this.fkId = fkId == null ? null : fkId.trim();
    }

    public String getAccbankname() {
        return accbankname;
    }

    public void setAccbankname(String accbankname) {
        this.accbankname = accbankname == null ? null : accbankname.trim();
    }

    public String getAccbankid() {
        return accbankid;
    }

    public void setAccbankid(String accbankid) {
        this.accbankid = accbankid == null ? null : accbankid.trim();
    }

    public String getAccname() {
        return accname;
    }

    public void setAccname(String accname) {
        this.accname = accname == null ? null : accname.trim();
    }

    public String getAccno() {
        return accno;
    }

    public void setAccno(String accno) {
        this.accno = accno == null ? null : accno.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getBranchnameAddress() {
        return branchnameAddress;
    }

    public void setBranchnameAddress(String branchnameAddress) {
        this.branchnameAddress = branchnameAddress == null ? null : branchnameAddress.trim();
    }
    
    public String getBranchnameProvCode() {
		return branchnameProvCode;
	}

	public void setBranchnameProvCode(String branchnameProvCode) {
		this.branchnameProvCode = branchnameProvCode;
	}

	public String getBranchnameCityCode() {
		return branchnameCityCode;
	}

	public void setBranchnameCityCode(String branchnameCityCode) {
		this.branchnameCityCode = branchnameCityCode;
	}

	public String getBranchnameProvName() {
        return branchnameProvName;
    }

    public void setBranchnameProvName(String branchnameProvName) {
        this.branchnameProvName = branchnameProvName == null ? null : branchnameProvName.trim();
    }

    public String getBranchnameCityName() {
        return branchnameCityName;
    }

    public void setBranchnameCityName(String branchnameCityName) {
        this.branchnameCityName = branchnameCityName == null ? null : branchnameCityName.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getTypeVal() {
        return typeVal;
    }

    public void setTypeVal(String typeVal) {
        this.typeVal = typeVal == null ? null : typeVal.trim();
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag == null ? null : deleteFlag.trim();
    }

    public String getDeleteFlagVal() {
        return deleteFlagVal;
    }

    public void setDeleteFlagVal(String deleteFlagVal) {
        this.deleteFlagVal = deleteFlagVal == null ? null : deleteFlagVal.trim();
    }

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getIsDefaultVal() {
		return isDefaultVal;
	}

	public void setIsDefaultVal(String isDefaultVal) {
		this.isDefaultVal = isDefaultVal;
	}

	public String getIsSalary() {
		return isSalary;
	}

	public void setIsSalary(String isSalary) {
		this.isSalary = isSalary;
	}

	public String getIsSalaryVal() {
		return isSalaryVal;
	}

	public void setIsSalaryVal(String isSalaryVal) {
		this.isSalaryVal = isSalaryVal;
	}
    
}