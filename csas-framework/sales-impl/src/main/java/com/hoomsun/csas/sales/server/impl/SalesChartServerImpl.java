/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.server.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.core.dao.SysEmployeeMapper;
import com.hoomsun.csas.sales.api.model.vo.ParentDeptInfoVo;
import com.hoomsun.csas.sales.api.model.vo.SalesmanReportVo;
import com.hoomsun.csas.sales.api.server.inter.SalesChartServerI;
import com.hoomsun.csas.sales.dao.SalesmanReportVoMapper;

/**
 * 作者：liushuai <br>
 * 创建时间：2017年12月21日 <br>
 * 描述： 个贷统计报表（这里少计算了拒贷的额度）
 */
@Service("chartServer")
public class SalesChartServerImpl implements SalesChartServerI {

	private SalesmanReportVoMapper salesmanReportVoMapper;

	@Value("${HSOADB_NAME}")
	private String hsoaDB;

	@Autowired
	public void setSalesmanReportVoMapper(SalesmanReportVoMapper salesmanReportVoMapper) {
		this.salesmanReportVoMapper = salesmanReportVoMapper;
	}

	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;

	private List<ParentDeptInfoVo> findallString(String empId){
		List<ParentDeptInfoVo> deptList = new ArrayList<ParentDeptInfoVo>();
		String deptsStr = salesmanReportVoMapper.selectDeptByManager(empId);
		if(deptsStr == null) {
			return deptList;
		}
		
		try {
			String[] depts = deptsStr.split(",");
			for (String deptId : depts) {
				deptList.add(salesmanReportVoMapper.selectDeptInfo(deptId)); // 授权部门
			}
		}catch(Exception e){
				
		}
		// 暂时去掉
		// deptList.add(salesmanReportVoMapper.selectDeptInfo(deptMap.get("DEPTID"))); // 当前部门（比如：门店经理还可以看门店业绩【不止是团队业绩】）
		return deptList;
	}
	
	// 根据等级筛选部门
	private List<ParentDeptInfoVo> findAllDeptId(String empId, int level) {
		if (StringUtils.isBlank(empId)) {
			return null;
		}
		List<ParentDeptInfoVo> list = findallString(empId);
		List<ParentDeptInfoVo> levelList = new ArrayList<ParentDeptInfoVo>();
		for (ParentDeptInfoVo deptInfo : list) {
			int depLevel = salesmanReportVoMapper.selectDeptLevel(deptInfo.getId());
			if (depLevel == level) {
				levelList.add(deptInfo);
			}
		}
		return levelList;
	}

	// 事业部, 业绩试讲所有关联部门一个个查询业绩
	@Override
	public Pager<SalesmanReportVo> findBigAreaChartInfo(String empId, String deptId, String startDate, String endDate) {
		if (StringUtils.isBlank(empId) || StringUtils.isBlank(deptId)) {
			return null;
		}
		if (StringUtils.isBlank(startDate)){
			startDate = DateUtil.formart(DateUtil.ymd);
		}
		
		if (StringUtils.isBlank(endDate)){
			endDate = DateUtil.formart(DateUtil.ymd);
		}
		
		List<SalesmanReportVo> li = new ArrayList<SalesmanReportVo>();
		List<ParentDeptInfoVo> depts = findAllDeptId(empId, 2); // 获取所有管理的部门（并且是为3的）
		depts.add(salesmanReportVoMapper.selectDeptInfo(deptId));
		for (ParentDeptInfoVo deptInfo : depts) { // 遍历部门
			SalesmanReportVo chartInfo = salesmanReportVoMapper.selectByBigArea(deptInfo.getId(), startDate, endDate); // 每个子部门获取到的统计信息
			chartInfo.setBigAreaId(deptInfo.getId()); // 存储当前部门ID
			chartInfo.setBitAreaName(deptInfo.getDeptName()); // 存储当前部门名称
			li.add(chartInfo);
		}
		return new Pager<SalesmanReportVo>(li, li.size());
	}

