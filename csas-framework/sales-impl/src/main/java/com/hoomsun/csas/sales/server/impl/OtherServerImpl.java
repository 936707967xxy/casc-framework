/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.server.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.csas.sales.api.exception.AuditException;
import com.hoomsun.csas.sales.api.model.UserChsi;
import com.hoomsun.csas.sales.api.model.UserHouseFund;
import com.hoomsun.csas.sales.api.model.UserSocialsecurity;
import com.hoomsun.csas.sales.api.model.UserTaoBaoAddress;
import com.hoomsun.csas.sales.api.model.UserTaobao;
import com.hoomsun.csas.sales.api.model.vo.OtherVo;
import com.hoomsun.csas.sales.api.server.inter.OtherServerI;
import com.hoomsun.csas.sales.dao.UserChsiMapper;
import com.hoomsun.csas.sales.dao.UserHouseFundMapper;
import com.hoomsun.csas.sales.dao.UserSocialsecurityMapper;
import com.hoomsun.csas.sales.dao.UserTaoBaoAddressMapper;
import com.hoomsun.csas.sales.dao.UserTaobaoMapper;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月7日 <br>
 * 描述：其他审核数据
 */
@Service("otherServer")
public class OtherServerImpl implements OtherServerI {

	@Autowired
	private UserChsiMapper userChsiMapper;
	@Autowired
	private UserTaobaoMapper userTaobaoMapper;
	@Autowired
	private UserTaoBaoAddressMapper userTaoBaoAddressMapper;
	@Autowired
	private UserHouseFundMapper userHouseFundMapper;
	@Autowired
	private UserSocialsecurityMapper userSocialsecurityMapper;

	@Override
	public OtherVo findByApplyId(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			throw new AuditException("参数异常! applyId is not null!");
		}
		// 学历
		UserChsi chsi = new UserChsi(); 
		if(userChsiMapper.selectByApplyId(applyId)!=null){
			chsi = userChsiMapper.selectByApplyId(applyId);
		}
		// 淘宝
		UserTaobao taobao =  new UserTaobao(); 
		if(userTaobaoMapper.selectByApplyId(applyId)!=null){
			taobao= userTaobaoMapper.selectByApplyId(applyId);
		}
		// 公积金
		UserHouseFund houseFund = new UserHouseFund();
		if(userHouseFundMapper.selectByApplyId(applyId)!=null){
			houseFund = userHouseFundMapper.selectByApplyId(applyId);
		}
		// 社保
		UserSocialsecurity socialsecurity = new UserSocialsecurity();
		if(userSocialsecurityMapper.selectByApplyId(applyId)!=null){
			socialsecurity = userSocialsecurityMapper.selectByApplyId(applyId);
		}

		OtherVo vo = new OtherVo();
		vo.setUserChsi(chsi);
		vo.setTaobao(taobao);
		vo.setHouseFund(houseFund);
		vo.setSocialsecurity(socialsecurity);
		return vo;
	}
	
	@Override
	public Json saveOrUpdate(OtherVo otherVo) {
		if (otherVo == null) {
			return new Json(false, "保存失败!不能保存空数据!");
		} else {
			saveOrUpadteChsi(otherVo.getUserChsi());
			saveOrUpadteHouseFund(otherVo.getHouseFund());
			saveOrUpadteSocialsecurity(otherVo.getSocialsecurity());
			saveOrUpdateTaobao(otherVo.getTaobao());
			return new Json(true, "保存成功!");
		}
	}

	@Override
	public Json saveOrUpadteChsi(UserChsi chsi) {
		if (chsi != null) {
			String applyId = chsi.getApplyId();
			if (StringUtils.isBlank(applyId)) {
				return new Json(false, "参数异常,apply id is null!");
			}
			// 验证数据是否存在
			Integer result = userChsiMapper.checkByApplyId(applyId);
			if (result > 0) {
				userChsiMapper.updateByPrimaryKeySelective(chsi);
			} else {
				chsi.setChsiId(PrimaryKeyUtil.getPrimaryKey());
				userChsiMapper.insertSelective(chsi);
			}
			return new Json(true, "保存成功!");
		}
		return new Json(false, "无法保存空数据!");
	}

	@Override
	public Json saveOrUpadteHouseFund(UserHouseFund houseFund) {
		if (houseFund != null) {
			String applyId = houseFund.getApplyId();
			if (StringUtils.isBlank(applyId)) {
				return new Json(false, "参数异常,apply id is null!");
			}
			// 验证数据是否存在
			Integer result = userHouseFundMapper.checkByApplyId(applyId);
			if (result > 0) {
				userHouseFundMapper.updateByPrimaryKeySelective(houseFund);
			} else {
				houseFund.setHfId(PrimaryKeyUtil.getPrimaryKey());
				userHouseFundMapper.insertSelective(houseFund);
			}
			return new Json(true, "保存成功!");
		}
		return new Json(false, "无法保存空数据!");
	}

	@Override
	public Json saveOrUpadteSocialsecurity(UserSocialsecurity socialsecurity) {
		if (socialsecurity != null) {
			String applyId = socialsecurity.getApplyId();
			if (StringUtils.isBlank(applyId)) {
				return new Json(false, "参数异常,apply id is null!");
			}
			// 验证数据是否存在
			Integer result = userSocialsecurityMapper.checkByApplyId(applyId);
			if (result > 0) {
				userSocialsecurityMapper.updateByPrimaryKeySelective(socialsecurity);
			} else {
				socialsecurity.setSiId(PrimaryKeyUtil.getPrimaryKey());
				userSocialsecurityMapper.insertSelective(socialsecurity);
			}
			return new Json(true, "保存成功!");
		}
		return new Json(false, "无法保存空数据!");
	}

	@Override
	public Json saveOrUpdateTaobao(UserTaobao taobao) {
		if (taobao != null) {
			String applyId = taobao.getApplyId();
			if (StringUtils.isBlank(applyId)) {
				return new Json(false, "参数异常,apply id is null!");
			}
			// 验证数据是否存在
			String id = userTaobaoMapper.findIdByApplyId(applyId);
			if (!StringUtils.isBlank(id)) {
				userTaobaoMapper.updateByPrimaryKeySelective(taobao);
			} else {
				id = PrimaryKeyUtil.getPrimaryKey();
				taobao.setTbId(id);
				userTaobaoMapper.insertSelective(taobao);
			}
			
			
			List<UserTaoBaoAddress> address = taobao.getAddresses();
			if (null != address && address.size() > 0) {
				userTaoBaoAddressMapper.deleteByFKId(id);
				for (UserTaoBaoAddress addr : address) {
					addr.setTbAddressId(PrimaryKeyUtil.getPrimaryKey());
					addr.setTbFkid(id);
					userTaoBaoAddressMapper.insertSelective(addr);
				}
			}
			return new Json(true, "保存成功!");
		}
		return new Json(false, "无法保存空数据!");
	}

}
