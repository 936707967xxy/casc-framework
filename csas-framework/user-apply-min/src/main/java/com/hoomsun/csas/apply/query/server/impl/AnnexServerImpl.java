/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.apply.query.server.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.csas.apply.query.dao.AnnexMapper;
import com.hoomsun.csas.apply.query.exception.MinQueryException;
import com.hoomsun.csas.apply.query.model.Annex;
import com.hoomsun.csas.apply.query.server.inter.AnnexServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月5日 <br>
 * 描述：附件
 */
@Service("annexDataServer")
public class AnnexServerImpl implements AnnexServerI {
	private AnnexMapper annexMapper;

	@Autowired
	public void setAnnexMapper(AnnexMapper annexMapper) {
		this.annexMapper = annexMapper;
	}

	@Override
	public Annex selectByPrimaryKey(String anxId) {
		return annexMapper.selectByPrimaryKey(anxId);
	}

	@Override
	public List<Annex> findByApplyIdImgType(String applyId, String imgType) {
		if (StringUtils.isBlank(applyId)) {
			throw new MinQueryException("参数异常!applyId is null1");
		}
		if (StringUtils.isBlank(imgType)) {
			throw new MinQueryException("参数异常!imgType is null1");
		}
		return annexMapper.findByApplyIdImgType(applyId, imgType);
	}

}
