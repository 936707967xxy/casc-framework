package com.hoomsun.after.api.util.FYutil;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.hoomsun.after.api.model.param.FYProjectEntryParam;
import com.hoomsun.after.api.model.param.FYdeductModel;
import com.hoomsun.after.api.model.param.FyQrytransParam;
import com.hoomsun.after.api.model.vo.DeductResult;
import com.hoomsun.after.api.util.IDGenerator;
import com.hoomsun.after.api.util.FYutil.http.HttpUtil;
import com.hoomsun.common.model.Json;
import com.hoomsun.core.util.PrimaryKeyUtil;

/**
 * 作者：zwLiu <br>
 * 创建时间：2018年4月23日 <br>
 * 描述：此类针对富友划扣（认证支付、网关支付、快捷支付）
 *
 */
public class FYdeductUtil {

	//测试
	private static final String MERID = "0002900F0345142";
	private static final String VER = "2.0";
	
	//测试项目录入
	private static final String PROJECT_ENTRY = "https://fht-test.fuiou.com/fuMer/inspro.do";
	//测试代收
	private static final String  DEDUCTURL = "https://fht-test.fuiou.com/fuMer/req.do";
	//测试5要素
	private static final String  ELEMENTS_5_URL = "https://fht-test.fuiou.com/fuMer/api_contract5.do";
	private static final String  ELEMENTS_5_MSG_URL = "https://fht-test.fuiou.com/fuMer/api_verifyMsg.do";
	//正式5要素
	//private static final String  ELEMENTS_5_URL = "https://fht.fuiou.com/api_contractAPP.do";
	//测试交易查询
    private static final String QRYTRANS_URL = "https://fht-test.fuiou.com/fuMer/req.do";
	//测试密钥
	private static final String KEY = "123456";
	
	private static final Logger LOG = Logger.getLogger(FYdeductUtil.class);
	
