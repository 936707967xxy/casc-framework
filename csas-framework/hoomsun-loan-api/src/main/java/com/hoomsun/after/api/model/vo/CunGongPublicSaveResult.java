/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.vo;

import java.util.Date;

import com.hoomsun.after.api.util.excel.secode.annotation.ExcelField;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年4月26日 <br>
 * 描述：存公记录响应参数
 */
public class CunGongPublicSaveResult {

	private String id;

    private String etydat;

    private String etytim;

    private String transactionDate;

    private String trscod;

    private String naryur;

    private String trsamtc;

    private String amtcdr;

    private String trsblv;

    private String refnbr;

    private String reqnbr;

    private String busnam;

    private String rpynam;

    private String rpyacc;

    private String rpybnk;

    private String rpyadr;

    private String narext;

    private String trsamt;

    private String creatTime;

    private String corporateBankAccount;

    private String dataSources;

    private String verificationStatus;

    private String castId;

    private String castName;

    private String verificationPeopleId;

    private String verificationPeopleName;

    private String verificationPeopleDate;

    private String loanId;

    private String stream;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@ExcelField(title="交易日", align=1, sort=1)
	public String getEtydat() {
		return etydat;
	}

	public void setEtydat(String etydat) {
		this.etydat = etydat;
	}
	@ExcelField(title="交易时间", align=1, sort=2)
	public String getEtytim() {
		return etytim;
	}

	public void setEtytim(String etytim) {
		this.etytim = etytim;
	}
	@ExcelField(title="交易日期", align=1, sort=3)
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	@ExcelField(title="交易类型", align=1, sort=4)
	public String getTrscod() {
		return trscod;
	}
	public void setTrscod(String trscod) {
		this.trscod = trscod;
	}
	@ExcelField(title="摘要", align=1, sort=26)
	public String getNaryur() {
		return naryur;
	}
	public void setNaryur(String naryur) {
		this.naryur = naryur;
	}
	@ExcelField(title="贷方金额", align=1, sort=5)
	public String getTrsamtc() {
		return trsamtc;
	}
	public void setTrsamtc(String trsamtc) {
		this.trsamtc = trsamtc;
	}
	@ExcelField(title="借贷标记", align=1, sort=6)
	public String getAmtcdr() {
		return amtcdr;
	}
	public void setAmtcdr(String amtcdr) {
		this.amtcdr = amtcdr;
	}
	@ExcelField(title="公户余额", align=1, sort=7)
	public String getTrsblv() {
		return trsblv;
	}
	public void setTrsblv(String trsblv) {
		this.trsblv = trsblv;
	}
	@ExcelField(title="流水号", align=1, sort=8)
	public String getRefnbr() {
		return refnbr;
	}
	public void setRefnbr(String refnbr) {
		this.refnbr = refnbr;
	}
	@ExcelField(title="流程实例号", align=1, sort=9)
	public String getReqnbr() {
		return reqnbr;
	}
	public void setReqnbr(String reqnbr) {
		this.reqnbr = reqnbr;
	}
	@ExcelField(title="业务名称", align=1, sort=10)
	public String getBusnam() {
		return busnam;
	}
	public void setBusnam(String busnam) {
		this.busnam = busnam;
	}
	@ExcelField(title="收/付方名称", align=1, sort=11)
	public String getRpynam() {
		return rpynam;
	}
	public void setRpynam(String rpynam) {
		this.rpynam = rpynam;
	}
	@ExcelField(title="收/付方帐号", align=1, sort=12)
	public String getRpyacc() {
		return rpyacc;
	}
	public void setRpyacc(String rpyacc) {
		this.rpyacc = rpyacc;
	}
	@ExcelField(title="收/付方开户行名", align=1, sort=13)
	public String getRpybnk() {
		return rpybnk;
	}
	public void setRpybnk(String rpybnk) {
		this.rpybnk = rpybnk;
	}
	@ExcelField(title="收/付方开户行地址", align=1, sort=14)
	public String getRpyadr() {
		return rpyadr;
	}
	public void setRpyadr(String rpyadr) {
		this.rpyadr = rpyadr;
	}
	@ExcelField(title="扩展摘要", align=1, sort=15)
	public String getNarext() {
		return narext;
	}
	public void setNarext(String narext) {
		this.narext = narext;
	}
	@ExcelField(title="交易金额", align=1, sort=16)
	public String getTrsamt() {
		return trsamt;
	}
	public void setTrsamt(String trsamt) {
		this.trsamt = trsamt;
	}
	@ExcelField(title="创建时间", align=1, sort=17)
	public String getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
	@ExcelField(title="对应公户", align=1, sort=18)
	public String getCorporateBankAccount() {
		return corporateBankAccount;
	}
	public void setCorporateBankAccount(String corporateBankAccount) {
		this.corporateBankAccount = corporateBankAccount;
	}
	@ExcelField(title="数据来源", align=1, sort=19)
	public String getDataSources() {
		return dataSources;
	}
	public void setDataSources(String dataSources) {
		this.dataSources = dataSources;
	}
	@ExcelField(title="是否认领", align=1, sort=20)
	public String getVerificationStatus() {
		return verificationStatus;
	}
	public void setVerificationStatus(String verificationStatus) {
		this.verificationStatus = verificationStatus;
	}
	@ExcelField(title="客户ID", align=1, sort=21)
	public String getCastId() {
		return castId;
	}
	public void setCastId(String castId) {
		this.castId = castId;
	}
	@ExcelField(title="客户姓名", align=1, sort=22)
	public String getCastName() {
		return castName;
	}
	public void setCastName(String castName) {
		this.castName = castName;
	}
	@ExcelField(title="认领人ID", align=1, sort=23)
	public String getVerificationPeopleId() {
		return verificationPeopleId;
	}
	public void setVerificationPeopleId(String verificationPeopleId) {
		this.verificationPeopleId = verificationPeopleId;
	}
	@ExcelField(title="认领人姓名", align=1, sort=24)
	public String getVerificationPeopleName() {
		return verificationPeopleName;
	}
	public void setVerificationPeopleName(String verificationPeopleName) {
		this.verificationPeopleName = verificationPeopleName;
	}
	@ExcelField(title="认领时客户当前所在进件编号", align=1, sort=27)
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}
	@ExcelField(title="认领时间", align=1, sort=25)
	public String getVerificationPeopleDate() {
		return verificationPeopleDate;
	}

	public void setVerificationPeopleDate(String verificationPeopleDate) {
		this.verificationPeopleDate = verificationPeopleDate;
	}
}
