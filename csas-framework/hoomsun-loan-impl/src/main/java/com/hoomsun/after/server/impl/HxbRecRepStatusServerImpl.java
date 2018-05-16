/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.server.impl;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.after.api.model.table.HxbRepaySend;
import com.hoomsun.after.api.server.HxbRecRepStatusServer;
import com.hoomsun.after.dao.HxbRepaySendMapper;
import com.hoomsun.common.util.CreateSerialNo;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.digest.DigestVerify;
import com.hoomsun.util.ScGeneralInfo;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年5月14日 <br>
 * 描述：
 */
@Service("hxbRecRepStatusServer")
public class HxbRecRepStatusServerImpl implements HxbRecRepStatusServer {

	private static final Logger log = LoggerFactory.getLogger(HxbRecRepStatusServer.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private HxbRepaySendMapper hxbRepaySendMapper;

	@Override
	public JSONObject saveRecRepStatus(HttpServletRequest req) {

		// 返回hxb数据
		JSONObject returnObj = new JSONObject();
		String errMsg = "";

		try {

			req.setCharacterEncoding("utf-8");

			BufferedReader bufferReader = req.getReader();// 获取头部参数信息
			StringBuffer buffer = new StringBuffer();
			String line = " ";
			while ((line = bufferReader.readLine()) != null) {
				buffer.append(line);

			}

			String postData = buffer.toString();

			log.info("还款状态推送接口接收红小宝信息:" + postData);

			if (postData != null && !"".equals(postData)) {
				JSONObject RecRepStatusObj = new JSONObject();
				RecRepStatusObj = RecRepStatusObj.parseObject(postData);

				JSONObject generalInfo = RecRepStatusObj.getJSONObject("generalInfo");

				ScGeneralInfo info = new ScGeneralInfo();
				info.setApplyTime(generalInfo.getString("applyTime"));
				info.setSerialNo(generalInfo.getString("serialNo"));
				info.setSign(generalInfo.getString("sign"));
				Boolean verify = new DigestVerify().verify(generalInfo.getString("sign"), info, "UTF-8", "debx-zhixin");
				// 接口加密验证成功
				if (verify) {
					JSONArray loanRepayResultList = new JSONArray();
					loanRepayResultList = RecRepStatusObj.getJSONArray("loanRepayResultList");
					int count = 0;// 还款状态处理正确条数

					for (int i = 0; i < loanRepayResultList.size(); i++) {

						String loanRefId = loanRepayResultList.getJSONObject(i).getString("loanRefId");// 至信借款的唯一编号
						Integer periodNumber = loanRepayResultList.getJSONObject(i).getInteger("periodNumber");// 期序
						String repaidTime = loanRepayResultList.getJSONObject(i).getString("repaidTime");// 实际还款完成时间
						String repaidType = loanRepayResultList.getJSONObject(i).getString("repaidType");// 还款类型：IN_REPAY(提前还款),COMMON_REPAY(正常还款)

						String overdueFlag = loanRepayResultList.getJSONObject(i).getString("overdueFlag");// 是否逾期,是:1,
																											// 否:0(当逾期状态是，正常还款即是逾期)

						Date repaidDate = null;
						if (repaidTime != null && !"".equals(repaidTime)) {
							repaidDate = sdf.parse(repaidTime);
						}

						HxbRepaySend hxbRepaySend = new HxbRepaySend();
						hxbRepaySend.setId(PrimaryKeyUtil.getPrimaryKey());
						hxbRepaySend.setLoanId(loanRefId);
						hxbRepaySend.setStream(periodNumber);
						hxbRepaySend.setRepaidType(repaidType);
						hxbRepaySend.setRepaidTime(repaidDate);
						hxbRepaySend.setOverdueFlag(overdueFlag);
						hxbRepaySend.setCreateDate(new Date());
						hxbRepaySendMapper.insert(hxbRepaySend);
						count++;

					}

					SimpleDateFormat asdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String handleTime = asdf.format(new Date());

					returnObj.put("totalCount", loanRepayResultList.size());
					if (count == loanRepayResultList.size()) {
						returnObj.put("status", 1000);
						returnObj.put("handleTime", handleTime);

					} else if (count > 0) {
						returnObj.put("status", 1100);
						returnObj.put("handleTime", handleTime);
						errMsg = "部分更新失败";
					}
				}
				// 接口加密验证失败
				else {
					errMsg = "签名校验未通过";
				}
				returnObj.put("generalInfo", CreateSerialNo.sign());
				returnObj.put("errMsg", errMsg);

			}

			log.info("还款状态推送接口返回给红小宝信息:" + returnObj);

			log.info("postData=================" + postData);

		} catch (Exception e) {
			e.printStackTrace();
			SimpleDateFormat asdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String handleTime = asdf.format(new Date());

			returnObj.put("generalInfo", CreateSerialNo.sign());
			returnObj.put("status", 2000);
			returnObj.put("handleTime", handleTime);
			returnObj.put("errMsg", "系统未知异常");

			log.info("还款状态推送接口请求异常:" + e);
		}
		return returnObj;

	}

}
