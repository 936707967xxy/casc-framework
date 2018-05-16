package com.hoomsun.csas.sales.api.model;
/**
 * 
 * 作者：liming<br>
 * 创建时间：2017年12月9日 <br>
 * 描述： 淘宝地址
 *
 */
public class UserTaoBaoAddress {
    private String tbAddressId;

    private String phone;     //手机号

    private String location;  //地址(陕西省 西安市 高陵区 鹿苑街道)

    private String takeman;   //收货人

    private String detailedaddress; //详细地址(幸福北路39号陕汽集团)

    private String postcode;  //(邮政编码)

    private String tbFkid;

    public String getTbAddressId() {
        return tbAddressId;
    }

    public void setTbAddressId(String tbAddressId) {
        this.tbAddressId = tbAddressId == null ? null : tbAddressId.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getTakeman() {
        return takeman;
    }

    public void setTakeman(String takeman) {
        this.takeman = takeman == null ? null : takeman.trim();
    }

    public String getDetailedaddress() {
        return detailedaddress;
    }

    public void setDetailedaddress(String detailedaddress) {
        this.detailedaddress = detailedaddress == null ? null : detailedaddress.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public String getTbFkid() {
        return tbFkid;
    }

    public void setTbFkid(String tbFkid) {
        this.tbFkid = tbFkid == null ? null : tbFkid.trim();
    }
}