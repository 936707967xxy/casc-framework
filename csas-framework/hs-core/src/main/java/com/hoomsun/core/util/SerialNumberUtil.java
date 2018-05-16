/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.util;


import com.hoomsun.common.thread.ThreadPoolExecutorFactory;
import com.hoomsun.core.server.inter.SerialNumberServerI;

/**
 * 作者：liming <br>
 * 创建时间：2018年1月26日 <br>
 * 描述：
 */
public class SerialNumberUtil {
	private SerialNumberServerI serialNumberServer;
	
	public SerialNumberUtil(SerialNumberServerI serialNumberServer) {
		this.serialNumberServer = serialNumberServer;
	}

	public SerialNumberServerI getSerialNumberServer() {
		return serialNumberServer;
	}

	public void setSerialNumberServer(SerialNumberServerI serialNumberServer) {
		this.serialNumberServer = serialNumberServer;
	}

	public String buildNumber(String type, String prefix) {
		ThreadPoolExecutorFactory executorFactory = new ThreadPoolExecutorFactory();
		SerialNumberThread numberThread =  new SerialNumberThread(serialNumberServer, type, prefix);
		executorFactory.getThreadPoolExecutor().execute(numberThread);
		return numberThread.getSerialNumber();
	}
	
	
	public class SerialNumberThread implements Runnable{
		
		private SerialNumberServerI serialNumberServer;
		private String type;
		private String prefix;
		private String serialNumber;
		
		public SerialNumberThread(SerialNumberServerI serialNumberServer,String type, String prefix) {
			this.serialNumberServer = serialNumberServer;
			this.type = type;
			this.prefix = prefix;
		}

		public SerialNumberServerI getSerialNumberServer() {
			return serialNumberServer;
		}


		public void setSerialNumberServer(SerialNumberServerI serialNumberServer) {
			this.serialNumberServer = serialNumberServer;
		}
		
		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getPrefix() {
			return prefix;
		}

		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}
		
		public String getSerialNumber() {
			return serialNumber;
		}

		public void setSerialNumber(String serialNumber) {
			this.serialNumber = serialNumber;
		}

		@Override
		public void run() {
			setSerialNumber(serialNumberServer.createNumber(type, prefix));
			System.out.println(serialNumber);
		}
		
	}
}
