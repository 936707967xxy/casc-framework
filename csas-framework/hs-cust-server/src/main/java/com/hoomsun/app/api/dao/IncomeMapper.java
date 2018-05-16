package com.hoomsun.app.api.dao;

import java.util.List;

import com.hoomsun.app.api.model.Income;

public interface IncomeMapper {
    int deleteByPrimaryKey(String incomeid);

    int insert(Income record);

    int insertSelective(Income record);

    Income selectByPrimaryKey(String incomeid);

    int updateByPrimaryKeySelective(Income record);

    int updateByPrimaryKey(Income record);
    
    /**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年10月07日 <br>
	 * 描述： 查询所有已上传得收入图像
	 * @param department
	 * @return
	 */
    List<Income>  selectAllByFkid(String fkId);
    
    /**
  	 * 作者：liudongliang <br>
  	 * 创建时间：2017年10月1日 <br>
  	 * 描述： 删除收入证明 
  	 * @param department
  	 * @return
  	 */
    int deleteByFkid(String fkId);
}