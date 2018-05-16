/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.util.CreateSerialNo;
import com.hoomsun.common.util.HttpClientUtil;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.core.dao.HxbRateMapper;
import com.hoomsun.core.dao.HxbReplaymentPlanMapper;
import com.hoomsun.core.model.HxbRate;
import com.hoomsun.core.model.HxbReplaymentPlan;
import com.hoomsun.core.model.SysProduct;
import com.hoomsun.core.server.inter.SysProductServerI;
import com.hoomsun.csas.audit.dao.FinalAuditMapper;
import com.hoomsun.csas.audit.dao.HxbRecordMapper;
import com.hoomsun.csas.audit.model.FinalAudit;
import com.hoomsun.csas.audit.model.HxbRecord;
import com.hoomsun.csas.audit.server.inter.LendImportServerI;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.model.UserContacts;
import com.hoomsun.csas.sales.dao.UserApplyMapper;

/**
 * 作者：liusong <br>
 * 创建时间：2018年2月2日 <br>
 * 描述：
 */
@Service("lendImportServer")
public class LendImportServerImpl implements LendImportServerI{
	@Autowired
	private UserApplyMapper userApplyMapper;
	@Autowired
	private FinalAuditMapper finalAuditMapper;
	@Autowired
	private SysProductServerI productServer;
	@Autowired
	private HxbReplaymentPlanMapper hxbReplaymentPlanMapper;
	@Autowired
	private HxbRecordMapper hxbRecordMapper;
	@Autowired
	private HxbRateMapper hxbRateMapper;
	@Value("${LEND_IMPORT}")
	private String lendImport;
	@Override
	public Json lendImport(String applyId) {
		UserApply userApply = userApplyMapper.findApplyById(applyId);
		FinalAudit finalAudit = finalAuditMapper.findByApplyId(applyId);
		SysProduct pro = productServer.findByTypeIdFinalAudit(finalAudit.getProductId());
		JSONObject object = new JSONObject();
		Map<String, Object> lend = new HashMap<String, Object>();// lend(loan,user)
		Map<String, Object> loan = new HashMap<String, Object>();// loan
		Map<String, Object> user = new HashMap<String, Object>();// user
		// loan(必填参数)
//		String loanRefId = userApply.getApplyId();// 借款的唯一编号
		String loanRefId = userApply.getLoanId();// 借款的唯一编号
		String title = userApply.getLoanUseVal();// 借款标题
		String channelAmount = finalAudit.getApprovedAmount().toString();// 期望借款金额,用户实际到手的借款金额
		Integer months = finalAudit.getProductPeriod();// 借款周期（月）
		String description = "贷款用于" + title + "，以上信息已考察认证，同时，经审核借款人所提供资料真实有效，符合红小宝的借款审核标准。";// 描述
		String contractCode = "HXB_" + loanRefId;// 选填，借款签约时的合同编号，合同编号唯一；若推送则使用渠道传递编号，若不推送则根据规则自动生成
		String productName = finalAudit.getProductName();// 产品名(测试为至信) finalAudit.getProductName()
		String monthlyFee = (pro.getRealMonthRate().multiply(new BigDecimal(100))).toString();// 渠道方产品费率(缺失---------)
		String graduation = userApply.getEduBackgroundVal();// 学历
		String university = userApply.getGraduateInstitutions();// 毕业学校
		String marriageStatus = userApply.getMaritalStatusVal();// 婚姻状态 未婚:0,
																// 已婚:1,离异:2,丧偶:3
		if (marriageStatus.equals("未婚")) {
			marriageStatus = "0";
		} else if (marriageStatus.equals("已婚")) {
			marriageStatus = "1";
		} else if (marriageStatus.equals("离异")) {
			marriageStatus = "2";
		} else if (marriageStatus.equals("丧偶")) {
			marriageStatus = "3";
		} else {
			marriageStatus = "0";
		}
		String hasChild = userApply.getChildNumber();// 是否有子女:1,0
		if (hasChild.equals("0")) {
			hasChild = "0";
		} else {
			hasChild = "1";
		}
		// （0:无房产,1：商业按揭购房，2：持证抵押房，3：公积金按揭购房，4：商业/公积金组合按揭购房，5：无按揭购房(全款购房),）
		String hasHouse = userApply.getAssetInfo().getPropertyType().toString();// 是否有房:1,0
		String hasHouseLoan = "";// 是否有房贷:1,0
		if ("1".equals(hasHouse) || "2".equals(hasHouse) || "3".equals(hasHouse) || "4".equals(hasHouse)) {
			hasHouse = "1";
			hasHouseLoan = "1";
		} else if ("0".equals(hasHouse)) {
			hasHouse = "0";
			hasHouseLoan = "0";
		} else if ("5".equals(hasHouse)) {
			hasHouse = "1";
			hasHouseLoan = "0";
		}
		String hasCar = userApply.getAssetInfo().getCarHava().toString();// 是否有车:1,0
		String hasCarLoan = "";// 是否有车贷1,0
		if ("1".equals(hasCar)) {
			hasCar = "0";
			hasCarLoan = "0";
		} else if ("2".equals(hasCar)) {
			hasCar = "1";
			hasCarLoan = "0";
		} else if ("3".equals(hasCar)) {
			hasCar = "1";
			hasCarLoan = "1";
		}
		String companyIndustry = userApply.getUserOccupationalInfo().getIndustryVal();// 公司所属行业
		String homeTown = userApply.getRresidenceProvName();// 籍贯所在省
		String accountLocation = userApply.getRresidenceCityName();// 籍贯所在城市
		String residenceTel = userApply.getCustMobile();// 居住地电话
		String residence = userApply.getHouseAddressDetail();// 居住地址
		String companyName = userApply.getUserOccupationalInfo().getFullWorkName();// 单位名称
		String jobStatus = userApply.getUserOccupationalInfo().getJobTitle();// 职业状态
		String companyLocation = userApply.getUserOccupationalInfo().getCompanyCityName();// 公司所在城市
		String companyProvince = userApply.getUserOccupationalInfo().getCompanyProvName();// 公司所在省份
		String companyAddress = userApply.getUserOccupationalInfo().getCompanyAddressDetail();// 公司详细地址
		String companyCategory = userApply.getUserOccupationalInfo().getCompanyKindVa();// 公司类别
		String companyPost = userApply.getUserOccupationalInfo().getPositionVal();// 公司职位
		String monthlyIncome = userApply.getUserOccupationalInfo().getSalaryMonthly();// 工作月收入
		List<UserContacts> userContacts = userApply.getUserContacts();
		if (userContacts != null && userContacts.size() > 1) {
			String immediateName = userApply.getUserContacts().get(0).getName();// 直系亲属名称
			String immediateRelationShip = userApply.getUserContacts().get(0).getRelationshipVal();// 直系亲属关系
			String immediateTel = userApply.getUserContacts().get(0).getPhone();// 直系亲属电话
			String otherRelationName = userApply.getUserContacts().get(1).getName();// 其他联系人名称
			String otherRelationShip = userApply.getUserContacts().get(1).getRelationshipVal();// 其他联系人关系
			String otherRelationTel = userApply.getUserContacts().get(1).getPhone();// 其他联系人电话
			loan.put("immediateName", immediateName);
			loan.put("immediateRelationShip", immediateRelationShip);
			loan.put("immediateTel", immediateTel);
			loan.put("otherRelationName", otherRelationName);
			loan.put("otherRelationShip", otherRelationShip);
			loan.put("otherRelationTel", otherRelationTel);
		} else if (userContacts != null && userContacts.size() == 1) {
			String immediateName = userApply.getUserContacts().get(0).getName();// 直系亲属名称
			String immediateRelationShip = userApply.getUserContacts().get(0).getRelationshipVal();// 直系亲属关系
			String immediateTel = userApply.getUserContacts().get(0).getPhone();// 直系亲属电话
			String otherRelationName = "暂无";// 其他联系人名称
			String otherRelationShip = "暂无";// 其他联系人关系
			String otherRelationTel = "暂无";// 其他联系人电话
			loan.put("immediateName", immediateName);
			loan.put("immediateRelationShip", immediateRelationShip);
			loan.put("immediateTel", immediateTel);
			loan.put("otherRelationName", otherRelationName);
			loan.put("otherRelationShip", otherRelationShip);
			loan.put("otherRelationTel", otherRelationTel);
		}
		loan.put("loanRefId", loanRefId);
		loan.put("title", title);
		loan.put("channelAmount", channelAmount);
		loan.put("months", months);
		loan.put("description", description);
		loan.put("contractCode", contractCode);
		loan.put("productName", productName);
		loan.put("monthlyFee", monthlyFee);
		loan.put("graduation", graduation);
		loan.put("university", university);
		loan.put("marriageStatus", marriageStatus);
		loan.put("hasChild", hasChild);
		loan.put("hasHouse", hasHouse);
		loan.put("hasHouseLoan", hasHouseLoan);
		loan.put("hasCar", hasCar);
		loan.put("hasCarLoan", hasCarLoan);
		loan.put("companyIndustry", companyIndustry);
		loan.put("homeTown", homeTown);
		loan.put("accountLocation", accountLocation);
		loan.put("residenceTel", residenceTel);
		loan.put("residence", residence);
		loan.put("companyName", companyName);
		loan.put("jobStatus", jobStatus);
		loan.put("companyLocation", companyLocation);
		loan.put("companyProvince", companyProvince);
		loan.put("companyAddress", companyAddress);
		loan.put("companyCategory", companyCategory);
		loan.put("companyPost", companyPost);
		loan.put("monthlyIncome", monthlyIncome);
		// user(必填参数)
		String userRefId = userApply.getApplyId();// 用户的唯一编号
		String mail = userApply.getEmail();// 邮箱
		String mobile = userApply.getCustMobile();// 手机号码
		String realName = userApply.getCustName();// 身份证真实姓名
		String idCard = userApply.getIdNumber();// 身份证号,只支持新版18位
		String address = userApply.getRresidenceAddressDetail();// 身份证地址(userApply.getIdAddr()-----目前没有数据)
		String gender = userApply.getCustSex();// 1:男,0:女
		if (gender.equals("男")) {
			gender = "1";
		}
		if (gender.equals("女")) {
			gender = "0";
		}
		user.put("userRefId", userRefId);
		user.put("mail", mail);
		user.put("mobile", mobile);
		user.put("realName", realName);
		user.put("idCard", idCard);
		user.put("address", address);
		user.put("gender", gender);
		lend.put("loan", loan);
		lend.put("user", user);
		object.put("lend", lend);
		object.put("generalInfo", CreateSerialNo.sign());

		// 存入记录表信息
		HxbRecord record = new HxbRecord();
		record.setApplyId(applyId);
		BigDecimal channelAmounts = new BigDecimal(channelAmount);
		record.setChannelAmount(channelAmounts);
		record.setCustName(realName);
		record.setRecordTime(new Date());
		record.setProductName(productName);
		record.setProductId(finalAudit.getProductId());
		record.setProductMonth(months);
		record.setPushType("1");
		record.setPushTypeVal("借款导入接口");
		record.setRecordId(PrimaryKeyUtil.getPrimaryKey());
		String json = object.toJSONString();

		// 链接红小宝测试路径
//		String url = "http://123.126.19.2:8040/lend/import/";//测试地址
//		String url = "http://lend.hoomxb.com/lend/import/";//正式地址
		String url = lendImport;//正式地址
		String result = HttpClientUtil.doPostJson(url, null, null, json);

		JSONObject resultObj = JSONObject.parseObject(result);
		// 验签
		boolean verify = CreateSerialNo.decode(resultObj.getJSONObject("generalInfo"));
		if (!verify) {
			return new Json(false, "红小宝验签失败!");
		}
		int status = resultObj.getIntValue("status");
		if (status == 1000) {
			// 合同借款金额
			String contractAmount = resultObj.getJSONObject("lendResult").getString("amount");
			BigDecimal contract = new BigDecimal(contractAmount);
			record.setContractAmount(contract);
			// 存入记录表数据
			hxbRecordMapper.insertSelective(record);
			// 平台服务费（y）
			String hxbServiceFees = resultObj.getJSONObject("lendResult").getString("hxbServiceFee");
			BigDecimal hxbServiceFee = new BigDecimal(hxbServiceFees);
			// 渠道服务费（z1）
			String channelServiceFees = resultObj.getJSONObject("lendResult").getString("channelServiceFee");
			BigDecimal channelServiceFee = new BigDecimal(channelServiceFees);
			// 渠道服务费（z2）
			String creditServiceFees = resultObj.getJSONObject("lendResult").getString("creditServiceFee");
			BigDecimal creditServiceFee = new BigDecimal(creditServiceFees);
			/**
			 * 
			 * 线上服务费存库
			 * 
			 * 
			 */
			HxbRate rate = hxbRateMapper.selectByApplyId(applyId);
			if(rate != null){
				rate.setApplyId(applyId);
				rate.setChannelServiceFee(channelServiceFee);
				rate.setCreditServiceFee(creditServiceFee);
				rate.setHxbServiceFee(hxbServiceFee);
				hxbRateMapper.updateByPrimaryKeySelective(rate);
			}else{
				rate = new HxbRate();
				rate.setApplyId(applyId);
				rate.setChannelServiceFee(channelServiceFee);
				rate.setCreditServiceFee(creditServiceFee);
				rate.setHxbServiceFee(hxbServiceFee);
				rate.setHoomxbId(PrimaryKeyUtil.getPrimaryKey());
				hxbRateMapper.insertSelective(rate);
			}
			return new Json(true,"推送成功！");
		}else{
			return new Json(false,"推送失败！失败原因"+resultObj.get("errMsg"));
		}
	}
	
