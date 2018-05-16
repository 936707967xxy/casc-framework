/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.server.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.csas.sales.api.exception.AuditException;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.model.UserContacts;
import com.hoomsun.csas.sales.api.model.vo.CallBillsVo;
import com.hoomsun.csas.sales.api.server.inter.CallBillsServerI;
import com.hoomsun.csas.sales.dao.UserApplyMapper;
import com.hoomsun.csas.sales.dao.UserContactsMapper;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月7日 <br>
 * 描述：
 */
@Service("callBillsServer")
public class CallBillsServerImpl implements CallBillsServerI {
	private UserContactsMapper userContactsMapper;
	private UserApplyMapper userApplyMapper;

	@Autowired
	public void setUserContactsMapper(UserContactsMapper userContactsMapper) {
		this.userContactsMapper = userContactsMapper;
	}

	@Autowired
	public void setUserApplyMapper(UserApplyMapper userApplyMapper) {
		this.userApplyMapper = userApplyMapper;
	}

	@Override
	public CallBillsVo findByApplyId(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			throw new AuditException("参数异常! applyId is not null!");
		}

		UserApply userApply = userApplyMapper.selectByPrimaryKey(applyId);

		UserContacts cnt = new UserContacts();
		cnt.setName(userApply.getCustName());
		cnt.setPhone(userApply.getCustMobile());
		cnt.setRelationship(0);// 关系ID
		cnt.setRelationshipVal("本人");// 关系val
		cnt.setIsFillIn(1);
		cnt.setContId(applyId+"sf");

		List<UserContacts> contacts = userContactsMapper.findByApplyId(applyId, null);
		contacts.add(cnt);
		CallBillsVo vo = new CallBillsVo();
		vo.setContacts(contacts);
		return vo;
	}

}
