package com.uletian.ultcrm.business.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.uletian.ultcrm.business.entity.MessageTemplate;
import com.uletian.ultcrm.business.repo.MessageTemplateRepository;
import com.uletian.ultcrm.business.value.TemplateMessage;

@Service
public class TemplateQueueService {
	
	@Qualifier("templateMessageQueue")
	@Autowired
	private Destination templateMessageQueue;
	
	@Autowired
	private JmsTemplate jmsTemplate; 
	
	@Autowired
	private MessageTemplateRepository messageTemplateRepository;
	
	public void sendTemplateMessage(TemplateMessage message){
		jmsTemplate.send(templateMessageQueue, new TemplateMessageCreator(message));
	}
	
	@Cacheable("tech")
	public ArrayList<MessageTemplate> getMessageTemplates(){
		ArrayList<MessageTemplate> arrays = new ArrayList<MessageTemplate>(0);
		Iterable<MessageTemplate> messageTemplates = messageTemplateRepository.findAll();
		if (messageTemplates != null) {
			for (MessageTemplate messageTemplate : messageTemplates) {
				arrays.add(messageTemplate);
			}
		}
		return arrays;
	}
	
	public MessageTemplate getMessageTemplate(String code){
		MessageTemplate messageTemplate = null;
		ArrayList<MessageTemplate> messageTemplates = getMessageTemplates();
		for (MessageTemplate tempMessageTemplate : messageTemplates) {
			if (code.equals(tempMessageTemplate.getCode())) {
				messageTemplate = tempMessageTemplate;
			}
		}
		return messageTemplate;
	}
	
	private class TemplateMessageCreator implements MessageCreator{
		private TemplateMessage message;

		public TemplateMessageCreator(TemplateMessage message){
			this.message = message;
		}

		@Override
		public Message createMessage(Session session) throws JMSException {
			Document doc = DocumentHelper.createDocument();
			Namespace namespace = new Namespace("ns0", "http://crm/91jpfw.cn");
			Element root = doc.addElement(new QName("message", namespace));
			Element template = root.addElement(new QName("template"));
			template.addElement(new QName("openid")).addText(message.getOpenid());
			template.addElement(new QName("templateId")).addText(message.getTemplateId());
			template.addElement(new QName("url")).addCDATA(message.getUrl() == null ? "" : message.getUrl());
			Element params = template.addElement(new QName("params"));
			HashMap<String, String> paramMap = message.getParam();
			Iterator<String> iterator = paramMap.keySet().iterator();
			while (iterator.hasNext()) {
				String keyStr = iterator.next();
				Element param = params.addElement(new QName("param"));
				param.addElement(new QName("key")).addText(keyStr);
				param.addElement(new QName("value")).addCDATA(paramMap.get(keyStr));
			}
			return session.createTextMessage(doc.asXML());
		}
	}
}
