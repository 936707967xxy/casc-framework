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

import com.hoomsun.app.api.model.AfreshCareerInfo;
import com.hoomsun.app.api.model.AfreshContacterInfo;
import com.hoomsun.app.api.server.inter.AfreshCareerInfoServerI;
import com.hoomsun.app.api.server.inter.AfreshContacterInfoMapperServerI;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.core.server.inter.BaseServerI;



/**
 * 
 * @author 作者：liudongliang <br>
 * @Date   创建时间：2017-12-04 <br>
 * @Description 
 *         1.添加职业信息
 *         192.168.3.19:8080/app-admin/web/personalcarerer/addcareerinfo.do?ID=0374431c90a942ff96993a90231bb889&FULL_WORK_NAME=hoomsun&ADDRESS=dizhi&COMPANY_ADDRESS_DETAIL=dizhi12&COMPANY_TEL_CODE=096&COMPANY_TEL_VALUE=793811&SALARY_MONTHLY=1000-2000
 *         2 省       
 *         192.168.3.19:8080/app-admin/web/personalcarerer/allprovinces.do 
 *         3 市
 *         192.168.3.19:8080/app-admin/web/personalcarerer/findcitiebypro.do?provinceId 
 *         4 区
 *         192.168.3.19:8080/app-admin/web/personalcarerer/findareabycity.do?cityId=
 *         
 */
@Controller
@RequestMapping("/web/personalcarerer")
public class PersonalCarererConteoller {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AfreshCareerInfoServerI afreshCareerServer;
	
	@Autowired
	private AfreshContacterInfoMapperServerI afreshContacterServer;
	

	@Autowired
	private BaseServerI baseServer;
	
	
	
	/**
	 * @Description     省编码
	 * @param 
	 * @return  Map
	 * @Date    2017-12-04
	 * @LoggerAnnotation(moduleName = "基本信息管理", option = "省编码列表查询")
	 */
	
	@RequestMapping("allprovinces.do")
	@ResponseBody
	public Map<String, Object> allprovinces(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 添加职业信息
			List<Map<String, String>> AllProvinces = baseServer.findAllProvinces();
			result.put("data", AllProvinces);
			result.put("errorInfo", "省编码");
			result.put("errorCode", 0);

		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("省编码时间：" + (endTime - startTime));
		}
		logger.info("省编码信息:" + result);

