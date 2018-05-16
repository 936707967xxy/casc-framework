/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.server;

import java.util.List;

import com.hoomsun.after.api.model.param.OverdueRecordUpdateParmas;
import com.hoomsun.after.api.model.table.OverdueRecord;
import com.hoomsun.after.api.model.vo.OverdueRecordUpdateVo;

/**
 * 作者：金世强 <br>
 * 创建时间：2018年4月3日 <br>
 * 描述：逾期天数回退相关处理
 */
public interface OverdueDaysRollbackServer {

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月8日 <br>
	 * 描述： 查询客户所有逾期数据（未结清）
	 * 
	 * @param loanId
	 * @return
	 */
	List<OverdueRecord> selectOverdueAll(String loanId);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述：预算客户逾期天数回退数据
	 * 
	 * @param overdueRecordUpdateParmas
	 * @return
	 */
	OverdueRecordUpdateVo selectOverdueAll2(OverdueRecordUpdateParmas overdueRecordUpdateParmas);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 逾期天数回退
	 * 
	 * @param overdueRecordUpdateParmas
	 */
	void saveOverdueDaysRollback(OverdueRecordUpdateParmas overdueRecordUpdateParmas);

}
