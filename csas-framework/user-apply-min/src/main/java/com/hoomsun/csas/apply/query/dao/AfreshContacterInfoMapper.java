package com.hoomsun.csas.apply.query.dao;

import com.hoomsun.csas.apply.query.model.AfreshContacterInfo;

/**
 * 作者：ygzhao <br>
 * 创建时间：2017年11月21日 <br>
 * 描述：联系人细心dao
 */
public interface AfreshContacterInfoMapper {

	AfreshContacterInfo selectByPrimaryKey(String cninfoPkId);

}