package com.hoomsun.app.api.server.inter;

import java.util.List;

import com.hoomsun.app.api.model.AfreshContacterInfo;



/**
 * 作者：liudongliang <br>
 * 创建时间：2017年9月14日<br>
 * 描述：联系人信息
 * 
 * @return
 */
public interface AfreshContacterInfoMapperServerI {
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：联系人信息添加
	 * 
	 * @return
	 */
	 int insertSelective(AfreshContacterInfo record);
	 /**
		 * 作者：liudongliang <br>
		 * 创建时间：2017年9月14日<br>
		 * 描述：查询是否存在联系人 
		 * 
		 * @return
		 */
	 List<AfreshContacterInfo> selectByFkid(String fkId);
	 
	 /**
		 * 作者：liudongliang <br>
		 * 创建时间：2017年9月14日<br>
		 * 描述：修改存在的联系人 
		 * 
		 * @return
		 */
	 int updateByPrimaryKey(AfreshContacterInfo record);
	 /**
		 * 作者：liudongliang <br>
		 * 创建时间：2017年12月15日<br>
		 * 描述：删除存在的联系人 
		 * 
		 * @return
		 */
	 int deleteByfkId(String fkId);
}
