package com.hoomsun.app.api.controller.credit;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.app.api.model.CarrearInfo;
import com.hoomsun.app.api.server.inter.CarrearInfoServerI;
import com.hoomsun.common.util.PrimaryKeyUtil;

/**
 * 
 * @author 作者：liudongliang <br>
 * @Date   创建时间：2017-12-14 <br>
 * @Description 
 *              1.职业信息
 *              192.168.3.19:8080/app-admin/web/carrear/addcarrearinfo.do
 *              2.查询个人信息
 *              192.168.3.19:8080/app-admin/web/carrear/carrearinfo.do?ID=
 */
@Controller
@RequestMapping("web/carrear")
public class CarrearInfoController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CarrearInfoServerI  carrearInfoServerI;
	
	/**
	 * @Description     添加个人职业信息
	 * @param   实体 
	 * @return  Map
	 * @Date    2017-12-14
	 * 
	 */	
	@RequestMapping("addcarrearinfo.do")
	@ResponseBody
	public Map<String, Object> addCarrearInfo(HttpServletRequest request,@RequestBody CarrearInfo carrearinfo) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			String fk_id=carrearinfo.getFkId();
			if(StringUtils.isBlank(fk_id)){
				result.put("errorInfo", "未找到用户！！");
				result.put("errorCode", 1);
			}
			
			CarrearInfo CarrearInfo=carrearInfoServerI.selectByfkId(fk_id);
			if(CarrearInfo!=null){
				carrearinfo.setOcinfoPkId(CarrearInfo.getOcinfoPkId());
				int i=carrearInfoServerI.updateByfkId(carrearinfo);
				result.put("data", carrearinfo);
				result.put("errorInfo", "修改个人职业信息");
				result.put("errorCode", 0);
			}else{
				carrearinfo.setOcinfoPkId(PrimaryKeyUtil.getPrimaryKey());
				int i=carrearInfoServerI.insertSelective(carrearinfo);
				result.put("data", carrearinfo);
				result.put("errorInfo", "添加个人职业信息");
				result.put("errorCode", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("修改个人职业信息使用时间：" + (endTime - startTime));
		}
		logger.info("修改个人职业信息:" + result);

		return result;
	}
	
	/**
	 * @Description     查询个人职业信息
	 * @param   实体 
	 * @return  Map
	 * @Date    2017-12-14
	 * 
	 */	
	@RequestMapping("carrearinfo.do")
	@ResponseBody
	public Map<String, Object> CarrearInfo(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String fk_id=request.getParameter("ID");
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if(StringUtils.isBlank(fk_id)){
				result.put("errorInfo", "未找到用户！！");
				result.put("errorCode", 1);
			}			
			CarrearInfo CarrearInfo=carrearInfoServerI.selectByfkId(fk_id);
			result.put("data", CarrearInfo);
			result.put("errorInfo", "查询个人职业信息");
			result.put("errorCode", 0);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("查询个人职业信息使用时间：" + (endTime - startTime));
		}
		logger.info("查询个人职业信息:" + result);

		return result;
	}
	

}