	/**
	 * 
	 * 作者：zwLiu <br>
	 * 创建时间：2018年4月26日 <br>
	 * 描述：富友项目录入
	 */
	public static Map<String, String> FyProhectEntry(FYProjectEntryParam fyProjectEntry){
		LOG.info("项目录入：" + fyProjectEntry.toString());
		String UpdownStatus = fyProjectEntry.getUpdownStatus();
		Map<String, String> retMap = new HashMap<String,String>();
		//线上线下判断
		if(UpdownStatus != null || !UpdownStatus.equals("")){
			if("0".equals(UpdownStatus)){
				
			}
			if("1".equals(UpdownStatus)){
				retMap.put("memo", "不支持此类型客户录入！");
				retMap.put("ret", "1111");
				return retMap;
				
			}
		}
			
				try {
					//准备请求参数
					String orderno = IDGenerator.getNextID("");
					int ss = (int)((Math.random()*9+1)*100000);
					String ssn = String.valueOf(ss);
					BigDecimal projectAmt = fyProjectEntry.getProjectAmt().multiply(BigDecimal.valueOf(100)).setScale(0);
					String contractNm = fyProjectEntry.getContractNm();
					String projectDeadline = fyProjectEntry.getProjectDeadline();
					String borNm = fyProjectEntry.getBorNm();
					String idNo = fyProjectEntry.getIdNo();
					String cardNo = fyProjectEntry.getCardNo();
					String mobileNo = fyProjectEntry.getMobileNo();
					String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>" 
								+ "<project>" 
								+ "<ver>" + VER + "</ver>" 
								+ "<orderno>" + orderno + "</orderno>" 
								+ "<mchnt_nm>红上测试</mchnt_nm>" 
								+ "<project_ssn>"+ ssn + "</project_ssn>" 
								+ "<project_amt>" + projectAmt + "</project_amt>" 
								+ "<contract_nm>" + contractNm + "</contract_nm>" 
								+ "<project_deadline>" + projectDeadline + "</project_deadline>" 
								+"<max_invest_num></max_invest_num>"
								+"<min_invest_num></min_invest_num>"
								+ "<bor_nm> " + borNm + " </bor_nm>" 
								+ "<id_tp>0</id_tp>" 
								+ "<id_no>" + idNo + "</id_no>" 
								+ "<card_no>" + cardNo + "</card_no>" 
								+ "<mobile_no>" + mobileNo + "</mobile_no>" 
								+ "</project>";
					LOG.info("项目录入请求报文：" + xml);
					String macSource = MERID + "|" + KEY + "|" + xml;
					String mac = MD5Util.encode(macSource, "UTF-8").toUpperCase();
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("merid", MERID));
					params.add(new BasicNameValuePair("xml", xml));
					params.add(new BasicNameValuePair("mac", mac));
					Map<String, Object> resultMap = HttpUtil.requestPost(PROJECT_ENTRY, params);
					LOG.info("项目录入返回结果转Map" + resultMap.toString());
					String ret = String.valueOf(resultMap.get("ret"));
					String memo = String.valueOf(resultMap.get("memo"));				
					if("0000".equals(ret)){
						String retOrderno = String.valueOf(resultMap.get("orderno"));
						String projectId = String.valueOf(resultMap.get("project_id"));

						
						retMap.put("retOrderno", retOrderno);
						retMap.put("projectId", projectId);
						retMap.put("memo", memo);
						retMap.put("ret", ret);
						return retMap;
						
					}else{
						retMap.put("memo", memo);
						retMap.put("ret", ret);
						return retMap;
					}
				} catch (ClientProtocolException e) {
					retMap.put("memo", "请求异常！");
					retMap.put("ret", "1111");
					e.printStackTrace();
					return retMap;
				} catch (IOException e) {
					retMap.put("memo", "请求异常！");
					retMap.put("ret", "1111");
					e.printStackTrace();
					return retMap;
				} catch (DocumentException e) {
					retMap.put("memo", "返回报文解析异常！");
					retMap.put("ret", "1111");
					e.printStackTrace();
					return retMap;
				}
			
	}
	

	/**
	 * 
	 * 作者：zwLiu <br>
	 * 创建时间：2018年4月23日 <br>
	 * 描述：五要素鉴权
	 * @return Json 
	 * 注：请求成功后，json中，data 为 0时不需要验证码，为1时需要验证码
	 */
	public static Json elements5Check(FYdeductModel fYdeduct){
		LOG.info("富友5要素鉴权：" + fYdeduct.toString());
		try {
			//回拨号码 (默认)			
	    	String isCallback ="0";
			//业务类型（默认）
	        String busiCd ="AC01" ;
			//证件类型（默认身份证）	        
	        String credtTp = "0";
			//银行卡号 	        
	        String acntNo =fYdeduct.getAccntno();
			//行别	        
	        String bankCd =fYdeduct.getBankno();
			//户名 	        
	        String userNm = fYdeduct.getAccntnm();
			//证件号码	        
	        String credtNo =fYdeduct.getCertno();
			//签约来源（默认）	        
	        String srcChnl ="APP";
			//账户类型（默认借记卡）	        
	        String acntTp = "01";
			//手机号	        
	        String mobileNo =fYdeduct.getMobile();
			//商户号
			String mchntCd = MERID;
			//保留字段 
			String reserved1 ="代收签约";
	        ArrayList<String> list=new ArrayList<String>();
			list.add(isCallback);
			list.add(busiCd);
			list.add(credtTp);
			list.add(acntNo);
			list.add(bankCd);
			list.add(userNm);
			list.add(credtNo);
			list.add(srcChnl);
			list.add(acntTp);
			list.add(mobileNo);
			list.add(mchntCd);
			list.add(reserved1);
			String signature = SignatureUtil.hex(list,KEY);
			
			String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
			+"<custmrBusi>"
			+"<isCallback>" + isCallback + "</isCallback>"
			+"<busiCd>" + busiCd + "</busiCd>"
			+"<credtTp>" + credtTp + "</credtTp>"
			+"<acntNo>" + acntNo + "</acntNo>"
			+"<bankCd>" + bankCd + "</bankCd>"
			+"<userNm>" + userNm + "</userNm>"
			+"<credtNo>" + credtNo + "</credtNo>"
			+"<srcChnl>" + srcChnl + "</srcChnl>"
			+"<acntTp>" + acntTp + "</acntTp>"
			+"<mobileNo>" + mobileNo + "</mobileNo>"
			+"<mchntCd>" + mchntCd + "</mchntCd>"
			+"<reserved1>" + reserved1 + "</reserved1>"
			//+"<pageFrontUrl>" + pageFrontUrl + "</pageFrontUrl>"
			+"<signature>" + signature + "</signature>"
			+"</custmrBusi>";
			LOG.info("富友5要素鉴权请求报文" + xml);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("xml", xml));		
			Map<String, Object> map = HttpUtil.requestPost(ELEMENTS_5_URL,params);
			LOG.info("富友5要素鉴权返回结果转Map" + map);
			String respCd = String.valueOf(map.get("respCd"));
			String respDesc = String.valueOf(map.get("respDesc"));
			if("0000".equals(respCd)){
				return new Json(true, respDesc,1);
			}else if("300016".equals(respCd)){
				return new Json(true, respDesc,0);
			}
			else{
				return new Json(false, respDesc);
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return new Json(false, "请求异常！");	
		} catch (IOException e) {
			e.printStackTrace();
			return new Json(false, "请求异常！");			
		} catch (DocumentException e) {
			e.printStackTrace();
			return new Json(false, "返回报文解析异常！");	
		}
		
	}

	/**
	 * 
	 * 作者：zwLiu <br>
	 * 创建时间：2018年4月24日 <br>
	 * 描述：五要素鉴权短信验证 
	 * @param fYdeduct
	 */
	public static Json elements5Msg(FYdeductModel fYdeduct){
        try {
			String acntNo = fYdeduct.getAccntno();
			String mchntCd = MERID;
			String verifyCode =fYdeduct.getVerifyCode();
			
			ArrayList<String> list=new ArrayList<String>();
			list.add(acntNo);
			list.add(mchntCd);
			list.add(verifyCode);        
			String signature = SignatureUtil.hex(list,"123456");		

			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<custmrBusi>"
					+ "<acntNo>" + acntNo + "</acntNo>"
					+ "<mchntCd>" + mchntCd + "</mchntCd>"
					+ "<verifyCode>" + verifyCode + "</verifyCode>"
					+ "<signature>" + signature + "</signature>"
					+ "</custmrBusi>";
			LOG.info("5要素验证码请求报文" + xml);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("xml", xml));
			Map<String, Object> resultMap = HttpUtil.requestPost(ELEMENTS_5_MSG_URL,params);
			LOG.info("5要素验证码返回报文转map" + resultMap);
			
			String respCd = String.valueOf(resultMap.get("respCd"));
			String respDesc = String.valueOf(resultMap.get("respDesc"));
			if("0000".equals(respCd)){
				return new Json(true, respDesc);
			}else{
				return new Json(false, respDesc);
				
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return new Json(false, "请求异常！");			
		} catch (IOException e) {
			e.printStackTrace();
			return new Json(false, "请求异常！");			
		} catch (DocumentException e) {
			e.printStackTrace();
			return new Json(false, "报文解析异常！");			
		}
	}
	
	/**
	 * 
	 * 作者：zwLiu <br>
	 * 创建时间：2018年4月23日 <br>
	 * 描述：富友代扣
	 */
	
	public static DeductResult FYdeduct(FYdeductModel fYdeduct){
		LOG.info("富友划扣："+fYdeduct.toString());
				String UpdownStatus = fYdeduct.getUpdownStatus();
				DeductResult result = new DeductResult();
				String orderno = null;
			try {
				if(UpdownStatus != null || !UpdownStatus.equals("")){
					if("0".equals(UpdownStatus)){
						
					}
					if("1".equals(UpdownStatus)){
						result.setDeductStatus("1111");
						result.setTransId(orderno);
						result.setDeductDate(new Date());
						result.setRespCode("1111");
						result.setRespMsg("不支持此类型客户划扣");
						return result;
					}
				}
				//准备参数
				BigDecimal amt = fYdeduct.getAmt().multiply(BigDecimal.valueOf(100)).setScale(0);
				//交易日期
				String merdt = FYdeductUtil.getDate();
				//订单号
				orderno = IDGenerator.getNextID("FYLS");
				//卡号
				String accntno = fYdeduct.getAccntno();
				//用户名
				String accntnm = fYdeduct.getAccntnm();
				//手机号
				String mobile = fYdeduct.getMobile();
				//身份证号
				String certno = fYdeduct.getCertno();
				//开户行代码
				String bankno = fYdeduct.getBankno();
				//项目ID
				String projectId = fYdeduct.getPrijectid();
				String memo = "代扣测试";
				//拼接xml
				String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"
							+"<incomeforreq>"
							+"<ver>" + VER + "</ver>"
							+"<merdt>"+ merdt +"</merdt>"
							+"<orderno>" + orderno + "</orderno>"
							+"<bankno>"+ bankno +"</bankno>"
							+"<accntno>" + accntno + "</accntno>"
							+"<accntnm>" + accntnm + "</accntnm>"
							+"<amt>" + amt + "</amt>"
							+"<entseq>" + orderno + "</entseq>"
							+"<memo>" + memo + "</memo>"
							+"<mobile>" + mobile + "</mobile>"
							+"<certtp>0</certtp>"
							+"<certno>" + certno + "</certno>"
							+"<projectid>" + projectId + "</projectid>"
							+"<txncd>" + "06" + "</txncd>"
							+"<addDesc>1</addDesc>"
							+"</incomeforreq>";
				LOG.info("富友划扣请求报文："+ xml);
				
				String macSource = MERID + "|" + KEY + "|"+"sincomeforreq"+"|"+xml;	
				String mac = MD5Util.encode(macSource, "UTF-8").toUpperCase();  	
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("merid", MERID));
				params.add(new BasicNameValuePair("reqtype", "sincomeforreq"));
				params.add(new BasicNameValuePair("xml", xml));
				params.add(new BasicNameValuePair("mac", mac));        
				Map<String, Object> map = HttpUtil.requestPost(DEDUCTURL,params);
				LOG.info("富友划扣返回报文转map："+ map);

				String ret = String.valueOf(map.get("ret"));
				String additionalInfo = String.valueOf(map.get("memo"));

				if("000000".equals(ret)){
					result.setDeductStatus("0000");
					result.setTransId(orderno);
					result.setDeductDate(new Date());
					result.setRespCode(ret);
					result.setRespMsg(additionalInfo);
					result.setDeductMoney(fYdeduct.getAmt());
					result.setAdditionalInfo("线下富友");
					return result;
				}else if(FYdeductUtil.getTimeOutStatus(ret)){
					result.setDeductStatus("2222");
					result.setTransId(orderno);
					result.setDeductDate(new Date());
					result.setRespCode(ret);
					result.setRespMsg(additionalInfo);
					result.setDeductMoney(fYdeduct.getAmt());
					result.setAdditionalInfo("线下富友");
					return result;
				}else{
					result.setDeductStatus("1111");
					result.setTransId(orderno);
					result.setDeductDate(new Date());
					result.setRespCode(ret);
					result.setRespMsg(additionalInfo);
					result.setDeductMoney(fYdeduct.getAmt());
					result.setAdditionalInfo("线下富友");	
					return result;
				}
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				result.setDeductStatus("2222");
				result.setTransId(orderno);
				result.setDeductDate(new Date());
				result.setRespMsg("请求异常！");
				result.setDeductMoney(fYdeduct.getAmt());
				result.setAdditionalInfo("线下富友");
				return result;				
			} catch (IOException e) {
				e.printStackTrace();
				e.printStackTrace();
				result.setDeductStatus("2222");
				result.setTransId(orderno);
				result.setDeductDate(new Date());
				result.setRespMsg("请求异常！");
				result.setDeductMoney(fYdeduct.getAmt());
				result.setAdditionalInfo("线下富友");
				return result;				
			} catch (DocumentException e) {
				e.printStackTrace();
				e.printStackTrace();
				e.printStackTrace();
				result.setDeductStatus("2222");
				result.setTransId(orderno);
				result.setDeductDate(new Date());
				result.setRespMsg("返回报文解析异常！");
				result.setDeductMoney(fYdeduct.getAmt());
				result.setAdditionalInfo("线下富友");
				return result;					
			}

		
	}	
	
	/**
	 * 
	 * 作者：zwLiu <br>
	 * 创建时间：2018年4月27日 <br>
	 * 描述：划扣结果查证 
	 * @param fYdeduct
	 * @return
	 */
	public static DeductResult FyDeductVerification(FyQrytransParam fyQrytransParam){
		DeductResult result = new DeductResult();
		
		String updownStatus = fyQrytransParam.getUpdownStatus();
		
			if("0".equals(updownStatus)){
				
			}
			if("1".equals(updownStatus)){
				
			}
		

			String busicd = fyQrytransParam.getBusicd();
			//原请求流水
			String orderno = fyQrytransParam.getOrderno();
			//开始日期
			String startdt = fyQrytransParam.getStartdt();
			//结束日期
			String enddt = fyQrytransParam.getEnddt();
			//交易状态
			String transst = fyQrytransParam.getTransst();
			try {
				if(busicd == null || "".equals(busicd) ){
					busicd = "AC01";
				}
				
				if(transst == null){
					transst = "";
				}
				if(orderno == null){
					orderno = "";
				}
				if(startdt == null || enddt == null){
					startdt = "";
					enddt = "";
				}

				String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"
						+"<qrytransreq>"
						+"<ver>1.00</ver>"
						////AP01:代付  AC01：代收  TP01：退票
						+"<busicd>"+ busicd +"</busicd>"    
						//查询多个流水，流水中间用英文,间隔，一次最多50个
						+"<orderno>" + orderno + "</orderno>"     
						+"<startdt>" + startdt + "</startdt>"  
						+"<enddt>" + enddt + "</enddt>"
						//+"<transst>1</transst>"
						+"</qrytransreq>";
				String macSource = MERID + "|"+ KEY +"|"+"qrytransreq"+"|"+xml;	
				String mac = MD5Util.encode(macSource, "UTF-8").toUpperCase();
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("merid", MERID));
				params.add(new BasicNameValuePair("reqtype", "qrytransreq"));
				params.add(new BasicNameValuePair("xml", xml));
				params.add(new BasicNameValuePair("mac", mac));    	
				Map<String, Object> resultMap = HttpUtil.requestPost(QRYTRANS_URL,params);			
				//请求状态（请求成功 : 000000 ）
				String ret = String.valueOf(resultMap.get("ret"));
				//该笔订单交易信息
				Map<String, Object> map1 =  (Map<String, Object>) resultMap.get("trans");
				if("000000".equals(ret)){
					//交易状态
					//0 交易未发送
					//1 交易已发送且成功
					//2 交易已发送且失败
					//3 交易发送中
					//7 交易已发送且超时
					String state = String.valueOf(map1.get("state"));
					result.setFyCheck(state);
					switch (state) {
					//0 交易未发送
					case "0":
						result.setDeductStatus("1111");
						result.setTransId(orderno);
						result.setDeductDate(new Date());
						result.setRespCode(ret);
						result.setRespMsg(String.valueOf(map1.get("reason")));
						result.setAdditionalInfo(String.valueOf(map1.get("result")));

						result.setDeductMoney(FYdeductUtil.unitConversion(String.valueOf(map1.get("amt"))));
						result.setAdditionalInfo("线下富友");						 
						break;
					//1 交易已发送且成功	
					case "1":
						result.setDeductStatus("0000");
						result.setTransId(orderno);
						result.setDeductDate(new Date());
						result.setRespCode(ret);
						result.setRespMsg(String.valueOf(map1.get("reason")));
						result.setAdditionalInfo(String.valueOf(map1.get("result")));
						result.setDeductMoney(FYdeductUtil.unitConversion(String.valueOf(map1.get("amt"))));
						result.setAdditionalInfo("线下富友");								
						break;
					//2 交易已发送且失败						
					case "2":
						result.setDeductStatus("1111");
						result.setTransId(orderno);
						result.setDeductDate(new Date());
						result.setRespCode(ret);
						result.setRespMsg(String.valueOf(map1.get("reason")));
						result.setAdditionalInfo(String.valueOf(map1.get("result")));
						result.setDeductMoney(FYdeductUtil.unitConversion(String.valueOf(map1.get("amt"))));
						result.setAdditionalInfo("线下富友");							
						break;
					//3 交易发送中						
					case "3":
						result.setDeductStatus("2222");
						result.setTransId(orderno);
						result.setDeductDate(new Date());
						result.setRespCode(ret);
						result.setRespMsg(String.valueOf(map1.get("reason")));
						result.setAdditionalInfo(String.valueOf(map1.get("result")));
						result.setDeductMoney(FYdeductUtil.unitConversion(String.valueOf(map1.get("amt"))));
						result.setAdditionalInfo("线下富友");							
						break;
					//7 交易已发送且超时						
					case "7":
						result.setDeductStatus("2222");
						result.setTransId(orderno);
						result.setDeductDate(new Date());
						result.setRespCode(ret);
						result.setRespMsg(String.valueOf(map1.get("reason")));
						result.setAdditionalInfo(String.valueOf(map1.get("result")));
						result.setDeductMoney(FYdeductUtil.unitConversion(String.valueOf(map1.get("amt"))));
						result.setAdditionalInfo("线下富友");							
						break;						

					default:
						result.setDeductStatus("2222");
						result.setTransId(orderno);
						result.setDeductDate(new Date());
						result.setRespCode(ret);
						result.setRespMsg(String.valueOf(map1.get("reason")));
						result.setAdditionalInfo(String.valueOf(map1.get("result")));
						result.setDeductMoney(FYdeductUtil.unitConversion(String.valueOf(map1.get("amt"))));
						result.setAdditionalInfo("线下富友");							
						break;
					}
						
				}
				return result;			
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				result.setDeductStatus("2222");
				result.setTransId(orderno);
				result.setDeductDate(new Date());
				result.setRespMsg("请求异常！");
				result.setAdditionalInfo("线下富友");
				return result;				
			} catch (IOException e) {
				e.printStackTrace();
				result.setDeductStatus("2222");
				result.setTransId(orderno);
				result.setDeductDate(new Date());
				result.setRespMsg("请求异常！");
				result.setAdditionalInfo("线下富友");
				return result;					
			} catch (DocumentException e) {
				e.printStackTrace();
				result.setDeductStatus("2222");
				result.setTransId(orderno);
				result.setDeductDate(new Date());
				result.setRespMsg("返回报文解析异常！");
				result.setAdditionalInfo("线下富友");
				return result;	
			}


		
	}
	
	
	/**
	 * 
	 * 作者：zwLiu <br>
	 * 创建时间：2018年4月27日 <br>
	 * 描述：分转元 
	 * @param valueOf
	 * @return
	 */
	private static BigDecimal unitConversion(String amt) {
		
		return new BigDecimal(amt).divide(new BigDecimal("100"));
	}


	/**
	 * 
	 * 作者：zwLiu <br>
	 * 创建时间：2018年4月23日 <br>
	 * 描述：日期格式化 
	 * @return
	 */
	public static String getDate(){
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		Date date=new Date();
		String date1=sf.format(date);
		return date1;
	}
	public static String getEntseq(String sign){
		return sign + PrimaryKeyUtil.getPrimaryKey();
	}
	

	
	/**
	 * 超时状态判断
	 * 作者：zwLiu <br>
	 * 创建时间：2018年4月26日 <br>
	 * 描述： 
	 * @param ret
	 * @return
	 */
	public static boolean getTimeOutStatus(String ret){
		List<String> lis = new ArrayList<String>();
		lis.add("200001");
		lis.add("200002");
		lis.add("999999");
		return lis.contains(ret);
		
	}
	
