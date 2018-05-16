package com.hoomsun.after.api.util.property;

public class BankCode {

	public static String getSupportBankCode(String bankName) {

		if ("工商银行".equals(bankName)) {
			return "ICBC";
		} else if ("农业银行".equals(bankName)) {
			return "ABC";
		} else if ("中国银行".equals(bankName)) {
			return "BOC";
		} else if ("建设银行".equals(bankName)) {
			return "CCB";
		} else if ("民生银行".equals(bankName)) {
			return "CMBC";
		} else if ("交通银行".equals(bankName)) {
			return "BCOM";
		} else if ("光大银行".equals(bankName)) {
			return "CEB";
		} else if ("广发银行".equals(bankName)) {
			return "GDB";
		} else if ("兴业银行".equals(bankName)) {
			return "CIB";
		} else if ("平安银行".equals(bankName)) {
			return "PAB";
		} else if ("浦发银行".equals(bankName)) {
			return "SPDB";
		} else if ("上海银行".equals(bankName)) {
			return "SHB";
		} else if ("邮政储蓄".equals(bankName)) {
			return "PSBC";
		} else if ("中信银行".equals(bankName)) {
			return "CITIC";
		} else if ("招商银行".equals(bankName)) {
			return "CMB";
		} else if ("华夏银行".equals(bankName)) {
			return "HXB";
		} else {

			return "1111";
		}

	}

	public static String getBankCode2(String bankName) {

		if ("工商银行".equals(bankName) || "ICBC".equals(bankName)) {
			return "0102";
		} else if ("农业银行".equals(bankName) || "ABC".equals(bankName)) {
			return "0103";
		} else if ("中国银行".equals(bankName) || "BOC".equals(bankName)) {
			return "0104";
		} else if ("建设银行".equals(bankName) || "CCB".equals(bankName)) {
			return "0105";
		} else if ("民生银行".equals(bankName) || "CMBC".equals(bankName)) {
			return "0305";
		} else if ("交通银行".equals(bankName) || "BCOM".equals(bankName)) {
			return "0301";
		} else if ("光大银行".equals(bankName) || "CEB".equals(bankName)) {
			return "0303";
		} else if ("兴业银行".equals(bankName) || "CIB".equals(bankName)) {
			return "0309";
		} else if ("平安银行".equals(bankName) || "PAB".equals(bankName)) {
			return "0307";
		} else if ("浦发银行".equals(bankName) || "SPDB".equals(bankName)) {
			return "0310";
		} else if ("邮政储蓄".equals(bankName) || "PSBC".equals(bankName)) {
			return "0403";
		} else if ("中信银行".equals(bankName) || "CITIC".equals(bankName)) {
			return "0302";
		} else if ("招商银行".equals(bankName) || "CMB".equals(bankName)) {
			return "0308";
		} else if ("华夏银行".equals(bankName) || "HXB".equals(bankName)) {
			return "0304";
		} else if ("广发银行".equals(bankName) || "GDB".equals(bankName)) {
			return "0306";
		} else {
			return "1111";
		}

	}

	public static String getBankCode3(String bankName) {

		if ("工商银行".equals(bankName) || "ICBC".equals(bankName)) {
			return "0102";
		} else if ("农业银行".equals(bankName) || "ABC".equals(bankName)) {
			return "0103";
		} else if ("中国银行".equals(bankName) || "BOC".equals(bankName)) {
			return "0104";
		} else if ("建设银行".equals(bankName) || "CCB".equals(bankName)) {
			return "0105";
		} else if ("民生银行".equals(bankName) || "CMBC".equals(bankName)) {
			return "0305";
		} else if ("交通银行".equals(bankName) || "BCOM".equals(bankName)) {
			return "0301";
		} else if ("光大银行".equals(bankName) || "CEB".equals(bankName)) {
			return "0303";
		} else if ("兴业银行".equals(bankName) || "CIB".equals(bankName)) {
			return "0309";
		} else if ("平安银行".equals(bankName) || "PAB".equals(bankName)) {
			return "0307";
		} else if ("浦发银行".equals(bankName) || "SPDB".equals(bankName)) {
			return "0310";
		} else if ("邮政储蓄".equals(bankName) || "PSBC".equals(bankName)) {
			return "0403";
		} else if ("中信银行".equals(bankName) || "CITIC".equals(bankName)) {
			return "0302";
		} else if ("招商银行".equals(bankName) || "CMB".equals(bankName)) {
			return "0308";
		} else if ("华夏银行".equals(bankName) || "HXB".equals(bankName)) {
			return "0304";
		} else if ("广发银行".equals(bankName) || "GDB".equals(bankName)) {
			return "0306";
		} else {
			return "1111";
		}

	}

}
