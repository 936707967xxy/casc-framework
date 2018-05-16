package com.hoomsun.app.api.controller.credit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.app.api.util.PositionUtils;
import com.hoomsun.core.server.inter.SysEmployeeServerI;
import com.hoomsun.csas.sales.api.model.NameAuthentication;
import com.hoomsun.csas.sales.api.server.inter.NameAuthenticationServerI;


/**
 * 
 * @author 刘栋梁
 * @date 2017-09-14
 * @resource 门店定位 1.门店定位
 *           http://192.168.3.19:8080/app-admin/web/departlocation/findDepart.do?LONGITUDE=1&LATITUDE=1&inviteCode=201611150157
 *           
 *           1.邀请码修改  申请门店信息时邀请码必须填写 
 *           192.168.3.19:8080/app-admin/web/departlocation/updatainvescode.do?ID=&INVITECODE=&INVITEDEPTID=
 *
 */
@Controller
@RequestMapping("web/departlocation")
public class DepartLocationController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysEmployeeServerI   sysEmployeeServerI;

	@Autowired
	private NameAuthenticationServerI nameAuthenticationServerI;
	
	/**
	 * 查询门店定位 刘栋梁 1.1 2017-07-14
	 * @LoggerAnnotation(moduleName = "门店定位接口", option = "查询门店定位")
	 * @return
	 */
	
