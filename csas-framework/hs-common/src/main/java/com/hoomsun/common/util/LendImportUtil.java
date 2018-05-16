package com.hoomsun.common.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author liusong
 * @resource 借款导入接口util
 */

public class LendImportUtil {
	/**
	 * 处理向hxb推送的Json数据
	 */
	public static JSONObject parameterHandling(HttpServletRequest req) throws UnsupportedEncodingException {
		String loanRefId = req.getParameter("loanRefId");
		String loanId = req.getParameter("loanId");// 流水号（至信借款的唯一编号）
		JSONObject object = new JSONObject();
		// 1. generalInfo
		object.put("generalInfo", CreateSerialNo.sign());

		// 2. lend
		Map<String, Object> lend = new HashMap<String, Object>();// 存储第二层请求数据

		// 2.1 user
		Map<String, Object> user = new HashMap<String, Object>();// 存储第二层请求数据(第二层的user)
		String mail = req.getParameter("mail");
		String mobile = req.getParameter("mobile");
		String realName = req.getParameter("realName");
		String idCard = req.getParameter("idCard");
		String address = req.getParameter("address");
		String gender = req.getParameter("gender");
		String productName = req.getParameter("productName");
		if (gender.equals("男")) {
			gender = "1";
		} else if (gender.equals("女")) {
			gender = "0";
		}
		user.put("userRefId", loanId);// 至信用户的唯一编号
		user.put("mail", mail);// 邮箱
		user.put("mobile", mobile);// 手机号
		user.put("realName", realName);// 身份证真实姓名
		user.put("idCard", idCard);// 身份证号,只支持新版18位
		user.put("address", address);// 身份证地址
		user.put("gender", gender);// 1:男,0:女
		// 终审没有参数 bank
		user.put("cardNumber", "");// 银行卡号
		user.put("bankType", "");// 银行代码表
		user.put("bankAddress", "");// 支行详细名称
		user.put("province", "");// 开户省份
		user.put("city", "");// 开户城市

		// 2.2 loan
		Map<String, Object> loan = new HashMap<String, Object>();// 存储第二层请求数据(第二层的loan)
		loan.put("loanRefId", loanId);// 至信借款的唯一编号
		String title = req.getParameter("title");
		loan.put("title", "".equals(title) ? "其他" : title);// 借款标题
		String channelAmount = req.getParameter("channelAmount");
		loan.put("channelAmount", channelAmount);// 期望借款金额,用户实际到手的借款金额
		String months = req.getParameter("months");
		loan.put("months", months);// 借款周期（月）
		loan.put("description", "贷款用于" + title + "，以上信息已考察认证，同时，经审核借款人所提供资料真实有效，符合红小宝的借款审核标准。");// 暂时写死，至信没有传输数据(描述)
		loan.put("contractCode", loanRefId);// 选填，借款签约时的合同编号，合同编号唯一；若推送则使用渠道传递编号，若不推送则根据规则自动生成。
		loan.put("productName", productName);// 产品名:固定为"至信"
		String monthlyFee = req.getParameter("monthlyFee");
		loan.put("monthlyFee", monthlyFee); // 利率线下定(渠道方产品费率)月费率
		String graduation = req.getParameter("graduation");
		loan.put("graduation", graduation);// 学历: 高中或以下, 大专, 本科, 研究生或以上
		String university = req.getParameter("university");
		loan.put("university", university);// (毕业学校)
		String marriageStatus = req.getParameter("marriageStatus");
		// 婚姻状态: 未婚:0, 已婚:1,离异:2,丧偶:3
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
		loan.put("marriageStatus", marriageStatus);// 婚姻状态: 未婚:0,
													// 已婚:1,离异:2,丧偶:3
		String hasChild = req.getParameter("hasChild");
		loan.put("hasChild", hasChild);// 是否有子女:1,0(暂时写死，至信没有传输数据)
		String hasHouse = req.getParameter("hasHouse");
		// 02 无按揭购房 01 商业按揭购房 04 自建房 03 公积金按揭购房
		if (hasHouse.equals("")) {
			hasHouse = "0";
		} else {
			hasHouse = "1";
		}
		loan.put("hasHouse", hasHouse);// 是否有房:1,0
		String hasHouseLoan = req.getParameter("hasHouseLoan");
		if (hasHouse.equals("01") || hasHouse.equals("03")) {
			hasHouseLoan = "1";
		} else {
			hasHouseLoan = "0";
		}
		loan.put("hasHouseLoan", hasHouseLoan);// 是否有房贷:1,0
		String hasCar = req.getParameter("hasCar");
		loan.put("hasCar", hasCar);// 暂时写死，至信没有传输数据(是否有车:1,0)
		String hasCarLoan = req.getParameter("hasCarLoan");
		loan.put("hasCarLoan", hasCarLoan);// 暂时写死，至信没有传输数据(是否有车贷1,0)
		String companyIndustry = req.getParameter("companyIndustry");
		loan.put("companyIndustry", companyIndustry);// 公司所属行业
		String homeTown = req.getParameter("homeTown");
		loan.put("homeTown", homeTown);// 籍贯所在省
		String accountLocation = req.getParameter("accountLocation");
		loan.put("accountLocation", accountLocation);// 籍贯所在城市
		String residenceTel = req.getParameter("residenceTel");
		loan.put("residenceTel", residenceTel);// 居住地电话
		String residence = req.getParameter("residence");
		loan.put("residence", residence);// 居住地址
		String companyName = req.getParameter("companyName");
		loan.put("companyName", companyName);// 单位名称
		String jobStatus = req.getParameter("jobStatus");
		loan.put("jobStatus", jobStatus);// 职业状态
		String companyLocation = req.getParameter("companyLocation");
		loan.put("companyLocation", companyLocation);// 公司所在城市
		String companyProvince = req.getParameter("companyProvince");
		loan.put("companyProvince", companyProvince);// 公司所在省份
		String companyAddress = req.getParameter("companyAddress");
		loan.put("companyAddress", companyAddress);// 公司详细地址
		String companyCategory = req.getParameter("companyCategory");
		loan.put("companyCategory", companyCategory);// 公司类别
		String companyPost = req.getParameter("companyPost");
		loan.put("companyPost", companyPost);// 公司职位
		String monthlyIncome = req.getParameter("monthlyIncome");
		loan.put("monthlyIncome", monthlyIncome);// 工作月收入
		// 联系人信息
		String immediateName = req.getParameter("immediateName");
		String immediateRelationShip = req.getParameter("immediateRelationShip");
		String immediateTel = req.getParameter("immediateTel");
		String otherRelationName = req.getParameter("otherRelationName");
		String otherRelationShip = req.getParameter("otherRelationShip");
		String otherRelationTel = req.getParameter("otherRelationTel");
		loan.put("immediateName", immediateName);// 直系亲属名称
		loan.put("immediateRelationShip", immediateRelationShip);// 直系亲属关系
		loan.put("immediateTel", immediateTel);// 直系亲属电话
		loan.put("otherRelationName", otherRelationName);// 其他联系人名称
		loan.put("otherRelationShip", otherRelationShip);// 其他联系人关系
		loan.put("otherRelationTel", otherRelationTel);// 其他联系人手机
		lend.put("user", user);
		lend.put("loan", loan);
		// lend
		object.put("lend", lend);
		return object;
	}
}
