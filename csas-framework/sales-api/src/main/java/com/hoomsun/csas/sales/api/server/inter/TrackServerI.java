/**
 * Copyright www.idawn.org 邹益伟 idawnorg@gmail.com
 */
package com.hoomsun.csas.sales.api.server.inter;

import java.util.List;

import com.hoomsun.csas.sales.api.model.vo.TrackVO;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月24日 <br>
 * 描述：简单的流程追踪
 */
public interface TrackServerI {

	List<TrackVO> trackByApplyId(String applyId);
	
	List<TrackVO> trackSimplByApplyId(String applyId);
}
