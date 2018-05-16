/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.server.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.model.DataGrid;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.dao.BlackInfoMapper;
import com.hoomsun.core.dao.SysEmployeeMapper;
import com.hoomsun.core.model.vo.OAStore;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.server.inter.SerialNumberServerI;
import com.hoomsun.core.util.PrimaryKeyUtil;
import com.hoomsun.csas.sales.api.exception.AuditException;
import com.hoomsun.csas.sales.api.model.Intention;
import com.hoomsun.csas.sales.api.server.inter.IntentionServerI;
import com.hoomsun.csas.sales.dao.IntentionMapper;
import com.hoomsun.csas.sales.util.IntentionDate;

/**
 * 作者：liming <br>
 * 创建时间：2017年11月15日 <br>
 * 描述：预约表的业务实现
 */
@Service("intentionServer")
public class IntentionServerImpl implements IntentionServerI {

	private IntentionMapper intentionMaper;

	@Autowired
	public void setIntentionMaper(IntentionMapper intentionMaper) {
		this.intentionMaper = intentionMaper;
	}

	@Autowired
	private SerialNumberServerI serialnumberServer;

	@Autowired
	private BlackInfoMapper blackinfomapper;

	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;// 员工表
	
	@Value("${HSOADB_NAME}")
	private String hsoaDB;

	@SuppressWarnings("unused")
	@Override
	public Json addIntention(Intention intention, SessionRouter sessionRouter) throws ParseException {
		String empId = sessionRouter.getEmpId();//登录人id
		String empName =sessionRouter.getEmpName();
		String deptId = sessionRouter.getDeptId();
		String deptNo=sessionRouter.getDeptNo();
		if (StringUtils.isBlank(empId) || StringUtils.isBlank(deptId)) {
			throw new AuditException("无法获取你的登录信息,请尝试重新登录!");
		}else{
			intention.setCreateEmployee(empId);
			intention.setCreateEmployeeVal(empName);
		}
		
		if (StringUtils.isBlank(deptId)) {
			throw new AuditException("参数异常,无法获取你的部门信息!");
		}
		Map<String,Object> map =new HashMap<String, Object>();

		if (StringUtils.isBlank(intention.getOrdplyId())) {
			Timestamp time = new Timestamp(System.currentTimeMillis()); 
			intention.setApplydate(time);
			intention.setOrdplyId(PrimaryKeyUtil.getPrimaryKey());
			intention.setApplyid(serialnumberServer.createNumber("3", deptNo));
			

		}
		//插入证件类型
		intention.setIdcerttype("0");
		intention.setIdcerttypeval("身份证");
		//插入门店
		OAStore store = sysEmployeeMapper.findStoreByDeptId(deptId);
		
		
			String storeId=sessionRouter.getStoreId();
			String setOrgname=sessionRouter.getStoreName();
			intention.setOrgid(storeId);// 插入门店id
			intention.setOrgname(setOrgname);//门店名称
	
		
		
		//查询申请日期
		String mobile=intention.getMobile();
		
		//日期计算类
		IntentionDate sad=new IntentionDate();
		int result = 0;
		
			//查询申请日期
			String applydate=intentionMaper.selectApplyDate(mobile);
			//判断封申请日期是否大于闭日期
			long day;
			if(applydate==null||applydate==""){
				day=0;
				//添加数据
				result= intentionMaper.insertSelective(intention);
			}else{
				day= sad.ApplyDate(applydate);
		
				long closeDate=7;//封闭日期
				
				if(day<=closeDate){
					return new Json(false, "申请日期小于封闭日期，7天以后添加咨询!");
				}else{
					//添加数据
					result= intentionMaper.insertSelective(intention);
				}
			}
		
			
		
		if (result > 0) {
			return new Json(true, "预约表数据添加成功!");
		} else {
			return new Json(false, "预约表数据添加失败!");
		}


	}

	@Override
	public Json updateIntention(Intention intention) {
		int result = intentionMaper.updateByPrimaryKeySelective(intention);
		if (result > 0) {
			return new Json(true, "预约表数据修改成功!");
		} else {
			return new Json(false, "预约表数据修改失败!");
		}

	}

	@Override
	public Json deleteIntention(String ordplyId) {
		int result = intentionMaper.deleteByPrimaryKey(ordplyId);
		if (result > 0) {
			return new Json(true, "预约表数据删除成功!");
		} else {
			return new Json(false, "预约表数据删除失败!");
		}
	}

	@Override
	public DataGrid<Intention> findPageData(Integer page, Integer rows, String loanid) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (null != page && null != rows) {
			rows = rows > 50 ? 50 : rows;
			param.put("pageIndex", (page - 1) * rows);
			param.put("pageSize", rows);
		}

