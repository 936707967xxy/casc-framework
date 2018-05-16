/**
 * Copyright www.idawn.org 邹益伟 idawnorg@gmail.com
 */
package com.hoomsun.csas.audit.server.inter;

import java.util.List;

import com.hoomsun.common.model.Json;
import com.hoomsun.csas.audit.model.PhoneVerify;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月10日 <br>
 * 描述：电话核查的业务
 */
public interface PhoneVerifyServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月10日 <br>
	 * 描述：更具申请ID获取电核数据 
	 * @param applyId
	 * @return
	 */
	List<PhoneVerify> findByApplyId(String applyId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月10日 <br>
	 * 描述：根据联系人id获取审核数据
	 * @param consId
	 * @return
	 */
	PhoneVerify findByConsId(String consId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月10日 <br>
	 * 描述： 更具申请ID获取审核数据
	 * @param id
	 * @return
	 */
	PhoneVerify findById(String id);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月10日 <br>
	 * 描述： 保存电核数据
	 * @param phoneVerify
	 * @return
	 */
	Json savePhoneVerify(PhoneVerify phoneVerify);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月10日 <br>
	 * 描述： 修改电核数据
	 * @param phoneVerify
	 * @return
	 */
	Json updatePhoneVerify(PhoneVerify phoneVerify);
}
