/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.util.enums;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月14日 <br>
 * 描述：
 */
public enum CustomerLoanBalEnum {

	/**
	 * 查询类型
	 */
	QUERY_TYPE_FRONT("0","前线"),
	QUERY_TYPE_BACKUP("1","后援"),
	
	/**
	 * 客户是否逾期
	 */
	LOADBAL_DALAYFLAG_TRUE("0","未逾期"),
	LOADBAL_DALAYFLAG_FALSE("1","逾期"),
	/**
	 * 客户处于前线还是后援
	 */
	LOADBAL_CUSTOMER_FRONT("0","前线"),
	LOADBAL_CUSTOMER_BACKUP("1","后援"),
	
	/**
	 * 客户是否结清
	 */
	CUSTOMER_SETTLE_FLAG_YES("1","已结清"),
	CUSTOMER_SETTLE_FLAG_NO("0","未结清"),
	/**
	 * 客户是否挂起
	 */
	CUSTOMER_HANG_UP_YES("1","是"),
	CUSTOMER_HANG_UP_NO("0","否"),
	/**
	 * 客户外访申请状态
	 */
	OUTBOUND_STATUS_0("0","通过"),
	OUTBOUND_STATUS_1("1","失效"),
	OUTBOUND_STATUS_2("2","申请中"),
	OUTBOUND_STATUS_3("3","催收结束转正常时效"),
	/**
	 * 是否认领
	 */
	VERIFICATION_PEOPLE_ID_YES("1","已认领"),
	VERIFICATION_PEOPLE_ID_NO("0","未认领");
	
	private String mess ;
    private String code ;
     
    private CustomerLoanBalEnum( String code,String mess  ){
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
	
}