	// 大区
	@Override
	public Pager<SalesmanReportVo> findAreaChartInfo(String empId, String deptId, String startDate, String endDate) {
		if (StringUtils.isBlank(empId) || StringUtils.isBlank(deptId)) {
			return null;
		}
		if (StringUtils.isBlank(startDate)){
			startDate = DateUtil.formart(DateUtil.ymd);
		}
		
		if (StringUtils.isBlank(endDate)){
			endDate = DateUtil.formart(DateUtil.ymd);
		}
		
		List<SalesmanReportVo> li = new ArrayList<SalesmanReportVo>();
		// 查询自己部门下的大区
		List<SalesmanReportVo> chartInfo = salesmanReportVoMapper.selectByArea(deptId, startDate, endDate);// 当前部门
		ParentDeptInfoVo parDept = salesmanReportVoMapper.selectParentDeptInfo(deptId);
		for (SalesmanReportVo salesmanReportVo : chartInfo) {
			salesmanReportVo.setBigAreaId(parDept.getId());// 存储上级部门ID
			salesmanReportVo.setBitAreaName(parDept.getDeptName());// 存储上级部门名称
		}
		li.addAll(chartInfo);
		
		// 查询员工作用域的大区
		List<ParentDeptInfoVo> depts = findAllDeptId(empId, 3); // 获取所有管理的部门（并且是为3的）
		for (ParentDeptInfoVo deptInfo : depts) { // 遍历部门
			SalesmanReportVo saleInfo = salesmanReportVoMapper.selectBySingDept(deptInfo.getId(), startDate, endDate); // 每个管理部门获取到的统计信息
			ParentDeptInfoVo parDeptTmp = salesmanReportVoMapper.selectParentDeptInfo(deptInfo.getId()); // 获取管理部门的上级部门信息
			saleInfo.setBigAreaId(parDeptTmp.getId()); // 存储上级部门ID
			saleInfo.setBitAreaName(parDeptTmp.getDeptName()); // 存储上级部门名称
			saleInfo.setAreaId(deptInfo.getId()); // 存储管理里部门ID
			saleInfo.setAreaName(deptInfo.getDeptName()); //存储管理部门名称
			li.add(saleInfo);
		}
		return new Pager<SalesmanReportVo>(li, li.size());
	}
	
	// 分部经理--查询城市
	@Override
	public Pager<SalesmanReportVo> findcity(String empId, String deptId, String startDate, String endDate) {
		if (StringUtils.isBlank(empId) || StringUtils.isBlank(deptId)) {
			return null;
		}
		if (StringUtils.isBlank(startDate)){
			startDate = DateUtil.formart(DateUtil.ymd);
		}
		
		if (StringUtils.isBlank(endDate)){
			endDate = DateUtil.formart(DateUtil.ymd);
		}
		
		List<SalesmanReportVo> li = new ArrayList<SalesmanReportVo>();
		// 查询自己部门下的大区
		List<SalesmanReportVo> chartInfo = salesmanReportVoMapper.selectByStore(deptId, startDate, endDate);// 当前部门
		ParentDeptInfoVo parDept = salesmanReportVoMapper.selectParentDeptInfo(deptId);
		for (SalesmanReportVo salesmanReportVo : chartInfo) {
			salesmanReportVo.setAreaId(parDept.getId());// 存储上级部门ID
			salesmanReportVo.setAreaName(parDept.getDeptName());// 存储上级部门名称
		}
		li.addAll(chartInfo);
		
		// 查询员工作用域的大区
		List<ParentDeptInfoVo> depts = findAllDeptId(empId, 4); // 获取所有管理的部门（并且是为3的）
		for (ParentDeptInfoVo deptInfo : depts) { // 遍历部门
			SalesmanReportVo saleInfo = salesmanReportVoMapper.selectBySingDept(deptInfo.getId(), startDate, endDate); // 每个管理部门获取到的统计信息
			ParentDeptInfoVo parDeptTmp = salesmanReportVoMapper.selectParentDeptInfo(deptInfo.getId()); // 获取管理部门的上级部门信息
			saleInfo.setBigAreaId(parDeptTmp.getId()); // 存储上级部门ID
			saleInfo.setBitAreaName(parDeptTmp.getDeptName()); // 存储上级部门名称
			saleInfo.setAreaId(deptInfo.getId()); // 存储管理里部门ID
			saleInfo.setAreaName(deptInfo.getDeptName()); //存储管理部门名称
			li.add(saleInfo);
		}
		return new Pager<SalesmanReportVo>(li, li.size());
	}
	

