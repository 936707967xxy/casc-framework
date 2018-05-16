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

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.model.TreeNode;
import com.hoomsun.core.dao.SysDepartmentMapper;
import com.hoomsun.core.model.SysDepartment;
import com.hoomsun.core.model.vo.VueLazyTree;
import com.hoomsun.core.server.inter.SysDepartmentServerI;
import com.hoomsun.core.util.PrimaryKeyUtil;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月10日 <br>
 * 描述：部门管理的业务实现
 */
@Service("deptServer")
public class SysDepartmentServerImpl implements SysDepartmentServerI {
	private SysDepartmentMapper departmentMapper;

	@Autowired
	public void setDepartmentMapper(SysDepartmentMapper departmentMapper) {
		this.departmentMapper = departmentMapper;
	}

	@Override
	public Json createDept(SysDepartment department) {
		if (StringUtils.isBlank(department.getDeptId())) {
			department.setDeptId(PrimaryKeyUtil.getPrimaryKey());
		}

		String parentId = department.getDeptParent();
		if (StringUtils.isBlank(parentId) || "-1".equals(parentId)) {
			department.setDeptParent(null);
		}
		department.setDeptStatus(1);
		int result = departmentMapper.insertSelective(department);
		if (result > 0) {
			return new Json(true, "部门添加成功!");
		} else {
			return new Json(false, "部门添加失败!");
		}
	}

	@Override
	public Json updateDept(SysDepartment department) {
		String parentId = department.getDeptParent();
		if(StringUtils.isBlank(parentId)){
			return new Json(false, "部门修改失败，修改参数有误！");
		}
		if (StringUtils.isBlank(parentId) || "-1".equals(parentId)) {
			department.setDeptParent(null);
		}
		int result = departmentMapper.updateByPrimaryKeySelective(department);
		if (result > 0) {
			return new Json(true, "部门修改成功!");
		} else {
			return new Json(false, "部门修改失败!");
		}
	}

	@Override
	public Json deleteDept(String deptId) {
		if(StringUtils.isBlank(deptId)){
			return new Json(false, "部门删除失败，删除参数有误！");
		}
		int result = departmentMapper.deleteByPrimaryKey(deptId);
		if (result > 0) {
			return new Json(true, "部门删除成功!");
		} else {
			return new Json(false, "部门删除失败!");
		}
	}

	@Override
	public SysDepartment findById(String deptId) {
		if(StringUtils.isBlank(deptId)){
			return null;
		}
		return departmentMapper.selectByDeptId(deptId);
	}

	@Override
	public Pager<SysDepartment> findPageData(Integer page, Integer rows, String comId, String deptName) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (null != page && rows != null) { 
			rows = rows > 200 ? 200 : rows;
		}else {
			page = 1; // 关于这里是否需要给出默认值，与前端框架有关系
			rows = 10;
		}
		param.put("pageIndex", page);
		param.put("pageSize", rows);
		
//		log.error("pageIndex" + page + "pageSize" + rows);
//		log.error("deptName" + deptName + "comId" + comId);
		if (!StringUtils.isBlank(deptName)) {
			param.put("deptName", "%" + deptName + "%");
		}
		if (!StringUtils.isBlank(comId)) {
			param.put("comId", comId);
		}

		List<SysDepartment> departments = departmentMapper.findPageData(param);
		int total = departmentMapper.findPageCount(param);
		return new Pager<SysDepartment>(departments, total);
	}

	@Override
	public List<SysDepartment> findAllData() {
		return departmentMapper.findAllData();
	}

	@Override
	public List<TreeNode> findTreeNode(String comId) {
		List<SysDepartment> depts = departmentMapper.findByComId(comId);
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		nodes.add(new TreeNode("-1", "所有部门", null));
		for (SysDepartment dept : depts) {
			nodes.add(new TreeNode(dept.getDeptId(), dept.getDeptName(), "-1"));
		}
		return nodes;
		
//		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
//		comboTrees.add(new ComboTree("-1", "", "请选择"));
//		if (!StringUtils.isBlank(comId)) {
//			List<SysDepartment> resources = departmentMapper.findByComId(comId);
//			if (resources != null && resources.size() > 0) {
//				for (SysDepartment res : resources) {
//					String parentId = res.getDeptParent();
//					ComboTree ct;
//					if (StringUtils.isBlank(parentId)) {
//						ct = new ComboTree(res.getDeptId(), res.getDeptName());
//					} else {
//						ct = new ComboTree(res.getDeptId(), parentId, res.getDeptName());
//					}
//					comboTrees.add(ct);
//				}
//			}
//		}
//		return comboTrees;
	}

	@Override
	public Json deleteMultiRole(List<String> deptIds) {
		int result = departmentMapper.deleteByMultiDepts(deptIds);
		if (result > 0) {
			return new Json(true, "删除多个部门成功!");
		} else {
			return new Json(false, "删除多个部门失败!");
		}
	}

	@Override
	public List<VueLazyTree> findVueTreeData(String parentId) {
		return departmentMapper.findVueTreeData(parentId);
	}
	
	/**
	 * 
	 * 作者：liudongliang <br>
	 * 创建时间：2018年1月15日 <br>
	 * 描述：查询门店编号
	 * @param parentId
	 * @return
	 */
	@Override
	public SysDepartment selectByPrimaryKey(String deptId){
		return departmentMapper.selectByPrimaryKey( deptId);
	}

	@Override
	public List<SysDepartment> findAllStoreShow() {
		return departmentMapper.selectAllStoreInfoShow();
	}

}
