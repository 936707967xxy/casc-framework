/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.csas.audit.model.PhoneVerify;
import com.hoomsun.csas.audit.server.inter.PhoneVerifyServerI;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.model.UserCallVisual;
import com.hoomsun.csas.sales.api.model.UserContacts;
import com.hoomsun.csas.sales.api.model.vo.CreditVo;
import com.hoomsun.csas.sales.api.model.vo.FinancialVo;
import com.hoomsun.csas.sales.api.model.vo.OtherVo;
import com.hoomsun.csas.sales.api.server.inter.CallBillsServerI;
import com.hoomsun.csas.sales.api.server.inter.CreditServerI;
import com.hoomsun.csas.sales.api.server.inter.FinancialServerI;
import com.hoomsun.csas.sales.api.server.inter.OtherServerI;
import com.hoomsun.csas.sales.api.server.inter.UserApplyServerI;
import com.hoomsun.csas.sales.api.server.inter.UserCallVisualServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月1日 <br>
 * 描述：申请信息相关的查询控制层
 */
@Controller
public class ApplyController {
	@Autowired
	private UserApplyServerI userApplyDataServer;
	@Autowired
	private CreditServerI creditServer;// 征信
	@Autowired
	private FinancialServerI financialServer;
	@Autowired
	private CallBillsServerI callBillsServer;
	@Autowired
	private OtherServerI otherServer;
	@Autowired
	private PhoneVerifyServerI phoneVerifyServer;
	@Autowired
	private UserCallVisualServerI userCallVisualServer;

	// 获取申请数据信息
	@RequestMapping("/sys/user/apply/data.do")
	@ResponseBody
	public UserApply findApply(String applyId, HttpServletRequest request) {
		if (StringUtils.isBlank(applyId)) {
			return null;
		}

		return userApplyDataServer.findApplyById(applyId);
	}

	// 获取征信数据信息
	@RequestMapping("/sys/user/credit/data.do")
	@ResponseBody
	public CreditVo findCredit(String applyId, HttpServletRequest request) {
		CreditVo creditVo = creditServer.findByApplyId(applyId);
		return creditVo;
	}

	// 获取财务数据
	@RequestMapping("/sys/user/financial/data.do")
	@ResponseBody
	public FinancialVo findFinancial(String applyId, HttpServletRequest request) {
		return financialServer.findByApplyId(applyId);
	}

	// 获取通话详单信息
	@RequestMapping("/sys/user/callbill/data.do")
	@ResponseBody
	public JSONObject findCallBills(String applyId, HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		List<UserContacts> contacts = callBillsServer.findByApplyId(applyId).getContacts();

		List<UserContacts> driving = new ArrayList<UserContacts>();
		List<UserContacts> unDriving = new ArrayList<UserContacts>();
		for (UserContacts cnts : contacts) {
			Integer online = cnts.getIsFillIn();
			if (null == online || 0 == online) {
				unDriving.add(cnts);
			} else {
				driving.add(cnts);
			}
		}
		
		// online 0:自动获取（不可审核 通话详单） 1:客户填写(联系人)

		obj.put("contacts", driving);// 联系人
		obj.put("cellBills", unDriving);// 通话详单
		List<PhoneVerify> phoneVerifies = phoneVerifyServer.findByApplyId(applyId);
		obj.put("phoneVerifies", phoneVerifies);// 审核信息

		// 通话详单可视化数据
		UserCallVisual visual = userCallVisualServer.findByApply(applyId);
		buildChartData(visual, obj);
		return obj;
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月5日 <br>
	 * 描述：可视化数据封装
	 */
	private void buildChartData(UserCallVisual visual, JSONObject obj) {
		if (null == visual) {
			buildChartDefault(obj);
			return;
		}
		// 通话连续性 —— 每月全部、主叫的通话频次
		String oneGraphicData = visual.getOneGraphicData();
		if (StringUtils.isNotBlank(oneGraphicData)) {
			JSONArray array = new JSONArray();
			JSONArray charts = JSONObject.parseArray(oneGraphicData);
			for (Object object : charts) {
				JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(object));
				// { name: "06月", value: [30.3, 40.6] }, // value值 第一个为主叫，第二为被叫
				JSONObject data = new JSONObject();
				data.put("name", result.getString("month"));
				String[] arr = new String[] { result.getString("dialingFrequency"), result.getString("callFrequency") };
				data.put("value", arr);
				array.add(data);
			}
			obj.put("dataOne", array);
		}

		String twoGraphicData = visual.getTwoGraphicData();
		if (StringUtils.isNotBlank(twoGraphicData)) {
			JSONArray array = new JSONArray();
			JSONArray charts = JSONObject.parseArray(twoGraphicData);
			for (Object object : charts) {
				JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(object));
				// { name: "06月", value: [30.3, 40.6] }, // value值 第一个为主叫，第二为被叫
				// "calledFrequency": "20.7",//被叫匹配率
				// "dialingFrequency": "17.2",//主叫匹配率
				// "month": "08月"//月份
				JSONObject data = new JSONObject();
				data.put("name", result.getString("month"));
				String[] arr = new String[] { result.getString("dialingFrequency"), result.getString("calledFrequency") };
				data.put("value", arr);
				array.add(data);
			}
			obj.put("dataTwo", array);
		}

