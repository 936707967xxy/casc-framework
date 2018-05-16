/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.dao;

import java.util.List;
import java.util.Map;

import com.hoomsun.csas.sales.api.model.vo.LoanInfoVo;

/**
 * 作者：liming <br>
 * 创建时间：2017年12月16日 <br>
 * 描述：放款查询
 */
public interface LoanInfoMapper {
		
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月16日 <br>
	 * 描述： 查询所有的放款数据
	 * @param map
	 * @return
	 */
	List<LoanInfoVo>  findAllData(Map<String, Object> map);
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月16日 <br>
	 * 描述： 查询放款条数
	 * @param map
	 * @return
	 */
	int selectCount(Map<String, Object> map);
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月16日 <br>
	 * 描述： 根据申请id查询放款数据
	 * @param applyId
	 * @return
	 */
	LoanInfoVo selectByApplyId(String applyId);
}