		if (!StringUtils.isBlank(loanid)) {
			param.put("loanid", "%" + loanid + "%");
		}
		List<Intention> intention = intentionMaper.findPageData(param);
		Integer total = intentionMaper.findPageCount(param);
		return new DataGrid<Intention>(total, intention);
	}

	@Override
	public JSONObject findById(String ordplyId) {
		// TODO Auto-generated method stub
		return intentionMaper.findById(ordplyId);
	}

	@Override
	public Pager<Intention> findPage(Integer page, Integer rows, String custname, SessionRouter sessionRouter, String mobile) {
		String empId = sessionRouter.getEmpId();
		String storeId = sessionRouter.getStoreId();
		String deptId = sessionRouter.getDeptId();
		if (StringUtils.isBlank(empId) || StringUtils.isBlank(storeId) || StringUtils.isBlank(deptId)) {
			throw new AuditException("无法获取你的登录信息,请尝试重新登录!");
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		// OAStore store =
		// sysEmployeeOAMapper.findStoreByDeptId(deptId,this.hsoaDB );
		// if(store!=null){
		// param.put("orgid", store.getStoreId());//获取门店
		//
		// }
		//验证当前登录人是不是部门负责人
		String mgrId = sysEmployeeMapper.findDeptManager(deptId);
		Integer isMgr = 0;//1:部门负责人  0不是
		if (empId.equals(mgrId)) {//是部门的负责人
			isMgr = 1;
		}
		param.put("isMgr", isMgr); 
		param.put("empId", empId);
		

		
		if (null != page && null != rows) {
			rows = rows > 200 ? 200 : rows;
			param.put("page", page);
			param.put("rows", rows);
		} else {
			page = 1; // 关于这里是否需要给出默认值，与前端框架有关系
			rows = 10;
		}

		if (!StringUtils.isBlank(custname)) {
			param.put("custname", "%" + custname + "%");
		}
		if (!StringUtils.isBlank(mobile)) {
			param.put("mobile", "%" + mobile + "%");
		}
		List<Intention> intention = intentionMaper.findPageData(param);
		Integer total = intentionMaper.findPageCount(param);
		return new Pager<Intention>(intention, total);
	}

	@Override
	public Intention findIntentionById(String ordplyId) {
		// TODO Auto-generated method stub
		return intentionMaper.findIntentionById(ordplyId);
	}

	@Override
	public Intention findByOrdplyId(String ordplyId) {

		return intentionMaper.findByOrdplyId(ordplyId);
	}

	@Override
	public Json checkPhone(String phone) {

		int result = intentionMaper.checkPhone(phone);
		if (result > 0) {
			return new Json(true, "手机号已存在");
		} else {
			result = blackinfomapper.findByPhone(phone);
			if (result > 0) {
				return new Json(true, "黑名单客户");
			} else {
				return new Json(false, "正常客户");
			}
		}
	}

	@Override
	public Json checkIdNumber(String idnumber) {

		int result = intentionMaper.checkIdNumber(idnumber);
		if (result > 0) {

			return new Json(true, "身份证号已存在");
		} else {
			result = blackinfomapper.findByIdNumber(idnumber);
			if (result > 0) {
				return new Json(true, "黑名单客户");
			} else {
				return new Json(false, "正常客户");
			}
		}
	}

	@Override
	public Pager<Intention> findPageByStore(Integer page, Integer rows, String custname, String deptId, String mobile) {
		
		if (StringUtils.isBlank(deptId)) {
			throw new AuditException("参数异常,无法获取你的部门信息!");
		}
		//获取操作人所在的门店
		OAStore store = sysEmployeeMapper.findStoreByDeptId(deptId);
		String storeId = store.getStoreId();
		if (StringUtils.isBlank(storeId)) {
			throw new AuditException("参数异常,无法获取你的门店信息!");
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("storeId", storeId);
		if (null != page && null != rows) {
			rows = rows > 100 ? 100 : rows;
			param.put("page", page);
			param.put("rows", rows);
		} else {
			page = 1;
			rows = 10;
		}

		if (!StringUtils.isBlank(custname)) {
			param.put("custname", "%" + custname + "%");
		}
		
		if (!StringUtils.isBlank(mobile)) {
			param.put("mobile", mobile);
		}
		
		List<Intention> intention = intentionMaper.findPagerByStore(param);
		Integer total = intentionMaper.findPagerCountByStore(param);
		return new Pager<Intention>(intention, total);
	}

}
