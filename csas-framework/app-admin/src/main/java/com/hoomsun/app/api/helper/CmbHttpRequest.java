package com.hoomsun.app.api.helper;
import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.Properties;
import org.dom4j.DocumentException;
import com.hoomsun.core.model.CmsAccount;



/**
 * 
 * HTTP通讯范例: 直接支付
 * 
 */
public class CmbHttpRequest {
	/**
	 * 生成请求报文   直接支付
	 * 
	 * @return
	 */
	private  static String getRequestStr(CmsAccount cms,String begDate,String endDate) {
		// 构造支付的请求报文
		XmlPacket xmlPkt = new XmlPacket("GetTransInfo", cms.getLgnnam());
		Map sdktsinfx = new Properties();
		sdktsinfx.put("BBKNBR", cms.getBbknbr());  //分行号 
		sdktsinfx.put("C_BBKNBR", cms.getcBbknbr());  //分行号 
		sdktsinfx.put("ACCNBR", cms.getAccnbr());  //银行账号	
		sdktsinfx.put("BGNDAT", begDate);  //开始时间 
		sdktsinfx.put("ENDDAT", endDate);  //结束时间 
		//sdktsinf_X.put("LOWAMT", "");  // 最小额
		//sdktsinf_X.put("HGHAMT","");  // 最大额 
		sdktsinfx.put("AMTCDR", "C");  //借贷码  C(1) C：收入 ：收入  	D：支出 ： 		
		xmlPkt.putProperty("SDKTSINFX", sdktsinfx);	
		return xmlPkt.toXmlString();
	}


	/**
	 * 连接前置机，发送请求报文，获得返回报文
	 * 
	 * @param data
	 * @return
	 * @throws MalformedURLException
	 */
	private static String sendRequest(String data) {
		String result = "";
		try {
			URL url;
			url = new URL("http://localhost:9090");

			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream os;
			os = conn.getOutputStream();
			os.write(data.toString().getBytes("gbk"));
			os.close();

			//修改编码格式   刘栋梁  文档中无
			BufferedReader br = new BufferedReader(new InputStreamReader(conn
					.getInputStream(),"gbk"));
			String line;
			while ((line = br.readLine()) != null) {
				result += line;
			}

			System.out.println("结果集=="+result);
			br.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	
	
	/**
	 * 处理返回的结果
	 * 
	 * @param result
	 * @throws UnsupportedEncodingException 
	 * @throws DocumentException 
	 */
	private static XmlPacket processResult(String result) throws UnsupportedEncodingException, DocumentException{
		XmlPacket pktRsp=new XmlPacket();
		if (result != null && result.length() > 0) {
		    pktRsp = XmlPacket.valueOf(result);  
		} else {
				pktRsp.setRETCOD("100");
				pktRsp.setERRMSG("响应报文解析失败");;
			}
		return pktRsp;
	}
		
		

	
	public static XmlPacket getBankInfo(CmsAccount cms,String begDate,String endDate ) throws UnsupportedEncodingException, DocumentException{
		// 生成请求报文
		String data = CmbHttpRequest.getRequestStr(cms,begDate,endDate);

		// 连接前置机，发送请求报文，获得返回报文
		String result = CmbHttpRequest.sendRequest(data);

		// 处理返回的结果
		XmlPacket xml=CmbHttpRequest.processResult(result);
		return xml;
	}

	
	
	

}