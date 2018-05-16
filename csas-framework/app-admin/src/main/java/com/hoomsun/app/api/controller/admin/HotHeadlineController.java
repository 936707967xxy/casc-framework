/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.app.api.controller.admin;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hoomsun.app.api.model.HotHeadline;
import com.hoomsun.app.api.server.inter.HotHeadlineServerI;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.anno.Permission;


/**
 * 作者：liushuai <br>
 * 创建时间：2017年9月19日 <br>
 * 描述：通知公告管理控制层
 */
@Controller
@RequestMapping("sys/hot")
public class HotHeadlineController {
	
	private HotHeadlineServerI hotHeadlineServer;

	@Autowired
	public void setHotHeadlineServer(HotHeadlineServerI hotHeadlineServer) {
		this.hotHeadlineServer = hotHeadlineServer;
	}


	@RequestMapping(value = "page.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Pager<HotHeadline> findDataGrid(Integer page, Integer rows, String headline) {
		return hotHeadlineServer.findPage(page, rows, headline);
	}


	@RequestMapping(value = "create.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json addhot( HotHeadline hotHeadLine,HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/");
		return hotHeadlineServer.createHotHeadline(path, hotHeadLine);
	}

	

	
	@Permission("hot_edit")
	@RequestMapping(value = "/sys/hot/update.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json edithot(HotHeadline hotHeadLine,HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/");
		return hotHeadlineServer.updateHotHeadline(path, hotHeadLine);
	}

	
	@Permission("hot_delete")
	@RequestMapping(value = "remove.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Json deletehot(String hotId,HttpServletRequest request) {
		return hotHeadlineServer.deleteHotHeadline(hotId);
	}

	/**
	 * 作者：liushuai <br>
	 * 创建时间：2017年9月20日 <br>
	 * 功能描述： 测试模板创建使用
	 * 
	 * @param editor
	 *            这里是指公告内容
	 * @return
	 * 
	 */
	// @RequestMapping(value="/sys/hot/creatTmp.do",method= RequestMethod.GET)
	// @ResponseBody
	// public String findDataGrid(HttpServletRequest request, String editor,
	// String headline){
	// String path = request.getSession().getServletContext().getRealPath("/");
	// hotHeadlineServer.createHtmlFromTmp(path, headline, editor);
	// return "success";
	// }

}
