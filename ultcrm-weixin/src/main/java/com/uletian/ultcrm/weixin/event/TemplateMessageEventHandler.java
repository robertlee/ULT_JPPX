/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.weixin.event;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.TemplateMessageResultCount;
import com.uletian.ultcrm.business.repo.CustomerRepository;
import com.uletian.ultcrm.business.repo.TemplateMessageResultCountRepository;

import reactor.util.StringUtils;
import weixin.popular.bean.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.event.EventHandler;

/**
 * 
 * @author robertxie
 * 2015年9月29日
 */
@Component
public class TemplateMessageEventHandler implements EventHandler{

	private static Logger logger = Logger.getLogger(TemplateMessageEventHandler.class);
	
	@Autowired
	private TemplateMessageResultCountRepository templateMessageResultCountRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public XMLMessage handleEvent(EventMessage eventMessage) {
		logger.debug("eventMessage is "+eventMessage.toString());
		// 判断消息发送的结果是成功还是失败
		String status = eventMessage.getStatus();
		// 用户
		String customerOpenId = eventMessage.getFromUserName();
		String msgId = eventMessage.getMsgId();
		
		// 查询客户信息
		Customer customer = customerRepository.findByOpenid(customerOpenId);
		TemplateMessageResultCount templateMessageResultCount = templateMessageResultCountRepository.findByCustomerAndMsgId(customer, msgId);
		if (templateMessageResultCount == null) {
			// 新建一个记录
			templateMessageResultCount = new TemplateMessageResultCount();
			templateMessageResultCount.setMsgId(msgId);
			templateMessageResultCount.setCustomer(customer);
		}
		
		if (!StringUtils.isEmpty(status)) {
			if (status.startsWith("success")) {
				// 发送成功
				templateMessageResultCount.setResult(TemplateMessageResultCount.RESULT_SUCCESS);
			}
			else if (status.contains("block")) {
				// 用户拒绝
				templateMessageResultCount.setResult(TemplateMessageResultCount.RESULT_USER_BLOCK);
			}
			else if (status.contains("system")) {
				// 系统故障
				templateMessageResultCount.setResult(TemplateMessageResultCount.RESULT_SYSTEM_FAILED);
			}
		}
		templateMessageResultCount.setFinishTime(new Date());
		templateMessageResultCountRepository.save(templateMessageResultCount);
		return null;
	}
	
	
}
