/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.csas.audit.dao.BPMNMapper;
import com.hoomsun.csas.audit.exception.AuditException;
import com.hoomsun.csas.audit.model.vo.HistoricTaskVo;
import com.hoomsun.csas.audit.server.inter.ActivitiServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月28日 <br>
 * 描述：
 */
@Service("activitiServer")
public class ActivitiServerImpl implements ActivitiServerI {

	@Autowired
	private BPMNMapper bpmnMapper;

	@Override
	public List<HistoricTaskVo> findAuditHistoric(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			throw new AuditException("参数异常!");
		}
		return bpmnMapper.findAuditHistoric(applyId);
	}

}
