/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.util.BFutil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.hoomsun.after.api.model.param.BFCertifiedBKParam;
import com.hoomsun.after.api.model.param.BFCertifiedPayCheckParam;
import com.hoomsun.after.api.model.param.BFVerificationModel;
import com.hoomsun.after.api.model.param.BFdeductModel;
import com.hoomsun.after.api.model.param.BfBFCertifiedPayParam;
import com.hoomsun.after.api.model.vo.BFCertifiedBKVo;
import com.hoomsun.after.api.model.vo.BFCertifiedPayVo;
import com.hoomsun.after.api.model.vo.BFdeductResult;
import com.hoomsun.after.api.model.vo.BFverification;
import com.hoomsun.after.api.util.IDGenerator;
import com.hoomsun.after.api.util.BFutil.http.HttpUtil;
import com.hoomsun.after.api.util.BFutil.rsa.RsaCodingUtil;
import com.hoomsun.core.util.PrimaryKeyUtil;

/**
 * 作者：zwLiu <br>
 * 创建时间：2018年3月14日 <br>
 * 描述：此类针对宝付划扣
 */
public class BFdeductUtil {
	/**
	 * 身份表示
	 * 01 身份证
	 */
	private static final String ID_CARD_TYPE = "01";
	/**
	 * 接入类型
	 * 默认 0000
	 */
	private static final String BIZ_TYPE = "0000";
	/**
	 * 安全标识
	 * 默认 2
	 */
	private static final String PAY_CM = "2";
	
	/**
	 * 线上标识
	 */
	private static final String ON_LINE = "1";
	/**
	 * 线下标识
	 */	
	private static final String OFF_LINE = "0";
	/**
	 * 交易子类
	 * 13 划扣
	 * 31查证
	 * 01 直接绑卡
	 * 15 预支付
	 * 16 预支付确认
	 */	
	private static final int BF_SUB_TYPE = 13;
	private static final int BF_CHECK_TYPE = 31;
	private static final int BF_BIND_CARD_TYPE = 01;
	
	private static final int BF_CERTIFIED_TYPE = 15;
	private static final int BF_CERTIFIED_CHECK_TYPE = 16;
	
	
	
	/**
	 * 请求方保留域
	 */
	private static final String REQ_RESERVED = "贷后";
	
	
	private static final Logger LOG = Logger.getLogger(BFdeductUtil.class);
	/**
	 * 
	 * 作者：zwLiu<br>
	 * 创建时间：2018年3月16日 <br>
	 * 描述： 宝付划扣
	 * @param deductModel
	 * @return BFdeductResult
	 * @throws Exception
	 */
	public static BFdeductResult deduct(BFdeductModel deductModel) {
		LOG.info("进入了宝付划扣。");
		// 获取划扣所需参数
		Map<String, Object> bodyPostParam  = new HashMap<String, Object>();
		String trans_id = "TID" + PrimaryKeyUtil.getPrimaryKey();
		bodyPostParam.put("trans_id",  trans_id);
		// 银行卡编码
		bodyPostParam.put("payCode", deductModel.getPayCode());
		// 银行卡卡号
		bodyPostParam.put("accNo", deductModel.getAccNo());
		// 身份证号码
		bodyPostParam.put("idCard", deductModel.getIdCard());
		// 姓名
		bodyPostParam.put("idHolder", deductModel.getIdHolder());
		// 银行预留手机号
		bodyPostParam.put("mobile", deductModel.getMobile());
		//交易日期
		bodyPostParam.put("tradeDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 月还金额
		bodyPostParam.put("txnAmt", deductModel.getTxnAmt());
		//计算手续费
		BigDecimal feesMoney = BFdeductUtil.getFeesMoney(deductModel.getTxnAmt());
		//获取请求头文件
		Map<String, String> headPostParam = BFdeductUtil.getParams(deductModel.getPath(),null,deductModel.getFlag(),BF_SUB_TYPE);
		
		BFdeductResult deductResult =  new BFdeductResult();
		try {
			String XmlOrJson = getXmlOrJson(headPostParam,bodyPostParam);
			Map<String, String> keyMap = getKeyMap(deductModel.getPath(),deductModel.getFlag());
			String base64str = SecurityUtil.Base64Encode(XmlOrJson);
			String pfxpath = keyMap.get("pfxpath");
			String pfxPwd = keyMap.get("pfxPwd");
			String postUrl = keyMap.get("postUrl");
			String cerpath = keyMap.get("cerpath");	
			String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, pfxPwd);
			// 加入请求密文	
			headPostParam.put("data_content", data_content);
			String PostString = HttpUtil.RequestForm(postUrl, headPostParam);
			LOG.info("请求返回：" + PostString);
			PostString = RsaCodingUtil.decryptByPubCerFile(PostString, cerpath);
			if (PostString.isEmpty()) {
				// 判断解密是否正确。如果为空则宝付公钥不正确
				LOG.error("=====检查解密公钥是否正确！");
			}
			PostString = SecurityUtil.Base64Decode(PostString);
			LOG.info("=====返回查询数据解密结果:" + PostString);
			if (headPostParam.get("data_type").equals("xml")) {
				PostString = JXMConvertUtil.XmlConvertJson(PostString);
				LOG.info("=====返回结果转JSON:" + PostString);
			}
			String ReturnStr = null;
			Map<String, Object> ArrayDataString = JXMConvertUtil.JsonConvertHashMap(PostString);// 将JSON转化为Map对象。
			LOG.info("转换为MAP对象：" + ArrayDataString);
			ReturnStr = "返回码：" + ArrayDataString.get("resp_code") + ", 返回消息：" + ArrayDataString.get("resp_msg");
			if (ArrayDataString.get("resp_code").toString().equals("0000")) {
				ReturnStr += ", 成功金额：" + ArrayDataString.get("succ_amt") + "分" + ", 商户订单号：" + ArrayDataString.get("trans_id");
			}
			System.out.println(ReturnStr);
			/**
			 * 划扣结果处理
			 */
			String success = "0000";
			if(success.equals(ArrayDataString.get("resp_code"))){
				deductResult.setDeductStatus("0000");
				deductResult.setDeductDate(getTradeDate(ArrayDataString.get("trade_date")));
				deductResult.setDeductMoney(BFdeductUtil.conversionMoney(new BigDecimal(String.valueOf(ArrayDataString.get("succ_amt")))));
				deductResult.setRespCode(String.valueOf(ArrayDataString.get("resp_code")));
				deductResult.setRespMsg(String.valueOf(ArrayDataString.get("resp_msg")));
				deductResult.setTransId(String.valueOf(ArrayDataString.get("trans_id")));
			}else if (BFdeductUtil.getRespCodeBoolean(ArrayDataString.get("resp_code"))){
				deductResult.setDeductStatus("2222");
				deductResult.setDeductDate(getTradeDate(ArrayDataString.get("trade_date")));			
				deductResult.setDeductMoney(deductModel.getTxnAmt());			
				deductResult.setRespCode(String.valueOf(ArrayDataString.get("resp_code")));
				deductResult.setRespMsg(String.valueOf(ArrayDataString.get("resp_msg")));			
				deductResult.setTransId(String.valueOf(ArrayDataString.get("trans_id")));			
			}
			else{
				deductResult.setDeductStatus("1111");
				deductResult.setDeductDate(getTradeDate(ArrayDataString.get("trade_date")));			
				deductResult.setDeductMoney(deductModel.getTxnAmt());			
				deductResult.setRespCode(String.valueOf(ArrayDataString.get("resp_code")));
				deductResult.setRespMsg(String.valueOf(ArrayDataString.get("resp_msg")));			
				deductResult.setTransId(String.valueOf(ArrayDataString.get("trans_id")));			
			}
			deductResult.setAdditionalInfo(String.valueOf(ArrayDataString.get("additional_info")));
			deductResult.setFeesMoney(feesMoney);
			return deductResult;
			
		} catch (Exception e) {
			deductResult.setDeductStatus("2222");
			deductResult.setDeductDate(new Date());			
			deductResult.setDeductMoney(deductModel.getTxnAmt());			
			deductResult.setRespMsg("请求异常！");		
			deductResult.setTransId(trans_id);			
			e.printStackTrace();
			return deductResult;
		}
		}
	

