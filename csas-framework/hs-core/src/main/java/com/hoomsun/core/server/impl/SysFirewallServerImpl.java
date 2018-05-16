/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.DataGrid;
import com.hoomsun.common.model.Json;
import com.hoomsun.core.dao.SysFirewallMapper;
import com.hoomsun.core.model.SysFirewall;
import com.hoomsun.core.server.inter.SysFirewallServerI;
import com.hoomsun.core.util.DateUtil;
import com.hoomsun.core.util.PrimaryKeyUtil;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月8日 <br>
 * 描述：
 */
@Service("firewallServer")
public class SysFirewallServerImpl implements SysFirewallServerI {
	@Autowired
	private SysFirewallMapper firewallMapper;

	@Override
	public List<String> findBlackList() {
		return firewallMapper.findBlackList();
	}

	@Override
	public List<String> findWhiteList() {
		return firewallMapper.findWhiteList();
	}

	@Override
	public Json addFirewall(SysFirewall firewall) {
		if (StringUtils.isBlank(firewall.getFwId())) {
			firewall.setFwId(PrimaryKeyUtil.getPrimaryKey());
		}
		firewall.setCreateTime(DateUtil.getTimestamp());
		String IpAddr = firewall.getIpAddr();
		if (StringUtils.isBlank(IpAddr) || "-1".equals(IpAddr)) {
			firewall.setIpAddr(null);
		}
		
		int result = firewallMapper.insertSelective(firewall);
		if(result > 0){
			return new Json(true, "防火墙数据添加成功!");
		}else{
			return new Json(false, "防火墙数据添加失败!");
		}
		
	}

	@Override
	public Json updateFirewall(SysFirewall firewall) {
		String IP_ADDR = firewall.getIpAddr();
		if (StringUtils.isBlank(IP_ADDR) || "-1".equals(IP_ADDR)) {
			firewall.setIpAddr(null);
		}
		int result=firewallMapper.updateByPrimaryKey(firewall);
		if(result > 0){
			return new Json(true, "防火墙数据修改成功!");
		}else{
			return new Json(false, "防火墙数据修改失败!");
		}
	}
	
	@Override
	public Json deleteFirewall(String fwId) {
		int result=firewallMapper.deleteByPrimaryKey(fwId);
		if(result > 0){
			return new Json(true, "防火墙数据删除成功!");
		}else{
			return new Json(false, "防火墙数据删除失败!");
		}
		
	}

	@Override
	public List<SysFirewall> findAllData() {
		// TODO Auto-generated method stub
		return firewallMapper.findAll();
	}
	
	@Override
	public DataGrid<SysFirewall> findPageData(Integer page, Integer rows, String ipAddr) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (null != page && null != rows) {
			rows = rows > 50 ? 50 : rows;
			param.put("pageIndex", (page-1)*rows);
			param.put("pageSize", rows);
		}
		
		if (!StringUtils.isBlank(ipAddr)) {
			param.put("ipAddr", "%"+ipAddr+"%");
		}
		
		List<SysFirewall> firewall =firewallMapper.findPageData(param);
		
		Integer total =firewallMapper.findPageCount(param);
		return new DataGrid<SysFirewall>(total, firewall);
	}

	@Override
	public SysFirewall findByFieId(String fwId) {
		if (StringUtils.isBlank(fwId)) {
			return null;
		}
		
		return firewallMapper.selectByPrimaryKey(fwId);
		
	}

	@Override
	public SysFirewall findByIP(String ipAddr) {
		// TODO Auto-generated method stub
		return firewallMapper.findByIP(ipAddr);
	}


}
