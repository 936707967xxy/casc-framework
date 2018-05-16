/**
 * Copyright www.idawn.org 邹益伟 idawnorg@gmail.com
 */
package com.hoomsun.csas.audit.server.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.csas.audit.dao.PhoneVerifyMapper;
import com.hoomsun.csas.audit.model.PhoneVerify;
import com.hoomsun.csas.audit.server.inter.PhoneVerifyServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月10日 <br>
 * 描述： 电核数据的业务
 */
@Service("phoneVerifyServer")
public class PhoneVerifyServerImpl implements PhoneVerifyServerI {
	
	@Autowired
	private PhoneVerifyMapper phoneVerifyMapper;
	
	@Override
	public List<PhoneVerify> findByApplyId(String applyId) {
		return phoneVerifyMapper.findByApplyId(applyId);
	}

	@Override
	public PhoneVerify findByConsId(String consId) {
		return phoneVerifyMapper.findByConsId(consId);
	}

	@Override
	public PhoneVerify findById(String id) {
		return phoneVerifyMapper.selectByPrimaryKey(id);
	}

	@Override
	public Json savePhoneVerify(PhoneVerify phoneVerify) {
		String applyId = phoneVerify.getApplyId();
		String phone = phoneVerify.getTelNumber();
		
		PhoneVerify result = phoneVerifyMapper.queryByApplyTel(applyId,phone);
		if(result != null) {
			phoneVerify.setPvId(result.getPvId());
			return updatePhoneVerify(phoneVerify);
		}else {
			String id = PrimaryKeyUtil.getPrimaryKey();
			phoneVerify.setPvId(id);
			Integer in = phoneVerifyMapper.insertSelective(phoneVerify);
			if (in > 0) {
				return new Json(true, "保存成功！");
			} else {
				return new Json(false, "保存失败！");
			}
		}
	}

	@Override
	public Json updatePhoneVerify(PhoneVerify phoneVerify) {
		Integer result = phoneVerifyMapper.updateByPrimaryKeySelective(phoneVerify);
		if (result > 0) {
			return new Json(true, "修改成功！");
		} else {
			return new Json(false, "修改失败！");
		}
	}

}
