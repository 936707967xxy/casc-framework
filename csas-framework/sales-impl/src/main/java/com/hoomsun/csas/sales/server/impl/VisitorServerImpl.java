/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.server.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.csas.sales.api.model.Visitor;
import com.hoomsun.csas.sales.api.server.inter.VisitorServerI;
import com.hoomsun.csas.sales.dao.VisitorMapper;

/**
 * 作者：liming <br>
 * 创建时间：2017年12月8日 <br>
 * 描述：回访记录业务层
 */
@Service("visitorServer")
public class VisitorServerImpl implements VisitorServerI{
	
	@Autowired
	private VisitorMapper visitorMapper;
	
	@Override
	public Json addVisitor(Visitor visitor) {
		if(StringUtils.isBlank(visitor.getVisId())){
			visitor.setVisId(PrimaryKeyUtil.getPrimaryKey());//主键
			Timestamp ts = new Timestamp(System.currentTimeMillis());  
			visitor.setVisTime(ts);
		}
		
		int result=visitorMapper.insertSelective(visitor);
		if(result>0){
			return new Json(true,"添加回访成功");
		}else{
			return new Json(false,"添加回访失败");
		}
		
	}

	@Override
	public Json updateVisitor(Visitor visitor) {
		int result=visitorMapper.updateByPrimaryKeySelective(visitor);
		if(result>0){
			return new Json(true,"修改回访成功");
		}else{
			return new Json(false,"修改回访失败");
		}
	}

	@Override
	public Json deleteVisitor(String visId) {
		int result=visitorMapper.deleteByPrimaryKey(visId);
		if(result>0){
			return new Json(true,"删除回访成功");
		}else{
			return new Json(false,"删除回访失败");
		}
	}

	@Override
	public Pager<Visitor> findPage(Integer page, Integer rows, String visTime,String visFkid) {
		Map<String, Object> param = new HashMap<String, Object>();

		if (null != page && null != rows) {
			rows = rows > 200 ? 200 : rows;
			param.put("page", page);
			param.put("rows", rows);
		} else {
			page = 1; // 关于这里是否需要给出默认值，与前端框架有关系
			rows = 10;
		}

		if (!StringUtils.isBlank(visTime)) {
			param.put("visTime", "%" + visTime + "%");
		}
		if (!StringUtils.isBlank(visFkid)) {
			param.put("visFkid",visFkid);
		};
		List<Visitor> visitor = visitorMapper.findPageData(param);
		
		Integer total = visitorMapper.findPageCount(param);
		return new Pager<Visitor>(visitor, total);
		
	}

	@Override
	public Visitor findVisitorById(String visId) {
		
		return visitorMapper.selectByPrimaryKey(visId);
	}
}
