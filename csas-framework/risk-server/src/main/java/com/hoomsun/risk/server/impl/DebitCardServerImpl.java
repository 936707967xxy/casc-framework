/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.server.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Result;
import com.hoomsun.risk.model.DebitCard;
import com.hoomsun.risk.model.DebitCard.DebitCardBill;
import com.hoomsun.risk.server.inter.DebitCardServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月6日 <br>
 * 描述：银行流水的业务实现
 */
@Service("debitCardServer")
public class DebitCardServerImpl implements DebitCardServerI {

	@Autowired
	private MongoTemplate mongoTemplate;
	private static MathContext mc = new MathContext(2, RoundingMode.HALF_EVEN);

	@Override
	public Result saveDebitCard(DebitCard debitCard) {
		if (null == debitCard) {
			return new Result(1001, "参数异常!");
		}

		debitCard.setCreateDate(new Date());
		buildDebitCard(debitCard);
		mongoTemplate.save(debitCard);

		return new Result(0000, "保存成功!");
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月6日 <br>
	 * 描述： 计算账单数据
	 * 
	 * @param debitCard
	 */
	public void buildDebitCard(DebitCard debitCard) {
		// 计算流水合计
		List<DebitCardBill> debitCardBills = debitCard.getDebitCardBills();

		DebitCardBill sumBill = new DebitCard.DebitCardBill();
		sumBill.setStreamVal(3);
		sumBill.setStreamName("总计");
		for (DebitCardBill debitCardBill : debitCardBills) {
			BigDecimal sum = BigDecimal.ZERO;// 和
			BigDecimal avg = BigDecimal.ZERO;// 平均值
			BigDecimal coeff = BigDecimal.ZERO;// 变异系数
			BigDecimal standard = BigDecimal.ZERO;// 标准差
			BigDecimal variance = BigDecimal.ZERO;// 方差
			BigDecimal median = BigDecimal.ZERO;// 中位数

			BigDecimal one = debitCardBill.getOneMonth();
			BigDecimal two = debitCardBill.getTwoMonth();
			BigDecimal three = debitCardBill.getThreeMonth();
			BigDecimal four = debitCardBill.getFourMonth();
			BigDecimal five = debitCardBill.getFiveMonth();
			BigDecimal six = debitCardBill.getSixMonth();

			sum = one.add(two).add(three).add(four).add(five).add(six);
			if (sum.equals(BigDecimal.ZERO)) {
				continue;
			}
			
			debitCardBill.setTotal(sum);
			avg = sum.divide(new BigDecimal(6), mc);
			debitCardBill.setTheAverage(avg);

			// 1.方差 s^2=[(x1-x)^2+(x2-x)^2+......(xn-x)^2]/n
			variance = ((one.subtract(avg).pow(2, mc)).add((two.subtract(avg).pow(2, mc))).add((three.subtract(avg).pow(2, mc))).add((four.subtract(avg).pow(2, mc))).add((five.subtract(avg).pow(2, mc))).add((six.subtract(avg).pow(2, mc))))
					.divide(new BigDecimal(6), mc);
			debitCardBill.setVariance(variance);
			// 2.标准差=方差的算术平方根 Math.sqrt() 开平方 Math.pow(27, 1/3) 就是对27的1/3次方
			// 就是根号3下的27
			standard = new BigDecimal(Math.sqrt(variance.doubleValue()));
			debitCardBill.setStandard(standard);

			median = buildMedian(one, two, three, four, five, six);
			debitCardBill.setMedian(median);

			// 变异系数CV = 标准差/平均数
			coeff = standard.divide(avg, mc);
			debitCardBill.setCoefficintVariation(coeff);

			one = one.add(debitCardBill.getOneMonth());
			two = two.add(debitCardBill.getTwoMonth());
			three = three.add(debitCardBill.getThreeMonth());
			four = four.add(debitCardBill.getFourMonth());
			five = five.add(debitCardBill.getFiveMonth());
			six = six.add(debitCardBill.getSixMonth());

			sumBill.setOneMonth(one);
			sumBill.setTwoMonth(two);
			sumBill.setThreeMonth(three);
			sumBill.setFourMonth(four);
			sumBill.setFiveMonth(five);
			sumBill.setSixMonth(six);
		}
		buildSumBill(sumBill);
		debitCard.addDebitCardBills(sumBill);

		// 收入稳定性的 收入趋势
		buildInCom(debitCard);
	}

	public void buildInCom(DebitCard debitCard) {
		List<DebitCardBill> debitCardBills = debitCard.getDebitCardBills();
		// 排序
		Collections.sort(debitCardBills, new BillComparator());

		int length = debitCardBills.size();

		DebitCardBill z = debitCardBills.get(length - 1);
		DebitCardBill y = debitCardBills.get(length - 2);
		DebitCardBill x = null;
		if (length >= 3) {
			x = debitCardBills.get(length - 3);
		}

		// 变异系数CV = (z1、z2、z3...z6)的标准差 / (z1、z2、z3...z6)的平均数
		BigDecimal cv = z.getStandard().divide(z.getTheAverage(), mc);

		// 1、当 (y1+y2+...y6)/(x1+x2+..x6) <= 0.4
		BigDecimal sumDiv = BigDecimal.ZERO;
		if (x != null && !x.getTotal().equals(BigDecimal.ZERO)) {
			sumDiv = y.getTotal().divide(x.getTotal(), mc);
			if (sumDiv.doubleValue() <= 0.4) {
				if (cv.doubleValue() < 0.05) {// 4非常稳定
					debitCard.setIncomeStabilityVal(4);
					debitCard.setIncomeStability("非常稳定");
				} else if (cv.doubleValue() < 0.1 && cv.doubleValue() >= 0.05) {// 1稳定
					debitCard.setIncomeStabilityVal(1);
					debitCard.setIncomeStability("稳定");
				} else if (cv.doubleValue() < 0.25 && cv.doubleValue() >= 0.1) {// 2不稳定
					debitCard.setIncomeStabilityVal(2);
					debitCard.setIncomeStability("不稳定");
				} else if (cv.doubleValue() >= 0.25) {// 3很不稳定
					debitCard.setIncomeStabilityVal(3);
					debitCard.setIncomeStability("很不稳定");
				}
			} else if (sumDiv.doubleValue() > 0.4) {
				if (cv.doubleValue() < 0.1) {// 4非常稳定
					debitCard.setIncomeStabilityVal(4);
					debitCard.setIncomeStability("非常稳定");
				} else if (cv.doubleValue() < 0.25 && cv.doubleValue() >= 0.1) {// 1稳定
					debitCard.setIncomeStabilityVal(1);
					debitCard.setIncomeStability("稳定");
				} else if (cv.doubleValue() < 0.5 && cv.doubleValue() >= 0.25) {// 2不稳定
					debitCard.setIncomeStabilityVal(2);
					debitCard.setIncomeStability("不稳定");
				} else if (cv.doubleValue() >= 0.5) {// 3很不稳定
					debitCard.setIncomeStabilityVal(3);
					debitCard.setIncomeStability("很不稳定");
				}
			}
		} else {// 私营业主
			if (cv.doubleValue() < 0.1) {// 4非常稳定
				debitCard.setIncomeStabilityVal(4);
				debitCard.setIncomeStability("非常稳定");
			} else if (cv.doubleValue() < 0.25 && cv.doubleValue() >= 0.1) {// 1稳定
				debitCard.setIncomeStabilityVal(1);
				debitCard.setIncomeStability("稳定");
			} else if (cv.doubleValue() < 0.5 && cv.doubleValue() >= 0.25) {// 2不稳定
				debitCard.setIncomeStabilityVal(2);
				debitCard.setIncomeStability("不稳定");
			} else if (cv.doubleValue() >= 0.5) {// 3很不稳定
				debitCard.setIncomeStabilityVal(3);
				debitCard.setIncomeStability("很不稳定");
			}
		}

		// MEAN 收入趋势：
		BigDecimal mean = z.getMedian();
		BigDecimal four = z.getFourMonth();
		BigDecimal five = z.getFiveMonth();
		BigDecimal six = z.getSixMonth();

		if (four.doubleValue() < mean.doubleValue() && five.doubleValue() < mean.doubleValue() && six.doubleValue() < mean.doubleValue()) {
			if (cv.doubleValue() < 0.1) {
				debitCard.setRevenueTrendVal(1);
				debitCard.setRevenueTrend("小幅下降");
			}else if (cv.doubleValue() >= 0.1) {
				debitCard.setRevenueTrendVal(2);
				debitCard.setRevenueTrend("明显下降");
			}
		}else if (four.doubleValue() > mean.doubleValue() && five.doubleValue() > mean.doubleValue() && six.doubleValue() > mean.doubleValue()) {
			if (cv.doubleValue() < 0.1) {
				debitCard.setRevenueTrendVal(4);
				debitCard.setRevenueTrend("小幅上升");
			}else if (cv.doubleValue() >= 0.1) {
				debitCard.setRevenueTrendVal(3);
				debitCard.setRevenueTrend("明显上升");
			}
		}else {
			debitCard.setRevenueTrendVal(5);
			debitCard.setRevenueTrend("其他");
		}
		
	}

	public void buildSumBill(DebitCardBill sumBill) {
		BigDecimal sum = BigDecimal.ZERO;// 和
		BigDecimal avg = BigDecimal.ZERO;// 平均值
		BigDecimal coeff = BigDecimal.ZERO;// 变异系数
		BigDecimal standard = BigDecimal.ZERO;// 标准差
		BigDecimal variance = BigDecimal.ZERO;// 方差
		BigDecimal median = BigDecimal.ZERO;// 中位数

		BigDecimal one = sumBill.getOneMonth();
		BigDecimal two = sumBill.getTwoMonth();
		BigDecimal three = sumBill.getThreeMonth();
		BigDecimal four = sumBill.getFourMonth();
		BigDecimal five = sumBill.getFiveMonth();
		BigDecimal six = sumBill.getSixMonth();

		sum = one.add(two).add(three).add(four).add(five).add(six);
		sumBill.setTotal(sum);
		avg = sum.divide(new BigDecimal(6), mc);
		sumBill.setTheAverage(avg);

		// 1.方差 s^2=[(x1-x)^2+(x2-x)^2+......(xn-x)^2]/n
		variance = ((one.subtract(avg).pow(2, mc)).add((two.subtract(avg).pow(2, mc))).add((three.subtract(avg).pow(2, mc))).add((four.subtract(avg).pow(2, mc))).add((five.subtract(avg).pow(2, mc))).add((six.subtract(avg).pow(2, mc))))
				.divide(new BigDecimal(6), mc);
		sumBill.setVariance(variance);
		// 2.标准差=方差的算术平方根 Math.sqrt() 开平方 Math.pow(27, 1/3) 就是对27的1/3次方
		// 就是根号3下的27
		standard = new BigDecimal(Math.sqrt(variance.doubleValue()));
		sumBill.setStandard(standard);

		median = buildMedian(one, two, three, four, five, six);
		sumBill.setMedian(median);

		// 变异系数CV = 标准差/平均数
		coeff = standard.divide(avg, mc);
		sumBill.setCoefficintVariation(coeff);
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月6日 <br>
	 * 描述： 中位数 <br/>
	 * 例一：{1,2,3,4,5,6} => (3+4)/2 <br/>
	 * 例二：{1,2,3,4,5} => 3
	 * 
	 * @param values
	 * @return
	 */
	public static BigDecimal buildMedian(BigDecimal... values) {
		Arrays.sort(values);
		int length = values.length;
		BigDecimal mean = BigDecimal.ZERO;
		if (length % 2 == 0) {
			int index = length / 2;
			mean = (values[index].add(values[index - 1])).divide(new BigDecimal(2), mc);
		} else {
			int index = length / 2;
			mean = values[index];
		}
		return mean;
	}

	public class ArrayComparator implements Comparator<BigDecimal> {
		@Override
		public int compare(BigDecimal a, BigDecimal b) {
			return a.compareTo(b);
		}
	}

	public class BillComparator implements Comparator<DebitCardBill> {
		@Override
		public int compare(DebitCardBill a, DebitCardBill b) {
			Integer aVal = a.getStreamVal();
			Integer bVal = b.getStreamVal();
			return aVal.compareTo(bVal);
		}
	}
	
	
	public static void main(String[] args) {
		List<DebitCardBill> debitCardBills = new ArrayList<>();
		DebitCardBill cardBill = new DebitCard.DebitCardBill();
		cardBill.setStreamVal(3);
		cardBill.setStreamName("合计");
		cardBill.setOneMonth(new BigDecimal(300));
		debitCardBills.add(cardBill);
		
		cardBill = new DebitCard.DebitCardBill();
		cardBill.setStreamVal(1);
		cardBill.setStreamName("工资");
		cardBill.setOneMonth(new BigDecimal(100));
		debitCardBills.add(cardBill);
		
		cardBill = new DebitCard.DebitCardBill();
		cardBill.setStreamVal(2);
		cardBill.setStreamName("对公");
		cardBill.setOneMonth(new BigDecimal(200));
		debitCardBills.add(cardBill);
		
		// 排序
		Collections.sort(debitCardBills, new DebitCardServerImpl().new BillComparator());
		System.out.println(debitCardBills.toString());
	}
}
