/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
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
import com.hoomsun.app.api.model.Banner;
import com.hoomsun.app.api.server.inter.BannerServerI;


/**
 * 作者：liudongliang  <br>
 * 创建时间：2017年12月11日 <br>
 * 描述：Banner管理控制层
 * 
 * 添加app接口 2017-09-14 
 * 1.app所需banner
 * 192.168.3.19:8080/app-admin/web/banner/banner.do
 */
@Controller
public class BannerApiController {
	private final static Logger log = LoggerFactory.getLogger(BannerApiController.class);
	
	@Autowired
	private BannerServerI bannerServer;

	
	private static IpUrlEnum ip=IpUrlEnum.HSFS_IP;

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-08-21
	 * @resource 查询banner
	 * @LoggerAnnotation(moduleName = "Banner管理控制层", option = "查询banner")
	 */
	
	@RequestMapping(value = "/web/banner/banner.do")
	@ResponseBody
	public Map<String, Object> banner(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			List<Banner> list = bannerServer.selectByisopen();
			for (Banner obj : list) {
				obj.setBannerurl(ip.getIpUrl() + obj.getBannerurl());
			}
			result.put("data", list);
			result.put("errorInfo", "成功!! ");
			result.put("errorCode", 0);

		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			log.info("banner界面详情：" + (endTime - startTime));
		}
		log.info("banner界面详情:" + result);
		return result;
	}

}
