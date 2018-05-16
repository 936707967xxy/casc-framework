package com.hoomsun.core.util;

import java.util.List;

public class BillsDateRangeCfg {
	private List<BillsDateRange> billsDateRanges;
	private List<BillsDateRange> onlineBillsDateRanges;

	public List<BillsDateRange> getBillsDateRanges() {
		return billsDateRanges;
	}

	public void setBillsDateRanges(List<BillsDateRange> billsDateRanges) {
		this.billsDateRanges = billsDateRanges;
	}

	public List<BillsDateRange> getOnlineBillsDateRanges() {
		return onlineBillsDateRanges;
	}

	public void setOnlineBillsDateRanges(List<BillsDateRange> onlineBillsDateRanges) {
		this.onlineBillsDateRanges = onlineBillsDateRanges;
	}

}
