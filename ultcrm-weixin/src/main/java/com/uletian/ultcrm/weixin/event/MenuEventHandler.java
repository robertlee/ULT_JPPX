/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.weixin.event;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.Config;
import com.uletian.ultcrm.business.service.ConfigService;

import weixin.popular.bean.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLNewsMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.event.EventHandler;

/**
 * 
 * @author robertxie
 * 2015年9月16日
 */
@Component
public class MenuEventHandler implements EventHandler{
	private static final String WEIXIN_TELEPHONE="WEIXIN_TELEPHONE";
	
	private static final String WEIXIN_COMPANY_URL="WEIXIN_COMPANY_URL";
	private static final String WEIXIN_COMPANY_DESC="WEIXIN_COMPANY_DESC";
	private static final String WEIXIN_COMPANY_TITLE="WEIXIN_COMPANY_TITLE";
	private static final String WEIXIN_COMPANY_PICURL="WEIXIN_COMPANY_PICURL";
	
	@Autowired
	private ConfigService configService;
	
	// 处理微信菜单事件
	@Override
	public XMLMessage handleEvent(EventMessage eventMessage) {

		switch(eventMessage.getEventKey()) {
			case "telephone":return  new  XMLTextMessage(eventMessage,configService.getValue(WEIXIN_TELEPHONE)); 
			case "companyInfo":return handleCompanyInfo(eventMessage);
		}
		
		return null;
	}
	
	private XMLMessage handleCompanyInfo(EventMessage eventMessage) {
		
		XMLNewsMessage.Article article = new XMLNewsMessage.Article();
		article.setDescription(configService.getValue(WEIXIN_COMPANY_DESC));
		article.setPicurl(configService.getValue(WEIXIN_COMPANY_PICURL));
		article.setTitle(configService.getValue(WEIXIN_COMPANY_TITLE));
		article.setUrl(configService.getValue(WEIXIN_COMPANY_URL));
		List<XMLNewsMessage.Article> articleList = new ArrayList<XMLNewsMessage.Article>();
		articleList.add(article);
		XMLNewsMessage result = new XMLNewsMessage(eventMessage.getFromUserName(),eventMessage.getToUserName(),articleList);
		
		return result;
	}
	
}
