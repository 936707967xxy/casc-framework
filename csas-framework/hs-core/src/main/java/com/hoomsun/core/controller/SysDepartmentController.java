/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.model.TreeNode;
import com.hoomsun.core.anno.LoggerAnnotation;
import com.hoomsun.core.anno.Permission;
import com.hoomsun.core.enums.OptionType;
import com.hoomsun.core.model.SysDepartment;
import com.hoomsun.core.model.vo.VueLazyTree;
import com.hoomsun.core.server.inter.SysDepartmentServerI;
import com.hoomsun.core.util.LoginUtil;

/**
 * 作者：liusong <br>
 * 创建时间：2017年9月11日 <br>
 * 描述：系统部门管理的控制层
 */
@Controller
public class SysDepartmentController {
	private SysDepartmentServerI departmentServerI;

	@Autowired
	public void setDepartmentServerI(SysDepartmentServerI departmentServerI) {
		this.departmentServerI = departmentServerI;
	}

	// 跳转到部门列表页面
	@Permission("dept_query")
	@RequestMapping(value = "/sys/dept/listview.do", method = RequestMethod.GET)
	public String deptView() {
		return "dept/listview";
	}

	@Permission("dept_query")
	@RequestMapping(value = "/sys/dept/page.do", method = { RequestMethod.POST })
	@ResponseBody
	public Pager<SysDepartment> findDataGrid(Integer page, Integer rows, String comId, String deptName) {
		return departmentServerI.findPageData(page, rows, comId, deptName);
	}

	@Permission("dept_add")
	@RequestMapping("/sys/dept/addview.do")
	public String addView() {
		return "dept/addview";
	}

	@Permission("dept_edit")
	@RequestMapping("/sys/dept/query.do")
	@ResponseBody
	public SysDepartment findById(String deptId, HttpServletRequest request) {
		SysDepartment department = departmentServerI.findById(deptId);
		return department;
	}

	/**
	 * 作者：liusong <br>
	 * 创建时间：2017年9月11日 <br>
	 * 描述： 部门添加方法
	 * 
	 * @param department
	 *            部门对象
	 * @return
	 */
	@LoggerAnnotation(moduleName = "部门管理", option = "添加部门", optionType = OptionType.CREATE)
	@Permission("dept_add")
	@RequestMapping(value = "/sys/dept/create.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json createDept(SysDepartment department, HttpServletRequest request) {
		return departmentServerI.createDept(department);
	}

	/**
	 * 作者：liusong <br>
	 * 创建时间：2017年9月11日 <br>
	 * 描述： 部门修改方法
	 * 
	 * @param department
	 *            部门对象
	 * @return
	 */
	@LoggerAnnotation(moduleName = "部门管理", option = "修改部门", selectMethod = "findById", idName = "deptId", idIndex = 0, beanId = "deptServer", optionType = OptionType.UPDATE)
	@Permission("dept_edit")
	@RequestMapping(value = "/sys/dept/update.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json updateDept(SysDepartment department, HttpServletRequest request) {
		return departmentServerI.updateDept(department);
	}

	/**
	 * 作者：liusong <br>
	 * 创建时间：2017年9月11日 <br>
	 * 描述： 根据deptId删除对应部门信息
	 * 
	 * @param deptId
	 *            部门id
	 * @return
	 */
	@LoggerAnnotation(moduleName = "部门管理", option = "删除部门", selectMethod = "findById", idName = "deptId", idIndex = 0, beanId = "deptServer", optionType = OptionType.DELETE)
	@Permission("dept_delete")
	@RequestMapping("/sys/dept/remove.do")
	@ResponseBody
	public Json deleteDept(String deptId, HttpServletRequest request) {
		return departmentServerI.deleteDept(deptId);
	}
	
	@LoggerAnnotation(moduleName = "部门管理", option = "删除多个部门", selectMethod = "findById", idName = "deptId", idIndex = 0, beanId = "deptServer", optionType = OptionType.DELETE)
	@Permission("dept_delete")
	@RequestMapping("/sys/dept/removemulti.do")
	@ResponseBody
	public Json deleteMultiDept(@RequestBody List<String> deptIds, HttpServletRequest request) {
		return departmentServerI.deleteMultiRole(deptIds);
	}

	@RequestMapping(value = "/sys/dept/treenode.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public List<TreeNode> findTreeNode(String comId, HttpServletRequest request) {
		if (StringUtils.isAllBlank(comId)) {
			comId = LoginUtil.getLoginSession(request).getComId();
		}
		return departmentServerI.findTreeNode(comId);
	}
	
	
	@RequestMapping(value = "/sys/dept/vuetreedata.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public  List<VueLazyTree> vueTreeData(String parentId) {
		return departmentServerI.findVueTreeData(parentId);
	}
	
	// 查询所有营业部下拉列表
	@RequestMapping(value = "/sys/dept/storeslist.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public   List<SysDepartment> findStoresList() {
		return departmentServerI.findAllStoreShow();
	}
}
