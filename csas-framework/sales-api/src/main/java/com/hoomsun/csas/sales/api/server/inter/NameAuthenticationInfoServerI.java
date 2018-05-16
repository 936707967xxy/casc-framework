package com.hoomsun.csas.sales.api.server.inter;

import java.util.Map;
import com.hoomsun.app.api.model.AuthenticationUrl;
import com.hoomsun.csas.sales.api.model.NameAuthenticationInfoWithBLOBs;

/**
 * 作者：liudongliang<br>
 * 创建时间：2017年9月13日<br>
 * 描述：创建实名信息认证表
 * @param 
 * @return
 */
public interface NameAuthenticationInfoServerI {

	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月13日<br>
	 * 描述：插入实名认证信息
	 * @param  NameAuthenticationInfoWithBLOBs
	 * @return 
	 */
	public  int insertSelective(NameAuthenticationInfoWithBLOBs record);
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月13日<br>
	 * 描述：查询是否有实名信息
	 * @param  NameAuthenticationInfoWithBLOBs
	 * @return 
	 */
	public   NameAuthenticationInfoWithBLOBs selectByFkid(String fkId);
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月13日<br>
	 * 描述：修改实名认证信息
	 * @param  NameAuthenticationInfoWithBLOBs
	 * @return 
	 */
	public int updateByPrimaryKeySelective(NameAuthenticationInfoWithBLOBs record);
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年10-26日<br>
	 * 描述：线下单子推送实名数据 
	 * @param  NameAuthenticationInfoWithBLOBs
	 * @return 
	 */
	Map<String,Object>   selectDataByFkid(String fkId);
	
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年2月24日 <br>
	 * 描述： 插入实名认证信息并存储存储实名认证图片路径
	 * @param id
	 * @param nameAuth
	 * @param authenticationUrl
	 * @return
	 */
	
	public int saveNameAuthInfoAndUrlInfo(String id, NameAuthenticationInfoWithBLOBs nameAuth, AuthenticationUrl  authenticationUrl);
}
