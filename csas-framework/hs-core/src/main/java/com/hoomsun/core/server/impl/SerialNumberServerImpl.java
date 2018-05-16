/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.core.dao.SerialNumberMapper;
import com.hoomsun.core.model.SerialNumber;
import com.hoomsun.core.server.inter.SerialNumberServerI;
import com.hoomsun.core.util.PrimaryKeyUtil;

/**
 * 作者：liming <br>
 * 创建时间：2017年11月29日 <br>
 * 描述：生成编号的业务层 
 */
@Service("serialNumberServer")
public class SerialNumberServerImpl implements SerialNumberServerI {
	@Autowired
	private SerialNumberMapper serialnumberMapper;

	@Override
	public Json create(SerialNumber serialnumber) {
		if (StringUtils.isBlank(serialnumber.getId())) {
			serialnumber.setId(PrimaryKeyUtil.getPrimaryKey());
			serialnumber.setCode(1);
		}
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		String date = fmt.format(new Date());
		serialnumber.setCreatetime(date);
		int reuslt = serialnumberMapper.insertSelective(serialnumber);
		if (reuslt > 0) {
			return new Json(true, "生成申请编号成功!");
		} else {
			return new Json(false, "生成申请编号失败!");
		}

	}

	@Override
	public Pager<SerialNumber> findPage(Integer page, Integer rows, String typeval) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (null != page && null != rows) {
			rows = rows > 200 ? 200 : rows;
			param.put("page", page);
			param.put("rows", rows);
		} else {
			page = 1; // 关于这里是否需要给出默认值，与前端框架有关系
			rows = 10;
		}

		if (!StringUtils.isBlank(typeval)) {
			param.put("typeval", "%" + typeval + "%");
		}
		List<SerialNumber> intention = serialnumberMapper.findPageData(param);
		Integer total = serialnumberMapper.findPageCount(param);
		return new Pager<SerialNumber>(intention, total);
	}

	@Override
	public Json updateSerialNumber(SerialNumber serialnumber) {
		int result = serialnumberMapper.updateByPrimaryKey(serialnumber);
		if (result > 0) {
			return new Json(true, "编号规则修改成功！");
		} else {
			return new Json(false, "编号规则修改失败！");
		}

	}

	@Override
	public Json deleteSerialNumber(String id) {
		int result = serialnumberMapper.deleteByPrimaryKey(id);
		if (result > 0) {
			return new Json(true, "删除编号成功！");
		} else {
			return new Json(false, "删除编号失败！");
		}
	}

	@Override
	public SerialNumber findById(String id) {
		return serialnumberMapper.findById(id);
	}

	// 返回编号
	@Override
	public String createSerialNumber(String type, String prefix) {
		// prefix 前缀 如XA
		// 根据(预约，申请，合同类型 ) 查询生成序列号的类型
		SerialNumber serialnumber = serialnumberMapper.findByType(type);
		// 获取主键
		String id = serialnumber.getId();
		// 获取类型
		String types = serialnumber.getType();
		String typevals = serialnumber.getTypeval();
		// 序列号类型
		String model = serialnumber.getModel();
		String formatDatemodel = DateUtil.formart(model);

		// 获取自增的code，不能为空
		int code = serialnumber.getCode();
		code = code + 1;
		int codelength = serialnumber.getCodlength();
		// 获取编号的生成时间
		String createtime = serialnumber.getCreatetime();
		StringBuilder ft = new StringBuilder();
		for (int i = 0; i < codelength; i++) {
			ft.append("0");
		}
		if (formatDatemodel.equals(createtime)) {
			// 时间相等code+1 修改合同编号 001 变002
			SerialNumber updatenew = new SerialNumber();
			updatenew.setId(id);

			DecimalFormat decimalFormat = new DecimalFormat(ft.toString());
			String newcode = decimalFormat.format(code);

			updatenew.setCode(code);

			updatenew.setCodlength(codelength);

			updatenew.setCreatetime(formatDatemodel);

			updatenew.setModel(model);
			updatenew.setType(types);
			updatenew.setTypeval(typevals);

			serialnumberMapper.updateByPrimaryKey(updatenew);
			// 返回的编号code++
			String updateserialnumberold = prefix + formatDatemodel + newcode;

			return updateserialnumberold;
		} else {
			// 时间不等,编号code=000001
			SerialNumber updateOld = new SerialNumber();

			updateOld.setId(id);
			code = 1;
			DecimalFormat decimalFormat = new DecimalFormat(ft.toString());
			String oldcode = decimalFormat.format(code);
			updateOld.setCode(code);
			updateOld.setCodlength(codelength);
			updateOld.setCreatetime(formatDatemodel);
			updateOld.setModel(model);
			updateOld.setType(types);
			updateOld.setTypeval(typevals);

			serialnumberMapper.updateByPrimaryKey(updateOld);
			// 返回的编号
			String updateserialnumbernew = prefix + formatDatemodel + oldcode;
			return updateserialnumbernew;
		}

	}

	@Override
	public String createNumber(String type, String prefix) {
		Map<String, String> param = new HashMap<String,String>();
		param.put("p_type", type);
		param.put("p_prefix", prefix);
//		param.put("s_num", "");
		serialnumberMapper.getSerialNumber(param);
		String num = param.get("s_num").trim().toUpperCase();
		num = num.replaceAll(" ", "");
		num = num.replaceAll(" ", "");
		return num;
	}

}
