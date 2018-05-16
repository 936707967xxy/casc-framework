/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.sales;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.model.DataGrid;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.server.inter.BlackInfoServserI;
import com.hoomsun.core.util.LoginUtil;
import com.hoomsun.csas.sales.api.model.AssetInfo;
import com.hoomsun.csas.sales.api.model.Intention;
import com.hoomsun.csas.sales.api.model.OccupationalInfo;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.model.UserContacts;
import com.hoomsun.csas.sales.api.server.inter.IntentionServerI;

/**
 * 作者：liming <br>
 * 创建时间：2017年11月15日 <br>
 * 描述：添加预约客户控制层
 */
@Controller
public class IntentionController {

	private Logger log = LoggerFactory.getLogger(getClass());

	private IntentionServerI intentionserver;

	@Autowired
	public void setIntentionserver(IntentionServerI intentionserver) {
		this.intentionserver = intentionserver;
	}
	@Autowired
	private BlackInfoServserI  BlackInfoServser;
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月15日 <br>
	 * 描述： 跳转到预约页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listintention.do", method = RequestMethod.POST)
	public String intentionView() {
		log.info("跳转到添加预约页面==============");

		return "intention/listintention";

	}

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月16日 <br>
	 * 描述： 预约客户信息展示数据
	 * 
	 * @param page
	 *            页码
	 * @param rows
	 *            每页显示多少条数据
	 * @param loanid
	 *            根据客户编号模糊查询
	 * @return
	 */
	@RequestMapping(value = "/intentiondatagrid.do")
	@ResponseBody
	public DataGrid<Intention> finDataGrid(Integer page, Integer rows, String loanid) {
		log.info("展示预约数据==================");
		return intentionserver.findPageData(page, rows, loanid);
	}

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月21日 <br>
	 * 描述： 预约信息 新的分页
	 * 
	 * @param request
	 * @param page
	 *            当前页为1
	 * @param rows
	 * @param custname
	 *            模糊查询
	 * @return
	 */
	@RequestMapping(value = "sys/intention/intentionpager.do", method = { RequestMethod.POST })
	@ResponseBody
	public Pager<Intention> intentionPager(HttpServletRequest request, Integer page, Integer rows, String custname,String mobile) {
		log.info("预约信息展示带分页===============");
		
		SessionRouter sessionRouter=LoginUtil.getLoginSession(request);
		
		if (sessionRouter == null) {
			return null;
		}
	
		
		return intentionserver.findPage(page, rows, custname, sessionRouter, mobile);
	}
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月15日 <br>
	 * 描述： 录入客户预约信息
	 * 
	 * @param intention
	 * @param requset
	 * @param response
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "sys/intention/addintention.do", method = RequestMethod.POST)
	@ResponseBody
	public Json addIntention(@RequestBody Intention intention, HttpServletRequest request, HttpServletResponse response) throws ParseException {
		log.info("录入客户的预约信息==============");
		SessionRouter sessionRouter=LoginUtil.getLoginSession(request);
		if (sessionRouter == null) {
			return new Json(false, "登录异常,请刷新页面!");
		}
//		String comid=sr.getComId();//公司id
//		String comname=sr.getComName();//公司名称
		String empId=sessionRouter.getEmpId();//登录人员id
		String empName=sessionRouter.getEmpName();//登录人员name
		String deptId = sessionRouter.getDeptId();//部门id
		String deptname=sessionRouter.getDeptName();//部门name
		intention.setTeamid(deptId);//部门id
		intention.setDeptname(deptname);//部门name
		intention.setAppoperid(empId);//业务员编号
		intention.setAppopername(empName);//业务员姓名
		
		return intentionserver.addIntention(intention, sessionRouter);

	}

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月15日 <br>
	 * 描述： 修改预约信息表
	 * 
	 * @param intention
	 * @param requset
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "sys/intention/updateintention.do", method = RequestMethod.POST)
	@ResponseBody
	public Json updateIntention(@RequestBody Intention intention, HttpServletRequest requset, HttpServletResponse response) {
		log.info("修改客户的预约信息==============");

		return intentionserver.updateIntention(intention);

	}

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月15日 <br>
	 * 描述： 根据主键删除预约信息
	 * 
	 * @param ordplyId
	 * @param requset
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "sys/intention/deleteintention.do", method = RequestMethod.POST)
	@ResponseBody
	public Json deleteIntention(String ordplyId, HttpServletRequest requset, HttpServletResponse response) {
		log.info("删除客户的预约信息==============:" + ordplyId);
		return intentionserver.deleteIntention(ordplyId);

	}

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月20日 <br>
	 * 描述： 根据主键查询预约信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "sys/intention/findintentionbyid.do", method = RequestMethod.GET)
	@ResponseBody
	public Intention findIntentionByID(String ordplyId, HttpServletRequest requset, HttpServletResponse response) {
		log.info("根据主键查询客户的预约信息ordplyId==============:" + ordplyId);
		return intentionserver.findIntentionById(ordplyId);

	}

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月21日 <br>
	 * 描述： 根据主键查询预约信息
	 * 
	 * @param ordplyId
	 * @param requset
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/intention/findbyid.do", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject findById(String ordplyId, HttpServletRequest requset, HttpServletResponse response) {
		log.info("根据主键查询客户的预约信息ordplyId==============:" + ordplyId);

		return intentionserver.findById(ordplyId);

	}
	
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月5日 <br>
	 * 描述：  提供申请表没有的预约客户
	 * @param ordplyId
	 * @param requset
	 * @return
	 */
	@RequestMapping(value = "/sys/intention/findbyordplyId.do", method = RequestMethod.GET)
	@ResponseBody
	public UserApply findintentionById(String ordplyId, HttpServletRequest requset){
		log.info("提供申请表没有的预约客户=================");
		Intention intention=intentionserver.findByOrdplyId(ordplyId);
		//预约表信息
		String custname=intention.getCustname();//姓名
		String paperid=intention.getPaperid();//身份证
		String sexval=intention.getSexval();//性别
		String mobile=intention.getMobile();//手机号
		String appoperId=intention.getAppoperid();//业务员id
		String appoperName=intention.getAppopername();//业务员name
		String teamid=intention.getTeamid();//团队编号
		String deptname=intention.getDeptname();//团队名称
		String source=intention.getSource();//客户来源id
		String sourceval=intention.getSourceval();//客户来源
		
		//申请表model
		UserApply userapply=new UserApply();
		userapply.setCustName(custname);
		userapply.setIdNumber(paperid);
		userapply.setCustSex(sexval);
		userapply.setCustMobile(mobile);
		userapply.setSalesEmpId(appoperId);//销售id
		userapply.setSalesEmpName(appoperName);//销售name
		userapply.setSalesEmpDeptId(teamid);//销售所在部门id
		userapply.setSalesEmpDeptName(deptname);//销售所在部门名称
		userapply.setStoreId(intention.getOrgid());//门店id
		userapply.setStoreName(intention.getOrgname());//门店名称
		if (source != null) {
			userapply.setSources(Integer.parseInt(source));
		}
		
		userapply.setSourcesValue(sourceval);
		//赋值对象
		AssetInfo aset = new AssetInfo();//资产信息表
		OccupationalInfo occ = new OccupationalInfo();//职业信息表
		userapply.setAssetInfo(aset);
		List<UserContacts> lsst = new ArrayList<UserContacts>();//联系人
		userapply.setUserContacts(lsst);
		userapply.setUserOccupationalInfo(occ);
		return userapply;
		
	}
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月20日 <br>
	 * 描述： 检查手机号是否存在
	 * @param phone
	 * @param requset
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "sys/intention/checkphone.do", method = RequestMethod.GET)
	@ResponseBody
	public Json checkPhone(String phone, HttpServletRequest requset, HttpServletResponse response) {
		log.info("检查预约手机号是否存在phone==============:" + phone);
		return intentionserver.checkPhone(phone);

	}

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月20日 <br>
	 * 描述： 检查身份证是否存在
	 * @param idnumber
	 * @return
	 */
	@RequestMapping(value = "sys/intention/checkidnumber.do", method = RequestMethod.GET)
	@ResponseBody
	public Json checkIdNumber(String idnumber, HttpServletRequest requset, HttpServletResponse response) {
		log.info("检查预约身份证是否存在idnumber==============:" + idnumber);
		return intentionserver.checkIdNumber(idnumber);

	}
	

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月5日 <br>
	 * 描述： 根据预约信息的手机号查询是否为黑名单客户
	 * @param phone
	 * @param requset
	 * @return
	 */
	@RequestMapping(value = "sys/blackinfo/findbyphone.do", method = RequestMethod.GET)
	@ResponseBody
	public Json findByPhone(String phone, HttpServletRequest request){
		
		
		return BlackInfoServser.findByPhone(phone);
		
	} 
	
}
