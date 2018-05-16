/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.common.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ywzou 作者：ywzou <br>
 *         创建时间：2017年11月17日 <br>
 *         描述：线程的类
 */
public abstract class RunningAbstract implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(RunningAbstract.class);
	
	public volatile boolean keepRun = true;
	public long intervalTime = 0;// 首次间隔时长
	public long stepTime = 0;// 每次间隔时长
	public int pushNum = 1;// 推送次数
	public boolean increment = false;// 是否递增
	
	private Integer index = 0;
	
	public RunningAbstract() {
	}
	
	public RunningAbstract(long intervalTime, long stepTime, int pushNum, boolean increment) {
		this.intervalTime = intervalTime;
		this.stepTime = stepTime;
		this.pushNum = pushNum;
		this.increment = increment;
	}

	@Override
	public void run() {
		while (keepRun) {
			index = index + 1;
			log.info("####【线程执行"+ index +"次】####");
			doWork();
		}
	}

	public abstract void doWork();

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月18日 <br>
	 * 描述： 首次间隔时长
	 * 
	 * @return
	 */
	public long getIntervalTime() {
		return intervalTime;
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月18日 <br>
	 * 描述： 首次间隔时长
	 * 
	 * @param intervalTime
	 */
	public void setIntervalTime(long intervalTime) {
		this.intervalTime = intervalTime;
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月18日 <br>
	 * 描述： 每次间隔时长
	 * 
	 * @return
	 */
	public long getStepTime() {
		return stepTime;
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月18日 <br>
	 * 描述：每次间隔时长
	 * 
	 * @param stepTime
	 */
	public void setStepTime(long stepTime) {
		this.stepTime = stepTime;
	}
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月18日 <br>
	 * 描述： 推送次数
	 * @return
	 */
	public int getPushNum() {
		return pushNum;
	}
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月18日 <br>
	 * 描述： 推送次数
	 * @param pushNum
	 */
	public void setPushNum(int pushNum) {
		this.pushNum = pushNum;
	}
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月18日 <br>
	 * 描述：是否递增
	 * @return
	 */
	public boolean isIncrement() {
		return increment;
	}
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月18日 <br>
	 * 描述： 是否递增
	 * @param increment
	 */
	public void setIncrement(boolean increment) {
		this.increment = increment;
	}

}
