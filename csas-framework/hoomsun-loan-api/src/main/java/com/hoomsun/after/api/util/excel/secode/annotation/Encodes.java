package com.hoomsun.after.api.util.excel.secode.annotation;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class Encodes {

	private static final String DEFAULT_URL_ENCODING = "UTF-8";
	private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

	
	/**
	 * @author xinyuan.xu@hoomsun.com
	 * @param input
	 * @return
	 * @Description: Hex编码
	 * @param 2018年2月2日
	 */
	public static String encodeHex(byte[] input) {
		return new String(Hex.encodeHex(input));
	}

	/**
	 * 
	 * @author xinyuan.xu@hoomsun.com
	 * @param input
	 * @return
	 * @Description:Hex解码.
	 * @param 2018年2月2日
	 */
	public static byte[] decodeHex(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * @author xinyuan.xu@hoomsun.com
	 * @param input
	 * @return
	 * @Description:Base64编码.
	 * @param 2018年2月2日
	 */
	public static String encodeBase64(byte[] input) {
		return new String(Base64.encodeBase64(input));
	}
	/**
	 * @author xinyuan.xu@hoomsun.com
	 * @param input
	 * @return
	 * @Description:Base64编码.
	 * @param 2018年2月2日
	 */
	public static String encodeBase64(String input) {
		try {
			return new String(Base64.encodeBase64(input.getBytes(DEFAULT_URL_ENCODING)));
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * @author xinyuan.xu@hoomsun.com
	 * @param input
	 * @return
	 * @Description:Base64解码.
	 * @param 2018年2月2日 下午3:39:18
	 */
	public static byte[] decodeBase64(String input) {
		return Base64.decodeBase64(input.getBytes());
	}
	
	/**
	 * @author xinyuan.xu@hoomsun.com
	 * @param input
	 * @return
	 * @Description:Base64解码.
	 * @param 2018年2月2日
	 */
	public static String decodeBase64String(String input) {
		try {
			return new String(Base64.decodeBase64(input.getBytes()), DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * @author xinyuan.xu@hoomsun.com
	 * @param input
	 * @return
	 * @Description:Base62编码。
	 * @param 2018年2月2日
	 */
	public static String encodeBase62(byte[] input) {
		char[] chars = new char[input.length];
		for (int i = 0; i < input.length; i++) {
			chars[i] = BASE62[((input[i] & 0xFF) % BASE62.length)];
		}
		return new String(chars);
	}
	/**
	 * @author xinyuan.xu@hoomsun.com
	 * @param part
	 * @return
	 * @Description:URL 编码, Encode默认为UTF-8. 
	 * @param 2018年2月2日
	 */
	public static String urlEncode(String part) {
		try {
			return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * @author xinyuan.xu@hoomsun.com
	 * @param part
	 * @return
	 * @Description:URL 解码, Encode默认为UTF-8. 
	 * @param 2018年2月2日
	 */
	public static String urlDecode(String part) {

		try {
			return URLDecoder.decode(part, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw Exceptions.unchecked(e);
		}
	}
}