public static void main(String[] args) {
	FYdeductModel fYdeduct = new FYdeductModel();
	/**
	 * 项目录入
	 */
	FYProjectEntryParam entryParam = new FYProjectEntryParam("0", new BigDecimal("-50000"), "CS0000000020180425", "730", "金世强", "610114199412051559", "6214852113975885", "18710486944");
	FYdeductUtil.FyProhectEntry(entryParam);

	
	/**
	 * 五要素鉴权
	 */
//	//行别	  0102
//	fYdeduct.setBankno("0308");
//	//户名 
//	 fYdeduct.setAccntnm("金世强");
//	//手机号
//	 fYdeduct.setMobile("18710486944");
//	 //银行卡号 
//	 fYdeduct.setAccntno("6214852113975885");	
//	 //证件号码
//	fYdeduct.setCertno("610114199412051559");
	
//	//行别	 0308
//	fYdeduct.setBankno("0403");
//	//户名 
//	fYdeduct.setAccntnm("张林");
//	//手机号
//	fYdeduct.setMobile("18282114741");
//	//银行卡号 
//	fYdeduct.setAccntno("6217997900031271133");	
//	//证件号码
//	fYdeduct.setCertno("513701199509103925");
	
	
	
	
	
//	FYdeductUtil.elements5Check(fYdeduct);
	
//	/**
//	 * 验证码
//	 */
//	fYdeduct.setAccntno("6217997900031271133");
//	fYdeduct.setVerifyCode("000000");//000000
//	FYdeductUtil.elements5Msg(fYdeduct);	
	
	/**
	 * 代扣
	 */
//	fYdeduct.setBankno("0308");
//	fYdeduct.setAccntnm("金世强");
//	fYdeduct.setAccntno("6214852113975885");
//	fYdeduct.setAmt(new BigDecimal("10.00"));
//	fYdeduct.setMobile("18710486944");
//	fYdeduct.setUpdownStatus("0");
//	fYdeduct.setCertno("610114199412051559");
//	fYdeduct.setPrijectid("0345142_20180426_000002");
//	FYdeductUtil.FYdeduct(fYdeduct);
	
	/**
	 * 查询交易
	 */
//	FyQrytransParam fyQrytransParam = new FyQrytransParam();
//	fyQrytransParam.setStartdt("20180420");
//	fyQrytransParam.setEnddt("20180427");
//	fyQrytransParam.setOrderno("490820180420140405");
//	FYdeductUtil.FyDeductVerification(fyQrytransParam);
}	
	
}
