/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.ComboTree;
import com.hoomsun.common.model.DataGrid;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.dao.SysCompanyMapper;
import com.hoomsun.core.dao.SysDepartmentMapper;
import com.hoomsun.core.model.SysCompany;
import com.hoomsun.core.model.vo.VueLazyTree;
import com.hoomsun.core.server.inter.SysCompanyServerI;
import com.hoomsun.core.util.PrimaryKeyUtil;

/**
 * 作者：liushuai <br>
 * 创建时间：2017年9月11日 <br>
 * 描述：公司管理的业务实现
 */
@Service("comServer")
public class SysCompanyServerImp implements SysCompanyServerI {
	private SysCompanyMapper companyMapper;
	
	@Autowired
	private SysDepartmentMapper departmentMapper;
	
	@Autowired
	public void setCompanyMapper(SysCompanyMapper companyMapper) {
		this.companyMapper = companyMapper;
	}
	
	public SysCompanyServerImp() {
	}
	
	public Json createCom(SysCompany company) {
		if (StringUtils.isBlank(company.getComId())) {
			company.setComId(PrimaryKeyUtil.getPrimaryKey());
		}
		company.setComStatus(1);
		int result = companyMapper.insertSelective(company);
		if(result > 0){
			return new Json(true, "公司添加成功!");
		}else{
			return new Json(false, "公司添加失败!");
		}
	}

	@Override
	public Json updateCom(SysCompany company) {
		int result = companyMapper.updateByPrimaryKeySelective(company);
		if (result > 0) {
			return new Json(true, "公司更新成功!");
		} else {
			return new Json(true, "公司更新失败!");
		}
	}

	@Override
	public Json deleteCom(String comId) {
		int result = companyMapper.deleteByPrimaryKey(comId);
		if (result > 0) {
			return new Json(true, "公司删除成功!");
		} else {
			return new Json(true, "公司删除失败!");
		}
	}

	@Override
	public SysCompany findById(String comId) {
		
		return companyMapper.selectByComId(comId);
	}

	@Override
	public DataGrid<SysCompany> findPageData(Integer page, Integer rows, String comName) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (page == null || rows == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		rows = rows > 50 ? 50 : rows;
		
		param.put("pageIndex", (page - 1) * rows);
		param.put("pageSize", rows);

		if (!StringUtils.isBlank(comName)) {
			param.put("comName", "%"+comName+"%");
		}

		List<SysCompany> companys = companyMapper.findPageData(param);
		int total = companyMapper.findPageCount(param);
		return new DataGrid<SysCompany>(total, companys);
	}
	
	
	@Override
	public Pager<SysCompany> findPage(Integer page, Integer rows, String comName) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (page == null || rows == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		rows = rows > 50 ? 50 : rows;
		
		param.put("pageIndex", page);
		param.put("pageSize", rows);

		if (!StringUtils.isBlank(comName)) {
			param.put("comName", "%"+comName+"%");
		}

		List<SysCompany> companys = companyMapper.findPageData(param);
		int total = companyMapper.findPageCount(param);
		return new Pager<SysCompany>( companys,total);
	}

	@Override
	public List<SysCompany> findAllData() {
		return companyMapper.findAllData();
	}
	
	@Override
	public List<ComboTree> findComboTree() {
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		comboTrees.add(new ComboTree("-1", "", "请选择"));
		List<SysCompany> companys = findAllData();
		if (companys != null && companys.size() > 0) {
			for (SysCompany com : companys) {
				String parentId = com.getComParent();
				ComboTree ct;
				if (StringUtils.isBlank(parentId)) {
					ct = new ComboTree(com.getComId(), com.getComName());
				} else {
					ct = new ComboTree(com.getComId(), parentId, com.getComName());
				}
				comboTrees.add(ct);
			}
		}
		return comboTrees;
	}

	public List<Map<String, Object>> treeData(){
		List<Map<String, Object>> comlist= companyMapper.findtreeData();
		for(Map<String, Object>  obj:  comlist){
			List<Map<String, Object>>  list=departmentMapper.findByCom(obj.get("VALUE")+"");
			obj.put("children", list);
		}
		return comlist;
		
	}

	@Override
	public List<VueLazyTree> findVueTreeData(String parentId) {
		return companyMapper.findVueTreeData(parentId);
	}
}
