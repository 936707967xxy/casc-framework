/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.sales;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.BlackInfo;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.server.inter.BaseServerI;
import com.hoomsun.core.server.inter.BlackInfoServserI;
import com.hoomsun.core.util.LoginUtil;

/**
 * 作者：liming <br>
 * 创建时间：2017年12月4日 <br>
 * 描述：黑名单控制层
 */
@Controller
public class BlackInfoController {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private BlackInfoServserI  BlackInfoServser;
	@Autowired
	public void setBlackInfoServser(BlackInfoServserI blackInfoServser) {
		BlackInfoServser = blackInfoServser;
	}
	
	@Autowired
	private BaseServerI baseServer; 
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 分页查询
	 * @param request
	 * @param page
	 * @param rows
	 * @param custname
	 * @return
	 */
	@RequestMapping(value = "sys/blackinfo/page.do", method = { RequestMethod.POST })
	@ResponseBody
	public Pager<BlackInfo> BlackInfoPage(HttpServletRequest request, Integer page, Integer rows, String custname,String phone,String idnumber,Integer custstate){
		SessionRouter sessionRouter=LoginUtil.getLoginSession(request);
		if (sessionRouter == null) {
			return null;
		}
		log.info("黑名单分页查询=====================");
		return BlackInfoServser.findPage(page, rows, custname,phone,idnumber,custstate);
		
	}
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述：添加黑名单
	 * @param request
	 * @param blackinfo
	 * @return
	 */
	@RequestMapping(value = "sys/blackinfo/add.do",  method = RequestMethod.POST)
	@ResponseBody
	public Json addBlackInfo(@RequestBody BlackInfo blackinfo,HttpServletRequest request){
		SessionRouter sessionRouter=LoginUtil.getLoginSession(request);
		if (sessionRouter == null) {
			return new Json(false, "登录异常,请刷新页面!");
		}
		
		return BlackInfoServser.addBlackInfo(blackinfo,sessionRouter);
		
		
	}
	
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 修改黑名单
	 * @param request
	 * @param blackinfo
	 * @return
	 */
	@RequestMapping(value = "sys/blackinfo/update.do",  method = RequestMethod.POST)
	@ResponseBody
	public Json updateBlackInfo(@RequestBody BlackInfo blackinfo,HttpServletRequest request){
		SessionRouter sessionRouter=LoginUtil.getLoginSession(request);
		if (sessionRouter == null) {
			return new Json(false, "登录异常,请刷新页面!");
		}
		return BlackInfoServser.updateBlackInfo(blackinfo,sessionRouter);
	}
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 根据id删除
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "sys/blackinfo/delete.do",  method = RequestMethod.POST)
	@ResponseBody
	public Json deleteBlackInfo(HttpServletRequest request,String id){
		return BlackInfoServser.delteBlackInfo(id);
	}
	
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 根据id查询
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "sys/blackinfo/findbyid.do",  method = RequestMethod.GET)
	@ResponseBody
	public  BlackInfo findById(HttpServletRequest request,String id){
		return BlackInfoServser.findById(id);
	}
	

	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年1月21日 <br>
	 * 描述： 同意审批
	 * @param blackinfo
	 * @param requset
	 * @return
	 */
	@RequestMapping(value = "sys/blackinfo/agree.do", method = RequestMethod.POST)
	@ResponseBody
	public Json Agree(@RequestBody BlackInfo blackinfo, HttpServletRequest request){
		SessionRouter sessionRouter=LoginUtil.getLoginSession(request);
		if (sessionRouter == null) {
			return new Json(false, "登录异常,请刷新页面!");
		}
		
		return BlackInfoServser.agree(blackinfo,sessionRouter);
		
	} 
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年1月21日 <br>
	 * 描述： 不同意审批
	 * @param blackinfo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "sys/blackinfo/noagree.do", method = RequestMethod.POST)
	@ResponseBody
	public Json noAgree(@RequestBody BlackInfo blackinfo, HttpServletRequest request){
		SessionRouter sessionRouter=LoginUtil.getLoginSession(request);
		if (sessionRouter == null) {
			return new Json(false, "登录异常,请刷新页面!");
		}
		
		return BlackInfoServser.noagree(blackinfo,sessionRouter);
		
	} 
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年1月21日 <br>
	 * 描述： 获取省份
	 * @return
	 */
	@RequestMapping(value = "/sys/blackinfo/findallprovinces.do")
	@ResponseBody
	public List<Map<String, String>> findAllProvinces() {
		return baseServer.findAllProvinces();
	}

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年1月21日 <br>
	 * 描述： 获取市
	 * @param provinceId
	 * @return
	 */
	@RequestMapping(value = "/sys/blackinfo/findcitiebypro.do")
	@ResponseBody
	public List<Map<String, String>> findCitieByPro(String provinceId) {
		return baseServer.findCitieByPro(provinceId);
	}

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年1月21日 <br>
	 * 描述： 获取区
	 * @param cityId
	 * @return
	 */
	@RequestMapping(value = "/sys/blackinfo/findareabycity.do")
	@ResponseBody
	public List<Map<String, String>> findAreaByCity(String cityId) {
		return baseServer.findAreaByCity(cityId);
	}
}
