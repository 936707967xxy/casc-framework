/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.settle.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSON;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月11日 <br>
 * 描述：读取初始化资源信息
 */
public class SAXRederXML {
	/**
	 * 读取XML
	 * 
	 * @return
	 */
	public static Map<String, Object> readerXML(String xml) throws DocumentException {
		Map<String, Object> result = new HashMap<String, Object>();
		Document document = DocumentHelper.parseText(xml);
		Element root = document.getRootElement();
		List<Element> childs = root.elements();
		childBuild(childs,result);
		return result;
	}

	public static void childBuild(List<Element> childs,Map<String, Object> result) {
		if (childs == null || childs.size() < 1) {
			return;
		}
		
		Map<String, Integer> keys = new HashMap<String, Integer>();
		for (Element element : childs) {
			String key = element.getName();
			Integer value = keys.get(key);
			value = value == null ? 1 :(value+1);
			keys.put(key, value);
		}
		
		for (Element element : childs) {
			String key = element.getName();
			List<Element> child = element.elements();
			Map<String, Object> map = new HashMap<String, Object>();
			for (Element cl : child) {
				List<Element> cls = cl.elements();
				String cKey = cl.getName();
				if (null != cls && cls.size() > 0) {
					map.put(cKey, childsBuild(cls));
				} else {
					String value = cl.getTextTrim();
					map.put(cKey, value);
				}
			}
			
			
			if(keys.get(key) > 1) {
				@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) result.get(key);
				if (null == list || list.size() < 1) {
					list = new ArrayList<Object>();
					list.add(map);
					result.put(key, list);
				}else {
					list.add(map);
				}
			}else {
				result.put(key, map);
			}
		}
	}
	
	public static List<Object> childsBuild(List<Element> childs) {
		List<Object> list = new ArrayList<Object>();
		for (Element element : childs) {
			String key = element.getName();
			List<Element> child = element.elements();
			if (null != child && child.size() > 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(key, childsBuild(child));
				list.add(map);
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				String value = element.getTextTrim();
				map.put(key, value);
				list.add(map);
			}
		}
		return list;
	}
	
	public static void main(String[] args) {
		String str = "<?xml version=\"1.0\" encoding=\"GBK\"?><CMBSDKPGK><INFO><DATTYP>2</DATTYP><ERRMSG></ERRMSG><FUNNAM>GetTransInfo</FUNNAM><LGNNAM>银企直连专用集团2</LGNNAM><RETCOD>0</RETCOD></INFO><NTQTSINFZ><AMTCDR>C</AMTCDR><APDFLG>Y</APDFLG><ATHFLG></ATHFLG><BBKNBR>59</BBKNBR><CHKNBR></CHKNBR><C_ATHFLG>无</C_ATHFLG><C_BBKNBR>福州</C_BBKNBR><C_ETYDAT>2016年12月21日</C_ETYDAT><C_GSBBBK></C_GSBBBK><C_RPYBBK></C_RPYBBK><C_TRSAMT>3.02</C_TRSAMT><C_TRSAMTC>3.02</C_TRSAMTC><C_TRSBLV>3.02</C_TRSBLV><C_VLTDAT>2016年12月21日</C_VLTDAT><ETYDAT>20161221</ETYDAT><ETYTIM>151136</ETYTIM><GSBACC>591902896710201</GSBACC><GSBBBK></GSBBBK><GSBNAM>银企直连专用账户9</GSBNAM><INFFLG>2</INFFLG><NAREXT>M000232600001B</NAREXT><NARYUR>由591902896710201协议转入</NARYUR><REFNBR>G3370300000043C</REFNBR><REFSUB></REFSUB><RPYBBK></RPYBBK><RSV30Z>**</RSV30Z><RSV31Z>10</RSV31Z><RSV50Z></RSV50Z><TRSAMT>3.02</TRSAMT><TRSAMTC>3.02</TRSAMTC><TRSANL>ZHGATR</TRSANL><TRSBLV>3.02</TRSBLV><TRSCOD>CSTC</TRSCOD><VLTDAT>20161221</VLTDAT><YURREF></YURREF></NTQTSINFZ><NTQTSINFZ><AMTCDR>C</AMTCDR><APDFLG>Y</APDFLG><ATHFLG></ATHFLG><BBKNBR>59</BBKNBR><CHKNBR></CHKNBR><C_ATHFLG>无</C_ATHFLG><C_BBKNBR>福州</C_BBKNBR><C_ETYDAT>2016年12月21日</C_ETYDAT><C_GSBBBK></C_GSBBBK><C_RPYBBK></C_RPYBBK><C_TRSAMT>3.01</C_TRSAMT><C_TRSAMTC>3.01</C_TRSAMTC><C_TRSBLV>3.01</C_TRSBLV><C_VLTDAT>2016年12月21日</C_VLTDAT><ETYDAT>20161221</ETYDAT><ETYTIM>151137</ETYTIM><GSBACC>591902896710201</GSBACC><GSBBBK></GSBBBK><GSBNAM>银企直连专用账户9</GSBNAM><INFFLG>2</INFFLG><NAREXT>M000232600001B</NAREXT><NARYUR>由591902896710201协议转入</NARYUR><REFNBR>G3370300000047C</REFNBR><REFSUB></REFSUB><RPYBBK></RPYBBK><RSV30Z>**</RSV30Z><RSV31Z>10</RSV31Z><RSV50Z></RSV50Z><TRSAMT>3.01</TRSAMT><TRSAMTC>3.01</TRSAMTC><TRSANL>ZHGATR</TRSANL><TRSBLV>3.01</TRSBLV><TRSCOD>CSTC</TRSCOD><VLTDAT>20161221</VLTDAT><YURREF></YURREF></NTQTSINFZ><NTQTSINFZ><AMTCDR>C</AMTCDR><APDFLG>Y</APDFLG><ATHFLG></ATHFLG><BBKNBR>59</BBKNBR><CHKNBR></CHKNBR><C_ATHFLG>无</C_ATHFLG><C_BBKNBR>福州</C_BBKNBR><C_ETYDAT>2016年12月21日</C_ETYDAT><C_GSBBBK></C_GSBBBK><C_RPYBBK></C_RPYBBK><C_TRSAMT>3.04</C_TRSAMT><C_TRSAMTC>3.04</C_TRSAMTC><C_TRSBLV>3.04</C_TRSBLV><C_VLTDAT>2016年12月21日</C_VLTDAT><ETYDAT>20161221</ETYDAT><ETYTIM>151237</ETYTIM><GSBACC>591902896710201</GSBACC><GSBBBK></GSBBBK><GSBNAM>银企直连专用账户9</GSBNAM><INFFLG>2</INFFLG><NAREXT>M000232600001B</NAREXT><NARYUR>由591902896710201协议转入</NARYUR><REFNBR>G3370300000051C</REFNBR><REFSUB></REFSUB><RPYBBK></RPYBBK><RSV30Z>**</RSV30Z><RSV31Z>10</RSV31Z><RSV50Z></RSV50Z><TRSAMT>3.04</TRSAMT><TRSAMTC>3.04</TRSAMTC><TRSANL>ZHGATR</TRSANL><TRSBLV>3.04</TRSBLV><TRSCOD>CSTC</TRSCOD><VLTDAT>20161221</VLTDAT><YURREF></YURREF></NTQTSINFZ></CMBSDKPGK>";
		try {
			System.out.println(JSON.toJSONString(readerXML(str)));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
