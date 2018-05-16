package com.hoomsun.app.api.controller.credit;


import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hoomsun.after.api.server.ChangeInfoServer;
import com.hoomsun.app.api.help.SmsauController;
import com.hoomsun.app.api.model.AfreshProtoinfo;
import com.hoomsun.app.api.server.inter.AfreshProtoinfoServerI;
import com.hoomsun.common.util.HttpClientUtil;
import com.hoomsun.common.util.JQBKUtils;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.core.model.Bankinfo;
import com.hoomsun.core.model.SysContract;
import com.hoomsun.core.server.inter.BankinfoServerI;
import com.hoomsun.core.server.inter.SysContractServerI;
import com.hoomsun.csas.sales.api.model.NameAuthentication;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.server.inter.NameAuthenticationServerI;
import com.hoomsun.csas.sales.api.server.inter.UserApplyServerI;

/**
 * 
 * @author 作者：liudongliang <br>
 * @Date 创建时间：2017-12-04 <br>
 * @Description 1.支持的银行卡类型
 *              192.168.3.19:8080/app-admin/web/personalbank/bankinfo.do
 *              3.绑定银行卡是否添加支付密码 
 *              192.168.3.19:8080/app-admin/web/personalbank/isaddbankcode.do?ID=
 *              4.交易密码,银行卡发送短信
 *              192.168.3.19:8080/app-admin/web/personalbank/bankcodesend.do?PHONE=
 *              5.交易密码，银行卡发送短信-验证
 *              192.168.3.19:8080/app-admin/web/personalbank/bankcodecheck.do?PHONE=&CODE=
 *              6.交易密码是否与之前一直
 *              192.168.3.19:8080/app-admin/web/personalbank/bankpwdtruecheck.do?ID=&BANKPWD
 *              7.添加支付密码 
 *              192.168.3.19:8080/app-admin/web/personalbank/addbankpwd.do?ID=&BANKPWD=
 *              8.银行卡三要素验证           
 *              192.168.3.19:8080/app-admin/web/personalbank/depbankcheck.do?idcode=&bankcode=
 *              9. 银行卡添加     额外卡 
 *              192.168.3.19:8080/app-admin/web/personalbank/addProtoinfo.do?ID=0374431c90a942ff96993a90231bb889&idCard=idCard1
 *              &ACCBANKNAME=ACCBANKNAME1&ACCBANKID=ACCBANKID1&ACCNAME=ACCNAME1&ACCNO=ACCNO1&MOBILE=MOBILE1
 *              &TYPE          1  默认卡  == 还款卡              2  其他卡      3 工资卡
 *              11.设置银行卡 
 *              192.168.3.19:8080/app-admin/web/personalbank/updatebank.do?protoinfoId&Code    设置还款卡  1            删除       0           解绑   2
 */
