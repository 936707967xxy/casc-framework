/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.after;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.after.api.model.param.OverdueRecordUpdateParmas;
import com.hoomsun.after.api.model.table.OverdueRecord;
import com.hoomsun.after.api.model.vo.OverdueRecordUpdateVo;
import com.hoomsun.after.api.server.OverdueDaysRollbackServer;
import com.hoomsun.common.model.Json;

/**
 * 作者：金世强 <br>
 * 创建时间：2018年4月3日 <br>
 * 描述：逾期天数回退
 */
@Controller
public class OverdueDaysRollbackController {

	@Autowired
	private OverdueDaysRollbackServer overdueDaysRollbackServer;

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 加载逾期天数回退列表
	 * 
	 * @param loanId
	 * @return
	 */
	@RequestMapping("/after/overdue/overduedaysrollbacklist.do")
	public @ResponseBody List<OverdueRecord> saveOverdueDaysRollbackList(String loanId) {
		List<OverdueRecord> overdueRecords = overdueDaysRollbackServer.selectOverdueAll(loanId);

		return overdueRecords;

	}

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 预算逾期天数回退数据
	 * 
	 * @param loanId
	 * @return
	 */
	@RequestMapping("/after/overdue/overduedaysrollbacklist2.do")
	public @ResponseBody OverdueRecordUpdateVo saveOverdueDaysRollbackList2(@RequestBody JSONObject jb) {
		
		OverdueRecordUpdateParmas overdueRecordUpdateParmas = JSONObject.toJavaObject(jb, OverdueRecordUpdateParmas.class);

		OverdueRecordUpdateVo overdueRecords = overdueDaysRollbackServer.selectOverdueAll2(overdueRecordUpdateParmas);

		return overdueRecords;

	}

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 逾期天数回退操作
	 * 
	 * @param jb
	 * @return
	 */
	@RequestMapping("/after/overdue/overduedaysrollback.do")
	public @ResponseBody Json saveOverdueDaysRollback(@RequestBody JSONObject jb) {

		OverdueRecordUpdateParmas overdueRecordUpdateParmas = JSONObject.toJavaObject(jb, OverdueRecordUpdateParmas.class);

		overdueDaysRollbackServer.saveOverdueDaysRollback(overdueRecordUpdateParmas);

		Json json = new Json();
		json.setSuccess(true);
		json.setMsg("操作成功！");
		return json;

	}

}
