package com.hoomsun.app.api.controller.credit;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hoomsun.app.api.enums.IpUrlEnum;



/**
 * 
 * http://192.168.3.19:8080/hsfs-web/web/signpage/tosign.do 提示页面 
 * http://192.168.3.19:8080/hsfs-web/web/signpage/dati.do 客户认答题页面
 * http://192.168.3.19:8080/hsfs-web/web/signpage/banklimit.do 银行卡限额
 * http://192.168.3.19:8080/hsfs-web/web/signpage/companydesc.do 公司介绍
 * http://192.168.3.19:8080/hsfs-web/web/signpage/jiekuan.do  借款协议
 * http://192.168.3.19:8080/hsfs-web/web/signpage/zhuce.do  注册协议
 * http://192.168.3.19:8080/hsfs-web/web/signpage/kefu.do  客服协议
 * http://192.168.3.19:8080/hsfs-web/web/signpage/tishixiaoxi.do  提示消息
 * 
 * 
 * @author 刘栋梁
 * @date 2017-09-11
 */
@RequestMapping("web/signpage")
@Controller
public class StaticPageController {

	private String signUrl = "/static/html/protocol/protocol.html"; // 协议察看

	private String datiUrl = "/static/html/webroot/toup.html"; // 问答页

	private String bankUrl = "/static/html/banklimit/limitation.html"; // 银行卡限额

	private String conpanyUrl = "/static/html/companydesc/company.html"; // 公司介绍

	private String jiekuanUrl = "/static/html/jiekuan/jiekuanxieyi.html"; // 借款协议

	private String zhuceUrl = "/static/html/zhuce/zhucexieyi.html"; // 注册协议
	
	private String kefuUrl = "/static/html/kefu/kefu.html"; // 客服协议  
	
	private String tishixiaoxiUrl = "/static/html/tishixiaoxi/tishixiaoxi.html"; // 提示消息

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private IpUrlEnum ip=IpUrlEnum.HSFS_IP;

	

	/**
	 * @author 作者：客户认证提示  <br>
	 * @Description 客户是否已注册
	 * @param phone
	 * @return Map
	 * @Date 2017-12-04
	 * @LoggerAnnotation(moduleName = "加载页面资源", option = "认证提示协议")
	 */
	
