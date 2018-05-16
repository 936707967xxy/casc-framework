/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.audit.model.vo.UserApplyVO;
import com.hoomsun.csas.audit.server.inter.TracingFlowServerI;
import com.hoomsun.csas.sales.api.model.vo.TrackVO;
import com.hoomsun.csas.sales.api.server.inter.TrackServerI;

/**
 * 作者：liushuai <br>
 * 创建时间：2018年1月23日 <br>
 * 描述：
 */
@Controller
public class TracingFlowController {
	@Autowired
	private TracingFlowServerI tracingFlowServer;
	@Autowired
	private TrackServerI trackServer;
	
	@RequestMapping("/sys/track/pager.do")
	@ResponseBody
	public Pager<UserApplyVO> findPager(Integer page, Integer rows, String custName, String idNumber, String loanId, String storeId){
		return tracingFlowServer.findPager(page, rows, custName, idNumber, loanId, storeId);
	}
	
	@RequestMapping(value = "/sys/track/simplquery.do")
	@ResponseBody
	public List<TrackVO> trackSimplByApplyId(String applyId,HttpServletRequest request){
		return trackServer.trackSimplByApplyId(applyId);
	}
	
}
