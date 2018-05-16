package com.hoomsun.csas.audit.model;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月31日 <br>
 * 描述： 终审
 */
public class FinalAudit {
    private String finalId;
    private String procInstId;
    private String taskId;
    private Integer finalStatus;
    private Date auditDate;
    private String auditEmpName;
    private String auditEmp;
    private String inHandleOpinion;
    private String handleOpinionVal;
    private Integer handleOpinion;
    private BigDecimal monthPay;
    private BigDecimal approvedAmount;
    private String productPayVal;
    private Integer productPay;
    private BigDecimal productRate;
    private Integer productPeriod;
    private BigDecimal productFeeRate;
    private String productName;
    private String productId;
    private String applyId;
    private String prodAlias;
    private Integer isOnline;
    private Integer rejectType;//拒贷类型
    private String rejectTypeVal;//拒贷类型值
    private Integer custType;//客户类型
    private String custTypeVal;//客户类型值
    private String oneOpinion;//一级审批意见
    private BigDecimal irrVal;//综合年化成本
    public String getFinalId() {
        return finalId;
    }

    public void setFinalId(String finalId) {
        this.finalId = finalId == null ? null : finalId.trim();
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId == null ? null : procInstId.trim();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }


    public Integer getFinalStatus() {
		return finalStatus;
	}

	public void setFinalStatus(Integer finalStatus) {
		this.finalStatus = finalStatus;
	}

	public Integer getProductPay() {
		return productPay;
	}

	public void setProductPay(Integer productPay) {
		this.productPay = productPay;
	}

	public Integer getProductPeriod() {
		return productPeriod;
	}

	public void setProductPeriod(Integer productPeriod) {
		this.productPeriod = productPeriod;
	}

	public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public String getAuditEmpName() {
        return auditEmpName;
    }

    public void setAuditEmpName(String auditEmpName) {
        this.auditEmpName = auditEmpName == null ? null : auditEmpName.trim();
    }

    public String getAuditEmp() {
        return auditEmp;
    }

    public void setAuditEmp(String auditEmp) {
        this.auditEmp = auditEmp == null ? null : auditEmp.trim();
    }

    public String getInHandleOpinion() {
        return inHandleOpinion;
    }

    public void setInHandleOpinion(String inHandleOpinion) {
        this.inHandleOpinion = inHandleOpinion == null ? null : inHandleOpinion.trim();
    }

    public String getHandleOpinionVal() {
        return handleOpinionVal;
    }

    public void setHandleOpinionVal(String handleOpinionVal) {
        this.handleOpinionVal = handleOpinionVal == null ? null : handleOpinionVal.trim();
    }

    public Integer getHandleOpinion() {
		return handleOpinion;
	}

	public void setHandleOpinion(Integer handleOpinion) {
		this.handleOpinion = handleOpinion;
	}

	public BigDecimal getMonthPay() {
        return monthPay;
    }

    public void setMonthPay(BigDecimal monthPay) {
        this.monthPay = monthPay;
    }

    public BigDecimal getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(BigDecimal approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    public String getProductPayVal() {
        return productPayVal;
    }

    public void setProductPayVal(String productPayVal) {
        this.productPayVal = productPayVal == null ? null : productPayVal.trim();
    }


    public BigDecimal getProductRate() {
        return productRate;
    }

    public void setProductRate(BigDecimal productRate) {
        this.productRate = productRate;
    }


    public BigDecimal getProductFeeRate() {
        return productFeeRate;
    }

    public void setProductFeeRate(BigDecimal productFeeRate) {
        this.productFeeRate = productFeeRate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getProdAlias() {
        return prodAlias;
    }

    public void setProdAlias(String prodAlias) {
        this.prodAlias = prodAlias == null ? null : prodAlias.trim();
    }

	public Integer getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}

	public Integer getRejectType() {
		return rejectType;
	}

	public void setRejectType(Integer rejectType) {
		this.rejectType = rejectType;
	}

	public String getRejectTypeVal() {
		return rejectTypeVal;
	}

	public void setRejectTypeVal(String rejectTypeVal) {
		this.rejectTypeVal = rejectTypeVal;
	}

	public Integer getCustType() {
		return custType;
	}

	public void setCustType(Integer custType) {
		this.custType = custType;
	}

	public String getCustTypeVal() {
		return custTypeVal;
	}

	public void setCustTypeVal(String custTypeVal) {
		this.custTypeVal = custTypeVal;
	}

	public String getOneOpinion() {
		return oneOpinion;
	}

	public void setOneOpinion(String oneOpinion) {
		this.oneOpinion = oneOpinion;
	}

	public BigDecimal getIrrVal() {
		return irrVal;
	}

	public void setIrrVal(BigDecimal irrVal) {
		this.irrVal = irrVal;
	}
	
	
	
	
    
    
}