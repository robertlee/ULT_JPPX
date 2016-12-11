package com.uletian.ultcrm.weixin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.service.WeixinConfig;

import weixin.popular.support.TokenManager;

public class WeixinTokenScheduled {

	private Logger logger = LoggerFactory.getLogger(WeixinTokenScheduled.class);
	
	@Autowired
	private WeixinConfig weixinConfig;
	
	@Scheduled(cron = "0/1 * * * * ?")
	public void refreshToken() {
		String token = TokenManager.setToken(weixinConfig.getAppId(), weixinConfig.getAppSecret());
		logger.info("Scheduled refreshToken: " + token);
	}
}
