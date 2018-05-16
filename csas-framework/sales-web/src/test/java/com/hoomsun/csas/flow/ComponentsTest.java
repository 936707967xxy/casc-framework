/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.flow;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.file.FileUtil;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.core.model.SysComponents;
import com.hoomsun.core.server.inter.SysComponentsServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月17日 <br>
 * 描述：
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-cfg.xml")
public class ComponentsTest {

	@Autowired
	private SysComponentsServerI componentsServer;
	@Value("${SYSTEM_NAME}")
	private String systemName;
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月5日 <br>
	 * 描述：初始化菜单权限
	 */
	@Test
	public void initData() {
		String result = FileUtil.redFile("D:\\SVN\\CSAS_178\\trunk\\csas-framework\\sales-web\\src\\main\\resources\\routes.json");
		JSONArray router = JSONObject.parseArray(result.trim());
		List<SysComponents> list = new ArrayList<SysComponents>();
		for (Object object : router) {
			String text = JSONObject.toJSONString(object);
			JSONObject jsonObject = JSONObject.parseObject(text);
			SysComponents components = new SysComponents();
			String id = PrimaryKeyUtil.getPrimaryKey();
			components.setCptId(id);
			components.setPath(jsonObject.getString("path"));
			components.setComponent(jsonObject.getString("component"));
			components.setName(jsonObject.getString("name"));
			components.setRedirect(jsonObject.getString("redirect"));
			components.setIconcls(jsonObject.getString("iconcls"));
			components.setHidden(jsonObject.getBoolean("hidden") ? 0 : 1);
			components.setParentId(jsonObject.getString("parentId"));
			components.setAscription(jsonObject.getString("ascription"));
			components.setSort(jsonObject.getInteger("sort"));
			components.setCptType(jsonObject.getString("type"));
			components.setCptValue(jsonObject.getString("value"));
			components.setDropdown(jsonObject.getBoolean("dropdown") ? 1 : 0);
			components.setAscription(systemName);
			list.add(components);
			
			JSONArray children = jsonObject.getJSONArray("children");
			if (children != null) {
				buildChild(id, children, list);
			}
		}
		
		for (SysComponents sysComponents : list) {
			componentsServer.createComponents(sysComponents);
		}
	}

	public void buildChild(String parentId, JSONArray child, List<SysComponents> list) {
		for (Object object : child) {
			String text = JSONObject.toJSONString(object);
			JSONObject jsonObject = JSONObject.parseObject(text);
			SysComponents components = new SysComponents();
			String id = PrimaryKeyUtil.getPrimaryKey();
			components.setCptId(id);
			components.setPath(jsonObject.getString("path"));
			components.setComponent(jsonObject.getString("component"));
			components.setName(jsonObject.getString("name"));
			components.setRedirect(jsonObject.getString("redirect"));
			components.setIconcls(jsonObject.getString("iconcls"));
			components.setHidden(jsonObject.getBoolean("hidden") ? 0 : 1);
			components.setParentId(jsonObject.getString("parentId"));
			components.setAscription(jsonObject.getString("ascription"));
			components.setSort(jsonObject.getInteger("sort"));
			components.setCptType(jsonObject.getString("type"));
			components.setCptValue(jsonObject.getString("value"));
			components.setDropdown(jsonObject.getBoolean("dropdown") ? 1 : 0);
			components.setParentId(parentId);
			components.setAscription(systemName);
			list.add(components);
			JSONArray children = jsonObject.getJSONArray("children");
			if (children != null) {
				buildChild(id, children, list);
			}
		}
	}

}
