/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.after;

import java.util.Calendar;

import com.hoomsun.core.controller.SysOAEmployeeController;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年5月9日 <br>
 * 描述：
 */
public class Test {

	public static void main(String[] args) {

		Calendar c = Calendar.getInstance();

		c.set(Calendar.YEAR, 1994);
		c.set(Calendar.MONTH, 3);
		c.set(Calendar.DAY_OF_MONTH, 10);

		long times = c.getTime().getTime();
		long times2 = c.getTime().getTime();

		int[] a = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33 };
		int[] b = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 };

		String result = "";

		int z = 33;
		for (int i = 0; i < 6; i++) {
			int index = (int) (times % z);
			result = result + a[index] + " ";
			z--;
			for (int j = index; j < z; j++) {
				a[j] = a[(int) j + 1];
			}

			times = times / 33;

		}

		result = result + "[" + b[(int) (times2 % 16)] + "]";

		System.out.println(result);

	}
}
