package com.hoomsun.after.api.model.table;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
/**
 * 
 * 作者：屈楠 <br>
 * 创建时间：2018年2月27日 <br>
 * 描述：HS_AFTER_PUBLIC_SAVE存公表
 *
 */

public class PublicSave {
   
	
	
	private String id;

	//交易日
    private String etydat;

    //交易时间
    private String etytim;

    //交易日期
    private Date transactionDate;

    //交易类型
    private String trscod;

    //摘要
    private String naryur;

    //贷方金额(存公存入金额)
    private BigDecimal trsamtc;

    //借贷标记(C:收入，D:支出)
    private String amtcdr;

    //公户余额
    private BigDecimal trsblv;

    //流水号
    private String refnbr;

    //流程实例号
    private String reqnbr;

    //业务名称
    private String busnam;

    //收/付方名称（用户姓名）
    private String rpynam;

    //收/付方帐号（银行卡号）
    private String rpyacc;

    // 收/付方开户行名（银行名称）
    private String rpybnk;

    //收/付方开户行地址（银行开户行详细地址）
    private String rpyadr;

    //扩展摘要
    private String narext;

    //交易金额
    private BigDecimal trsamt;

    //创建时间
    private Date creatTime;

    //对应公户（0707，7180）
    private String corporateBankAccount;

    //数据来源（银企直链，结算导入）
    private String dataSources;

    //是否认领(0:未认领；1：已认领)
    private String verificationStatus;

    //客户ID
    private String castId;

    //客户姓名
    private String castName;

    //认领人ID
    private String verificationPeopleId;

    //认领人姓名
    private String verificationPeopleName;

    //认领时间
    private Date verificationPeopleDate;

    //认领时客户当前所在进件编号
    private String loanId;

    //认领时客户所出期次
    private Integer stream;
    
    /**
	 * 页码
	 */
	private int page;
	/**
	 * 每页条数
	 */
	private int pageSize;
	/**
	 * 导出文件专用
	 */
	private HttpServletResponse response;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getEtydat() {
        return etydat;
    }

    public void setEtydat(String etydat) {
        this.etydat = etydat == null ? null : etydat.trim();
    }

    public String getEtytim() {
        return etytim;
    }

    public void setEtytim(String etytim) {
        this.etytim = etytim == null ? null : etytim.trim();
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTrscod() {
        return trscod;
    }

    public void setTrscod(String trscod) {
        this.trscod = trscod == null ? null : trscod.trim();
    }

    public String getNaryur() {
        return naryur;
    }

    public void setNaryur(String naryur) {
        this.naryur = naryur == null ? null : naryur.trim();
    }

    public BigDecimal getTrsamtc() {
        return trsamtc;
    }

    public void setTrsamtc(BigDecimal trsamtc) {
        this.trsamtc = trsamtc;
    }

    public String getAmtcdr() {
        return amtcdr;
    }

    public void setAmtcdr(String amtcdr) {
        this.amtcdr = amtcdr == null ? null : amtcdr.trim();
    }

    public BigDecimal getTrsblv() {
        return trsblv;
    }

    public void setTrsblv(BigDecimal trsblv) {
        this.trsblv = trsblv;
    }

    public String getRefnbr() {
        return refnbr;
    }

    public void setRefnbr(String refnbr) {
        this.refnbr = refnbr == null ? null : refnbr.trim();
    }

    public String getReqnbr() {
        return reqnbr;
    }

    public void setReqnbr(String reqnbr) {
        this.reqnbr = reqnbr == null ? null : reqnbr.trim();
    }

    public String getBusnam() {
        return busnam;
    }

    public void setBusnam(String busnam) {
        this.busnam = busnam == null ? null : busnam.trim();
    }

    public String getRpynam() {
        return rpynam;
    }

    public void setRpynam(String rpynam) {
        this.rpynam = rpynam == null ? null : rpynam.trim();
    }

    public String getRpyacc() {
        return rpyacc;
    }

    public void setRpyacc(String rpyacc) {
        this.rpyacc = rpyacc == null ? null : rpyacc.trim();
    }

    public String getRpybnk() {
        return rpybnk;
    }

    public void setRpybnk(String rpybnk) {
        this.rpybnk = rpybnk == null ? null : rpybnk.trim();
    }

    public String getRpyadr() {
        return rpyadr;
    }

    public void setRpyadr(String rpyadr) {
        this.rpyadr = rpyadr == null ? null : rpyadr.trim();
    }

    public String getNarext() {
        return narext;
    }

    public void setNarext(String narext) {
        this.narext = narext == null ? null : narext.trim();
    }

    public BigDecimal getTrsamt() {
        return trsamt;
    }

    public void setTrsamt(BigDecimal trsamt) {
        this.trsamt = trsamt;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getCorporateBankAccount() {
        return corporateBankAccount;
    }

    public void setCorporateBankAccount(String corporateBankAccount) {
        this.corporateBankAccount = corporateBankAccount == null ? null : corporateBankAccount.trim();
    }

    public String getDataSources() {
        return dataSources;
    }

    public void setDataSources(String dataSources) {
        this.dataSources = dataSources == null ? null : dataSources.trim();
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus == null ? null : verificationStatus.trim();
    }

    public String getCastId() {
        return castId;
    }

    public void setCastId(String castId) {
        this.castId = castId == null ? null : castId.trim();
    }

    public String getCastName() {
        return castName;
    }

    public void setCastName(String castName) {
        this.castName = castName == null ? null : castName.trim();
    }

    public String getVerificationPeopleId() {
        return verificationPeopleId;
    }

    public void setVerificationPeopleId(String verificationPeopleId) {
        this.verificationPeopleId = verificationPeopleId == null ? null : verificationPeopleId.trim();
    }

    public String getVerificationPeopleName() {
        return verificationPeopleName;
    }

    public void setVerificationPeopleName(String verificationPeopleName) {
        this.verificationPeopleName = verificationPeopleName == null ? null : verificationPeopleName.trim();
    }

    public Date getVerificationPeopleDate() {
        return verificationPeopleDate;
    }

    public void setVerificationPeopleDate(Date verificationPeopleDate) {
        this.verificationPeopleDate = verificationPeopleDate;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId == null ? null : loanId.trim();
    }

    public Integer getStream() {
        return stream;
    }

    public void setStream(Integer stream) {
        this.stream = stream;
    }

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
}