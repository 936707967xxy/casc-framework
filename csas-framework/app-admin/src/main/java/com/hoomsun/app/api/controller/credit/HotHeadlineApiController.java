package com.hoomsun.app.api.controller.credit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.app.api.enums.IpUrlEnum;
import com.hoomsun.app.api.model.HotHeadline;
import com.hoomsun.app.api.server.inter.HotHeadlineServerI;


/**
 * 
 * @author 刘栋梁
 * @date 2017-11-01
 * @resource 1.查询首页推送通告
 *           192.168.3.19:8080/app-admin/web/hotheadlineapi/getNews.do
 *         
 */

@Controller
@RequestMapping("web/hotheadlineapi")
public class HotHeadlineApiController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private HotHeadlineServerI hotHeadlineServer;
	
	
	private static IpUrlEnum ip=IpUrlEnum.HSFS_IP;
	
	
	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-09-05
	 * @resource 查询首页推送通告
	 * @LoggerAnnotation(moduleName = "消息通知", option = "查询首页推送通告")
	 *
	 */
	
	@RequestMapping(value = "getNews.do")
	@ResponseBody
	public Map<String, Object> getNews(HttpServletRequest req) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<HotHeadline> list = hotHeadlineServer.findAllData();
			for (HotHeadline obj : list) {
				obj.setContentUrl(ip.getIpUrl() + obj.getContentUrl());
			}
			result.put("data", list);
			result.put("errorInfo", "成功!! ");
			result.put("errorCode", 0);

		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "数据查询失败！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("查询首页推送通告界面详情：" + (endTime - startTime));
		}

		logger.info("查询首页推送通告界面详情:" + result);
		return result;
	}
	
}
