/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.impl;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * 作者：liusong <br>
 * 创建时间：2018年2月12日 <br>
 * 描述：
 */
public class HelloService implements JavaDelegate {  
	  
    @Override  
    public void execute(DelegateExecution arg0) throws Exception {  
        System.out.println("---------------------------------------------");  
        System.out.println();  
        System.out.println("Hello Service " + this.toString()  
                + "Is Saying Hello To Every One !");  
        System.out.println("---------------------------------------------");  
        System.out.println();  
    }  
  
}  
