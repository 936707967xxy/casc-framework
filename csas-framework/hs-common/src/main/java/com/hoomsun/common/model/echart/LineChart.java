/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.common.model.echart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月27日 <br>
 * 描述：折线图
 */
public class LineChart {
	private List<String> xAxis = new ArrayList<String>();
	private Map<String,List<Integer>> seriesData = new HashMap<String,List<Integer>>();
	
	public void addxAxis(String str) {
		this.xAxis.add(str);
	}
	
	public void addSeriesData(String dataName,List<Integer> val) {
		this.seriesData.put(dataName, val);
	}
	
	public LineChart() {
	}
	
	public LineChart(List<String> xAxis, Map<String,List<Integer>> seriesData) {
		this.xAxis = xAxis;
		this.seriesData = seriesData;
	}

	public List<String> getxAxis() {
		return xAxis;
	}

	public void setxAxis(List<String> xAxis) {
		this.xAxis = xAxis;
	}

	public Map<String,List<Integer>> getSeriesData() {
		return seriesData;
	}

	public void setSeriesData(Map<String,List<Integer>> seriesData) {
		this.seriesData = seriesData;
	}

	@Override
	public String toString() {
		return "LineChart [xAxis=" + xAxis + ", seriesData=" + seriesData + "]";
	}
}
