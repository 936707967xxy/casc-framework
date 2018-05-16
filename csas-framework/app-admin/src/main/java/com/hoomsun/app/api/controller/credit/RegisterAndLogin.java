package com.hoomsun.app.api.controller.credit;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.app.api.enums.IpUrlEnum;
import com.hoomsun.app.api.help.DownloadHelper;
import com.hoomsun.app.api.help.SmsauController;
import com.hoomsun.app.api.model.AppLoginLog;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.common.util.RSACoder;
import com.hoomsun.common.util.SystemUtils;
import com.hoomsun.common.util.UploadPathUtil;
import com.hoomsun.core.server.inter.SysEmployeeServerI;
import com.hoomsun.core.util.DateUtil;
import com.hoomsun.csas.sales.api.model.NameAuthentication;
import com.hoomsun.csas.sales.api.server.inter.NameAuthenticationServerI;
import com.hoomsun.message.model.Notice;
import com.hoomsun.message.server.inter.NoticeServerI;

/**
 * @author 作者：liudongliang <br>
 * @Date 创建时间：2017年11月30日 <br>
 * @Description 描述：注册-登陆
 * @resource 1. 注册接口判断客户是否已经注册接口
 *           192.168.3.19:8080/app-admin/web/registerandlogin/isregister.do?PHONE= 
 *           2. 登陆短信发送
 *           192.168.3.19:8080/app-admin/web/registerandlogin/registersend.do?PHONE= 
 *           4. 注册成功
 *           92.168.3.19:8080/app-admin/web/registerandlogin/register.do?PHONE=&PWD=&CODE=&UUID=&REGISTRATIONID=&TYPE=&WXTAKEN&INVITECODE=&INVITEDEPTID= 
 *           5. 登陆接口
 *           192.168.3.19:8080/app-admin/web/registerandlogin/login.do?PHONE=&PWD=&UUID=&REGISTRATIONID= &CLIENT
 *           6. 找回密码发送短信
 *           192.168.3.19:8080/app-admin/web/registerandlogin/findpwd.do?PHONE=
 *           7. 找回密码短信验证
 *           192.168.3.19:8080/app-admin/web/registerandlogin/codecheck.do?PHONE=&CODE= 
 *           8. 找回密码个人信息验证
 *           192.168.3.19:8080/app-admin/web/registerandlogin/personcheck.do?PHONE=&CUSTNAME=&PAPERID= 
 *           9. 修改密码
 *           192.168.3.19:8080/app-admin/web/registerandlogin/updatepwd.do?PHONE=&PWD= 
 *           10.支付密码
 *           192.168.3.19:8080/app-admin/web/registerandlogin/addbankpwd.do?ID=&BANKPWD= 
 *           11.客户手填写机号是否已绑定微信
 *           192.168.3.19:8080/app-admin/web/registerandlogin/isregisterweixin.do?PHONE 
 *           12.客户手机号绑定微信 /返回登陆信息
 *           192.168.3.19:8080/app-admin/web/registerandlogin/registerweixin.do?ID= &WXTAKEN=&file
 *           13.微信登陆/是否绑定
 *           192.168.3.19:8080/app-admin/web/registerandlogin/weixinlogin.do?wxtaken 
 *           14.邀请码判断
 *           192.168.3.19:8080/app-admin/web/registerandlogin/invitecodesearch.do?INVITECODE 
 *           15.语音验证码
 *           192.168.3.19:8080/app-admin/web/registerandlogin/registersendvoice.do?PHONE=
 * 
 */
@Controller
@RequestMapping("web/registerandlogin")
public class RegisterAndLogin {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private NameAuthenticationServerI hsServerI;
	
	@Autowired
	private UploadPathUtil uploadPathUtil;
	
	@Autowired
	private NoticeServerI noticeServerI;
	
