package com.hoomsun.app.api.server.inter;

import com.hoomsun.app.api.model.AfreshCareerInfo;


/**
 * 作者：liudongliang <br>
 * 创建时间：2017年9月14日<br>
 * 描述：职业信息添加信息
 * 
 * @return
 */
public interface AfreshCareerInfoServerI {
	 /**
	  * 作者：liudongliang <br>
	  * 创建时间：2017年9月14日<br>
	  * 描述：第一次录入职业信息
	  * 
	  * @return
	  */
	 int insertSelective(AfreshCareerInfo record);
	 /**
	  * 作者：liudongliang <br>
	  * 创建时间：2017年9月14日<br>
	  * 描述：修改职业信息
	  * 
	  * @return
	  */
	 int updateByPrimaryKeySelective(AfreshCareerInfo record);
	 /**
	  * 作者：liudongliang <br>
	  * 创建时间：2017年9月14日<br>
	  * 描述：查询用户是否已经录入信息
	  * 
	  * @return
	  */
	 AfreshCareerInfo selectByFkid(String fkId);
}
