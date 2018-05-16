package com.hoomsun.message.server.inter;

import java.util.List;

import com.hoomsun.message.model.NameAuthentication;


/**
 * 作者：liudongliang<br>
 * 创建时间：2017年9月12日<br>
 * 描述：创建认证表
 * @param 
 * @return
 */
public interface NameAuthenticationServerI {

	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年10月24日<br>
	 * 描述：创建公司
	 * @param company 查询所有数据补全签名  
	 * @return
	 */
	List<NameAuthentication> selectAllData();
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年10月24日<br>
	 * 描述：身份证获取极光id
	 * @param 
	 * @return
	 */
	NameAuthentication selectByIdcard(String paperid);
}
