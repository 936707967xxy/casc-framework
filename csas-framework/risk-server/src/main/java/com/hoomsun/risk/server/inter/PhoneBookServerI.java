/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.server.inter;

import com.hoomsun.common.model.Result;
import com.hoomsun.risk.model.PhoneBook;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月1日 <br>
 * 描述：通讯录的业务数据
 */
public interface PhoneBookServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月1日 <br>
	 * 描述： 添加某人的通讯录数据
	 * @return
	 */
	public Result createPhoneBook(PhoneBook phoneBook) ;
	
}
