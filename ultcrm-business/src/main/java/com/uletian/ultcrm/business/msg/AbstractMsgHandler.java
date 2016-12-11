/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.msg;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

/**
 * 
 * @author robertxie
 * 2015年10月23日
 */
public abstract class AbstractMsgHandler extends MessageService implements MessageListener{
	private static Logger logger = Logger.getLogger(AbstractMsgHandler.class);
	
	protected Class clazz;
	protected String name;
	protected String topicName;
	
	protected Map<String,MsgActionHandler> handlerMap = new HashMap<String,MsgActionHandler>();
	
	public AbstractMsgHandler(Class clazz, String name){
		this(clazz,name,name);
	}
	
	public AbstractMsgHandler(Class clazz, String name,String topicName){
		this.clazz = clazz;
		this.name = name;
		this.topicName = topicName;
		addHandlers();
	}
	
	public void addHandler(String actionName, MsgActionHandler handler) {
		handlerMap.put(actionName, handler);
	}
	
	public abstract void addHandlers();
	
	/**
	 * 获取消息，然后定位到action字段，然后指定给特定的handler处理。
	 */
	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			logger.info("receive a msg :"+textMessage.getText());
			MsgObject msgObject= this.convertMsgStringToObject(textMessage.getText(), clazz, name);
			// 获取action
			String action = msgObject.getAction();
			MsgActionHandler  handler = handlerMap.get(action);
			if (handler != null) {
				logger.info("hanling msg,topic="+this.topicName+", action= "+action+", msg:"+msgObject);
				handler.handleMsg(msgObject);
			}
			else {
				logger.error("can't found the handler,topic="+this.topicName+"action= "+action);
			}
		} catch (Exception e) {
			logger.error("处理消息失败，消息内容是："+message, e);
		}
		
	}
	
}