	// 门店业绩
	@Override
	public Pager<SalesmanReportVo> findStore(String empId, String deptId, String startDate, String endDate) {
		if (StringUtils.isBlank(empId) || StringUtils.isBlank(deptId)) {
			return null;
		}
		if (StringUtils.isBlank(startDate)){
			startDate = DateUtil.formart(DateUtil.ymd);
		}
		
		if (StringUtils.isBlank(endDate)){
			endDate = DateUtil.formart(DateUtil.ymd);
		}
		
		List<SalesmanReportVo> li = new ArrayList<SalesmanReportVo>();
		List<SalesmanReportVo> chartInfo = salesmanReportVoMapper.selectByStore(deptId, startDate, endDate);
		li.addAll(chartInfo);
		
		// 查询员工作用域的门店
		List<ParentDeptInfoVo> depts = findAllDeptId(empId, 5); // 获取所有管理的部门（并且是为3的）
		for (ParentDeptInfoVo deptInfo : depts) { // 遍历部门
			List<SalesmanReportVo> chartInfo2 = salesmanReportVoMapper.selectByStore(deptInfo.getId(), startDate, endDate);
			li.addAll(chartInfo2);
		}
		return new Pager<SalesmanReportVo>(li, li.size());
	}

	

	// 团队业绩
	@Override
	public Pager<SalesmanReportVo> findTeam(String empId, String deptId, String startDate, String endDate) {
		
		if (StringUtils.isBlank(empId) || StringUtils.isBlank(deptId)) {
			return null;
		}
		if (StringUtils.isBlank(startDate)){
			startDate = DateUtil.formart(DateUtil.ymd);
		}
		
		if (StringUtils.isBlank(endDate)){
			endDate = DateUtil.formart(DateUtil.ymd);
		}
		
		List<SalesmanReportVo> li = new ArrayList<SalesmanReportVo>();
			// 查询部门下的所有员工
		List<SalesmanReportVo> chartInfo = salesmanReportVoMapper.selectByTeam(deptId, startDate, endDate);
		li.addAll(chartInfo);
		// 查询员工作用域的门店
		List<ParentDeptInfoVo> depts = findAllDeptId(empId, 6); // 获取所有管理的部门（并且是为3的）
		for (ParentDeptInfoVo deptInfo : depts) { // 遍历部门
			List<SalesmanReportVo> chartInfo2 = salesmanReportVoMapper.selectByTeam(deptInfo.getId(), startDate, endDate);
			li.addAll(chartInfo2);
		}
		return new Pager<SalesmanReportVo>(li, li.size());
	}

	// 查询个人业绩
	@Override
	public Pager<SalesmanReportVo> findPerson(String empId, String deptId, String startDate, String endDate) {
		if (StringUtils.isBlank(empId) || StringUtils.isBlank(deptId)) {
			return null;
		}
		List<SalesmanReportVo> li = new ArrayList<SalesmanReportVo>();
		String mgrId = sysEmployeeMapper.findDeptManager(deptId);
		Integer isMgr = 0;//
		if (empId.equals(mgrId)) {// 是部门的负责人
			isMgr = 1;
		}
		if (StringUtils.isBlank(startDate)){
			startDate = DateUtil.formart(DateUtil.ymd);
		}
		
		if (StringUtils.isBlank(endDate)){
			endDate = DateUtil.formart(DateUtil.ymd);
		}
		
		// 查询个人
		List<SalesmanReportVo> chartInfo = salesmanReportVoMapper.selectByPerson(empId, deptId, isMgr,  startDate, endDate);
		li.addAll(chartInfo);
		
		// 查询员工作用域的门店
		List<ParentDeptInfoVo> depts = findAllDeptId(empId, 6); // 获取所有管理的部门（并且是为3的）
		for (ParentDeptInfoVo deptInfo : depts) { // 遍历部门
			List<SalesmanReportVo> chartInfo2 = salesmanReportVoMapper.selectByPerson(empId, deptInfo.getId(), 1, startDate, endDate);
			li.addAll(chartInfo2);
		}
		return new Pager<SalesmanReportVo>(li, li.size());
	}

}
