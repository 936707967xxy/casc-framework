package com.hoomsun.csas.sales.api.model;

public class UserPbccre {
    private String crId;

    private String creditTime;  //报告时间        征信上的报告时间（精确到秒）

    private String jstysyqzh;  //出现90天逾期账户数

    private String isDz; //出现冻结、呆账、止付       有或无

    private String isDqyq;//是否有当前逾期的账户      有或无

    private String hjkSum;//贷记卡账户总数     除准贷记卡外，所有状态的贷记卡的张数

    private String hjkUse;//贷记卡未销户账户数         未销户账户数

    private String hjkAmt;//贷记卡授信总额              仅指使用中贷记卡（不含销户和未激活）

    private String hjkUserAmt;//贷记卡已使用额度占比          正常使用中的贷记卡已使用总额度/授信总额

    private String hjkYqRate;//贷记卡单张最高逾期比例         仅指使用中的贷记卡（不含销户和未激活），单卡逾期比例=单卡逾期月数/（发放至截至的月数），选取逾期比例最高的卡；发放至截至的月数超过五年的，按照60个月计算

    private String hjkYqAmt;//贷记卡当前逾期总金额                 所有贷记卡的当前逾期总金额     

    private String hjkYqSum;//贷记卡贷记卡逾期账户数            所有状态下逾期的贷记卡账户数

    private String hjkDqyqSum;//贷记卡贷记卡当前逾期账户数        房贷笔数

    private String fdSum;//房贷笔数          含已结清和在还状态的房贷贷款

    private String fdAmt;//房贷余额         在还状态的房贷贷款余额

    private String fdYh;//房贷月还金额      在还中的所有房贷月还总额

    private String fdYhqs;//房贷已还款期数             自发放到截至时间正常还款中最长的房贷期数

    private String otherSum;//其他贷款笔数           含已结清和在还状态的贷款   

    private String otherAmt;//其他贷款余额            在还状态的贷款余额

    private String otherYh;//其他贷款月还金额       在还中的所有贷款月还总额

    private String wldAmt;//微粒贷额度                     ①如有还款中的贷款，选择发放时间距截至时间最短的正常还款中的微粒贷额度，②如均已经结清则选结清时间距截至时间在1年内的微粒贷额度（如有多笔，选取发放时间距截至时间最短的微粒贷）；

    private String wldTime;//微粒贷时长                 自发放到截至时间正常还款中的期数（暂定）

    private String wldOverTime;//最近一笔微粒贷结清时间             结清时间距报告时间最近的一笔微粒贷的结清时间

    private String wldSum;//微粒贷笔数（包含结清）

    private String dkYqRate;//贷款逾期最高比例       贷款逾期期数/（发放至截至或结清时间的月数），选取逾期比例最高的贷款比例；若放时间距截至或结清时间超过五年的，按照60个月计算

    private String dkFeiyinhang;//未结清的非银行授信笔数         未结清的无银行字样的贷款笔数

    private String yiyueDywwSum;//近一个月≥5万大额到期笔数       报告时间之后一个月内大额到期的贷款笔数

    private String yiyueDywwAmt;//近一个月≥5万大额到期总额       报告时间之后一个月内大额到期的贷款到期总金额

    private String yqRateXyln;//发放时间小于2年的贷款逾期比例    所有状态的贷款逾期期数/（发放至截至或结清时间的月数），选取逾期比例最高的贷款比例；若放时间距截至或结清时间超过五年的，按照60个月计算

    private String dkYqSum;//贷款逾期账户数           所有状态的贷款

    private String yqRateDyln;//发放时间小于2年的贷款逾期比例                     所有状态的贷款逾期期数/（发放至截至或结清时间的月数），选取逾期比例最高的贷款比例；若放时间距截至或结清时间超过五年的，按照60个月计算

    private String dkYqAmt;//当前逾期总金额                所有贷款的当前逾期总金额

    private String dkDqyqSum;//贷款当前逾期账户数

    private String jlycxchgrcx;//近6个月个人网查次数（临柜除外）       

    private String jlycxchbqsc;//近6个月保前查询次数                                   查询时间距报告时间6个月（含）内的保前查询次数

    private String jlycxchdhcw;//近6个月机构查询次数（贷后管理除外）           贷后管理除外，查询时间距报告时间6个月（含）内的次数

    private String zxyycxcs;//征信近1个月查询次数（贷后管理除外）                  贷后管理除外，查询时间距报告时间1个月（含）内的保前查询次数（包含机构查询和个人查询）
    
    private Object addDate;

    private String applyId;
    
    private String liabilitiesLoan;  //负债：   贷款***（征信）

    private String liabilitiesCard;  //负债：信用卡***（征信）； 

