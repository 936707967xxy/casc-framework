package com.hoomsun.app.api.controller.flow;

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

import com.hoomsun.after.api.server.APPServer;
import com.hoomsun.core.model.SysContract;
import com.hoomsun.csas.sales.api.model.UserApply;


/**
 * @author 刘栋梁 作者：刘栋梁 <br>
 *         创建时间：2018年01月04日 <br>
 *         描述：
 *         1.近期日还款 
 *         http://192.168.3.19:8080/app-admin/web/afterloan/selectRepaymentplan.do?ID= 
 *         2.还款记录 
 *         http://192.168.3.19:8080/app-admin/web/afterloan/selectRepaymentdetial.do?ID=qq
 *       
 *          
 */


@Controller
@RequestMapping("web/afterloan")
public class AfterLoanController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private  APPServer aPPServer;
	

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-12-04
	 * @resource   近 七期日还款 
	 *    
	 */
	@RequestMapping(value = "selectRepaymentplan.do")
	@ResponseBody
	public Map<String, Object> selectRepaymentplan(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String ID = request.getParameter("ID");     // ID

		Map<String, Object> map = new HashMap<String, Object>(); // 返回数据 
		try {
			map=aPPServer.sevenDaysRepayment(ID);
		
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
	 * @date 2017-12-04
	 * @resource  还款记录
	 *    
	 */
	@RequestMapping(value = "selectRepaymentdetial.do")
	@ResponseBody
	public Map<String, Object> selectRepaymentdetial(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String ID = request.getParameter("ID");     // ID

		Map<String, Object> map = new HashMap<String, Object>(); // 返回数据 
		try {
			 map=aPPServer.paymentHistory(ID);
			
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
	 * @date 2017-12-04
	 * @resource  还款记录详情
	 *    
	 */
	@RequestMapping(value = "selectRepaymentdetialInfo.do")
	@ResponseBody
	public Map<String, Object> selectRepaymentdetialInfo(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String applyId = request.getParameter("applyId");     // ID

		Map<String, Object> map = new HashMap<String, Object>(); // 返回数据 
		try {
			 map=aPPServer.getsinglepayment(applyId);
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", "数据查询失败！");
			map.put("errorCode", 1);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("还款记录详情：" + (endTime - startTime));
		}

		logger.info("还款记录详情:" + map);
		return map;
	}
}
