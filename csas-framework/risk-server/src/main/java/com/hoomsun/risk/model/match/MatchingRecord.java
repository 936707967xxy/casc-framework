/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.model.match;

import org.springframework.data.mongodb.core.index.Indexed;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月9日 <br>
 * 描述：匹配结果
 */
public class MatchingRecord {
	@Indexed
	private String matchType;// 匹配类型
	private String matchTypeVal;
	private Integer matchLevel;// 匹配等级 1:黄色 2：橙色 3：红色
	private String matchLevelVal;
	private String remarks;// 描述
	@Indexed
	private String cheatId;// 外键 欺诈主要表
	private Double score;// 分值

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getMatchTypeVal() {
		return matchTypeVal;
	}

	public void setMatchTypeVal(String matchTypeVal) {
		this.matchTypeVal = matchTypeVal;
	}

	public Integer getMatchLevel() {
		return matchLevel;
	}

	public void setMatchLevel(Integer matchLevel) {
		this.matchLevel = matchLevel;
	}

	public String getMatchLevelVal() {
		return matchLevelVal;
	}

	public void setMatchLevelVal(String matchLevelVal) {
		this.matchLevelVal = matchLevelVal;
	}

	public String getCheatId() {
		return cheatId;
	}

	public void setCheatId(String cheatId) {
		this.cheatId = cheatId;
	}

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

}
