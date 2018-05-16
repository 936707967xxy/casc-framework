package com.hoomsun.csas.sales.api.model.vo;

import java.math.BigDecimal;

public class UserApplyVo {

	private String applyId;

	private String applyDate;

	private Integer auditType;

	private Integer sources;

	private String precessStatusVal;

	private String precessStatus;

	private String precessId;

	private BigDecimal suggestRate;

	private BigDecimal suggestAmount;

	private String productPayVal;

	private Integer productPay;

	private BigDecimal productFeeRate;

	private BigDecimal productRate;

	private Integer applyPeriod;

	private BigDecimal applyAmount;
	
	private BigDecimal agreedAmount;
	
	private String agreedProduct;

	private String productName;

	private String productId;

	private String idAddr;

	private String comTypeVal;

	private Integer comType;

	private BigDecimal incom;

	private String comTel;

	private String detailedAddr;

	private String custComAddr;

	private String custCom;

	private String custMobile;

	private Integer custAge;

	private String custSex;

	private String idTypeVal;

	private Integer idType;

	private String idNumber;

	private String custName;

	private String loanId;

	private String creditSesame;

	private String particlesBorrow;

	private String uuid;

	private String custId;
	private String storeId;
	private String storeName;
	
	private String signconfirmVal;

    private Integer signconfirm;
    
    private Integer conStatus;  //0 未放款  1 放款生效  2  结清    3  提前结清  
    
    private Integer productPeriod;  //审批期数
    
	public UserApplyVo() {
		super();
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public Integer getAuditType() {
		return auditType;
	}

	public void setAuditType(Integer auditType) {
		this.auditType = auditType;
	}

	public Integer getSources() {
		return sources;
	}

	public void setSources(Integer sources) {
		this.sources = sources;
	}

	public String getPrecessStatusVal() {
		return precessStatusVal;
	}

	public void setPrecessStatusVal(String precessStatusVal) {
		this.precessStatusVal = precessStatusVal;
	}

	public String getPrecessStatus() {
		return precessStatus;
	}

	public void setPrecessStatus(String precessStatus) {
		this.precessStatus = precessStatus;
	}

	public String getPrecessId() {
		return precessId;
	}

	public void setPrecessId(String precessId) {
		this.precessId = precessId;
	}

	public BigDecimal getSuggestRate() {
		return suggestRate;
	}

	public void setSuggestRate(BigDecimal suggestRate) {
		this.suggestRate = suggestRate;
	}

	public BigDecimal getSuggestAmount() {
		return suggestAmount;
	}

	public void setSuggestAmount(BigDecimal suggestAmount) {
		this.suggestAmount = suggestAmount;
	}

	public String getProductPayVal() {
		return productPayVal;
	}

	public void setProductPayVal(String productPayVal) {
		this.productPayVal = productPayVal;
	}

	public Integer getProductPay() {
		return productPay;
	}

	public void setProductPay(Integer productPay) {
		this.productPay = productPay;
	}

	public BigDecimal getProductFeeRate() {
		return productFeeRate;
	}

	public void setProductFeeRate(BigDecimal productFeeRate) {
		this.productFeeRate = productFeeRate;
	}

	public BigDecimal getProductRate() {
		return productRate;
	}

	public void setProductRate(BigDecimal productRate) {
		this.productRate = productRate;
	}

	public Integer getApplyPeriod() {
		return applyPeriod;
	}

	public void setApplyPeriod(Integer applyPeriod) {
		this.applyPeriod = applyPeriod;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public BigDecimal getAgreedAmount() {
		return agreedAmount;
	}

	public void setAgreedAmount(BigDecimal agreedAmount) {
		this.agreedAmount = agreedAmount;
	}

	public String getAgreedProduct() {
		return agreedProduct;
	}

	public void setAgreedProduct(String agreedProduct) {
		this.agreedProduct = agreedProduct;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getIdAddr() {
		return idAddr;
	}

	public void setIdAddr(String idAddr) {
		this.idAddr = idAddr;
	}

	public String getComTypeVal() {
		return comTypeVal;
	}

	public void setComTypeVal(String comTypeVal) {
		this.comTypeVal = comTypeVal;
	}

	public Integer getComType() {
		return comType;
	}

	public void setComType(Integer comType) {
		this.comType = comType;
	}

	public BigDecimal getIncom() {
		return incom;
	}

	public void setIncom(BigDecimal incom) {
		this.incom = incom;
	}

	public String getComTel() {
		return comTel;
	}

	public void setComTel(String comTel) {
		this.comTel = comTel;
	}

	public String getDetailedAddr() {
		return detailedAddr;
	}

	public void setDetailedAddr(String detailedAddr) {
		this.detailedAddr = detailedAddr;
	}

	public String getCustComAddr() {
		return custComAddr;
	}

	public void setCustComAddr(String custComAddr) {
		this.custComAddr = custComAddr;
	}

	public String getCustCom() {
		return custCom;
	}

	public void setCustCom(String custCom) {
		this.custCom = custCom;
	}

	public String getCustMobile() {
		return custMobile;
	}

	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}

	public Integer getCustAge() {
		return custAge;
	}

	public void setCustAge(Integer custAge) {
		this.custAge = custAge;
	}

	public String getCustSex() {
		return custSex;
	}

	public void setCustSex(String custSex) {
		this.custSex = custSex;
	}

	public String getIdTypeVal() {
		return idTypeVal;
	}

	public void setIdTypeVal(String idTypeVal) {
		this.idTypeVal = idTypeVal;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getCreditSesame() {
		return creditSesame;
	}

	public void setCreditSesame(String creditSesame) {
		this.creditSesame = creditSesame;
	}

	public String getParticlesBorrow() {
		return particlesBorrow;
	}

	public void setParticlesBorrow(String particlesBorrow) {
		this.particlesBorrow = particlesBorrow;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getSignconfirmVal() {
		return signconfirmVal;
	}

	public void setSignconfirmVal(String signconfirmVal) {
		this.signconfirmVal = signconfirmVal;
	}

	public Integer getSignconfirm() {
		return signconfirm;
	}

	public void setSignconfirm(Integer signconfirm) {
		this.signconfirm = signconfirm;
	}

	public Integer getConStatus() {
		return conStatus;
	}

	public void setConStatus(Integer conStatus) {
		this.conStatus = conStatus;
	}

	public Integer getProductPeriod() {
		return productPeriod;
	}

	public void setProductPeriod(Integer productPeriod) {
		this.productPeriod = productPeriod;
	}

	
    
    
}
