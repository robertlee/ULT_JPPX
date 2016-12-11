/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.weixin.controller;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import weixin.popular.api.Global;
import weixin.popular.api.WeixinAPI;

import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.Event;
import com.uletian.ultcrm.business.repo.CustomerRepository;
import com.uletian.ultcrm.business.repo.EventRepository;
import com.uletian.ultcrm.business.service.CustomerInfoSyncService;
import com.uletian.ultcrm.business.service.CustomerService;
import com.uletian.ultcrm.business.service.EventMessageService;
import com.uletian.ultcrm.business.service.WeixinConfig;
import com.uletian.ultcrm.weixin.service.WeixinAuthService;
import com.google.gson.Gson;

/**
 * 
 * @author robertxie
 * 2015年9月17日
 */
@RestController
public class WeixinAuthController {

	
	private static Logger logger = Logger.getLogger(WeixinAuthController.class);
	
	@Autowired
	private WeixinConfig weixinConfig;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private WeixinAuthService weixinAuthService;
	
	@Autowired
	private CustomerInfoSyncService customerInfoSyncService;
	
	@Autowired
	private EventRepository eventRepository;
	
	private Gson gson = new Gson();

	@Autowired
	private EventMessageService eventMessageService;
	
	/**
	 * 根据code获取customer，如果没有查询到就新建一个customer。
	 * @param weixinCode
	 * @param type snsapi_base,snsapi_userinfo
	 * @return
	 */
	@RequestMapping("/getCustomerByCode/{weixinCode}/{type}")
	public Customer getCustomerByCode(@PathVariable("weixinCode")String weixinCode,@PathVariable("type")String type){
		logger.info("weixinCode is "+weixinCode+", type is "+type);
		if (StringUtils.isEmpty(type)) {
			return new Customer();
		}
		Customer customer = null;
		if (type.equalsIgnoreCase("snsapi_base")) {
			Map map = WeixinAPI.getOpenId(weixinCode, weixinConfig.getAppId(), weixinConfig.getAppSecret(), Global.grantType);
			logger.info("type is snsapi_base, and  weixinCode is " + weixinCode + " json:\n"+gson.toJson(map));
			if (map.get("openid") != null) {
				customer = customerService.getCustomerByOpenId(map.get("openid").toString());
			}
		}
		else {
			customer = weixinAuthService.getWeixinUserInfo(weixinCode);
		}
		return customer;
	}
}
