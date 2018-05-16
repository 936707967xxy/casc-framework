package com.hoomsun.csas.sales.api.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
/**
 * 预约申请表
 * @author liming
 *
 */
public class Intention {
    private String ordplyId;//主键

    private String applyid;//预约申请号

    private String loanid;//业务申请编号

    private Timestamp applydate;//预约日期

    private String custname;//客户姓名

    private String custid;//客户编号

    private String sex;//性别

    private String sexval;

    private String idcerttype;//证件类型

    private String idcerttypeval;

    private String paperid;//证件号码

    private BigDecimal age;//年龄

    private String certvaliddate;//证件有效期

    private String mobile;//手机号

    private String prodname;//产品名称

    private String prodid;//产品id

    private String applychan;//受理渠道

    private String applychanexp;//受理渠道说明

    private String appoperid;//业务员编号

    private String appopername;//业务员姓名

    private String appoperphone;//业务员手机号

    private String source;//客户来源

    private String sourceval;//客户来源_值

    private String operid;//客服编号

    private String orgid;//门店编号

    private String deptid;//机构主键

    private String leaderid;//团队经理编号

    private String teamid;//团队编号

    private String deptname;//团队名称

    private String leadername;//团队经理名称

    private String loanpurpose;//借款用途

    private String loanpurposeval;//借款用途值

    private String loanuseexp;//其他用途说明

    private Long loanquota;//申请贷款额度

    private String loanterms;//贷款申请期限

    private Long maxretuamt;//最高月还款额

    private String appliststat;//预约单据状态

    private String appliststatval;//预约单据状态

    private String lordrefucode;//拒贷原因码

    private String lordrefucause;//拒贷原因

    private String otherrefunote;//其他拒绝原因说明

    private String lastchgdate;//最后变更日期

    private String isroundloan;//是否循环贷（否）备注：0 循环  1 非循环

    private String isroundloanval;//是否循环贷

    private String oloanid;//历史进件单号

    private String applyststus;//申请状态

    private Long approvedamount;//批核金额

    private String approvedproduct;//批核产品

    private Long contractamount;//合同金额
    
    private String createEmployee;//录入人员

    private String createEmployeeVal;//录入人员
    
    private String orgname;//门店名称
    
    public String getOrdplyId() {
        return ordplyId;
    }

    public void setOrdplyId(String ordplyId) {
        this.ordplyId = ordplyId == null ? null : ordplyId.trim();
    }

    public String getApplyid() {
        return applyid;
    }

    public void setApplyid(String applyid) {
        this.applyid = applyid == null ? null : applyid.trim();
    }

    public String getLoanid() {
        return loanid;
    }

    public void setLoanid(String loanid) {
        this.loanid = loanid == null ? null : loanid.trim();
    }

    public Timestamp getApplydate() {
        return applydate;
    }

