/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @author robertxie
 * 2015年9月17日
 */
@Component 
public class WeixinConfig {
	
	@Value("${appId}")
	private String appId;
	
	@Value("${appSecret}")
	private String appSecret;
	
	@Value("${hostPath}")
	private String hostPath;
	
	@Value("${weixinToken}")
	private String weixinToken;
	
	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}
	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	/**
	 * @return the appSecret
	 */
	public String getAppSecret() {
		return appSecret;
	}
	/**
	 * @param appSecret the appSecret to set
	 */
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	/**
	 * @return the hostPath
	 */
	public String getHostPath() {
		return hostPath;
	}
	/**
	 * @param hostPath the hostPath to set
	 */
	public void setHostPath(String hostPath) {
		this.hostPath = hostPath;
	}
	
	public String getWeixinToken() {
		return weixinToken;
	}
	public void setWeixinToken(String weixinToken) {
		this.weixinToken = weixinToken;
	}
	
	
	
}
