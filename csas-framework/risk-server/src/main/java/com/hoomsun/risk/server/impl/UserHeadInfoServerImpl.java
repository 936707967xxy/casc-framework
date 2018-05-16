package com.hoomsun.risk.server.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.risk.dao.UserHeadInfoVoMapper;
import com.hoomsun.risk.model.UserHeadInfoVo;
import com.hoomsun.risk.server.inter.UserHeadInfoServerI;
/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月24日 <br>
 * 描述： 用户画像
 */
@Service("userHeadInfoServer")
public class UserHeadInfoServerImpl implements UserHeadInfoServerI {

	@Autowired
	private UserHeadInfoVoMapper userHeadInfoVoMapper;

	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2018年01月9日 <br>
	 * 描述:保存用户画像
	 * 
	 * @param applyId
	 *            开始时间
	 * @return
	 */
	public Json saveUserHeadinfo(String applyId) {

		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "apply参数异常!");
		}

		Map<String, Object> userApply = userHeadInfoVoMapper.findApplybyApplyId(applyId);
		Map<String, Object> storinfo = userHeadInfoVoMapper.findStorebyId(userApply.get("STORE_ID") + ""); // 部门信息
		Map<String, Object> occupationalinfo = userHeadInfoVoMapper.selectOccupationalinfo(applyId);

		Map<String, Object> cis = userHeadInfoVoMapper.selectCis(applyId);
		if (cis == null) {
			cis = new HashMap<String, Object>();
		}

		Map<String, Object> pbccrc = userHeadInfoVoMapper.selectPbccrc(applyId);
		if (pbccrc == null) {
			pbccrc = new HashMap<String, Object>();
		}

		Map<String, Object> tongdun = userHeadInfoVoMapper.selectTongdun(applyId);
		if (tongdun == null) {
			tongdun = new HashMap<String, Object>();
		}

		Map<String, Object> socialsecurity = userHeadInfoVoMapper.selectSocialsecurity(applyId);
		if (socialsecurity == null) {
			socialsecurity = new HashMap<String, Object>();
		}

		UserHeadInfoVo userHeadInfoVo = new UserHeadInfoVo();
		userHeadInfoVo.setPoId(PrimaryKeyUtil.getPrimaryKey());
		userHeadInfoVo.setApplyId(applyId);
		userHeadInfoVo.setCreatedTime(DateUtil.getTimestamp());

		userHeadInfoVo.setCustName(userApply.get("CUST_NAME") + ""); // 姓名
		userHeadInfoVo.setCustSex(userApply.get("CUST_SEX") + ""); // 性别
		userHeadInfoVo.setIdNumber(userApply.get("ID_NUMBER") + ""); // 身份证
		userHeadInfoVo.setCustAge(Integer.parseInt(userApply.get("CUST_AGE") + "")); // 年龄

		String dept_work_addr = storinfo.get("DEPT_WORK_ADDR") + ""; // 门店详细地址
		String rresidenceCityName = storinfo.get("RRESIDENCE_CITY_NAME") + ""; // 户籍所在地市

		if (dept_work_addr.contains(rresidenceCityName)) {
			userHeadInfoVo.setIsLocalPerson("是");// 是否本地人
		} else {
			userHeadInfoVo.setIsLocalPerson("否");// 是否本地人
		}

		userHeadInfoVo.setForecastLiveTimePq("");// 爬取预测本地居住时长
		userHeadInfoVo.setForecastLiveTimeTd("");// 同盾预测本地居住时长
		userHeadInfoVo.setForecastLiveTimeZx("");// 资信预测本地居住时长

		userHeadInfoVo.setMaritalStatusCust(userApply.get("MARITAL_STATUS_VAL") + "");// 客户填-婚姻状况
		userHeadInfoVo.setMaritalStatusTd(tongdun.get("MARITAL_STATUS_TD") + ""); // 同盾-婚姻状况
		userHeadInfoVo.setMaritalStatusZx(cis.get("MARITAL_STATUS_ZX") + ""); // 上海资信-婚姻状况

		userHeadInfoVo.setAddressCust(userApply.get("HOUSE_ADDRESS") + ""); // 客户填-住址
		userHeadInfoVo.setAddressTd(tongdun.get("ADDRESS_TD") + ""); // 同盾-住址
		userHeadInfoVo.setAddressPq(""); // 淘宝爬取-住址
		userHeadInfoVo.setAddressZx(cis.get("ADDRESS_ZX") + ""); // 资信-住址
		userHeadInfoVo.setAddressXsAdd(""); // 信审添加-住址(待定)

		userHeadInfoVo.setCompanyNameCust(occupationalinfo.get("FULL_WORK_NAME") + ""); // 客户填-单位名称
		userHeadInfoVo.setCompanyNameSb(socialsecurity.get("SI_COM") + ""); // 社保网站-单位名称
		userHeadInfoVo.setCompanyNameTd(tongdun.get("COMPANY_NAME_TD") + ""); // 同盾-单位名称
		userHeadInfoVo.setCompanyNameZx(cis.get("COMPANY_NAME_ZX") + ""); // 资信-单位名称

		userHeadInfoVo.setIndustryIn(occupationalinfo.get("INDUSTRY_IN_VAL") + ""); // 行业
		userHeadInfoVo.setJobTitle(occupationalinfo.get("JOB_TITLE") + ""); // 职位名称
		userHeadInfoVo.setPosition(occupationalinfo.get("POSITION_VAL") + ""); // 职级

		userHeadInfoVo.setCompanyKind(occupationalinfo.get("COMPANY_KIND_VA") + "");// 单位性质
		userHeadInfoVo.setInComeCust(occupationalinfo.get("SALARY_MONTHLY") + ""); // 客户填--收入

		userHeadInfoVo.setInComeAverage("");// 平均值--收入
		userHeadInfoVo.setInComeModel("");// 中位数--收入
		userHeadInfoVo.setInComeCoef("");// 变异系数--收入

		userHeadInfoVo.setLiabilitiesLoan(pbccrc.get("LIABILITIES_LOAN") + ""); // 贷款-负债
		userHeadInfoVo.setLiabilitiesCard(pbccrc.get("LIABILITIES_CARD") + ""); // 信用卡-负债
		userHeadInfoVo.setLiabilitiesCurrent(pbccrc.get("LIABILITIES_CURRENT") + ""); // 当前负债金额

		userHeadInfoVo.setPropertyTypeCust(userApply.get("LIVE_CONDITIONS_VAL") + ""); // 客户填--房产类型
		userHeadInfoVo.setPropertyTypeZx(pbccrc.get("PROPERTY_TYPE_ZX") + ""); // 征信--房产类型

		int i = userHeadInfoVoMapper.insertSelective(userHeadInfoVo);
		if (i == 0) {
			return new Json(false, "用户画像表添加异常");
		}

		return new Json(true, "添加成功！！");
	}
}