    private String liabilitiesCurrent;//当前负债金额： 未结清贷款月还 + 信用卡账单金额*10%

    private String propertyTypeZx;//房产类型：按揭（商贷）/按揭（公积金）（通过征信查询判断）
    
    private UserPbccrcHtml userPbccrcHtml;

    public String getCrId() {
        return crId;
    }

    public void setCrId(String crId) {
        this.crId = crId == null ? null : crId.trim();
    }

    public String getCreditTime() {
        return creditTime;
    }

    public void setCreditTime(String creditTime) {
        this.creditTime = creditTime == null ? null : creditTime.trim();
    }

    public String getJstysyqzh() {
        return jstysyqzh;
    }

    public void setJstysyqzh(String jstysyqzh) {
        this.jstysyqzh = jstysyqzh == null ? null : jstysyqzh.trim();
    }

    public String getIsDz() {
        return isDz;
    }

    public void setIsDz(String isDz) {
        this.isDz = isDz == null ? null : isDz.trim();
    }

    public String getIsDqyq() {
        return isDqyq;
    }

    public void setIsDqyq(String isDqyq) {
        this.isDqyq = isDqyq == null ? null : isDqyq.trim();
    }

    public String getHjkSum() {
        return hjkSum;
    }

    public void setHjkSum(String hjkSum) {
        this.hjkSum = hjkSum == null ? null : hjkSum.trim();
    }

    public String getHjkUse() {
        return hjkUse;
    }

    public void setHjkUse(String hjkUse) {
        this.hjkUse = hjkUse == null ? null : hjkUse.trim();
    }

    public String getHjkAmt() {
        return hjkAmt;
    }

    public void setHjkAmt(String hjkAmt) {
        this.hjkAmt = hjkAmt == null ? null : hjkAmt.trim();
    }

    public String getHjkUserAmt() {
        return hjkUserAmt;
    }

    public void setHjkUserAmt(String hjkUserAmt) {
        this.hjkUserAmt = hjkUserAmt == null ? null : hjkUserAmt.trim();
    }

    public String getHjkYqRate() {
        return hjkYqRate;
    }

    public void setHjkYqRate(String hjkYqRate) {
        this.hjkYqRate = hjkYqRate == null ? null : hjkYqRate.trim();
    }

    public String getHjkYqAmt() {
        return hjkYqAmt;
    }

    public void setHjkYqAmt(String hjkYqAmt) {
        this.hjkYqAmt = hjkYqAmt == null ? null : hjkYqAmt.trim();
    }

    public String getHjkYqSum() {
        return hjkYqSum;
    }

    public void setHjkYqSum(String hjkYqSum) {
        this.hjkYqSum = hjkYqSum == null ? null : hjkYqSum.trim();
    }

    public String getHjkDqyqSum() {
        return hjkDqyqSum;
    }

    public void setHjkDqyqSum(String hjkDqyqSum) {
        this.hjkDqyqSum = hjkDqyqSum == null ? null : hjkDqyqSum.trim();
    }

    public String getFdSum() {
        return fdSum;
    }

    public void setFdSum(String fdSum) {
        this.fdSum = fdSum == null ? null : fdSum.trim();
    }

    public String getFdAmt() {
        return fdAmt;
    }

    public void setFdAmt(String fdAmt) {
        this.fdAmt = fdAmt == null ? null : fdAmt.trim();
    }

    public String getFdYh() {
        return fdYh;
    }

    public void setFdYh(String fdYh) {
        this.fdYh = fdYh == null ? null : fdYh.trim();
    }

    public String getFdYhqs() {
        return fdYhqs;
    }

    public void setFdYhqs(String fdYhqs) {
        this.fdYhqs = fdYhqs == null ? null : fdYhqs.trim();
    }

    public String getOtherSum() {
        return otherSum;
    }

    public void setOtherSum(String otherSum) {
        this.otherSum = otherSum == null ? null : otherSum.trim();
    }

    public String getOtherAmt() {
        return otherAmt;
    }

    public void setOtherAmt(String otherAmt) {
        this.otherAmt = otherAmt == null ? null : otherAmt.trim();
    }

    public String getOtherYh() {
        return otherYh;
    }

    public void setOtherYh(String otherYh) {
        this.otherYh = otherYh == null ? null : otherYh.trim();
    }

    public String getWldAmt() {
        return wldAmt;
    }

    public void setWldAmt(String wldAmt) {
        this.wldAmt = wldAmt == null ? null : wldAmt.trim();
    }

    public String getWldTime() {
        return wldTime;
    }

    public void setWldTime(String wldTime) {
        this.wldTime = wldTime == null ? null : wldTime.trim();
    }

    public String getWldOverTime() {
        return wldOverTime;
    }

