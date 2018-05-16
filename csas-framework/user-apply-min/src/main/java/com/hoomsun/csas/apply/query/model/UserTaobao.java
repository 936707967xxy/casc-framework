package com.hoomsun.csas.apply.query.model;
/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月3日 <br>
 * 描述： 淘宝信息
 *
 */
public class UserTaobao {
    private String tbId;

    private String addDate;

    private String jsypjhk;//近3个月平均还款金额

    private String jsyzcje;//近3个月支出金额

    private String jsyzrje;//近3个月转入金额

    private String jsyxfzb;//近3个月消费占比

    private String bnyebljsy;//余额宝累计6月收益

    private String zfbzhye;//支付宝账户余额

    private String hbed;//花呗可用额度

    private String adrss;

    private String shdzzs;//收货地址总数

    private String bnshdz;//近6个月收货地址

    private String applyId;

    public String getTbId() {
        return tbId;
    }

    public void setTbId(String tbId) {
        this.tbId = tbId == null ? null : tbId.trim();
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate == null ? null : addDate.trim();
    }

    public String getJsypjhk() {
        return jsypjhk;
    }

    public void setJsypjhk(String jsypjhk) {
        this.jsypjhk = jsypjhk == null ? null : jsypjhk.trim();
    }

    public String getJsyzcje() {
        return jsyzcje;
    }

    public void setJsyzcje(String jsyzcje) {
        this.jsyzcje = jsyzcje == null ? null : jsyzcje.trim();
    }

    public String getJsyzrje() {
        return jsyzrje;
    }

    public void setJsyzrje(String jsyzrje) {
        this.jsyzrje = jsyzrje == null ? null : jsyzrje.trim();
    }

    public String getJsyxfzb() {
        return jsyxfzb;
    }

    public void setJsyxfzb(String jsyxfzb) {
        this.jsyxfzb = jsyxfzb == null ? null : jsyxfzb.trim();
    }

    public String getBnyebljsy() {
        return bnyebljsy;
    }

    public void setBnyebljsy(String bnyebljsy) {
        this.bnyebljsy = bnyebljsy == null ? null : bnyebljsy.trim();
    }

    public String getZfbzhye() {
        return zfbzhye;
    }

    public void setZfbzhye(String zfbzhye) {
        this.zfbzhye = zfbzhye == null ? null : zfbzhye.trim();
    }

    public String getHbed() {
        return hbed;
    }

    public void setHbed(String hbed) {
        this.hbed = hbed == null ? null : hbed.trim();
    }

    public String getAdrss() {
        return adrss;
    }

    public void setAdrss(String adrss) {
        this.adrss = adrss == null ? null : adrss.trim();
    }

    public String getShdzzs() {
        return shdzzs;
    }

    public void setShdzzs(String shdzzs) {
        this.shdzzs = shdzzs == null ? null : shdzzs.trim();
    }

    public String getBnshdz() {
        return bnshdz;
    }

    public void setBnshdz(String bnshdz) {
        this.bnshdz = bnshdz == null ? null : bnshdz.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }
}