package com.hoomsun.app.api.controller.flow;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.core.enums.SystemParamEnum;
import com.hoomsun.core.model.SysContract;
import com.hoomsun.core.model.SysProduct;
import com.hoomsun.core.model.SystemParam;
import com.hoomsun.core.server.inter.SysContractServerI;
import com.hoomsun.core.server.inter.SysEmployeeServerI;
import com.hoomsun.core.server.inter.SysProductServerI;
import com.hoomsun.core.server.inter.SystemParamServerI;
import com.hoomsun.core.util.BillsDateRangeCfg;
import com.hoomsun.core.util.RepaymentMethodsNow;
import com.hoomsun.csas.sales.api.model.Annex;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.server.inter.AnnexServerI;
import com.hoomsun.csas.sales.api.server.inter.UserApplyServerI;
import com.hoomsun.message.server.inter.NoticeServerI;

/**
 * @author 刘栋梁 作者：刘栋梁 <br>
 *         创建时间：2017年12月20日 <br>
 *         描述： 1.app进件 申请记录
 *         http://192.168.3.19:8080/app-admin/web/activity/appproduct.do?ID=
 *         2.门店进件 申请记录
 *         http://192.168.3.19:8080/app-admin/web/activity/creproduct.do?ID=qq
 *         3.签约列表
 *         http://192.168.3.19:8080/app-admin/web/activity/tosignup.do?ID=qq
 *         4.签约详情
 *         http://192.168.3.19:8080/app-admin/web/activity/signdetail.do?applyId=&storeId
 *         4.签约
 *         http://192.168.3.19:8080/app-admin/web/activity/updatasign.do?applyId=
 *         6.流程节点
 *         http://192.168.3.19:8080/app-admin/web/activity/selectactivity.do?precessId=
 *         7.放款中/逾期的二级页面查询
 *         http://192.168.3.19:8080/app-admin/web/activity/selectpayinfo.do?applyId=805def0c5a624da2aa754ce8def78503
 *         7.合同详情
 *         http://192.168.3.19:8080/app-admin/web/activity/selectimg.do?applyId=
 *         8.提交前还款预览
 *         http://192.168.3.19:8080/app-admin/web/activity/repaymentDetail.do?productId=062808a160d44bc9a7224fcb0392f3ef&auditMoney=5000
 * 
 */