	@Autowired
	private SysEmployeeServerI   sysEmployeeServerI;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * @author 作者：liudongliang <br>
	 * @Description 客户是否已注册
	 * @param phone
	 * @return Map
	 * @Date 2017-11-30
	 * @LoggerAnnotation(moduleName = "注册登录", option = "判断客户是否已注册")
	 */
	@RequestMapping("isregister.do")
	@ResponseBody
	public Map<String, Object> isregister(HttpServletRequest request) {
		// 获取开始时间
		long startTime = System.currentTimeMillis();
		String phone = request.getParameter("PHONE");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 判断客户是否存在
			NameAuthentication hs = hsServerI.selectByPhone(phone);
			if (hs == null) {
				result.put("errorInfo", "该手机号未注册！");
				result.put("errorCode", 1);
			} else {
				result.put("errorInfo", "该手机号已注册！");
				result.put("errorCode", 0);
			}
			result.put("data", hs);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			// 获取结束时间
			long endTime = System.currentTimeMillis();
			logger.info("客户是否已注册使用时间：" + (endTime - startTime));
		}
		logger.info("客户是否已注册:" + result);

		return result;
	}

	/**
	 * @Description 注册短信发送 刘栋梁
	 * @param PHONE
	 * @return Map
	 * @Date 2017-11-30
	 * @LoggerAnnotation(moduleName = "注册登录", option = "登录短信发送")
	 */
	@RequestMapping("registersend.do")
	@ResponseBody
	public Map<String, Object> registersend(HttpServletRequest request) {
		// 获取开始时间
		long startTime = System.currentTimeMillis();
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
			// appId
			String appId = "775db3d12adf4bcf9e9d5f576e11d74d";
			// 短信模板templateId
			String templateId = "129022";
			// 参数
			String to = phone;
			String para = n + "";
			logger.info("发送短信" + to + "============" + n);
			// 发短信
			String flag = SmsauController.TemplateSMS(json, accountSid, token,
					appId, templateId, to, para);
			// 成功
			if ("0".equals(flag)) {
				result.put("errorCode", 0);
				result.put("errorInfo", "短信发送成功");
				// 失败
			} else if ("1".equals(flag)) {
				result.put("errorCode", 1);
				result.put("errorInfo", "短信发送失败");
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "短信发送失败！");
			result.put("errorCode", 1001);
		} finally {
			// 获取结束时间
			long endTime = System.currentTimeMillis();
			logger.info("注册短信发送使用时间：" + (endTime - startTime));
		}
		logger.info("注册短信发送:" + result);

		return result;
	}

	/**
	 * @Description 登录语音短信发送 刘栋梁
	 * @param PHONE
	 * @return Map
	 * @Date 2017-11-30
	 * @LoggerAnnotation(moduleName = "注册登录", option = "登录语音短信发送")
	 */

	@RequestMapping("registersendvoice.do")
	@ResponseBody
	public Map<String, Object> registersendVoice(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String phone = request.getParameter("PHONE");
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			Random random = new Random();
			int verifyCode = random.nextInt(899999) + 100000;
			request.getSession().setAttribute(phone, verifyCode + "");

			// 参数
			String para = verifyCode + "";
			logger.info("发送短信" + phone + "====" + verifyCode);

			String accountSid = "138bc91472ac5b5192195669d9246d71";
			// Auth Token
			String token = "b85406e6f8c9ba1a6dec6d34a66bb52e";
			// appId
			String appId = "975c95443bf24c4fa6b8782557800df1";

			String resultString = SmsauController.voiceCode(accountSid, token,
					appId, phone, para);

			logger.info("登录语音返回信息=" + resultString);

			JSONObject object = JSON.parseObject(resultString);
			JSONObject resp = (JSONObject) object.get("resp");
			String code = resp.get("respCode").toString();
			// 发送状态
			if ("000000".equals(code)) {
				result.put("errorCode", 0);
				result.put("errorInfo", "登录语音短信发送成功");
				// 失败
			} else {
				result.put("errorCode", 1);
				result.put("errorInfo", "登录语音短信发送失败");
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "短信发送失败！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("登录语音短信发送使用时间：" + (endTime - startTime));
		}
		logger.info("登录语音短信发送:" + result);

		return result;
	}
	
	
	/**
	 * @Description 注册登录 刘栋梁
	 * @param PHONE
	 * @return Map
	 * @Date 2017-11-30
	 * @LoggerAnnotation(moduleName = "注册登录", option = "注册成功")
	 */
	@RequestMapping("register.do")
	@ResponseBody
	public Map<String, Object> register(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String phone = request.getParameter("PHONE");
		String pwd = request.getParameter("PWD");
		String type = request.getParameter("TYPE");
		String code = request.getParameter("CODE"); // 验证码
		String uuid = request.getParameter("UUID"); // 设备码
		String registrationId = request.getParameter("REGISTRATIONID"); // 极光信息推送
		String wxtaken = request.getParameter("WXTAKEN"); // taken
		String invitecode = request.getParameter("INVITECODE");
		String invitedeptid = request.getParameter("INVITEDEPTID");

		HttpSession session = request.getSession();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			logger.info("传值" + phone + "--" + code+"--"+registrationId);
			String sessioncode = session.getAttribute(phone).toString();
			if (sessioncode.equals(code)) {
				NameAuthentication hs = new NameAuthentication();
				String id = PrimaryKeyUtil.getPrimaryKey();
				hs.setId(id);
				hs.setPhone(phone);
				hs.setPwd(pwd);
				hs.setUuid(uuid);
				hs.setRegistrationid(registrationId); // 极光信息推送
				hs.setIsauthentication("0");
				hs.setIsauthenticationVal("未实名认证");
				hs.setIssesame("0");
				hs.setIssesameVal("未芝麻认证");
				hs.setSesameGradeval("0");
				hs.setSesameTime(DateUtil.getCurrentTime()); // 默认芝麻起始时间
				hs.setIncome("0");
				hs.setIncomeTime(DateUtil.getCurrentTime()); //默认收入起始时间	
				hs.setContact("0");                          
				hs.setContactTime(DateUtil.getCurrentTime());//默认通讯录起始时间	
				hs.setAuthenticationMesg("未验证"); // 同盾 内部查询 实名认证
				hs.setRenfaMesg("未验证"); // 人法 芝麻认证
				hs.setWxtaken(wxtaken); // 微信taken
				hs.setInvitecode(invitecode); // 邀请码
				hs.setInvitedeptid(invitedeptid); // 邀请门店
				hs.setType(type); // 注册类型
				hs.setCreateTime(DateUtil.getTimestamp()); // 创建时间
				hs.setLoginTime(DateUtil.getTimestamp()); // 登录时间
				hs.setLoginIp(SystemUtils.getIpAddr(request)); // 获取登录IP
				Map<String, String> rsaMap = RSACoder.getPublicKeyAndSign(id + "&&PHONE");
				logger.info("公钥：" + rsaMap.get("publicKey"));
				logger.info("私钥：" + rsaMap.get("privateKey"));
				logger.info("签名：" + rsaMap.get("sign"));
				hs.setPublicKey(rsaMap.get("publicKey"));
				hs.setPrivateKey(rsaMap.get("privateKey"));
				hs.setSign(rsaMap.get("sign"));

				int i = hsServerI.insertSelective(hs);
				if (i == 0) {
					result.put("data", hs);
					result.put("errorInfo", "注册失败，请重试！");
					result.put("errorCode", 1);
				} else {
					// 存储日志信息
					AppLoginLog log = new AppLoginLog();
					log.setEmpName(phone);  // 登录名
					log.setLoginName(hs.getCustname()); // 登录人
					log.setLoginDate(new Date()); // 登录时间
					log.setLoginIP(SystemUtils.getIpAddr(request)); // 客户端IP
					log.setLoginClient(SystemUtils.getClientInfo(request)); // 获取系统和浏览器名称
					mongoTemplate.insert(log);
					
					result.put("data", hs);
					result.put("errorInfo", "注册成功！");
					result.put("errorCode", 0); 
					// 清除验证码
					session.removeAttribute(phone);
					//注册发送注册消息
					String MsgFirst="欢迎使用微沙信用，开启您的信用之旅";		
					Notice  notice=new Notice();
					notice.setNoticeid(PrimaryKeyUtil.getPrimaryKey());
					notice.setCustid(id);
					notice.setFlag("3");
					notice.setFlagVal("系统消息");
					notice.setMessage(MsgFirst);
					notice.setConsult("0");
					notice.setNoticeData(DateUtil.getTimestamp());
					noticeServerI.insertSelective(notice);
					String MsgTwo="恭喜您注册成功";	
					notice.setNoticeid(PrimaryKeyUtil.getPrimaryKey());
					notice.setMessage(MsgTwo);
					notice.setNoticeData(DateUtil.getTimestamp());
					noticeServerI.insertSelective(notice);
				}

				
			} else {
				result.put("errorInfo", "验证码错误！！");
				result.put("errorCode", 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			// 获取结束时间
			long endTime = System.currentTimeMillis(); 
			logger.info("注册使用时间：" + (endTime - startTime));
		}
		logger.info("注册:" + result);

		return result;
	}

	
	/**
	 * @Description 客户邀请码查询    刘栋梁
	 * @param PHONE
	 * @return Map
	 * @Date 2017-11-30
	 * @LoggerAnnotation(moduleName = "注册登录", option = "客户邀请码查询")
	 */
	@RequestMapping("invitecodesearch.do")
	@ResponseBody
	public Map<String, Object> invitecodesearch(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String inviteCode = request.getParameter("INVITECODE");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			logger.info("传值inviteCode==" + inviteCode );
			// 填入各个表单域的值
			Map<String, Object> User=sysEmployeeServerI.findAppUserInfo(inviteCode);
			if (User==null) {				
				result.put("data", User);
				result.put("errorInfo", "当前用户不存在,请核对邀请码");
				result.put("errorCode", 2);
			} else {
				String userlock=User.get("EMP_STATUS_VAL")+"";
				if("离职".equals(userlock)){
					result.put("data", User);
					result.put("errorInfo", "当前用户离职,请核对邀请码");
					result.put("errorCode", 2);
				}else{
					//判断有没有 不在营业部
					Map<String, Object> deptResult=sysEmployeeServerI.findAppUserStorInfo(User.get("DEPT_ID")+"");
					if(deptResult==null){
						result.put("errorInfo", "未查找到该邀请码对应的营业部,请核对");
						result.put("errorCode", 2);
						return result;
					}
					
					result.put("data", User);
					result.put("errorInfo", "查询成功,请核对邀请码信息");
					result.put("errorCode", 0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("邀请码判断门店使用时间：" + (endTime - startTime));
		}
		logger.info("邀请码判断门店:" + result);

		return result;
	}
	
	
	/**
	 * @Description pc注册界面     刘栋梁
	 * @param  PHONE,PWD,TYPE,CODE,UUID,REGISTRATIONID
	 * @return Map
	 * @Date   2017-12-01(废弃)
	 * @LoggerAnnotation(moduleName = "注册登录", option = "注册界面")
	 */
	@RequestMapping("pcregister.do")
	@ResponseBody
	public Map<String, Object> pcRegister(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String phone = request.getParameter("PHONE");
		String pwd = request.getParameter("PWD");
		String type = request.getParameter("TYPE");
		String code = request.getParameter("CODE");
		String uuid = request.getParameter("UUID"); // 设备码
		String registrationId = request.getParameter("REGISTRATIONID"); // 极光信息推送
		// String WXTAKEN = request.getParameter("WXTAKEN"); //taken

		HttpSession session = request.getSession();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			logger.info("传值" + phone + "--" + code);
			String sessioncode = session.getAttribute(phone).toString();
			if (sessioncode.equals(code)) {
				NameAuthentication hs = new NameAuthentication();
				String id = PrimaryKeyUtil.getPrimaryKey();
				hs.setId(id);
				hs.setPhone(phone);
				hs.setPwd(pwd);
				hs.setUuid(uuid);
				hs.setRegistrationid(registrationId); // 极光信息推送
				hs.setIsauthentication("0");
				hs.setIsauthenticationVal("未实名认证");
				hs.setIssesame("0");
				hs.setIssesameVal("未芝麻认证");
				hs.setSesameGradeval("0");
				hs.setSesameTime(DateUtil.getCurrentTime()); // 默认芝麻起始时间
				hs.setIncome("0");
				hs.setIncomeTime(DateUtil.getCurrentTime()); //默认收入起始时间	
				hs.setContact("0");                          
				hs.setContactTime(DateUtil.getCurrentTime());//默认通讯录起始时间	
				hs.setAuthenticationMesg("未验证"); // 同盾 内部查询 实名认证
				hs.setRenfaMesg("未验证"); // 人法 芝麻认证
				hs.setWxtaken("未绑定"); // 微信taken
				hs.setInvitecode("000000"); // 邀请码
				hs.setInvitedeptid("000000"); // 邀请门店
				hs.setType(type); // 注册类型
				hs.setCreateTime(DateUtil.getTimestamp());
				Map<String, String> rsaMap = RSACoder.getPublicKeyAndSign(id + "&&PHONE");
				logger.info("公钥：" + rsaMap.get("publicKey"));
				logger.info("私钥：" + rsaMap.get("privateKey"));
				logger.info("签名：" + rsaMap.get("sign"));
				hs.setPublicKey(rsaMap.get("publicKey"));
				hs.setPrivateKey(rsaMap.get("privateKey"));
				hs.setSign(rsaMap.get("sign"));

				int i = hsServerI.insertSelective(hs);

				if (i == 0) {
					result.put("data", hs);
					result.put("errorInfo", "注册失败，请重试！");
					result.put("errorCode", 1);
				} else {
					result.put("data", hs);
					result.put("errorInfo", "注册成功！");
					result.put("errorCode", 0);
					// 清除验证码
					session.removeAttribute(phone);
				}

			} else {
				result.put("errorInfo", "验证码错误！！");
				result.put("errorCode", 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("注册使用时间：" + (endTime - startTime));
		}
		logger.info("注册:" + result);

		return result;
	}
	
	
	/**
	 * @Description 登陆       刘栋梁
	 * @param  PHONE,PWD,TYPE,CLIENT,UUID,REGISTRATIONID
	 * @return Map
	 * @Date   2017-12-01
	 * @LoggerAnnotation(moduleName = "登录", option = "登陆接口")
	 */
	@RequestMapping("login.do")
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String phone = request.getParameter("PHONE");
		String pwd = request.getParameter("PWD");
		String uuid = request.getParameter("UUID"); // 设备码
		String registrationId = request.getParameter("REGISTRATIONID"); // 极光信息推送
		String client = request.getParameter("CLIENT"); // 手机型号

		
		logger.info("注册登录registrationId="+registrationId);		
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			NameAuthentication hs = hsServerI.selectByPhone(phone);
			if (hs == null) {
				result.put("errorInfo", "该手机号未注册！");
				result.put("errorCode", 1);
			} else {
				if (!pwd.equals(hs.getPwd())) {
					result.put("errorInfo", "登陆密码错误！！");
					result.put("errorCode", 1);
				} else {
					NameAuthentication hss = new NameAuthentication();
					hss.setId(hs.getId());
					hss.setUuid(uuid); // 设备ID
					hss.setRegistrationid(registrationId); // 极光ID
					hss.setLoginTime(DateUtil.getTimestamp()); // 登录时间
					hss.setLoginIp(SystemUtils.getIpAddr(request)); // 获取登录IP
					int i = hsServerI.updateByPrimaryKeySelective(hss);
					// 存储日志信息
					AppLoginLog log = new AppLoginLog();
					log.setEmpName(phone);  // 登录名
					log.setLoginName(hs.getCustname()); // 登录人
					log.setLoginDate(new Date()); // 登录时间
					log.setLoginIP(SystemUtils.getIpAddr(request)); // 客户端IP
					log.setLoginClient(SystemUtils.getClientInfo(request)); // 获取系统和浏览器名称
					mongoTemplate.insert(log);
					logger.info("登录日志" + log);
					
					hs.setUuid(uuid);
					hs.setRegistrationid(registrationId);
					if (hs.getPhotopath() == null) {
						hs.setPhotopath("");
					} else {
						// 路径获取
						hs.setPhotopath(IpUrlEnum.HSFS_IP.getIpUrl() + hs.getPhotopath());
					}
					if (i == 0) {
						result.put("data", hs);
						result.put("errorInfo", "登陆失败，请重试！");
						result.put("errorCode", 1);
					} else {
						result.put("data", hs);
						result.put("errorInfo", "登陆成功！");
						result.put("errorCode", 0);
						//AppLoginLog log = new AppLoginLog();
						// TODO 这里是否存储 用户数据库的ID
						//log.setLoginName(phone); // 登录账号
						//log.setEmpName(hs.getCustname()); // 登录人
						//log.setLoginDate(new Date()); // 登录时间
						//log.setLoginIP(request.getRemoteAddr()); // 客户端IP
						//log.setUuid(UUID); // 获取系统和浏览器名称
						if (client == null) {
							client = "暂无";
						}
						//log.setLoginClient(CLIENT);
						//mongoTemplate.insert(log);    // 没有uuid不可以存入,没有REGISTRATIONID 可以
													
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			 // 获取结束时间
			long endTime = System.currentTimeMillis();
			logger.info("login使用时间：" + (endTime - startTime));
		}
		logger.info("login:" + result);

		return result;
	}
	
	
	
	/**
	 * @Description 客户找回密码发送短信        刘栋梁
	 * @param  PHONE
	 * @return Map
	 * @Date   2017-12-01
	 * @LoggerAnnotation(moduleName = "注册登录", option = "找回密码发送短信")
	 */
	
	@RequestMapping("findpwd.do")
	@ResponseBody
	public Map<String, Object> findPwd(HttpServletRequest request) {
		// 获取开始时间
		long startTime = System.currentTimeMillis();
		String phone = request.getParameter("PHONE");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 判断客户是否存在
			NameAuthentication hs = hsServerI.selectByPhone(phone);
			if (hs == null) {
				result.put("errorInfo", "该手机号未注册！");
				result.put("errorCode", 0);
			} else {
				Random random = new Random();
				int n = random.nextInt(899999) + 100000;
				request.getSession().setAttribute(phone, n + "");
				boolean json = true;
				// Account Sid
				String accountSid = "138bc91472ac5b5192195669d9246d71";
				// Auth Token
				String token = "b85406e6f8c9ba1a6dec6d34a66bb52e";
				// appId
				String appId = "775db3d12adf4bcf9e9d5f576e11d74d";
				// 短信模板templateId
				String templateId = "129022";
				// 参数
				String to = phone;
				String para = n + "";
				logger.info("客户找回密码发送短信"+to + "=====" + n);
				// 发短信
				String flag = SmsauController.TemplateSMS(json, accountSid, token, appId, templateId, to, para);
				// 成功
				if ("0".equals(flag)) {
					result.put("errorCode", 0);
					result.put("errorInfo", "短信发送成功");
				// 失败
				} else if ("1".equals(flag)) {
					result.put("errorCode", 1);
					result.put("errorInfo", "短信发送失败");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("客户找回密码使用时间：" + (endTime - startTime));
		}
		logger.info("客户找回密码:" + result);

		return result;
	}

	
	
	/**
	 * @Description 客户找回密码验证码验证        刘栋梁
	 * @param  PHONE,CODE
	 * @return Map
	 * @Date   2017-12-01
	 * @LoggerAnnotation(moduleName = "注册登录", option = "找回密码短信校验")
	 */
	
	@RequestMapping("codecheck.do")
	@ResponseBody
	public Map<String, Object> codeCheck(HttpServletRequest request) {
		// 获取开始时间
		long startTime = System.currentTimeMillis(); 
		HttpSession session = request.getSession();

		String phone = request.getParameter("PHONE");
		String code = request.getParameter("CODE");

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String sessioncode = session.getAttribute(phone).toString();
			if (sessioncode.equals(code)) {
				result.put("errorInfo", "验证码正确！！");
				result.put("errorCode", 0);
				// 清除验证码
				session.removeAttribute(phone);
			} else {
				result.put("errorInfo", "验证码错误！");
				result.put("errorCode", 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			// 获取结束时间
			long endTime = System.currentTimeMillis(); 
			logger.info("客户找回密码验证码验证时间：" + (endTime - startTime));
		}
		logger.info("客户找回密码验证码验证 :" + result);

		return result;
	}
	
	
	/**
	 * @Description 客户找回密码个人信息验证        刘栋梁
	 * @param  PHONE,CODE
	 * @return Map
	 * @Date   2017-12-01
	 * @LoggerAnnotation(moduleName = "注册登录", option = "找回密码个人信息验证")
	 */
	
	@RequestMapping("personcheck.do")
	@ResponseBody
	public Map<String, Object> personcheck(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String phone = request.getParameter("PHONE");
		String custname = request.getParameter("CUSTNAME");
		String paperid = request.getParameter("PAPERID");

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 判断客户是否存在
			NameAuthentication hs = hsServerI.selectByPhone(phone);
			if (custname.equals(hs.getCustname()) && paperid.equals(hs.getPaperid())) {
				result.put("errorInfo", "身份认证成功！！");
				result.put("errorCode", 0);
			} else {
				result.put("errorInfo", "身份认证失败！！");
				result.put("errorCode", 1);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("客户找回密码个人信息验证时间：" + (endTime - startTime));
		}
		logger.info("客户找回密码个人信息验证 :" + result);

		return result;
	}


	
	/**
	 * @Description 修改密码       刘栋梁
	 * @param  PHONE,PWD
	 * @return Map
	 * @Date   2017-12-01
	 * @LoggerAnnotation(moduleName = "注册登录", option = "修改密码")
	 */
	
	@RequestMapping("updatepwd.do")
	@ResponseBody
	public Map<String, Object> updatePwd(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String phone = request.getParameter("PHONE");
		String pwd = request.getParameter("PWD");

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			logger.info("传值" + phone + "--" + pwd);
			NameAuthentication hs = new NameAuthentication();
			hs.setPhone(phone);
			hs.setPwd(pwd);
			int i = hsServerI.updateByPhoneSelective(hs);
			if (i == 0) {
				result.put("errorInfo", "修改失败！");
				result.put("errorCode", 1);
			} else {
				result.put("errorInfo", "修改成功！");
				result.put("errorCode", 0);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("修改密码使用时间：" + (endTime - startTime));
		}
		logger.info("修改密码:" + result);

		return result;
	}
	
	
	/**
	 * @Description 添加支付密码       刘栋梁
	 * @param  ID,BANKPWD
	 * @return Map
	 * @Date   2017-12-01
	 * @LoggerAnnotation(moduleName = "注册登录", option = "添加支付密码")
	 */
	
	@RequestMapping("addbankpwd.do")
	@ResponseBody
	public Map<String, Object> addbankpwd(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String id = request.getParameter("ID");
		String bankpwd = request.getParameter("BANKPWD");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			logger.info("支付传值" + id + "--" + bankpwd);
			// 添加支付密码
			NameAuthentication hs = new NameAuthentication();
			hs.setId(id);
			hs.setBankpwd(bankpwd);
			int i = hsServerI.updateByPrimaryKeySelective(hs);
			if (i == 0) {
				result.put("errorInfo", "修改失败！");
				result.put("errorCode", 1);
			} else {
				result.put("errorInfo", "修改成功！");
				result.put("errorCode", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("修改密码使用时间：" + (endTime - startTime));
		}
		logger.info("添加支付密码:" + result);

		return result;
	}
	
	
	/**
	 * @Description 客户手填写机号是否已绑定微信        刘栋梁
	 * @param  PHONE
	 * @return Map
	 * @Date   2017-12-01
	 * @LoggerAnnotation(moduleName = "注册登录", option = "客户手填写机号是否已绑定微信")
	 */
	
	@RequestMapping("isregisterweixin.do")
	@ResponseBody
	public Map<String, Object> isRegisterWeixin(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String phone = request.getParameter("PHONE");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 判断客户是否存在
			NameAuthentication hs = hsServerI.selectByPhone(phone);
			if (hs.getWxtaken() == null || hs.getWxtaken().equals("未绑定") || "".equals(hs.getWxtaken())) {
				result.put("errorInfo", "该手机号未绑定其他微信！");
				result.put("errorCode", 1);
			} else {
				result.put("errorInfo", "该手机号已绑定微信！");
				result.put("errorCode", 0);
			}

			result.put("data", hs);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("客户手填写机号是否已绑定微信   时间：" + (endTime - startTime));
		}
		logger.info("客户手填写机号是否已绑定微信   :" + result);

		return result;
	}

	
	/**
	 * @Description 客户手机号绑定微信 /返回登陆信息        刘栋梁
	 * @param  PHONE
	 * @return Map
	 * @Date   2017-12-01
	 * @LoggerAnnotation(moduleName = "注册登录", option = "客户手机号绑定微信 /返回登陆信息")
	 */
	
	@RequestMapping("registerweixin.do")
	@ResponseBody
	public Map<String, Object> registerweixin(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String wxtaken = request.getParameter("WXTAKEN");
		String id = request.getParameter("ID");
		String file = request.getParameter("file");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 微信头像上传
			String photopath =null;
			if (!file.isEmpty() && !StringUtils.isBlank(file)) {
				String viewPath = uploadPathUtil.userUrl();
				String saveName = System.currentTimeMillis() + ".png";
				File f = new File(uploadPathUtil.saveUserPath() + File.separator + id + File.separator + "head");
				if (!f.exists()) {
					f.mkdirs();
				}
				// 文件保存路径
				String filePath = uploadPathUtil.saveUserPath() + File.separator + 	id + File.separator + "head" + File.separator + saveName;
				// 转存文件
				DownloadHelper.downloadNet(file, filePath);
				photopath = viewPath+"/" + id + "/head/" + saveName;
				
			}
			NameAuthentication nameAuthen = new NameAuthentication();
			nameAuthen.setId(id);
			nameAuthen.setWxtaken(wxtaken);
			if(photopath!=null){
				nameAuthen.setPhotopath(photopath);
			}			
			int i = hsServerI.updateByPrimaryKeySelective(nameAuthen);
			NameAuthentication hs = hsServerI.selectByPrimaryKey(id);
			// 路径获取
			hs.setPhotopath(IpUrlEnum.HSFS_IP.getIpUrl() +hs.getPhotopath());
			if (i == 0) {
				result.put("errorInfo", "该手机号绑定微信失败  ！");
				result.put("errorCode", 1);
				result.put("data", hs);
			} else {
				result.put("data", hs);
				result.put("errorInfo", "该手机号绑定微信成功！！");
				result.put("errorCode", 0);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("手机号绑定微信  时间：" + (endTime - startTime));
		}
		logger.info("手机号绑定微信    :" + result);

		return result;
	}


	
	/**
	 * @Description 微信登陆/是否绑定        刘栋梁
	 * @param  wxtaken
	 * @return Map
	 * @Date   2017-12-01
	 * @LoggerAnnotation(moduleName = "注册登录", option = "微信登陆/是否绑定")
	 */
	@RequestMapping("weixinlogin.do")
	@ResponseBody
	public Map<String, Object> weixinlogin(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String wxtaken = request.getParameter("wxtaken");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			logger.info("微信 taken--" + wxtaken);
			NameAuthentication nameauth = hsServerI.selectByWxtaken(wxtaken);
			if (nameauth == null) {
				result.put("data", nameauth);
				result.put("errorInfo", "微信未与账号绑定！");
				result.put("errorCode", 1);
			} else {
				// 存储日志信息
				AppLoginLog log = new AppLoginLog();
				log.setEmpName(wxtaken);  // 登录名
				log.setLoginName(nameauth.getCustname()); // 登录人
				log.setLoginDate(new Date()); // 登录时间
				log.setLoginIP(SystemUtils.getIpAddr(request)); // 客户端IP
				log.setLoginClient(SystemUtils.getClientInfo(request)); // 获取系统和浏览器名称
				mongoTemplate.insert(log);
				
				nameauth.setPhotopath(IpUrlEnum.HSFS_IP.getIpUrl() + nameauth.getPhotopath());
				result.put("data", nameauth);
				result.put("errorInfo", "微信登陆 成功！");
				result.put("errorCode", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("微信登陆 时间：" + (endTime - startTime));
		}
		logger.info("微信登陆 :" + result);

		return result;
	}
	
	
//	private void appLoginLog(NameAuthentication nameauth){
//		AppLoginLog log = new AppLoginLog();
//		log.setEmpName(nameauth.getPhone());  // 登录名
//		log.setLoginName(user.getEmpName()); // 登录人
//		log.setLoginDate(new Date()); // 登录时间
//		log.setLoginIP(SystemUtils.getIpAddr(request)); // 客户端IP
//		log.setLoginClient(SystemUtils.getClientInfo(request)); // 获取系统和浏览器名称
//		mongoTemplate.insert(log);
//	}
}