/*	@RequestMapping(value = "findDepart.do")
	@ResponseBody
	public Map<String, Object> findDepart(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		
		String inviteCode = request.getParameter("inviteCode");
		logger.info( "===inviteCode=" + inviteCode);

		Map<String, Object> map = new HashMap<String, Object>();
		try {			   
				Map<String, Object> userResult=sysEmployeeServerI.findAppUserInfo(inviteCode);
				if(userResult!=null){
					String partId=userResult.get("DEPT_ID").toString();
					Map<String, Object> deptResult=sysEmployeeServerI.findAppUserStorInfo(partId);
					if (deptResult != null ) {					
						userResult.put("STORE_ID", deptResult.get("DEPT_ID"));
						userResult.put("STORE_NAME", deptResult.get("DEPT_NAME"));
						map.put("errorInfo", "查询成功");
						map.put("errorCode", 0);
						map.put("data", userResult);
					}else{
						map.put("errorInfo", "未查找到该邀请码对应的营业部,请核对");
						map.put("errorCode", 1);
					}
					
				}else{
					
					map.put("errorInfo", "未查找到该邀请码对应的营业部,请核对");
					map.put("errorCode", 1);
				}
				
				
			

		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", "网络异常，请稍后！");
			map.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("查询门店定位 使用时间：" + (endTime - startTime));
		}
		logger.info("当前区域最近的门店Departmap" + map);
		return map;

	}*/
	
	
	@RequestMapping(value = "findDepart.do")
	@ResponseBody
	public Map<String, Object> findDepart(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String Longitude = request.getParameter("LONGITUDE");
		String Latitude = request.getParameter("LATITUDE");
		String inviteCode = request.getParameter("inviteCode");
		logger.info(Longitude + "=================" + Latitude + "===inviteCode=" + inviteCode);

		Map<String, Object> map = new HashMap<String, Object>();
		try {			
			if (!"000000".equals(inviteCode)) { // 客户邀请码-----------门店已定
				Map<String, Object> userResult=sysEmployeeServerI.findAppUserInfo(inviteCode);
				String partId=userResult.get("DEPT_ID").toString();
				Map<String, Object> deptResult=sysEmployeeServerI.findAppUserStorInfo(partId);
				if (deptResult != null && userResult!=null) {
					userResult.put("STORE_ID", deptResult.get("DEPT_ID"));
					userResult.put("STORE_NAME", deptResult.get("DEPT_NAME"));
					map.put("errorInfo", "查询成功");
					map.put("errorCode", 0);
					map.put("data", userResult);
					return map;
				}else{
					map.put("errorInfo", "未查找到该邀请码对应的营业部,请核对");
					map.put("errorCode", 1);
					return map;
				}
			}

			// 获取数据库中当前单子的最后一个节点 -----------------------
			List<Map<String, Object>> list = sysEmployeeServerI.findAllStoreData();
			Map<Object, String> disMap = new HashMap<Object, String>();
			// 求所有距离数组
			for (int i = 0; i < list.size(); i++) {
				String CreditId = list.get(i).get("DEPT_ID").toString();
				Double Longitude1 = Double.parseDouble(list.get(i).get("DEPT_LATITUDE").toString().trim());
				Double Latitude1 = Double.parseDouble(list.get(i).get("DEPT_LONGITUDE").toString().trim());
				Double dis = PositionUtils.disTance(Double.parseDouble(Longitude), Double.parseDouble(Latitude), Longitude1, Latitude1); // 求距离
				disMap.put(dis, CreditId); // 存部门id为值 距离为key
			}
			// 排序
			logger.info("距离======" + disMap);
			Set<Object> set = disMap.keySet();
			Object[] obj = set.toArray();
			Arrays.sort(obj);
			logger.info("最近距离======" + obj[0] + "====" + disMap.get(obj[0]));
			
			//没邀请码            归属固定帐号 
			//{"USER_LOCK":1,"STORE_NAME":"柳州桂中大道营业部","USER_ID":"201611150157","COMPANY_ID":441,"USER_NAME":"姚金琼","COMPANY_NAME":"红上至信商务信息咨询（上海）有限公司","PART_NAME":"销售三部","PART_ID":2103,"ID":10288,"STORE_ID":749,"STATUS":"离职"}
			Map<String, Object> userResult=new  HashMap<String, Object>();
			userResult.put("EMP_ID", "000000");
			userResult.put("EMP_NAME", "000000");
			userResult.put("DEPT_ID", "000000");
			userResult.put("DEPT_NAME", "000000");
			Map<String, Object> deptResult=sysEmployeeServerI.findAppUserStorInfo(disMap.get(obj[0]));
			if (deptResult != null && userResult!=null) {
				userResult.put("STORE_ID", deptResult.get("DEPT_ID"));
				userResult.put("STORE_NAME", deptResult.get("DEPT_NAME"));
				map.put("errorInfo", "查询成功");
				map.put("errorCode", 0);
				map.put("data", userResult);
				return map;
			}else{
				map.put("errorInfo", "定位失败");
				map.put("errorCode", 1);
			}

		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", "网络异常，请稍后！");
			map.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("查询门店定位 使用时间：" + (endTime - startTime));
		}
		logger.info("当前区域最近的门店Departmap" + map);
		return map;

	}
	
	
	/**
	 * 邀请码修改 刘栋梁 1.1 2017-10-24
	 * 
	 * @return
	 * @LoggerAnnotation(moduleName = "门店定位接口", option = "邀请码修改")
	 */
	
	@RequestMapping(value = "updatainvescode.do")
	@ResponseBody
	public Map<String, Object> updataInvescode(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String ID = request.getParameter("ID");
		String INVITEDEPTID = request.getParameter("INVITEDEPTID");
		String INVITECODE = request.getParameter("INVITECODE");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> oAUser=sysEmployeeServerI.findAppUserInfo(INVITECODE);
			if (oAUser==null) {				
				map.put("errorInfo", "当前用户不存在,请核对邀请码");
				map.put("errorCode", 1);
				return map;
			} else {
				String userlock=oAUser.get("STATUS")+"";
				if("离职".equals(userlock)){
					map.put("errorInfo", "当前用户离职,请核对邀请码");
					map.put("errorCode", 1);
					return map;
			    }
			}
			
			if (!StringUtils.isBlank(ID) || !StringUtils.isBlank(INVITEDEPTID)) { // 客户邀请码
				NameAuthentication hs = new NameAuthentication();
				hs.setId(ID);
				hs.setInvitecode(INVITECODE);
				hs.setInvitedeptid(INVITEDEPTID);
				int i = nameAuthenticationServerI.updateByPrimaryKeySelective(hs);
				if (i == 1) {
					map.put("errorInfo", "修改成功");
					map.put("errorCode", 0);
					map.put("data", hs);
				} else {
					map.put("errorInfo", "修改失败");
					map.put("errorCode", 1);
					map.put("data", hs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", "网络异常，请稍后！");
			map.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("邀请码修改：" + (endTime - startTime));
		}
		logger.info("邀请码修改" + map);
		return map;

	}



}
