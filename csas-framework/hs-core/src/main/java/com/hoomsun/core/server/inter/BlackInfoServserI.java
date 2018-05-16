/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.inter;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.BlackInfo;
import com.hoomsun.core.model.vo.SessionRouter;


/**
 * 作者：liming <br>
 * 创建时间：2017年12月4日 <br>
 * 描述：黑名单
 */
public interface BlackInfoServserI {
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 添加黑名单
	 * @param blackinfo
	 * @return
	 */
	Json addBlackInfo(BlackInfo blackinfo,SessionRouter sessionRouter);
	
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 修改黑名单
	 * @param blackinfo
	 * @return
	 */
	Json updateBlackInfo(BlackInfo blackinfo,SessionRouter sessionRouter);
	
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 删除黑名单
	 * @param blackinfo
	 * @return
	 */
	Json delteBlackInfo(String id);
	
	
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 分页
	 * @param page
	 * @param rows
	 * @param custname
	 * @return
	 */
	Pager<BlackInfo> findPage(Integer page, Integer rows,String custname,String phone,String idnumber,Integer custstate);
	
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 根据id查询
	 * @param id
	 * @return
	 */
	BlackInfo  findById(String id);

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月5日 <br>
	 * 描述： 根据预约信息的手机号查询是否为黑名单客户
	 * @param phone
	 * @return
	 */
	Json findByPhone(String phone);
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年1月21日 <br>
	 * 描述： 同意审批
	 * @param blackinfo
	 * @return
	 */
	Json agree(BlackInfo blackinfo,SessionRouter sessionRouter);

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年1月21日 <br>
	 * 描述： 不同意审批
	 * @param blackinfo
	 * @param sessionRouter
	 * @return
	 */
	Json noagree(BlackInfo blackinfo, SessionRouter sessionRouter);

}
