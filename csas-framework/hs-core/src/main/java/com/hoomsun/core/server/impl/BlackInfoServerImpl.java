/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.dao.BlackInfoMapper;
import com.hoomsun.core.model.BlackInfo;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.server.inter.BlackInfoServserI;
import com.hoomsun.core.util.PrimaryKeyUtil;


/**
 * 作者：liming <br>
 * 创建时间：2017年12月4日 <br>
 * 描述：黑名单业务层
 */
@Service("blackInfoServser")
public class BlackInfoServerImpl implements  BlackInfoServserI{
	
	@Autowired
	private BlackInfoMapper blackinfomapper;
	
	
	@Autowired
	public void setBlackinfomapper(BlackInfoMapper blackinfomapper) {
		this.blackinfomapper = blackinfomapper;
	}

	@Override
	public Json addBlackInfo(BlackInfo blackinfo,SessionRouter sessionRouter) {
		String empId = sessionRouter.getEmpId();//登录人id
		String empName =sessionRouter.getEmpName();

		if (StringUtils.isBlank(blackinfo.getId())) {
		
			blackinfo.setId(PrimaryKeyUtil.getPrimaryKey());
		}
		Timestamp time = new Timestamp(System.currentTimeMillis()); 
		blackinfo.setRollinDate(time);//转入时间
		blackinfo.setOperator(empName);//录入人员
		blackinfo.setRollinPerson(empId);//转入人员id
		blackinfo.setRollinPersonVal(empName);//转入人员姓名
		blackinfo.setIsblackflag("1");//0正常，1黑名单
		blackinfo.setCuststate("1");//客户状态{0正常,1黑名单转入申请,2黑名单转出申请}
		blackinfo.setCuststateVal("黑名单转入");
		
		int result=blackinfomapper.insertSelective(blackinfo);
		if (result > 0) {
			return new Json(true, "黑名单转入成功!");
		} else {
			return new Json(false, "黑名单转入失败!");
		}
		
	}

	@Override
	public Json updateBlackInfo(BlackInfo blackinfo,SessionRouter sessionRouter) {
		String empId = sessionRouter.getEmpId();//登录人id
		String empName =sessionRouter.getEmpName();
		blackinfo.setIsblackflag("1");//0正常，1黑名单
		blackinfo.setCuststate("2");//客户状态{0正常,1黑名单转入申请,2黑名单转出申请}
		blackinfo.setCuststateVal("黑名单转出");
		Timestamp time = new Timestamp(System.currentTimeMillis());
		blackinfo.setRolloutDate(time);//转出时间
		blackinfo.setRolloutPerosn(empId);
		blackinfo.setRolloutPerosnVal(empName);
		int result=blackinfomapper.updateByPrimaryKeySelective(blackinfo);
		if (result > 0) {
			return new Json(true, "黑名单转出成功!");
		} else {
			return new Json(false, "黑名单转出失败!");
		}
	}

	@Override
	public Json delteBlackInfo(String id) {
		int result=blackinfomapper.deleteByPrimaryKey(id);
		if (result > 0) {
			return new Json(true, "黑名单数据删除成功!");
		} else {
			return new Json(false, "黑名单数据删除失败!");
		}
	}



	@Override
	public Pager<BlackInfo> findPage(Integer page, Integer rows, String custname,String phone,String idnumber,Integer custstate) {
		Map<String, Object> param = new HashMap<String, Object>();

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
		if (!StringUtils.isBlank(phone)) {
			param.put("phone", "%" + phone + "%");
		}
		if (!StringUtils.isBlank(idnumber)) {
			param.put("idnumber", "%" + idnumber + "%");
		}
		if(custstate != null){
			param.put("custstate",custstate);
		}
		List<BlackInfo> blackinfo = blackinfomapper.findPageData(param);
		Integer total = blackinfomapper.findPageCount(param);
		return new Pager<BlackInfo>(blackinfo, total);
		
	}

	@Override
	public BlackInfo findById(String id) {
		
		return blackinfomapper.findById(id);
	}

	@Override
	public Json findByPhone(String phone) {
		int count=blackinfomapper.findByPhone(phone);
		if(count>0){
			return new Json(false, "此客户是黑名单客户！");
		}else{
			return new Json(true, "此客户是正常客户！");
		}
		
		
	}
	//同意审批
	@Override
	public Json agree(BlackInfo blackinfo,SessionRouter sessionRouter) {
		String empId = sessionRouter.getEmpId();//登录人id
		String empName =sessionRouter.getEmpName();
		blackinfo.setDealPersonId(empId);
		blackinfo.setDealPersonName(empName);
		//1同意转入，2同意转出
		String custState=blackinfo.getCuststate();	
		if("1".equals(custState)){
			blackinfo.setDealopinions("1");
			blackinfo.setDealopinionsval("同意");
			blackinfo.setIsblackflag("1");//0正常，1黑名单
			blackinfo.setCuststate("3");//已审核
			blackinfo.setCuststateVal("已转入");
		}else if("2".equals(custState)){
			blackinfo.setDealopinions("1");
			blackinfo.setDealopinionsval("同意");
			blackinfo.setIsblackflag("0");//0正常，1黑名单
			blackinfo.setCuststate("3");//已审核
			blackinfo.setCuststateVal("已转出");
		}
	
		int result=blackinfomapper.updateByPrimaryKeySelective(blackinfo);
		if (result > 0) {
			return new Json(true, "同意审批!");
		} else {
			return new Json(false, "审批失败!");
		}
	
	}

	//不同意审批
	@Override
	public Json noagree(BlackInfo blackinfo, SessionRouter sessionRouter) {
		String empId = sessionRouter.getEmpId();//登录人id
		String empName =sessionRouter.getEmpName();
		blackinfo.setDealPersonId(empId);
		blackinfo.setDealPersonName(empName);
		//1同意转入，2同意转出
		blackinfo.setDealopinions("0");
		blackinfo.setDealopinionsval("不同意");
		blackinfo.setIsblackflag("1");//0正常，1黑名单
		int result=blackinfomapper.updateByPrimaryKeySelective(blackinfo);
		if (result > 0) {
			return new Json(true, "不同意审批!");
		} else {
			return new Json(false, "审批失败!");
		}
	}


}
