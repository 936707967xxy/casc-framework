/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.common.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月17日 <br>
 * 描述：IO 工具
 */
public class FileUtil {
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月17日 <br>
	 * 描述： 读取文件
	 * @param fileName 文件绝对路径   D:\\test.txt
	 * @return
	 */
	public static String redFile(String fileName) {
		StringBuilder result = new StringBuilder();
		try {
			File file = new File(fileName);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String str = "";
			while ((str = reader.readLine()) != null) {// 使用readLine方法，一次读一行
				result.append(str);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}

}