	@Override
	public Json lendImportReplaymentPlan(String applyId) {
		UserApply userApply = userApplyMapper.findApplyById(applyId);
		FinalAudit finalAudit = finalAuditMapper.findByApplyId(applyId);
		SysProduct pro = productServer.findByTypeIdFinalAudit(finalAudit.getProductId());
		JSONObject object = new JSONObject();
		Map<String, Object> lend = new HashMap<String, Object>();// lend(loan,user)
		Map<String, Object> loan = new HashMap<String, Object>();// loan
		Map<String, Object> user = new HashMap<String, Object>();// user
		// loan(必填参数)
//		String loanRefId = userApply.getApplyId();// 借款的唯一编号
		String loanRefId = userApply.getLoanId();// 借款的唯一编号
		String title = userApply.getLoanUseVal();// 借款标题
		String channelAmount = finalAudit.getApprovedAmount().toString();// 期望借款金额,用户实际到手的借款金额
		Integer months = finalAudit.getProductPeriod();// 借款周期（月）
		String description = "贷款用于" + title + "，以上信息已考察认证，同时，经审核借款人所提供资料真实有效，符合红小宝的借款审核标准。";// 描述
		String contractCode = "HXB_" + loanRefId;// 选填，借款签约时的合同编号，合同编号唯一；若推送则使用渠道传递编号，若不推送则根据规则自动生成
		String productName = finalAudit.getProductName();// 产品名(测试为至信) finalAudit.getProductName()
		String monthlyFee = (pro.getRealMonthRate().multiply(new BigDecimal(100))).toString();// 渠道方产品费率(缺失---------)
		String graduation = userApply.getEduBackgroundVal();// 学历
		String university = userApply.getGraduateInstitutions();// 毕业学校
		String marriageStatus = userApply.getMaritalStatusVal();// 婚姻状态 未婚:0,
																// 已婚:1,离异:2,丧偶:3
		if (marriageStatus.equals("未婚")) {
			marriageStatus = "0";
		} else if (marriageStatus.equals("已婚")) {
			marriageStatus = "1";
		} else if (marriageStatus.equals("离异")) {
			marriageStatus = "2";
		} else if (marriageStatus.equals("丧偶")) {
			marriageStatus = "3";
		} else {
			marriageStatus = "0";
		}
		String hasChild = userApply.getChildNumber();// 是否有子女:1,0
		if (hasChild.equals("0")) {
			hasChild = "0";
		} else {
			hasChild = "1";
		}
		// （0:无房产,1：商业按揭购房，2：持证抵押房，3：公积金按揭购房，4：商业/公积金组合按揭购房，5：无按揭购房(全款购房),）
		String hasHouse = userApply.getAssetInfo().getPropertyType().toString();// 是否有房:1,0
		String hasHouseLoan = "";// 是否有房贷:1,0
		if ("1".equals(hasHouse) || "2".equals(hasHouse) || "3".equals(hasHouse) || "4".equals(hasHouse)) {
			hasHouse = "1";
			hasHouseLoan = "1";
		} else if ("0".equals(hasHouse)) {
			hasHouse = "0";
			hasHouseLoan = "0";
		} else if ("5".equals(hasHouse)) {
			hasHouse = "1";
			hasHouseLoan = "0";
		}
		String hasCar = userApply.getAssetInfo().getCarHava().toString();// 是否有车:1,0
		String hasCarLoan = "";// 是否有车贷1,0
		if ("1".equals(hasCar)) {
			hasCar = "0";
			hasCarLoan = "0";
		} else if ("2".equals(hasCar)) {
			hasCar = "1";
			hasCarLoan = "0";
		} else if ("3".equals(hasCar)) {
			hasCar = "1";
			hasCarLoan = "1";
		}
		String companyIndustry = userApply.getUserOccupationalInfo().getIndustryVal();// 公司所属行业
		String homeTown = userApply.getRresidenceProvName();// 籍贯所在省
		String accountLocation = userApply.getRresidenceCityName();// 籍贯所在城市
		String residenceTel = userApply.getCustMobile();// 居住地电话
		String residence = userApply.getHouseAddressDetail();// 居住地址
		String companyName = userApply.getUserOccupationalInfo().getFullWorkName();// 单位名称
		String jobStatus = userApply.getUserOccupationalInfo().getJobTitle();// 职业状态
		String companyLocation = userApply.getUserOccupationalInfo().getCompanyCityName();// 公司所在城市
		String companyProvince = userApply.getUserOccupationalInfo().getCompanyProvName();// 公司所在省份
		String companyAddress = userApply.getUserOccupationalInfo().getCompanyAddressDetail();// 公司详细地址
		String companyCategory = userApply.getUserOccupationalInfo().getCompanyKindVa();// 公司类别
		String companyPost = userApply.getUserOccupationalInfo().getPositionVal();// 公司职位
		String monthlyIncome = userApply.getUserOccupationalInfo().getSalaryMonthly();// 工作月收入
		List<UserContacts> userContacts = userApply.getUserContacts();
		if (userContacts != null && userContacts.size() > 1) {
			String immediateName = userApply.getUserContacts().get(0).getName();// 直系亲属名称
			String immediateRelationShip = userApply.getUserContacts().get(0).getRelationshipVal();// 直系亲属关系
			String immediateTel = userApply.getUserContacts().get(0).getPhone();// 直系亲属电话
			String otherRelationName = userApply.getUserContacts().get(1).getName();// 其他联系人名称
			String otherRelationShip = userApply.getUserContacts().get(1).getRelationshipVal();// 其他联系人关系
			String otherRelationTel = userApply.getUserContacts().get(1).getPhone();// 其他联系人电话
			loan.put("immediateName", immediateName);
			loan.put("immediateRelationShip", immediateRelationShip);
			loan.put("immediateTel", immediateTel);
			loan.put("otherRelationName", otherRelationName);
			loan.put("otherRelationShip", otherRelationShip);
			loan.put("otherRelationTel", otherRelationTel);
		} else if (userContacts != null && userContacts.size() == 1) {
			String immediateName = userApply.getUserContacts().get(0).getName();// 直系亲属名称
			String immediateRelationShip = userApply.getUserContacts().get(0).getRelationshipVal();// 直系亲属关系
			String immediateTel = userApply.getUserContacts().get(0).getPhone();// 直系亲属电话
			String otherRelationName = "暂无";// 其他联系人名称
			String otherRelationShip = "暂无";// 其他联系人关系
			String otherRelationTel = "暂无";// 其他联系人电话
			loan.put("immediateName", immediateName);
			loan.put("immediateRelationShip", immediateRelationShip);
			loan.put("immediateTel", immediateTel);
			loan.put("otherRelationName", otherRelationName);
			loan.put("otherRelationShip", otherRelationShip);
			loan.put("otherRelationTel", otherRelationTel);
		}
		loan.put("loanRefId", loanRefId);
		loan.put("title", title);
		loan.put("channelAmount", channelAmount);
		loan.put("months", months);
		loan.put("description", description);
		loan.put("contractCode", contractCode);
		loan.put("productName", productName);
		loan.put("monthlyFee", monthlyFee);
		loan.put("graduation", graduation);
		loan.put("university", university);
		loan.put("marriageStatus", marriageStatus);
		loan.put("hasChild", hasChild);
		loan.put("hasHouse", hasHouse);
		loan.put("hasHouseLoan", hasHouseLoan);
		loan.put("hasCar", hasCar);
		loan.put("hasCarLoan", hasCarLoan);
		loan.put("companyIndustry", companyIndustry);
		loan.put("homeTown", homeTown);
		loan.put("accountLocation", accountLocation);
		loan.put("residenceTel", residenceTel);
		loan.put("residence", residence);
		loan.put("companyName", companyName);
		loan.put("jobStatus", jobStatus);
		loan.put("companyLocation", companyLocation);
		loan.put("companyProvince", companyProvince);
		loan.put("companyAddress", companyAddress);
		loan.put("companyCategory", companyCategory);
		loan.put("companyPost", companyPost);
		loan.put("monthlyIncome", monthlyIncome);
		// user(必填参数)
		String userRefId = userApply.getApplyId();// 用户的唯一编号
		String mail = userApply.getEmail();// 邮箱
		String mobile = userApply.getCustMobile();// 手机号码
		String realName = userApply.getCustName();// 身份证真实姓名
		String idCard = userApply.getIdNumber();// 身份证号,只支持新版18位
		String address = userApply.getRresidenceAddressDetail();// 身份证地址(userApply.getIdAddr()-----目前没有数据)
		String gender = userApply.getCustSex();// 1:男,0:女
		if (gender.equals("男")) {
			gender = "1";
		}
		if (gender.equals("女")) {
			gender = "0";
		}
		user.put("userRefId", userRefId);
		user.put("mail", mail);
		user.put("mobile", mobile);
		user.put("realName", realName);
		user.put("idCard", idCard);
		user.put("address", address);
		user.put("gender", gender);
		lend.put("loan", loan);
		lend.put("user", user);
		object.put("lend", lend);
		object.put("generalInfo", CreateSerialNo.sign());

		// 存入记录表信息
		HxbRecord record = new HxbRecord();
		record.setApplyId(applyId);
		BigDecimal channelAmounts = new BigDecimal(channelAmount);
		record.setChannelAmount(channelAmounts);
		record.setCustName(realName);
		record.setRecordTime(new Date());
		record.setProductName(productName);
		record.setProductId(finalAudit.getProductId());
		record.setProductMonth(months);
		record.setPushType("1");
		record.setPushTypeVal("借款导入接口");
		record.setRecordId(PrimaryKeyUtil.getPrimaryKey());
		String json = object.toJSONString();

		// 链接红小宝测试路径
//		String url = "http://123.126.19.2:8040/lend/import/";//测试地址
//		String url = "http://lend.hoomxb.com/lend/import/";//正式地址
		String url = lendImport;//正式地址
		String result = HttpClientUtil.doPostJson(url, null, null, json);

		JSONObject resultObj = JSONObject.parseObject(result);
		// 验签
		boolean verify = CreateSerialNo.decode(resultObj.getJSONObject("generalInfo"));
		if (!verify) {
			return new Json(false, "红小宝验签失败!");
		}
		int status = resultObj.getIntValue("status");
		if (status == 1000) {
			// 合同借款金额
			String contractAmount = resultObj.getJSONObject("lendResult").getString("amount");
			BigDecimal contract = new BigDecimal(contractAmount);
			record.setContractAmount(contract);
			// 存入记录表数据
			hxbRecordMapper.insertSelective(record);
			// 平台服务费（y）
			String hxbServiceFees = resultObj.getJSONObject("lendResult").getString("hxbServiceFee");
			BigDecimal hxbServiceFee = new BigDecimal(hxbServiceFees);
			// 渠道服务费（z1）
			String channelServiceFees = resultObj.getJSONObject("lendResult").getString("channelServiceFee");
			BigDecimal channelServiceFee = new BigDecimal(channelServiceFees);
			// 渠道服务费（z2）
			String creditServiceFees = resultObj.getJSONObject("lendResult").getString("creditServiceFee");
			BigDecimal creditServiceFee = new BigDecimal(creditServiceFees);
			/**
			 * 
			 * 线上服务费存库
			 * 
			 * 
			 */
			HxbRate rate = hxbRateMapper.selectByApplyId(applyId);
			if(rate != null){
				rate.setApplyId(applyId);
				rate.setChannelServiceFee(channelServiceFee);
				rate.setCreditServiceFee(creditServiceFee);
				rate.setHxbServiceFee(hxbServiceFee);
				hxbRateMapper.updateByPrimaryKeySelective(rate);
			}else{
				rate = new HxbRate();
				rate.setApplyId(applyId);
				rate.setChannelServiceFee(channelServiceFee);
				rate.setCreditServiceFee(creditServiceFee);
				rate.setHxbServiceFee(hxbServiceFee);
				rate.setHoomxbId(PrimaryKeyUtil.getPrimaryKey());
				hxbRateMapper.insertSelective(rate);
			}
			// 获取一个json,再获取list,添加还款明细表
			JSONObject lendResult = resultObj.getJSONObject("lendResult");
			JSONArray repaymentPlanList = lendResult.getJSONArray("repaymentPlanList");
			Iterator<Object> jsonIterator = repaymentPlanList.iterator();
			/*
			 * 
			 * 
			 * 存入数据库之前先删除还款明细表，防止出现重复数据
			 * 
			 * 
			 */
			int countByApplyId = hxbReplaymentPlanMapper.countByApplyId(applyId);
			if (countByApplyId > 0) {
				hxbReplaymentPlanMapper.deleteByApplyId(applyId);
			}
			// 处理还款明细表数据
			while (jsonIterator.hasNext()) {
				JSONObject object1 = (JSONObject) jsonIterator.next();
				// 期序
				Integer phaseNumber = object1.getInteger("phaseNumber");
				// 当期应还本息
				double amount = object1.getDouble("amount");
				BigDecimal amountDecimal = BigDecimal.valueOf(amount);
				// 当期应还本金
				double principal = object1.getDouble("principal");
				BigDecimal principalDecimal = BigDecimal.valueOf(principal);
				// 提前还清应收总额:本期利息+剩余本金
				double inRepayTotalAmount = object1.getDouble("inRepayTotalAmount");
				BigDecimal inRepayTotalAmountDecimal = BigDecimal.valueOf(inRepayTotalAmount);
				// 提前还清减免渠道服务费1（z1）给至信
				double inRepayDerateChannelServiceFee = object1.getDouble("inRepayDerateChannelServiceFee");
				BigDecimal channel = BigDecimal.valueOf(inRepayDerateChannelServiceFee);
				// 提前还清减免渠道服务费2（z2）
				double inRepayDerateCreditServiceFee = object1.getDouble("inRepayDerateCreditServiceFee");
				BigDecimal credit = BigDecimal.valueOf(inRepayDerateCreditServiceFee);
				// 提前还清减免红小宝平台服务费（y）
				double inRepayDerateHxbServiceFee = object1.getDouble("inRepayDerateHxbServiceFee");
				BigDecimal hxb = BigDecimal.valueOf(inRepayDerateHxbServiceFee);
				// 当期应还利息
				double interest = object1.getDouble("interest");
				BigDecimal interestDecimal = BigDecimal.valueOf(interest);
				// 当期期初剩余本金
				double initialPrincipal = object1.getDouble("initialPrincipal");
				BigDecimal initialPrincipalDecimal = BigDecimal.valueOf(initialPrincipal);
				// 当期期末剩余本金
				double finalPrincipal = object1.getDouble("finalPrincipal");
				BigDecimal finalPrincipalDecimal = BigDecimal.valueOf(finalPrincipal);

				/**
				 * 存入线上还款明细表
				 */
				HxbReplaymentPlan plan = new HxbReplaymentPlan();
				plan.setPlanId(PrimaryKeyUtil.getPrimaryKey());
				plan.setApplyId(applyId);
				plan.setPhaseNumber(phaseNumber);
				plan.setAmount(amountDecimal);
				plan.setPrincipal(principalDecimal);
				plan.setInRepayTotalAmount(inRepayTotalAmountDecimal);
				plan.setChannelServiceFee(channel);
				plan.setCreditServiceFee(credit);
				plan.setHxbServiceFee(hxb);
				plan.setInterest(interestDecimal);
				plan.setInitialPrincipal(initialPrincipalDecimal);
				plan.setFinalPrincipal(finalPrincipalDecimal);
				hxbReplaymentPlanMapper.insertSelective(plan);
				/**
				 * 存入线下还款明细表(是因为合同展示以线下日期为准，所以线下还款明细表也存一份红小宝还款计划，以便于后期展示数据)
				 */
				/*
				 * SysRepaymentPlan replay = new SysRepaymentPlan();
				 * replay.setPlanId(PrimaryKeyUtil.getPrimaryKey());
				 * replay.setApplyId(applyId); replay.setRepayStatus(0);
				 * replay.setPreretuamtCredit(credit);// 提前还清减免渠道服务费2（z2）
				 * replay.setPreretuamtHxb(hxb);// 提前还清减免红小宝平台服务费
				 * replay.setPreretuamtChannel(channel);// 提前还清减免渠道服务费1（z1）给至信
				 * replay.setBal(initialPrincipalDecimal);// 剩余本金
				 * replay.setShouldInte(interestDecimal);// 应还利息
				 * replay.setShouldCapi(principalDecimal);// 应还本金
				 * replay.setShouldAmt(amountDecimal);// 应还金额=应还利息+应还本金
				 * replay.setShouldTerm(phaseNumber);// 应还期数
				 * replay.setAdvanceShould(inRepayTotalAmountDecimal);// 提前还款应还
				 * replay.setAdvanceMoney(inRepayTotalAmountDecimal);
				 * replaymentPlanMapper.insertSelective(replay);
				 */
			}
			return new Json(true,"推送成功！");
		}else{
			return new Json(false,"推送失败！失败原因"+resultObj.get("errMsg"));
		}
		
	}

}
