/**
 * Copyright www.idawn.org 邹益伟 idawnorg@gmail.com
 */
package com.hoomsun.csas.sales.server.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.util.DateUtil;
import com.hoomsun.csas.sales.api.model.vo.HistoricTaskVo;
import com.hoomsun.csas.sales.api.model.vo.TrackVO;
import com.hoomsun.csas.sales.api.server.inter.TrackServerI;
import com.hoomsun.csas.sales.dao.ActivitiMapper;
import com.hoomsun.csas.sales.util.JumpTaskCmd;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月24日 <br>
 * 描述：简单的流程追踪
 */
@Service("trackServer")
public class TrackServerImpl implements TrackServerI {
	private ActivitiMapper activitiMapper;

	@Autowired
	public void setActivitiMapper(ActivitiMapper activitiMapper) {
		this.activitiMapper = activitiMapper;
	}

	@Override
	public List<TrackVO> trackByApplyId(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			return null;
		}
		List<HistoricTaskVo> historicTaskVos = activitiMapper.findHistoricTask(applyId);

		if (historicTaskVos != null && historicTaskVos.size() > 0) {
			List<TrackVO> trackVOs = new ArrayList<TrackVO>();
			for (HistoricTaskVo ht : historicTaskVos) {
				Date start = ht.getStartTime();
				Date claim = ht.getClaitmTime();
				Date end = ht.getEndTime();

				if (null != start) {
					if (null != claim) {// 未签收
						TrackVO vo = new TrackVO();
						vo.setTaskId(ht.getTaskId());
						vo.setTitle(ht.getName());
						vo.setDescription("未签收");
						trackVOs.add(vo);
					} else {
						TrackVO vo = new TrackVO();
						vo.setTaskId(ht.getTaskId());
						vo.setTitle(ht.getName());
						vo.setDescription("已签收【" + claim + "】");
						trackVOs.add(vo);

						if (null != end) {
							vo = new TrackVO();
							vo.setTaskId(ht.getTaskId());
							vo.setTitle(ht.getName());
							vo.setDescription("未审核");
							trackVOs.add(vo);
						} else {
							vo = new TrackVO();
							vo.setTaskId(ht.getTaskId());
							vo.setTitle(ht.getName());

							String taskKey = ht.getTaskDefKey();
							if (StringUtils.isNotBlank(taskKey)) {
								if ("finalAudit".equalsIgnoreCase(taskKey)) {// 如果是终审
									Integer option = ht.getHandleOption();
									if (option != null) {
										if (4 == option) {
											vo.setDescription("已审核【" + end + "】,审核意见：通过，批核产品：" + ht.getProduct() + "，批核金额：¥" + ht.getMoney());
										} else if (0 == option) {
											vo.setDescription("已审核【" + end + "】,审核意见：拒贷，原因：" + ht.getRemarks());
										} else {
											String optionVal = ht.getHandleOptionVal();
											vo.setDescription("已审核【" + end + "】,审核意见：" + optionVal);
										}
										trackVOs.add(vo);
									} else {
										vo.setDescription("已审核【" + end + "】,审核意见：未知");
										trackVOs.add(vo);
									}
								} else {
									String deleteReason = ht.getDeleteReason();
									String optionVal = getHandle(deleteReason);
									vo.setDescription("已审核【" + end + "】,审核意见：" + optionVal);
									trackVOs.add(vo);
								}
							} else {
								vo.setDescription("已审核【" + end + "】");
								trackVOs.add(vo);
							}
						}
					}
				}
			}
			return trackVOs;
		}