    public void setWldOverTime(String wldOverTime) {
        this.wldOverTime = wldOverTime == null ? null : wldOverTime.trim();
    }

    public String getWldSum() {
        return wldSum;
    }

    public void setWldSum(String wldSum) {
        this.wldSum = wldSum == null ? null : wldSum.trim();
    }

    public String getDkYqRate() {
        return dkYqRate;
    }

    public void setDkYqRate(String dkYqRate) {
        this.dkYqRate = dkYqRate == null ? null : dkYqRate.trim();
    }

    public String getDkFeiyinhang() {
        return dkFeiyinhang;
    }

    public void setDkFeiyinhang(String dkFeiyinhang) {
        this.dkFeiyinhang = dkFeiyinhang == null ? null : dkFeiyinhang.trim();
    }

    public String getYiyueDywwSum() {
        return yiyueDywwSum;
    }

    public void setYiyueDywwSum(String yiyueDywwSum) {
        this.yiyueDywwSum = yiyueDywwSum == null ? null : yiyueDywwSum.trim();
    }

    public String getYiyueDywwAmt() {
        return yiyueDywwAmt;
    }

    public void setYiyueDywwAmt(String yiyueDywwAmt) {
        this.yiyueDywwAmt = yiyueDywwAmt == null ? null : yiyueDywwAmt.trim();
    }

    public String getYqRateXyln() {
        return yqRateXyln;
    }

    public void setYqRateXyln(String yqRateXyln) {
        this.yqRateXyln = yqRateXyln == null ? null : yqRateXyln.trim();
    }

    public String getDkYqSum() {
        return dkYqSum;
    }

    public void setDkYqSum(String dkYqSum) {
        this.dkYqSum = dkYqSum == null ? null : dkYqSum.trim();
    }

    public String getYqRateDyln() {
        return yqRateDyln;
    }

    public void setYqRateDyln(String yqRateDyln) {
        this.yqRateDyln = yqRateDyln == null ? null : yqRateDyln.trim();
    }

    public String getDkYqAmt() {
        return dkYqAmt;
    }

    public void setDkYqAmt(String dkYqAmt) {
        this.dkYqAmt = dkYqAmt == null ? null : dkYqAmt.trim();
    }

    public String getDkDqyqSum() {
        return dkDqyqSum;
    }

    public void setDkDqyqSum(String dkDqyqSum) {
        this.dkDqyqSum = dkDqyqSum == null ? null : dkDqyqSum.trim();
    }

    public String getJlycxchgrcx() {
        return jlycxchgrcx;
    }

    public void setJlycxchgrcx(String jlycxchgrcx) {
        this.jlycxchgrcx = jlycxchgrcx == null ? null : jlycxchgrcx.trim();
    }

    public String getJlycxchbqsc() {
        return jlycxchbqsc;
    }

    public void setJlycxchbqsc(String jlycxchbqsc) {
        this.jlycxchbqsc = jlycxchbqsc == null ? null : jlycxchbqsc.trim();
    }

    public String getJlycxchdhcw() {
        return jlycxchdhcw;
    }

    public void setJlycxchdhcw(String jlycxchdhcw) {
        this.jlycxchdhcw = jlycxchdhcw == null ? null : jlycxchdhcw.trim();
    }

    public String getZxyycxcs() {
        return zxyycxcs;
    }

    public void setZxyycxcs(String zxyycxcs) {
        this.zxyycxcs = zxyycxcs == null ? null : zxyycxcs.trim();
    }

	public UserPbccrcHtml getUserPbccrcHtml() {
		return userPbccrcHtml;
	}

	public void setUserPbccrcHtml(UserPbccrcHtml userPbccrcHtml) {
		this.userPbccrcHtml = userPbccrcHtml;
	}

	public Object getAddDate() {
		return addDate;
	}

	public void setAddDate(Object addDate) {
		this.addDate = addDate;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getLiabilitiesLoan() {
		return liabilitiesLoan;
	}

	public void setLiabilitiesLoan(String liabilitiesLoan) {
		this.liabilitiesLoan = liabilitiesLoan;
	}

	public String getLiabilitiesCard() {
		return liabilitiesCard;
	}

	public void setLiabilitiesCard(String liabilitiesCard) {
		this.liabilitiesCard = liabilitiesCard;
	}

	public String getLiabilitiesCurrent() {
		return liabilitiesCurrent;
	}

	public void setLiabilitiesCurrent(String liabilitiesCurrent) {
		this.liabilitiesCurrent = liabilitiesCurrent;
	}

	public String getPropertyTypeZx() {
		return propertyTypeZx;
	}

	public void setPropertyTypeZx(String propertyTypeZx) {
		this.propertyTypeZx = propertyTypeZx;
	}
    
}