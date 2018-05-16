package com.hoomsun.app.api.controller.credit;

import java.util.ArrayList;
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

import com.hoomsun.common.util.DateUtil;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.csas.sales.api.model.NameAuthentication;
import com.hoomsun.csas.sales.api.server.inter.NameAuthenticationServerI;
import com.hoomsun.message.helper.PushHelper;
import com.hoomsun.message.model.Notice;
import com.hoomsun.message.server.inter.NoticeServerI;


/**
 * 
 * @author 刘栋梁
 * @date 2017-11-01
 * @resource 1.消息总条数
 *           192.168.3.19:8080/app-admin/web/appnotice/noticeSum.do?ID=111 
 *           2.消息前俩条
 *           192.168.3.19:8080/app-admin/web/appnotice/latelynotice.do?ID=111 
 *           3.所有消息
 *           192.168.3.19:8080/app-admin/web/appnotice/allnotice.do?ID=111
 *           4.修改所有推送消息为已读
 *           192.168.3.19:8080/app-admin/web/appnotice/updateallnotice.do?id=
 *           5.数据中心调用推送认证状态
 *           192.168.3.19:8080/app-admin/web/appnotice/sendchecknotice.do?idcard&message
 *           6.消息的删除 
 *           192.168.3.19:8080/app-admin/web/appnotice/deletenotice.do?noticeid=
 *           
 *           
 *           
 *           
 *           4.消息首页(废)
 *           192.168.3.19:8080/app-admin/web/appnotice/notice.do?ID=111 
 *           5.消息分类列表(废)
 *           192.168.3.19:8080/app-admin/web/appnotice/noticesearch.do?ID=111&FLAG=1
 *           6.修改推送消息为已读(废)
 *           192.168.3.19:8080/app-admin/web/appnotice/updatenotice.do?id=&flag=
 *          
 *           
 */

@Controller
@RequestMapping("web/appnotice")
public class AppNoticeController {

	private final static Logger log = LoggerFactory.getLogger(AppNoticeController.class);

	@Autowired
	private NoticeServerI NoticeServer;
	
	@Autowired
	private NameAuthenticationServerI nameAuthentServer;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 消息总条数 2017-11-01 1.1 刘栋梁
	 * 
	 * @param request
	 * @return
	 * @LoggerAnnotation(moduleName = "消息通知", option = "消息总条数查询")
	 */
	
	@RequestMapping("noticeSum.do")
	@ResponseBody
	public Map<String, Object> noticeSum(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String ID = request.getParameter("ID");

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = NoticeServer.selectSumByCustid(ID);
			result.put("data", map);
			result.put("errorInfo", "查询成功！！！");
			result.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			log.info(ID + "消息总条数使用时间：" + (endTime - startTime));
		}
		log.info(ID + "消息总条数:" + result);

