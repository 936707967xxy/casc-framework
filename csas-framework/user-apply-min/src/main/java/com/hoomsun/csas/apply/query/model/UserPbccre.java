package com.hoomsun.csas.apply.query.model;
/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月3日 <br>
 * 描述： 征信
 *
 */
public class UserPbccre {
    private String crId;

    private String addDate;
    
    private String jlycxchgrcx;   //征信查询次数中筛选出近6个月个人查询次数（不含临柜查询）

    private String jlycxchbqsc;   //征信查询次数中筛选出近6个月保前审查次数

    private String jsycxchoutdh;  //征信近3个月内查询次数（不含贷后管理）

    private String zxsycxcs;//征信近3个月内查询次数

    private String zxyycxcssy;//征信近1个月内个人查询次数

    private String zxyycxcs;//征信近1个月内机构查询次数（不含贷后管理）

    private String dkdqyqje;//贷款当前逾期金额

    private String lndkjstyqcs;//最近两年内发放的贷款90天以上逾期总次数

    private String lndkyqys;//最近两年内发放的贷款逾期总月数

    private String xyfzb;//信用负债率

    private String qtdkyh;//其他贷款月还款总额

    private String fyhdkzb;//非银行贷款占比

    private String jsyxzdkbs;//最近3个月新增其他贷款笔数

    private String fdyhqs;//房贷已还期数

    private String fdyh;//房贷月还款总额

    private String fdze;//房贷发放总额

    private String zykdqyqje;//信用卡当前逾期金额

    private String lnxykyjstqzys;//最近两年内发放的信用卡90天以上逾期总次数

    private String lnxykyqzys;//最近两年内发放的信用卡逾期总月数

    private String xyksyzb;//信用卡已使用额度占比

    private String dzxykzged;//单张信用卡最高额度

    private String xyksxze;//信用卡授信总额

    private String sxsc;//授信时长

    private String wtrdbbs;//为他人担保笔数

    private String jstysyqzh;//发生过90天以上逾期的贷款账户数

    private String yqzhs;//发生过逾期的贷款账户数

    private String wjqzh;//未结清/销户账户（购房贷款）

    private String applyId;//

    public String getCrId() {
        return crId;
    }

    public void setCrId(String crId) {
        this.crId = crId == null ? null : crId.trim();
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate == null ? null : addDate.trim();
    }

    public String getZxsycxcs() {
        return zxsycxcs;
    }

    public void setZxsycxcs(String zxsycxcs) {
        this.zxsycxcs = zxsycxcs == null ? null : zxsycxcs.trim();
    }

    public String getZxyycxcssy() {
        return zxyycxcssy;
    }

    public void setZxyycxcssy(String zxyycxcssy) {
        this.zxyycxcssy = zxyycxcssy == null ? null : zxyycxcssy.trim();
    }

    public String getZxyycxcs() {
        return zxyycxcs;
    }

    public void setZxyycxcs(String zxyycxcs) {
        this.zxyycxcs = zxyycxcs == null ? null : zxyycxcs.trim();
    }

    public String getDkdqyqje() {
        return dkdqyqje;
    }

    public void setDkdqyqje(String dkdqyqje) {
        this.dkdqyqje = dkdqyqje == null ? null : dkdqyqje.trim();
    }

    public String getLndkjstyqcs() {
        return lndkjstyqcs;
    }

    public void setLndkjstyqcs(String lndkjstyqcs) {
        this.lndkjstyqcs = lndkjstyqcs == null ? null : lndkjstyqcs.trim();
    }

    public String getLndkyqys() {
        return lndkyqys;
    }

    public void setLndkyqys(String lndkyqys) {
        this.lndkyqys = lndkyqys == null ? null : lndkyqys.trim();
    }

    public String getXyfzb() {
        return xyfzb;
    }

    public void setXyfzb(String xyfzb) {
        this.xyfzb = xyfzb == null ? null : xyfzb.trim();
    }

    public String getQtdkyh() {
        return qtdkyh;
    }

    public void setQtdkyh(String qtdkyh) {
        this.qtdkyh = qtdkyh == null ? null : qtdkyh.trim();
    }

