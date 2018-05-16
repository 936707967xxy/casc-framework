/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import java.text.ParseException;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.model.DataGrid;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.csas.sales.api.model.Intention;

/**
 * 作者：liming <br>
 * 创建时间：2017年11月15日 <br>
 * 描述：客户预约
 */
public interface IntentionServerI {

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月15日 <br>
	 * 描述： 添加预约信息
	 * 
	 * @param intention
	 * @return
	 * @throws ParseException 
	 */
	Json addIntention(Intention intention,SessionRouter sessionRouter) throws ParseException;

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月15日 <br>
	 * 描述：
	 * 
	 * @param intention
	 * @return
	 */
	Json updateIntention(Intention intention);

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月15日 <br>
	 * 描述： 根据客户编号删除预约表
	 * 
	 * @param loanid
	 * @return
	 */
	Json deleteIntention(String ordplyId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月10日 <br>
	 * 描述： 分页查询数据
	 * 
	 * @param page
	 *            当前页码
	 * @param rows
	 *            每页显示多少数据
	 * @param loanid
	 *            职位名称 模糊
	 * @return
	 */
	DataGrid<Intention> findPageData(Integer page, Integer rows, String loanid);

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月21日 <br>
	 * 描述： 预约信息的分页
	 * 
	 * @param page
	 * @param rows
	 * @param roleName
	 * @return
	 */
	Pager<Intention> findPage(Integer page, Integer rows, String custname,SessionRouter sessionRouter,String mobile);

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月20日 <br>
	 * 描述： 根据主键查询预约信息
	 * 
	 * @param ordplyId
	 * @return
	 */
	Intention findIntentionById(String ordplyId);

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月20日 <br>
	 * 描述： 根据主键查询预约信息
	 * 
	 * @param ordplyId
	 * @return json数据
	 */
	JSONObject findById(String ordplyId);
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月5日 <br>
	 * 描述： 提供申请表没有的预约客户
	 * @param ordplyId
	 * @return
	 */
	Intention findByOrdplyId(String ordplyId);
	
	
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月20日 <br>
	 * 描述： 检查手机号是否存在
	 * @param phone
	 * @return
	 */
	Json checkPhone(String phone);
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月20日 <br>
	 * 描述： 检查身份证是否存在
	 * @param idnumber
	 * @return
	 */
	Json checkIdNumber(String idnumber);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月21日 <br>
	 * 描述： 咨询列表的选取
	 * @param page
	 * @param rows
	 * @param custname
	 * @param deptId
	 * @param mobile
	 * @return
	 */
	Pager<Intention> findPageByStore(Integer page, Integer rows, String custname, String deptId, String mobile);
}
