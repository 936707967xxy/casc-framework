/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.inter;

import java.util.Map;


/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月29日 <br>
 * 描述：流程执行的业务接口
 */
public interface ProcessExecutionI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月29日 <br>
	 * 描述： 根据流程key启动流程 并且返回流程实例ID
	 * @param processKey
	 * @return
	 */
	String startProcess(String processKey,String businessId,Map<String, Object> param);
	
	void completeTask();
}
