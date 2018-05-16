/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.ComboTree;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.anno.LoggerAnnotation;
import com.hoomsun.core.enums.OptionType;
import com.hoomsun.core.model.SysJob;
import com.hoomsun.core.model.vo.VueLazyTree;
import com.hoomsun.core.server.inter.SysJobServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月12日 <br>
 * 描述：职位管理的控制层
 */
@Controller
public class SysJobController {

	private SysJobServerI jobServer;

	@Autowired
	public void setJobServer(SysJobServerI jobServer) {
		this.jobServer = jobServer;
	}

	//@Permission("job_query")
	@RequestMapping(value = "/sys/job/listview.do", method = RequestMethod.GET)
	public String listView() {
		return "/job/listview";
	}

	@RequestMapping(value = "/sys/job/page.do", method = RequestMethod.POST)
	@ResponseBody
	public Pager<SysJob> getPager(Integer page, Integer rows, String jobName) {
		return jobServer.findPageData(page, rows, jobName);
	}

	//@Permission("job_add")
	@RequestMapping("/sys/job/addview.do")
	public String addView() {
		return "job/addview";
	}

	@LoggerAnnotation(moduleName = "职位管理", option = "添加职位", optionType = OptionType.CREATE)
	//@Permission("job_add")
	@RequestMapping(value = "/sys/job/create.do", method = RequestMethod.POST)
	@ResponseBody
	public Json createJob(SysJob job, HttpServletRequest request) {
		return jobServer.createJob(job);
	}

	//@Permission("job_edit")
	@RequestMapping("/sys/job/query.do")
	@ResponseBody
	public  SysJob editView(String jobId, HttpServletRequest request) {
		return jobServer.findById(jobId);
	}

	@LoggerAnnotation(moduleName = "职位管理", option = "修改职位", selectMethod = "findById", idName = "jobId", idIndex = 0, beanId = "jobServer", optionType = OptionType.UPDATE)
	//@Permission("job_edit")
	@RequestMapping(value = "/sys/job/update.do", method = RequestMethod.POST)
	@ResponseBody
	public Json updateJob(SysJob job, HttpServletRequest request) {
		return jobServer.updateJob(job);
	}

	@LoggerAnnotation(moduleName = "职位管理", option = "删除职位", selectMethod = "findById", idName = "jobId", idIndex = 0, beanId = "jobServer", optionType = OptionType.DELETE)
	//@Permission("job_delete")
	@RequestMapping(value = "/sys/job/remove.do", method = RequestMethod.POST)
	@ResponseBody
	public Json deleteJob(String jobId, HttpServletRequest request) {
		return jobServer.deleteJob(jobId);
	}

	@RequestMapping(value = "/sys/job/combotree.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public List<ComboTree> findComboTree() {
		return jobServer.findComboTree();
	}
	
	@RequestMapping(value = "/sys/job/vuetreedata.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public  List<VueLazyTree> vueTreeData(String parentId) {
		return jobServer.findVueTreeData(parentId);
	}

}
