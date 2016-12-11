/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.weixin.service;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import weixin.popular.api.MessageAPI;
import weixin.popular.bean.templatemessage.TemplateMessage;
import weixin.popular.bean.templatemessage.TemplateMessageItem;
import weixin.popular.bean.templatemessage.TemplateMessageResult;
import weixin.popular.support.TokenManager;

import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.TemplateMessageResultCount;
import com.uletian.ultcrm.business.repo.TemplateMessageResultCountRepository;
import com.uletian.ultcrm.business.service.WeixinConfig;

/**
 * 微信模板消息的接口
 * @author robertxie
 * 2015年9月29日
 */
@Component
public class WeixinTplMsgService {
	
	private static Logger logger = Logger.getLogger(WeixinTplMsgService.class);
	
	@Autowired
	private WeixinConfig weixinConfig;
	
	@Autowired
	private TemplateMessageResultCountRepository templateMessageResultCountRepository;
	
	public void sendMessage(Customer customer,String url,String templateId, HashMap<String,String> param) {
		logger.info("the open id is "+customer.getOpenid());
		TemplateMessage tplMsg = new TemplateMessage();
		
		tplMsg.setTouser(customer.getOpenid());
		tplMsg.setTemplate_id(templateId);
		tplMsg.setUrl(url);
		tplMsg.setTopcolor(TemplateMessage.DEFAULT_COLOR);
		
		LinkedHashMap<String, TemplateMessageItem> data = new LinkedHashMap<String, TemplateMessageItem>();
		for (String key: param.keySet()) {
			// 处理参数
			TemplateMessageItem item = new TemplateMessageItem(param.get(key),TemplateMessage.DEFAULT_COLOR);
			data.put(key, item);
		}
		
		tplMsg.setData(data);
		TemplateMessageResult result = MessageAPI.messageTemplateSend(TokenManager.getToken(weixinConfig.getAppId()), tplMsg);
		logger.info("Send tpl msg result is "+result.toString());
		
		// 将发送结果保留起来
		if (!StringUtils.isEmpty(result.getMsgid())) {
			TemplateMessageResultCount templateMessageResultCount = new TemplateMessageResultCount();
			Date currentDate = new Date();
			templateMessageResultCount.setCustomer(customer);
			templateMessageResultCount.setSendTime(currentDate);
			templateMessageResultCount.setMsgId(result.getMsgid().toString());
			templateMessageResultCount.setFinishTime(currentDate);
			templateMessageResultCount.setMsgId(result.getMsgid().toString());
			templateMessageResultCount.setResult(result.getErrcode());
			templateMessageResultCountRepository.save(templateMessageResultCount);
		}
		else {
			// 发送失败
			logger.error("error code is "+result.getErrcode()+", error message is "+result.getErrmsg());
		}
	}
	
}
