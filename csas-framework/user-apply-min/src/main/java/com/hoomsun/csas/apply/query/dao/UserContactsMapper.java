/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.apply.query.dao;

import com.hoomsun.csas.apply.query.model.UserContacts;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月4日 <br>
 * 描述：
 */
public interface UserContactsMapper {

	UserContacts selectByPrimaryKey(String phoneid);

	UserContacts selectByApplyId(String applyId);

}