@Controller
@RequestMapping("web/activity")
public class ApplyInfoController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserApplyServerI userApplyServer;

	@Autowired
	private SysContractServerI sysContractServerI;

	@Autowired
	private SysEmployeeServerI sysEmployeeServerI;

	@Autowired
	private BillsDateRangeCfg billsDateRangeUtil;

	@Autowired
	private SysProductServerI productServer;

	@Autowired
	private NoticeServerI noticeServer;

	@Autowired
	private AnnexServerI annexServer;

	@Autowired
	private SystemParamServerI systemParamServer;

	@RequestMapping(value = "appproduct.do")
	@ResponseBody
	public Map<String, Object> selectAppproduct(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String ID = request.getParameter("ID"); // ID

		Map<String, Object> map = new HashMap<String, Object>(); // 返回数据
		try {
			List<UserApply> list = userApplyServer.selectAppAllData(ID);
			for (UserApply userApply : list) { // 合同状态 0 未放款 1 放款生效 2 结清 3 未结清 4
												// 提前结清 5 逾期
				if (userApply.getCon() == null) {
					SysContract SysContract = new SysContract();
					SysContract.setConStatus(0);
					userApply.setCon(SysContract);
				}
			}
			map.put("data", list);
			map.put("errorInfo", "查询成功");
			map.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", "数据查询失败！");
			map.put("errorCode", 1);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("单子状态使用时间：" + (endTime - startTime));
		}

		logger.info("allproductMap:" + map);
		return map;
	}

	@RequestMapping(value = "creproduct.do")
	@ResponseBody
	public Map<String, Object> selectCreproduct(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String ID = request.getParameter("ID"); // ID

		Map<String, Object> map = new HashMap<String, Object>(); // 返回数据
		try {
			List<UserApply> list = userApplyServer.selectAppAllCreData(ID);
			for (UserApply userApply : list) {
				if (userApply.getCon() == null) {
					SysContract SysContract = new SysContract();
					SysContract.setConStatus(0);
					userApply.setCon(SysContract);
				}
			}
			map.put("data", list);
			map.put("errorInfo", "查询成功");
			map.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", "数据查询失败！");
			map.put("errorCode", 1);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("单子状态使用时间：" + (endTime - startTime));
		}

		logger.info("allproductMap:" + map);
		return map;
	}

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-12-20
	 * @resource 签约界面的所有单子
	 * @LoggerAnnotation(moduleName = "客户单子相关", option = "查询签约列表")
	 */

	@RequestMapping(value = "/tosignup.do")
	@ResponseBody
	public Map<String, Object> selectToSignUp(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String ID = request.getParameter("ID");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<UserApply> list = userApplyServer.selectAppSignData(ID);
			map.put("data", list);
			map.put("errorInfo", "数据查询成功！！！");
			map.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", "数据查询失败！");
			map.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("待签约 查询  使用时间：" + (endTime - startTime));
		}

		logger.info(" 签约界面 :" + map);
		return map;
	}

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-08-31
	 * @resource 签约详情-----签约信息,放款页面使用统一接口
	 * @LoggerAnnotation(moduleName = "客户单子相关", option = "签约详情")
	 */
	@RequestMapping(value = "/signdetail.do")
	@ResponseBody
	public Map<String, Object> signdetail(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String applyId = request.getParameter("applyId");
		String storeId = request.getParameter("storeId");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> data = sysEmployeeServerI.findStorebyId(storeId);
			if (data != null) {
				UserApply userApply = userApplyServer.selectApplyTableByAppId(applyId);
				data.put("SIGNCONFIRM", userApply.getSignconfirm());
				data.put("SIGNCONFIRM_VAL", userApply.getSignconfirmVal());
				BigDecimal auditMoney = userApply.getAgreedAmount(); // 批准金额
				SysProduct product = productServer.findAllById(userApply.getAgreeProductId());
				SysContract contract = RepaymentMethodsNow.computeRepaymentCon(auditMoney, product);
				data.put("CONAMOUNT", contract.getConAmount());
				data.put("LOANAMOUNT", contract.getLoanAmount());
			}

			map.put("data", data);
			map.put("errorInfo", "数据查询成功！！！");
			map.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", "数据查询失败！");
			map.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("待签约 查询  使用时间：" + (endTime - startTime));
		}

		logger.info(" 签约界面 :" + map);
		return map;
	}

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-08-31
	 * @resource 签约状态修改 --触发线下的协议签订是否显示标识
	 * @LoggerAnnotation(moduleName = "客户单子相关", option = "签约成功推送")
	 */
	@RequestMapping(value = "/updatasign.do")
	@ResponseBody
	public Map<String, Object> updatasign(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String applyId = request.getParameter("applyId");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserApply hs = new UserApply();
			hs.setApplyId(applyId);
			hs.setSignconfirm(1);
			hs.setSignconfirmVal("人脸识别通过");
			userApplyServer.updateAppSignconfirm(hs);

			UserApply userApply = userApplyServer.selectApplyTableByAppId(applyId);
			String Msg = "";
			Msg = "您提交的" + userApply.getAgreedProductAlias() + "借款申请已完成线上签约，请您前往门店进行协议拟制";
			noticeServer.sendMsg(applyId, Msg, 1); // 申请成功推送
			map.put("errorInfo", "数据查询成功！！！");
			map.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", "数据查询失败！");
			map.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("签约状态修改 时间：" + (endTime - startTime));
		}

		logger.info("签约状态修改  :" + map);
		return map;
	}

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-12-20
	 * @resource 流程信息
	 * @LoggerAnnotation(moduleName = "客户单子相关", option = "查询客户单子流程节点")
	 */
	@RequestMapping(value = "selectactivity.do")
	@ResponseBody
	public Map<String, Object> selectActivity(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间

		String precessId = request.getParameter("precessId"); // 单子邦定身份证
		Map<String, Object> map = new HashMap<String, Object>(); // 返回数据

		try {
			Map<String, Object> para = new HashMap<String, Object>();
			para.put("precessId", precessId);
			List<Map<String, Object>> list = userApplyServer.selectActrivity(para);
			map.put("data", list);
			map.put("errorInfo", "查询成功");
			map.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", "数据查询失败！");
			map.put("errorCode", 1);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("单子状态使用时间：" + (endTime - startTime));
		}

		logger.info("allproductMap:" + map);
		return map;
	}

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-12-25
	 * @resource 借款详情
	 * @LoggerAnnotation(moduleName = "客户单子相关", option = "签约详情")
	 */

	@RequestMapping(value = "/selectpayinfo.do")
	@ResponseBody
	public Map<String, Object> selectPayinfo(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String applyId = request.getParameter("applyId");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SysContract sysContract = sysContractServerI.findinfoByApplyId(applyId);
			map.put("data", sysContract);
			map.put("errorInfo", "数据查询成功！！！");
			map.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", "数据查询失败！");
			map.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info(" 借款详情使用时间：" + (endTime - startTime));
		}

		logger.info("  借款详情 :" + map);
		return map;
	}

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-12-25
	 * @resource 借款详情---合同地址
	 * 
	 */

	@RequestMapping(value = "/selectimg.do")
	@ResponseBody
	public Map<String, Object> selectImg(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String applyId = request.getParameter("applyId");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Annex> list = annexServer.findByApplyIdImgType(applyId, 6);
			map.put("data", list);
			map.put("errorInfo", "借款详情---合同地址！！！");
			map.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", "数据查询失败！");
			map.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info(" 借款详情---合同地址：" + (endTime - startTime));
		}

		logger.info("  借款详情---合同地址 :" + map);
		return map;
	}

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-12-27
	 * @resource 还款预览
	 * @LoggerAnnotation(moduleName = "客户单子相关", option = "签约详情")
	 */

	@RequestMapping(value = "/repaymentDetail.do")
	@ResponseBody
	public Map<String, Object> repaymentdetail(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String productId = request.getParameter("productId");
		String auditMoney = request.getParameter("auditMoney");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SysProduct product = productServer.findAllById(productId);
			SystemParam systemParam = systemParamServer.findParamByKey(SystemParamEnum.PUSH_ONLINE.getKey());
			String paramValue = systemParam.getParamValue();
			Integer online = 0;
			if (!StringUtils.isBlank(paramValue)) {
				online = Integer.parseInt(paramValue);
			}
			SysContract contract = RepaymentMethodsNow.computeRepaymentPlan(new BigDecimal(auditMoney), new Date(), product, billsDateRangeUtil, online);

			map.put("data", contract);
			map.put("errorInfo", "数据查询成功！！！");
			map.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", "数据查询失败！");
			map.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("待签约 查询  使用时间：" + (endTime - startTime));
		}

		logger.info(" 签约界面 :" + map);
		return map;
	}

	@RequestMapping(value = "test.do")
	@ResponseBody
	public SysContract test() {
		BigDecimal auditMoney = new BigDecimal("100000");// 批准金额
		String prodId = "414c5be3bde94aae8c6c412a4145c3ab";
		SysProduct product = productServer.findAllById(prodId);
		SystemParam systemParam = systemParamServer.findParamByKey(SystemParamEnum.PUSH_ONLINE.getKey());
		String paramValue = systemParam.getParamValue();
		Integer online = 0;
		if (!StringUtils.isBlank(paramValue)) {
			online = Integer.parseInt(paramValue);
		}
		SysContract contract = RepaymentMethodsNow.computeRepaymentPlan(auditMoney, new Date(), product, billsDateRangeUtil, online);
		return contract;
	}
}
