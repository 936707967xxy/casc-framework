package com.hoomsun.app.api.util;

/**
 * @ClassName:     PositionUtils
 * @Description:   TODO(求经纬度距离) 
 * @author         刘栋梁
 * @version        V1.0  
 * @Date           2017-6-26 上午10:41:54 
 */
public class PositionUtils {
	
	
	public static void main(String [] args){
		//纬度  34.2493800000,108.9444100000   经度       陕西省西安市碑林区美莎酒店
		//纬度  34.2437000000,108.9465300000   经度       陕西省西安市碑林区中贸广场
		//34.2365200000,108.8901500000          陕西省西安市高新区旺座国际
		//34.1076500000,108.6190300000          陕西省西安市锦绣华庭
		//34.3410800000,108.9479800000          陕西省西安市张家堡
		System.out.println("公司距离=="+disTance(108.943798,34.249301,108.9465300000,34.2437000000));
		System.out.println(selecTionSort().toString());
	}
	
	/** 
	 * 计算地球上任意两点(经纬度)距离 
	 *  
	 * @param long1 
	 *            第一点经度 
	 * @param lat1 
	 *            第一点纬度 
	 * @param long2 
	 *            第二点经度 
	 * @param lat2 
	 *            第二点纬度 
	 * @return 返回距离 单位：米 
	 */ 

	public static double disTance(double long1, double lat1, double long2, double lat2) {  
	    double a, b, r;  
	    // 地球半径  
	    r = 6378137; 
	    lat1 = lat1 * Math.PI / 180.0;  
	    lat2 = lat2 * Math.PI / 180.0;  
	    a = lat1 - lat2;  
	    b = (long1 - long2) * Math.PI / 180.0;  
	    double d;  
	    double sa2, sb2;  
	    sa2 = Math.sin(a / 2.0);  
	    sb2 = Math.sin(b / 2.0);  
	    d = 2  
	            * r  
	            * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)  
	                    * Math.cos(lat2) * sb2 * sb2));  
	    return d;  
	}  
	
	/**  
	 * 选择排序和冒泡排序差不多，只是冒泡排序在发现比它小的时候就交换，而选择排序是只有在确定了最小的数据之后，才会发生交换。
	 */ 
	public static double[] selecTionSort(){
		double[] a={1,3,2,45,65,33,12};
		  for (int i = 0; i < a.length; i++) {  
	            int min = i;                      /* 将当前下标定义为最小值下标 */  
	            for (int j = i + 1; j < a.length; j++) {  
	                if (a[min] > a[j]) {          /* 如果有小于当前最小值的关键字 */  
	                    min = j;                  /* 将此关键字的下标赋值给min */  
	                }  
	            }  
	            if (i != min) {                   /* 若min不等于i，说明找到比当前外循环小的值，交换 */  
	                double tmp = a[min];  
	                a[min] = a[i];  
	                a[i] = tmp;  
	            }  
	        } 
		  for(int z=0;z<a.length;z++){
			  System.out.println(a[z]);
		  }
		
		return a;
	}
	
}
