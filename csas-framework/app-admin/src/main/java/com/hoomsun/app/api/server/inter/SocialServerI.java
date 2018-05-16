package com.hoomsun.app.api.server.inter;

import java.util.List;

import com.hoomsun.app.api.model.Social;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.SysCompany;

public interface SocialServerI {

	Json addSocial(Social social);
	
	/**
	 * 作者：刘栋梁<br>
	 * 创建时间：2017年12月01日<br>
	 * 描述：分页查询社保数据信息
	 * @param page 当前页码
	 * @param rows 每页数据量
	 * @param type 类型
	 * @return
	 */
	Pager<Social> findPage(Integer page,Integer rows,String province);
	
	/**
	 * 作者：刘栋梁<br>
	 * 创建时间：2017年12月05日<br>
	 * 描述：查找修改数据
	 */
	Social selectByPrimaryKey(String socialId);
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12月5日<br>
	 * 描述：修社保
	 * @param social 社保修改数据信息
	 * @return
	 */
	Json updateSocial(Social social);
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12月5日<br>
	 * 描述：删除社保
	 * @param social 删除社保数据信息
	 * @return
	 */
	Json deleteSocial(String socialId);
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12月5日<br>
	 * 描述：满足该省下的城市
	 * @param param
	 * @return keys:pageIndex,pageSize,type
	 */
	List<Social> selectByProvince(String provinceid);
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12月6日<br>
	 * 描述：查询接口值
	 * @param param
	 * @return keys:pageIndex,pageSize,type
	 */
	Social  selectApiByUniqueKey(String uniqueKey);
}