    public String getFyhdkzb() {
        return fyhdkzb;
    }

    public void setFyhdkzb(String fyhdkzb) {
        this.fyhdkzb = fyhdkzb == null ? null : fyhdkzb.trim();
    }

    public String getJsyxzdkbs() {
        return jsyxzdkbs;
    }

    public void setJsyxzdkbs(String jsyxzdkbs) {
        this.jsyxzdkbs = jsyxzdkbs == null ? null : jsyxzdkbs.trim();
    }

    public String getFdyhqs() {
        return fdyhqs;
    }

    public void setFdyhqs(String fdyhqs) {
        this.fdyhqs = fdyhqs == null ? null : fdyhqs.trim();
    }

    public String getFdyh() {
        return fdyh;
    }

    public void setFdyh(String fdyh) {
        this.fdyh = fdyh == null ? null : fdyh.trim();
    }

    public String getFdze() {
        return fdze;
    }

    public void setFdze(String fdze) {
        this.fdze = fdze == null ? null : fdze.trim();
    }

    public String getZykdqyqje() {
        return zykdqyqje;
    }

    public void setZykdqyqje(String zykdqyqje) {
        this.zykdqyqje = zykdqyqje == null ? null : zykdqyqje.trim();
    }

    public String getLnxykyjstqzys() {
        return lnxykyjstqzys;
    }

    public void setLnxykyjstqzys(String lnxykyjstqzys) {
        this.lnxykyjstqzys = lnxykyjstqzys == null ? null : lnxykyjstqzys.trim();
    }

    public String getLnxykyqzys() {
        return lnxykyqzys;
    }

    public void setLnxykyqzys(String lnxykyqzys) {
        this.lnxykyqzys = lnxykyqzys == null ? null : lnxykyqzys.trim();
    }

    public String getXyksyzb() {
        return xyksyzb;
    }

    public void setXyksyzb(String xyksyzb) {
        this.xyksyzb = xyksyzb == null ? null : xyksyzb.trim();
    }

    public String getDzxykzged() {
        return dzxykzged;
    }

    public void setDzxykzged(String dzxykzged) {
        this.dzxykzged = dzxykzged == null ? null : dzxykzged.trim();
    }

    public String getXyksxze() {
        return xyksxze;
    }

    public void setXyksxze(String xyksxze) {
        this.xyksxze = xyksxze == null ? null : xyksxze.trim();
    }

    public String getSxsc() {
        return sxsc;
    }

    public void setSxsc(String sxsc) {
        this.sxsc = sxsc == null ? null : sxsc.trim();
    }

    public String getWtrdbbs() {
        return wtrdbbs;
    }

    public void setWtrdbbs(String wtrdbbs) {
        this.wtrdbbs = wtrdbbs == null ? null : wtrdbbs.trim();
    }

    public String getJstysyqzh() {
        return jstysyqzh;
    }

    public void setJstysyqzh(String jstysyqzh) {
        this.jstysyqzh = jstysyqzh == null ? null : jstysyqzh.trim();
    }

    public String getYqzhs() {
        return yqzhs;
    }

    public void setYqzhs(String yqzhs) {
        this.yqzhs = yqzhs == null ? null : yqzhs.trim();
    }

    public String getWjqzh() {
        return wjqzh;
    }

    public void setWjqzh(String wjqzh) {
        this.wjqzh = wjqzh == null ? null : wjqzh.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

	public String getJlycxchgrcx() {
		return jlycxchgrcx;
	}

	public void setJlycxchgrcx(String jlycxchgrcx) {
		this.jlycxchgrcx = jlycxchgrcx;
	}

	public String getJlycxchbqsc() {
		return jlycxchbqsc;
	}

	public void setJlycxchbqsc(String jlycxchbqsc) {
		this.jlycxchbqsc = jlycxchbqsc;
	}

	public String getJsycxchoutdh() {
		return jsycxchoutdh;
	}

	public void setJsycxchoutdh(String jsycxchoutdh) {
		this.jsycxchoutdh = jsycxchoutdh;
	}
    
    
}