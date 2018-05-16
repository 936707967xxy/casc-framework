/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.common.security;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月17日 <br>
 * 描述：非对称加密
 */
public class RSA {
	private final static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDXFicoMTUOU0VIzqejkmA3h5OIavgJUDAh4QEXjXwTaz/KbHT/P0U3xud3CBO1gwYwILy/45RhHaskeuXYanT4UwvPZ4UhKulSWu+kyruDNYReob61qTE22eALovWWeXQNEprvRThOcN0qMzYl1tbjA2OcMXrP0Joobm9BNjWnOQIDAQAB";
	private final static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANcWJygxNQ5TRUjOp6OSYDeHk4hq+AlQMCHhAReNfBNrP8psdP8/RTfG53cIE7WDBjAgvL/jlGEdqyR65dhqdPhTC89nhSEq6VJa76TKu4M1hF6hvrWpMTbZ4Aui9ZZ5dA0Smu9FOE5w3SozNiXW1uMDY5wxes/Qmihub0E2Nac5AgMBAAECgYBAEp5Zj8cRXGcAWGeCB8HX6PBfLBZ1l8iu3vtXS9ORaVqYb5Su0OdKxVsAj4xnfv+h6K6xrxbtk0v/UwkTXis3np66sFau7vyNt4bLe9GZZybSEmKnso6M/oavjU3GpSeYPH/HXAj6Z21jqC2agA5JfBk08RUUMvDtynLElc42gQJBAOwyk0QZlbt1TYOKvoyiemi1O6c2xUQqwud0mx3m/GIbNjcRuhcpP0MGAaq/x8QTFIWY9R0jplxuLpiLUcASxkkCQQDpHnqva4BzgwK6q6fUvGB5nnWPWd92myudtzDYLKTU2WQ2LyNgEP6FRAugCSEkkyNddasgo9QTlOhWkkuIFxlxAkEAuEY08rprQRchZHEIfLv+eFZ8tdZbTPV3nhAxPMELQpcsUwZ3gr5hZq72+foppmWkax6MjTFVB2PG4qg/HXUIUQJAMxqybIhVlX0P+RouZa2SPLcnMHDUUyz7J80EES/gRqUNme9FgwbWtSJQphm6QUlcRF3KURJ5KH3JXUke88uUkQJBANNC6zz7DRB87NZkfmjEggDjgHU21l3tGQfUIlwn+ATBKmMy7FQy7FW2D31Mx2zZs5QbNZPw9Hi/swNEE80u24w=";

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月17日 <br>
	 * 描述： 私钥加密 —— 加密
	 * 
	 * @param data 要加密签名的数据
	 * @return
	 * @throws Exception
	 */
	public static String codePrivate(String data) throws Exception {
		// 2.私钥加密、公钥解密——加密
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey.getBytes()));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		byte[] result = cipher.doFinal(data.getBytes());
		return Base64.encodeBase64String(result);
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月17日 <br>
	 * 描述： 私钥加密、公钥解密——解密
	 * @param sing 签名
	 * @return
	 * @throws Exception
	 */
	public static String encodePublic(String sign) throws Exception {
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey.getBytes()));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte[] result = cipher.doFinal(Base64.decodeBase64(sign));
		return new String(result);
	}
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月17日 <br>
	 * 描述： 公钥加密、私钥解密——加密
	 * @param data 要加密签名的数据
	 * @return
	 * @throws Exception
	 */
	public static String codePublic(String data) throws Exception {
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey.getBytes()));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] result = cipher.doFinal(data.getBytes());
		return Base64.encodeBase64String(result);
	}
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月17日 <br>
	 * 描述： 公钥加密、私钥解密——解密
	 * @param sing 签名
	 * @return
	 * @throws Exception
	 */
	public static String encodePrivate(String sign) throws Exception {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey.getBytes()));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] result = cipher.doFinal(Base64.decodeBase64(sign));
		return new String(result);
	}
	

	public static Map<String,String> getKeyPair() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
		 Map<String,String> map = new HashMap<String,String>();
		 System.out.println(Base64.encodeBase64String(rsaPublicKey.getEncoded()));
		 System.out.println(Base64.encodeBase64String(rsaPrivateKey.getEncoded()));
		 map.put("publicKey", Base64.encodeBase64String(rsaPublicKey.getEncoded()));
		 map.put("privateKey", Base64.encodeBase64String(rsaPrivateKey.getEncoded()));
		return map;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(getKeyPair());
//		String sign = codePrivate("zouyiwei2017");
//		System.out.println(sign);
//		System.out.println(encodePublic(sign));
	}
}
