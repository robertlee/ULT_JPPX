/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.weixin;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.service.WeixinConfig;

import weixin.popular.support.TokenManager;

/**
 * 
 * @author robertxie
 * 2015年9月19日
 */
@Component
public class WeixinManager {
	private static Logger logger = Logger.getLogger(WeixinManager.class);
	
	@Autowired
	private WeixinConfig weixinConfig;
	
	@PostConstruct 
	public void  init(){
		if (Boolean.parseBoolean(weixinConfig.getWeixinToken())) {
			logger.info("初始化微信\nappid:" + weixinConfig.getAppId()+"\nappsecret:"+weixinConfig.getAppSecret());
			TokenManager.init(weixinConfig.getAppId(), weixinConfig.getAppSecret());
		}
	}
	
}