		return null;
	}

	private String getHandle(String deleteReason) {
		if (StringUtils.isBlank(deleteReason)) {
			return "未知";
		} else {
			if (JumpTaskCmd.COMPLETED.equals(deleteReason)) {
				return "通过";
			}

			if (JumpTaskCmd.WITHDRAW.equals(deleteReason)) {
				return "撤回";
			}

			if (JumpTaskCmd.ROLLBACK.equals(deleteReason)) {
				return "退回";
			}

			if (JumpTaskCmd.END.equals(deleteReason)) {
				return "结束";
			}

			if (JumpTaskCmd.REJECT.equals(deleteReason)) {
				return "拒贷";
			}

			if (JumpTaskCmd.WAIVER.equals(deleteReason)) {
				return "客户放弃";
			}
		}
		return "未知";
	}

	private String getNodeName(String taskKey) {
		if (StringUtils.isBlank(taskKey)) {
			return "未知";
		} else {
			if ("qcCheck".equals(taskKey)) {
				return "质检";
			}

			if ("preAudit".equals(taskKey)) {
				return "初审";
			}

			if ("finalAudit".equals(taskKey)) {
				return "终审";
			}

			if ("addData".equals(taskKey)) {
				return "补充资料";
			}

			if ("excessAudit".equals(taskKey)) {
				return "超额审批";
			}

			if ("makeCon".equals(taskKey)) {
				return "签约";
			}

			if ("conAudit".equals(taskKey)) {
				return "合同审查";
			}

			if ("loanAudit".equals(taskKey)) {
				return "撮配";
			}
		}
		return "未知";
	}

	@Override
	public List<TrackVO> trackSimplByApplyId(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			return null;
		}
		List<HistoricTaskVo> historicTaskVos = activitiMapper.findHistoricTask(applyId);

		if (historicTaskVos != null && historicTaskVos.size() > 0) {
			List<TrackVO> trackVOs = new ArrayList<TrackVO>();
			for (HistoricTaskVo ht : historicTaskVos) {
				Date start = ht.getStartTime();
				Date claim = ht.getClaitmTime();
				Date end = ht.getEndTime();
				String taskKey = ht.getTaskDefKey();
				String nodeName = getNodeName(taskKey);

				if (start != null) {
					TrackVO vo = new TrackVO();
					vo.setTaskId(ht.getTaskId());
					vo.setTitle(ht.getName());
					vo.setDescription("等待" + nodeName);
					vo.setHandelTime(DateUtil.yyyyMMddHHmmss.format(start));
					trackVOs.add(vo);
				}

				if (claim != null) {
					TrackVO vo = new TrackVO();
					vo.setTaskId(ht.getTaskId());
					vo.setTitle(ht.getName());
					vo.setDescription(nodeName + "中");
					vo.setHandelTime(DateUtil.yyyyMMddHHmmss.format(claim));
					trackVOs.add(vo);
				}

				if ("loanAudit".equalsIgnoreCase(taskKey)&&claim != null) {
					TrackVO vo = new TrackVO();
					vo.setTaskId(ht.getTaskId());
					vo.setTitle(ht.getName());
					vo.setDescription(nodeName + "成功");
					vo.setHandelTime(DateUtil.yyyyMMddHHmmss.format(claim));
					trackVOs.add(vo);
				}

				if ("finalAudit".equalsIgnoreCase(taskKey)) {// 如果是终审
					if (end != null) {
						Integer option = ht.getHandleOption();
						if (option != null) {
							TrackVO vo = new TrackVO();
							StringBuilder desc = new StringBuilder();
							if (option == 4) {
								desc.append("终审通过,");
								desc.append("批核金额:" + ht.getMoney());
								desc.append(",批核产品:" + ht.getProduct());
							} else if (option == 0) {
								desc.append("拒贷," + ht.getRejectType());
							} else {
								desc.append(ht.getHandleOptionVal());
							}
							vo.setTaskId(ht.getTaskId());
							vo.setTitle(ht.getName());
							vo.setDescription(desc.toString());
							vo.setHandelTime(DateUtil.yyyyMMddHHmmss.format(end));
							trackVOs.add(vo);
						}
					}
				}
			}
			return trackVOs;
		}

		return null;
	}
}
