/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.common.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：liushuai <br>
 * 创建时间：2017年9月30日 <br>
 * 描述：RSA签名验证算法
 * 资料：http://blog.csdn.net/baidu_34012226/article/details/53331147 所有签名相关（这里采用第五种方式）
 */
public class RSACoder {

	private final static Logger log = LoggerFactory.getLogger(RSACoder.class);

	// 数字签名，密钥算法
	private static final String KEY_ALGORITHM = "RSA";

	/**
	 * 数字签名 签名/验证算法
	 */
	private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/**
	 * RSA密钥长度，RSA算法的默认密钥长度是1024 密钥长度必须是64的倍数，在512到65536位之间
	 */
	private static final int KEY_SIZE = 512;
	// 公钥
	private static final String PUBLIC_KEY = "RSAPublicKey";

	// 私钥
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * 初始化密钥对
	 * 
	 * @return Map 甲方密钥的Map
	 */
	private static Map<String, Object> initKey() throws Exception {
		// 实例化密钥生成器
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		// 初始化密钥生成器
		keyPairGenerator.initialize(KEY_SIZE);
		// 生成密钥对
		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		// 甲方公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic(); // 用作验证
		// 甲方私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate(); // 用作签名
		// 将密钥存储在map中
		Map<String, Object> keyMap = new HashMap<String, Object>();
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;

	}

	/**
	 * 执行签名
	 * 
	 * @param data待签名数据
	 * @param privateKey
	 *            密钥
	 * @return byte[] 数字签名
	 */
	private static byte[] sign(byte[] data, byte[] privateKey) throws Exception {

		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 生成私钥
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 实例化Signature
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		// 初始化Signature
		signature.initSign(priKey);
		// 更新
		signature.update(data);
		return signature.sign();
	}

	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            待校验数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 * @return boolean 校验成功返回true，失败返回false
	 */
	private static boolean verify(byte[] data, byte[] publicKey, byte[] sign) throws Exception {
		// 转换公钥材料
		// 实例化密钥工厂
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 初始化公钥
		// 密钥材料转换
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
		// 产生公钥
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
		// 实例化Signature
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		// 初始化Signature
		signature.initVerify(pubKey);
		// 更新
		signature.update(data);
		// 验证

		return signature.verify(sign);
	}

	
	/* ======================= 下面这些方法用于封装上面3个方法，用于直接外部调用  =============================*/
	
	/**
	 * 取得私钥
	 * 
	 * @param keyMap
	 *            密钥map
	 * @return byte[] 私钥
	 */
	private static byte[] getPrivateKey(Map<String, Object> keyMap) {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return key.getEncoded();
	}

	/**
	 * 取得公钥
	 * 
	 * @param keyMap
	 *            密钥map
	 * @return byte[] 公钥
	 */
	private static byte[] getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return key.getEncoded();
	}

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年10月23日 <br>
	 * 描述： 通过加密原文获取 公钥，私钥，签名
	 * 
	 * @param str
	 *            加密原文
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getPublicKeyAndSign(String str) throws Exception {
		// 初始化密钥
		// 生成密钥对
		Map<String, Object> keyMap = RSACoder.initKey();
		// 公钥
		byte[] publicKeyB = RSACoder.getPublicKey(keyMap);
		// 私钥
		byte[] privateKeyB = RSACoder.getPrivateKey(keyMap);

		String publicKey = Base64.encodeBase64String(publicKeyB);
		String privateKey = Base64.encodeBase64String(privateKeyB);

		byte[] signB = RSACoder.sign(str.getBytes(), privateKeyB);
		String sign = Base64.encodeBase64String(signB);

		Map<String, String> signMap = new HashMap<String, String>();
		signMap.put("publicKey", publicKey);
		signMap.put("privateKey", privateKey);
		signMap.put("sign", sign);
		log.info("RSA加密：" + signMap);
		return signMap;
	}

	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            加密原文
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 * @return boolean 校验成功返回true，失败返回false
	 */
	public static boolean verify(String srcStr, String publicKeyStr, String signStr) throws Exception {
		byte[] publicKeyB = Base64.decodeBase64(publicKeyStr);
		byte[] signB = Base64.decodeBase64(signStr);
		byte[] src = srcStr.getBytes();
		try {
			return RSACoder.verify(src, publicKeyB, signB);
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		String str = "11111";
		Map<String, String> signMap = getPublicKeyAndSign(str);

		System.out.println("公钥：" + signMap.get("publicKey"));
		System.out.println("私钥：" + signMap.get("privateKey"));
		System.out.println("签名：" + signMap.get("sign"));

		if (verify(str, signMap.get("publicKey"), signMap.get("sign"))) {
			System.out.println("签名验证成功");
		} else {
			System.out.println("签名验证失败");
		}
	}
}