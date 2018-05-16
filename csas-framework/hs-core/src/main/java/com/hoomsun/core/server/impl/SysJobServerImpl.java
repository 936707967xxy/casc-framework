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
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.dao.SysJobMapper;
import com.hoomsun.core.model.SysJob;
import com.hoomsun.core.model.vo.VueLazyTree;
import com.hoomsun.core.server.inter.SysJobServerI;
import com.hoomsun.core.util.PrimaryKeyUtil;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月10日 <br>
 * 描述：职位管理的业务实现
 */
@Service("jobServer")
public class SysJobServerImpl implements SysJobServerI {
	
	private SysJobMapper jobMapper;
	@Autowired
	public void setJobMapper(SysJobMapper jobMapper) {
		this.jobMapper = jobMapper;
	}

	@Override
	public Json createJob(SysJob job) {
		if (StringUtils.isBlank(job.getJobId())) {
			job.setJobId(PrimaryKeyUtil.getPrimaryKey());
		}
		
		String parentId = job.getJobParent();
		if (StringUtils.isBlank(parentId) || "-1".equals(parentId)) {
			job.setJobParent(null);
		}
		
		int result = jobMapper.insertSelective(job);
		if(result > 0){
			return new Json(true, "职位添加成功!");
		}else{
			return new Json(false, "职位添加失败!");
		}
	}

	@Override
	public Json updateJob(SysJob job) {
		String parentId = job.getJobParent();
		if (StringUtils.isBlank(parentId) || "-1".equals(parentId)) {
			job.setJobParent(null);
		}
		int result = jobMapper.updateByPrimaryKeySelective(job);
		if(result > 0){
			return new Json(true, "职位修改成功!");
		}else{
			return new Json(false, "职位修改失败!");
		}
	}

	@Override
	public Json deleteJob(String jobId) {
		int result = jobMapper.deleteByPrimaryKey(jobId);
		if(result > 0){
			return new Json(true, "职位删除成功!");
		}else{
			return new Json(false, "职位删除失败!");
		}
	}

	@Override
	public SysJob findById(String jobId) {
		if (StringUtils.isBlank(jobId)) {
			return null;
		}
		return jobMapper.selectByJobId(jobId);
	}

	@Override
	public Pager<SysJob> findPageData(Integer page, Integer rows, String name) {
		if (null == page || rows == null) {
			return null;
		}
		rows = rows > 100 ? 100 : rows;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pageIndex", (page-1)*rows);
		param.put("pageSize", rows);
		
		if (!StringUtils.isBlank(name)) {
			param.put("jobName", "%"+name+"%");
		}
		
		List<SysJob> jobs = jobMapper.findPageData(param);
		Integer total = jobMapper.findPageCount(param);
		return new Pager<SysJob>(jobs, total);
	}

	@Override
	public List<SysJob> findAllData() {
		return jobMapper.findAll();
	}

	@Override
	public SysJob findByName(String jobName) {
		return jobMapper.findByName(jobName);
	}

	@Override
	public List<ComboTree> findComboTree() {
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		comboTrees.add(new ComboTree("-1", "请选择"));
		List<SysJob> jobs = findAllData();
		for (SysJob sysJob : jobs) {
			ComboTree ct = new ComboTree(sysJob.getJobId(), sysJob.getJobName());
			if (!StringUtils.isBlank(sysJob.getJobParent())) {
				ct.setParentId(sysJob.getJobParent());
			}
			comboTrees.add(ct);
		}
		return comboTrees;
	}

	@Override
	public List<VueLazyTree> findVueTreeData(String parentId) {
		return jobMapper.findVueTreeData(parentId);
	}

}
