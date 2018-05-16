package com.hoomsun.app.api.controller.credit;

import java.math.BigDecimal;
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
import com.hoomsun.core.model.SysProductType;
import com.hoomsun.core.server.inter.SysProductServerI;
import com.hoomsun.core.server.inter.SysProductTypeServerI;

/**
 * 
 * @author 刘栋梁
 * @date 2017-09-15
 * @resource app产品接口
 *           1.首页产品显示 
 *           192.168.3.19:8080/app-admin/web/product/product.do
 *           2.门店四个产品信息 
 *           192.168.3.19:8080/app-admin/web/product/creditproduct.do
 *           3.app小产品参数
 *           192.168.3.19:8080/app-admin/web/product/productdetil.do?rate= &prodType=&amt=
 *           4.门店小产品参数
 *           192.168.3.19:8080/app-admin/web/product/productcredetil.do?prodType=
 *
 */

@Controller
@RequestMapping("web/product")
public class ProductInfoController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private IpUrlEnum ip=IpUrlEnum.HSFS_IP;

	@Autowired
	private SysProductTypeServerI   sysProductTypeServerI;
	
	@Autowired
	private SysProductServerI   sysProductServer;

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-09-15
	 * @resource 主界面四个产品信息
	 * @LoggerAnnotation(moduleName = "app产品接口", option = "首页产品查询显示")
	 */
	
	@RequestMapping(value = "product.do")
	@ResponseBody
	public Map<String, Object> productInfo(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String isonline="1";
			List<SysProductType> list = sysProductTypeServerI.findOnlineData(isonline);
			for (SysProductType obj : list) {
				obj.setProducturl( obj.getProducturl());
				obj.setGotourl(ip.getIpUrl() +obj.getGotourl());
			}
			
			SysProductType sysProductType = new SysProductType();
			short Isonline='1';
			sysProductType.setIsonline(Isonline);
			sysProductType.setProdName("更多方案");
			sysProductType.setMaxCreditAmt("200000");
			sysProductType.setMixCreditAmt("10000");
			sysProductType.setProductdesc("月费率低至1.36%");
			list.add(sysProductType);
			map.put("data", list);
			map.put("errorInfo", "查询成功！！");
			map.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", "数据查询失败！");
			map.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("主界面四个产品信息：" + (endTime - startTime));
		}

		logger.info("主界面四个产品信息:" + map);
		return map;
	}

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-09-15
	 * @resource 门店四个产品信息
	 * @LoggerAnnotation(moduleName = "app产品接口", option = "门店四个产品信息查询")
	 */
	
	@RequestMapping(value = "creditproduct.do")
	@ResponseBody
	public Map<String, Object> creditproduct(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		// 设备码
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String isonline="0";
			List<SysProductType> list = sysProductTypeServerI.findOnlineData(isonline);
			for (SysProductType obj : list) {
				obj.setProducturl(obj.getProducturl());
				obj.setGotourl(ip.getIpUrl() + obj.getGotourl());
			}
			map.put("data", list);
			map.put("errorInfo", "查询成功！！");
			map.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", "数据查询失败！");
			map.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("主界面四个产品信息：" + (endTime - startTime));
		}

		logger.info("主界面四个产品信息:" + map);
		return map;
	}

	
	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-08-28
	 * @resource 二级页面获取产品详情------app产品
	 * @LoggerAnnotation(moduleName = "app产品接口", option = "二级页面获取产品详情")
	 */
	
	@RequestMapping(value = "productdetil.do")
	@ResponseBody
	public Map<String, Object> productdetil(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String rate = request.getParameter("rate"); // 利率
		String prodType = request.getParameter("prodType"); // 大产品
		String amt = request.getParameter("amt"); // 额度
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> para = new HashMap<String, Object>();
			para.put("prodType", prodType);
			para.put("realMonthRate", new BigDecimal(rate));
			List<Map<String, Object>> list = sysProductServer.findproductData(para);

			for (Map<String, Object> promap : list) {
				promap.put("MAX_CREDIT_AMT",amt );
			}
			map.put("data", list);
			map.put("errorInfo", "查询成功！！");
			map.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", "数据查询失败！");
			map.put("errorCode", 1001);
		} finally {

			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("申请界面产品品信息：" + (endTime - startTime));
		}

		logger.info("申请界面产品品信息:" + map);
		return map;
	}


	/**
	 * 
	 * @author 刘栋梁
	 * @date 2018-02-05
	 * @resource 二级页面获取产品详情------门店产品
	 * @LoggerAnnotation(moduleName = "app产品接口", option = "二级页面获取产品详情")
	 */
	
	@RequestMapping(value = "productcredetil.do")
	@ResponseBody
	public Map<String, Object> productcredetil(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		
		String prodType = request.getParameter("prodType"); // 大产品
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> para = new HashMap<String, Object>();
			para.put("prodType", prodType);
			List<Map<String, Object>> list = sysProductServer.findproductCreData(para);
			map.put("data", list);
			map.put("errorInfo", "查询成功！！");
			map.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", "数据查询失败！");
			map.put("errorCode", 1001);
		} finally {

			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("申请界面产品品信息：" + (endTime - startTime));
		}

		logger.info("申请界面产品品信息:" + map);
		return map;
	}

}
