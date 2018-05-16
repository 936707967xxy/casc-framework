/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.util;

import com.hoomsun.csas.audit.dao.BPMNMapper;

/**
 * 作者：liushuai <br>
 * 创建时间：2018年1月6日 <br>
 * 描述：
 */
public class ActivitiUtils {
	
	public static boolean getClaimTaskSign(BPMNMapper bpmnMapper, String empId){
		int sub = bpmnMapper.selectClaimTaskSign(empId); // 个人签收任务数量-每个人可以签收的任务数量
		if (sub >= 0) {
			return false;
		}else {
			return true;
		}
	}
}
