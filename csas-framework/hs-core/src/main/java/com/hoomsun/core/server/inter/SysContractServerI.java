/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.inter;

import java.util.List;
import java.util.Map;

import com.hoomsun.common.model.Json;
import com.hoomsun.core.model.SysContract;

/**
 * 作者：liusong <br>
 * 创建时间：2017年12月8日 <br>
 * 描述：
 */
public interface SysContractServerI {
	
	Json createContract(SysContract contract);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：修改公司
	 * @param company 公司修改数据信息
	 * @return
	 */
	Json updateCom(SysContract contract);
	
	SysContract selectByApplyId(String applyId);
	
	int selectByApplyIdCount(String applyId);
	
	int updateConStatus(String applyId);
	
	/**
     * 作者：ywzou <br>
     * 创建时间：2017年12月20日 <br>
     * 描述： 根据合同ID  获取合同的详细数据信息 含还款计划
     * @param conId
     * @return
     */
    SysContract findByConId(String conId);
    /**
     * 作者：ywzou <br>
     * 创建时间：2017年12月20日 <br>
     * 描述： 根据合同申请 获取合同的详细数据信息 含还款计划
     * @param applyId
     * @return
     */
    SysContract findByApplyId(String applyId);
    
    /**
     * 作者：刘栋梁 <br>
     * 创建时间：2017年12月20日 <br>
     * 描述： 根据合apply查询合同信息
     * @param applyId
     * @return
     */
    SysContract findinfoByApplyId(String applyId);
    
	String findApplyIdByConId(String conId);
	
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