@Controller
@RequestMapping("web/personalbank")
public class PersonalBankController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BankinfoServerI bankServer;
	
	@Autowired
	private AfreshProtoinfoServerI afreshProtoServer;

	@Autowired
	private NameAuthenticationServerI hsServerI;
	
	@Autowired
	private UserApplyServerI userApplyServerI;
	
	@Autowired
	private ChangeInfoServer changeInfoServer;
	
	@Autowired
	private SysContractServerI sysContractServerI;
	
	/**
	 * @Description     支持添加的银行卡下拉列表
	 * @param 
	 * @return  Map
	 * @Date    2017-12-04
	 * @LoggerAnnotation(moduleName = "银行卡信息接口", option = "银行卡下拉列表展示")
	 */
	
	@RequestMapping("bankinfo.do")
	@ResponseBody
	public Map<String, Object> bankinfo(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Bankinfo> list = bankServer.findAllData();
			result.put("data", list);
			result.put("errorInfo", "银行卡下拉列表拉去成功");
			result.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("银行卡下拉列表使用时间：" + (endTime - startTime));
		}
		logger.info("银行卡下拉列表:" + result);
		return result;
	}
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年9月21日 <br>
	 * 描述： 恒丰验证第一步
	 * 
	 * @param request
	 * @return
	 * @LoggerAnnotation(moduleName = "银行卡信息接口", option = "恒丰验证第一步")
	 */
	
	@RequestMapping(value = "addProtoinfoByBasicInfo.do", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> addProtoinfoByBasicInfo(HttpServletRequest request) {

		String ACCNAME = request.getParameter("ACCNAME"); // 客户姓名
		String idCard = request.getParameter("idCard"); // 身份证号码
		String ACCNO = request.getParameter("ACCNO"); // 银行卡号
		String MOBILE = request.getParameter("MOBILE"); // 手机号
		String GGcustomerId = request.getParameter("GGcustomerId"); // 客户号
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errorCode", 1);
		result.put("errorInfo", "恒丰第一步鉴权失败，请重试");
		try {
			Map<String, String> res = JQBKUtils.jqbka(ACCNAME, idCard, ACCNO, MOBILE, GGcustomerId);

			result.put("errorCode", res.get("message"));
			result.put("errorInfo", res.get("errorMessage"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result.toString());
		return result;
	}

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年9月21日 <br>
	 * 描述： 恒丰验证第二步
	 * 
	 * @param request
	 * @return
	 * @LoggerAnnotation(moduleName = "银行卡信息接口", option = "恒丰验证第二步")
	 * 
	 */
	
	@RequestMapping(value = "addProtoinfoByPhoneCode.do", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> addProtoinfoByPhoneCode(HttpServletRequest request) {
		String ACCNAME = request.getParameter("ACCNAME"); // 客户姓名
		String idCard = request.getParameter("idCard"); // 身份证号码
		String ACCNO = request.getParameter("ACCNO"); // 银行卡号
		String MOBILE = request.getParameter("MOBILE"); // 手机号
		String GGcustomerId = request.getParameter("GGcustomerId"); // 客户号
		String phoneCode = request.getParameter("phoneCode"); // 手机验证码
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errorInfo", "恒丰绑卡验证码认证失败，请重试");
		result.put("errorCode", 1);
		try {
			Map<String, String> res = JQBKUtils.authentication(ACCNAME, idCard, ACCNO, MOBILE, GGcustomerId, phoneCode);
			result.put("errorCode", res.get("message"));
			result.put("errorInfo", res.get("errorMessage"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	/**
	 * @Description     银行卡添加 第一张卡 还款卡
	 * @param           
	 * @return  Map
	 * @Date    2018-01-06
	 * @LoggerAnnotation(moduleName = "银行卡信息接口", option = "银行卡添加")
	 * 
	 */	
	@RequestMapping("addProtoinfo.do")
	@ResponseBody
	public Map<String, Object> addProtoinfo(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String ID = request.getParameter("ID");
		// String idCard = request.getParameter("idCard"); // 关联身份证
		String ACCBANKNAME = request.getParameter("ACCBANKNAME"); // 银行名称
		String ACCBANKID = request.getParameter("ACCBANKID"); // 银行名称编号
		String ACCNAME = request.getParameter("ACCNAME"); // 开户人
		String ACCNO = request.getParameter("ACCNO"); // 开户账号
		String MOBILE = request.getParameter("MOBILE"); // 手机号
		
		String BRANCHNAME_PROV_CODE = request.getParameter("BRANCHNAME_PROV_CODE"); // 开户支行省编码
		String BRANCHNAME_PROV_NAME = request.getParameter("BRANCHNAME_PROV_NAME"); // 开户支行省
		String BRANCHNAME_CITY_CODE = request.getParameter("BRANCHNAME_CITY_CODE"); // 开户支行市编码
		String BRANCHNAME_CITY_NAME = request.getParameter("BRANCHNAME_CITY_NAME"); // 开户支行市
		String BRANCHNAME_ADDRESS = request.getParameter("BRANCHNAME_ADDRESS"); // 开户支行详细地址
		String TYPE = request.getParameter("TYPE"); // 类型
		String IS_SALARY = request.getParameter("IS_SALARY"); // 是否工资卡
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			logger.info("银行卡短信验证传值" + MOBILE);
			List<AfreshProtoinfo> list=afreshProtoServer.selectByAccno(ACCNO);
			if(list.size()>=1){
				result.put("errorInfo", "已添加过该银行卡");
				result.put("errorCode", 1);
				return result;
			}
			
			AfreshProtoinfo Protoinfo = new AfreshProtoinfo();
			Protoinfo.setFkId(ID);
			Protoinfo.setAccname(ACCNAME);
			Protoinfo.setAccno(ACCNO);
			Protoinfo.setAccbankid(ACCBANKID);
			Protoinfo.setAccbankname(ACCBANKNAME);
			Protoinfo.setMobile(MOBILE);
			Protoinfo.setBranchnameProvCode(BRANCHNAME_PROV_CODE);
			Protoinfo.setBranchnameProvName(BRANCHNAME_PROV_NAME);
			Protoinfo.setBranchnameCityCode(BRANCHNAME_CITY_CODE);
			Protoinfo.setBranchnameCityName(BRANCHNAME_CITY_NAME);
			Protoinfo.setBranchnameAddress(BRANCHNAME_ADDRESS);
			Protoinfo.setDeleteFlag("1");
			Protoinfo.setDeleteFlagVal("生效");
			
			
			if("0".equals(IS_SALARY)){
				Protoinfo.setIsSalary(IS_SALARY);
				Protoinfo.setIsSalaryVal("工资卡");
			}else{
				Protoinfo.setIsSalary(IS_SALARY);
				Protoinfo.setIsSalaryVal("非工资卡");
			}
			
			if("1".equals(TYPE)){   //第一张   还款卡				
				Protoinfo.setType("1");
				Protoinfo.setTypeVal("还款卡");
				Protoinfo.setIsDefault("0");
				Protoinfo.setIsDefaultVal("默认卡");
				
			}if("2".equals(TYPE)){
				Protoinfo.setType("2");
				Protoinfo.setTypeVal("其他卡");
				Protoinfo.setIsDefault("1");
				Protoinfo.setIsDefaultVal("非默认卡");
			}
			

			Protoinfo.setProtoinfoId(PrimaryKeyUtil.getPrimaryKey());
			int i = afreshProtoServer.insertSelective(Protoinfo);
			if (i == 0) {
				result.put("errorInfo", "添加银行卡失败，请重试");
				result.put("errorCode", 1);
				return result;
			}
			
			result.put("data", Protoinfo);
			result.put("errorInfo", "添加银行卡成功");
			result.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("银行卡添加使用时间：" + (endTime - startTime));
		}
		logger.info("银行卡添加:" + result);

		return result;
	}
	
	
	
	
	

	/**
	 * @Description     银行卡修改                                         设置还款卡   0  /删除            1
	 * @param           
	 * @return  Map
	 * @Date    2018-01-06
	 * @LoggerAnnotation(moduleName = "银行卡信息接口", option = "判断是否添加支付密码")
	 * 
	 */	
	
	@RequestMapping(value = "/updatebank.do")
	@ResponseBody
	public Map<String, Object> updatebank(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String protoinfoId = request.getParameter("protoinfoId");
		String Code = request.getParameter("Code");    //    删除 0                 设置还款卡   1           解绑   2
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			AfreshProtoinfo Protoinfo = afreshProtoServer.selectByPrimaryKey(protoinfoId);
			if("0".equals(Protoinfo.getIsDefault()) && "0".equals(Code)){
				map.put("errorInfo", "默认银行卡不支持删除");
				map.put("errorCode", 1);
				return map;
			}
			
			if("1".equals(Protoinfo.getType()) && "0".equals(Code)){
				map.put("errorInfo", "还款卡不支持删除,请先更换还款卡");
				map.put("errorCode", 1);
				return map;
			}
			
			
			/*if("0".equals(Protoinfo.getIsSalary())){
				map.put("errorInfo", "工资卡不能修改  ");
				map.put("errorCode", 1);
				return map;
			}*/
			
			Map<String, String>  constatus=new HashMap<String, String>();
			constatus.put("bankNo",  Protoinfo.getAccno());
			List<SysContract> list=sysContractServerI.findConPayData(constatus);
			
			
			Calendar now = Calendar.getInstance();  
	        int nowDate= now.get(Calendar.DAY_OF_MONTH);
			for(SysContract sysContract:list){
				int payDate=Integer.parseInt(sysContract.getPayDate());
				if(payDate-nowDate<=3    && payDate-nowDate>=0 ){
					map.put("errorInfo", "抱歉，当前为结算期，请于结算期后再进行更换  ");
					map.put("errorCode", 1);
					return map;
				}
			}
			
			
			Map<String,Object>  para=new HashMap<String, Object>();
			if("1".equals(Code)){
				
				para.put("type", "1");
				para.put("typeVal", "还款卡");
				para.put("protoinfoId", protoinfoId);
				afreshProtoServer.updateByType(Protoinfo.getFkId());   //原先还款卡变成个人卡 
				int i=afreshProtoServer.updateByProtoinfoId(para);
				if(i==1){
					//修改所有还款卡----------贷后的修改
					List<UserApply>    AccList= userApplyServerI.selectChangeAcc(Protoinfo.getFkId());
					for(UserApply  apply:AccList){
						changeInfoServer.updateloanbal(Protoinfo.getAccbankid(),apply.getApplyId(),
								Protoinfo.getMobile() ,Protoinfo.getAccno());						
					}
															
					map.put("errorInfo", "还款卡设置成功 ");
					map.put("errorCode", 0);
				}else{
					map.put("errorInfo", "还款卡设置失败 ");
					map.put("errorCode", 1);
				}
				
			}if("0".equals(Code)){
				int i=afreshProtoServer.deleteByProtoinfoId(protoinfoId);
				if(i==1){
					map.put("errorInfo", "删除成功 ");
					map.put("errorCode", 0);
				}else{
					map.put("errorInfo", "删除失败 ");
					map.put("errorCode", 1);
				}
				
			}/*if("2".equals(Code)){
				para.put("protoinfoId", protoinfoId);
				para.put("type", "2");
				para.put("typeVal", "其他卡");
				para.put("protoinfoId", protoinfoId);
				int i=afreshProtoServer.updateByProtoinfoId(para);
				if(i==1){
					map.put("errorInfo", "解绑成功");
					map.put("errorCode", 0);
				}else{
					map.put("errorInfo", "解绑失败");
					map.put("errorCode", 1);
				}
				
			}*/
			
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", "网络异常，请稍后！！");
			map.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("银行卡修改是否是  还款卡   ：" + (endTime - startTime));
		}
		logger.info("银行卡修改是否是  还款卡    " + map);
		return map;
	}

	
	
	
	/**
	 * @Description     银行卡添加 
	 * @param           
	 * @return  Map
	 * @Date    2017-12-04
	 * @LoggerAnnotation(moduleName = "银行卡信息接口", option = "判断是否添加支付密码")
	 * 
	 */	
	
	@RequestMapping(value = "/isaddbankcode.do")
	@ResponseBody
	public Map<String, Object> isAddBankcode(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String ID = request.getParameter("ID");
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			NameAuthentication login = hsServerI.selectByPrimaryKey(ID);
			if (login.getBankpwd() == null || ("").equals(login.getBankpwd())) {
				map.put("errorInfo", "未添加密码");
				map.put("errorCode", 1);
			} else {
				map.put("errorInfo", "已添加密码");
				map.put("errorCode", 0);
			}

		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", "网络异常，请稍后！！");
			map.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("判断是否添加支付密码  ：" + (endTime - startTime));
		}
		logger.info("判断是否添加支付密码   " + map);
		return map;
	}

	
	
	/**
	 * @Description     交易密码,银行卡发送短信
	 * @param           
	 * @return  Map
	 * @Date    2017-12-04
	 * @LoggerAnnotation(moduleName = "银行卡信息接口", option = "交易密码,银行卡发送短信")
	 * 
	 */		
	@RequestMapping("bankcodesend.do")
	@ResponseBody
	public Map<String, Object> bankcodesend(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String phone = request.getParameter("PHONE");
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			Random random = new Random();
			int n = random.nextInt(899999) + 100000;
			request.getSession().setAttribute(phone, n + "");

			boolean json = true;
			// Account Sid
			String accountSid = "138bc91472ac5b5192195669d9246d71";
			// Auth Token
			String token = "b85406e6f8c9ba1a6dec6d34a66bb52e";
			/*
			 * // appId String appId = "1939dac76141479b8a33895ab5dcfd34"; //
			 * 短信模板templateId String templateId = "33956";
			 */
			// appId
			String appId = "775db3d12adf4bcf9e9d5f576e11d74d";
			// 短信模板templateId
			String templateId = "129022";
			// 参数
			String to = phone;
			String para = n + "";
			logger.info(to + "\t" + n);
			// 发短信
			String flag = SmsauController.TemplateSMS(json, accountSid, token, appId, templateId, to, para);
			if ("0".equals(flag)) {// 成功
				result.put("errorCode", 0);
				result.put("errorInfo", "短信发送成功");
			} else if ("1".equals(flag)) {// 失败
				result.put("errorCode", 1);
				result.put("errorInfo", "短信发送失败");
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "短信发送失败！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("银行卡发送短信：" + (endTime - startTime));
		}
		logger.info("银行卡发送短信:" + result);

		return result;
	}

	/**
	 * @Description     交易密码，银行卡发送短信-验证
	 * @param           
	 * @return  Map
	 * @Date    2017-12-04
	 * @LoggerAnnotation(moduleName = "银行卡信息接口", option = "交易密码，银行卡发送短信-验证")
	 * 
	 */	
	
	@RequestMapping("bankcodecheck.do")
	@ResponseBody
	public Map<String, Object> bankcodecheck(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		HttpSession session = request.getSession();
		String PHONE = request.getParameter("PHONE");
		String CODE = request.getParameter("CODE");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String sessioncode = session.getAttribute(PHONE).toString();
			if (sessioncode.equals(CODE)) {
				result.put("errorInfo", "验证码正确！！");
				result.put("errorCode", 0);
				// 清除验证码
				//session.removeAttribute(PHONE);
			} else {
				result.put("errorInfo", "验证码错误！");
				result.put("errorCode", 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("银行卡发送短信-验证 ：" + (endTime - startTime));
		}
		logger.info("银行卡发送短信-验证  :" + result);

		return result;
	}

	
	/**
	 * @Description     交易密码是否与之前一直
	 * @param           
	 * @return  Map
	 * @Date    2017-12-04
	 * @LoggerAnnotation(moduleName = "银行卡信息接口", option = "判断交易密码是否与之前一致")
	 * 
	 */	
	@RequestMapping("bankpwdtruecheck.do")
	@ResponseBody
	public Map<String, Object> bankPwdtruecheck(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String ID = request.getParameter("ID");
		String BANKPWD = request.getParameter("BANKPWD");
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			NameAuthentication login = hsServerI.selectByPrimaryKey(ID);
			if ((BANKPWD).equals(login.getBankpwd())) {
				result.put("errorInfo", "交易密码与上次不符！！");
				result.put("errorCode", 1);
			} else {
				result.put("errorInfo", "交易密码与上次一致！！");
				result.put("errorCode", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info(" 交易密码是否与之前一直  ：" + (endTime - startTime));
		}
		logger.info(" 交易密码是否与之前一直   :" + result);

		return result;
	}

	
	/**
	 * @Description     添加支付密码
	 * @param           
	 * @return  Map
	 * @Date    2017-12-04
	 * @LoggerAnnotation(moduleName = "银行卡信息接口", option = "添加支付密码")
	 * 
	 */	
	
	@RequestMapping("addbankpwd.do")
	@ResponseBody
	public Map<String, Object> addbankpwd(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String ID = request.getParameter("ID");
		String BANKPWD = request.getParameter("BANKPWD");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			logger.info("支付传值" + ID + "--" + BANKPWD);
			// 添加支付密码
			NameAuthentication Hs = new NameAuthentication();
			Hs.setId(ID);
			Hs.setBankpwd(BANKPWD);

			int i = hsServerI.updateByPrimaryKeySelective(Hs);
			if (i == 0) {
				result.put("errorInfo", "添加支付密码失败 ！");
				result.put("errorCode", 1);
			} else {
				result.put("errorInfo", "添加支付密码成功！");
				result.put("errorCode", 0);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("添加支付密码使用时间：" + (endTime - startTime));
		}
		logger.info("添加支付密码:" + result);

		return result;
	}

	
	/**
	 * @Description     银行卡三要素验证
	 * @param           
	 * @return  Map
	 * @Date    2017-12-04
	 * @LoggerAnnotation(moduleName = "银行卡信息接口", option = "银行卡三要素验证")
	 * 
	 */		
	@RequestMapping("depbankcheck.do")
	@ResponseBody
	public Map<String, Object> depBankCheck(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String idcode = request.getParameter("idcode");
		String bankcode = request.getParameter("bankcode");
		String name = request.getParameter("name");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String url = "http://tianxingshuke.com/api/rest/common/organization/auth"; // 服务码
			Map<String, Object> querys=new HashMap<String, Object>();
			querys.put("account", "hsjr");
			querys.put("signature","49f12d56335d466da447591605e3087b");
			String str=HttpClientUtil.doPost(url, null, null, querys);
			logger.info("银行卡获取授权="+str);
			Map map = JSON.parseObject(str);
			// 判断是否成功获取机构编码
			String back = map.get("success") + "";
			Map datamap = JSON.parseObject(map.get("data")+"");
			if (back.equals("true")) {
				String checkUrl = "http://tianxingshuke.com/api/rest/unionpay/auth/3element";
				       
				String accessToken = (String) datamap.get("accessToken");
				Map<String, Object> checkQuerys=new HashMap<String, Object>();
				checkQuerys.put("account", "hsjr");
				checkQuerys.put("accessToken",accessToken);
				checkQuerys.put("name", name);
				checkQuerys.put("idCard", idcode);
				checkQuerys.put("accountNO", bankcode);
				
				String checkStr=HttpClientUtil.doGet(checkUrl, null, checkQuerys);
				logger.info("银行卡三要素="+checkStr);
				Map checkMmap = JSON.parseObject(checkStr);
				String checkBack = checkMmap.get("success") + "";
				if("false".equals(checkBack)){
					result.put("errorInfo", "银行卡帐号与当前注册人不符!!");
					result.put("errorCode", 1);
				}else{
					//true  不一定是通过 
					Map data = JSON.parseObject(checkMmap.get("data") + "");
					String checkStatus = data.get("checkStatus") + "";
					if("SAME".equals(checkStatus) || "ACCOUNTNO_UNABLE_VERIFY".equals(checkStatus)){
						result.put("errorInfo", "银行卡三要素验证验证通过!!");
						result.put("errorCode", 0);
					}else{
						result.put("errorInfo", "银行卡帐号与当前注册人不符!!");
						result.put("errorCode", 1);
					}
					
				}
				
			} else {
				result.put("errorInfo", "银行卡获取授权失败!!");
				result.put("errorCode", 1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
			e.printStackTrace();
		}finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("银行卡三要素验证使用时间：" + (endTime - startTime));
		}
		logger.info("银行卡三要素验证:" + result);
		
		return result;

	}
}
