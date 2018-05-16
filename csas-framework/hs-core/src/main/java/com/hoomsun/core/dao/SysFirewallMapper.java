package com.hoomsun.core.dao;

import java.util.List;
import java.util.Map;


import com.hoomsun.core.model.SysFirewall;


public interface SysFirewallMapper {
    int deleteByPrimaryKey(String fwId);

    int insert(SysFirewall record);

    int insertSelective(SysFirewall record);

    SysFirewall selectByPrimaryKey(String fwId);

    int updateByPrimaryKeySelective(SysFirewall record);

    int updateByPrimaryKey(SysFirewall record);
    /**
     * 作者：ywzou <br>
     * 创建时间：2017年11月8日 <br>
     * 描述： 获取黑名单IP
     * @return
     */
    List<String> findBlackList();
    /**
     * 作者：ywzou <br>
     * 创建时间：2017年11月8日 <br>
     * 描述： 获取白名单IP
     * @return
     */
    List<String> findWhiteList();
    
    /**
     * 
     * 作者：liming <br>
     * 创建时间：2017年11月9日 <br>
     * 描述： 根据条件查询
     * @param param
     * @return
     */
	List<SysFirewall> findPageData(Map<String, Object> param);
	/**
	 * 
	 * 作者：liming <br>
	 * 创建时间：2017年11月9日 <br>
	 * 描述：  根据条件查询
	 * @param param
	 * @return
	 */
	Integer findPageCount(Map<String, Object> param);
	/**
	 * 
	 * 作者：liming <br>
	 * 创建时间：2017年11月9日 <br>
	 * 描述： 查询全部数据
	 * @return
	 */
	List<SysFirewall> findAll();
    
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