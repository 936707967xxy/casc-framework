package com.hoomsun.app.api.server.inter;

import java.util.List;

import com.hoomsun.app.api.model.Income;

public interface IncomeServerI {

	int insertSelective(Income record);

	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年10月07日 <br>
	 * 描述： 查询所有已上传得收入图像
	 * 
	 * @param department
	 * @return
	 */
	List<Income> selectAllByFkid(String fkId);
	
	 /**
  	 * 作者：liudongliang <br>
  	 * 创建时间：2017年10月10日 <br>
  	 * 描述： 删除收入证明 
  	 * @param department
  	 * @return
  	 */
    int deleteByFkid(String fkId);
}