		// 无需处理数据
		String threeGraphicData = visual.getThreeGraphicData();
		if (StringUtils.isNotBlank(threeGraphicData)) {
			JSONArray charts = JSONObject.parseArray(threeGraphicData);
			obj.put("dataThree", charts);
		}

		String fourGraphicData = visual.getFourGraphicData();
		if (StringUtils.isNotBlank(fourGraphicData)) {
			JSONArray array = new JSONArray();
			JSONArray charts = JSONObject.parseArray(fourGraphicData);
			for (Object object : charts) {
				JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(object));
				// {name: '西安', value: [40,30]}, // value值 第一个为主叫，第二为被叫
				JSONObject data = new JSONObject();
				data.put("name", result.getString("region"));
				String[] arr = new String[] { result.getString("dialingFrequency"), result.getString("calledFrequency") };
				data.put("value", arr);
				array.add(data);
			}
			obj.put("dataFour", array);
		}

		// 空数据 待开发
		String fiveGraphicData = visual.getFiveGraphicData();
		if (StringUtils.isNotBlank(fiveGraphicData)) {
			JSONArray charts = JSONObject.parseArray(fiveGraphicData);
			obj.put("calledFrequency", charts);
		} else {
			// {name: '无', value: 0}
			JSONObject data = new JSONObject();
			data.put("name", "无");
			data.put("value", "0");
			JSONArray array = new JSONArray();
			array.add(data);
			obj.put("dataFive", array);

			data = new JSONObject();
			data.put("name", "无");
			data.put("value", "0");
			array = new JSONArray();
			array.add(data);
			obj.put("dataSix", array);
		}

		// dataSeven --> sixGraphicData
		String sixGraphicData = visual.getSixGraphicData();
		if (StringUtils.isNotBlank(sixGraphicData)) {
			JSONArray array = new JSONArray();
			JSONArray charts = JSONObject.parseArray(sixGraphicData);

			for (Object object : charts) {
				JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(object));
				JSONObject data = new JSONObject();
				data.put("name", result.getString("name"));
				String[] arr = new String[] { result.getString("dialingFrequency"), result.getString("calledFrequency"), result.getString("timeNum"), result.getString("phone"), };
				data.put("value", arr);
				array.add(data);
			}
			obj.put("dataSeven", array);
			// {name: '张三', value:[34,52,23,134000000]}, // value
			// 值，第一个为主叫次数，第二个为被叫次数，第三个通话时长，第四个为电话号码
			// {
			// "calledFrequency": 78,//被叫频次
			// "phone": "15129931805",//手机号码
			// "dialingFrequency": 59,//主叫频次
			// "timeNum": 10462,//通话时长
			// "name": "高娟"//姓名
			// },
		}

		// sevenGraphicData --> dataEight
		String sevenGraphicData = visual.getSevenGraphicData();
		if (StringUtils.isNotBlank(sevenGraphicData)) {
			JSONArray array = new JSONArray();
			JSONArray charts = JSONObject.parseArray(sevenGraphicData);
			for (Object object : charts) {
				JSONObject result = JSONObject.parseObject(JSONObject.toJSONString(object));
				JSONObject data = new JSONObject();
				data.put("name", result.getString("name"));
				String[] arr = new String[] { result.getString("radius"), result.getString("angle"), result.getString("phone") };
				data.put("value", arr);
				array.add(data);
			}
			obj.put("dataEight", array);
			// {name: '张三', 'value': [0.7,180,1340000000]}, // value
			// 值，第一个为距离圆心距离，第二个为角度，第三个为电话号码
			// {
			// "phone": "15129931805",//手机号码
			// "name": "高娟",//姓名
			// "radius": "0.3",//圆心距离
			// "angle": 103//角度
			// },
		}

	}

	private void buildChartDefault(JSONObject obj) {
		// 通话连续性 —— 每月全部、主叫的通话频次
		JSONArray array = new JSONArray();
		// { name: "06月", value: [30.3, 40.6] }, // value值 第一个为主叫，第二为被叫
		JSONObject data = new JSONObject();
		data.put("name", "无");
		String[] arr = new String[] { "0", "0" };
		data.put("value", arr);
		array.add(data);
		obj.put("dataOne", array);

		array = new JSONArray();
		// { name: "06月", value: [30.3, 40.6] }, // value值 第一个为主叫，第二为被叫
		// "calledFrequency": "20.7",//被叫匹配率
		// "dialingFrequency": "17.2",//主叫匹配率
		// "month": "08月"//月份
		data = new JSONObject();
		data.put("name", "无");
		arr = new String[] { "0", "0" };
		data.put("value", arr);
		array.add(data);
		obj.put("dataTwo", array);

		// 无需处理数据
		array = new JSONArray();
		array.add(new String[] { "0", "0", "0" });
		obj.put("dataThree", array);

		array = new JSONArray();
		// {name: '西安', value: [40,30]}, // value值 第一个为主叫，第二为被叫
		data = new JSONObject();
		data.put("name", "无");
		arr = new String[] { "0", "0" };
		data.put("value", arr);
		array.add(data);
		obj.put("dataFour", array);

		// 空数据 待开发 同行业电话撞库
		// {name: '无', value: 0}
		data = new JSONObject();
		data.put("name", "无");
		data.put("value", "0");
		array = new JSONArray();
		array.add(data);
		obj.put("dataFive", array);

		data = new JSONObject();
		data.put("name", "无");
		data.put("value", "0");
		array = new JSONArray();
		array.add(data);
		obj.put("dataSix", array);

		// dataSeven --> sixGraphicData
		array = new JSONArray();
		data = new JSONObject();
		data.put("name", "无");
		arr = new String[] { "0", "0", "0", "0" };
		data.put("value", arr);
		array.add(data);
		obj.put("dataSeven", array);

		// sevenGraphicData --> dataEight
		data = new JSONObject();
		data.put("name", "无");
		arr = new String[] { "0", "0", "0" };
		data.put("value", arr);
		array.add(data);
		obj.put("dataEight", array);
		// {name: '张三', 'value': [0.7,180,1340000000]}, // value
		// 值，第一个为距离圆心距离，第二个为角度，第三个为电话号码
		// {
		// "phone": "15129931805",//手机号码
		// "name": "高娟",//姓名
		// "radius": "0.3",//圆心距离
		// "angle": 103//角度
		// },

	}

	// 获取其他数据信息
	@RequestMapping("/sys/user/other/data.do")
	@ResponseBody
	public OtherVo findOther(String applyId, HttpServletRequest request) {
		OtherVo otherVo = otherServer.findByApplyId(applyId);
		if (otherVo == null) {
			otherVo = new OtherVo();
		}
		return otherVo;
	}
	
	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年12月18日 <br>
	 * 描述：提交回显
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/sys/applymodel/submitfindbyid.do")
	@ResponseBody
	public UserApply submitFindById(String applyId, HttpServletRequest request) {
		UserApply applyModel = userApplyDataServer.submitFindById(applyId);
		return applyModel;
	}
}
