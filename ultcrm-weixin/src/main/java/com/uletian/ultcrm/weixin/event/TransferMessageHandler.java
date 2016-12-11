/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.weixin.event;

import org.springframework.stereotype.Component;

import weixin.popular.bean.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTransferMessage;
import weixin.popular.event.EventHandler;

/**
 * 将用户的消息都转发到多客服系统，本系统不处理， 发送类型为transfer_customer_service的消息
 * @author robertxie
 * 2015年11月6日
 */
@Component
public class TransferMessageHandler implements EventHandler {

	@Override
	public XMLMessage handleEvent(EventMessage eventMessage) {
		return new XMLTransferMessage(eventMessage);
	}



}
