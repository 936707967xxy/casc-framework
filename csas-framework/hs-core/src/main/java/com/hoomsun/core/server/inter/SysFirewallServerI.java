/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.inter;

import java.util.List;

import com.hoomsun.common.model.DataGrid;
import com.hoomsun.common.model.Json;
import com.hoomsun.core.model.SysFirewall;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月8日 <br>
 * 描述：防火墙
 */
public interface SysFirewallServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月8日 <br>
	 * 描述： 获取黑名单IP
	 * 
	 * @return
	 */
	List<String> findBlackList();

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月8日 <br>
	 * 描述： 获取白名单IP
	 * 
	 * @return
	 */
	List<String> findWhiteList();
	
	

	/**
	 * 
	 * 作者：liming <br>
	 * 创建时间：2017年11月9日 <br>
	 * 描述：  查询所有的数据
	 * @return
	 */
	List<SysFirewall> findAllData();
	
	/**
	 * 
	 * 作者：liming <br>
	 * 创建时间：2017年11月9日 <br>
	 * 描述： 
	 * @param page  当前页码
	 * @param rows  每页显示多少数据
	 * @param IP_ADDR  模糊查询
	 * @return
	 */
	DataGrid<SysFirewall> findPageData(Integer page, Integer rows, String IP_ADDR);
	
	/**
	 * 
	 * 作者：liming <br>
	 * 创建时间：2017年11月9日 <br>
	 * 描述： 插入防火墙数据
	 * @param firewall
	 * @return
	 */
	Json addFirewall(SysFirewall firewall);
	/**
	 * 
	 * 作者：liming <br>
	 * 创建时间：2017年11月9日 <br>
	 * 描述： 修改防火墙数据
	 * @param firewall
	 * @return
	 */
	Json updateFirewall(SysFirewall firewall);
	/**
	 * 
	 * 作者：liming <br>
	 * 创建时间：2017年11月9日 <br>
	 * 描述： 根据id删除
	 * @param FieId
	 * @return
	 */
	Json deleteFirewall(String fwId);
	/**
	 * 
	 * 作者：liming <br>
	 * 创建时间：2017年11月9日 <br>
	 * 描述： 根据id查询
	 * @param FieId
	 * @return
	 */
	SysFirewall findByFieId(String fwId);
	/**
	 * 
	 * 作者：liming <br>
	 * 创建时间：2017年11月9日 <br>
	 * 描述： 根据ip查询
	 * @param IP_ADDR
	 * @return
	 */
	SysFirewall findByIP(String ipAddr);
}