    public void setApplydate(Timestamp applydate) {
        this.applydate = applydate;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname == null ? null : custname.trim();
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid == null ? null : custid.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getSexval() {
        return sexval;
    }

    public void setSexval(String sexval) {
        this.sexval = sexval == null ? null : sexval.trim();
    }

    public String getIdcerttype() {
        return idcerttype;
    }

    public void setIdcerttype(String idcerttype) {
        this.idcerttype = idcerttype == null ? null : idcerttype.trim();
    }

    public String getIdcerttypeval() {
        return idcerttypeval;
    }

    public void setIdcerttypeval(String idcerttypeval) {
        this.idcerttypeval = idcerttypeval == null ? null : idcerttypeval.trim();
    }

    public String getPaperid() {
        return paperid;
    }

    public void setPaperid(String paperid) {
        this.paperid = paperid == null ? null : paperid.trim();
    }

    public BigDecimal getAge() {
        return age;
    }

    public void setAge(BigDecimal age) {
        this.age = age;
    }

    public String getCertvaliddate() {
        return certvaliddate;
    }

    public void setCertvaliddate(String certvaliddate) {
        this.certvaliddate = certvaliddate == null ? null : certvaliddate.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getProdname() {
        return prodname;
    }

    public void setProdname(String prodname) {
        this.prodname = prodname == null ? null : prodname.trim();
    }

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid == null ? null : prodid.trim();
    }

    public String getApplychan() {
        return applychan;
    }

    public void setApplychan(String applychan) {
        this.applychan = applychan == null ? null : applychan.trim();
    }

    public String getApplychanexp() {
        return applychanexp;
    }

    public void setApplychanexp(String applychanexp) {
        this.applychanexp = applychanexp == null ? null : applychanexp.trim();
    }

    public String getAppoperid() {
        return appoperid;
    }

    public void setAppoperid(String appoperid) {
        this.appoperid = appoperid == null ? null : appoperid.trim();
    }

    public String getAppopername() {
        return appopername;
    }

    public void setAppopername(String appopername) {
        this.appopername = appopername == null ? null : appopername.trim();
    }

    public String getAppoperphone() {
        return appoperphone;
    }

    public void setAppoperphone(String appoperphone) {
        this.appoperphone = appoperphone == null ? null : appoperphone.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getSourceval() {
        return sourceval;
    }

    public void setSourceval(String sourceval) {
        this.sourceval = sourceval == null ? null : sourceval.trim();
    }

    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid == null ? null : operid.trim();
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid == null ? null : orgid.trim();
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid == null ? null : deptid.trim();
    }

    public String getLeaderid() {
        return leaderid;
    }

    public void setLeaderid(String leaderid) {
        this.leaderid = leaderid == null ? null : leaderid.trim();
    }

    public String getTeamid() {
        return teamid;
    }

    public void setTeamid(String teamid) {
        this.teamid = teamid == null ? null : teamid.trim();
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname == null ? null : deptname.trim();
    }

    public String getLeadername() {
        return leadername;
    }

    public void setLeadername(String leadername) {
        this.leadername = leadername == null ? null : leadername.trim();
    }

    public String getLoanpurpose() {
        return loanpurpose;
    }

    public void setLoanpurpose(String loanpurpose) {
        this.loanpurpose = loanpurpose == null ? null : loanpurpose.trim();
    }

    public String getLoanpurposeval() {
        return loanpurposeval;
    }

    public void setLoanpurposeval(String loanpurposeval) {
        this.loanpurposeval = loanpurposeval == null ? null : loanpurposeval.trim();
    }

    public String getLoanuseexp() {
        return loanuseexp;
    }

    public void setLoanuseexp(String loanuseexp) {
        this.loanuseexp = loanuseexp == null ? null : loanuseexp.trim();
    }

    public Long getLoanquota() {
        return loanquota;
    }

    public void setLoanquota(Long loanquota) {
        this.loanquota = loanquota;
    }

    public String getLoanterms() {
        return loanterms;
    }

    public void setLoanterms(String loanterms) {
        this.loanterms = loanterms == null ? null : loanterms.trim();
    }

    public Long getMaxretuamt() {
        return maxretuamt;
    }

    public void setMaxretuamt(Long maxretuamt) {
        this.maxretuamt = maxretuamt;
    }

    public String getAppliststat() {
        return appliststat;
    }

    public void setAppliststat(String appliststat) {
        this.appliststat = appliststat == null ? null : appliststat.trim();
    }

    public String getAppliststatval() {
        return appliststatval;
    }

    public void setAppliststatval(String appliststatval) {
        this.appliststatval = appliststatval == null ? null : appliststatval.trim();
    }

    public String getLordrefucode() {
        return lordrefucode;
    }

    public void setLordrefucode(String lordrefucode) {
        this.lordrefucode = lordrefucode == null ? null : lordrefucode.trim();
    }

    public String getLordrefucause() {
        return lordrefucause;
    }

    public void setLordrefucause(String lordrefucause) {
        this.lordrefucause = lordrefucause == null ? null : lordrefucause.trim();
    }

    public String getOtherrefunote() {
        return otherrefunote;
    }

    public void setOtherrefunote(String otherrefunote) {
        this.otherrefunote = otherrefunote == null ? null : otherrefunote.trim();
    }

    public String getLastchgdate() {
        return lastchgdate;
    }

    public void setLastchgdate(String lastchgdate) {
        this.lastchgdate = lastchgdate == null ? null : lastchgdate.trim();
    }

    public String getIsroundloan() {
        return isroundloan;
    }

    public void setIsroundloan(String isroundloan) {
        this.isroundloan = isroundloan == null ? null : isroundloan.trim();
    }

    public String getIsroundloanval() {
        return isroundloanval;
    }

    public void setIsroundloanval(String isroundloanval) {
        this.isroundloanval = isroundloanval == null ? null : isroundloanval.trim();
    }

    public String getOloanid() {
        return oloanid;
    }

    public void setOloanid(String oloanid) {
        this.oloanid = oloanid == null ? null : oloanid.trim();
    }

    public String getApplyststus() {
        return applyststus;
    }

    public void setApplyststus(String applyststus) {
        this.applyststus = applyststus == null ? null : applyststus.trim();
    }

    public Long getApprovedamount() {
        return approvedamount;
    }

    public void setApprovedamount(Long approvedamount) {
        this.approvedamount = approvedamount;
    }

    public String getApprovedproduct() {
        return approvedproduct;
    }

    public void setApprovedproduct(String approvedproduct) {
        this.approvedproduct = approvedproduct == null ? null : approvedproduct.trim();
    }

    public Long getContractamount() {
        return contractamount;
    }

    public void setContractamount(Long contractamount) {
        this.contractamount = contractamount;
    }
    
    public String getCreateEmployee() {
        return createEmployee;
    }

    public void setCreateEmployee(String createEmployee) {
        this.createEmployee = createEmployee == null ? null : createEmployee.trim();
    }
    public String getCreateEmployeeVal() {
        return createEmployeeVal;
    }

    public void setCreateEmployeeVal(String createEmployeeVal) {
        this.createEmployeeVal = createEmployeeVal == null ? null : createEmployeeVal.trim();
    }
    
    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname == null ? null : orgname.trim();
    }
}