	/**
	 * 
	 * 作者：zwLiu<br>
	 * 创建时间：2018年3月16日 <br>
	 * 描述：宝付查证
	 * @return 
	 * @throws Exception 
	 */
	public static BFverification verification(BFVerificationModel verificationModel) {		
		Map<String, String> HeadPostParam = BFdeductUtil.getParams(verificationModel.getPath(),verificationModel.getBfzcFlag(),verificationModel.getFlag(),BF_CHECK_TYPE);
		Map<String, Object> XMLArray = new HashMap<String, Object>();
		// 接入类型
		XMLArray.put("biz_type", BIZ_TYPE);
		XMLArray.put("orig_trans_id", verificationModel.getOrigTransId());
		XMLArray.put("orig_trade_date",  verificationModel.getOrigTradeDate());
		XMLArray.put("txn_sub_type", HeadPostParam.get("txn_sub_type"));
		XMLArray.put("terminal_id", HeadPostParam.get("terminal_id"));
		XMLArray.put("member_id", HeadPostParam.get("member_id"));
		XMLArray.put("trans_serial_no", BFdeductUtil.getNextID("TID"));
		XMLArray.put("additional_info", "查证");
		XMLArray.put("req_reserved", "贷后");	
		// 获取密钥、请求地址
		Map<String, String> keyMap = BFdeductUtil.getKeyMap(verificationModel.getPath(),verificationModel.getFlag());
		// 商户私钥
		String pfxpath = keyMap.get("pfxpath");
		// 宝付公钥
		String cerpath = keyMap.get("cerpath");
		String XmlOrJson = "";
		Map<String, Object> ArrayDataString = null;
		BFverification verification  = new BFverification();
		try {
			if (HeadPostParam.get("data_type").equals("xml")) {
				Map<Object, Object> ArrayToObj = new HashMap<Object, Object>();
				ArrayToObj.putAll(XMLArray);
				XmlOrJson = MapToXml.Coverter(ArrayToObj, "data_content");
			} else {
				JSONObject jsonObjectFromMap = JSONObject.fromObject(XMLArray);
				XmlOrJson = jsonObjectFromMap.toString();
			}
			LOG.info("请求参数：" + XmlOrJson);

			String base64str = SecurityUtil.Base64Encode(XmlOrJson);		
			String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, keyMap.get("pfxPwd"));
			HeadPostParam.put("data_content", data_content);// 加入请求密文

			String PostString = HttpUtil.RequestForm(keyMap.get("postUrl"), HeadPostParam);
			LOG.info("请求返回：" + PostString);
			PostString = RsaCodingUtil.decryptByPubCerFile(PostString, cerpath);
			if (PostString.isEmpty()) {// 判断解密是否正确。如果为空则宝付公钥不正确
				LOG.error("=====检查解密公钥是否正确！");
			}
			PostString = SecurityUtil.Base64Decode(PostString);
			LOG.info("=====返回查询数据解密结果:" + PostString);
			if (HeadPostParam.get("data_type").equals("xml")) {
				PostString = JXMConvertUtil.XmlConvertJson(PostString);
				LOG.info("=====返回结果转JSON:" + PostString);
			}

			ArrayDataString = JXMConvertUtil.JsonConvertHashMap(PostString);
			LOG.info("转换为MAP对象：" + ArrayDataString);
		
		/**
		 * 结果处理
		 * S：交易成功
		 * F：交易失败
		 * I：处理中
		 * FF：交易失败；订单暂不存在
		 */

			//交易状态
			String orderStat = String.valueOf(ArrayDataString.get("order_stat"));
			//原交易订单号
			String origTransId = String.valueOf(ArrayDataString.get("orig_trans_id"));
			//原交易订单日期
			Date origTradeDate = BFdeductUtil.getTradeDate(ArrayDataString.get("orig_trade_date"));
			//应答码
			String respCode = String.valueOf(ArrayDataString.get("resp_code"));
			//应答信息
			String respMsg = String.valueOf(ArrayDataString.get("resp_msg"));
			
			if("S".equalsIgnoreCase(orderStat)){
				//成功金额
				String succAmt = String.valueOf(ArrayDataString.get("succ_amt"));
				//交易状态
				verification.setOrderStat("0000");
				//原交易时间
				verification.setOrigTradeDate(origTradeDate);
				//原交易订单号
				verification.setOrigTransId(origTransId);
				//交易金额
				verification.setSuccAmt(new BigDecimal(succAmt));
				//应答码
				verification.setRespCode(respCode);
				//应答信息
				verification.setRespMsg(respMsg);
				//查证时间
				verification.setCheckDate(new Date());
				
				return verification;
			}else if("F".equalsIgnoreCase(orderStat) || "FF".equalsIgnoreCase(orderStat)){
				//交易状态
				verification.setOrderStat("1111");
				//原交易时间
				verification.setOrigTradeDate(origTradeDate);
				//原交易订单号
				verification.setOrigTransId(origTransId);
				//应答码
				verification.setRespCode(respCode);
				//应答信息
				verification.setRespMsg(respMsg);
				//查证时间
				verification.setCheckDate(new Date());
				return verification;
				
				
			}else if("I".equalsIgnoreCase(orderStat)){
				//交易状态
				verification.setOrderStat("2222");
				//原交易时间
				verification.setOrigTradeDate(origTradeDate);
				//原交易订单号
				verification.setOrigTransId(origTransId);
				//应答码
				verification.setRespCode(respCode);
				//应答信息
				verification.setRespMsg(respMsg);
				//查证时间
				verification.setCheckDate(new Date());
				return verification;				
				
			}
		} catch (Exception e) {
			//交易状态
			verification.setOrderStat("2222");
			//订单号
			verification.setOrigTransId(verificationModel.getOrigTransId());
			//应答信息
			verification.setRespMsg("请求超时！");
			//查证时间
			verification.setCheckDate(new Date());
			e.printStackTrace();
			return verification;			
			
		}
		return null;
	}	
	
	/**
	 * 
	 * 作者：zwLiu <br>
	 * 创建时间：2018年5月14日 <br>
	 * 描述：宝付认证支付，绑卡 
	 * @param bFCertifiedBKParam
	 */
	public static BFCertifiedBKVo BfBindCard(BFCertifiedBKParam bFCertifiedBKParam){
		LOG.info("进入了绑卡操作");
		LOG.info("=======直接绑卡接口01=========");
		LOG.info("姓名" + bFCertifiedBKParam.getCustName());
		LOG.info("身份证号" + bFCertifiedBKParam.getIdCard());
		LOG.info("配置文件路径" + bFCertifiedBKParam.getPath());
		
		BFCertifiedBKVo bFCertifiedBKVo = new BFCertifiedBKVo();
		
		// 获取划扣所需参数
		Map<String, Object> bodyPostParam  = new HashMap<String, Object>();
		//银行卡编码
		String pay_code = bFCertifiedBKParam.getBankCode();
		//银行卡卡号
        String acc_no = bFCertifiedBKParam.getBankAccount();
        //身份证号码
        String id_card = bFCertifiedBKParam.getIdCard();
        //姓名
        String id_holder = bFCertifiedBKParam.getCustName();
        //银行预留手机号
        String mobile = bFCertifiedBKParam.getPhoneNumber();
        
		String trans_id = "TID" + PrimaryKeyUtil.getPrimaryKey();
		
		String path = bFCertifiedBKParam.getPath();
		String flag = bFCertifiedBKParam.getFlag();
        Map<String, String> headPostParam = BFdeductUtil.getParams(path,flag,"1",BF_BIND_CARD_TYPE);        

        //准备加密数据
        bodyPostParam.put("txn_sub_type", headPostParam.get("txn_sub_type"));
        bodyPostParam.put("biz_type", BIZ_TYPE);
        bodyPostParam.put("terminal_id", headPostParam.get("terminal_id"));
        bodyPostParam.put("member_id", headPostParam.get("member_id"));
        bodyPostParam.put("trans_serial_no", BFdeductUtil.getNextID("TID"));
        bodyPostParam.put("trans_id", trans_id);
        bodyPostParam.put("acc_no", acc_no);
        bodyPostParam.put("id_card_type", "01");
        bodyPostParam.put("id_card", id_card);
        bodyPostParam.put("id_holder", id_holder);
        bodyPostParam.put("mobile", mobile);
        bodyPostParam.put("trade_date", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        bodyPostParam.put("additional_info", "绑卡");
        bodyPostParam.put("req_reserved", "绑卡操作");
		String XmlOrJson = "";
		try {
			if (headPostParam.get("data_type").equals("xml")) {
				Map<Object, Object> ArrayToObj = new HashMap<Object, Object>();
				ArrayToObj.putAll(bodyPostParam);
				XmlOrJson = MapToXml.Coverter(ArrayToObj, "data_content");
			} else {
				JSONObject jsonObjectFromMap = JSONObject.fromObject(bodyPostParam);
				XmlOrJson = jsonObjectFromMap.toString();
			}
			
			
			
			Map<String, String> keyMap = getKeyMap(path,flag);
			String base64str = SecurityUtil.Base64Encode(XmlOrJson);
			String pfxpath = keyMap.get("pfxpath");
			String pfxPwd = keyMap.get("pfxPwd");
			String postUrl = keyMap.get("postUrl");
			String cerpath = keyMap.get("cerpath");
			String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, pfxPwd);
			// 加入请求密文	
			headPostParam.put("data_content", data_content);
			String PostString = HttpUtil.RequestForm(postUrl, headPostParam);
			if (PostString.isEmpty()) {
				// 判断解密是否正确。如果为空则宝付公钥不正确
				LOG.error("=====检查解密公钥是否正确！");
			}
			PostString = SecurityUtil.Base64Decode(PostString);
			LOG.info("=====返回查询数据解密结果:" + PostString);
			if (headPostParam.get("data_type").equals("xml")) {
				PostString = JXMConvertUtil.XmlConvertJson(PostString);
				LOG.info("=====返回结果转JSON:" + PostString);
			}
			String ReturnStr = null;
			Map<String, Object> ArrayDataString = JXMConvertUtil.JsonConvertHashMap(PostString);// 将JSON转化为Map对象。
			LOG.info("转换为MAP对象：" + ArrayDataString);
			 String  resp_code = "0000";
			 if(resp_code.equals(String.valueOf(ArrayDataString.get("resp_code"))) ){
				 bFCertifiedBKVo.setOrderNo(String.valueOf(ArrayDataString.get("trans_id")));
				 bFCertifiedBKVo.setBindStatus("0");
				 bFCertifiedBKVo.setBindId(String.valueOf(ArrayDataString.get("bind_id")));			 
				 bFCertifiedBKVo.setStatusMsg(String.valueOf(ArrayDataString.get("resp_msg")));
				 return bFCertifiedBKVo;
			 }else{
				 bFCertifiedBKVo.setOrderNo(String.valueOf(ArrayDataString.get("trans_id")));
				 bFCertifiedBKVo.setBindStatus("-1");
				 bFCertifiedBKVo.setStatusMsg(String.valueOf(ArrayDataString.get("resp_msg")));			 
				 return bFCertifiedBKVo;
			 }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			 bFCertifiedBKVo.setOrderNo(trans_id);
			 bFCertifiedBKVo.setBindStatus("-1");
			 bFCertifiedBKVo.setStatusMsg("请求异常！");			 
			 LOG.error("密文处理异常！");
			 return bFCertifiedBKVo;			
		} catch (IOException e) {
			e.printStackTrace();
			 bFCertifiedBKVo.setOrderNo(trans_id);
			 bFCertifiedBKVo.setBindStatus("-1");
			 bFCertifiedBKVo.setStatusMsg("请求异常！");			 
			 LOG.error("密文处理异常！");
			 return bFCertifiedBKVo;			
		} catch (Exception e) {
			 bFCertifiedBKVo.setOrderNo(trans_id);
			 bFCertifiedBKVo.setBindStatus("-1");
			 bFCertifiedBKVo.setStatusMsg("请求异常！");			 
			 e.printStackTrace();
			 return bFCertifiedBKVo;			
		}
		
		
        
	}
	/**
	 * 
	 * 作者：zwLiu <br>
	 * 创建时间：2018年5月14日 <br>
	 * 描述： 宝付认证支付
	 * @return
	 */
	public static BFCertifiedPayVo BfBFCertifiedPay(BfBFCertifiedPayParam bfBFCertifiedPay){
		String path = bfBFCertifiedPay.getPath();
		BFCertifiedPayVo bfCertifiedPayVo = new BFCertifiedPayVo();
		//金额      
		BigDecimal txnAmt =  bfBFCertifiedPay.getTxnAmt();
		//bindId
		String bind_id = bfBFCertifiedPay.getBindId();
		//风控参数
		String client_ip = bfBFCertifiedPay.getClientIp();
		String flag = bfBFCertifiedPay.getFlag();
        Map<String, String> headPostParam = BFdeductUtil.getParams(path,flag,"1",BF_CERTIFIED_TYPE);

        Map<String,Object> bodyPostParam = new HashMap<String,Object>();
        
        bodyPostParam.put("txn_sub_type", headPostParam.get("txn_sub_type"));
        bodyPostParam.put("biz_type", "0000");
        bodyPostParam.put("terminal_id", headPostParam.get("terminal_id"));
        bodyPostParam.put("member_id", headPostParam.get("member_id"));
     
        Map<String,String> ClientIp = new HashMap<String,String>();
        ClientIp.put("client_ip", bfBFCertifiedPay.getClientIp()); //风控参数
        String trade_date=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());//交易日期      
        
        bodyPostParam.put("bind_id",bind_id);        
        bodyPostParam.put("trans_id", "CZID"+PrimaryKeyUtil.getPrimaryKey());     
        bodyPostParam.put("risk_content", ClientIp);        
        bodyPostParam.put("txn_amt", txnAmt.multiply(BigDecimal.valueOf(100)));//金额以分为单位(整型数据)并把元转换成分         
        bodyPostParam.put("trans_serial_no", IDGenerator.getNextID("CZLS"));
        bodyPostParam.put("trade_date", trade_date);
        bodyPostParam.put("additional_info", "APP客户充值");
        bodyPostParam.put("req_reserved", "保留");

        
		String XmlOrJson = "";

		try {
			if (headPostParam.get("data_type").equals("xml")) {
				Map<Object, Object> ArrayToObj = new HashMap<Object, Object>();
				ArrayToObj.putAll(bodyPostParam);
				XmlOrJson = MapToXml.Coverter(ArrayToObj, "data_content");
			} else {
				JSONObject jsonObjectFromMap = JSONObject.fromObject(bodyPostParam);
				XmlOrJson = jsonObjectFromMap.toString();
			}          
			
			
			Map<String, String> keyMap = getKeyMap(path,flag);
			String base64str = SecurityUtil.Base64Encode(XmlOrJson);
			String pfxpath = keyMap.get("pfxpath");
			String pfxPwd = keyMap.get("pfxPwd");
			String postUrl = keyMap.get("postUrl");
			String cerpath = keyMap.get("cerpath");
			String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, pfxPwd);		
			// 加入请求密文	
			headPostParam.put("data_content", data_content);
			String PostString = HttpUtil.RequestForm(postUrl, headPostParam);
			if (PostString.isEmpty()) {
				// 判断解密是否正确。如果为空则宝付公钥不正确
				LOG.error("=====检查解密公钥是否正确！");
			}
			PostString = SecurityUtil.Base64Decode(PostString);
			LOG.info("=====返回查询数据解密结果:" + PostString);
			if (headPostParam.get("data_type").equals("xml")) {
				PostString = JXMConvertUtil.XmlConvertJson(PostString);
				LOG.info("=====返回结果转JSON:" + PostString);
			}
			String ReturnStr = null;
			Map<String, Object> ArrayDataString = JXMConvertUtil.JsonConvertHashMap(PostString);// 将JSON转化为Map对象。
			LOG.info("转换为MAP对象：" + ArrayDataString);
			 String resp_code = "0000";
			 //交易成功
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			 if(resp_code.equals(String.valueOf(ArrayDataString.get("resp_code")))){
				 bfCertifiedPayVo.setDeductStatus("0000");
				 //订单交易时间
				 String  tradeDate = String.valueOf(ArrayDataString.get("trade_date"));
				 //商户订单号
				 String transId = String.valueOf(ArrayDataString.get("trans_id"));
				 //宝付业务流水号
				 String businessNo = String.valueOf(ArrayDataString.get("business_no"));
				 //
				 String statusMsg = String.valueOf(ArrayDataString.get("resp_msg"));
				 
				 String additionalInfo = String.valueOf(ArrayDataString.get("additional_info"));
				 
				 
				 
				 bfCertifiedPayVo.setDeductDate(sdf.parse(tradeDate));
				 bfCertifiedPayVo.setStatusMsg(statusMsg);
				 bfCertifiedPayVo.setOrderNo(String.valueOf(transId));
				 bfCertifiedPayVo.setBusinessNo(businessNo);
				 bfCertifiedPayVo.setAdditionalInfo(additionalInfo);
				 return bfCertifiedPayVo;
			 }else{
				 String additionalInfo = String.valueOf(ArrayDataString.get("additional_info"));				 
				 String statusMsg = String.valueOf(ArrayDataString.get("resp_msg"));	
				 bfCertifiedPayVo.setDeductDate(new Date());
				 bfCertifiedPayVo.setDeductStatus("1111");
				 bfCertifiedPayVo.setStatusMsg(statusMsg);
				 bfCertifiedPayVo.setAdditionalInfo(additionalInfo);				 
				 return bfCertifiedPayVo;
			 }
		} catch (UnsupportedEncodingException e) {
			LOG.error("密文处理异常！");
			e.printStackTrace();
		} catch (IOException e) {
			LOG.error("密文处理异常！");
			e.printStackTrace();
		} catch (ParseException e) {
			LOG.error("日期处理异常！");
			e.printStackTrace();
		} catch (Exception e) {
			 String statusMsg = String.valueOf("请求超时！");	
			 bfCertifiedPayVo.setDeductStatus("1111");
			 bfCertifiedPayVo.setStatusMsg(statusMsg);
			e.printStackTrace();
			return bfCertifiedPayVo;			
		}
		return null;
		
        
		
	}
	/**
	 * 
	 * 作者：zwLiu <br>
	 * 创建时间：2018年5月15日 <br>
	 * 描述：确认支付 
	 * @param mapParam
	 * @return
	 */
	public static BFCertifiedPayVo BfBFCertifiedPayCheck(BFCertifiedPayCheckParam  bfCertifiedPayCheck){
		LOG.info("=======确认支付16接口=========");
		LOG.info("传入参数：" + bfCertifiedPayCheck.toString());
		
		String path = bfCertifiedPayCheck.getPath();
		
		BFCertifiedPayVo bfCertifiedPayVo = new BFCertifiedPayVo();		
		
		String sms_code = String.valueOf(bfCertifiedPayCheck.getSmsCode());//支付短信验证码
    	String business_no = String.valueOf(bfCertifiedPayCheck.getBusinessNo());//宝付业务流水号
    	if(business_no.isEmpty()){
    		bfCertifiedPayVo.setDeductStatus("-1");
    		bfCertifiedPayVo.setStatusMsg("业务流水号为空!!");
    		return bfCertifiedPayVo;
    	}
		String flag = bfCertifiedPayCheck.getFlag();
        Map<String, String> headPostParam = BFdeductUtil.getParams(path,flag,"1",BF_CERTIFIED_CHECK_TYPE);
    	
        String trade_date=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());//交易日期        
        Map<String,Object> bodyPostParam = new HashMap<String,Object>();
        bodyPostParam.put("txn_sub_type", headPostParam.get("txn_sub_type"));
        bodyPostParam.put("biz_type", "0000");
        bodyPostParam.put("terminal_id", headPostParam.get("terminal_id"));
        bodyPostParam.put("member_id", headPostParam.get("member_id"));        
        bodyPostParam.put("sms_code", sms_code);
        bodyPostParam.put("business_no", business_no); 
        bodyPostParam.put("trans_serial_no",  IDGenerator.getNextID("CZSN"));
        bodyPostParam.put("trade_date", trade_date);
        bodyPostParam.put("additional_info", "确认交易");
        bodyPostParam.put("req_reserved", "保留");  
		String XmlOrJson = "";

			try {
				if (headPostParam.get("data_type").equals("xml")) {
					Map<Object, Object> ArrayToObj = new HashMap<Object, Object>();
					ArrayToObj.putAll(bodyPostParam);
					XmlOrJson = MapToXml.Coverter(ArrayToObj, "data_content");
				} else {
					JSONObject jsonObjectFromMap = JSONObject.fromObject(bodyPostParam);
					XmlOrJson = jsonObjectFromMap.toString();
				}     	
				Map<String, String> keyMap = getKeyMap(path,flag);
				String base64str = SecurityUtil.Base64Encode(XmlOrJson);
				String pfxpath = keyMap.get("pfxpath");
				String pfxPwd = keyMap.get("pfxPwd");
				String postUrl = keyMap.get("postUrl");
				String cerpath = keyMap.get("cerpath");
				String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, pfxPwd);		
				// 加入请求密文	
				headPostParam.put("data_content", data_content);
				String PostString = HttpUtil.RequestForm(postUrl, headPostParam);
				if (PostString.isEmpty()) {
					// 判断解密是否正确。如果为空则宝付公钥不正确
					LOG.error("=====检查解密公钥是否正确！");
				}
				PostString = SecurityUtil.Base64Decode(PostString);
				LOG.info("=====返回查询数据解密结果:" + PostString);
				if (headPostParam.get("data_type").equals("xml")) {
					PostString = JXMConvertUtil.XmlConvertJson(PostString);
					LOG.info("=====返回结果转JSON:" + PostString);
				}
				String ReturnStr = null;
				Map<String, Object> ArrayDataString = JXMConvertUtil.JsonConvertHashMap(PostString);// 将JSON转化为Map对象。
				LOG.info("转换为MAP对象：" + ArrayDataString);
				 String resp_code = "0000";
				 //交易成功
				 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				 
				 if(resp_code.equals(String.valueOf(ArrayDataString.get("resp_code")))){
					 bfCertifiedPayVo.setDeductStatus("0000");
					 bfCertifiedPayVo.setStatusMsg(String.valueOf(ArrayDataString.get("resp_msg")));
					 BigDecimal succAmt = 	new BigDecimal(String.valueOf(ArrayDataString.get("succ_amt")));
					 bfCertifiedPayVo.setDeductMoney(succAmt.divide(new BigDecimal("100")));
					 bfCertifiedPayVo.setOrderNo(String.valueOf(ArrayDataString.get("trans_id")));
					 bfCertifiedPayVo.setDeductDate(sdf.parse(String.valueOf(ArrayDataString.get("trade_date"))));
					 return bfCertifiedPayVo;
				 }			
				 	/*
					 * 需查证状态码 BF00100 BF00112 BF00113 BF00115 BF00144 BF00202
					 */
					else if (BFdeductUtil.getRespCodeBoolean(ArrayDataString.get("resp_code"))) {
						 
						bfCertifiedPayVo.setDeductStatus("2222");
						bfCertifiedPayVo.setStatusMsg(String.valueOf(ArrayDataString.get("resp_msg")));				
						bfCertifiedPayVo.setOrderNo(String.valueOf(ArrayDataString.get("trans_id")));				
						bfCertifiedPayVo.setDeductDate(sdf.parse(String.valueOf(ArrayDataString.get("trade_date"))));
						
						bfCertifiedPayVo.setDeductDate(sdf.parse(String.valueOf(ArrayDataString.get("additional_info"))));
						
						
						return bfCertifiedPayVo;
						
					}else{
						bfCertifiedPayVo.setDeductStatus("1111");
						bfCertifiedPayVo.setStatusMsg(String.valueOf(ArrayDataString.get("resp_msg")));
						bfCertifiedPayVo.setOrderNo(String.valueOf(ArrayDataString.get("trans_id")));
						bfCertifiedPayVo.setDeductDate(sdf.parse(String.valueOf(ArrayDataString.get("trade_date"))));
						bfCertifiedPayVo.setDeductDate(sdf.parse(String.valueOf(ArrayDataString.get("additional_info"))));
						return bfCertifiedPayVo;
					 }
			} catch (UnsupportedEncodingException e) {
				LOG.info("密文处理异常！");
				e.printStackTrace();
			} catch (IOException e) {
				LOG.info("密文处理异常！");
				e.printStackTrace();
			} catch (ParseException e) {
				LOG.info("日期处理异常！");
				e.printStackTrace();
			} catch (Exception e) {
				bfCertifiedPayVo.setDeductStatus("2222");
				bfCertifiedPayVo.setStatusMsg("请求异常!");				
				bfCertifiedPayVo.setOrderNo(bfCertifiedPayCheck.getOrderNo());				
				bfCertifiedPayVo.setDeductDate(new Date());
				e.printStackTrace();
				return bfCertifiedPayVo;			
			}
		return null;
	}
	
	
	
	
	
	/**
	 * 
	 * 作者：zwLiu<br>
	 * 创建时间：2018年3月16日 <br>
	 * 描述： 判断状态码是否是查证
	 * @param object
	 * @return
	 */
	private static boolean getRespCodeBoolean(Object respCode) {		
		List<String> checkLis = new ArrayList<String>();
		checkLis.add("BF00100");
		checkLis.add("BF00112");
		checkLis.add("BF00113");
		checkLis.add("BF00115");
		checkLis.add("BF00144");
		checkLis.add("BF00202");
		
		return checkLis.contains(String.valueOf(respCode)); 
	}
	/**
	 * 
	 * 作者：zwLiu<br>
	 * 创建时间：2018年3月16日 <br>
	 * 描述： 时间格式化
	 * @param object
	 * @return
	 * @throws ParseException 
	 */
	private static Date getTradeDate(Object tradeDate){
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			return sdf.parse(String.valueOf(tradeDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * 作者：zwLiu<br>
	 * 创建时间：2018年3月16日 <br>
	 * 描述： 密钥是否存在
	 * @param path
	 * @param flag
	 * @return
	 */
	private static Map<String, String> getKeyMap(String path, String flag) {
		//获取密钥
		Map<String, String> keyMap = BFdeductUtil.getKeyPath(path,flag);
		// 判断公私钥文件是否存在
		File pfxfile = new File(keyMap.get("pfxpath"));
		if (!pfxfile.exists()) {
			LOG.error("=====私钥文件不存在！======");
			System.out.println("=====私钥文件不存在！======");
		}
		File cerfile = new File(keyMap.get("cerpath"));
		if (!cerfile.exists()) {// 判断宝付公钥是否为空
			LOG.error("=====公钥文件不存在！======");
			System.out.println("=====公钥文件不存在！======");
		}
		return keyMap;
	}
	/**
	 * 
	 * 作者：zwLiu<br>
	 * 创建时间：2018年3月14日 <br>
	 * 描述： 获取密钥
	 * @param path
	 * @return
	 */
	private static Map<String, String> getKeyPath(String path,String flag) {
		LOG.info("获取公私钥路径，加载app.properties......");
		Map<String, String> keyPathMap = new HashMap<String, String>();
		// 加载
		Properties prop = new Properties();
		try {
			InputStream in =  new BufferedInputStream(new FileInputStream(path + "/app.properties"));
			prop.load(in);
			//获取请求路径
			keyPathMap.put("postUrl", prop.getProperty("post.url").trim()); 
			String pfxpath = null;
			String cerpath = null;
			String pfxPwd = null;
			if(OFF_LINE.equals(flag)){
				//获取私钥路径
				 pfxpath = path + "/CER/" + prop.getProperty("offpfx.name").trim(); 
				//获取公钥路径
				 cerpath = path + "/CER/" +prop.getProperty("offcer.name").trim();
				//获取私钥密码
				pfxPwd = prop.getProperty("offpfx.pwd").trim(); 				
			}else if(ON_LINE.equals(flag)){
				//获取私钥路径
				 pfxpath = path + "/CER/" + prop.getProperty("onpfx.name").trim(); 
				//获取公钥路径
				 cerpath = path + "/CER/" +prop.getProperty("oncer.name").trim();
				//获取私钥密码
				pfxPwd = prop.getProperty("onpfx.pwd").trim(); 				
			}
			keyPathMap.put("pfxpath", pfxpath);
			keyPathMap.put("cerpath", cerpath);
			keyPathMap.put("pfxPwd", pfxPwd);
			
		} catch (IOException e) {
			LOG.error("获取公私钥路径，加载app.properties出错！！");
			e.printStackTrace();
		}
		//获取公私钥路径
		
		return keyPathMap;
	}	

	/**
	 * 
	 * 作者：zwLiu <br>
	 * 创建时间：2018年3月16日 <br>
	 * 描述： 获取请求参数
	 * @param headPostParam
	 * @param bodyPostParam
	 * @return
	 * @throws Exception 
	 */
	private static String getXmlOrJson(Map<String, String> headPostParam, Map<String, Object> bodyPostParam) throws Exception {
		Map<String, String> XMLArray = new HashMap<String, String>();
		XMLArray.put("biz_type", BIZ_TYPE);
		XMLArray.put("pay_cm", PAY_CM);
		XMLArray.put("id_card_type", ID_CARD_TYPE);
		XMLArray.put("req_reserved", REQ_RESERVED);	
		XMLArray.put("txn_sub_type", headPostParam.get("txn_sub_type"));
		XMLArray.put("terminal_id", headPostParam.get("terminal_id"));
		XMLArray.put("member_id", headPostParam.get("member_id"));
		// 支付金额转换成分
		BigDecimal txnAmt = new BigDecimal(String.valueOf(bodyPostParam.get("txnAmt")));
		String txn_amt = String.valueOf(txnAmt.multiply(BigDecimal.valueOf(100)).setScale(0));
		XMLArray.put("txn_amt",txn_amt);

		XMLArray.put("additional_info", headPostParam.get("additional_info"));
		XMLArray.put("pay_code",String.valueOf(bodyPostParam.get("payCode")));
		XMLArray.put("acc_no",String.valueOf(bodyPostParam.get("accNo"))  );
		XMLArray.put("id_card", String.valueOf(bodyPostParam.get("idCard")));
		XMLArray.put("id_holder", String.valueOf(bodyPostParam.get("idHolder")));
		XMLArray.put("mobile",String.valueOf(bodyPostParam.get("mobile") ));
		XMLArray.put("trade_date",String.valueOf(bodyPostParam.get("tradeDate")));
		XMLArray.put("trans_id", String.valueOf(bodyPostParam.get("trans_id")));
		XMLArray.put("trans_serial_no", BFdeductUtil.getNextID("TID"));
		
		String XmlOrJson = "";
		if (headPostParam.get("data_type").equals("xml")) {
			Map<Object, Object> ArrayToObj = new HashMap<Object, Object>();
			ArrayToObj.putAll(XMLArray);
			XmlOrJson = MapToXml.Coverter(ArrayToObj, "data_content");
		} else {
			JSONObject jsonObjectFromMap = JSONObject.fromObject(XMLArray);
			XmlOrJson = jsonObjectFromMap.toString();
		}
		LOG.info("请求参数：" + XmlOrJson);	
		return XmlOrJson;
	}
	/**
	 * 
	 * 作者：zwLiu<br>
	 * 创建时间：2018年3月14日 <br>
	 * 描述： 加载划扣配置文件
	 * @param path
	 * @return
	 */
	private static Map<String,String> getParams(String path,String flag,String bfczFlag, int type){				
		LOG.info("加载app.properties......");
		Map<String,String> HeadPostParam = new HashMap<String,String>();        
		//加载
		Properties prop =  new  Properties();
        try {	  
		InputStream in =  new BufferedInputStream(new FileInputStream(path + "/app.properties"));
		  prop.load(in);
	      HeadPostParam.put("version",  prop.getProperty("version").trim());
	      HeadPostParam.put("txn_type", prop.getProperty("txn.type").trim());		  
	      HeadPostParam.put("data_type", prop.getProperty("data.type").trim());	  
	      HeadPostParam.put("txn_sub_type", String.valueOf(type));		  

		  if(OFF_LINE.equals(flag)){
		      HeadPostParam.put("member_id", prop.getProperty("offmember.id").trim());
		      HeadPostParam.put("terminal_id", prop.getProperty("offterminal.id").trim());
		      HeadPostParam.put("additional_info", "线下宝付");
		      
		  }else if(ON_LINE.equals(flag)){
		      if(bfczFlag != null && "1".equals(bfczFlag)){
			      HeadPostParam.put("member_id", prop.getProperty("onczmember.id").trim());
			      HeadPostParam.put("terminal_id", prop.getProperty("onczterminal.id").trim());
			      HeadPostParam.put("additional_info", "线上宝付认证支付");		    	  
		      }else{
			      HeadPostParam.put("member_id", prop.getProperty("onmember.id").trim());
			      HeadPostParam.put("terminal_id", prop.getProperty("onterminal.id").trim());
			      HeadPostParam.put("additional_info", "线上宝付");		    	  
		      }			  


		  }
		} catch (IOException e) {
			LOG.error("加载app.properties出错！！");
			e.printStackTrace();
		}	        
		return HeadPostParam;		
	}

	/**
	 * 
	 * 作者：zwLiu<br>
	 * 创建时间：2018年3月16日 <br>
	 * 描述： 获取流水号
	 * @return
	 */
	private static String getNextID(String sign) {

		int i =  (int) (100 + Math.random()*(900-1+1));
		String id = sign + String.valueOf(new Date().getTime()) + i;
		return id;
	}
	/**
	 * 
	 * 作者：zwLiu <br>
	 * 创建时间：2018年3月15日 <br>
	 * 描述： 分转元
	 * @param args
	 */
	private static BigDecimal conversionMoney(BigDecimal deductMoney){
		
		return deductMoney.divide(new BigDecimal(100));
		
	}
	/**
	 * 
	 * 作者：zwliu<br>
	 * 创建时间：2018年3月19日 <br>
	 * 描述： 计算划扣手续费
	 * @param args
	 */
	private static BigDecimal getFeesMoney(BigDecimal money) {
		BigDecimal feesMoney = null;
		if(money !=null && (money.compareTo(new BigDecimal("0"))  > 0)){
			if(money.compareTo(new BigDecimal("2000")) <= 0 ){
				feesMoney = new BigDecimal("2");
			}else if(money.compareTo(new BigDecimal("2000")) > 0 ){
				feesMoney = money.multiply(new BigDecimal("0.001"));
			}
			return feesMoney;
			
		}
		
		
		
		return money;
	
		
	}
	
	
	public static void main(String[] args) {
		
		
		
		
		
		
		
		
		BFdeductModel bFdeductModel = new BFdeductModel();
		bFdeductModel.setAccNo("6228480444455553334");
		bFdeductModel.setFlag("1");
		bFdeductModel.setIdCard("320301198502169142");
		bFdeductModel.setIdHolder("孙宝");
		bFdeductModel.setMobile("15502990369");
		bFdeductModel.setPath("E:\\key\\bf");
		bFdeductModel.setPayCode("ABC");
		bFdeductModel.setTxnAmt(new BigDecimal("0"));
/**
 * 查证
 */
//		BFVerificationModel verificationModel = new BFVerificationModel();
//		verificationModel.setFlag("1");
//		verificationModel.setOrigTransId("TIDe50e907cefb2431990e26208f2e774f");
//		verificationModel.setOrigTradeDate("20180319093821");
//		verificationModel.setPath("E:\\key\\bf");

		
		
		
		
		
		try {
			System.out.println(BFdeductUtil.deduct(bFdeductModel).toString());
			//BFdeductUtil.verification(verificationModel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
