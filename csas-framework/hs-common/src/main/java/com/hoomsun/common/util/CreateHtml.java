package com.hoomsun.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class CreateHtml {
	/**
	 * 作者：liushuai <br>
	 * 创建时间：2017年9月20日 <br>
	 * 描述： 根据模板生成相应html<br>
	 * TODO : 关于如果创建html文件失败怎么办
	 * 
	 * @param path
	 *            模板根路径
	 * @param setpath
	 *            生成路径
	 * @param content
	 *            公告内容
	 * @param headline
	 *            公告标题
	 * @param time
	 *            公告发布时间
	 */
	public static void create(String path, String setpath, String content, String headline, String time) {
		try {
			Configuration configuration = new Configuration(Configuration.VERSION_2_3_26);
			configuration.setDirectoryForTemplateLoading(new File(path));
			configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_26));
			configuration.setDefaultEncoding("UTF-8"); // 设置模板文档读取编码格式
			// 给模板文档赋值
			Template template = configuration.getTemplate("static.html");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("headline", headline); // 这里的key对应模板中的相应的${变量}
			paramMap.put("time", time);
			paramMap.put("content", content);

			Writer writer = new OutputStreamWriter(new FileOutputStream(setpath), "UTF-8");
			template.process(paramMap, writer);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}

	}
}
