/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package weixin.popular.event;

import weixin.popular.bean.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;

/**
 * 
 * @author robertxie
 * 2015年9月16日
 */
public interface EventHandler {
	
	public XMLMessage handleEvent(EventMessage eventMessage);
	
}
