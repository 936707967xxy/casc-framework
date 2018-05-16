/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.server.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.csas.sales.api.exception.AuditException;
import com.hoomsun.csas.sales.api.model.Annex;
import com.hoomsun.csas.sales.api.server.inter.AnnexServerI;
import com.hoomsun.csas.sales.dao.AnnexMapper;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月7日 <br>
 * 描述：
 */
@Service("annexServer")
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
	public List<Annex> findByApplyIdImgType(String applyId, Integer imgType) {
		if (StringUtils.isBlank(applyId)) {
			throw new AuditException("参数异常!applyId is null1");
		}
		return annexMapper.findByApplyIdImgType(applyId, imgType);
	}


	@Override
	public List<Map<String, Object>> queryReView(String applyId, Integer imgType) {
		List<Map<String, Object>> list = annexMapper.queryReView(applyId,imgType);
		List<Map<String,Object>> newList = new ArrayList<Map<String,Object>>();
		//将原来list转换为回显参数
		for (Map<String, Object> oldMap : list) {
			Map<String, Object> newMap = new HashMap<String, Object>();
			newMap.put("name",oldMap.get("FILE_NAME"));
			newMap.put("url",oldMap.get("PRE_VIEW"));
			newMap.put("anxId",oldMap.get("ANX_ID"));
			Map<String,Object> new2Map = new HashMap<String, Object>();
			Map<String,Object> obj = new HashMap<String, Object>();
		    obj.put("preView",oldMap.get("PRE_VIEW"));
		    obj.put("anxId", oldMap.get("ANX_ID"));
			new2Map.put("data", obj);
			new2Map.put("msg", "上传成功");
			new2Map.put("success", true);
			newMap.put("response",new2Map);
			newList.add(newMap);
		}
		return newList;
	}


	@Override
	public Json deleteByanxId(String anxId) {
		int reCount = annexMapper.deleteByPrimaryKey(anxId);
		if(reCount>0){
			return new Json(true,"删除成功");
		}
		return new Json(false,"删除失败");
	}

}
