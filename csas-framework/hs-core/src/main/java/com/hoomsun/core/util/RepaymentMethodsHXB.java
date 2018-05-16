/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hoomsun.common.util.DateUtil;
import com.hoomsun.core.exception.CoreException;
import com.hoomsun.core.model.HxbReplaymentPlan;
import com.hoomsun.core.model.SysContract;
import com.hoomsun.core.model.SysProduct;
import com.hoomsun.core.model.SysProductAdvance;
import com.hoomsun.core.model.SysProductRate;
import com.hoomsun.core.model.SysRepaymentPlan;
import com.hoomsun.core.server.inter.HxbReplaymentPlanServerI;
import com.hoomsun.core.util.RepaymentMethods.Advance;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月27日 <br>
 * 描述：还款计划计算方法 等额本息 等额本金 按期预付利息到期还本 到期一次性结清
 */
public class RepaymentMethodsHXB {
	/**
	 * 账单日区间计算
	 */
	private static List<BillsDateRange> billsDateRanges = new ArrayList<BillsDateRange>();

	private static MathContext mc = new MathContext(6, RoundingMode.HALF_UP);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月27日 <br>
	 * 描述： 生成合同 计算划款计划 账单日计算 1-8 还款日是当月30号 9-24还款日是下月15号 25到月底最后一天还款日是下月30号
	 * 
	 * @param auditMoney
	 *            批核金额
	 * @param sign
	 *            签约日期
	 * @param product
	 *            到期时间
	 * @param type
	 *            还款方式
	 * @return
	 */
	public static SysContract computeRepaymentPlan(BigDecimal conAmount, BigDecimal payMonth, String applyId, BigDecimal auditMoney, Date sign, SysProduct product, BillsDateRangeCfg dateRangeUtil,
			HxbReplaymentPlanServerI hxbReplaymentPlanServer, Integer isOnline) {
		SysContract contract = null;
		if (null == sign) {
			throw new CoreException("签约时间不能为空！");
		}

		if (null == product) {
			throw new CoreException("签约产品不能不能为空！");
		}

		if (null != isOnline && isOnline == 1) {
			billsDateRanges = dateRangeUtil.getOnlineBillsDateRanges();
		} else {
			billsDateRanges = dateRangeUtil.getBillsDateRanges();
		}

		Integer day = getDay(sign);
		Integer weight = 0;
		Map<String, Integer> result = getBillDay(day, weight, isOnline);
		Integer payDate = result.get("day");// 还款日
		// 得到还款日
		Date start = getStartDate(sign, payDate, result.get("weight"));
		// 还款方式 （1：等额本息 2：等额本金 3 ：到期一次性结清 4：按期预付利息到期还本）
		Integer type = product.getPayType();
		switch (type) {
		case 1:// 等额本息
			contract = averageCapitalPlusInterest(conAmount, payMonth, applyId, auditMoney, sign, start, payDate, product, hxbReplaymentPlanServer);
			break;
		default:
			break;
		}
		return contract;
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月27日 <br>
	 * 描述： 等额本息的计算方式 月还款额 = [贷款本金×月利率×（1+月利率）^还款月数]÷[（1+月利率）^还款月数－1]
	 * 
	 * @param auditMoney
	 *            批核金额
	 * @param start
	 *            开始时间
	 * @param product
	 *            产品信息
	 * @return
	 */
	public static SysContract averageCapitalPlusInterest(BigDecimal conAmount, BigDecimal payMonth, String applyId, BigDecimal auditMoney, Date sign, Date start, Integer payDate, SysProduct product,
			HxbReplaymentPlanServerI hxbReplaymentPlanServer) {
		SysContract contract = new SysContract();
		contract.setProductPeriod(product.getSterm());
		contract.setProductRate(product.getYearRate());
		contract.setProductName(product.getProdName());
		contract.setProductId(product.getProdId());
		contract.setProductPay(product.getPayType());
		contract.setProductPayVal(product.getPayTypeVal());
		contract.setRealRate(product.getRealMonthRate());
		
		contract.setStartTime(start);
		contract.setSignTime(sign);

		// 年利率
		BigDecimal yearRate = product.getYearRate();
		// 期限
		Integer sterm = product.getSterm();
		BigDecimal bigSterm = BigDecimal.valueOf(sterm);
		contract.setEndTime(getEndTime(start, sterm));

		// 计算出合同金额
		// 费用信息
		// contract.setServiceFee(JSONObject.toJSONString(fee.getCost()));//费用详情

		// 费用合计(月/期)
		// BigDecimal feeSum = fee.sum();
		// contract.setSumFee(scale(feeSum.multiply(bigSterm,mc), 2));

		// 月利率
		BigDecimal monthRate = yearRate.divide(BigDecimal.valueOf(12), mc);

		List<SysProductAdvance> advances = product.getProductAdvances();// 提前还款参数设置
		List<SysRepaymentPlan> plans = buildPlan(applyId, sterm, sign, start, payDate, monthRate, conAmount, auditMoney, advances, payMonth, hxbReplaymentPlanServer);
		
		//综合年化成本
		BigDecimal irr = buildIRR(sterm, auditMoney, payMonth);
		contract.setIrrVal(irr.multiply(new BigDecimal(12), mc).multiply(new BigDecimal(100),mc));
		
		// 每月还款日
		contract.setPayDate(payDate.toString());
		// 合同金额
		contract.setConAmount(conAmount);
		// 放款金额
		contract.setLoanAmount(auditMoney);
		// 还款计划
		contract.setRepaymentPlans(plans);
		// 月还款额(线下算法)
		contract.setMonthMoney(payMonth);

		// 总利息 = 还款月数 × 每月月供额 - 贷款本金
		contract.setInterest((payMonth.multiply(bigSterm, mc)).subtract(conAmount));

		return contract;
	}

	private static List<SysRepaymentPlan> buildPlan(String applyId, Integer sterm, Date sign, Date start, Integer payDate, BigDecimal monthRate, BigDecimal conAmount, BigDecimal auditMoney, List<SysProductAdvance> advances,
			BigDecimal payMonth, HxbReplaymentPlanServerI hxbReplaymentPlanServer) {
		List<SysRepaymentPlan> plans = new ArrayList<SysRepaymentPlan>();
		// boolean isSign = false;
		// if (firstMonth != null && firstMonth != BigDecimal.ZERO) {
		// isSign = true;
		// sterm = sterm + 1;
		// }

		// 剩余本金 = 上期剩余本金 - 上期月还利息
		// 提前还款 = 本期月还本金 + 剩余本金
		// 月还利息 = 剩余本金 * 月利息
		// 月还本金 = 月还 - 月还利息
		/**
		 * 每月月供额 =〔贷款本金 × 月利率 × (1+月利率)^还款月数〕÷〔(1＋月利率)＾还款月数-1〕 每月应还利息 = 贷款本金 ×
		 * 月利率 ×〔(1+月利率)^还款月数-(1+月利率)^(还款月序号-1)〕÷〔(1+月利率)^还款月数-1〕 每月应还本金 = 贷款本金
		 * × 月利率 × (1+月利率)^(还款月序号-1)÷〔(1+月利率)^还款月数-1〕 总利息 = 还款月数 × 每月月供额 - 贷款本金
		 */
		Date shouldDate = start;
		BigDecimal bal = scale(conAmount, 2);// 剩余本金 = 上期剩余本金 - 月还本金
		BigDecimal preretutotalamt = BigDecimal.ZERO;// 提前还款
		BigDecimal shouldCapi = BigDecimal.ZERO;// 月还本金
		BigDecimal shouldInte = BigDecimal.ZERO;// 月还利息
		BigDecimal shouldAmt = BigDecimal.ZERO;// 应还金额
		monthRate = monthRate.divide(new BigDecimal(100), mc);
		payMonth = scale(payMonth, 2);
		List<HxbReplaymentPlan> planHxb = hxbReplaymentPlanServer.selectByApplyId(applyId);
		if (planHxb.size() > 0 && planHxb.size() == sterm) {
			// 往线下还款明细表存入红小宝还款计划表
			for (int i = 1; i <= sterm; i++) {
				HxbReplaymentPlan hxb = hxbReplaymentPlanServer.selectByApplyIdAndPhaseNumber(applyId, i);
				SysRepaymentPlan plan = new SysRepaymentPlan();
				plan.setShouldTerm(i);// 应还期数

				if (i == sterm) {// 最后一期
					plan.setPreretuamtCredit(hxb.getCreditServiceFee());// 提前还清减免渠道服务费2（z2）
					plan.setPreretuamtHxb(hxb.getHxbServiceFee());// 提前还清减免红小宝平台服务费
					plan.setPreretuamtChannel(hxb.getChannelServiceFee());// 提前还清减免渠道服务费1（z1）给至信
					plan.setBal(hxb.getInitialPrincipal());// 剩余本金(期初)
					plan.setEndBal(hxb.getFinalPrincipal());// 剩余本金(期末)
					plan.setShouldDate(shouldDate);
					plan.setShouldCapi(hxb.getPrincipal());// 应还本金
					plan.setShouldInte(hxb.getInterest());// 应还利息
					plan.setShouldAmt(hxb.getAmount());// 应还金额=应还利息+应还本金
					plan.setRepayStatus(0);
					// 减免金额
					BigDecimal reduce = hxb.getCreditServiceFee().add(hxb.getHxbServiceFee()).add(hxb.getChannelServiceFee());
					plan.setAdvanceReduce(reduce);
					plan.setAdvanceShould(hxb.getInRepayTotalAmount().add(reduce));
					plan.setAdvanceMoney(hxb.getInRepayTotalAmount());
				} else {
					plan.setPreretuamtCredit(hxb.getCreditServiceFee());// 提前还清减免渠道服务费2（z2）
					plan.setPreretuamtHxb(hxb.getHxbServiceFee());// 提前还清减免红小宝平台服务费
					plan.setPreretuamtChannel(hxb.getChannelServiceFee());// 提前还清减免渠道服务费1（z1）给至信
					plan.setBal(hxb.getInitialPrincipal());// 剩余本金
					plan.setEndBal(hxb.getFinalPrincipal());// 剩余本金(期末)
					plan.setShouldDate(shouldDate);
					plan.setShouldCapi(hxb.getPrincipal());// 应还本金
					plan.setShouldInte(hxb.getInterest());// 应还利息
					plan.setShouldAmt(hxb.getAmount());// 应还金额=应还利息+应还本金
					plan.setRepayStatus(0);
					// 减免金额
					BigDecimal reduce = hxb.getCreditServiceFee().add(hxb.getHxbServiceFee()).add(hxb.getChannelServiceFee());
					plan.setAdvanceReduce(reduce);
					plan.setAdvanceShould(hxb.getInRepayTotalAmount().add(reduce));
					plan.setAdvanceMoney(hxb.getInRepayTotalAmount()); // 实际提前还款(一次性还款)

					// 下一期时间
					shouldDate = nextPayDate(shouldDate, 1, payDate);
					// 剩余本金
				}
				plans.add(plan);
			}
		} else {
			// 线下还款明细表
			for (int i = 1; i <= sterm; i++) {
				SysRepaymentPlan plan = new SysRepaymentPlan();
				plan.setShouldTerm(i);// 应还期数

				if (i == sterm) {// 最后一期
					shouldInte = scale(bal.multiply(monthRate, mc), 2);
					shouldCapi = payMonth.subtract(shouldInte, mc);
					shouldAmt = payMonth;
					// preretutotalamt = shouldCapi.add(bal, mc);
					preretutotalamt = bal.add(payMonth, mc);

					plan.setBal(bal);
					plan.setShouldDate(shouldDate);
					plan.setShouldCapi(shouldCapi);
					plan.setShouldInte(shouldInte);
					plan.setShouldAmt(shouldAmt);
					plan.setRepayStatus(0);

					// 减免金额
					BigDecimal reduce = getAdvances(conAmount, auditMoney, advances, bal, sterm, i);
					plan.setAdvanceReduce(reduce);
					plan.setAdvanceShould(preretutotalamt);

					plan.setAdvanceMoney(preretutotalamt.subtract(reduce));
				} else {
					shouldInte = scale(bal.multiply(monthRate, mc), 2);// 月还利息 =
																		// 剩余本金
																		// *
																		// 月利息
					shouldCapi = payMonth.subtract(shouldInte, mc);// 月还 - 月还利息
					shouldAmt = payMonth;
					// preretutotalamt = shouldCapi.add(bal, mc);
					preretutotalamt = bal.add(payMonth, mc);

					plan.setBal(bal);// 剩余本金(期初)
					plan.setShouldDate(shouldDate);
					plan.setShouldCapi(shouldCapi);
					plan.setShouldInte(shouldInte);
					plan.setShouldAmt(shouldAmt);
					plan.setRepayStatus(0);
					// 减免金额 提前还款退费
					BigDecimal reduce = getAdvances(conAmount, auditMoney, advances, bal, sterm, i);
					plan.setAdvanceReduce(reduce);// 提前还款减免
					plan.setAdvanceShould(preretutotalamt);// 提前还款应还
					plan.setAdvanceMoney(preretutotalamt.subtract(reduce));// 实际提前还款(一次性还款)

					// 下一期时间
					shouldDate = nextPayDate(shouldDate, 1, payDate);
					// 剩余本金
					bal = bal.subtract(shouldCapi, mc);
					plan.setEndBal(bal);// 剩余本金(期末)
				}
				plans.add(plan);
			}
		}

		return plans;
	}

	// 提前还款
	// 0：剩余本金 1：总费用 2：合同金额 3：放款金额
	private static BigDecimal getAdvances(BigDecimal conAmount, BigDecimal auditMoney, List<SysProductAdvance> advances, BigDecimal bal, Integer sterm, Integer step) {
		if (advances != null && advances.size() > 1 && !advances.isEmpty()) {
			// 获取计算类型 和 计算比例 某一期
			for (SysProductAdvance ad : advances) {
				Integer start = ad.getStartPeriods() == null ? -1 : ad.getStartPeriods();
				Integer end = ad.getEndPeriods() == null ? -1 : ad.getEndPeriods();
				if (start <= step && step <= end) {
					BigDecimal rate = ad.getRate();
					Integer computeType = ad.getComputeType();

					BigDecimal adv = BigDecimal.ZERO;

					rate = rate.divide(new BigDecimal(100), mc);
					switch (computeType) {
					case 0:// 剩余本金 bal
						adv = bal.multiply(rate, mc);
						break;
					case 1:// 总费用 conAmount - auditMoney
						adv = (conAmount.subtract(auditMoney)).multiply(rate, mc);
						break;
					case 2:// 合同金额 conAmount
						adv = conAmount.multiply(rate, mc);
						break;
					case 3:// 放款金额 auditMoney
						adv = auditMoney.multiply(rate, mc);
						break;
					default:
						break;
					}
					return adv;
				}
			}
		} else {
			List<Advance> defaultAdvance = initAdvance(sterm);
			for (Advance advance : defaultAdvance) {
				Integer start = advance.getStart() == null ? -1 : advance.getStart();
				Integer end = advance.getEnd() == null ? -1 : advance.getEnd();
				if (start <= step && step <= end) {
					BigDecimal rate = advance.getValue();
					Integer computeType = 1;

					BigDecimal adv = BigDecimal.ZERO;

					rate = rate.divide(new BigDecimal(100), mc);
					switch (computeType) {
					case 0:// 剩余本金 bal
						adv = bal.multiply(rate, mc);
						break;
					case 1:// 总费用 conAmount - auditMoney
						adv = (conAmount.subtract(auditMoney)).multiply(rate, mc);
						break;
					case 2:// 合同金额 conAmount
						adv = conAmount.multiply(rate, mc);
						break;
					case 3:// 放款金额 auditMoney
						adv = auditMoney.multiply(rate, mc);
						break;
					default:
						break;
					}
					return adv;
				}
			}
		}
		return BigDecimal.ZERO;
	}

	private static List<Advance> initAdvance(Integer periods) {
		List<Advance> advances = new ArrayList<RepaymentMethods.Advance>();
		Advance advance = new RepaymentMethods().new Advance();
		switch (periods) {
		case 12:
			advance.setStart(1);
			advance.setEnd(3);
			advance.setValue(new BigDecimal(55));
			advances.add(advance);

			advance = new RepaymentMethods().new Advance();
			advance.setStart(4);
			advance.setEnd(12);
			advance.setValue(BigDecimal.ZERO);
			advances.add(advance);
			break;
		case 18:
			advance = new RepaymentMethods().new Advance();
			advance.setStart(1);
			advance.setEnd(3);
			advance.setValue(new BigDecimal(70));
			advances.add(advance);

			advance = new RepaymentMethods().new Advance();
			advance.setStart(4);
			advance.setEnd(6);
			advance.setValue(new BigDecimal(47));
			advances.add(advance);

			advance = new RepaymentMethods().new Advance();
			advance.setStart(7);
			advance.setEnd(18);
			advance.setValue(BigDecimal.ZERO);
			advances.add(advance);
			break;
		case 24:
			advance = new RepaymentMethods().new Advance();
			advance.setStart(1);
			advance.setEnd(3);
			advance.setValue(new BigDecimal(73));
			advances.add(advance);

			advance = new RepaymentMethods().new Advance();
			advance.setStart(4);
			advance.setEnd(6);
			advance.setValue(new BigDecimal(60));
			advances.add(advance);

			advance = new RepaymentMethods().new Advance();
			advance.setStart(7);
			advance.setEnd(12);
			advance.setValue(new BigDecimal(25));
			advances.add(advance);

			advance = new RepaymentMethods().new Advance();
			advance.setStart(13);
			advance.setEnd(24);
			advance.setValue(BigDecimal.ZERO);
			advances.add(advance);
			break;
		case 36:
			advance = new RepaymentMethods().new Advance();
			advance.setStart(1);
			advance.setEnd(3);
			advance.setValue(new BigDecimal(79));
			advances.add(advance);

			advance = new RepaymentMethods().new Advance();
			advance.setStart(4);
			advance.setEnd(6);
			advance.setValue(new BigDecimal(72));
			advances.add(advance);

			advance = new RepaymentMethods().new Advance();
			advance.setStart(7);
			advance.setEnd(12);
			advance.setValue(new BigDecimal(55));
			advances.add(advance);

			advance = new RepaymentMethods().new Advance();
			advance.setStart(13);
			advance.setEnd(18);
			advance.setValue(new BigDecimal(33));
			advances.add(advance);

			advance = new RepaymentMethods().new Advance();
			advance.setStart(19);
			advance.setEnd(36);
			advance.setValue(BigDecimal.ZERO);
			advances.add(advance);
			break;
		case 48:
			advance = new RepaymentMethods().new Advance();
			advance.setStart(1);
			advance.setEnd(3);
			advance.setValue(new BigDecimal(79));
			advances.add(advance);

			advance = new RepaymentMethods().new Advance();
			advance.setStart(4);
			advance.setEnd(6);
			advance.setValue(new BigDecimal(72));
			advances.add(advance);

			advance = new RepaymentMethods().new Advance();
			advance.setStart(7);
			advance.setEnd(12);
			advance.setValue(new BigDecimal(60));
			advances.add(advance);

			advance = new RepaymentMethods().new Advance();
			advance.setStart(13);
			advance.setEnd(18);
			advance.setValue(new BigDecimal(40));
			advances.add(advance);

			advance = new RepaymentMethods().new Advance();
			advance.setStart(19);
			advance.setEnd(24);
			advance.setValue(new BigDecimal(20));
			advances.add(advance);

			advance = new RepaymentMethods().new Advance();
			advance.setStart(25);
			advance.setEnd(48);
			advance.setValue(BigDecimal.ZERO);
			advances.add(advance);
			break;
		default:
			break;
		}
		return advances;
	}

	// [贷款本金×月利率×（1+月利率）^还款月数]÷[（1+月利率）^还款月数－1]
	/**
	 * 每月月供额 =〔贷款本金 × 月利率 × (1+月利率)^还款月数〕÷〔(1＋月利率)＾还款月数-1〕 每月应还利息 = 贷款本金 × 月利率
	 * ×〔(1+月利率)^还款月数-(1+月利率)^(还款月序号-1)〕÷〔(1+月利率)^还款月数-1〕 每月应还本金 = 贷款本金 × 月利率 ×
	 * (1+月利率)^(还款月序号-1)÷〔(1+月利率)^还款月数-1〕 总利息 = 还款月数 × 每月月供额 - 贷款本金
	 */
	public static BigDecimal getMonthPay(BigDecimal money, Integer sterm, BigDecimal monthRate) {
		BigDecimal monthPay = BigDecimal.ZERO;
		BigDecimal one = BigDecimal.ONE;
		monthRate = monthRate.divide(new BigDecimal(100), mc);
		monthPay = (money.multiply(monthRate, mc).multiply(((one.add(monthRate)).pow(sterm, mc)), mc)).divide((one.add(monthRate)).pow(sterm, mc).subtract(one), mc);
		return monthPay;
	}

	// 处理小数
	public static BigDecimal scale(BigDecimal data, Integer scale) {
		scale = scale == null ? 2 : scale;
		return data.setScale(scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月27日 <br>
	 * 描述：获取每月要收取的费用数据
	 * 
	 * @param auditMoney
	 *            审核金额
	 * @param productRates
	 *            产品费用信息
	 * @return
	 */
	public static Fee buildFee(BigDecimal auditMoney, List<SysProductRate> productRates, BigDecimal sterm) {
		Fee fee = new RepaymentMethodsHXB().new Fee();
		for (SysProductRate rate : productRates) {
			Integer isperiod = rate.getIsperiod();// 是否按期收取(0:false 1:true)
			Integer charging = rate.getCharging();// 收费方式（0:预收、1后收）
			Integer iscapital = rate.getIscapital();// 是否作为本金(0:false 1:true)
			Integer compute = rate.getCompute();// 计费方式（0本金、1笔）
			String name = rate.getPrType();

			BigDecimal proportion = rate.getProportion().divide(new BigDecimal(100), mc);// 计算比例

			// 【按期】【预收】【作为本金】
			if (isperiod == 1 && charging == 0 && iscapital == 1) {
				BigDecimal aqyszbj = fee.getAqyszbj();
				if (compute == 0) {// 本金
					// 月费率 * 批核 * 期限
					aqyszbj = proportion.multiply(auditMoney, mc);
					fee.putCost(name, scale(aqyszbj.multiply(sterm, mc), 2));
				} else if (compute == 1) {// 笔
					aqyszbj = proportion;
					fee.putCost(name, scale(aqyszbj, 2));
				}
				fee.addAqyszbj(aqyszbj);
			}

			// 【按期】【预收】【不作为本金】
			if (isperiod == 1 && charging == 0 && iscapital == 0) {
				BigDecimal aqysbzbj = fee.getAqysbzbj();
				if (compute == 0) {// 本金
					// 月费率 * 批核 * 期限
					aqysbzbj = proportion.multiply(auditMoney, mc);
					fee.putCost(name, scale(aqysbzbj.multiply(sterm, mc), 2));
				} else if (compute == 1) {// 笔
					aqysbzbj = proportion;
					fee.putCost(name, scale(aqysbzbj, 2));
				}
				fee.addAqysbzbj(aqysbzbj);
			}

			// 【按期】【后收】【作为本金】
			if (isperiod == 1 && charging == 1 && iscapital == 1) {
				BigDecimal aqhszbj = fee.getAqhszbj();
				if (compute == 0) {// 本金
					// 月费率 * 批核 * 期限
					aqhszbj = proportion.multiply(auditMoney, mc);
					fee.putCost(name, scale(aqhszbj.multiply(sterm, mc), 2));
				} else if (compute == 1) {// 笔
					aqhszbj = proportion;
					fee.putCost(name, scale(aqhszbj, 2));
				}
				fee.addAqhszbj(aqhszbj);
			}

			// 【按期】【后收】【不作为本金】
			if (isperiod == 1 && charging == 1 && iscapital == 0) {
				BigDecimal aqhsbzbj = fee.getAqhsbzbj();
				if (compute == 0) {// 本金
					// 月费率 * 批核 * 期限
					aqhsbzbj = proportion.multiply(auditMoney, mc);
					fee.putCost(name, scale(aqhsbzbj.multiply(sterm, mc), 2));
				} else if (compute == 1) {// 笔
					aqhsbzbj = proportion;
					fee.putCost(name, scale(aqhsbzbj, 2));
				}
				fee.addAqhsbzbj(aqhsbzbj);
			}

			// 【不按期】【预收】【作为本金】现有业务方式
			if (isperiod == 0 && charging == 0 && iscapital == 1) {
				BigDecimal baqyszbj = fee.getBaqyszbj();
				if (compute == 0) {// 本金
					// 月费率 * 批核 * 期限
					baqyszbj = proportion.multiply(auditMoney, mc);
					fee.putCost(name, scale(baqyszbj.multiply(sterm, mc), 2));
				} else if (compute == 1) {// 笔
					baqyszbj = proportion;
					fee.putCost(name, scale(baqyszbj, 2));
				}
				fee.addBaqyszbj(baqyszbj);
			}

			// 【不按期】【预收】【不作为本金】
			if (isperiod == 0 && charging == 0 && iscapital == 0) {
				BigDecimal baqysbzbj = fee.getBaqysbzbj();
				if (compute == 0) {// 本金
					// 月费率 * 批核 * 期限
					baqysbzbj = proportion.multiply(auditMoney, mc);
					fee.putCost(name, scale(baqysbzbj.multiply(sterm, mc), 2));
				} else if (compute == 1) {// 笔
					baqysbzbj = proportion;
					fee.putCost(name, scale(baqysbzbj, 2));
				}
				fee.addBaqysbzbj(baqysbzbj);
			}

			// 【不按期】【后收】【作为本金】
			if (isperiod == 0 && charging == 1 && iscapital == 1) {
				BigDecimal baqhszbj = fee.getBaqhszbj();
				if (compute == 0) {// 本金
					// 月费率 * 批核 * 期限
					baqhszbj = proportion.multiply(auditMoney, mc);
					fee.putCost(name, scale(baqhszbj.multiply(sterm, mc), 2));
				} else if (compute == 1) {// 笔
					baqhszbj = proportion;
					fee.putCost(name, scale(baqhszbj, 2));
				}
				fee.addBaqhszbj(baqhszbj);
			}

			// 【不按期】【后收】【不作为本金】
			if (isperiod == 0 && charging == 1 && iscapital == 0) {
				BigDecimal baqhsbzbj = fee.getBaqhsbzbj();
				if (compute == 0) {// 本金
					// 月费率 * 批核 * 期限
					baqhsbzbj = proportion.multiply(auditMoney, mc);
					fee.putCost(name, scale(baqhsbzbj.multiply(sterm, mc), 2));
				} else if (compute == 1) {// 笔
					baqhsbzbj = proportion;
					fee.putCost(name, scale(baqhsbzbj, 2));
				}
				fee.addBaqhsbzbj(baqhsbzbj);
			}
		}
		return fee;
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月27日 <br>
	 * 描述： 获取起息时间
	 * 
	 * @param sign
	 *            签约日期
	 * @param day
	 *            签约时那天
	 * @param weight
	 *            权重值
	 * @return
	 */
	private static Date getStartDate(Date sign, Integer day, Integer weight) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sign);
		Integer month = calendar.get(Calendar.MONTH);
		Integer year = calendar.get(Calendar.YEAR);
		// 1-8 还款日是当月30号 9-24还款日是下月15号 25到月底最后一天还款日是下月30号
		if (month == 1) {// 不为2月的以传入的数据为准
			if (weight > 0 ) {
				calendar.add(Calendar.MONTH, weight);
				calendar.set(Calendar.DAY_OF_MONTH, day);
			} else {
				if (day < 28) {
					calendar.set(Calendar.DAY_OF_MONTH, day);
				}else {
					if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {// 闰年 29
						calendar.set(Calendar.DAY_OF_MONTH, 29);
					} else {// 28 平年
						calendar.set(Calendar.DAY_OF_MONTH, 28);
					}
				}
			}
		} else {
			if (month == 0) {
				if (weight > 0) {
					calendar.add(Calendar.MONTH, weight);
					if (day >= 28) {
						if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {// 闰年  29
							calendar.set(Calendar.DAY_OF_MONTH, 29);
						} else {// 28 平年
							calendar.set(Calendar.DAY_OF_MONTH, 28);
						}
					} else {
						calendar.set(Calendar.DAY_OF_MONTH, day);
					}
				} else {
					calendar.set(Calendar.DAY_OF_MONTH, day);
				}
			} else {
				calendar.add(Calendar.MONTH, weight);
				calendar.set(Calendar.DAY_OF_MONTH, day);
			}
		}

//		calendar.set(Calendar.HOUR_OF_DAY, 0);
//		calendar.set(Calendar.MINUTE, 0);
//		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月27日 <br>
	 * 描述： 计算出账单日和计算权重值
	 * 
	 * @param day
	 *            几号
	 * @param weight
	 *            权重值
	 */
	private static Map<String, Integer> getBillDay(Integer day, Integer weight, Integer isOnline) {
		if (null == billsDateRanges || billsDateRanges.size() < 1) {
			if (null != isOnline && isOnline == 1) {
				initOnlineRanges();
			} else {
				initRanges();
			}
		}

		for (BillsDateRange range : billsDateRanges) {
			Integer start = range.getStart();
			Integer end = range.getEnd();
			if (start == -1 && end == -1) {
				break;
			}

			if (start != -1 && end != -1) {
				if (start <= day && day <= end) {
					day = range.getValue();
					weight = range.getWeight();
					break;
				} else {
					continue;
				}
			}

			if (start == -1 && end != -1) {
				if (day <= end) {
					day = range.getValue();
					weight = range.getWeight();
					break;
				} else {
					continue;
				}
			}

			if (start != -1 && end == -1) {
				if (day >= start) {
					day = range.getValue();
					weight = range.getWeight();
					break;
				} else {
					continue;
				}
			}
		}

		Map<String, Integer> result = new HashMap<String, Integer>();
		result.put("day", day);
		result.put("weight", weight);
		return result;
	}

	// 1-8 还款日是当月30号 9-24还款日是下月15号 25到月底最后一天还款日是下月30号
	private static void initRanges() {
		billsDateRanges.add(new BillsDateRange(1, 8, 30, 0));
		billsDateRanges.add(new BillsDateRange(9, 24, 15, 1));
		billsDateRanges.add(new BillsDateRange(25, -1, 30, 1));
	}

	private static void initOnlineRanges() {
		billsDateRanges.add(new BillsDateRange(1, 5, 22, 0));
		billsDateRanges.add(new BillsDateRange(6, 16, 5, 1));
		billsDateRanges.add(new BillsDateRange(17, 25, 15, 1));
		billsDateRanges.add(new BillsDateRange(26, -1, 22, 1));
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月27日 <br>
	 * 描述： 获取账单日 签约时间是几号
	 * 
	 * @return
	 */
	private static Integer getDay(Date sign) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sign);
		Integer day = calendar.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	private static Date getEndTime(Date start, Integer step) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		calendar.add(Calendar.MONTH, (step - 1));
		// calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + );
		return calendar.getTime();
	}

	private static Date nextPayDate(Date shouldDate, Integer step, Integer payDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(shouldDate);
		Integer year = calendar.get(Calendar.YEAR);
		Integer month = calendar.get(Calendar.MONTH);

		if (month == 0 && payDate >= 28) { // 二月
			calendar.add(Calendar.MONTH, step);
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {// 闰年 29
				calendar.set(Calendar.DAY_OF_MONTH, 29);
			} else {// 28 平年
				calendar.set(Calendar.DAY_OF_MONTH, 28);
			}
		} else {
			calendar.add(Calendar.MONTH, step);
			calendar.set(Calendar.DAY_OF_MONTH, payDate);
		}
		return calendar.getTime();
	}

	public static void main(String[] args) throws ParseException {
		Date date = DateUtil.yyyyMMdd.parse("2018-02-06");
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(date);
		// calendar.add(Calendar.MONTH, 1);
		// System.out.println(DateUtil.yyyyMMdd.format(calendar.getTime()));

		// 1-8 还款日是当月30号 9-24还款日是下月15号 25到月底最后一天还款日是下月30号
		//	    线上账单日为：5、15、22：
		//        1号——5号为当月22日；
		//        6号——16日为次月5日；
		//        17号——25号为次月15日；
		//        26号——月底为次月22日；
		//线下账单日为：15、30：
		//        1号——8号为当月30日（28、29）；
		//        9号——24号为次月15日；
		//        25号——31号为次月30日（28、29）；
		System.out.println(DateUtil.yyyyMMdd.format(getStartDate(date, 5, 1)));

		System.out.println(DateUtil.yyyyMMdd.format(nextDate(date, 30)));
		System.out.println("***" + DateUtil.yyyyMMdd.format(nextPayDate(date, 1, 30)));
	}

	private static Date nextDate(Date shouldDate, Integer payDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(shouldDate);
		Integer year = calendar.get(Calendar.YEAR);
		Integer month = calendar.get(Calendar.MONTH);

		if (month == 0 && payDay >= 28) {// 1月
			calendar.set(Calendar.MONTH, 1);
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {// 闰年 29
				calendar.set(Calendar.DAY_OF_MONTH, 29);
			} else {// 28 平年
				calendar.set(Calendar.DAY_OF_MONTH, 28);
			}
		} else {
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
			calendar.set(Calendar.DAY_OF_MONTH, payDay);
		}
		return calendar.getTime();
	}

	public class Fee {
		// 【按期】【预收】【作为本金】
		private BigDecimal aqyszbj = BigDecimal.ZERO;
		// 【按期】【预收】【不作为本金】
		private BigDecimal aqysbzbj = BigDecimal.ZERO;
		// 【按期】【后收】【作为本金】 现有业务方式
		private BigDecimal aqhszbj = BigDecimal.ZERO;
		// 【按期】【后收】【不作为本金】
		private BigDecimal aqhsbzbj = BigDecimal.ZERO;
		// 【不按期】【预收】【作为本金】
		private BigDecimal baqyszbj = BigDecimal.ZERO;
		// 【不按期】【预收】【不作为本金】
		private BigDecimal baqysbzbj = BigDecimal.ZERO;
		// 【不按期】【后收】【不作为本金】
		private BigDecimal baqhszbj = BigDecimal.ZERO;
		// 【不按期】【后收】【不作为本金】
		private BigDecimal baqhsbzbj = BigDecimal.ZERO;

		private Map<String, Object> cost = new HashMap<String, Object>();

		public void putCost(String key, BigDecimal value) {
			cost.put(key, value);
		}

		public BigDecimal getAqyszbj() {
			return aqyszbj;
		}

		public void setAqyszbj(BigDecimal aqyszbj) {
			this.aqyszbj = aqyszbj;
		}

		public void addAqyszbj(BigDecimal aqyszbj) {
			this.aqyszbj = this.aqyszbj.add(aqyszbj);
		}

		public BigDecimal getAqysbzbj() {
			return aqysbzbj;
		}

		public void setAqysbzbj(BigDecimal aqysbzbj) {
			this.aqysbzbj = aqysbzbj;
		}

		public void addAqysbzbj(BigDecimal aqysbzbj) {
			this.aqysbzbj = this.aqysbzbj.add(aqysbzbj);
		}

		public BigDecimal getAqhszbj() {
			return aqhszbj;
		}

		public void setAqhszbj(BigDecimal aqhszbj) {
			this.aqhszbj = aqhszbj;
		}

		public void addAqhszbj(BigDecimal aqhszbj) {
			this.aqhszbj = this.aqhszbj.add(aqhszbj);
		}

		public BigDecimal getAqhsbzbj() {
			return aqhsbzbj;
		}

		public void setAqhsbzbj(BigDecimal aqhsbzbj) {
			this.aqhsbzbj = aqhsbzbj;
		}

		public void addAqhsbzbj(BigDecimal aqhsbzbj) {
			this.aqhsbzbj = this.aqhsbzbj.add(aqhsbzbj);
		}

		public BigDecimal getBaqyszbj() {
			return baqyszbj;
		}

		public void setBaqyszbj(BigDecimal baqyszbj) {
			this.baqyszbj = baqyszbj;
		}

		public void addBaqyszbj(BigDecimal baqyszbj) {
			this.baqyszbj = this.baqyszbj.add(baqyszbj);
		}

		public BigDecimal getBaqysbzbj() {
			return baqysbzbj;
		}

		public void setBaqysbzbj(BigDecimal baqysbzbj) {
			this.baqysbzbj = baqysbzbj;
		}

		public void addBaqysbzbj(BigDecimal baqysbzbj) {
			this.baqysbzbj = this.baqysbzbj.add(baqysbzbj);
		}

		public BigDecimal getBaqhszbj() {
			return baqhszbj;
		}

		public void setBaqhszbj(BigDecimal baqhszbj) {
			this.baqhszbj = baqhszbj;
		}

		public void addBaqhszbj(BigDecimal baqhszbj) {
			this.baqhszbj = this.baqhszbj.add(baqhszbj);
		}

		public BigDecimal getBaqhsbzbj() {
			return baqhsbzbj;
		}

		public void setBaqhsbzbj(BigDecimal baqhsbzbj) {
			this.baqhsbzbj = baqhsbzbj;
		}

		public void addBaqhsbzbj(BigDecimal baqhsbzbj) {
			this.baqhsbzbj = this.baqhsbzbj.add(baqhsbzbj);
		}

		public Map<String, Object> getCost() {
			return cost;
		}

		public void setCost(Map<String, Object> cost) {
			this.cost = cost;
		}

		/**
		 * 作者：ywzou <br>
		 * 创建时间：2017年11月27日 <br>
		 * 描述： 费用合计
		 * 
		 * @return
		 */
		public BigDecimal sum() {
			BigDecimal sum = this.aqyszbj.add(aqysbzbj).add(aqhszbj).add(aqhsbzbj).add(baqyszbj).add(baqysbzbj).add(baqhszbj).add(baqhsbzbj);
			return sum;
		}

		@Override
		public String toString() {
			return "Fee [aqyszbj=" + aqyszbj + ", aqysbzbj=" + aqysbzbj + ", aqhszbj=" + aqhszbj + ", aqhsbzbj=" + aqhsbzbj + ", baqyszbj=" + baqyszbj + ", baqysbzbj=" + baqysbzbj + ", baqhszbj=" + baqhszbj + ", baqhsbzbj=" + baqhsbzbj
					+ "]";
		}
	}

	public static List<BillsDateRange> getBillsDateRanges() {
		return billsDateRanges;
	}

	public static void setBillsDateRanges(List<BillsDateRange> billsDateRanges) {
		RepaymentMethodsHXB.billsDateRanges = billsDateRanges;
	}
	
	/** 迭代次数 */
	public static int LOOPNUM = 1000;
	/** 最小差异 */
	public static final double MINDIF = 0.00000001;

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月8日 <br>
	 * 描述： 综合年化成本
	 * 
	 * @param loanMoney
	 *            放款金额
	 * @param monthMoney
	 *            月还的数据集合
	 * @return
	 */
	public static BigDecimal buildIRR(BigDecimal loanMoney, List<BigDecimal> monthMoney) {
		loanMoney = loanMoney.multiply(new BigDecimal(-1));
		monthMoney.add(0, loanMoney);

		BigDecimal flowOut = monthMoney.get(0);
		BigDecimal minValue = BigDecimal.ZERO;
		BigDecimal maxValue = BigDecimal.ONE;
		BigDecimal irr = BigDecimal.ZERO;
		while (LOOPNUM > 0) {
			// testValue = (minValue + maxValue) / 2;
			irr = (minValue.add(maxValue)).divide(new BigDecimal(2), mc);
			BigDecimal npv = NPV(monthMoney, irr);
			if (Math.abs(flowOut.doubleValue() + npv.doubleValue()) < MINDIF) {
				break;
			} else if (Math.abs(flowOut.doubleValue()) > npv.doubleValue()) {
				maxValue = irr;
			} else {
				minValue = irr;
			}
			LOOPNUM--;
		}
		return irr;
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月8日 <br>
	 * 描述： 综合年化成本 只适用于【等额本息】 每月还款相同
	 * 
	 * @param periods
	 *            期数
	 * @param loanMoney
	 *            放款金额
	 * @param monthMoney
	 *            月还款额
	 * @return
	 */
	public static BigDecimal buildIRR(Integer periods, BigDecimal loanMoney, BigDecimal monthMoney) {
		loanMoney = loanMoney.multiply(new BigDecimal(-1));
		List<BigDecimal> flowInArr = new ArrayList<BigDecimal>();
		flowInArr.add(loanMoney);
		for (int i = 0; i < periods; i++) {
			flowInArr.add(monthMoney);
		}

		BigDecimal flowOut = flowInArr.get(0);
		BigDecimal minValue = BigDecimal.ZERO;
		BigDecimal maxValue = BigDecimal.ONE;
		BigDecimal irr = BigDecimal.ZERO;
		while (LOOPNUM > 0) {
			// testValue = (minValue + maxValue) / 2;
			irr = (minValue.add(maxValue)).divide(new BigDecimal(2), mc);
			BigDecimal npv = NPV(flowInArr, irr);
			if (Math.abs(flowOut.doubleValue() + npv.doubleValue()) < MINDIF) {
				break;
			} else if (Math.abs(flowOut.doubleValue()) > npv.doubleValue()) {
				maxValue = irr;
			} else {
				minValue = irr;
			}
			LOOPNUM--;
		}
		return irr;
	}

	public static BigDecimal NPV(List<BigDecimal> flowInArr, BigDecimal rate) {
		BigDecimal npv = BigDecimal.ZERO;
		for (int i = 1; i < flowInArr.size(); i++) {
			// npv += flowInArr.get(i) / Math.pow(1 + rate, i);
			BigDecimal temp = flowInArr.get(i).divide((BigDecimal.ONE.add(rate)).pow(i), mc);
			npv = npv.add(temp);
		}
		return npv;
	}
}
