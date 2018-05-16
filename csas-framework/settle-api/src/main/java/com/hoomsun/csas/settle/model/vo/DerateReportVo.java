/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.settle.model.vo;

import java.math.BigDecimal;

/**
 * 作者：liming <br>
 * 创建时间：2018年1月25日 <br>
 * 描述：减免报表
 */
public class DerateReportVo {
	
	private String shouldDate;//应还款时间
	
	private String custType;//客户类型
	
	private String serialId;//进件编号
	
  	private String conNo;//合同编号

 	private String castName;//客户姓名
 	
 	private String cardNo;//身份证号
 	
	private String storeName;// 门店名称

	private String storeId;// 门店id
	  
    private Integer overdueNum; //逾期期数
	
    private Integer overdueDays;//逾期天数

 	private BigDecimal shouldCapi; // 应还本金

 	private BigDecimal shouldInte; 	// 应还利息
 	
 	private BigDecimal shouldPrincipal;//应收本息（本金+利息）
 	  
    private BigDecimal receivePenalty;//应收违约金

    private BigDecimal receiveInterest;//应收罚息
    
    private BigDecimal receivePenaltyInterest;//应收违罚金
    
    private BigDecimal receiveTotalMoney; //逾期应收总额
    
    private BigDecimal receivedMoney;//逾期已收总额
 
    private BigDecimal advanceShould;//提前还款应还金额
    
    private BigDecimal  advanceMoney;//提前还款已还金额
    
    private BigDecimal  advanceReduce;//实际减免金额

    private String applicationDesc;
	

	public String getShouldDate() {
		return shouldDate;
	}

	public void setShouldDate(String shouldDate) {
		this.shouldDate = shouldDate;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public String getConNo() {
		return conNo;
	}

	public void setConNo(String conNo) {
		this.conNo = conNo;
	}

	public String getCastName() {
		return castName;
	}

	public void setCastName(String castName) {
		this.castName = castName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public Integer getOverdueNum() {
		return overdueNum;
	}

	public void setOverdueNum(Integer overdueNum) {
		this.overdueNum = overdueNum;
	}

	public Integer getOverdueDays() {
		return overdueDays;
	}

	public void setOverdueDays(Integer overdueDays) {
		this.overdueDays = overdueDays;
	}

	public BigDecimal getShouldCapi() {
		return shouldCapi;
	}

	public void setShouldCapi(BigDecimal shouldCapi) {
		this.shouldCapi = shouldCapi;
	}

	public BigDecimal getShouldInte() {
		return shouldInte;
	}

	public void setShouldInte(BigDecimal shouldInte) {
		this.shouldInte = shouldInte;
	}

	public BigDecimal getShouldPrincipal() {
		return shouldPrincipal;
	}

	public void setShouldPrincipal(BigDecimal shouldPrincipal) {
		this.shouldPrincipal = shouldPrincipal;
	}

	public BigDecimal getReceivePenalty() {
		return receivePenalty;
	}

	public void setReceivePenalty(BigDecimal receivePenalty) {
		this.receivePenalty = receivePenalty;
	}

	public BigDecimal getReceiveInterest() {
		return receiveInterest;
	}

	public void setReceiveInterest(BigDecimal receiveInterest) {
		this.receiveInterest = receiveInterest;
	}

	public BigDecimal getReceivePenaltyInterest() {
		return receivePenaltyInterest;
	}

	public void setReceivePenaltyInterest(BigDecimal receivePenaltyInterest) {
		this.receivePenaltyInterest = receivePenaltyInterest;
	}

	public BigDecimal getReceiveTotalMoney() {
		return receiveTotalMoney;
	}

	public void setReceiveTotalMoney(BigDecimal receiveTotalMoney) {
		this.receiveTotalMoney = receiveTotalMoney;
	}

	public BigDecimal getReceivedMoney() {
		return receivedMoney;
	}

	public void setReceivedMoney(BigDecimal receivedMoney) {
		this.receivedMoney = receivedMoney;
	}

	public BigDecimal getAdvanceShould() {
		return advanceShould;
	}

	public void setAdvanceShould(BigDecimal advanceShould) {
		this.advanceShould = advanceShould;
	}

	public BigDecimal getAdvanceMoney() {
		return advanceMoney;
	}

	public void setAdvanceMoney(BigDecimal advanceMoney) {
		this.advanceMoney = advanceMoney;
	}

	public BigDecimal getAdvanceReduce() {
		return advanceReduce;
	}

	public void setAdvanceReduce(BigDecimal advanceReduce) {
		this.advanceReduce = advanceReduce;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	
	

	
    
    
	
    
}
