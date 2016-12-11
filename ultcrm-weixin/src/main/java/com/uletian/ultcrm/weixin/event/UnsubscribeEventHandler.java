/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.weixin.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.service.CustomerService;

import weixin.popular.bean.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.event.EventHandler;

/**
 * 
 * @author robertxie
 * 2015年9月16日
 */
@Component
public class UnsubscribeEventHandler implements EventHandler{
	
	@Autowired
	private CustomerService customerService;
	
	@Override
	public XMLMessage handleEvent(EventMessage eventMessage) {
		// 修改用户的状态
		String openId = eventMessage.getFromUserName();
		customerService.unsubscribeCustomerByOpenId(openId);
		return new XMLTextMessage(eventMessage,"再见，下次再会");
	}

}
