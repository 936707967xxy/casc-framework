/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.apply.query.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.csas.apply.query.model.Annex;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月5日 <br>
 * 描述：
 */
public interface AnnexMapper {
	Annex selectByPrimaryKey(String anxId);
	
	List<Annex> findByApplyIdImgType(@Param("applyId")String applyId,@Param("imgType")String imgType);
}
