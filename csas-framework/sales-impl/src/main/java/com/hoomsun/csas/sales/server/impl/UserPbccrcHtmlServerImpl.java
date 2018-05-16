/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.server.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.csas.sales.api.model.UserPbccrcHtml;
import com.hoomsun.csas.sales.api.server.inter.UserPbccrcHtmlServerI;
import com.hoomsun.csas.sales.dao.UserPbccrcHtmlMapper;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月18日 <br>
 * 描述：
 */
@Service("userPbccrcHtmlServer")
public class UserPbccrcHtmlServerImpl implements UserPbccrcHtmlServerI {
	@Autowired
	private UserPbccrcHtmlMapper userPbccrcHtmlMapper;
	
	@Override
	public Json create(UserPbccrcHtml pbccrcHtml) {
		if (pbccrcHtml != null) {
			String id = pbccrcHtml.getPhId();
			if (StringUtils.isBlank(id)) {
				id = PrimaryKeyUtil.getPrimaryKey();
			}
			pbccrcHtml.setPhId(id);
			int result = userPbccrcHtmlMapper.insertSelective(pbccrcHtml);
			if (result > 0) {
				return new Json(true, "添加成功!");
			} else {
				return new Json(false, "添加失败!");
			}
		}
		return new Json(false, "参数不能为空!");
	}

	@Override
	public UserPbccrcHtml findById(String id) {
		return userPbccrcHtmlMapper.selectByPrimaryKey(id);
	}

	@Override
	public UserPbccrcHtml findByApplyId(String applyId) {
		return userPbccrcHtmlMapper.selectByApplyId(applyId);
	}

	@Override
	public UserPbccrcHtml findByCrId(String crId) {
		return userPbccrcHtmlMapper.selectByCrId(crId);
	}

}
