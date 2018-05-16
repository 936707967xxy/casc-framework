/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.settle.server.inter;

import java.util.List;
import java.util.Map;

import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.settle.model.vo.DerateReportVo;

/**
 * 作者：liming <br>
 * 创建时间：2018年1月26日 <br>
 * 描述：减免报表
 */
public interface DerateReportVoServerI {

	
	
	Pager<DerateReportVo> findAllData(Integer page, Integer rows,String castName,String cardNo,String conNo,String startDate,String endDate);
	
	
	List<DerateReportVo>  findDerateList(Map<String, Object> param);

}
