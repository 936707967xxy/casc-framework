package com.hoomsun.core.model;
/**
 * 
 * 作者：liming<br>
 * 创建时间：2017年11月30日 <br>
 * 描述： 生成编号
 *
 */
public class SerialNumber {
    private String id;//主键

    private String model;//模板

    private Integer code;//序号

    private String createtime;//生成时间

    private Integer codlength;//序号长度

    private String type;//类型

    private String typeval;
   
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
    	this.code = code;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public Integer getCodlength() {
        return codlength;
    }

    public void setCodlength(Integer codlength) {
        this.codlength = codlength;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getTypeval() {
        return typeval;
    }

    public void setTypeval(String typeval) {
        this.typeval = typeval == null ? null : typeval.trim();
    }
}