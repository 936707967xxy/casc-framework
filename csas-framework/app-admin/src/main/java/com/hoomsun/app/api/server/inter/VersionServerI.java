package com.hoomsun.app.api.server.inter;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.app.api.model.Version;
import com.hoomsun.common.model.DataGrid;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.SysCompany;


/**
 * 作者：liudongliang<br>
 * 创建时间：2017年9月29日<br>
 * 描述：查询版本信息
 * @param 
 * @return
 */
public interface VersionServerI {

	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月29日<br>
	 * 描述：查询版本信息
	 * @param type 版本类型
	 * @return
	 */
	public Version selectBytype(String type);
	
	
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月29日<br>
	 * 描述：创建版本信息
	 * @param version 添加版本数据信息
	 * @return
	 */
	Json createVersion(Version version);
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月29日<br>
	 * 描述：修改版本信息
	 * @param version 修改版本数据信息
	 * @return
	 */
	Json updateVersion( Version version);
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月29日<br>
	 * 描述：删除版本
	 * @param Id 版本主键
	 * @return
	 */
	Json deleteVersion(String Id);
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月29日<br>
	 * 描述：根据主键查询版本信息
	 * @param Id 版本主键
	 * @return
	 */
	Version findById(String Id);
	
	/**
	 * 作者：刘栋梁<br>
	 * 创建时间：2017年12月01日<br>
	 * 描述：分页查询公司数据信息
	 * @param page 当前页码
	 * @param rows 每页数据量
	 * @param type 类型
	 * @return
	 */
	Pager<Version> findPage(Integer page,Integer rows,String type);
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月29日<br>
	 * 描述：查询出所有的版本信息
	 * @return
	 */
	List<Version> findAllData();
}
