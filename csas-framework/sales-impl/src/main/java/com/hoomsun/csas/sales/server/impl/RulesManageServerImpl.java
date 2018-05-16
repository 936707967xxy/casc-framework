package com.hoomsun.csas.sales.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.core.dao.BlackInfoMapper;
import com.hoomsun.core.model.SysContract;
import com.hoomsun.csas.sales.api.model.NameAuthentication;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.server.inter.RulesManageServerI;
import com.hoomsun.csas.sales.dao.NameAuthenticationMapper;
import com.hoomsun.csas.sales.dao.UserApplyMapper;

/**
 * 作者：liudongliang <br>
 * 创建时间：2017年12月29日 <br>
 * 描述：进件规则校验
 */
@Service("rulesManage")
public class RulesManageServerImpl implements RulesManageServerI {
	@Autowired
	private NameAuthenticationMapper nameAuthMapper;
	@Autowired
	private UserApplyMapper userApplyMapper;
	@Autowired
	private BlackInfoMapper blackInfoMapper;
	
	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-12-29
	 * @resource 1.线上、线下产品均不允许同时申请，即客户一旦申请一笔产品，在该订单结束之前不允许申请第二笔产品。
	 *           查询不为PROC_STATUS 不为放款 不为据贷 放弃 包含为NULL
	 */
	@Override
	public Map<String, Object> IsCredit(String idCard) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<UserApply> list = userApplyMapper.selectIsCredit(idCard);
		if (list.size() > 0) {
			result.put("errorInfo", "尊敬的客户，您已提交的申请正在审核当中，请您耐心等待勿重复提交。");
			result.put("errorCode", 1);
		} else {
			result.put("errorInfo", "是否有帐单在审核中规则,校验通过");
			result.put("errorCode", 0);
		}
		return result;
	}

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-12-29
	 * @resource 4、先拥有线上产品，状态结束（拒贷、通过、结清）之后允许申请门店产品
	 *           5、先申请门店产品，自申请之时到状态结束，不允许申请任何其他产品。该门店产品状态完结之后，进入封闭期，封闭期内不允许申请任何其他产品。
	 *           二者归为 查询 门店有放款成功的单子 未结清的不让贷
	 */
	@Override
	public Map<String, Object> StoreCreditIsFinish(String idCard) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<UserApply> list = userApplyMapper.selectStoreCreditIsFinish(idCard);
		for (UserApply UserApply : list) {
			SysContract contract = UserApply.getCon();
			// 合同状态 0 未放款 1 放款 2 结清 3 未结清 4 提前结清 5 逾期
			if (contract.getConStatus() == 0 || contract.getConStatus() == 1 || contract.getConStatus() == 3 || contract.getConStatus() == 5) {
				result.put("errorInfo", "尊敬的客户，由于您目前仍有一笔贷款尚未结清，暂时无法提交本次申请。");
				result.put("errorCode", 1);
				return result;
			}
		}
		result.put("errorInfo", "是否有门店申请未结清规则,校验通过");
		result.put("errorCode", 0);
		return result;
	}

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-12-29
	 * @resource 4.线上产品逾期，则也不能进件 App产品逾期不能进件
	 */
	@Override
	public Map<String, Object> AppCreditIsFinish(String idCard) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<UserApply> list = userApplyMapper.selectAppCreditIsFinish(idCard);
		for (UserApply UserApply : list) {
			SysContract contract = UserApply.getCon();
			// 合同状态 0 未放款 1 放款 2 结清 3 未结清 4 提前结清 5 逾期
			if (contract.getConStatus() == 5) {
				result.put("errorInfo", "您有一笔App申请超期,请结清完成再提交");
				result.put("errorCode", 1);
				return result;
			}
		}
		result.put("errorInfo", "是否有App申请超期规则,校验通过");
		result.put("errorCode", 0);
		return result;
	}

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-12-29
	 * @resource 身份证判断是否黑名单 /姓名,身份证,手机号
	 */
	@Override
	public Map<String, Object> BlackByIdCard(String idCard) {
		Map<String, Object> result = new HashMap<String, Object>();
		NameAuthentication hs = nameAuthMapper.selectByPaperId(idCard);
		if (hs == null) {
			result.put("errorInfo", "客户数据异常,请联系客服核对");
			result.put("errorCode", 1);
			return result;
		}

		if (!StringUtils.isBlank(idCard)) {
			int i = blackInfoMapper.findByIdNumber(idCard);
			if (i != 0) {
				result.put("errorInfo", "客户身份证" + idCard + "出现在黑名单,请联系客服核对");
				result.put("errorCode", 1);
				return result;
			}
		}

//		String name = hs.getCustname();
//		if (!StringUtils.isBlank(name)) {
//			int i = blackInfoMapper.findByCustname(name);
//			if (i != 0) {
//				result.put("errorInfo", "客户姓名" + name + "出现在黑名单,请联系客服核对");
//				result.put("errorCode", 1);
//				return result;
//			}
//		}

		String phone = hs.getPhone();
		if (!StringUtils.isBlank(phone)) {
			int i = blackInfoMapper.findByPhone(phone);
			if (i != 0) {
				result.put("errorInfo", "客户手机号" + phone + "出现在黑名单,请联系客服核对");
				result.put("errorCode", 1);
				return result;
			}
		}

		result.put("errorInfo", "是否黑名单规则,校验通过");
		result.put("errorCode", 0);
		return result;
	}

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2018-01-15
	 * @resource 封闭期-------线下产品才有封闭期,有线下产品据贷 90天
	 */
	@Override
	public Map<String, Object> Closeperiod(String idCard) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 默认封闭期
		Integer refuse = userApplyMapper.checkDeclineDate(idCard);// 拒贷校验
		if (refuse != null) {
			Map<String, Object> finalSubmit = userApplyMapper.selectFinalSubmit(idCard);// 查询终审提交表
			if (finalSubmit.get("REJECT_TYPE") != null) {
				Integer rejectType = Integer.parseInt(finalSubmit.get("REJECT_TYPE").toString());// 拒贷原因
				switch (rejectType) {
				case 0:// 虚假信息365
					if (refuse != null && refuse <= 365) {
						result.put("errorInfo", "尊敬的客户，由于您尚处在封闭期间，暂时无法提交本次申请。");
						result.put("errorCode", 1);
					}
					break;
				case 1:// 信息异常180
					if (refuse != null && refuse <= 180) {
						result.put("errorInfo", "尊敬的客户，由于您尚处在封闭期间，暂时无法提交本次申请。");
						result.put("errorCode", 1);
					}
					break;
				default:// 其他30
					if (refuse != null && refuse <= 30) {
						result.put("errorInfo", "尊敬的客户，由于您尚处在封闭期间，暂时无法提交本次申请。");
						result.put("errorCode", 1);
					}
					break;
				}
			}else{
				result.put("errorInfo", "客户已过封闭期");
				result.put("errorCode", 0);
			}
		}else{
			result.put("errorInfo", "客户未提交过信息");
			result.put("errorCode", 0);
		}
		return result;
		/*
		 * try {
		 * 
		 * for(UserApply UserApply:list){ String endstr = DateUtil.format(new
		 * Date(), "yyyy-MM-dd HH:mm:ss"); String startstr =
		 * DateUtil.format(UserApply.getApplyDate(), "yyyy-MM-dd HH:mm:ss"); int
		 * diff = DateUtil.getDay(startstr, endstr); if(diff<=90){ flag=false; }
		 * } if(flag){ result.put("errorInfo", "客户已过封闭期");
		 * result.put("errorCode", 0); }else{ result.put("errorInfo",
		 * "尊敬的客户，由于您尚处在封闭期间，暂时无法提交本次申请。"); result.put("errorCode", 1); } }
		 * catch (ParseException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } return result;
		 */
	}

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-12-29
	 * @resource 4、先拥有线上产品，状态结束（拒贷、结清）之后允许申请门店产品,放款成功之后不允许申请门店产品,仍然可申请线上剩余额度
	 *           是否存在app产品的单子
	 */
	@Override
	public Map<String, Object> AppIsExist(String idCard) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<UserApply> list = userApplyMapper.selectAppIsExist(idCard);
		if (list.size() > 0) {
			result.put("errorInfo", "尊敬的客户，由于您已提交一笔app申请，暂时无法提交本次申请。");
			result.put("errorCode", 1);
			return result;
		}
		result.put("errorInfo", "尊敬的客户，由于您已提交一笔app申请，暂时无法提交本次申请。");
		result.put("errorCode", 0);
		return result;
	}
}
