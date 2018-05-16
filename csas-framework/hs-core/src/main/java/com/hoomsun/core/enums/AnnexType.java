/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hoomsun.core.model.vo.AnnexTypeVo;

/**
 * 作者：liusong <br>
 * 创建时间：2017年12月22日 <br>
 * 描述：影像资料类型
 */
public enum AnnexType {
	/**
	 * 身份证明 1
	 */
	ANNEX_PROOF_IDENTITY(1, "身份证明"),
	/**
	 * 收入证明 2
	 */
	ANNEX_INCOME_CERTIFICATE(2, "收入证明"),
	/**
	 * 信用报告 3
	 */
	ANNEX_CREDIT_REPORT(3, "信用报告"),
	/**
	 * 银行流水 4
	 */
	ANNEX_BANK_WATER(4, "银行流水"),
	/**
	 * 申请单 5
	 */
	ANNEX_APPLY_INFO(5, "申请单"),
	/**
	 * 签约资料 6
	 */
	ANNEX_CONTRACT_INFORMATION(6, "签约资料"),
	/**
	 * 工作证明 7
	 */
	ANNEX_WORK_CERTIFICATE(7, "工作证明"),
	/**
	 * 住址信息证明 8
	 */
	ANNEX_ADDR_CERTIFICATE(8, "住址信息证明"),
	/**
	 * 核查信息类 9
	 */
	ANNEX_CHECK_CERTIFICATE(9, "核查信息类"),
	/**
	 * 经营证明 10
	 */
	ANNEX_BUSINESS_CERTIFICATE(10, "经营证明"),
	/**
	 * 学历证明 11
	 */
	ANNEX_EDUCATIONAL_CERTIFICATE(11, "学历证明"),
	/**
	 * 房产证明 12
	 */
	ANNEX_HOUSE_CERTIFICATE(12, "房产证明"),
	/**
	 * 资产证明 13
	 */
	ANNEX_ASSETS_CERTIFICATE(13, "资产证明"),
	/**
	 * 其他类 14
	 */
	ANNEX_OTHERS_CERTIFICATE(14, "其他类"),
	/**
	 * 补充材料信息15
	 */
	ANNEX_SUPPLEMENTARY_MATERIAL(15, "补充材料信息"),

	/**
	 * 审核影像 16
	 */
	ANNEX_AUDIT(16, "审核影像"),
	
	/**
	 * 复议影像 17
	 */
	REVIEW_AUDIT(17, "复议影像"),
	/**
	 * 借款协议(借款人声明)书 18
	 */
	STATEMENT_AUDIT(18, "借款协议(借款人声明)书"),
	/**
	 * 授权委托书 19
	 */
	POWER_AUDIT(19, "委托划款授权书"),
	/**
	 * 信用咨询与管理服务协议书 20
	 */
	CREDIT_AUDIT(20, "信用咨询与管理服务协议书"),
	
	/**
	 * 还款管理书21
	 */
	REPLAYMENT_AUDIT(21, "还款管理书"),
	/**
	 * 还款管理书22
	 */
	SERVICE_AUDIT(22, "服务记录表"),
	/**
	 * 还款管理书23
	 */
	NOTICES_AUDIT(23, "还款事项提醒函"),
	/**
	 * 通话详单
	 */
	CALL_BILL(24, "通话详单");

	private Integer type;
	private String name;

	private AnnexType(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月26日 <br>
	 * 描述： 获取所有的印象类型
	 * 
	 * @return
	 */
	public static List<Map<String, Object>> allAnnexType() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (AnnexType type : AnnexType.values()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("label", type.getName());
			map.put("value", type.getType());
			result.add(map);
		}
		return result;
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月26日 <br>
	 * 描述： 获取所有的印象类型
	 * 
	 * @return
	 */
	public static List<Map<String, Object>> allAnnexType(AnnexType[] excludes) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (AnnexType type : AnnexType.values()) {
			for (AnnexType exclude : excludes) {
				if (exclude.getType().equals(type.getType())) {
					continue;
				}else {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("label", type.getName());
					map.put("value", type.getType());
					result.add(map);
				}
			}
		}
		return result;
	}

	public static List<AnnexTypeVo> allAnnexTypeList() {
		List<AnnexTypeVo> all = new ArrayList<AnnexTypeVo>();
		for (AnnexType type : AnnexType.values()) {
			AnnexTypeVo annex = new AnnexTypeVo(type.getType(), type.getName());
			all.add(annex);
		}
		return all;
	}

}
