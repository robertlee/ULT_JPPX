/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.weixin;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 
 * @author robertxie
 * 2015年9月17日
 */
public class ApplicationListener implements org.springframework.context.ApplicationListener<ApplicationEvent>
,ApplicationContextInitializer<ConfigurableApplicationContext>{
	
	private static Logger logger = Logger.getLogger(ApplicationListener.class);
	
	/**
	 *  需要处理四种事件,按照顺序如下：
	 *  1.ApplicationStartedEvent 
	 *  2.ApplicationEnvironmentPreparedEvent 
	 *  3.ApplicationPreparedEvent 
	 *  4.ApplicationFailedEvent 
	 */	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		/********
		 logger.info("handle event："+event.getClass());
		
		if (event instanceof ApplicationPreparedEvent) {
			// 容器已经加载完毕
			logger.info("handle event,ApplicationStartedEvent");
		}
		
		if (event instanceof ApplicationEnvironmentPreparedEvent) {
			// 容器已经加载完毕
			logger.info("handle event,ApplicationEnvironmentPreparedEvent");
		}
		
		if (event instanceof ApplicationPreparedEvent) {
			// 容器已经加载完毕
			logger.info("handle event,ApplicationPreparedEvent");
		}
		
		if (event instanceof ApplicationFailedEvent) {
			// 容器已经加载完毕
			logger.info("handle event,ApplicationFailedEvent");
		}
		 ********/
		
	}



	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		logger.info("Context init lister start........");
	}
	
}
