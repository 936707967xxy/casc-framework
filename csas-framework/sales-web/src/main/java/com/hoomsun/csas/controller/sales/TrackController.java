/**
 * Copyright www.idawn.org 邹益伟 idawnorg@gmail.com
 */
package com.hoomsun.csas.controller.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.csas.sales.api.model.vo.TrackVO;
import com.hoomsun.csas.sales.api.server.inter.TrackServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月24日 <br>
 * 描述：简单的流程追踪
 */
@Controller
public class TrackController {
	
	private TrackServerI trackServer;
	@Autowired
	public void setTrackServer(TrackServerI trackServer) {
		this.trackServer = trackServer;
	}

	@RequestMapping(value = "/sys/track/query.do")
	@ResponseBody
	public List<TrackVO> trackByApplyId(String applyId,HttpServletRequest request){
		return trackServer.trackByApplyId(applyId);
	}
	
	@RequestMapping(value = "/sys/track/simplquery.do")
	@ResponseBody
	public List<TrackVO> trackSimplByApplyId(String applyId,HttpServletRequest request){
		return trackServer.trackSimplByApplyId(applyId);
	}
}
