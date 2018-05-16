/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.common.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ywzou
 * 作者：ywzou <br>
 * 创建时间：2017年11月17日 <br>
 * 描述：线程池工厂
 */
public class ThreadPoolExecutorFactory {
	private static final Logger log = LoggerFactory.getLogger(ThreadPoolExecutorFactory.class);
	/**
	 * corePoolSize 池中所保存的线程数，包括空闲线程。
	 */
	private int corePoolSize = 5;
	/**
	 * maximumPoolSize - 池中允许的最大线程数(采用LinkedBlockingQueue时没有作用)。
	 */
	private int maximumPoolSize = 5;
	/**
	 * keepAliveTime -当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间，线程池维护线程所允许的空闲时间 （秒）
	 */
	private long keepAliveTime = 30;
	/**
	 * capacity 执行前用于保持任务的队列（缓冲队列）
	 */
	private int capacity = 20;

	/**
	 * 线程池对象
	 */
	private ThreadPoolExecutor threadPoolExecutor = null;

	public ThreadPoolExecutorFactory() {
		log.info("#####  初始化线程池工厂类 【corePoolSize="+corePoolSize+",maximumPoolSize="+ maximumPoolSize +",keepAliveTime="+keepAliveTime+",capacity="+capacity+"】  ######");
		if (null == getThreadPoolExecutor()) {
			ThreadPoolExecutor t;
			synchronized (ThreadPoolExecutor.class) {
				t = getThreadPoolExecutor();
				if (null == t) {
					synchronized (ThreadPoolExecutor.class) {
						t = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(capacity), new ThreadPoolExecutor.DiscardOldestPolicy());
					}
					setThreadPoolExecutor(t);
				}
			}
		}
		log.info("#####【线程池子初始化成功】####");
	}

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}

	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	public long getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public ThreadPoolExecutor getThreadPoolExecutor() {
		return threadPoolExecutor;
	}

	public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
		this.threadPoolExecutor = threadPoolExecutor;
	}
	
	
}