		return result;
	}
	
	
	
	/**
	 * 消息最近俩条 2018-01-29       刘栋梁
	 * 
	 * @param request
	 * @return
	 * @LoggerAnnotation(moduleName = "消息通知", option = "消息最近俩条")
	 */
	
	@RequestMapping("latelynotice.do")
	@ResponseBody
	public Map<String, Object> latelyNotice(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String ID = request.getParameter("ID");

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Notice> list =new ArrayList<Notice>(); 
			list =NoticeServer.selectLatelynotice(ID);			
			result.put("data", list);
			result.put("errorInfo", "查询成功！！！");
			result.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			log.info(ID + "消息最近俩条使用时间：" + (endTime - startTime));
		}
		log.info(ID + "消息最近俩条:" + result);

		return result;
	}


	
	/**
	 * 所有消息    2018-01-29       刘栋梁
	 * 
	 * @param request
	 * @return
	 * @LoggerAnnotation(moduleName = "消息通知", option = "所有消息")
	 */
	
	@RequestMapping("allnotice.do")
	@ResponseBody
	public Map<String, Object> allNotice(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String ID = request.getParameter("ID");

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Notice> list =NoticeServer.selectallynotice(ID);				
			result.put("data", list);
			result.put("errorInfo", "查询成功！！！");
			result.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			log.info(ID + "消息最近俩条使用时间：" + (endTime - startTime));
		}
		log.info(ID + "消息最近俩条:" + result);

		return result;
	}
	
	
	
	/**
	 * 修改所有推送消息为已读            2018-02-01      刘栋梁
	 * 
	 * @param request
	 * @return
	 * @LoggerAnnotation(moduleName = "消息通知", option = "修改所有推送消息为已读")
	 */
	
	@RequestMapping("updateallnotice.do")
	@ResponseBody
	public Map<String, Object> updateAllNotice(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间

		String id = request.getParameter("id");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int i = NoticeServer.updateAll(id);
			result.put("errorInfo", "修改成功！！！");
			result.put("errorCode", 0);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			log.info("修改所有推送消息为已读使用时间：" + (endTime - startTime));
		}
		log.info( "修改所有推送消息为已读:" + result);

		return result;
	}
	
	
	@RequestMapping("deletenotice.do")
	@ResponseBody
	public Map<String, Object> deleteNotice(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String noticeid = request.getParameter("noticeid");

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
			int i = NoticeServer.deleteByPrimaryKey(noticeid);
			result.put("errorInfo", "消息的删除成功");
			result.put("errorCode", 0);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			log.info("消息的删除使用时间：" + (endTime - startTime));
		}
		log.info( "消息的删除:" + result);

		return result;
	}
	
	

	/**
	 * 认证消息通知消息的推送 2017-11-01 1.1 刘栋梁
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("sendchecknotice.do")
	@ResponseBody
	public Map<String, Object>  sendCheckNotice(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String msg = request.getParameter("message");
		String idcard = request.getParameter("idcard");
		String flag = "6";
		String flagVal = "认证消息";

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			NameAuthentication hs = nameAuthentServer.selectByIdcard(idcard);
			List<String> Relist = new ArrayList<String>();
			String Registrationid = hs.getRegistrationid();
			if (!StringUtils.isBlank(Registrationid)) {
				Relist.add(Registrationid);
			}

			Notice notice = new Notice();
			notice.setNoticeid(PrimaryKeyUtil.getPrimaryKey());
			notice.setApplyid("");
			notice.setCustid(hs.getId());
			notice.setConsult("0");
			notice.setFlag(flag);
			notice.setFlagVal(flagVal);
			notice.setMessage(msg);
			notice.setNoticeData(DateUtil.getTimestamp());
			NoticeServer.insertSelective(notice);

			// 信息推送
			PushHelper.SendPushWithTitle(Relist, msg, flagVal, flag);

			result.put("errorInfo", "保存成功");
			result.put("errorCode", 1);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "失败");
			result.put("errorCode", 0);
			
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("认证消息通知的推送使用时间：" + (endTime - startTime));
		}
		logger.info("认证消息通知的推送:" + result);

		return result;

	}
	
	
	/**
	 * 消息首页 2017-11-01 1.1 刘栋梁
	 * 
	 * @param request
	 * @return
	 * @LoggerAnnotation(moduleName = "消息通知", option = "消息首页列表查询")
	 */
	
	@RequestMapping("notice.do")
	@ResponseBody
	public Map<String, Object> notice(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String ID = request.getParameter("ID");

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

			Map<String, Object> AuditeMap = new HashMap<String, Object>();
			Map<String, Object> AuditeMapPara = new HashMap<String, Object>();
			AuditeMapPara.put("custid", ID);
			AuditeMapPara.put("flag", "1");
			Map<String, Object> AuditeMapCount = NoticeServer.selectCountByCustid(AuditeMapPara);
			Notice notice = NoticeServer.selectDataByCustid(AuditeMapPara);
			AuditeMap.put("count", AuditeMapCount.get("COUNT"));
			AuditeMap.put("flag", "1");
			AuditeMap.put("notice", notice);
			list.add(AuditeMap);

			Map<String, Object> BackMap = new HashMap<String, Object>();
			Map<String, Object> BackMapPara = new HashMap<String, Object>();
			BackMapPara.put("custid", ID);
			BackMapPara.put("flag", "2");
			Map<String, Object> BackMapCount = NoticeServer.selectCountByCustid(BackMapPara);
			Notice Backnotice = NoticeServer.selectDataByCustid(BackMapPara);
			BackMap.put("count", BackMapCount.get("COUNT"));
			BackMap.put("flag", "2");
			BackMap.put("notice", Backnotice);
			list.add(BackMap);

			Map<String, Object> sysMap = new HashMap<String, Object>();
			Map<String, Object> sysMapPara = new HashMap<String, Object>();
			sysMapPara.put("custid", ID);
			sysMapPara.put("flag", "3");
			Map<String, Object> sysMapCount = NoticeServer.selectCountByCustid(sysMapPara);
			Notice sysnotice = NoticeServer.selectDataByCustid(sysMapPara);
			sysMap.put("count", sysMapCount.get("COUNT"));
			sysMap.put("flag", "3");
			sysMap.put("notice", sysnotice);
			list.add(sysMap);

			/*Map<String, Object> AmtMap = new HashMap<String, Object>();
			Map<String, Object> AmtMapPara = new HashMap<String, Object>();
			AmtMapPara.put("custid", ID);
			AmtMapPara.put("flag", "4");
			Map<String, Object> AmtMapCount = NoticeServer.selectCountByCustid(AmtMapPara);
			Notice Amtnotice = NoticeServer.selectDataByCustid(AmtMapPara);
			AmtMap.put("count", AmtMapCount.get("COUNT"));
			AmtMap.put("flag", "4");
			AmtMap.put("notice", Amtnotice);
			list.add(AmtMap);

			Map<String, Object> ActivityMap = new HashMap<String, Object>();
			Map<String, Object> ActivityMapPara = new HashMap<String, Object>();
			ActivityMapPara.put("custid", ID);
			ActivityMapPara.put("flag", "5");
			Map<String, Object> ActivityMapCount = NoticeServer.selectCountByCustid(ActivityMapPara);
			Notice Activitynotice = NoticeServer.selectDataByCustid(ActivityMapPara);
			ActivityMap.put("count", ActivityMapCount.get("COUNT"));
			ActivityMap.put("flag", "5");
			ActivityMap.put("notice", Activitynotice);
			list.add(ActivityMap);
*/
			Map<String, Object> appMap = new HashMap<String, Object>();
			Map<String, Object> appMapPara = new HashMap<String, Object>();
			appMapPara.put("custid", ID);
			appMapPara.put("flag", "6");
			Map<String, Object> appMapCount = NoticeServer.selectCountByCustid(appMapPara);
			Notice appnotice = NoticeServer.selectDataByCustid(appMapPara);
			appMap.put("count", appMapCount.get("COUNT"));
			appMap.put("flag", "6");
			appMap.put("notice", appnotice);
			list.add(appMap);
			
			
			//签约信息
			Map<String, Object> signMap = new HashMap<String, Object>();
			Map<String, Object> signMapPara = new HashMap<String, Object>();
			signMapPara.put("custid", ID);
			signMapPara.put("flag", "7");
			Map<String, Object> signMapCount = NoticeServer.selectCountByCustid(signMapPara);
			Notice signnotice = NoticeServer.selectDataByCustid(signMapPara);
			signMap.put("count", signMapCount.get("COUNT"));
			signMap.put("flag", "7");
			signMap.put("notice", signnotice);
			list.add(signMap);
			
			result.put("data", list);
			result.put("errorInfo", "查询成功！！！");
			result.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			log.info(ID + "消息首页使用时间：" + (endTime - startTime));
		}
		log.info(ID + "消息首页:" + result);

		return result;
	}

	/**
	 * 消息分类列表 2017-11-01 1.1 刘栋梁
	 * 
	 * @param request
	 * @return
	 * @LoggerAnnotation(moduleName = "消息通知", option = "消息分类列表查询")
	 */
	
	@RequestMapping("noticesearch.do")
	@ResponseBody
	public Map<String, Object> noticeSearch(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String ID = request.getParameter("ID");
		String flag = request.getParameter("FLAG");

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> MapPara = new HashMap<String, Object>();
			MapPara.put("custid", ID);
			MapPara.put("flag", flag);
			List<Notice> list = NoticeServer.selectByCustid(MapPara);

			result.put("data", list);
			result.put("errorInfo", "查询成功！！！");
			result.put("errorCode", 0);

		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			log.info(ID + "消息分类列表使用时间：" + (endTime - startTime));
		}
		log.info(ID + "消息分类列表:" + result);

		return result;
	}

	/**
	 * 修改推送消息为已读 2017-11-01 1.1 刘栋梁
	 * 
	 * @param request
	 * @return
	 * @LoggerAnnotation(moduleName = "消息通知", option = "修改推送消息为已读")
	 */
	
	@RequestMapping("updatenotice.do")
	@ResponseBody
	public Map<String, Object> updateNotice(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String id = request.getParameter("id");
		String flag = request.getParameter("flag");

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Notice notice = new Notice();
			notice.setCustid(id);
			notice.setFlag(flag);	
			notice.setConsult("1");
			int i = NoticeServer.updateAllById(notice);
			result.put("errorInfo", "修改成功！！！");
			result.put("errorCode", 0);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			log.info("修改推送消息为已读使用时间：" + (endTime - startTime));
		}
		log.info( "修改推送消息为已读:" + result);

		return result;
	}
	
	
	
	
	
}
