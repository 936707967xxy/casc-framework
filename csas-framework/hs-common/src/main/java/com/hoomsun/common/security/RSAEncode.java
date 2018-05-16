/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.common.security;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hoomsun.common.util.RSACoder;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年10月23日 <br>
 * 描述：RAS 非对称加密算法数字签名
 */
public class RSAEncode {
	private static Logger log = LoggerFactory.getLogger(RSACoder.class);
	private static String src = "imooc security rsa";

	public static void main(String[] args) {
		try {
			Map<String, String> map = RSAEncode.encode("zouyiwei");
			for (Map.Entry<String, String> et : map.entrySet()) {
				System.out.println(et.getKey() + "*****" + et.getValue());
			}
//			System.out.println(map);
			System.out.println(RSAEncode.unencode(map.get("publicKey"), map.get("publicKey")));
		} catch (Exception e) {
		}
		
//		jdkRSA();
	}
	
	public static Map<String, String> encode(String key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Map<String, String> map = new HashMap<String, String>();
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(512);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
		String publicKeyStr = Base64.encodeBase64String(rsaPublicKey.getEncoded());
		String privateKeyStr = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
		map.put("publicKey", privateKeyStr);
		map.put("privateKey", privateKeyStr);
		log.info("######Public Key : " + publicKeyStr);
		log.info("######Private Key : " + privateKeyStr);

		// 2.私钥加密、公钥解密——加密
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		byte[] result = cipher.doFinal(src.getBytes());
		String signature = Base64.encodeBase64String(result);
		map.put("signature", signature);
		log.info("######私钥加密、公钥解密——加密 : " + signature);
		log.info("######私钥加密、公钥解密——加密 : " + map);
		return map;
	}

	public static String unencode(String sign, String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getBytes());
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKeyRsa = keyFactory.generatePublic(x509EncodedKeySpec);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, publicKeyRsa);
		byte[] result = cipher.doFinal(sign.getBytes());
		String encode = new String(result);
		log.info("######私钥加密、公钥解密——解密：" + encode);
		return encode;
	}

	public static void jdkRSA() {
		try {
			// 1.初始化密钥
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(512);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
			System.out.println("Public Key : " + Base64.encodeBase64String(rsaPublicKey.getEncoded()));
			System.out.println("Private Key : " + Base64.encodeBase64String(rsaPrivateKey.getEncoded()));

			// 2.私钥加密、公钥解密——加密
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] result = cipher.doFinal(src.getBytes());
			System.out.println("私钥加密、公钥解密——加密 : " + Base64.encodeBase64String(result));

			// 3.私钥加密、公钥解密——解密
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
			keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			result = cipher.doFinal(result);
			System.out.println("私钥加密、公钥解密——解密：" + new String(result));

			// 4.公钥加密、私钥解密——加密
			x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
			keyFactory = KeyFactory.getInstance("RSA");
			publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			result = cipher.doFinal(src.getBytes());
			System.out.println("公钥加密、私钥解密——加密 : " + Base64.encodeBase64String(result));

			// 5.公钥加密、私钥解密——解密
			pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
			keyFactory = KeyFactory.getInstance("RSA");
			privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			result = cipher.doFinal(result);
			System.out.println("公钥加密、私钥解密——解密：" + new String(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