		return result;
	}

	/**
	 * @Description     市编码列
	 * @param 
	 * @return  Map
	 * @Date    2017-12-04
	 * @LoggerAnnotation(moduleName = "基本信息管理", option = "市编码列表查询")
	 */
	
	@RequestMapping("findcitiebypro.do")
	@ResponseBody
	public Map<String, Object> findCitieByPro(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String provinceId = request.getParameter("provinceId");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 添加职业信息
			List<Map<String, String>> CitieByPro = baseServer.findCitieByPro(provinceId);
			result.put("data", CitieByPro);
			result.put("errorInfo", "省编码");
			result.put("errorCode", 0);

		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("省编码时间：" + (endTime - startTime));
		}
		logger.info("省编码信息:" + result);

		return result;
	}

	/**
	 * @Description     区编码列
	 * @param 
	 * @return  Map
	 * @Date    2017-12-04
	 * @LoggerAnnotation(moduleName = "基本信息管理", option = "区编码列表查询")
	 */
	
	@RequestMapping("findareabycity.do")
	@ResponseBody
	public Map<String, Object> findAreaByCity(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String cityId = request.getParameter("cityId");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 添加职业信息
			List<Map<String, String>> AreaByCity = baseServer.findAreaByCity(cityId);
			result.put("data", AreaByCity);
			result.put("errorInfo", "省编码");
			result.put("errorCode", 0);

		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("省编码时间：" + (endTime - startTime));
		}
		logger.info("省编码信息:" + result);

		return result;
	}

	
	/**
	 * @Description     添加职业信息
	 * @param 
	 * @return  Map
	 * @Date    2017-12-04
	 * @LoggerAnnotation(moduleName = "基本信息管理", option = "添加职业信息")
	 */
	
	@RequestMapping("addcareerinfo.do")
	@ResponseBody
	public Map<String, Object> addCareerInfo(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间

		//
		String ID = request.getParameter("ID");
		String FULL_WORK_NAME = request.getParameter("FULL_WORK_NAME"); // 单位名称
		String COMPANY_ADDRESS = request.getParameter("COMPANY_ADDRESS"); // 地址
		String COMPANY_ADDRESS_DETAIL_CODE = request.getParameter("COMPANY_ADDRESS_DETAIL_CODE"); // 区地址
		String COMPANY_ADDRESS_DETAIL = request.getParameter("COMPANY_ADDRESS_DETAIL"); // 区地址
		String COMPANY_ADDRESS_CITY_CODE = request.getParameter("COMPANY_ADDRESS_CITY_CODE"); // 市地址
		String COMPANY_ADDRESS_CITY = request.getParameter("COMPANY_ADDRESS_CITY"); // 市地址
		String COMPANY_ADDRESS_PRO_CODE = request.getParameter("COMPANY_ADDRESS_PRO_CODE"); // 省地址
		String COMPANY_ADDRESS_PRO = request.getParameter("COMPANY_ADDRESS_PRO"); // 省地址
		String COMPANY_TEL_CODE = request.getParameter("COMPANY_TEL_CODE"); // 单位电话区号
		String COMPANY_TEL_VALUE = request.getParameter("COMPANY_TEL_VALUE"); // 单位电话号
//		String COMPANY_TEL = null; // 单位电话
		String SALARY_MONTHLY = request.getParameter("SALARY_MONTHLY"); // 单位电话号
		
		
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 添加职业信息
			AfreshCareerInfo CareerInfoOne = afreshCareerServer.selectByFkid(ID);
			AfreshCareerInfo CareerInfo = new AfreshCareerInfo();
			CareerInfo.setFkId(ID);
			CareerInfo.setSalaryMonthly(SALARY_MONTHLY);
			CareerInfo.setFullWorkName(FULL_WORK_NAME);
			CareerInfo.setCompanyAddress(COMPANY_ADDRESS);
			CareerInfo.setCompanyAddressDetail(COMPANY_ADDRESS_DETAIL);
			CareerInfo.setCompanyAddressDetailCode(COMPANY_ADDRESS_DETAIL_CODE);
			
			CareerInfo.setCompanyAddressCity(COMPANY_ADDRESS_CITY);
			CareerInfo.setCompanyAddressCityCode(COMPANY_ADDRESS_CITY_CODE);
			CareerInfo.setCompanyAddressPro(COMPANY_ADDRESS_PRO);
			CareerInfo.setCompanyAddressProCode(COMPANY_ADDRESS_PRO_CODE);
			CareerInfo.setCompanyTelCode(COMPANY_TEL_CODE);
			CareerInfo.setCompanyTelValue(COMPANY_TEL_VALUE);
			CareerInfo.setCompanyTel(COMPANY_TEL_CODE + "-" + COMPANY_TEL_VALUE);
			// 修改或添加
			if (CareerInfoOne == null) {
				CareerInfo.setRinfoPkId(PrimaryKeyUtil.getPrimaryKey());
				int i = afreshCareerServer.insertSelective(CareerInfo);
				if (i == 0) {
					result.put("errorInfo", "添加职业信息,请重试");
					result.put("errorCode", 1);
					return result;
				}
			} else {
				CareerInfo.setRinfoPkId(CareerInfoOne.getRinfoPkId());
				int i = afreshCareerServer.updateByPrimaryKeySelective(CareerInfo);
				if (i == 0) {
					result.put("errorInfo", "重认证职业信息,请重试");
					result.put("errorCode", 1);
					return result;
				}
			}
			result.put("data", CareerInfo);
			result.put("errorInfo", "添加职业信息成功");
			result.put("errorCode", 0);

		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("添加联系人职业信息使用时间：" + (endTime - startTime));
		}
		logger.info("添加联系人职业信息:" + result);

		return result;
	}
	
	

	
}
