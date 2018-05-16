package com.hoomsun.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 
 * @author liusong
 * @date 2017-06-08
 * @resource 此类是随机生成字符串(主要是用做主键生成)
 *
 */
public class RandomString {
	/*
	 * 产生20位数的字符串，作为数据库中记录的唯一表示
	 */
	public static final String generateId() {

		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		String sysDate = sf.format(new Date());
		String randomStr = randomString(6);
		return sysDate + randomStr;
	}

	/*
	 * 产生随机字符串
	 */
	public static final String randomString(int length) {
		Random randGen = null;
		char[] numbersAndLetters = null;
		if (length < 1) {
			return "";
		}

		if (randGen == null) {
			randGen = new Random();
			numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
					+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		}

		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(51)];
		}

		return new String(randBuffer);
	}
	
}
