/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package weixin.popular.bean.xmlmessage;

import weixin.popular.bean.EventMessage;

/**
 * 
 * @author robertxie
 * 2015年11月6日
 */
public class XMLTransferMessage extends XMLMessage{

	/**
	 * @param toUserName
	 * @param fromUserName
	 * @param msgType
	 */
	public XMLTransferMessage(String toUserName, String fromUserName) {
		super(toUserName, fromUserName, "transfer_customer_service");
	}
	
	public XMLTransferMessage(EventMessage eventMessage) {
		super(eventMessage.getFromUserName(), eventMessage.getToUserName(), "transfer_customer_service");
	}

	@Override
	public String subXML() {
		return null;
	}
	
}
