package com.hoomsun.core.dao;

import java.util.List;
import java.util.Map;

import com.hoomsun.core.model.SysContract;

public interface SysContractMapper {
	int deleteByPrimaryKey(String conId);

	int insert(SysContract record);

	int insertSelective(SysContract record);

	SysContract selectByPrimaryKey(String conId);

	int updateByPrimaryKeySelective(SysContract record);

	int updateByPrimaryKey(SysContract record);

	SysContract selectByApplyId(String applyId);

	int selectByApplyIdCount(String applyId);

	int deleteByApplyId(String applyId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月20日 <br>
	 * 描述： 根据合同ID 获取合同的详细数据信息 含还款计划
	 * 
	 * @param conId
	 * @return
	 */
	SysContract findByConId(String conId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月20日 <br>
	 * 描述： 根据合同申请 获取合同的详细数据信息 含还款计划
	 * 
	 * @param applyId
	 * @return
	 */
	SysContract findByApplyId(String applyId);

	/**
	 * 作者：刘栋梁 <br>
	 * 创建时间：2017年12月20日 <br>
	 * 描述： 根据合apply查询合同信息
	 * 
	 * @param applyId
	 * @return
	 */
	SysContract findinfoByApplyId(String applyId);

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月22日 <br>
	 * 描述： 修改合同状态1
	 * 
	 * @param applyId
	 * @return
	 */
	int updateConStatus(String applyId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月24日 <br>
	 * 描述： 根据合同ID获取申请ID
	 * 
	 * @param conId
	 * @return
	 */
	String findApplyIdByConId(String conId);

	String findConIdByApplyId(String applyId);

	/**
	 * 作者：jinshiqiang <br>
	 * 创建时间：2018年01月10日 <br>
	 * 描述： 根据APPLYID修改合同状态
	 * 
	 * @param conId
	 * @return
	 */
	void updateConStatusByApplyId(Map<String, String> constatus);
	
	/**
	 * 作者：刘栋梁 <br>
	 * 创建时间：2018年1月16日 <br>
	 * 描述： 查询合同账单日---在修改银行卡时给提示信息 
	 * 
	 * @param applyId
	 * @return
	 */
	List<SysContract> findConPayData(Map<String, String> constatus);
}