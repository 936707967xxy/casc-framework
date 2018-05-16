package com.hoomsun.csas.sales.api.server.inter;


import java.util.List;
import java.util.Map;


/**
 * 作者：ygzhao <br>
 * 创建时间：2012年1月8日 <br>
 * 描述：认证清单
 */
public interface UserAllAuthInfoServerI {
	
	/**
	 * 根据applyId查询认证清单
	 * @param applyId
	 * @return
	 */
	public List<Map<String,Object>> selectByApplyId(String applyId);
}
