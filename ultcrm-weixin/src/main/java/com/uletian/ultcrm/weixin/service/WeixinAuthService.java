/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.weixin.service;

import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.repo.CustomerRepository;
import com.uletian.ultcrm.business.service.CustomerService;
import com.uletian.ultcrm.business.service.EventMessageService;

import com.uletian.ultcrm.business.service.WeixinConfig;
import com.uletian.ultcrm.common.util.StringUtils;

import com.uletian.ultcrm.weixin.controller.WeixinAuthController;
import com.google.gson.Gson;

import weixin.popular.api.Global;
import weixin.popular.api.WeixinAPI;
import weixin.popular.bean.UserInfo;

/** 
 * @author robertxie
 * 2015年9月19日
 */
@Component
public class WeixinAuthService {
	
	private static Logger logger = Logger.getLogger(WeixinAuthService.class);
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private WeixinConfig weixinConfig;
	
	@Autowired
	private EventMessageService eventMessageService;
	
	public Customer getWeixinUserInfo(String weixinCode) {
		Customer result = null;
		logger.info("getWeixinUserInfo code is "+weixinCode);
		//通过code换取网页授权access_token
		Map tokenMap = WeixinAPI.getOpenId(weixinCode, weixinConfig.getAppId(), weixinConfig.getAppSecret(), Global.grantType);
		//获取到access_token, openId, refresh_token, 暂时不刷新access_token
		//第三步：刷新access_token（如果需要）
		//暂时不需要处理
		//第四步：拉取用户信息(需scope为 snsapi_userinfo)
		if (tokenMap.get("errcode") != null) {
			logger.error("can not get openid, return info is "+tokenMap);
		}
		else {
			// 先用openId去检查一下，比较一下最近检查的时间
			String openId = tokenMap.get("openid").toString();
			//南昌培训项目不进行30天在线分析 Robert Lee
			result = customerService.checkWeixinUpdateTime(tokenMap.get("openid").toString());
			if (result != null) {
				return result;
			}
			UserInfo userInfo = WeixinAPI.userInfo(tokenMap.get("access_token").toString(), tokenMap.get("openid").toString());
			logger.info("getWeixinUserInfo userInfo is "+userInfo.toString());
			// 保存userInfo 到customer表中
			if (userInfo.isSuccess()) {
				Customer customer = customerService.getCustomerByOpenId(openId);
				boolean isFirst = false;
				if (customer == null) {
					customer = new Customer();
					customer.setOpenid(openId);
					isFirst = true;
				}
				customer.setStatus(1);
				BeanUtils.copyProperties(userInfo, customer);
				
				// 用户昵称的判断
				String nickName = customer.getNickname();
				if (StringUtils.isNotBlank(nickName) && nickName.contains("\\")) {
					int i = nickName.indexOf("\\");
					String newNickName = nickName.substring(0, i);
					customer.setNickname(newNickName);
				}
				
				result = customerService.createCustomer(customer);
				logger.info("A new customer created."+customer.getId());
				if (isFirst) {
					eventMessageService.sendEvent("firstjoin", customer.getId(), null);
				}
			}
		}
		return result;
	}
}
