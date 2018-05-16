/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.util.enums;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年4月18日 <br>
 * 描述：客户催收记录
 */
public enum CollectionRecordEnum {

	/*
	 * 客户接听状态
	 * HBA：已接听
		NA：无人接听
		SD：关机
		EP：空号
		SP：停机
		RJ:拒接
		CTF：呼转
		UTC:无法接通
	 */
	COLL_RECEIVING_STATE_HBA("HBA","已接听"),
	COLL_RECEIVING_STATE_NA("NA","无人接听"),
	COLL_RECEIVING_STATE_SD("SD","关机"),
	COLL_RECEIVING_STATE_EP("EP","空号"),
	COLL_RECEIVING_STATE_SP("SP","停机"),
	COLL_RECEIVING_STATE_RJ("RJ","拒接"),
	COLL_RECEIVING_STATE_CTF("CTF","呼转"),
	COLL_RECEIVING_STATE_UTC("UTC","无法接通"),
	/**
	 * 于客户关系
	 * MS:本人、
		SS:配偶、
		RE:亲属、
		FD:朋友、
		CE:同事、
		OS:其他
	 */
	COLL_CUS_RELATIONSHIP_MS("MS","本人"),
	COLL_CUS_RELATIONSHIP_SS("SS","配偶"),
	COLL_CUS_RELATIONSHIP_RE("RE","亲属"),
	COLL_CUS_RELATIONSHIP_FD("FD","朋友"),
	COLL_CUS_RELATIONSHIP_CE("CE","同事"),
	COLL_CUS_RELATIONSHIP_OS("OS","其他"),
	/**
	 * 提醒状态
	 */
	COLL_REMINDING_STATE_TRUE("0","已提醒"),
	COLL_REMINDING_STATE_FALSE("1","未提醒");
	
	private String mess ;
    private String code ;
     
    private CollectionRecordEnum( String code,String mess  ){
        this.mess = mess ;
        this.code = code ;
    }
    
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMess() {
		return mess;
	}

	public void setMess(String mess) {
		this.mess = mess;
	}
	
	public static String getName(String index) {
        for (CollectionRecordEnum c : CollectionRecordEnum.values()) {
            if (c.getCode() == index) {
                return c.mess;  
            }
        }
        return null;
    } 
}
