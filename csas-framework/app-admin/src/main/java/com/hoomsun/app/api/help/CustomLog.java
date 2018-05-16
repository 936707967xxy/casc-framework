/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.app.api.help;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hoomsun.common.util.SystemUtils;

/**
 * 作者：liushuai <br>
 * 创建时间：2018年2月25日 <br>
 * 描述：
 */
public class CustomLog {

	
	private static void outPutToLocalFile1(String msg){
		File urlLog = new File("D://HS_FS_UPLOAD//msg.log");
		if(!urlLog.exists()){
			try {
				urlLog.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(urlLog, true);
			PrintWriter out = new PrintWriter(fos);
			out.println(msg); // url写入
			out.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private static void outPutToLocalFile(HttpServletRequest request){
		String uri = request.getRequestURI();
		String ip = SystemUtils.getIpAddr(request);
		File urlLog = new File("D://HS_FS_UPLOAD//url.log");
		if(!urlLog.exists()){
			try {
				urlLog.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(urlLog, true);
			PrintWriter out = new PrintWriter(fos);
			out.println(uri + "                      ip:" + ip); // url写入
			Map paramMap = request.getParameterMap();
			Enumeration paramNames = request.getParameterNames();
			String key = null;
			while(paramNames.hasMoreElements()){
				key  = (String) paramNames.nextElement();
				String[]  values = (String[]) paramMap.get(key);
				out.print("                                " + key + ":");
				for(String str: values){
					out.print(new String(str.getBytes("iso-8859-1"), "UTF-8") + ", ");
				}
				out.println();
			}
			out.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
