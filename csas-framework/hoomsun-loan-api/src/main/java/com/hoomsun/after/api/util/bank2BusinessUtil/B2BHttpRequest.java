package com.hoomsun.after.api.util.bank2BusinessUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.dom4j.DocumentException;

import com.alibaba.fastjson.JSON;
import com.hoomsun.after.api.model.param.Bank2BusinessParam;
import com.hoomsun.after.api.model.vo.Bank2BusinessModel;
import com.hoomsun.after.api.model.vo.TransInfoModel;
import com.hoomsun.after.api.util.BFutil.JXMConvertUtil;
import com.hoomsun.after.api.util.cmb.XmlPacket;

/**
 * 
 * 作者：zwLiu <br>
 * 创建时间：2018年4月25日 <br>
 * 描述：银企直连工具 
 *
 */
public class B2BHttpRequest {
	
	//分行号
	private static final String BBKNBR ="29";
	//账号	
	private static final String ACCNBR ="129905992710707";
	//借贷码	
	private static final String AMTCDR ="C";
	//登陆账户
	private static final String USERNAME ="郝敏钰";
	//时间段查询
	private static final String GETTRANSINFO ="GetTransInfo";
	//断点查询
	private static final String GETTRANSINFOEX ="GetTransInfoEX";
	
	
	
	
	/**
	 * 银企直连时间段查询
	 * @param b2b(至少包含起止日期)
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	public static Bank2BusinessModel getTransInfo(Bank2BusinessParam b2b) throws IOException{
		// 生成请求报文
		String reqXml = B2BHttpRequest.getRequestStr(b2b);
		// 连接前置机，发送请求报文，获得返回报文
		String result = B2BHttpRequest.sendRequest(reqXml);
		//封装数据
		return JSON.parseObject(result, Bank2BusinessModel.class);
		
	}

	/**
	 * 银企直连断点查询
	 * @param b2b(交易日期、断点序号) 
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	public static Bank2BusinessModel getTransInfoEX(Bank2BusinessParam b2b) throws IOException{
		// 生成请求报文
		String reqXml = B2BHttpRequest.getRequestStr1(b2b);
		// 连接前置机，发送请求报文，获得返回报文
		String result = B2BHttpRequest.sendRequest(reqXml);		
		//封装数据
		return JSON.parseObject(result, Bank2BusinessModel.class);
		
	}
	


	/**
	 * 获取时间段查询请求报文
	 * @return
	 */
	private static String getRequestStr(Bank2BusinessParam b2b) {
		
		// 构造支付的请求报文
		XmlPacket xmlPkt = new XmlPacket(GETTRANSINFO, USERNAME);
		Map mpPodInfo = new Properties();
		//分行号
		mpPodInfo.put("BBKNBR", BBKNBR);
		//分行名称
		mpPodInfo.put("C_BBKNBR", "");
		//账号
		mpPodInfo.put("ACCNBR", ACCNBR);
		//起始日期
		mpPodInfo.put("BGNDAT", b2b.getStartDate());
		//结束日期
		mpPodInfo.put("ENDDAT", b2b.getEndDate());
		//最小金额
		mpPodInfo.put("LOWAMT", "");
		//最大金额
		mpPodInfo.put("HGHAMT", "");
		//借贷码
		mpPodInfo.put("AMTCDR", AMTCDR);
		
		xmlPkt.putProperty("SDKTSINFX", mpPodInfo);
		 return xmlPkt.toXmlString();	
		
	}
	/**
	 * 获取断点查询报文
	 */
	private static String getRequestStr1(Bank2BusinessParam b2b) {
		
		// 构造支付的请求报文
		XmlPacket xmlPkt = new XmlPacket(GETTRANSINFOEX, USERNAME);
		Map mpPodInfo = new Properties();
		//分行号
		mpPodInfo.put("BBKNBR", BBKNBR);
		//分行名称
		mpPodInfo.put("C_BBKNBR", "");
		//账号
		mpPodInfo.put("ACCNBR", ACCNBR);
		//交易日
		mpPodInfo.put("TRSDAT", b2b.getTransactionDate());
		//起始记账序号
		mpPodInfo.put("TRSSEQ", b2b.getTrsseq());
		
		xmlPkt.putProperty("SDKRBPTRSX", mpPodInfo);
		 return xmlPkt.toXmlString();	
		
	}	
	
	
	
	/**
	 * 连接前置机，发送请求报文，获得返回报文
	 * 
	 * @param data
	 * @return
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	private static String sendRequest(String data) throws IOException{
		String result = "";

			URL url;
			url = new URL("http://113.200.105.37:8085");

			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream os;
			os = conn.getOutputStream();
			os.write(data.toString().getBytes("gbk"));
			os.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(conn
					.getInputStream(),"gbk"));
			String line;
			while ((line = br.readLine()) != null) {
				result += line;
			}
			String resultJson = JXMConvertUtil.XmlConvertJson(result);
			br.close();
		return resultJson;
	}

	
	
public static void main(String[] args) throws IOException{
	
	Bank2BusinessParam b2b =  new Bank2BusinessParam();
//	b2b.setStartDate("20180424");
//	b2b.setEndDate("20180424");
//	Bank2BusinessModel b = B2BHttpRequest.getTransInfo(b2b);

	
	b2b.setTransactionDate("20180425");
	b2b.setTrsseq("0");
	
	Bank2BusinessModel b = B2BHttpRequest.getTransInfoEX(b2b);
	List<TransInfoModel> lis = b.getNtqtsinfz();
for (TransInfoModel transInfoModel : lis) {
	System.out.println(transInfoModel.getPramKeyid());
}
	System.out.println("----");
}
}
