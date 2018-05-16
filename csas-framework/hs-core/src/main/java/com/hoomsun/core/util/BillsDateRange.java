/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.util;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月27日 <br>
 * 描述：账单日区间
 */
public class BillsDateRange {
	private Integer start = -1;//开始区间
	private Integer end = -1;//区间结束
	private Integer value = -1;//值
	private Integer weight = 0;//权重值

	public BillsDateRange() {
	}

	public BillsDateRange(Integer start, Integer end) {
		this.start = start;
		this.end = end;
	}
	
	public BillsDateRange(Integer start, Integer end,Integer value,Integer weight) {
		this.start = start;
		this.end = end;
		this.value = value;
		this.weight = weight;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "BillsDateRange [start=" + start + ", end=" + end + ", value=" + value + ", weight=" + weight + "]";
	}

}