	@RequestMapping("tosign.do")
	@ResponseBody
	public Map<String, Object> tosign(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("url", ip.getIpUrl() + signUrl);
			result.put("data", map);
			result.put("errorInfo", "成功 ");
			result.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "数据查询失败！");
			result.put("errorCode", 1);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("客户认证提示  使用时间：" + (endTime - startTime));
		}
		logger.info("客户认证提示  :" + result);

		return result;
	}

	
	/**
	 * @author 作者：liudongliang  <br>
	 * @Description 客户认答题页面 
	 * @param phone
	 * @return Map
	 * @Date 2017-12-04
	 * @LoggerAnnotation(moduleName = "加载页面资源", option = "客户认答题页面")
	 */
	
	@RequestMapping("dati.do")
	@ResponseBody
	public Map<String, Object> datiUrl(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		Map<String, Object> result = new HashMap<String, Object>();
		try {
	
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("url", ip.getIpUrl() + datiUrl);
			result.put("data", map);
			result.put("errorInfo", "成功 ");
			result.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "数据查询失败！");
			result.put("errorCode", 1);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("客户认答题页面使用时间：" + (endTime - startTime));
		}
		logger.info("客户认答题页面:" + result);

		return result;
	}

	

	/**
	 * @author 作者：liudongliang  <br>
	 * @Description 银行卡限额
	 * @param phone
	 * @return Map
	 * @Date 2017-12-04
	 * @LoggerAnnotation(moduleName = "加载页面资源", option = "银行卡限额")
	 */
	
	@RequestMapping("banklimit.do")
	@ResponseBody
	public Map<String, Object> banklimit(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("url", ip.getIpUrl() + bankUrl);
			result.put("data", map);
			result.put("errorInfo", "成功 ");
			result.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "数据查询失败！");
			result.put("errorCode", 1);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("银行卡限额 使用时间：" + (endTime - startTime));
		}
		logger.info("银行卡限额 页面:" + result);

		return result;
	}

	
	/**
	 * @author 作者：liudongliang  <br>
	 * @Description 公司介绍
	 * @param phone
	 * @return Map
	 * @Date 2017-12-04
	 * @LoggerAnnotation(moduleName = "加载页面资源", option = "公司介绍")
	 */
	
	@RequestMapping("companydesc.do")
	@ResponseBody
	public Map<String, Object> companyDesc(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("url", ip.getIpUrl() + conpanyUrl);
			result.put("data", map);
			result.put("errorInfo", "成功 ");
			result.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "数据查询失败！");
			result.put("errorCode", 1);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("公司介绍 使用时间：" + (endTime - startTime));
		}
		logger.info("公司介绍 页面:" + result);

		return result;
	}

	
	/**
	 * @author 作者：liudongliang  <br>
	 * @Description   注册协议
	 * @param phone
	 * @return Map
	 * @Date 2017-12-04
	 * @LoggerAnnotation(moduleName = "加载页面资源", option = "注册提示协议")
	 */
	
	@RequestMapping("zhuce.do")
	@ResponseBody
	public Map<String, Object> zhuce(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("url", ip.getIpUrl() + zhuceUrl);
			result.put("data", map);
			result.put("errorInfo", "成功 ");
			result.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "数据查询失败！");
			result.put("errorCode", 1);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("注册协议 使用时间：" + (endTime - startTime));
		}
		logger.info("注册协议  页面:" + result);

		return result;
	}

	
	/**
	 * @author 作者：liudongliang  <br>
	 * @Description  借款协议
	 * @param phone
	 * @return Map
	 * @Date 2017-12-04
	 * @LoggerAnnotation(moduleName = "加载页面资源", option = "借款协议")
	 */
	
	@RequestMapping("jiekuan.do")
	@ResponseBody
	public Map<String, Object> jiekuan(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("url", ip.getIpUrl()+ jiekuanUrl);
			result.put("data", map);
			result.put("errorInfo", "成功 ");
			result.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "数据查询失败！");
			result.put("errorCode", 1);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("借款协议 使用时间：" + (endTime - startTime));
		}
		logger.info("借款协议页面:" + result);

		return result;
	}
	
	/**
	 * @author 作者：liudongliang  <br>
	 * @Description  客服页面 
	 * @param phone
	 * @return Map
	 * @Date 2017-12-04
	 * @LoggerAnnotation(moduleName = "加载页面资源", option = "借款协议")
	 */
	
	@RequestMapping("kefu.do")
	@ResponseBody
	public Map<String, Object> kefu(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("url", ip.getIpUrl()+ kefuUrl);
			result.put("data", map);
			result.put("errorInfo", "成功 ");
			result.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "数据查询失败！");
			result.put("errorCode", 1);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("客服页面  使用时间：" + (endTime - startTime));
		}
		logger.info("客服页面 :" + result);

		return result;
	}
	
	
	
	/**
	 * @author 作者：liudongliang  <br>
	 * @Description  提示消息
	 * @param phone
	 * @return Map
	 * @Date 2018-1-24
	 * @LoggerAnnotation(moduleName = "加载页面资源", option = "提示消息")
	 */
	
	@RequestMapping("tishixiaoxi.do")
	@ResponseBody
	public Map<String, Object> tishixiaoxi(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("url", ip.getIpUrl()+ tishixiaoxiUrl);
			result.put("data", map);
			result.put("errorInfo", "成功 ");
			result.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "数据查询失败！");
			result.put("errorCode", 1);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("提示消息  使用时间：" + (endTime - startTime));
		}
		logger.info("提示消息页面 :" + result);

		return result;
	}

}
