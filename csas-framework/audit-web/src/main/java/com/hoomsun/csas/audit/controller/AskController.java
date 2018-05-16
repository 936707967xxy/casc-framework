/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.model.Json;
import com.hoomsun.risk.model.match.ApplyHistory;
import com.hoomsun.risk.model.match.ApplyHistory.History;
import com.hoomsun.risk.model.match.BlackListMatch;
import com.hoomsun.risk.model.match.CallTopMatch;
import com.hoomsun.risk.model.match.CallTopMatch.Apply;
import com.hoomsun.risk.model.match.MatchingRecord;
import com.hoomsun.risk.server.inter.ApplyHistoryServerI;
import com.hoomsun.risk.server.inter.BlackListMatchServerI;
import com.hoomsun.risk.server.inter.CallTopMatchServerI;
import com.hoomsun.risk.server.inter.UserHeadInfoServerI;

/**
 * 作者：liusong <br>
 * 创建时间：2018年2月25日 <br>
 * 描述：反欺诈规则数据查询
 */
@Controller
public class AskController {
	@Autowired
	private ApplyHistoryServerI applyHistoryServer;
	@Autowired
	private BlackListMatchServerI blackListMatchServer;
	@Autowired
	private CallTopMatchServerI callTopMatchServer;
	
	//反欺诈规则数据查询
	@RequestMapping("/ask/list/data.do")
	@ResponseBody
	public Map<String,Object> findAskList(String applyId){
		Map<String,Object> result = new HashMap<String,Object>();
		ApplyHistory history = applyHistoryServer.findByApplyId(applyId);//申请历史 
		BlackListMatch blackList = blackListMatchServer.findByApplyId(applyId);//黑名单匹配
		CallTopMatch callTop = callTopMatchServer.findByApplyId(applyId);//通话记录通讯录通话topN匹配结果 当前申请与其他申请的数据匹配
		if(null != history){
			result.put("history", buildHistory(history));
		}
		if(null != blackList){
			result.put("blackList", bulidBlackList(blackList));
		}
		if(null != callTop){
			result.put("callTop", buildCallTop(callTop));
		}
		
		return result;
	}
	
	//申请历史 
	public JSONObject buildHistory(ApplyHistory history){
		if (null == history) {
			return null;
		}
		
		JSONObject object = new JSONObject();
		object.put("name", history.getLoanId());
		JSONObject label = new JSONObject();
		label.put("align", "right");
		label.put("offset", new Integer[]{-20,0});
		label.put("fontSize", "18");
		label.put("fontWeight", "800");
		object.put("label", label);
		
		JSONArray children = new JSONArray();
		HashSet<History> histories = history.getHistories();
		if (null != histories && histories.size() > 0) {
			for (History ht : histories) {
				String loanId = ht.getLoanId();
				label = new JSONObject();
				label.put("color", "#ED1B24");
				label.put("offset", new Integer[]{-20,0});
				label.put("fontSize", "16");
				label.put("fontWeight", "600");
				label.put("align", "right");
				
				JSONObject cld = new JSONObject();
				cld.put("name", loanId);
				cld.put("label", label);
				
				List<MatchingRecord> result = ht.getMatchResults();
				if (null != result && result.size() > 0) {
					JSONArray childrens = new JSONArray();
					for (MatchingRecord rt : result) {
						Integer matchLevel = rt.getMatchLevel();
						String matchVal = rt.getMatchTypeVal();
						
						JSONObject obj = new JSONObject();
						obj.put("name", matchVal);
						label = new JSONObject();
						
						String color = "";
						if (matchLevel == 1) {
							color="#D1BE00";
						} else if (matchLevel == 2){
							color="#F68713";
						} else if (matchLevel == 3){
							color="#C82128";
						}
						label.put("color", color);
						obj.put("label", label);
						childrens.add(obj);
					}
					cld.put("children", childrens);
				}
				children.add(cld);
			}
			object.put("children", children);
		}
		return object;
	}
	//黑名单匹配
	public JSONObject bulidBlackList(BlackListMatch blackList){
		if(null == blackList){
			return null;
		}
		JSONObject object = new JSONObject();
		object.put("name", blackList.getLoanId());
		JSONObject label = new JSONObject();
		label.put("align", "right");
		label.put("offset", new Integer[]{-20,0});
		label.put("fontSize", "18");
		label.put("fontWeight", "800");
		object.put("label", label);
		JSONArray children = new JSONArray();
		List<MatchingRecord> matchingRecords = blackList.getMatchingRecords();
		if(null != matchingRecords && matchingRecords.size() > 0){
			for (MatchingRecord matchingRecord : matchingRecords) {
				String matchVal = matchingRecord.getMatchTypeVal();
				JSONObject obj = new JSONObject();
				obj.put("name", matchVal);
				label = new JSONObject();
				label.put("color", "#ED1B24");
				label.put("fontSize", "16");
				label.put("fontWeight", "600");
				obj.put("label", label);
				children.add(obj);
			}
			object.put("children", children);
		}
		return object;
		
	} 
	//通话记录通讯录通话topN匹配结果
	public JSONObject buildCallTop(CallTopMatch callTop){
		if(null == callTop){
			return null;
		}
		JSONObject object = new JSONObject();
		object.put("name", callTop.getLoanId());
		JSONObject label = new JSONObject();
		label.put("align", "right");
		label.put("offset", new Integer[]{-20,0});
		label.put("fontSize", "18");
		label.put("fontWeight", "800");
		object.put("label", label);
		JSONArray children = new JSONArray();
		List<Apply> apply = callTop.getApplies();
		if (null != apply && apply.size() > 0) {
			for (Apply ht : apply) {
				String loanId = ht.getLoanId();
				label = new JSONObject();
				label.put("color", "#ED1B24");
				label.put("offset", new Integer[]{-20,0});
				label.put("fontSize", "16");
				label.put("fontWeight", "600");
				label.put("align", "right");
				
				JSONObject cld = new JSONObject();
				cld.put("name", loanId);
				cld.put("label", label);
				
				List<MatchingRecord> result = ht.getMatchResults();
				if (null != result && result.size() > 0) {
					JSONArray childrens = new JSONArray();
					for (MatchingRecord rt : result) {
						Integer matchLevel = rt.getMatchLevel();
						String matchVal = rt.getMatchTypeVal();
						
						JSONObject obj = new JSONObject();
						obj.put("name", matchVal);
						label = new JSONObject();
						
						String color = "";
						if (matchLevel == 1) {
							color="#D1BE00";
						} else if (matchLevel == 2){
							color="#F68713";
						} else if (matchLevel == 3){
							color="#C82128";
						}
						label.put("color", color);
						obj.put("label", label);
						childrens.add(obj);
					}
					cld.put("children", childrens);
				}
				children.add(cld);
			}
			object.put("children", children);
		}
		return object;
	}
}
