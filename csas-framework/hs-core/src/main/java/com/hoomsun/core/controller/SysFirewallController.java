/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.DataGrid;
import com.hoomsun.common.model.Json;
import com.hoomsun.core.anno.LoggerAnnotation;
import com.hoomsun.core.anno.Permission;
import com.hoomsun.core.model.SysFirewall;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.server.inter.SysFirewallServerI;
import com.hoomsun.core.util.LoginUtil;

/**
 * 作者：liming <br>
 * 创建时间：2017年11月9日 <br>
 * 描述：防火墙展示
 */
@Controller
public class SysFirewallController {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private  SysFirewallServerI  firewall;
	
	
	public SysFirewallServerI getFirewall() {
		return firewall;
	}
	@Autowired
	public void setFirewall(SysFirewallServerI firewall) {
		this.firewall = firewall;
	}
	/**
	 * 
	 * 作者：liming <br>
	 * 创建时间：2017年11月9日 <br>
	 * 描述： 跳转防火墙页面
	 * @return
	 */
	   @Permission("firewall_query")
	   @RequestMapping(value = "/sys/firewall/listview.do", method = RequestMethod.GET)
	   public String ProlistView(){
		   
		return "firewall/listview";
		   
	   }
	   /**
	    * 
	    * 作者：liming <br>
	    * 创建时间：2017年11月9日 <br>
	    * 描述： 防火墙展示数据
	    * @param page
	    * @param rows
	    * @param IP_ADDR
	    * @return
	    */
		@Permission("firewall_query")
		@RequestMapping(value = "/sys/firewall/firewallDatagrid.do")
		@ResponseBody
		public DataGrid<SysFirewall> finDataGrid(Integer page, Integer rows, String ipAddr) {
			log.info("防火墙展示数据==================");
			return firewall.findPageData(page, rows, ipAddr);
		}
		
		/**
		 * 
		 * 作者：liming <br>
		 * 创建时间：2017年11月9日 <br>
		 * 描述： 添加防火墙页面
		 * @return
		 */
		
		@Permission("firewall_add")
		@RequestMapping(value="/sys/firewall/addfirewallView.do",method=RequestMethod.GET)
		public String addFirewallView(){
			log.info("添加防火墙页面==================");
			return  "firewall/addview";
			
		}
		
		/**
		 * 
		 * 作者：liming <br>
		 * 创建时间：2017年11月9日 <br>
		 * 描述： 添加防火墙
		 * @param fire
		 * @return
		 */
		@LoggerAnnotation(moduleName = "防火墙设置", option = "添加ip")
		@Permission("firewall_add")
		@RequestMapping(value="/sys/firewall/addfirewall.do",method=RequestMethod.POST)
		@ResponseBody
		public Json  addFirewall(SysFirewall fire,HttpServletRequest request){
			log.info("添加防火墙数据==================");
			SessionRouter session = LoginUtil.getLoginSession(request);
			fire.setCreateEmp(session.getEmpId());
			return firewall.addFirewall(fire);
			
		}
		/**
		 * 
		 * 作者：liming <br>
		 * 创建时间：2017年11月9日 <br>
		 * 描述： 修改防火墙页面
		 * @param fieId
		 * @param request
		 * @return
		 */
		@Permission("firewall_edit")
		@RequestMapping(value="/sys/firewall/editfirewall.do",method=RequestMethod.GET)
		public String editView(String fwId, HttpServletRequest request) {
			log.info("修改防火墙页面==================");
			SysFirewall Firewall = firewall.findByFieId(fwId);
			request.setAttribute("SYS_FIREWALL_KEY", Firewall);
			return "firewall/editview";
		}
		/**
		 * 
		 * 作者：liming <br>
		 * 创建时间：2017年11月9日 <br>
		 * 描述： 修改防火墙
		 * @param fireWal
		 * @param request
		 * @return
		 */
		@LoggerAnnotation(moduleName = "防火墙设置", option = "修改ip")
		@Permission("firewall_edit")
		@RequestMapping(value = "/sys/firewall/updatefirewall.do", method = RequestMethod.POST)
		@ResponseBody
		public Json updatefirewall(SysFirewall fireWal, HttpServletRequest request) {
			return firewall.updateFirewall(fireWal);
		}
		
		/**
		 * 
		 * 作者：liming <br>
		 * 创建时间：2017年11月9日 <br>
		 * 描述： 根据id删除防火墙
		 * @param FwId
		 * @param request
		 * @return
		 */
		@LoggerAnnotation(moduleName = "防火墙设置", option = "删除ip")
		@Permission("firewall_delete")
		@RequestMapping(value = "/sys/firewall/removefirewall.do", method = RequestMethod.POST)
		@ResponseBody
		public Json deletefirewall(String fwId, HttpServletRequest request) {
			return firewall.deleteFirewall(fwId);
		}
}
