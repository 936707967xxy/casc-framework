/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.server.inter;

import com.hoomsun.risk.model.vo.UserApplyVo;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月30日 <br>
 * 描述：用户申请信息的业务接口
 */
public interface UserApplyServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月30日 <br>
	 * 描述：更具用户的证件号获取 
	 * @param idNumber 证件号
	 * @return
	 */
	UserApplyVo findByIdNumber(String idNumber);
	
}
